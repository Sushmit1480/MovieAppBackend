package com.cts.MovieBookingApp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "movies")
public class Movie {
	
	public static class MovieId{
		private String movieName;
		private String theatherName;
		
		public MovieId() {
			super();
		}

		public MovieId(String movieName, String theatherName) {
			super();
			this.movieName = movieName;
			this.theatherName = theatherName;
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
			
	}
	
	@Id
	private MovieId id;
	private int allotedSeats;
	
	public Movie() {
		super();
	}

	public Movie(MovieId id, int allotedSeats) {
		super();
		this.id = id;
		this.allotedSeats = allotedSeats;
	}

	public MovieId getId() {
		return id;
	}

	public void setId(MovieId id) {
		this.id = id;
	}

	public int getAllotedSeats() {
		return allotedSeats;
	}

	public void setAllotedSeats(int allotedSeats) {
		this.allotedSeats = allotedSeats;
	}	
}
