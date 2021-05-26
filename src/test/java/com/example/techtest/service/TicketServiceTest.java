package com.example.techtest.service;

import com.example.techtest.entity.Ticket;
import com.example.techtest.repository.ImageRepository;
import com.example.techtest.repository.TicketRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository fakeTaskRepo;

    @Mock
    private ImageRepository fakeSubtaskRepo;

    @Mock
    private TimeService fakeTimeService;

    @Captor
    ArgumentCaptor<Ticket> taskCaptor;

    private TicketService service;

    @Before
    public void setup() {
        service = new TicketService(fakeTaskRepo, fakeSubtaskRepo, fakeTimeService);
    }

    @Test
    public void pass() {
    }
}
