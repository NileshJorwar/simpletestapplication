package com.springboottest.simpletestapplication;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table
@Builder
public class TicketEntity {
    @Id
    @Column
    private String Id;

    @Column
    private int passenger_age;

    @Column
    private String name;

    public TicketEntity() {
        this.Id = UUID.randomUUID().toString();
    }

    public TicketEntity(String Id, int passenger_age, String name) {
    this.Id=Id;
    this.passenger_age=passenger_age;
    this.name=name;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getPassenger_age() {
        return passenger_age;
    }

    public void setPassenger_age(int passenger_age) {
        this.passenger_age = passenger_age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
