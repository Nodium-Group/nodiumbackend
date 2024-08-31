package nodium.group.backend.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import nodium.group.backend.dto.request.LoginRequest;
import nodium.group.backend.dto.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static nodium.group.backend.utils.AppUtils.BEARER;
import static nodium.group.backend.utils.AppUtils.LOGIN_URL;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
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
    @Sql(scripts = {"/db/truncate.sql"})
    void testUserCanRegister()throws Exception{
        mockMvc.perform(post("/api/v1/nodium/Users/Register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new RegisterRequest())))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    @Sql(scripts = {"/db/truncate.sql"})
    void testUserCanRegisterWithValidDetails()throws Exception{
        mockMvc.perform(post("/api/v1/nodium/Users/Register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegisterRequest("email@email.com",
                                "Password","first","last"))))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
    @Test
    @Sql(scripts = {"/db/truncate.sql"})
    void testUserCanRegisterAndLogin()throws Exception{
        mockMvc.perform(post("/api/v1/nodium/Users/Register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegisterRequest("email@email.com",
                                "Password","first","last"))))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
        mockMvc.perform(post(LOGIN_URL)
                        .header(AUTHORIZATION, BEARER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequest("email@email.com","Password"))))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}
