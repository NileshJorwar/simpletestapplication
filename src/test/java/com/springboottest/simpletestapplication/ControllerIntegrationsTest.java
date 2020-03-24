package com.springboottest.simpletestapplication;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
classes = SimpletestapplicationApplication.class)
@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-test.properties")
//@TestPropertySource("/application-test.properties")
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/*
* Integration Test
* We dont mock dependencies
* */

public class ControllerIntegrationsTest {

    Controller controller;
    TicketService mockTicketService;

    @LocalServerPort
    int randomPort;

    @Autowired
    private TestRestTemplate testRestTemplate;

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
    @Sql( {"classpath:data.sql", "classpath:schema.sql"})
    public void should_return_ticketEntity_using_mockMvc_but_does_not_return() throws Exception {
        MvcResult mvcResult=mockMvc.perform(
                get("/api/getticket/1"))
                .andReturn();
        System.out.println(mvcResult.getResponse());
    }

    @Test
//    @Transactional
    @Sql( {"classpath:data.sql", "classpath:schema.sql"})
    public void should_return_ticketEntity_using_restTemplate() throws Exception {

        TicketEntity ticketEntity= testRestTemplate.getForObject(
                new URI("http://localhost:" + randomPort + "/api/getticket/114"),TicketEntity.class);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + randomPort + "/api/getticket/testId2", HttpMethod.GET, null,String.class);
    }
}

