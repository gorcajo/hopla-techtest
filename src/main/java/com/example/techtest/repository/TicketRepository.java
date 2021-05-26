package com.example.techtest.repository;

import com.example.techtest.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long> {

    Page<Ticket> findAllByCompleted(boolean completed, Pageable pageable);

    Page<Ticket> findAllByCreatedOnBetween(OffsetDateTime from, OffsetDateTime to, Pageable pageable);

    Page<Ticket> findAllByCompletedAndCreatedOnBetween(boolean completed, OffsetDateTime from, OffsetDateTime to, Pageable pageable);
}
