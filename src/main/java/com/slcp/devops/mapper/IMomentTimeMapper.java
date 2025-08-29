package com.slcp.devops.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.slcp.devops.dto.MtWallsDTO;
import com.slcp.devops.entity.MtComments;
import com.slcp.devops.entity.MtWalls;
import org.springframework.stereotype.Repository;


/**
 * @author: Slcp
 * @date: 2020/9/23 20:01
 * @code: 一生的挚爱
 * @description:
 */
@Repository
public interface IMomentTimeMapper extends BaseMapper<MtComments> {

    /**
     * 查询墙
     * @param mtWallsIPage 分页
     * @param mtWalls 参数
     * @return 返回
     */
    IPage<MtWallsDTO> findWallPage(IPage<MtWallsDTO> mtWallsIPage, MtWalls mtWalls);
}
