
package com.dalhousie.csci5308.mms;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void loginAvailableForAll() throws Exception {
        mockMvc
                .perform(get("/admin/Login"))
                .andExpect(status().isOk());
    }

    @Test
    public void validLoginAdmin() throws Exception {
        mockMvc
                .perform(formLogin().loginProcessingUrl("/admin/Login").user("admin").password("admin"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin/home"))
                .andExpect(authenticated().withUsername("admin"));

        mockMvc
                .perform(logout().logoutUrl("/logout"))
                .andExpect(status().isOk());

    }
    @Test
    public void invalidLoginAdmin() throws Exception {
        String loginErrorUrl = "/admin/Login?error";
        mockMvc
                .perform(formLogin().loginProcessingUrl("/admin/Login").password("invalid"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(loginErrorUrl))
                .andExpect(unauthenticated());
        }
    @Test
    public void validLoginUser() throws Exception {
        mockMvc
                .perform(formLogin().loginProcessingUrl("/user/Login").user("test").password("123456"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/home"))
                .andExpect(authenticated().withUsername("test"));

        mockMvc
                .perform(logout().logoutUrl("/logout"))
                .andExpect(status().isOk());

    }
    @Test
    public void invalidLoginUser() throws Exception {
        String loginErrorUrl = "/user/Login?error";
        mockMvc
                .perform(formLogin().loginProcessingUrl("/user/Login").user("invalid").password("invalid"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(loginErrorUrl))
                .andExpect(unauthenticated());
    }
}
