package com.cts.MovieBookingApp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cts.MovieBookingApp.exception.MovieNotFoundException;
import com.cts.MovieBookingApp.models.LoginClass;
import com.cts.MovieBookingApp.models.Movie;
import com.cts.MovieBookingApp.models.Ticket;
import com.cts.MovieBookingApp.models.User;
import com.cts.MovieBookingApp.repository.MovieRepository;
import com.cts.MovieBookingApp.repository.UserRepository;
import com.cts.MovieBookingApp.services.MovieService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@WebAppConfiguration
@TestInstance(Lifecycle.PER_CLASS)
public class ControllerTest {

	protected MockMvc mvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean
	private MovieService service;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private MovieRepository movieRepository;

	protected void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@BeforeAll
	public void before() {
		setUp();
	}

	protected String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	@Test
	@DisplayName("Testing Register User Controller")
	void testRegisterUser() throws Exception {
		User user = new User(2116452, "Rohit", "Sharma", "Rohit.Sharma@gmail.com", "rs@456", "9876344452");
		String url = "/register";
		String body = mapToJson(user);
		when(userRepository.save(user)).thenReturn(user);
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(body))
				.andReturn();

		String response = mvcResult.getResponse().getContentAsString();
		assertEquals("User Registered Successfully", response);

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	@DisplayName("Testing Login User Controller")
	void testLoginUser() throws Exception {
		String url = "/login";
		LoginClass loginClass = new LoginClass("amit.mishra@gmail.com", "amit");
		String body = mapToJson(loginClass);
		User user = new User(111, "Amit", "Mishra", "amit.mishra@gmail.com", "amit", "1111111111");
		when(service.loginUser(Mockito.any(LoginClass.class))).thenReturn(user);
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(body))
				.andReturn();

		assertEquals(mvcResult.getResponse().getStatus(), 200);
	}

	@Test
	@DisplayName("Testing Forget Password Controller")
	void testForgetPassword() throws Exception {
		String url = "/forgotPassword";
		LoginClass loginClass = new LoginClass("amit.mishra@gmail.com", "mishra");
		String body = mapToJson(loginClass);

		// Checking when the user name is not matched
		when(service.forgetPassword(Mockito.any(LoginClass.class))).thenReturn(false);
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(body))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String msg = mvcResult.getResponse().getContentAsString();
		assertEquals("Enter correct Email Id", msg);

		when(service.forgetPassword(Mockito.any(LoginClass.class))).thenReturn(true);
		mvcResult = mvc
				.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(body))
				.andReturn();
		String response = mvcResult.getResponse().getContentAsString();
		assertEquals("Password Reset Successfully", response);
	}

	@Test
	@DisplayName("Testing Get All Movies Controller")
	void testGetAllMovies() throws Exception {
		String url = "/all";
		List<Movie> movies = new ArrayList<>();
		Movie m1 = new Movie(new Movie.MovieId("Pushpa", "PVR"), 100);
		Movie m2 = new Movie(new Movie.MovieId("Antman", "INOX"), 200);
		movies.add(m1);
		movies.add(m2);
		when(movieRepository.findAll()).thenReturn(movies);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		String list = mvcResult.getResponse().getContentAsString();
		Movie[] ll = mapFromJson(list, Movie[].class);
		assertEquals(2, ll.length);

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	@DisplayName("Testing Search By Movie Name Controller")
	void testSearchByMovieName() throws Exception {
		String url = "/movies/search/Pus";
		List<Movie> movies = new ArrayList<>();
		movies.add(new Movie(new Movie.MovieId("Pushpa", "PVR"), 100));
		when(service.searchByMovieName(Mockito.anyString())).thenReturn(movies);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		String list = mvcResult.getResponse().getContentAsString();
		Movie[] ll = mapFromJson(list, Movie[].class);
		assertEquals(1, ll.length);

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	@DisplayName("Testing Add Movie Controller")
	void testAddMovieController() throws Exception {
		String url = "/movie/add";
		Movie movie = new Movie(new Movie.MovieId("avenger", "pvr"), 250);
		String body = mapToJson(movie);
		when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(movie);
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(body))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		  String response = mvcResult.getResponse().getContentAsString();
		  System.out.println(response); 
		  Movie result = mapFromJson(response,Movie.class); 
		  assertEquals(movie.getId().getMovieName(),
		  result.getId().getMovieName());
	}
	
	@Test
	@DisplayName("Testig Book Ticket Controller")
	void testBookTicket() throws MovieNotFoundException, Exception{
		String url = "/bookTickets";
		List<String> seats = new ArrayList<>();
		seats.add("J1");
		seats.add("J2");
		seats.add("J3");
		Ticket ticket = new Ticket(678, "Avengers", "PVR", 3, seats);
//		Movie.MovieId key = new Movie.MovieId("Avengers", "PVR");
		String body = mapToJson(ticket);
		when(service.updateTicketRemains(Mockito.any(Movie.MovieId.class), Mockito.anyInt())).thenReturn(true);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(body)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String response = mvcResult.getResponse().getContentAsString();
		
		assertEquals(200, status);
		assertEquals("Ticket Booked Successfully", response);
	}
	
	@Test
	@DisplayName("Testing Delete Movie Controller")
	void testDeleteMovie() throws Exception{
		String url = "/RRR/delete/PVR";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(url).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		
		String msg = mvcResult.getResponse().getContentAsString();
		
		assertEquals(200, status);
		assertEquals("", msg);
	}
}