package com.example.demo;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(listeners = { MeTestExecutionListener.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
public class BarControllerTest {

    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    
    @Test
    @WithAnonymousUser
    public void shouldBeUnauthorized_withAnonymousUser() throws Exception {
        mockMvc.perform(get(BarController.PATH)).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = { UserRoles.ROLE_USER })
    public void shouldBeOk_withUserRole() throws Exception {
        mockMvc.perform(get(BarController.PATH)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = { UserRoles.ROLE_ADMIN })
    public void shouldBeForbidden_withAdminRole() throws Exception {
        mockMvc.perform(get(BarController.PATH)).andExpect(status().isForbidden());
    }
    
    @Test
    public void shouldBeUnauthorized_withAnonymousUser_withPostProcessor() throws Exception {
        mockMvc.perform(get(BarController.PATH).with(anonymous()))
        .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldBeOk_withUserRole_withPostProcessor() throws Exception {
        UserDetails userDetails = User.builder()
                .username("test")
                .password("test")
                .authorities(UserRoles.ROLE_USER)
                .build();
        mockMvc.perform(get(BarController.PATH).with(user(userDetails)))
        .andExpect(status().isOk());
    }

    @Test
    public void shouldBeForbidden_withAdminRole_withPostProcessor() throws Exception {
        UserDetails userDetails = User.builder()
                .username("test")
                .password("test")
                .authorities(UserRoles.ROLE_ADMIN)
                .build();
        mockMvc.perform(get(BarController.PATH).with(user(userDetails)))
        .andExpect(status().isForbidden());
    }
}
