package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @GetMapping("/view")
    public String viewPage() {
        return "view"; // resources/templates/role.html を表示（Thymeleafなど）
    }

    @GetMapping("/test")
    public String testPage(){
        return "test";
    }

    @GetMapping("role")
    public String rolePage(){
        return "role";
    }
}
