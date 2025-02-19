package nodium.group.backend.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import nodium.group.backend.dto.request.JobRequest;
import nodium.group.backend.dto.request.LoginRequest;
import nodium.group.backend.dto.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.BigInteger;

import static nodium.group.backend.utils.AppUtils.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @Sql({"/db/truncate.sql"})
    void testUserCanRegisterWithValidDetails()throws Exception{
        mockMvc.perform(post(REGISTER_URL)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(
                                new RegisterRequest("email@email.com",
                                "Password","first","last"))))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
    @Test
    @Sql({"/db/truncate.sql"})
    void testUserCanRegisterAndLogin()throws Exception{
        mockMvc.perform(post(REGISTER_URL)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(
                                new RegisterRequest("email@email.com",
                                "Password","first","laslt"))))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
        mockMvc.perform(post(LOGIN_URL)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(
                                new LoginRequest("email@email.com",
                                "Password"))))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
    @Test
    @Sql({"/db/truncate.sql"})
    void testUserCanPostJobs() throws Exception{
       var result =  mockMvc.perform(post(REGISTER_URL)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(
                                new RegisterRequest("email@email.com",
                                "Password","first","last"))))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andReturn();
       result = mockMvc.perform(post(LOGIN_URL)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(
                                new LoginRequest("email@email.com",
                                "Password"))))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andReturn();
        String id =  result.getResponse().getContentAsString().substring(26,27);
        mockMvc.perform(post("/api/v1/nodium/users/post-jobs")
                        .header(AUTHORIZATION, AUTH_HEADER_PREFIX+result.getResponse().
                                getHeader(AUTHORIZATION).substring(7).strip())
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(
                                new JobRequest(new BigInteger(id).longValue(),
                                "location",
                                "description","name",new BigDecimal("9000"),
                                "REMOTE"))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }
    @Test
    @Sql({"/db/truncate.sql"})
    void testProvidersCanRegister()throws Exception {
       var result =  mockMvc.perform(post(REGISTER_URL)
                .content("{\"email\":\"email@email.com\"," +
                        "\"password\":\"password\",\"firstname\":\"firstname\"," +
                        "\"lastname\":\"lastname\"}")
                        .contentType(APPLICATION_JSON))
                .andDo(req -> System.out.println(req.getRequest().getHeaderNames()))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        }
    }