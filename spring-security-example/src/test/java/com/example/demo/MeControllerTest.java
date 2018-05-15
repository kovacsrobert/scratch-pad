package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class MeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @WithAnonymousUser
    public void shouldBeUnauthorized_withAnonymousUser() throws Exception {
        mockMvc.perform(get(MeController.TEST_PATH))
                .andExpect(status().isUnauthorized());
    }
    
    @Test
    @WithMockUser(authorities = { UserRoles.ROLE_USER })
    public void shouldBeForbidden_withUserRole() throws Exception {
        mockMvc.perform(get(MeController.TEST_PATH))
                .andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser(authorities = { UserRoles.ROLE_ADMIN })
    public void shouldBeOk_withAdminRole() throws Exception {
        mockMvc.perform(get(MeController.TEST_PATH))
                .andExpect(status().isOk());
    }
}
