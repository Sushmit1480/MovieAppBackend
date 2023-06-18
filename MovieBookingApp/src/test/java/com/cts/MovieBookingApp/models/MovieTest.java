package com.cts.MovieBookingApp.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MovieTest {

	Movie movie1 = new Movie();
	
	@Test
	@DisplayName("Cheking If Movie Class Loading or Not")
	void movieIsLoadingOrNot() {
		assertThat(movie1).isNotNull();
	}
	
	@Test
	@DisplayName("Cheking If Movie Class Constructor Responding Correctly or Not")
	void testingMovieClassConstructor() {
		Movie movie = new Movie(new Movie.MovieId("Pushpa", "PVR"), 200);
		assertEquals("Pushpa", movie.getId().getMovieName());
		assertEquals("PVR", movie.getId().getTheatherName());
		assertEquals(200, movie.getAllotedSeats());
	}
	
	@Test
	@DisplayName("Cheking If Movie Class Constructor Responding Correctly or Not")
	void testMovieClassGetterSetter() {
		Movie movie = new Movie();
		movie.setId(new Movie.MovieId("RRR", "INOX"));
		movie.setAllotedSeats(200);
		
		assertEquals("RRR", movie.getId().getMovieName());
		assertEquals("INOX", movie.getId().getTheatherName());
		assertEquals(200, movie.getAllotedSeats());
	}
}
