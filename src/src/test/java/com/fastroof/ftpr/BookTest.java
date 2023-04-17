package com.fastroof.ftpr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastroof.ftpr.controller.BookApiController;
import com.fastroof.ftpr.pojo.PostBookRequestPojo;
import com.fastroof.ftpr.repository.BookFileRepository;
import com.fastroof.ftpr.repository.BookRepository;
import com.fastroof.ftpr.repository.CommentRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-before-sql.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BookTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookApiController controller;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookFileRepository bookFileRepository;


    @Test
    public void bookList() throws Exception {
        this.mvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bookRepository.findAll())));
    }

    @Test
    public void bookById() throws Exception {
        this.mvc.perform(get("/books/12"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bookRepository.findById(12))));
        this.mvc.perform(get("/books/55"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }


    @Test
    public void postBook() throws Exception {
        this.mvc.perform(post("/books"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mvc.perform(post("/books").header(HEADER, PREFIX + returnToken()))
                .andDo(print())
                .andExpect(status().isBadRequest());

        PostBookRequestPojo postBookRequestPojo = new PostBookRequestPojo();
        postBookRequestPojo.setName("Test book");
        postBookRequestPojo.setTagId(1);
        this.mvc.perform(post("/books").header(HEADER, PREFIX + returnToken())
                        .content(objectMapper.writeValueAsString(postBookRequestPojo)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void patchBook() throws Exception {
        this.mvc.perform(patch("/books/5"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mvc.perform(patch("/books/5").header(HEADER, PREFIX + returnToken()))
                .andDo(print())
                .andExpect(status().isBadRequest());

        this.mvc.perform(patch("/books/5").header(HEADER, PREFIX + returnToken())
                        .contentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED)
                        .content("name=new_Name"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void deleteBook() throws Exception {
        this.mvc.perform(delete("/books/5"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mvc.perform(delete("/books/5").header(HEADER, PREFIX + returnToken()))
                .andDo(print())
                .andExpect(status().isOk());

        this.mvc.perform(delete("/books/8").header(HEADER, PREFIX + returnToken()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void bookComments() throws Exception {
        this.mvc.perform(get("/books/2/comments"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(commentRepository.findAllByBookId(2))));

    }

    @Test
    public void bookFiles() throws Exception {
        this.mvc.perform(get("/books/2/files"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(bookFileRepository.findAllByBookId(2))));


        this.mvc.perform(get("/books/55/files"))
                .andDo(print())
                .andExpect(status().isNotFound());


    }


    @Test
    public void deleteBookFile() throws Exception {
        this.mvc.perform(delete("/books/6/files/7"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mvc.perform(delete("/books/6/files/7").header(HEADER, PREFIX + returnToken()))
                .andDo(print())
                .andExpect(status().isOk());

        this.mvc.perform(delete("/books/99/files/7").header(HEADER, PREFIX + returnToken()))
                .andDo(print())
                .andExpect(status().isNotFound());

        this.mvc.perform(delete("/books/6/files/99").header(HEADER, PREFIX + returnToken()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    public void postComment() throws Exception {
        this.mvc.perform(post("/books/10/comments"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mvc.perform(post("/books/10/comments").header(HEADER, PREFIX + returnToken()))
                .andDo(print())
                .andExpect(status().isBadRequest());

        this.mvc.perform(post("/books/10/comments").header(HEADER, PREFIX + returnToken())
                        .content("This is new comment"))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    public void postReport() throws Exception {
        this.mvc.perform(post("/books/10/report"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mvc.perform(post("/books/10/report").header(HEADER, PREFIX + returnToken()))
                .andDo(print())
                .andExpect(status().isBadRequest());

        this.mvc.perform(post("/books/99/report").header(HEADER, PREFIX + returnToken())
                        .content("This is new report"))
                .andDo(print())
                .andExpect(status().isNotFound());

        this.mvc.perform(post("/books/10/report").header(HEADER, PREFIX + returnToken())
                        .content("This is new report for book 10"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void postPersonalLibrary() throws Exception {
        this.mvc.perform(post("/books/6/personal-library"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mvc.perform(post("/books/6/personal-library").header(HEADER, PREFIX + returnToken()))
                .andDo(print())
                .andExpect(status().isBadRequest());
        this.mvc.perform(post("/books/10/personal-library").header(HEADER, PREFIX + returnToken()))
                .andDo(print())
                .andExpect(status().isOk());
        this.mvc.perform(post("/books/99/personal-library").header(HEADER, PREFIX + returnToken()))
                .andDo(print())
                .andExpect(status().isNotFound());

    }


    @Value("${link.to.auth.service}")
    private String linkToAuthService;


    @Autowired
    private ObjectMapper objectMapper;

    private static final OkHttpClient client = new OkHttpClient().newBuilder().build();
    private static final String TEST_ACCOUNT_EMAIL = "sasha@sasha.com";
    private static final String TEST_PASSWORD = "sashasasha";

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    private String returnToken() throws Exception {
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
