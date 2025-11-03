package com.slcp.devops.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slcp.devops.config.DoQueryCache;
import com.slcp.devops.dto.MtWallsDTO;
import com.slcp.devops.entity.Count;
import com.slcp.devops.entity.MtComments;
import com.slcp.devops.entity.MtFeedbacks;
import com.slcp.devops.entity.MtWalls;
import com.slcp.devops.mapper.IMomentTimeMapper;
import com.slcp.devops.service.AiChatService;
import com.slcp.devops.service.IMomentTimeService;
import com.slcp.devops.service.IMtFeedbacksService;
import com.slcp.devops.service.IMtWallsService;
import com.slcp.devops.utils.ImgUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
    @Resource
    private AiChatService aiChatService;

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
        //小c机器人 - 使用新的 Spring AI 服务
        simpleExecutorService.execute(() -> {
            MtComments mtComments = new MtComments();
            mtComments.setWallId(mtWalls.getId());
            mtComments.setName("小c机器人");
            mtComments.setUserId(ImgUtil.getIp().getString("query"));
            mtComments.setMoment(LocalDateTime.now());
            mtComments.setImgUrl(String.valueOf(RandomUtil.randomInt(0, 8)));
            try {
                // 使用新的 AI 服务获取回复
                if (aiChatService != null) {
                    String response = aiChatService.wallMessageBot(mtWalls.getMessage());
                    mtComments.setComment(response);
                } else {
                    mtComments.setComment("AI服务暂未启用，小c暂时无法回复😢");
                }
                this.save(mtComments);
            } catch (Exception e) {
                log.error("小c机器人异常:{}", e);
                // 保存错误提示
                mtComments.setComment("非常抱歉，小c暂时无法回复😢");
                this.save(mtComments);
            }
        });
        return mtWalls;
    }


}
