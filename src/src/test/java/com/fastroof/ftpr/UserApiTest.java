package com.fastroof.ftpr;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.PrepareTestInstance;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-before-sql.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserApiTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getPersonalLibrary() throws Exception{
        this.mvc.perform(get("/user/personal-library"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mvc.perform(get("/user/personal-library").header(HEADER,PREFIX + returnToken()))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void deleteBookFromPersonalLibrary() throws Exception{
        this.mvc.perform(delete("/user/personal-library/11"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mvc.perform(delete("/user/personal-library/11").header(HEADER,PREFIX + returnToken()))
                .andDo(print())
                .andExpect(status().isOk());

        this.mvc.perform(delete("/user/personal-library/11").header(HEADER,PREFIX + returnToken()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Value("${link.to.auth.service}")
    private String linkToAuthService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final OkHttpClient client = new OkHttpClient().newBuilder().build();
    private static final String TEST_ACCOUNT_EMAIL =  "sasha@sasha.com";
    private static final String TEST_PASSWORD = "sashasasha";

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    private String returnToken() throws Exception{
        RequestBody body = RequestBody
                .create(
                        objectMapper.writeValueAsString(
                                new LoginRequest(TEST_ACCOUNT_EMAIL, TEST_PASSWORD)), JSON);

        Request request = new Request.Builder()
                .url(this.linkToAuthService + "/api/auth/login")
                .method("POST", body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response);
            if (response.code() == 200) {
                return objectMapper.readValue(response.body().string(), JwtResponse.class).getToken();
            } else {
                return null;
            }
        }


    }

}
