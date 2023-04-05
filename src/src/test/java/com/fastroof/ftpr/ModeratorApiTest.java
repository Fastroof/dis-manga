package com.fastroof.ftpr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastroof.ftpr.repository.HelpRequestRepository;
import com.fastroof.ftpr.repository.ReportRepository;
import okhttp3.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-before-sql.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ModeratorApiTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private HelpRequestRepository helpRequestRepository;

    @Autowired
    private ReportRepository reportRepository;


    @Test
    public void getHelpRequests() throws Exception{
        this.mvc.perform(get("/moderator/help-requests"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mvc.perform(get("/moderator/help-requests").header(HEADER,PREFIX + returnModeratorToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(helpRequestRepository.findAll())));
    }

    @Test
    public void processHelpRequest() throws Exception{
        this.mvc.perform(post("/moderator/help-requests/2/process"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mvc.perform(post("/moderator/help-requests/2/process").header(HEADER,PREFIX + returnModeratorToken()))
                .andDo(print())
                .andExpect(status().isOk());
        this.mvc.perform(post("/moderator/help-requests/99/process").header(HEADER,PREFIX + returnModeratorToken()))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void getReports() throws Exception{
        this.mvc.perform(get("/moderator/reports"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mvc.perform(get("/moderator/reports").header(HEADER,PREFIX + returnModeratorToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(reportRepository.findAll())));
    }

    @Test
    public void processReport() throws Exception{
        this.mvc.perform(post("/moderator/reports/2/process"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mvc.perform(post("/moderator/reports/2/process").header(HEADER,PREFIX + returnModeratorToken()))
                .andDo(print())
                .andExpect(status().isOk());
        this.mvc.perform(post("/moderator/reports/99/process").header(HEADER,PREFIX + returnModeratorToken()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }








    @Value("${link.to.auth.service}")
    private String linkToAuthService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final OkHttpClient client = new OkHttpClient().newBuilder().build();
    private static final String TEST_MODERATOR_ACCOUNT_EMAIL =  "admin@admin.com";
    private static final String TEST_MODERATOR_PASSWORD = "adminadmin";

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    private String returnModeratorToken() throws Exception{
        RequestBody body = RequestBody
                .create(
                        objectMapper.writeValueAsString(
                                new LoginRequest(TEST_MODERATOR_ACCOUNT_EMAIL, TEST_MODERATOR_PASSWORD)), JSON);

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
