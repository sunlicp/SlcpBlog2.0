package com.slcp.devops.controller.admin;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.slcp.devops.api.Result;
import com.slcp.devops.entity.Search;
import com.slcp.devops.entity.SqlWhereWrapper;
import com.slcp.devops.entity.Type;
import com.slcp.devops.service.ITypeService;
import com.slcp.devops.utils.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author: Slcp
 * @date: 2020/9/23 8:51
 * @code: 一生的挚爱
 * @description: 分类控制器
 */

@RestController
@RequestMapping("/sys")
@AllArgsConstructor
@Tag(name = "类型接口查询", description = "类型接口查询")
public class TypeController {

    private final ITypeService typeService;

    @GetMapping("/type/{id}")
    @Operation(summary = "getTypeById", description = "根据主键获取信息")
    public Result<Type> getTypeById(@PathVariable("id") Long id) {
        return Result.data(typeService.getById(id));
    }

    @DeleteMapping("/type/{id}")
    @Operation(summary = "deleteType", description = "删除分类")
    public Result<Type> deleteType(@PathVariable("id") Long id) {
        return Result.condition(typeService.removeById(id));
    }

    @PostMapping("/type")
    @Operation(summary = "addType", description = "添加&修改分类")
    public Result<Type> addType(@RequestBody Type type) {
        if (ObjectUtil.isEmpty(type.getId()) && ObjectUtil.isNotEmpty(typeService.lambdaQuery().eq(Type::getName, type.getName()).one())) {
            return Result.fail("分类名已存在哦~");
        }
        return Result.condition(typeService.saveOrUpdate(type));
    }

    @GetMapping("/types")
    @Operation(summary = "types", description = "获取所有分类")
    public Result<List<Type>> types() {
        return Result.data(typeService.list());
    }

    @GetMapping("/type")
    @Operation(summary = "typeList", description = "获取分类列表")
    public Result<IPage<Type>> typeList(@RequestParam Map<String, Object> queryParam, Search search) {
        IPage<Type> listInfoByPage = SqlWhereWrapper.getPage(search);
        String name = (String) queryParam.get("query");
        return Result.data(StringUtil.isBlank(name) ? typeService.page(listInfoByPage) : typeService.page(listInfoByPage, new QueryWrapper<Type>().lambda().like(Type::getName, name)));
    }

}
