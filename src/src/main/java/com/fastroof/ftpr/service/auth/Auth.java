package com.fastroof.ftpr.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastroof.ftpr.security.UserDetailsImpl;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Auth {
    @Value("${link.to.auth.service}")
    private String linkToAuthService;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final OkHttpClient client = new OkHttpClient().newBuilder().build();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public UserDetailsImpl validateToken(String token) throws IOException {
        RequestBody body = RequestBody.create(objectMapper.writeValueAsString(new ValidateTokenRequest(token)), JSON);
        Request request = new Request.Builder()
                .url(this.linkToAuthService + "/api/auth/validate")
                .method("POST", body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 200) {
                return objectMapper.readValue(response.body().string(), UserDetailsImpl.class);
            } else {
                return null;
            }
        }
    }
}