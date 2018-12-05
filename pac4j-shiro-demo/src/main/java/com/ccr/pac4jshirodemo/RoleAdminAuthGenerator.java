package com.ccr.pac4jshirodemo;

import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.CommonProfile;

/**
 * 登录成功后，在保存UserProfile前会调用该接口，在profile中会保存一些信息，profile是session的抽象
 */
public class RoleAdminAuthGenerator implements AuthorizationGenerator<CommonProfile> {

    @Override
    public CommonProfile generate(final WebContext context, final CommonProfile profile) {
        profile.addRole("ROLE_ADMIN");
        profile.clearSensitiveData(); // remove the access token to reduce size and make the remember-me work
        profile.setRemembered(true);
        profile.addAttribute("some_message","hello");
        return profile;
    }
}
