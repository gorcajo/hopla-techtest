package com.example.techtest.service;

import com.example.techtest.cloudinary.CloudinaryPayload;
import com.example.techtest.cloudinary.CloudinaryRestClient;
import com.example.techtest.entity.Image;
import com.example.techtest.entity.Ticket;
import com.example.techtest.exception.NotFoundException;
import com.example.techtest.repository.ImageRepository;
import com.example.techtest.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.concurrent.Future;

@Service
public class TicketService {

    private final TimeService timeService;
    private final TicketRepository ticketRepository;
    private final ImageRepository imageRepository;
    private final CloudinaryRestClient cloudinaryClient;

    @Value("${cloudinary.cloudName}")
    private String cloudName;

    @Value("${cloudinary.apiKey}")
    private String apiKey;

    @Value("${cloudinary.secret}")
    private String secret;

    public TicketService(
            @Autowired TicketRepository ticketRepository,
            @Autowired ImageRepository imageRepository,
            @Autowired TimeService timeService,
            @Autowired CloudinaryRestClient cloudinaryClient) {

        this.ticketRepository = ticketRepository;
        this.imageRepository = imageRepository;
        this.timeService = timeService;
        this.cloudinaryClient = cloudinaryClient;
    }

    public Ticket createTicket(int desiredImageCount) {
        var ticket = new Ticket();
        ticket.setDesiredImageCount(desiredImageCount);
        ticket.setCompleted(false);
        ticket.setCreatedOn(timeService.getCurrentOffsetDateTime());
        return ticketRepository.save(ticket);
    }

    @Async
    public Future<Ticket> storeImage(Long ticketId, String imageBase64, String imageName) {
        var ticket = ticketRepository.findById(ticketId).orElseThrow(NotFoundException::new);
        var image = new Image(ticket, "/tmp/" + imageName);

        cloudinaryClient.upload(
                cloudName,
                "image",
                new CloudinaryPayload(
                        imageBase64,
                        apiKey,
                        timeService.getCurrentOffsetDateTime().toInstant().toEpochMilli(),
                        secret));

        imageRepository.save(image);
        ticket.getImages().add(image);

        if (ticket.getDesiredImageCount() == ticket.getImages().size()) {
            ticket.setCompleted(true);
        }

        ticketRepository.save(ticket);

        return new AsyncResult<>(ticket);
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

        if (!Objects.isNull(startMillisFromEpoch) && !Objects.isNull(endMillisFromEpoch)) {
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
