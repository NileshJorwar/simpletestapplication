package com.springboottest.simpletestapplication;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {"spring.profiles.active=test"}, classes = SimpletestapplicationApplication.class)
//@TestPropertySource("/application-test.properties")
//@ActiveProfiles("test")
public class TicketBookingDaoTest {

    @Autowired
    TicketBookingDao ticketBookingDao;

    @Test
    @Transactional
//    @Sql(value = "classpath:data.sql") // Not Needed as it is picked up automatically, it will be picked up if defined different name
    public void test_findby_id() {

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setId(115);
        ticketEntity.setName("testName");
        ticketEntity.setPassenger_age(10);
        ticketBookingDao.save(ticketEntity);

        TicketEntity actualResult= ticketBookingDao.findById(115).get();

        assertThat(ticketBookingDao.count()).isEqualTo(5);
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(115);
        assertThat(actualResult.getPassenger_age()).isEqualTo(10);
        assertThat(actualResult.getName()).isEqualTo("testName");

        ticketBookingDao.save(new TicketEntity());
        assertThat(ticketBookingDao.count()).isEqualTo(6);

    }

    @Test
    @Transactional
//    @Sql(value = "classpath:data.sql") // Not Needed as it is picked up automatically, it will be picked up if defined different name
    public void test_findby_name() {

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setId(115);
        ticketEntity.setName("testName");
        ticketEntity.setPassenger_age(10);
        ticketBookingDao.save(ticketEntity);

        TicketEntity actualResult= ticketBookingDao.findByName("testName").get();

        assertThat(ticketBookingDao.count()).isEqualTo(5);
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(115);
        assertThat(actualResult.getPassenger_age()).isEqualTo(10);
        assertThat(actualResult.getName()).isEqualTo("testName");

        ticketBookingDao.save(new TicketEntity());
        assertThat(ticketBookingDao.count()).isEqualTo(6);

    }

    @Test
    @Transactional
//    @Sql(value = "classpath:data.sql") // Not Needed as it is picked up automatically, it will be picked up if defined different name
    public void test_save_entity() {

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setId(115);
        ticketEntity.setName("testName");
        ticketEntity.setPassenger_age(10);

         TicketEntity actualResult= ticketBookingDao.save(ticketEntity);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(115);
        assertThat(actualResult.getPassenger_age()).isEqualTo(10);
        assertThat(actualResult.getName()).isEqualTo("testName");



    }
}