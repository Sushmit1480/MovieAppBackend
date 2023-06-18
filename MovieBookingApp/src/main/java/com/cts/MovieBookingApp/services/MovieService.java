package com.cts.MovieBookingApp.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.MovieBookingApp.exception.MovieNotFoundException;
import com.cts.MovieBookingApp.models.LoginClass;
import com.cts.MovieBookingApp.models.Movie;
import com.cts.MovieBookingApp.models.User;
//import com.cts.MovieBookingApp.models.User;

@Service
public interface MovieService {
	public User loginUser(LoginClass loginClass);
	public boolean forgetPassword(LoginClass loginClass);
	public List<Movie> searchByMovieName(String name);
	public boolean updateTicketRemains(Movie.MovieId key, int noOfTickets) throws MovieNotFoundException;
	public ResponseEntity<?> deleteMovie(String movieName, String theaterName);
}
