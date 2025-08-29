package com.slcp.devops.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.slcp.devops.api.Result;
import com.slcp.devops.config.DoQueryCache;
import com.slcp.devops.constant.DevOpsConstant;
import com.slcp.devops.dto.MtWallsDTO;
import com.slcp.devops.entity.*;
import com.slcp.devops.service.IMomentTimeService;
import com.slcp.devops.service.IMtFeedbacksService;
import com.slcp.devops.service.IMtWallsService;
import com.slcp.devops.utils.ImgUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * @author sunlicp
 * @since 2023/03/04 16:05
 */
@RestController
@AllArgsConstructor
@RequestMapping("momentTime")
@Slf4j
@CrossOrigin
@Api(value = "一刻时光", tags = "一刻时光")
public class MomentTimeController {
    private final IMomentTimeService momentTimeService;
    private final IMtWallsService mtWallsService;
    private final IMtFeedbacksService feedbacksService;

    /**
     * 获取IP
     * @return 返回
     */
    @PostMapping("/signip")
    @ApiOperation(httpMethod = DevOpsConstant.METHOD_POST, value = "signip", notes = "获取IP")
    public Result<JSONObject> signIp() {
        return Result.data(ImgUtil.getIp());
    }

    /**
     * 查询墙
     * @param feedbacks 参数
     * @return 返回
     */
    @PostMapping("/insertfeedback")
    @ApiOperation(httpMethod = DevOpsConstant.METHOD_POST, value = "insertfeedback", notes = "查询墙")
    public Result<MtFeedbacks> saveFeedBack(@RequestBody MtFeedbacks feedbacks) {
        feedbacksService.saveOrUpdate(feedbacks);
        return Result.data(feedbacks);
    }

    /**
     * 新建数据
     * @param mtWalls 实体
     * @return 返回
     */
    @PostMapping("/insertwall")
    @ApiOperation(httpMethod = DevOpsConstant.METHOD_POST, value = "insertwall", notes = "新建数据")
    public Result<MtWalls> saveWall(@RequestBody MtWalls mtWalls) {
        return Result.data(momentTimeService.saveWall(mtWalls));
    }

    /**
     * 查询墙
     * @param queryParam 参数
     * @param search 参数
     * @return 返回
     */
    @GetMapping("/findwallpage")
    @ApiOperation(httpMethod = DevOpsConstant.METHOD_GET, value = "getTypeById", notes = "根据主键获取信息")
    public Result<IPage<MtWallsDTO>> findWallPage(@RequestParam Map<String, Object> queryParam, Search search) {
        IPage<MtWallsDTO> mtWallsIPage = SqlWhereWrapper.getPage(search);
        MtWalls mtWalls = SqlWhereWrapper.convertParamsMapToObject(queryParam, MtWalls.class);
        return Result.data(momentTimeService.findWallPage(mtWallsIPage, mtWalls));
    }

    /**
     * 新建评论
     * @param mtComments 参数
     * @return 返回
     */
    @PostMapping("/insertcomment")
    @ApiOperation(httpMethod = DevOpsConstant.METHOD_POST, value = "insertcomment", notes = "新建评论")
    public Result<MtComments> saveComment(@RequestBody MtComments mtComments) {
        momentTimeService.saveOrUpdate(mtComments);
        return Result.data(mtComments);
    }

    /**
     * 获取评论
     *
     * @param id 参数
     * @return 返回
     */
    @GetMapping("/findcommentpage")
    @ApiOperation(httpMethod = DevOpsConstant.METHOD_GET, value = "findcommentpage", notes = "获取评论")
    @DoQueryCache(expireTime = 5)
    public Result<IPage<MtComments>> findComment(@RequestParam Serializable id, Search search) {
        IPage<MtComments> mtCommentsIPage = SqlWhereWrapper.getPage(search);
        momentTimeService.lambdaQuery().eq(MtComments::getWallId, id).page(mtCommentsIPage);
        if (CollUtil.isEmpty(mtCommentsIPage.getRecords())) {
            MtWalls mtWalls = mtWallsService.getById(id);
            if (ObjectUtil.isNotEmpty(mtWalls)) {
                momentTimeService.saveWall(mtWalls);
            }
        }
        return Result.data(mtCommentsIPage);
    }

    /**
     * 图片上传
     *
     * @param file 参数
     * @return 返回
     */
    @PostMapping("/profile")
    @ApiOperation(httpMethod = DevOpsConstant.METHOD_POST, value = "profile", notes = "图片上传")
    public Result<IPage<MtComments>> profile(@RequestBody MultipartFile file) {
        if (ObjectUtil.isEmpty(file)) {
            return Result.fail("上传文件为空~");
        }
        try {
            //使用工具类将文件上传到七牛云服务器 返回上传图片链接
            return Result.success(ImgUtil.upload(file));
        } catch (IOException e) {
            log.error("上传图片失败！" + e.getMessage());
        }
        return Result.fail("上传图片失败!");
    }
}
