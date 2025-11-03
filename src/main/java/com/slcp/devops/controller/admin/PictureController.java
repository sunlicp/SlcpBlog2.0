package com.slcp.devops.controller.admin;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.slcp.devops.api.Result;
import com.slcp.devops.entity.Picture;
import com.slcp.devops.entity.Search;
import com.slcp.devops.entity.SqlWhereWrapper;
import com.slcp.devops.service.IPictureService;
import com.slcp.devops.utils.ImgUtil;
import com.slcp.devops.utils.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author: Slcp
 * @date: 2020/9/23 19:57
 * @code: 一生的挚爱
 * @description: 图片管理控制器
 */
@RestController
@RequestMapping("/sys")
@AllArgsConstructor
@Tag(name = "图片管理接口", description = "图片管理接口")
@Slf4j
public class PictureController {

    private final IPictureService pictureService;

    @GetMapping("/picture/{id}")
    @Operation(summary = "getPictureById", description = "根据主键获取信息")
    public Result<Picture> getPictureById(@PathVariable("id") Long id) {
        return Result.data(pictureService.getById(id));
    }

    @DeleteMapping("/picture/{id}")
    @Operation(summary = "deletePicture", description = "删除友链")
    public Result<Picture> deletePicture(@PathVariable("id") Long id) {
        StringUtil.delPicture(pictureService.getById(id).getPictureAddress());
        return Result.condition(pictureService.removeById(id));
    }

    @PostMapping("/picture")
    @Operation(summary = "addPicture", description = "添加&修改友链")
    public Result<Picture> addPicture(@RequestBody Picture picture) {
        return Result.condition(pictureService.saveOrUpdate(picture));
    }

    @GetMapping("/picture")
    @Operation(summary = "pictureList", description = "获取友链")
    public Result<IPage<Picture>> pictureList(@RequestParam Map<String, Object> queryParam, Search search) {
        IPage<Picture> listInfoByPage = SqlWhereWrapper.getPage(search);
        String pictureName = (String) queryParam.get("query");
        return Result.data(StringUtil.isBlank(pictureName) ? pictureService.page(listInfoByPage) : pictureService.page(listInfoByPage, new QueryWrapper<Picture>().lambda().like(Picture::getPictureName, pictureName)));
    }

    /**
     * 七牛云上传方式
     * 3.后端代码调用
     *
     * @param file 上传对象
     * @return 数据
     */
    @RequestMapping("/pictures/upload")
    public Result<Picture> upload(MultipartFile file) {
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
