package com.springboottest.simpletestapplication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketBookingDao extends CrudRepository<TicketEntity,Integer> {
    Optional<TicketEntity> findByName(String tName);
}
