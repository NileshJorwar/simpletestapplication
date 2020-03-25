package com.springboottest.simpletestapplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest(properties = {"spring.profiles.active=test"})
@Transactional
class TicketServiceIntegrationTest {

    @Autowired
    TicketService ticketService;

    @Autowired
    TicketBookingDao ticketBookingDao;

    @Test
    public void should_return_ticket_if_called_createTicket()
    {
        TicketEntity expectedTicket = new TicketEntity();
        expectedTicket.setId(123);
        expectedTicket.setPassenger_age(10);
        expectedTicket.setName("Ramesh Solanki");

        TicketEntity actualTicket =ticketService.createTicket(expectedTicket);

        assertEquals(expectedTicket,actualTicket);

    }

    @Test
    public void should_return_ticket_if_called_getTicketEntityById()
    {
        TicketEntity expectedTicket = new TicketEntity();
//        expectedTicket.setId(123);
        expectedTicket.setPassenger_age(10);
        expectedTicket.setName("Ramesh Solanki");

        //ticketBookingDao.save(expectedTicket);
        ticketService.createTicket(expectedTicket);

        TicketEntity actualTicket =ticketService.getTicketEntityById(113);

        assertEquals(expectedTicket,actualTicket);

    }

    @Test
    public void should_return_ticket_if_called_getTicketEntityByName()
    {
        TicketEntity expectedTicket = new TicketEntity();
//        expectedTicket.setId(123);
        expectedTicket.setPassenger_age(10);
        expectedTicket.setName("Rameshan");

        //ticketBookingDao.save(expectedTicket);
        ticketService.createTicket(expectedTicket);

        TicketEntity actualTicket =ticketService.getTicketEntityByName("Rameshan");

        assertEquals(expectedTicket,actualTicket);

    }
}