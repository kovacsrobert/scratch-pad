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
public class FooControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @WithAnonymousUser
    public void shouldBeUnauthorized_withAnonymousUser() throws Exception {
        mockMvc.perform(get(FooController.PATH, "bar"))
                .andExpect(status().isUnauthorized());
    }
    
    @Test
    public void shouldBeUnauthorized_withNotBarArgument() throws Exception {
        mockMvc.perform(get(FooController.PATH, "meme"))
                .andExpect(status().isUnauthorized());
    }
    
    @Test
    @WithMockUser(authorities = { UserRoles.ROLE_ADMIN })
    public void shouldBeOk_withBarArgument() throws Exception {
        mockMvc.perform(get(FooController.PATH, "bar"))
                .andExpect(status().isOk());
    }
}
