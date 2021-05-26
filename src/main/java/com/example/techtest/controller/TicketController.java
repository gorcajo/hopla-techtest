package com.example.techtest.controller;

import com.example.techtest.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/tickets")
    public ResponseEntity<TicketDto> createTicket(@RequestBody TicketCreationRequestDto body) {
        return ResponseEntity.ok(new TicketDto(ticketService.createTicket(body.getDesiredImageCount())));
    }

    @GetMapping("/tickets")
    public ResponseEntity<Page<TicketDto>> listTickets(
            @RequestParam(required = false) Long startMillisFromEpoch,
            @RequestParam(required = false) Long endMillisFromEpoch,
            @RequestParam(required = false) Boolean completed,
            Pageable pageable) {

        return ResponseEntity.ok(
                ticketService
                        .listTickets(startMillisFromEpoch, endMillisFromEpoch, completed, pageable)
                        .map(TicketDto::new));
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable("id") long ticketId) {
        return ResponseEntity.ok(new TicketDto(ticketService.retrieveTicket(ticketId)));
    }

    @PostMapping("/tickets/{id}/images")
    public ResponseEntity<Page<TicketDto>> storeImageInTicket(
            @PathVariable("id") long ticketId,
            @RequestBody ImageDto image) {

        ticketService.storeImage(ticketId, image.getBase64(), image.getName());
        return ResponseEntity.accepted().build();
    }
}
