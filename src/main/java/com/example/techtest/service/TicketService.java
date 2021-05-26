package com.example.techtest.service;

import com.example.techtest.entity.Ticket;
import com.example.techtest.exception.NotFoundException;
import com.example.techtest.repository.ImageRepository;
import com.example.techtest.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TimeService timeService;
    private final TicketRepository ticketRepository;
    private final ImageRepository imageRepository;

    public TicketService(
            @Autowired TicketRepository ticketRepository,
            @Autowired ImageRepository imageRepository,
            @Autowired TimeService timeService) {

        this.ticketRepository = ticketRepository;
        this.imageRepository = imageRepository;
        this.timeService = timeService;
    }

    public Ticket createTicket(int desiredImageCount) {
        var ticket = new Ticket();
        ticket.setDesiredImageCount(desiredImageCount);
        ticket.setCompleted(false);
        ticket.setCreatedOn(timeService.getCurrentOffsetDateTime());
        return ticketRepository.save(ticket);
    }

    public void storeImage(Long ticketId, String imageBase64, String imageName) {
        // TODO
    }

    public Page<Ticket> listTickets(
            Long startMillisFromEpoch,
            Long endMillisFromEpoch,
            Boolean completed,
            Pageable pageable) {

        // TODO
        return new PageImpl<>(List.of(new Ticket(), new Ticket()));
    }

    public Ticket retrieveTicket(long id) {
        return ticketRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
