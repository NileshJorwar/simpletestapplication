package com.springboottest.simpletestapplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class ControllerUnitTest {

    Controller controller;
    TicketService ticketService;

    @BeforeEach
    public void setUp()
    {
        ticketService=mock(TicketService.class);
        controller=new Controller(ticketService);
    }

    @Test
    public void test_createTicket()
    {
        TicketEntity expectedTicketEntity =
                TicketEntity.builder()
                        .id(123)
                        .name("testName")
                        .passenger_age(20)
                        .build();

        when(ticketService.createTicket(any())).thenReturn(expectedTicketEntity);

        TicketEntity actualResponse = controller.createTicket(expectedTicketEntity);

        verify(ticketService,times(1)).createTicket(any());
//        verifyNoInteractions(ticketService);

        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse).isEqualToComparingFieldByField(expectedTicketEntity);

    }

    @Test
    public void test_getTicketById()
    {
        TicketEntity expectedTicketEntity =
                TicketEntity.builder()
                        .id(123)
                        .name("testName")
                        .passenger_age(20)
                        .build();

        when(ticketService.getTicketEntityById(anyInt())).thenReturn(expectedTicketEntity);

        TicketEntity actualResponse = controller.getTicketById(123);

        verify(ticketService,times(1)).getTicketEntityById(123);
//        verifyNoInteractions(ticketService);

        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse).isEqualToComparingFieldByField(expectedTicketEntity);
        assertThat(actualResponse.getPassenger_age()).isEqualTo(20);
        assertThat(actualResponse.getName()).isEqualTo(expectedTicketEntity.getName());

    }

    @Test
    public void test_getTicketByName()
    {
        TicketEntity expectedTicketEntity =
                TicketEntity.builder()
                        .id(123)
                        .name("testName")
                        .passenger_age(20)
                        .build();

        when(ticketService.getTicketEntityByName(anyString())).thenReturn(expectedTicketEntity);

        TicketEntity actualResponse = controller.getTicketByName("testName");

        verify(ticketService,times(1)).getTicketEntityByName("testName");
//        verifyNoInteractions(ticketService);

        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse).isEqualToComparingFieldByField(expectedTicketEntity);
        assertThat(actualResponse.getPassenger_age()).isEqualTo(20);
        assertThat(actualResponse.getName()).isEqualTo(expectedTicketEntity.getName());

    }


    @Test
    public void test_greet()
    {

        ResponseEntity<String> actualResult=
                controller.greet("testName","male");

        verifyNoInteractions(ticketService);
//        verifyNoInteractions(ticketService);

        assertThat(actualResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualResult.getBody()).isEqualTo("Hi Mr testName how are ya ");

    }

    @Test
    public void test_greetUsingPathVariables()
    {

        ResponseEntity<String> actualResult=
                controller.greetUsingPathVariables(30);

        verifyNoInteractions(ticketService);
//        verifyNoInteractions(ticketService);

        assertThat(actualResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualResult.getBody()).isEqualTo("If child or not false");

    }
}