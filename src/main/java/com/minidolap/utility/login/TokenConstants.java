package com.minidolap.utility.login;

public interface TokenConstants {
    long EXPIRATION_TIME = 864_000_000; // 10 days
    String TOKEN_PREFIX = "Bearer";
    String HEADER_STRING = "Authorization";
}
