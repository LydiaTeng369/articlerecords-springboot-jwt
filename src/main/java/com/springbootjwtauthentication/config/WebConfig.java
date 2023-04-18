package com.springbootjwtauthentication.config;

import com.springbootjwtauthentication.jwt.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements  WebMvcConfigurer {

    @Autowired
    JwtInterceptor jwtInterceptor;

/*
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // Specify the unified interface prefix of the controller
        configurer.addPathPrefix("/api", clazz -> clazz.isAnnotationPresent(RestController.class));
    }
*/


    // Add custom interceptor JwtInterceptor, set interception rules
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**")
                .excludePathPatterns("/api/user/fromLogin",
                        "/api/user/fromRegister",
                        "/api/user/login",
                        "/api/user/register");
    }


}

