package com.example.Blog_application.utils;

public class AppConstants {
    public final static String PAGE_NUMBER = "0";
    public final static String PAGE_SIZE = "10";
    public final static String SORT_BY = "id";
    public final static String SORT_DIR = "asc";
    public final static long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    public final static int ROLE_ADMIN = 501;
    public final static int ROLE_NORMAL = 502;
    public static final String[] PUBLIC_STATIC_URLS = {
            "/api/v1/auth/**",
            "/v3/api-docs",
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**"
    };
}
