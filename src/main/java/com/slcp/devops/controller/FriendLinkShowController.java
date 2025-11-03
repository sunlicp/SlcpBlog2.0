package com.slcp.devops.controller;

import com.slcp.devops.entity.FriendLink;
import com.slcp.devops.service.IFriendLinkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author: Slcp
 * @date: 2020/9/24 15:54
 * @code: 一生的挚爱
 * @description:
 */
@Controller
@AllArgsConstructor
@Tag(name = "友链接口查询", description = "友链接口查询")
public class FriendLinkShowController {

    private final IFriendLinkService friendLinkService;

    @GetMapping("/friends")
    public String friendLinks(Model model){
        model.addAttribute("friendLinks", friendLinkService.getFriends());
        return "friends";
    }

    @PostMapping("/friend/add")
    public String friendLinks(FriendLink friendLink){
        friendLinkService.saveOrUpdateByFriend(friendLink);
        return "redirect:/friends";
    }
}
