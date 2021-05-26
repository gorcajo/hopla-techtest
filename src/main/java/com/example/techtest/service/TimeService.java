package com.example.techtest.service;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class TimeService {

    public OffsetDateTime getCurrentOffsetDateTime() {
        return OffsetDateTime.now();
    }
}
