package com.ccr.pac4jshirodemo;


import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.EventListener;

/**
 * @author ccr12312@163.com at 2018-12-4
 */
@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean getShiroFilter() {
        ShiroFilter shiroFilter = new ShiroFilter();
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(shiroFilter);
        registrationBean.setOrder(1);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean<EventListener> getEnvironmentLoaderListener() {
        ServletListenerRegistrationBean<EventListener> registrationBean
                = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new EnvironmentLoaderListener());
        return registrationBean;
    }

}
