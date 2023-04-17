package com.fastroof.ftpr.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastroof.ftpr.security.UserDetailsImpl;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * The Auth Service Class.
 */
@Service
public class Auth {
    
    /** The link to auth service. */
    @Value("${link.to.auth.service}")
    private String linkToAuthService;
    
    /** The objectMapper Constant. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    /** The OkHttpClient Constant. */
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    /** The JSON MediaType Constant. */
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    /**
     * Validate token using external Auth Service.
     *
     * @param token the JWT token
     * @return the user details impl
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public UserDetailsImpl validateToken(String token) throws IOException {
        RequestBody body = RequestBody.create(OBJECT_MAPPER.writeValueAsString(new ValidateTokenRequest(token)), JSON);
        Request request = new Request.Builder()
                .url(this.linkToAuthService + "/api/auth/validate")
                .method("POST", body)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.code() == 200) {
                return OBJECT_MAPPER.readValue(response.body().string(), UserDetailsImpl.class);
            } else {
                return null;
            }
        }
    }
}