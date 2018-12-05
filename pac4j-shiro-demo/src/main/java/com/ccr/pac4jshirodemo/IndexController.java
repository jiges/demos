package com.ccr.pac4jshirodemo;

import io.buji.pac4j.subject.Pac4jPrincipal;
import org.apache.shiro.SecurityUtils;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ccr12312@163.com at 2018-12-5
 */
@Controller
public class IndexController {

    @RequestMapping("/protected/index")
    public String index(Model model){
        //获取当前用户的认证信息
        final Pac4jPrincipal principal = SecurityUtils.getSubject().getPrincipals().oneByType(Pac4jPrincipal.class);
        CommonProfile profile = principal.getProfile();
        if(profile != null) {
            model.addAttribute("username",profile.getUsername());
        }
        return "index";
    }


}
