package com.cts.MovieBookingApp.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tickets")
public class Ticket {
	
	@Id
	private int ticketId;
	private String movieName;
	private String theatherName;
	private int noOfTickets;
	private List<String> seats;
	
	public Ticket() {
		super();
	}

	public Ticket(int ticketId, String movieName, String theatherName, int noOfTickets, List<String> seats) {
		super();
		this.ticketId = ticketId;
		this.movieName = movieName;
		this.theatherName = theatherName;
		this.noOfTickets = noOfTickets;
		this.seats = seats;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getTheatherName() {
		return theatherName;
	}

	public void setTheatherName(String theatherName) {
		this.theatherName = theatherName;
	}

	public int getNoOfTickets() {
		return noOfTickets;
	}

	public void setNoOfTickets(int noOfTickets) {
		this.noOfTickets = noOfTickets;
	}

	public List<String> getSeats() {
		return seats;
	}

	public void setSeats(List<String> seats) {
		this.seats = seats;
	}
		
}