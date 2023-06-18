package com.cts.MovieBookingApp.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cts.MovieBookingApp.exception.MovieNotFoundException;
import com.cts.MovieBookingApp.models.LoginClass;
import com.cts.MovieBookingApp.models.Movie;
import com.cts.MovieBookingApp.models.User;
import com.cts.MovieBookingApp.repository.MovieRepository;
import com.cts.MovieBookingApp.repository.UserRepository;

@SpringBootTest
public class MovieServiceTest {

	@Autowired
	MovieService movieService;

	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private MovieRepository movieRepository;
	
	@Test
	@DisplayName("Checking if Movie Service class is Loading or Not")
	void movieServiceLoadingorNot() {
		assertThat(movieService).isNotNull();
	}

	@Test
	@DisplayName("Cheking loginUser Method with inputs")
	void checkLoginUserMethod() {
		List<User> users = new ArrayList<User>();
		users.add(new User(222, "Steve", "Smith", "Steve.Smith@gmail.com", "ss@123", "9567854321"));
		users.add(new User(223, "Shane", "Warne", "Shane.Warn@gmail.com", "sw@456", "9496703669"));		
		
		when(userRepository.findAll()).thenReturn(users);
		
		// CHecking with the correct credentials
		assertEquals(movieService.loginUser(new LoginClass("Shane.Warn@gmail.com", "sw@456")), true);

		//Checking with the wrong credentials
		assertEquals(movieService.loginUser(new LoginClass("Shane.Watson@gmail.com", "sw@46")), false);
	}
	
	@Test
	@DisplayName("Cheking Reset Password Method respondes correctly or not")
	void checkforgetPassword() {
		User user = new User(222, "Steve", "Smith", "Steve.Smith@gmail.com", "ss@123", "9567854321");
		
		when(userRepository.findByEmail("Steve.Smith@gmail.com")).thenReturn(user);
		
		// Checking with the correct email id
//		/ResponseEntity<String> response1 = 
//				new ResponseEntity<String>("Password Reset Successfully", HttpStatus.OK);
		assertEquals(true, movieService.forgetPassword(new LoginClass("Steve.Smith@gmail.com",
				"abc@123")));
		
		// Checking with the wring email id
		//ResponseEntity<String> resp = new ResponseEntity<String>("User Does Not Exist!", HttpStatus.OK);
		assertEquals(false, movieService.forgetPassword(new LoginClass("max.John@gmail.com", "dummy")));
	}
	
	@Test
	@DisplayName("Checking search by movie name method")
	void checkSearchByMovieName() {
		// Checking with the movie name present in the database
		List<Movie> movies = new ArrayList<Movie>();
		movies.add(new Movie(new Movie.MovieId("antman", "pvr"),150));
		movies.add(new Movie(new Movie.MovieId("antman", "inox"),200));
		
		when(movieRepository.findAll()).thenReturn(movies);
		assertEquals(movies, movieService.searchByMovieName("ant"));
		
		// Checking with the movie name not present in the database
		List<Movie> ll = new ArrayList<Movie>();
		assertEquals(movieService.searchByMovieName("pat"), ll);
	}
	
	@Test
	@DisplayName("Checking Update Ticket Count method")
	void checkUpdateTicketRemainsMethod() throws MovieNotFoundException{
		List<String> seats = new ArrayList<>();
		seats.add("J1");
		seats.add("J2");
		seats.add("J3");
		seats.add("J4");
		
		
		Movie.MovieId key = new Movie.MovieId("OMG", "PVR");
		Optional<Movie> movie = Optional.of(new Movie(key, 5));
		
		when(movieRepository.findById(key)).thenReturn(movie);
		Optional<Movie> response = movieRepository.findById(key);
		
		// checking if findbyid method work or not
		assertNotNull(response);
		
		// Check if the available tickets are less than the required tickets
		assertEquals(movieService.updateTicketRemains(key, 8), false);
		
		// check if the tickets are availbale
		assertEquals(movieService.updateTicketRemains(key, 4), true);
	}
}
