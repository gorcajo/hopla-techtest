package com.example.techtest.service;

import com.example.techtest.entity.Image;
import com.example.techtest.entity.Ticket;
import com.example.techtest.exception.NotFoundException;
import com.example.techtest.repository.ImageRepository;
import com.example.techtest.repository.TicketRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository fakeTicketRepo;

    @Mock
    private ImageRepository fakeImageRepo;

    @Mock
    private TimeService fakeTimeService;

    @Captor
    ArgumentCaptor<Ticket> ticketCaptor;

    private TicketService service;

    @Before
    public void setup() {
        service = new TicketService(fakeTicketRepo, fakeImageRepo, fakeTimeService);
    }

    @Test
    public void create_task() {
        // arrange

        var desiredImageCount = 3;

        var now = OffsetDateTime.now();

        when(fakeTicketRepo.save(any(Ticket.class)))
            .thenReturn(new Ticket(
                    1L,
                    3,
                    false,
                    now,
                    List.of()));

        when(fakeTimeService.getCurrentOffsetDateTime()).thenReturn(now);

        // act

        var ticket = service.createTicket(desiredImageCount);

        // assert

        assertThat(ticket.getId(), is(1L));
        assertThat(ticket.getDesiredImageCount(), is(3));
        assertThat(ticket.getCompleted(), is(Boolean.FALSE));
        assertThat(ticket.getCreatedOn(), is(now));
    }

    @Test
    public void retrieve_task() {
        // arrange

        var now = OffsetDateTime.now();

        when(fakeTicketRepo.findById(1L)).thenReturn(
                Optional.of(new Ticket(
                        1L,
                        3,
                        false,
                        now,
                        List.of(new Image(), new Image()))));

        // act

        var ticket = service.retrieveTicket(1L);

        // assert

        assertThat(ticket.getId(), is(1L));
        assertThat(ticket.getDesiredImageCount(), is(3));
        assertThat(ticket.getCompleted(), is(Boolean.FALSE));
        assertThat(ticket.getCreatedOn(), is(now));
        assertThat(ticket.getImages().size(), is(2));
    }

    @Test(expected = NotFoundException.class)
    public void retrieve_inexistent_task() {
        // arrange

        when(fakeTicketRepo.findById(1L)).thenReturn(Optional.empty());

        // act

        var ticket = service.retrieveTicket(1L);

        // assert
    }

    @Test
    public void list_tickets() {
        // arrange

        when(fakeTicketRepo.findById(1L)).thenReturn(Optional.empty());

        // act

        var ticket = service.retrieveTicket(1L);

        // assert
    }
}
