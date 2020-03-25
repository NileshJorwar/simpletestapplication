package com.springboottest.simpletestapplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TicketServiceUnitTest {

    TicketBookingDao mockTicketBookingDao;
    TicketService ticketService;

    @BeforeEach
    public void setup()
    {
        mockTicketBookingDao=mock(TicketBookingDao.class);
        ticketService=new TicketService(mockTicketBookingDao);
    }

    @Test
    public void test_createTicket()
    {
        TicketEntity expectedTicket = new TicketEntity();
        expectedTicket.setId(123);
        expectedTicket.setPassenger_age(10);
        expectedTicket.setName("Ramesh Solanki");

        when(mockTicketBookingDao.save(any())).thenReturn(expectedTicket);

        TicketEntity actualTicket=
                ticketService.createTicket(expectedTicket);

        verify(mockTicketBookingDao,times(1)).save(any());
        verifyNoMoreInteractions(mockTicketBookingDao);

        assertThat(actualTicket).isNotNull();
        assertThat(actualTicket).isEqualToComparingFieldByField(expectedTicket);

    }

    @Test
    public void test_getTicketEntityById()
    {
        TicketEntity expectedTicket = new TicketEntity();
        expectedTicket.setId(123);
        expectedTicket.setPassenger_age(10);
        expectedTicket.setName("Ramesh Solanki");

        Optional<TicketEntity> t =
            Optional.of(expectedTicket);

        when(mockTicketBookingDao.findById(anyInt())).thenReturn(t);

        TicketEntity actualTicket=
                ticketService.getTicketEntityById(10);

        verify(mockTicketBookingDao,times(1)).findById(anyInt());
        verifyNoMoreInteractions(mockTicketBookingDao);

        assertThat(actualTicket).isNotNull();
        assertThat(actualTicket).isEqualToComparingFieldByField(expectedTicket);

    }

    @Test
    public void test_getTicketEntityByName()
    {
        TicketEntity expectedTicket = new TicketEntity();
        expectedTicket.setId(123);
        expectedTicket.setPassenger_age(10);
        expectedTicket.setName("Ramesh Solanki");

        Optional<TicketEntity> t =
                Optional.of(expectedTicket);

        when(mockTicketBookingDao.findByName(anyString())).thenReturn(t);

        TicketEntity actualTicket=
                ticketService.getTicketEntityByName("test");

        verify(mockTicketBookingDao,times(1)).findByName(anyString());
        verifyNoMoreInteractions(mockTicketBookingDao);

        assertThat(actualTicket).isNotNull();
        assertThat(actualTicket).isEqualToComparingFieldByField(expectedTicket);

    }
}