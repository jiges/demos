package com.ccr.pac4jshirodemo;

import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.util.CommonHelper;

/**
 * 自定义认证
 * 可以在这里实现自己的认证逻辑，比如数据库等等
 * @author ccr12312@163.com at 2018-12-5
 */
public class MyAuthenticator implements Authenticator<UsernamePasswordCredentials> {
    @Override
    public void validate(UsernamePasswordCredentials credentials, WebContext context) {
        if (credentials == null) {
            throw new CredentialsException("No credential");
        }
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        if (CommonHelper.isBlank(username)) {
            throw new CredentialsException("Username cannot be blank");
        }
        if (CommonHelper.isBlank(password)) {
            throw new CredentialsException("Password cannot be blank");
        }
        if(CommonHelper.areNotEquals(username,"admin")) {
            throw new CredentialsException("cannot find user");
        }
        if (CommonHelper.areNotEquals(findPassword(username), password)) {
            throw new CredentialsException("Username : '" + username + "' does not match password");
        }
        final CommonProfile profile = new CommonProfile();
        profile.setId(username);
        profile.addAttribute(Pac4jConstants.USERNAME, username);
        credentials.setUserProfile(profile);
    }

    private String findPassword(String username){
        return "admin";
    }
}
