package com.slcp.devops.controller.admin;

import com.slcp.devops.api.Result;
import com.slcp.devops.entity.SysRole;
import com.slcp.devops.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Slcp
 * @description: 角色
 * @create: 2022-07-06 13:33:35
 **/
@RestController
@RequestMapping(value = "/sys")
@AllArgsConstructor
@Tag(name = "角色接口", description = "角色接口")
public class RoleController {

    private final IRoleService roleService;

    @GetMapping("/roles")
    @Operation(summary = "roleList", description = "获取所有角色")
    public Result<List<SysRole>> roleList() {
        return Result.data(roleService.list());
    }

    @DeleteMapping("/roles/{id}")
    @Operation(summary = "deleteRole", description = "根据角色id删除角色")
    public Result<List<SysRole>> deleteRole(@PathVariable("id") Long id) {
        return Result.condition(roleService.removeById(id));
    }

    @GetMapping("roles/{id}")
    @Operation(summary = "getRoleById", description = "根据角色id获取角色信息")
    public Result<SysRole> getRoleById(@PathVariable("id") Long id) {
        return Result.data(roleService.getById(id));
    }

    @PostMapping("/roles")
    @Operation(summary = "addRole", description = "添加&修改角色")
    public Result<List<SysRole>> addRole(@RequestBody SysRole role) {
        return Result.condition(roleService.saveOrUpdate(role));
    }

    @GetMapping("/roles1/{id}/{rid}")
    @Operation(summary = "updateRights", description = "根据角色id修改角色权限")
    public Result<List<SysRole>> updateRights(@PathVariable("id") Long id, @PathVariable("rid") Long rid) {
        roleService.lambdaUpdate().eq(SysRole::getId, id).set(SysRole::getRightsId, rid).update();
        return Result.data(roleService.list());
    }

}
