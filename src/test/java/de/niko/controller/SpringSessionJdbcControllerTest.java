package de.niko.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockCookie;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SpringSessionJdbcControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test() throws Exception {
        MockHttpSession mockHttpSession = new MockHttpSession();

        var request1 = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/").session(mockHttpSession))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.notNullValue()))
                .andReturn();

        var response1ContentAsString = request1.getResponse().getContentAsString();

        var cookie_0 = request1.getResponse().getCookies()[0];
        var mockCookie = new MockCookie(cookie_0.getName(), cookie_0.getValue());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/").cookie(mockCookie))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.equalTo(response1ContentAsString)));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/session").param("externalSessionId", response1ContentAsString))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.equalTo("Some value")));
    }
}
