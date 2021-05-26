package com.example.techtest.entity;

import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    private String name;

    public Image() {
    }

    public Image(Ticket ticket, String name) {
        this.id = 0L;
        this.ticket = ticket;
        this.name = name;
    }

    public Image(long id, Ticket ticket, String name) {
        this.id = id;
        this.ticket = ticket;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
