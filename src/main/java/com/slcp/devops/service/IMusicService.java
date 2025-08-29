package com.slcp.devops.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.slcp.devops.entity.Music;

/**
 * @author: Slcp
 * @description: 音乐Service
 * @create: 2022-06-28 09:00:11
 **/
public interface IMusicService extends IService<Music> {

    /**
     * 获取音乐
     * @return
     */
    Music one();

}
