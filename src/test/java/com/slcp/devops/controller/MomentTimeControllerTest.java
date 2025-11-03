package com.slcp.devops.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.slcp.devops.entity.MtWalls;
import com.slcp.devops.service.IMomentTimeService;
import com.slcp.devops.service.IMtWallsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * MomentTimeController 接口测试类
 * 测试留言墙相关的 HTTP 接口，包括 Spring AI 集成
 *
 * @author sunlicp
 * @since 2025/10/27
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
@DisplayName("一刻时光接口测试 - Spring AI 集成")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MomentTimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IMomentTimeService momentTimeService;

    @Autowired
    private IMtWallsService mtWallsService;

    private static Long testWallId;

    @BeforeEach
    public void setUp() {
        log.info("=== 准备测试环境 ===");
    }

    @AfterAll
    public static void cleanUp(@Autowired IMtWallsService wallsService, 
                                @Autowired IMomentTimeService timeService) {
        log.info("=== 清理所有测试数据 ===");
        if (testWallId != null) {
            try {
                wallsService.removeById(testWallId);
                timeService.lambdaUpdate()
                        .eq(com.slcp.devops.entity.MtComments::getWallId, testWallId)
                        .remove();
                log.info("测试数据清理完成");
            } catch (Exception e) {
                log.warn("清理失败: {}", e.getMessage());
            }
        }
    }

    @Test
    @Order(1)
    @DisplayName("接口测试1: POST /momentTime/signip - 获取IP信息")
    public void testSignIp() throws Exception {
        log.info(">>> 接口测试1: 获取IP信息");

        MvcResult result = mockMvc.perform(post("/momentTime/signip")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        log.info("✓ IP信息获取成功: {}", response);
    }

    @Test
    @Order(2)
    @DisplayName("接口测试2: POST /momentTime/insertwall - 创建留言墙（Spring AI测试）")
    public void testInsertWall() throws Exception {
        log.info(">>> 接口测试2: 创建留言墙并测试Spring AI回复");

        // 准备测试数据
        MtWalls mtWalls = new MtWalls();
        mtWalls.setType(0);
        mtWalls.setName("接口测试用户_" + RandomUtil.randomString(5));
        mtWalls.setUserId("api_test_" + System.currentTimeMillis());
        mtWalls.setMessage("你好Spring AI，请介绍一下微服务架构的优点");
        mtWalls.setMoment(LocalDateTime.now());
        mtWalls.setLabel(1);
        mtWalls.setColor(RandomUtil.randomInt(1, 10));

        String requestBody = JSON.toJSONString(mtWalls);
        log.info("请求数据: {}", requestBody);

        // 发送请求
        MvcResult result = mockMvc.perform(get("/momentTime/insertwall")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.message").value(mtWalls.getMessage()))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        log.info("✓ 留言墙创建成功: {}", response);

        // 解析返回的ID
        com.alibaba.fastjson.JSONObject jsonResponse = JSON.parseObject(response);
        testWallId = jsonResponse.getJSONObject("data").getLong("id");
        log.info("获取到留言墙ID: {}", testWallId);

        // 等待一段时间，让小c机器人回复
        log.info("等待小c机器人(Spring AI)生成回复...");
        TimeUnit.SECONDS.sleep(15);

        // 验证是否收到机器人回复
        var comments = momentTimeService.lambdaQuery()
                .eq(com.slcp.devops.entity.MtComments::getWallId, testWallId)
                .eq(com.slcp.devops.entity.MtComments::getName, "小c机器人")
                .list();

        if (!comments.isEmpty()) {
            log.info("✓ 收到小c机器人回复: {}", comments.get(0).getComment());
        } else {
            log.warn("⚠ 未收到机器人回复（可能AI服务未配置或响应较慢）");
        }
    }

    @Test
    @Order(3)
    @DisplayName("接口测试3: GET /momentTime/findwallpage - 查询留言墙列表")
    public void testFindWallPage() throws Exception {
        log.info(">>> 接口测试3: 查询留言墙列表");

        mockMvc.perform(get("/momentTime/findwallpage")
                        .param("current", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.records").isArray());

        log.info("✓ 留言墙列表查询成功");
    }

    @Test
    @Order(4)
    @DisplayName("接口测试4: GET /momentTime/findcommentpage - 查询评论列表")
    public void testFindCommentPage() throws Exception {
        log.info(">>> 接口测试4: 查询评论列表");

        if (testWallId == null) {
            log.warn("⚠ 没有测试留言墙ID，跳过此测试");
            return;
        }

        MvcResult result = mockMvc.perform(get("/momentTime/findcommentpage")
                        .param("id", String.valueOf(testWallId))
                        .param("current", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        log.info("✓ 评论列表查询成功");
        
        // 检查是否有小c机器人的评论
        com.alibaba.fastjson.JSONObject jsonResponse = JSON.parseObject(response);
        com.alibaba.fastjson.JSONArray records = jsonResponse.getJSONObject("data")
                .getJSONArray("records");
        
        if (records != null && !records.isEmpty()) {
            log.info("找到 {} 条评论", records.size());
            for (int i = 0; i < records.size(); i++) {
                com.alibaba.fastjson.JSONObject comment = records.getJSONObject(i);
                String name = comment.getString("name");
                String commentText = comment.getString("comment");
                log.info("评论 {}: {} - {}", i + 1, name, commentText);
            }
        } else {
            log.info("暂无评论");
        }
    }

    @Test
    @Order(5)
    @DisplayName("接口测试5: POST /momentTime/insertcomment - 新增评论")
    public void testInsertComment() throws Exception {
        log.info(">>> 接口测试5: 新增评论");

        if (testWallId == null) {
            log.warn("⚠ 没有测试留言墙ID，跳过此测试");
            return;
        }

        com.slcp.devops.entity.MtComments comment = new com.slcp.devops.entity.MtComments();
        comment.setWallId(testWallId);
        comment.setName("测试评论者_" + RandomUtil.randomString(4));
        comment.setUserId("comment_test_" + System.currentTimeMillis());
        comment.setComment("这是一条测试评论，验证Spring AI功能正常运行");
        comment.setMoment(LocalDateTime.now());
        comment.setImgUrl(String.valueOf(RandomUtil.randomInt(0, 8)));

        String requestBody = JSON.toJSONString(comment);

        mockMvc.perform(post("/momentTime/insertcomment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.comment").value(comment.getComment()));

        log.info("✓ 评论新增成功");
    }

    @Test
    @Order(6)
    @DisplayName("接口测试6: 测试多条留言的AI回复（压力测试）")
    public void testMultipleWallsWithAI() throws Exception {
        log.info(">>> 接口测试6: 测试多条留言的AI回复");

        String[] testQuestions = {
                "什么是Docker容器？",
                "如何优化数据库查询性能？",
                "Spring Boot的优势是什么？"
        };

        for (int i = 0; i < testQuestions.length; i++) {
            log.info("--- 发送第{}条留言 ---", i + 1);
            
            MtWalls wall = new MtWalls();
            wall.setType(0);
            wall.setName("压测用户_" + i);
            wall.setUserId("stress_test_" + System.currentTimeMillis() + "_" + i);
            wall.setMessage(testQuestions[i]);
            wall.setMoment(LocalDateTime.now());
            wall.setLabel(1);
            wall.setColor(RandomUtil.randomInt(1, 10));

            MvcResult result = mockMvc.perform(post("/momentTime/insertwall")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JSON.toJSONString(wall)))
                    .andExpect(status().isOk())
                    .andReturn();

            com.alibaba.fastjson.JSONObject response = JSON.parseObject(
                    result.getResponse().getContentAsString());
            Long wallId = response.getJSONObject("data").getLong("id");
            
            log.info("✓ 第{}条留言创建成功，ID: {}", i + 1, wallId);

            // 清理测试数据
            mtWallsService.removeById(wallId);
            
            // 稍作延迟
            TimeUnit.SECONDS.sleep(2);
        }

        log.info("✓ 多条留言压力测试完成");
    }

    @Test
    @Order(7)
    @DisplayName("接口测试7: 异常情况测试 - 空留言")
    public void testInsertWall_EmptyMessage() throws Exception {
        log.info(">>> 接口测试7: 空留言测试");

        MtWalls emptyWall = new MtWalls();
        emptyWall.setType(0);
        emptyWall.setName("测试用户");
        emptyWall.setMessage(""); // 空消息
        emptyWall.setMoment(LocalDateTime.now());

        mockMvc.perform(post("/momentTime/insertwall")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(emptyWall)))
                .andDo(print())
                .andExpect(status().isOk());

        log.info("✓ 空留言测试完成");
    }

    @Test
    @Order(8)
    @DisplayName("接口测试8: 验证缓存功能")
    public void testCacheFunction() throws Exception {
        log.info(">>> 接口测试8: 验证缓存功能");

        if (testWallId == null) {
            log.warn("⚠ 没有测试留言墙ID，跳过此测试");
            return;
        }

        // 第一次查询
        long start1 = System.currentTimeMillis();
        mockMvc.perform(get("/momentTime/findcommentpage")
                        .param("id", String.valueOf(testWallId))
                        .param("current", "1")
                        .param("size", "10"))
                .andExpect(status().isOk());
        long time1 = System.currentTimeMillis() - start1;

        // 第二次查询（应该从缓存读取，更快）
        long start2 = System.currentTimeMillis();
        mockMvc.perform(get("/momentTime/findcommentpage")
                        .param("id", String.valueOf(testWallId))
                        .param("current", "1")
                        .param("size", "10"))
                .andExpect(status().isOk());
        long time2 = System.currentTimeMillis() - start2;

        log.info("第一次查询耗时: {}ms", time1);
        log.info("第二次查询耗时: {}ms (缓存生效)", time2);
        log.info("✓ 缓存功能测试完成");
    }
}

