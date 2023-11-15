package com.example.login_test;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.login_test.user.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

 // http://localhost:8080 로 시작할 수 있도록 셋팅.
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@SpringBootTest
@Sql("classpath:db/dataset.sql")
@AutoConfigureMockMvc
public class UserControllerTest extends MyRestDoc{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testJoin() throws Exception{
        // given
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();

        joinDTO.setEmail("user@green.com");
        joinDTO.setPassword("Password@123");
        joinDTO.setUsername("임꺽정");

        String requestBody = objectMapper.writeValueAsString(joinDTO);

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }


    @Test
    public void testLogin() throws Exception{
        // given
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();

        joinDTO.setEmail("user@green.com");
        joinDTO.setPassword("Password@123");
        joinDTO.setUsername("임꺽정");

        String requestBody = objectMapper.writeValueAsString(joinDTO);

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
