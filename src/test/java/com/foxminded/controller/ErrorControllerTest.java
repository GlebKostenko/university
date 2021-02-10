package com.foxminded.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


class ErrorControllerTest {
    private ErrorController errorController = new ErrorController();
    private MockMvc mockMvc;

    ErrorControllerTest(){
        mockMvc = MockMvcBuilders.standaloneSetup(errorController).build();
    }

    @Test
    void exception() throws Exception{
        mockMvc.perform(get("/exceptions"))
                .andExpect(status().isOk())
                .andExpect(view().name("exceptions/error-page"));
    }
}