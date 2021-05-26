package com.example.techtest.entity;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int desiredImageCount;
    private boolean completed;
    private OffsetDateTime createdOn;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.EAGER)
    private List<Image> images;

    public Ticket() {
        this.images = new ArrayList<>();
    }

    public Ticket(Long id, Integer desiredImageCount, Boolean completed, OffsetDateTime createdOn, List<Image> imagePaths) {
        this.id = id;
        this.desiredImageCount = desiredImageCount;
        this.completed = completed;
        this.createdOn = createdOn;
        this.images = imagePaths;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDesiredImageCount() {
        return desiredImageCount;
    }

    public void setDesiredImageCount(int desiredImageCount) {
        this.desiredImageCount = desiredImageCount;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public OffsetDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(OffsetDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
