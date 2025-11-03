package com.slcp.devops.controller.admin;

import com.slcp.devops.api.Result;
import com.slcp.devops.entity.SysRights;
import com.slcp.devops.service.IRightsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Slcp
 * @description: 权限
 * @create: 2022-07-06 14:05:52
 **/
@RestController
@RequestMapping(value = "/sys")
@AllArgsConstructor
@Tag(name = "权限接口", description = "权限接口")
public class RightsController {

    private final IRightsService rightsService;

    @GetMapping("/rights/list")
    @Operation(summary = "rights", description = "获取权限列表")
    public Result<List<SysRights>> rights() {
        return Result.data(rightsService.list());
    }

}
