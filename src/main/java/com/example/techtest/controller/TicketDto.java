package com.example.techtest.controller;

import com.example.techtest.entity.Image;
import com.example.techtest.entity.Ticket;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TicketDto {

    private final Long id;
    private final Integer desiredImageCount;
    private final Boolean completed;
    private final OffsetDateTime createdOn;
    private final List<String> imagePaths;

    public TicketDto() {
        this.id = null;
        this.desiredImageCount = null;
        this.completed = null;
        this.createdOn = null;
        this.imagePaths = null;
    }

    public TicketDto(Ticket entity) {
        this.id = entity.getId();
        this.desiredImageCount = entity.getDesiredImageCount();
        this.completed = entity.getCompleted();
        this.createdOn = entity.getCreatedOn();
        this.imagePaths = entity.getImages().stream().map(Image::getName).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public Integer getDesiredImageCount() {
        return desiredImageCount;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public OffsetDateTime getCreatedOn() {
        return createdOn;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }
}
