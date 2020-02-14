package com.taskagile.app.web.apis;

import com.taskagile.app.domain.application.UserService;
import com.taskagile.app.domain.model.user.UsernameExistsException;
import com.taskagile.app.utils.JsonUtils;
import com.taskagile.app.web.payload.RegistrationPayload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService serviceMock;

    @Test
    public void register_blankPlayload_shouldFailAndReturn400() throws Exception {
        mvc.perform(post("/api/registrations")).andExpect(status().is(400));
    }

    @Test
    public void register_existedUsername_shouldFailAndReturn400() throws Exception{
        RegistrationPayload payload = new RegistrationPayload();
        payload.setUsername("exist");
        payload.setEmailAddress("test@taskagile.com");
        payload.setPassword("My password!");

        doThrow(UsernameExistsException.class)
                .when(serviceMock)
                .register(payload.toCommand());

        mvc.perform(
                post("/api/registrations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(payload)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").value("Username already exists"));

    }

}