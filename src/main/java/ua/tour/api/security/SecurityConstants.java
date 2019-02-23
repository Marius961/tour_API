package ua.tour.api.security;

public class SecurityConstants {

    static final String SECRET = "SecretKeyToGenJWTs";
    static final long EXPIRATION_TIME = 32_536_000;
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/auth/sign-up";
    public static final String SIGN_IN_URL = "/api/auth/sign-in";

}
