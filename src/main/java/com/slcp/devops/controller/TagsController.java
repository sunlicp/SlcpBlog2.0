package com.slcp.devops.controller;

import com.slcp.devops.dto.FirstPageDTO;
import com.slcp.devops.dto.TagDTO;
import com.slcp.devops.service.IBlogService;
import com.slcp.devops.service.ITagService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author: Slcp
 * @date: 2020/9/24 13:17
 * @code: 一生的挚爱
 * @description:
 */
@Controller
@AllArgsConstructor
@Tag(name = "标签接口查询", description = "标签接口查询")
@Slf4j
public class TagsController {

    private IBlogService blogService;
    private final ITagService tagService;

    @GetMapping("/tags/{tid}")
    public String listType(@PathVariable Long tid, Model model) {
        List<TagDTO> tags = tagService.getAllTag();
        if (tid > 0) {
            List<FirstPageDTO> blogs = blogService.getSearchBlogsByTagName(tid);
            model.addAttribute("pageInfo", blogs);
            model.addAttribute("pageInfoSize", blogs.size());
        } else {
            model.addAttribute("pageInfo", null);
            model.addAttribute("pageInfoSize", 0);
        }
        model.addAttribute("tags", tags);
        return "tags";
    }
}
