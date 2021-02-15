package com.foxminded.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringRunner.class)
@WebMvcTest(ErrorController.class)
class ErrorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void exception() throws Exception{
        mockMvc.perform(get("/exceptions"))
                .andExpect(status().isOk())
                .andExpect(view().name("exceptions/error-page"));
    }
}