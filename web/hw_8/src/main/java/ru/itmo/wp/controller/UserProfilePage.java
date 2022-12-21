package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.UserService;

@Controller
public class UserProfilePage extends Page {

    private final UserService userService;

    public UserProfilePage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/user/{id}"})
    public String getUserProfile(@PathVariable("id") String id, Model model) {
        Long userId;
        try {
            userId = Long.parseLong(id);
        } catch (NumberFormatException nfe) {
            model.addAttribute("error", "No such user");
            return "UserProfilePage";
        }

        User user = userService.findById(userId);
        if (user == null) {
            model.addAttribute("error", "No such user");
        } else {
            model.addAttribute("userProfile", user);
        }
        return "UserProfilePage";
    }
}
