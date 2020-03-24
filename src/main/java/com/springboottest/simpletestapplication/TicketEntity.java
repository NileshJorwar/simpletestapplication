package com.springboottest.simpletestapplication;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "TicketTable")
@Builder
public class TicketEntity {

    @Id
    @Column(length = 200)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int passenger_age;

    @Column
    private String name;

    public TicketEntity() {

    }

    public TicketEntity(int id, int passenger_age, String name) {
    this.id=id;
    this.passenger_age=passenger_age;
    this.name=name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
