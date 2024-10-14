package com.neeraj.library_mmgt_system.config;

public class JwtConstant {

    // Secret key for signing the JWT (replace with a secure, unique value for production)
    public static final String SECRET_KEY = "wpembytrwcvnryxksdbqwjebruyGHyudqgwveytrtrCSnwifoesarjbwe";

    // JWT Header for Authorization
    public static final String JWT_HEADER = "Authorization";

    // Token prefix to identify the token type
    public static final String TOKEN_PREFIX = "Bearer ";

    // Token expiration time (in milliseconds); here, it’s set for 10 days
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
}

