package com.springboottest.simpletestapplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@Import(ControllerSpringBootTest.ControllerConfig.class)
//@SpringBootTest//(properties = {"spring.profiles.active=test"})
@DirtiesContext
//@ActiveProfiles(value = {"test"})
//@TestPropertySource("/application-test.properties")
@TestPropertySource(properties = {"ageValue=20"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc // Needed for running HTTP methods with Springboot Test
//Includes both the @AutoConfigureWebMvc and the @AutoConfigureMockMvc, among other functionality.
public class ControllerSpringBootTest {

    Controller controller;
    TicketService mockTicketService;

    @Value("${ageValue}")
    private String valueFromProperty;

    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    int randomPort;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp()
    {
        mockTicketService=mock(TicketService.class);
        controller=new Controller(mockTicketService);
        this.mockMvc = standaloneSetup(this.controller).build();
    }

   @Test
    public void contextLoadsSmokeTest(){
        assertThat(controller).isNotNull();
    }

    @Test
    public void should_return_created_ticketEntity_when_called_createTicket_method() throws Exception {
        TicketEntity expectedTicketEntity =
                TicketEntity.builder()
                        .id(123)
                        .name("testName")
                        .passenger_age(20)
                        .build();

        when(mockTicketService.createTicket(any())).thenReturn(expectedTicketEntity);

        String inputJson = "{\"Id\":\"testId\",\"name\":\"testName\",\"passenger_age\":20}";

        MvcResult actualResult=
                mockMvc.perform(
                        post("/api/createticket")
                                .content(new ObjectMapper().writeValueAsString(expectedTicketEntity))
                                .accept(MediaType.APPLICATION_JSON)
                                //.content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
//                .andExpect(jsonPath("$.id").value("testId"))
//                .andExpect(jsonPath("$.name").value("testName"))
//                .andExpect(jsonPath("$.passenger_age").value(20));
                .andReturn();

        TicketEntity actualTicketEntity = new ObjectMapper().readValue(actualResult.getResponse().getContentAsString(),TicketEntity.class);

        verify(mockTicketService, Mockito.atLeastOnce()).createTicket(any());
        verify(mockTicketService,times(1)).createTicket(any());
        verifyNoMoreInteractions(mockTicketService);

        assertThat(actualResult).isNotNull();
        assertThat(actualTicketEntity).isEqualToComparingFieldByField(expectedTicketEntity);

    }

    @Test
    public void should_return_ticketEntity_when_called_getTicketById() throws Exception {

        TicketEntity expectedTicketEntity =
              TicketEntity.builder()
                .id(123)
                .name("testName")
                .passenger_age(20)
                .build();

       when(mockTicketService.getTicketEntityById(any()))
               .thenReturn(expectedTicketEntity);

       MvcResult actualResult =
                mockMvc.perform(
                        get("/api/getticket/testId")
                )
                .andReturn();

       verify(mockTicketService).getTicketEntityById(123);
        Mockito.verify(mockTicketService, Mockito.times(1)).getTicketEntityById(123);
        Mockito.verifyNoMoreInteractions(mockTicketService);

        TicketEntity actualTicketEntity = new ObjectMapper().readValue(actualResult.getResponse().getContentAsString(),TicketEntity.class);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getResponse().getStatus()).isEqualTo(200);

        assertThat(actualTicketEntity).isEqualToComparingFieldByField(expectedTicketEntity);
        assertThat(actualTicketEntity.getPassenger_age()).isEqualTo(expectedTicketEntity.getPassenger_age());
    }

    @Test
    public void greeting_using_mockMvc() throws Exception {

       MvcResult actualResult= mockMvc.perform(
                get("/api/greeting")
                .param("name","Shital")
                .param("gender","Female")
        ).andExpect(status().isOk())
               .andReturn();

       assertThat(actualResult).isNotNull();
       assertThat(actualResult.getResponse().getContentAsString()).isEqualTo("Hi Mrs Shital how are ya ");
   }

    @Test
    public void greetingPathVariable_using_mockMvc() throws Exception {

        MvcResult actualResult= mockMvc.perform(
                get("/api/greetingwithpathvariables/{age}","20")
        ).andReturn();

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getResponse().getContentAsString()).isEqualTo("If child or not false");
    }

    @Test
    public void should_return_string_for_greeting_using_RESTTEMPLATE() throws URISyntaxException {

        String url="http://localhost:"+randomPort+"/api/greeting?name=Sachin&gender=male";
        URI uri = new URI(url);

        ResponseEntity<String> responseEntity=
                testRestTemplate.getForEntity(uri,String.class);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo("Hi Mr Sachin how are ya ");
    }

    @Test
    public void should_return_string_for_greetingPathVariable_using_RESTTEMPLATE() throws URISyntaxException {

        String url="http://localhost:"+randomPort+"/api/greetingwithpathvariables/20";
        URI uri = new URI(url);

        ResponseEntity<String> responseEntity=
                testRestTemplate.getForEntity(uri,String.class);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo("If child or not false");
    }

    @TestConfiguration
    public static class ControllerConfig{

        @Bean
        TestRestTemplate testRestTemplate(){
            return new TestRestTemplate();
        }

    }

}