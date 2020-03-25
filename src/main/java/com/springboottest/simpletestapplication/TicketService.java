package com.springboottest.simpletestapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private TicketBookingDao ticketBookingDao;
    public TicketService(TicketBookingDao ticketBookingDao)
    {
        this.ticketBookingDao=ticketBookingDao;
    }

    public TicketEntity createTicket(TicketEntity ticketEntity) {
//        TicketEntity ticketEntity1 = new TicketEntity();
//        ticketEntity1.setId(123);
//        ticketEntity1.setPassenger_age(10);
//        ticketEntity1.setName("Ramesh Solanki");
        return ticketBookingDao.save(ticketEntity);
    }

    public TicketEntity getTicketEntityById(int tId) {
        return ticketBookingDao.findById(tId).get();
    }

    public TicketEntity getTicketEntityByName(String tName) {
        return ticketBookingDao.findByName(tName).get();
    }

}
