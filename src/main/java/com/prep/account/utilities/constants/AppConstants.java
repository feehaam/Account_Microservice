package com.prep.account.utilities.constants;

public class AppConstants {
    public static final String TOKEN_SECRET = "PAKHI_PAKA_PEPE_KHAY";
    // Setting for 30 days.
    public static final long TOKEN_EXPIRATION_TIME = 1000L * 60L * 60L * 24L * 30L;
    // Setting for 10 years.
    public static final long INTERNAL_TOKEN_EXPIRATION_TIME = 1000L * 60L * 60L * 24L * 365L * 10L;
    public static final String TOKEN_HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final Integer MAX_LOGIN_ATTEMPTS_LIMIT = 3;
    public static final String TOKEN_ALPHABETS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static final String EMAIL_API_BASE_URL_1 = "https://integration3666.azurewebsites.net/v1/email/send";
    public static final String EMAIL_API_BASE_URL_2 = "https://integration3666.azurewebsites.net/v2/email/send";

    // APIs
    public static final String SIGN_IN = "/access/login";
    public static final String SIGN_UP = "/account/create-account";
}
