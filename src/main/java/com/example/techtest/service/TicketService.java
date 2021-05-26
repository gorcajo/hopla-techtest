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

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Objects;

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

    public Page<Ticket> listTickets(Boolean completed, Pageable pageable) {
        return listTickets(completed, null, null, pageable);
    }

    public Page<Ticket> listTickets(Long startMillisFromEpoch, Long endMillisFromEpoch, Pageable pageable) {
        return listTickets(null, startMillisFromEpoch, endMillisFromEpoch, pageable);
    }

    public Page<Ticket> listTickets(Pageable pageable) {
        return listTickets(null, null, null, pageable);
    }

    public Page<Ticket> listTickets(
            Boolean completed,
            Long startMillisFromEpoch,
            Long endMillisFromEpoch,
            Pageable pageable) {

        OffsetDateTime from = null;
        OffsetDateTime to = null;

        if (!Objects.isNull(startMillisFromEpoch) &&  !Objects.isNull(endMillisFromEpoch)) {
            from = OffsetDateTime.ofInstant(
                    Instant.ofEpochMilli(startMillisFromEpoch),
                    ZoneId.of("Europe/Madrid"));

            to = OffsetDateTime.ofInstant(
                    Instant.ofEpochMilli(endMillisFromEpoch),
                    ZoneId.of("Europe/Madrid"));
        }

        if (!Objects.isNull(completed) && !Objects.isNull(from)) {
            return ticketRepository.findAllByCompletedAndCreatedOnBetween(completed, from, to, pageable);
        } else if (!Objects.isNull(completed)) {
            return ticketRepository.findAllByCompleted(completed, pageable);
        } else if (!Objects.isNull(from)) {
            return ticketRepository.findAllByCreatedOnBetween(from, to, pageable);
        } else {
            return ticketRepository.findAll(pageable);
        }
    }

    public Ticket retrieveTicket(long id) {
        return ticketRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
