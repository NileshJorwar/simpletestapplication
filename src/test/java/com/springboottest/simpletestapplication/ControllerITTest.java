package com.springboottest.simpletestapplication;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-test.properties")
//@TestPropertySource("/application-test.properties")
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/*
* Integration Test
* We dont mock dependencies
* */

public class ControllerITTest {

    Controller controller;
    TicketService mockTicketService;

    @LocalServerPort
    int randomPort;

    MockMvc mockMvc;

    @BeforeAll
    public void setUp()
    {
        mockTicketService=mock(TicketService.class);
        controller = new Controller(mockTicketService);
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void contextLoads()
    {
        assertThat(controller).isNotNull();
    }

    @Test
    @Transactional
    @Sql(value = "classpath:data.sql")
    public void testGet() throws Exception {
        MvcResult mvcResult=mockMvc.perform(
                get("/api/getticket/testId1"))
                .andReturn();
        System.out.println(mvcResult.getResponse());
    }

}