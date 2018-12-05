package com.ccr.pac4jshirodemo;

import org.pac4j.core.authorization.authorizer.ProfileAuthorizer;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Authorizer 判断用户是否具有访问资源的权限
 */
public class CustomAuthorizer extends ProfileAuthorizer<CommonProfile> {

    @Override
    public boolean isAuthorized(final WebContext context, final List<CommonProfile> profiles) throws HttpAction {
        return isAnyAuthorized(context, profiles);
    }

    @Override
    public boolean isProfileAuthorized(final WebContext context, final CommonProfile profile) {
        if (profile == null) {
            return false;
        }
        return StringUtils.startsWithIgnoreCase(profile.getUsername(), "jle");
    }
}