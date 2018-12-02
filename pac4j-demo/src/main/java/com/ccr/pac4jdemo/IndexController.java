package com.ccr.pac4jdemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/form/index")
    public String index(Model model){
        model.addAttribute("content", "this is index page");
        return "index";
    }

    @RequestMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @RequestMapping("/")
    public String index(){
        return "forward:/index";
    }
}


