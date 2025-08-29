package com.slcp.devops.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.slcp.devops.dto.MtWallsDTO;
import com.slcp.devops.entity.MtComments;
import com.slcp.devops.entity.MtWalls;

/**
 * @author sunlicp
 * @since 2023/03/04 16:10
 */
public interface IMomentTimeService extends IService<MtComments> {


    /**
     * 查询墙
     * @param mtWallsIPage 分页
     * @param mtWalls 参数
     * @return 返回
     */
    IPage<MtWallsDTO> findWallPage(IPage<MtWallsDTO> mtWallsIPage, MtWalls mtWalls);

    /**
     * 保存墙
     * @param mtWalls
     * @return
     */
    MtWalls saveWall(MtWalls mtWalls);
}
