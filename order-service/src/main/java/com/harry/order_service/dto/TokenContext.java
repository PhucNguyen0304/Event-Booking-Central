package com.harry.order_service.dto;

public class TokenContext {
    private static final ThreadLocal<String> TOKENS = new ThreadLocal<>();

    public static void setToken(String token) {
        TOKENS.set(token);
    }

    public static String getToken() {
        return TOKENS.get();
    }

    public static void clear() {
        TOKENS.remove();
    }
}
