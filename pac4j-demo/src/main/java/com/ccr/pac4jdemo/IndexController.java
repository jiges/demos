package com.ccr.pac4jdemo;

import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ProfileManager<CommonProfile> profileManager;

    @RequestMapping("/protected/index")
    public String protectedIndex(Model model){
        List<CommonProfile> profiles = profileManager.getAll(true);
        if(!CollectionUtils.isEmpty(profiles)) {
            model.addAttribute("username",profiles.get(0).getUsername());
        }
        return "index";
    }

    @RequestMapping("/protected/permission")
    public String permission(Model model){
        List<CommonProfile> profiles = profileManager.getAll(true);
        if(!CollectionUtils.isEmpty(profiles)) {
            model.addAttribute("username",profiles.get(0).getUsername());
        }
        return "nopermission";
    }

    @RequestMapping("/login")
    public String loginForm(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(Model model){
        List<CommonProfile> profiles = profileManager.getAll(true);
        if(!CollectionUtils.isEmpty(profiles)) {
            model.addAttribute("username",profiles.get(0).getUsername());
        }
        return "index";
    }

    @RequestMapping("/error403")
    public String error403(){
        return "error403";
    }


}


