package nodium.group.backend.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import nodium.group.backend.dto.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    void testUserCanRegister()throws Exception{
        mockMvc.perform(post("/api/v1/nodium/Users/Register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new RegisterRequest())))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    void testUserCanRegisterWithValidDetails()throws Exception{
        mockMvc.perform(post("/api/v1/nodium/Users/Register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegisterRequest("email@email.com",
                                "Password","first","last"))))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}
