package com.fastroof.ftpr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastroof.ftpr.pojo.HelpRequestPojo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-before-sql.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class HelpRequestTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;




    @Test
    public void postHelpRequest() throws Exception{
        this.mvc.perform(post("/help-request"))
                .andDo(print())
                .andExpect(status().isBadRequest());

        HelpRequestPojo helpRequestPojo = new HelpRequestPojo();
        helpRequestPojo.setEmail("YuraTest@gmail.com");
        helpRequestPojo.setText("This is test text");

        this.mvc.perform(post("/help-request")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("email=YuraTest@gmail.com" + "&text=This is test text"))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
