package com.cts.MovieBookingApp.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TicketTest {
	
	Ticket ticket1 = new Ticket();
	
	@Test
	@DisplayName("Cheking if Ticket Class Loading or Not.")
	void ticketIsLoadingOrNot() {
		assertThat(ticket1).isNotNull();
	}
	
	@Test
	@DisplayName("Checking if Ticket Class Constructor is Responding correctly or not")
	void testTicketClass() {
		List<String> seat = new ArrayList<>();
		seat.add("A1");
		seat.add("B1");
		seat.add("C3");
		Ticket ticket = new Ticket(111, "Pushpa", "PVR", 3, seat);
		assertEquals(111, ticket.getTicketId());
		assertEquals("Pushpa",ticket.getMovieName());
		assertEquals("PVR",ticket.getTheatherName());
		assertEquals(3, ticket.getNoOfTickets());
		assertEquals(seat, ticket.getSeats());
	}
	
	@Test
	@DisplayName("Checking if Ticket Class Getter and Setter is Responding correctly or not")
	void testGetterSetterOfTicketClass() {
		Ticket ticket = new Ticket();
		
		List<String> seat = new ArrayList<>();
		seat.add("A1");
		seat.add("B1");
		seat.add("C3");
		
		ticket.setTicketId(111);
		ticket.setMovieName("RRR");
		ticket.setTheatherName("PVR");
		ticket.setNoOfTickets(3);
		ticket.setSeats(seat);
		
		assertEquals(111, ticket.getTicketId());
		assertEquals("RRR",ticket.getMovieName());
		assertEquals("PVR",ticket.getTheatherName());
		assertEquals(3, ticket.getNoOfTickets());
		assertEquals(seat, ticket.getSeats());
	}
}
