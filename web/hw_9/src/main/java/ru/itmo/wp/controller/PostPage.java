package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping("/post/{id:[0-9]{1,10}}")
    public String postGet(Model model, @PathVariable long id) {
        Post post = postService.findById(id);
        if (post != null) {
            model.addAttribute("post", post);
            model.addAttribute("comment", new Comment());
        } else {
            model.addAttribute("postError", "No such post");
        }

        return "PostPage";
    }

    @Guest
    @PostMapping(path = "/post/{id:[0-9]{1,10}}")
    public String postPost(@Valid @ModelAttribute("comment") Comment comment,
                                    BindingResult bindingResult, HttpSession httpSession, Model model, @PathVariable long id) {
        Post post = postService.findById(id);
        if (post == null) {
            model.addAttribute("postError", "No such post");
            return "PostPage";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "PostPage";
        }

        postService.writeComment(comment, post, getUser(httpSession));
        putMessage(httpSession, "You published new comment");
        return "redirect:/post/" + id;
    }
}
