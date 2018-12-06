package com.ccr.pac4jdemo;

import lombok.extern.slf4j.Slf4j;
import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.stereotype.Component;

/**
 * 登录成功后，在保存UserProfile前会调用该接口，在profile中会保存一些信息，profile是session的抽象
 * 可以在这里将角色权限信息写入 profile
 */
@Component
@Slf4j
public class RoleAdminAuthGenerator implements AuthorizationGenerator<CommonProfile> {

    @Override
    public CommonProfile generate(final WebContext context, final CommonProfile profile) {
        log.info("do something before saving profile....");
        profile.addRole("ROLE_ADMIN");
        profile.addPermission("/protected/index");
        profile.clearSensitiveData(); // remove the access token to reduce size and make the remember-me work
        profile.setRemembered(true);
        profile.addAttribute("hello","hello");
        return profile;
    }
}
