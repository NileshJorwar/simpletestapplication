package com.springboottest.simpletestapplication;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {"spring.profiles.active=test"})
//@TestPropertySource("/application-test.properties")

//@ActiveProfiles("test")
class TicketBookingDaoTest {

    @Autowired
    TicketBookingDao ticketBookingDao;

    @Test
    @Transactional
//    @Sql(value = "classpath:data.sql")
    public void test_findby_id() {

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setId("testId");
        ticketEntity.setName("testName");
        ticketEntity.setPassenger_age(10);
        ticketBookingDao.save(ticketEntity);

        TicketEntity actualResult= ticketBookingDao.findById("testId").get();

        assertThat(ticketBookingDao.count()).isEqualTo(5);
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo("testId");
        assertThat(actualResult.getPassenger_age()).isEqualTo(10);
        assertThat(actualResult.getName()).isEqualTo("testName");

        ticketBookingDao.save(new TicketEntity());
        assertThat(ticketBookingDao.count()).isEqualTo(6);

    }


}