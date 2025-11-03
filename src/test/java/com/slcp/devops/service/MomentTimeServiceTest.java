package com.slcp.devops.service;

import cn.hutool.core.util.RandomUtil;
import com.slcp.devops.entity.MtComments;
import com.slcp.devops.entity.MtWalls;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MomentTimeService 测试类
 * 测试一刻时光的留言墙功能，特别是 Spring AI 集成
 * 
 * @author sunlicp
 * @since 2025/10/27
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@DisplayName("一刻时光服务测试 - Spring AI 集成测试")
public class MomentTimeServiceTest {

    @Autowired
    private IMomentTimeService momentTimeService;

    @Autowired(required = false)
    private AiChatService aiChatService;

    @Autowired
    private IMtWallsService mtWallsService;

    private MtWalls testWall;
    private Long testWallId;

    @BeforeEach
    public void setUp() {
        log.info("=== 开始测试前准备 ===");
        // 创建测试数据
        testWall = new MtWalls();
        testWall.setType(0); // 0-信息 1-图片
        testWall.setName("测试用户_" + RandomUtil.randomString(5));
        testWall.setUserId("test_user_" + System.currentTimeMillis());
        testWall.setMessage("你好，Spring AI，请问Java怎么学习？");
        testWall.setMoment(LocalDateTime.now());
        testWall.setLabel(1);
        testWall.setColor(RandomUtil.randomInt(1, 10));
        log.info("测试数据准备完成: {}", testWall.getName());
    }

    @AfterEach
    public void tearDown() {
        log.info("=== 测试清理 ===");
        // 清理测试数据
        if (testWallId != null) {
            try {
                // 删除测试留言墙
                mtWallsService.removeById(testWallId);
                // 删除关联的评论（小c机器人的回复）
                momentTimeService.lambdaUpdate()
                        .eq(MtComments::getWallId, testWallId)
                        .remove();
                log.info("测试数据清理完成，wallId: {}", testWallId);
            } catch (Exception e) {
                log.warn("清理测试数据失败: {}", e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("测试1: 保存留言墙 - 基础功能测试")
    public void testSaveWall_BasicFunction() {
        log.info(">>> 测试1: 保存留言墙 - 基础功能");
        
        // 执行保存
        MtWalls savedWall = momentTimeService.saveWall(testWall);
        testWallId = savedWall.getId();

        // 验证保存结果
        assertNotNull(savedWall, "保存的留言墙不应为空");
        assertNotNull(savedWall.getId(), "留言墙ID不应为空");
        assertEquals(testWall.getMessage(), savedWall.getMessage(), "留言内容应该一致");
        assertEquals(testWall.getName(), savedWall.getName(), "用户名应该一致");
        
        log.info("✓ 留言墙保存成功，ID: {}", savedWall.getId());
        
        // 验证数据库中是否真的保存了
        MtWalls dbWall = mtWallsService.getById(savedWall.getId());
        assertNotNull(dbWall, "数据库中应该能查询到保存的留言墙");
        log.info("✓ 数据库验证通过");
    }

    @Test
    @DisplayName("测试2: Spring AI 机器人回复功能测试")
    public void testSaveWall_WithAiResponse() throws InterruptedException {
        log.info(">>> 测试2: Spring AI 机器人回复功能");
        
        // 检查 Spring AI 服务是否可用
        if (aiChatService == null) {
            log.warn("⚠ Spring AI 服务未配置，测试将验证降级逻辑");
        } else {
            log.info("✓ Spring AI 服务已配置，将测试智能回复功能");
        }

        // 执行保存
        MtWalls savedWall = momentTimeService.saveWall(testWall);
        testWallId = savedWall.getId();
        
        log.info("留言墙已保存，ID: {}，等待小c机器人回复...", testWallId);
        
        // 等待异步任务执行（小c机器人回复是异步的）
        // 根据实际情况调整等待时间
        int maxWaitSeconds = 30;
        boolean gotReply = false;
        
        for (int i = 0; i < maxWaitSeconds; i++) {
            TimeUnit.SECONDS.sleep(1);
            
            // 查询是否有小c机器人的回复
            List<MtComments> comments = momentTimeService.lambdaQuery()
                    .eq(MtComments::getWallId, testWallId)
                    .eq(MtComments::getName, "小c机器人")
                    .list();
            
            if (!comments.isEmpty()) {
                gotReply = true;
                MtComments robotComment = comments.get(0);
                log.info("✓ 收到小c机器人回复 (等待{}秒)", i + 1);
                log.info("回复内容: {}", robotComment.getComment());
                
                // 验证回复内容
                assertNotNull(robotComment.getComment(), "机器人回复内容不应为空");
                assertTrue(robotComment.getComment().length() > 0, "机器人回复应该有内容");
                assertEquals(testWallId, robotComment.getWallId(), "评论应该关联到正确的留言墙");
                
                // 如果 AI 服务可用，验证回复内容不是错误消息
                if (aiChatService != null) {
                    assertFalse(robotComment.getComment().contains("暂未启用"), 
                            "AI服务已配置，不应返回'暂未启用'的消息");
                    assertFalse(robotComment.getComment().contains("无法回复"), 
                            "AI服务已配置，不应返回'无法回复'的消息");
                    log.info("✓ Spring AI 生成的智能回复验证通过");
                } else {
                    assertTrue(robotComment.getComment().contains("暂未启用") || 
                              robotComment.getComment().contains("无法回复"),
                            "AI服务未配置，应返回降级消息");
                    log.info("✓ 降级处理验证通过");
                }
                
                break;
            }
            
            if (i < maxWaitSeconds - 1) {
                log.info("等待机器人回复... ({}秒)", i + 1);
            }
        }
        
        assertTrue(gotReply, "应该在" + maxWaitSeconds + "秒内收到机器人回复");
    }

    @Test
    @DisplayName("测试3: Spring AI 不同类型问题的回复测试")
    public void testSaveWall_DifferentQuestions() throws InterruptedException {
        log.info(">>> 测试3: Spring AI 处理不同类型问题");
        
        if (aiChatService == null) {
            log.warn("⚠ Spring AI 服务未配置，跳过此测试");
            return;
        }

        String[] testMessages = {
                "你好，Spring AI 能介绍一下自己吗？",
                "如何优化Java应用的性能？",
                "什么是Spring Boot？"
        };

        for (String message : testMessages) {
            log.info("--- 测试问题: {} ---", message);
            
            // 创建新的留言
            MtWalls wall = new MtWalls();
            wall.setType(0);
            wall.setName("测试用户_" + RandomUtil.randomString(4));
            wall.setUserId("test_" + System.currentTimeMillis());
            wall.setMessage(message);
            wall.setMoment(LocalDateTime.now());
            wall.setLabel(1);
            wall.setColor(RandomUtil.randomInt(1, 10));

            // 保存并获取回复
            MtWalls saved = momentTimeService.saveWall(wall);
            Long wallId = saved.getId();
            
            // 等待回复
            boolean gotReply = false;
            for (int i = 0; i < 30; i++) {
                TimeUnit.SECONDS.sleep(1);
                
                List<MtComments> comments = momentTimeService.lambdaQuery()
                        .eq(MtComments::getWallId, wallId)
                        .eq(MtComments::getName, "小c机器人")
                        .list();
                
                if (!comments.isEmpty()) {
                    log.info("✓ 回复: {}", comments.get(0).getComment());
                    gotReply = true;
                    break;
                }
            }
            
            assertTrue(gotReply, "问题'" + message + "'应该收到回复");
            
            // 清理测试数据
            mtWallsService.removeById(wallId);
            momentTimeService.lambdaUpdate()
                    .eq(MtComments::getWallId, wallId)
                    .remove();
            
            // 稍作延迟，避免请求过快
            TimeUnit.SECONDS.sleep(2);
        }
        
        log.info("✓ 所有问题类型测试完成");
    }

    @Test
    @DisplayName("测试4: 保存图片类型的留言墙")
    public void testSaveWall_ImageType() {
        log.info(">>> 测试4: 保存图片类型的留言墙");
        
        testWall.setType(1); // 图片类型
        testWall.setMessage("这是一张美丽的图片");
        testWall.setImgUrl("https://example.com/test-image.jpg");
        
        MtWalls savedWall = momentTimeService.saveWall(testWall);
        testWallId = savedWall.getId();
        
        assertNotNull(savedWall);
        assertEquals(1, savedWall.getType());
        assertNotNull(savedWall.getImgUrl());
        
        log.info("✓ 图片类型留言墙保存成功");
    }

    @Test
    @DisplayName("测试5: 更新已存在的留言墙")
    public void testSaveWall_UpdateExisting() {
        log.info(">>> 测试5: 更新已存在的留言墙");
        
        // 先保存
        MtWalls savedWall = momentTimeService.saveWall(testWall);
        testWallId = savedWall.getId();
        
        log.info("初始保存完成，ID: {}", testWallId);
        
        // 更新内容
        savedWall.setMessage("更新后的留言内容 - Spring AI 测试");
        MtWalls updatedWall = momentTimeService.saveWall(savedWall);
        
        // 验证更新
        assertEquals(testWallId, updatedWall.getId(), "ID应该保持不变");
        assertEquals("更新后的留言内容 - Spring AI 测试", updatedWall.getMessage(), "内容应该更新");
        
        // 从数据库验证
        MtWalls dbWall = mtWallsService.getById(testWallId);
        assertEquals("更新后的留言内容 - Spring AI 测试", dbWall.getMessage());
        
        log.info("✓ 留言墙更新成功");
    }

    @Test
    @DisplayName("测试6: Spring AI 服务状态检查")
    public void testAiServiceStatus() {
        log.info(">>> 测试6: Spring AI 服务状态检查");
        
        if (aiChatService != null) {
            log.info("✓ Spring AI 服务已配置并可用");
            log.info("服务类型: {}", aiChatService.getClass().getName());
            
            // 尝试简单的AI调用测试
            try {
                String response = aiChatService.wallMessageBot("你好");
                assertNotNull(response, "AI响应不应为空");
                log.info("✓ AI服务测试调用成功");
                log.info("测试响应: {}", response);
            } catch (Exception e) {
                log.error("✗ AI服务调用失败: {}", e.getMessage());
                fail("AI服务配置存在问题: " + e.getMessage());
            }
        } else {
            log.warn("⚠ Spring AI 服务未配置");
            log.info("提示: 如需启用AI功能，请配置相关的AI服务Bean");
        }
    }

    @Test
    @DisplayName("测试7: 验证异步执行不阻塞主流程")
    public void testSaveWall_AsyncNonBlocking() {
        log.info(">>> 测试7: 验证异步执行不阻塞主流程");
        
        long startTime = System.currentTimeMillis();
        
        MtWalls savedWall = momentTimeService.saveWall(testWall);
        testWallId = savedWall.getId();
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // saveWall 应该很快返回（不等待AI回复）
        assertTrue(duration < 5000, "saveWall应该在5秒内返回，实际耗时: " + duration + "ms");
        
        log.info("✓ 方法执行时间: {}ms (异步执行验证通过)", duration);
        log.info("✓ 主流程未被AI调用阻塞");
    }
}

