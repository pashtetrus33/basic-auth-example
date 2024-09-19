package ru.skillbox.auth_basic_example.controller;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.skillbox.auth_basic_example.AbstractTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends AbstractTest {

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenUserRequestMethodWithUserRole_ThenReturnOk() throws Exception {

        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("Method called by user: user. Role is: ROLE_USER"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenUserRequestMethodWithAdminRole_ThenReturnOk() throws Exception {

        mockMvc.perform(get("/api/v1/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("Method called by user: admin. Role is: ROLE_ADMIN"));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsServiceImpl", value = "testUser",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void whenUserRequestMethodWithNameTestUser_ThenReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("Method called by user: testUser. Role is: ROLE_USER"));
    }
}