package com.springboottest.simpletestapplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@WebMvcTest // Does not start the full application context
// WebMvcTest Performance purpose -- should be used along with SpringRunner.class and creates bean MockMvc to simulate HTTP request
@RunWith(SpringRunner.class)
public class ControllerTest {

    @Autowired
    MockMvc mockMvc; // You can also use WebTestClient to test the HTTP methods

    //Cannot use RestTemplate for WebMvcTest
//    @Autowired
//    private TestRestTemplate restTemplate;


//    @Autowired
//    Controller controller;

    @MockBean
    TicketService ticketServiceMock;

    @Test
    public void getMappingMale() throws Exception {
        System.out.println("");
        String name = "Nilesh";
        String gender = "male";
        mockMvc.perform(
                get("/api/greeting")
                        .param("name", name)
                        .param("gender", gender)
        ).andExpect(
                status().isOk()
        ).andExpect(content().string(containsString(String.format("Hi Mr%s how are ya ", name))))
        ;

    }

    @Test
    public void getMappingFeMale() throws Exception {
        System.out.println("");
        String name = "Shital";
        String gender = "female";
        mockMvc.perform(
                get("/api/greeting")
                        .param("name", name)
                        .param("gender", gender)
        ).andExpect(
                status().isOk()
        ).andExpect(content().string(containsString(String.format("Hi Mrs%s how are ya ", name))))
        ;
    }

    @Test
    public void should_return_false_if_not_child() throws Exception {
        mockMvc.perform(get("/api/greetingwithpathvariables/19"))
                .andExpect(content().string("If child or notfalse"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_true_if_child() throws Exception {
        mockMvc.perform(get("/api/greetingwithpathvariables/15"))
                .andExpect(content().string("If child or nottrue"))
                .andExpect(status().isOk());

        verify(ticketServiceMock, times(0)).getTicketEntityById("");
//        verifyNoInteractions(ticketServiceMock);
        verifyZeroInteractions(ticketServiceMock);
    }

    @Test
    public void should_return_getTicketById() throws Exception {
        TicketEntity ticketEntity1 = new TicketEntity();
        ticketEntity1.setId("test");
        ticketEntity1.setPassenger_age(10);
        ticketEntity1.setName("Ramesh Solanki");

        when(ticketServiceMock.getTicketEntityById("test")).thenReturn(ticketEntity1);

        mockMvc.perform(
                get("/api/getticket/{ticketId}", "test"))
                .andExpect(content().string(containsString(new ObjectMapper().writeValueAsString(ticketEntity1))))
                .andExpect(status().isOk());

        verify(ticketServiceMock, times(1)).getTicketEntityById("test");
        verifyNoMoreInteractions(ticketServiceMock);
    }

    @Test
    public void should_return_postTicket() throws Exception {

        TicketEntity ticketEntity1 = new TicketEntity();
        ticketEntity1.setPassenger_age(10);
        ticketEntity1.setId("testId");
        ticketEntity1.setName("Ramesh Solanki");

        when(ticketServiceMock.createTicket(ticketEntity1)).thenReturn(ticketEntity1);

        MvcResult mvcResult = (MvcResult) mockMvc.perform(
                post("/api/createticket")
//                .content(String.valueOf(containsString(new ObjectMapper().writeValueAsString(ticketEntity1)))))
                        .content(asJsonString(ticketEntity1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println("");
//        verify(ticketServiceMock,times(1)).createTicket(ticketEntity1);
//        verifyNoMoreInteractions(ticketServiceMock);

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //Below Method cannot be used with @WebMvcTest annotation
    @Test
    public void should_return_postTicketUsingRestTemplate() throws Exception {

        String baseUrl = "http://localhost:8080/createticket/";

        TicketEntity ticketEntity1 = new TicketEntity();
        ticketEntity1.setId("testId");
        ticketEntity1.setPassenger_age(10);
        ticketEntity1.setName("Ramesh Solanki");

//        ResponseEntity<TicketEntity> actualResult =
//                this.restTemplate.postForEntity(baseUrl, ticketEntity1, TicketEntity.class);
//        Assert.assertEquals(actualResult.getBody().getName(), ticketEntity1.getName());
    }

}