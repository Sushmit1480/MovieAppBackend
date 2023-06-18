package com.cts.MovieBookingApp.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.MovieBookingApp.exception.MovieNotFoundException;
import com.cts.MovieBookingApp.models.LoginClass;
import com.cts.MovieBookingApp.models.Movie;
import com.cts.MovieBookingApp.models.Ticket;
import com.cts.MovieBookingApp.models.User;
import com.cts.MovieBookingApp.repository.MovieRepository;
import com.cts.MovieBookingApp.repository.TicketRepository;
import com.cts.MovieBookingApp.repository.UserRepository;
import com.cts.MovieBookingApp.services.MovieService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("unused")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/v1.0/moviebooking")
public class Controller {
	
	private static final Logger log = LoggerFactory.getLogger(Controller.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private MovieService movieService;
	
	 @Autowired
	 private KafkaTemplate<String, Object> kafkaTemplate;

	 @Autowired
	 private NewTopic topic;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user){
		log.info("Inside register user method");
		Random rand = new Random();
		int rand_int = 100000 + rand.nextInt(900000);
		user.setLoginId(rand_int);
		userRepository.save(user);
		log.info("Exiting register user method");
		return new ResponseEntity<String>("User Registered Successfully", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginClass loginClass) {
		log.info("Inside login method");
		User user = movieService.loginUser(loginClass);
		if(user != null) {
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("User not found", HttpStatus.OK);
	}
	
	@PostMapping("/forgotPassword")
	public ResponseEntity<String> forgotPassword(@RequestBody LoginClass loginClass) {
		log.info("Inside Password Rest Method");
		boolean result = movieService.forgetPassword(loginClass);
		if(result) {
			return new ResponseEntity<String>("Password Reset Successfully", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Enter correct Email Id", HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Movie>> getAllMovies(){
		log.info("Inside Get All Movies Method");
		List<Movie> movies = movieRepository.findAll();
		log.info("Exiting Get All Movies Method");
		return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
	}
	
	@GetMapping("/movies/search/{movieName}")
	public ResponseEntity<?> searchMovie(@PathVariable("movieName") String movieName){
		log.info("Inside Search Movie Method");
		List<Movie> movies = movieService.searchByMovieName(movieName);
		if(movies.size() != 0) {
			log.info("Exiting Search Movie Method");
			return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
		}
		log.info("Exiting Search Movie Method");
		return new ResponseEntity<String>("No Movie Found", HttpStatus.OK);
	}
	
	@PostMapping("/movie/add")
	public ResponseEntity<?> addMovie(@RequestBody Movie movie){
		log.info("Inside Add Movie Method");
		movie.setId(new Movie.MovieId(movie.getId().getMovieName().toLowerCase(), movie.getId().getTheatherName().toLowerCase()));
		movieRepository.save(movie);
		kafkaTemplate.send(topic.name(),"Movie added by admin");
		log.info("Exiting Add Movie Method");
		return new ResponseEntity<Movie>(movie, HttpStatus.OK);
	}
	
	@DeleteMapping("/{movieName}/delete/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable("movieName") String movieName, @PathVariable("id") String theaterName){
		log.info("Inside Delete Movie Method");
		log.info("Exiting Delete Movie Method");
		 kafkaTemplate.send(topic.name(),"Movie Deleted by the Admin. "+movieName+" is now not available");
		return movieService.deleteMovie(movieName, theaterName);
	}
	
	@PostMapping("/bookTickets")
	public ResponseEntity<String> ticketBooking(@RequestBody Ticket ticket) throws MovieNotFoundException{
		log.info("Inside Ticket Booking App");
		Movie.MovieId key = new Movie.MovieId(ticket.getMovieName(), ticket.getTheatherName());
		if(movieService.updateTicketRemains(key, ticket.getNoOfTickets())) {
			Random rand = new Random();
			int randint = 100000 + rand.nextInt(900000);
			ticket.setTicketId(randint);
			ticketRepository.save(ticket);
			 kafkaTemplate.send(topic.name(),"ticket booked successfully by user.");
			log.info("Exiting Ticket Booking App");
			return new ResponseEntity<String>("Ticket Booked Successfully", HttpStatus.OK);
		}
		log.info("Exiting Ticket Booking App");
		return new ResponseEntity<String>("Tickets Unavailable", HttpStatus.OK);
	}

}