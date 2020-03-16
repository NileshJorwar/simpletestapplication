package com.springboottest.simpletestapplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest//(properties = {"spring.profiles.active=test"})
@DirtiesContext
//@ActiveProfiles(value = {"test"})
//@TestPropertySource("/application-test.properties")
@TestPropertySource(properties = {"ageValue=20"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc // Needed for running HTTP methods with Springboot Test
public class ControllerSpringBootTest {

    @Autowired
    Controller controller;

    @Value("${ageValue}")
    private String valueFromProperty;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    int randomPort;

    @Autowired
    MockMvc mockMvc;

   @Test
    public void contextLoadsSmokeTest(){
        assertThat(controller).isNotNull();
    }

    @Test
    public void greeting_using_mockMvc() throws Exception {

       MvcResult actualResult= mockMvc.perform(
                get("/api/greeting")
                .param("name","Shital")
                .param("gender","Female")
        ).andReturn();

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
        assertThat(responseEntity.getBody()).isEqualTo("Hi MrSachin how are ya ");
    }

    @Test
    public void should_return_string_for_greetingPathVariable_using_RESTTEMPLATE() throws URISyntaxException {

        String url="http://localhost:"+randomPort+"/api/greetingwithpathvariables/20";
        URI uri = new URI(url);

        ResponseEntity<String> responseEntity=
                testRestTemplate.getForEntity(uri,String.class);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo("If child or notfalse");
    }


}