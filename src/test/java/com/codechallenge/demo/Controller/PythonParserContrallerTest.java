package com.codechallenge.demo.Controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InterpreterController.class)
@AutoConfigureWebClient
public class PythonParserContrallerTest {

    @Autowired
    private MockMvc mockMvc;
    String input = "{\r\n \"code\":\"%python print 1  \"\r\n }";

    @Test
    public void parsePythonInputCodeValid() throws Exception {
        this.mockMvc.perform(post("/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("{\r\n  \"result\" : \"1\"\r\n}"));
    }

}
