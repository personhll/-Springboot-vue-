package com.huanglulu.wiki.config;

//@Configuration
//public class SpringMvcConfig implements WebMvcConfigurer {
//
//    @Resource
//    LogInterceptor logInterceptor;
//    @Resource
//    LoginInterceptor loginInterceptor;
//
//    @Resource
//    ActionInterceptor actionInterceptor;

//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns(
//                        "/test/**",
//                        "/redis/**",
//                        "/user/login",
//                        "/category/all",
//                        "/ebook/list",
//                        "/doc/all/**",
//                        "/doc/vote/**",
//                        "/doc/find-content/**",
//                        "/ebook-snapshot/**"
//                );
//
//        registry.addInterceptor(actionInterceptor)
//                .addPathPatterns(
//                        "/*/save",
//                        "/*/delete/**",
//                        "/*/reset-password");

//        registry.addInterceptor(logInterceptor)
//                .addPathPatterns("/**").excludePathPatterns("login");
//    }
//}
