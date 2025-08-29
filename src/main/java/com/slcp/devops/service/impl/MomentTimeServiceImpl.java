package com.slcp.devops.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.util.Proxys;
import com.slcp.devops.config.DoQueryCache;
import com.slcp.devops.config.GptEventSourceListener;
import com.slcp.devops.dto.MtWallsDTO;
import com.slcp.devops.entity.Count;
import com.slcp.devops.entity.MtComments;
import com.slcp.devops.entity.MtFeedbacks;
import com.slcp.devops.entity.MtWalls;
import com.slcp.devops.mapper.IMomentTimeMapper;
import com.slcp.devops.service.IMomentTimeService;
import com.slcp.devops.service.IMtFeedbacksService;
import com.slcp.devops.service.IMtWallsService;
import com.slcp.devops.utils.ImgUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.net.Proxy;
import java.util.Arrays;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author: Slcp
 * @date: 2020/9/23 20:01
 * @code: 一生的挚爱
 * @description:
 */
@Service
public class MomentTimeServiceImpl extends ServiceImpl<IMomentTimeMapper, MtComments> implements IMomentTimeService {

    @Resource
    private IMtFeedbacksService mtFeedbacksService;
    @Resource
    private IMtWallsService mtWallsService;

    @Value("${openai.token}")
    private List<String> OPENAPI_TOKEN;

    private static final ExecutorService simpleExecutorService = new ThreadPoolExecutor(
            8,
            16,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(32),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    @DoQueryCache(expireTime = 5)
    public IPage<MtWallsDTO> findWallPage(IPage<MtWallsDTO> mtWallsIPage, MtWalls mtWalls) {
        IPage<MtWallsDTO> wallPage = this.baseMapper.findWallPage(mtWallsIPage, mtWalls);
        wallPage.getRecords().forEach(mtWallsDTO -> {
            QueryWrapper<MtFeedbacks> mtFeedbacksQueryWrapper = new QueryWrapper<>();
            mtFeedbacksQueryWrapper.lambda().eq(MtFeedbacks::getType, 0).eq(MtFeedbacks::getWallId, mtWallsDTO.getId());
            List<MtFeedbacks> feedbacksList = mtFeedbacksService.list(mtFeedbacksQueryWrapper);

            //获取喜欢数
            List<Count> list = ListUtil.list(false);
            list.add(new Count(feedbacksList.size()));
            mtWallsDTO.setLike(list);
            //是否喜欢
            list = ListUtil.list(false);
            for (MtFeedbacks mtFeedbacks : feedbacksList) {
                if (mtFeedbacks.getUserId().equals(mtWallsDTO.getUserId())) {
                    list.add(new Count(1));
                }
            }
            if (CollUtil.isEmpty(list)) {
                list.add(new Count(0));
            }
            mtWallsDTO.setIsLike(list);
            //评论数
            list = ListUtil.list(false);
            QueryWrapper<MtComments> commentsQueryWrapper = new QueryWrapper<>();
            commentsQueryWrapper.lambda().eq(MtComments::getWallId, mtWallsDTO.getId());
            list.add(new Count(this.list(commentsQueryWrapper).size()));
            mtWallsDTO.setComcount(list);
            //报销
            list = ListUtil.list(false);
            list.add(new Count(0));
            mtWallsDTO.setReport(list);
            mtWallsDTO.setRevoke(list);
        });
        return wallPage;
    }

    @Override
    public MtWalls saveWall(MtWalls mtWalls) {
        mtWallsService.saveOrUpdate(mtWalls);
        //小c机器人
        simpleExecutorService.execute(() -> {
            MtComments mtComments = new MtComments();
            mtComments.setWallId(mtWalls.getId());
            mtComments.setName("小c机器人");
            mtComments.setUserId(ImgUtil.getIp().getString("query"));
            mtComments.setMoment(LocalDateTime.now());
            mtComments.setImgUrl(String.valueOf(RandomUtil.randomInt(0, 8)));
            try {
                //国内需要代理 国外不需要
                // 2、设置代理
                Proxy proxy = Proxys.http("127.0.0.1", 7890);

                // 3、借助SDK工具，实例化ChatGPTStream工具类对象
                ChatGPTStream chatgptStream = ChatGPTStream.builder()
                        .timeout(50)
                        .apiKeyList(OPENAPI_TOKEN)
                        .proxy(proxy)
                        .apiHost("https://api.openai.com/")
                        .build()
                        .init();

                // 4、实例化流式输出类，设置监听，从而在所有消息输出完成后回调
                SseEmitter sseEmitter = new SseEmitter(-1L);
                GptEventSourceListener listener = new GptEventSourceListener(sseEmitter);

                // 5、加入历史消息记录，提供上下文信息
                // Message为SDK包中的数据结构
                // 6、加入本次提问问题
                Message system = Message.ofSystem("我搭建了个人博客网站，该网站全称是“SLCPの童话镇”，假设你现在是该网站的cc,专门回复访客的留言,记住要充满活力的回复哦。");
                Message message = Message.of(mtWalls.getMessage());

                // 7、设置完成时的回调函数
                listener.setOnComplete(msg -> {
                    // 保存历史信息
                    mtComments.setComment(msg);
                    this.save(mtComments);
                });
                // 8、提问
                chatgptStream.streamChatCompletion(Arrays.asList(system,message), listener);
            } catch (Exception e) {
                log.error("小c机器人:{}", e);
                // 保存历史信息
                mtComments.setComment("非常抱歉，小c无法连接到服务😢");
                this.save(mtComments);
            }
        });
        return mtWalls;
    }


}
