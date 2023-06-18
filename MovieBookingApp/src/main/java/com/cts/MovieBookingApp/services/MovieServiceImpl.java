package com.cts.MovieBookingApp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.MovieBookingApp.exception.MovieNotFoundException;
import com.cts.MovieBookingApp.models.LoginClass;
import com.cts.MovieBookingApp.models.Movie;
import com.cts.MovieBookingApp.models.Movie.MovieId;
import com.cts.MovieBookingApp.models.User;
import com.cts.MovieBookingApp.repository.MovieRepository;
import com.cts.MovieBookingApp.repository.UserRepository;

@Service
public class MovieServiceImpl implements MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User loginUser(LoginClass loginClass) {
		List<User> users = userRepository.findAll();
		for (User user : users) {
			if(user.getEmail().equals(loginClass.getEmail()) && 
					user.getPassword().equals(loginClass.getPassword())) {
				return user;
			}
		}
		return null;
	}
	
	@Override
	public boolean forgetPassword(LoginClass loginClass) {
		User user = userRepository.findByEmail(loginClass.getEmail());
		if(user!=null) {
			user.setPassword(loginClass.getPassword());
			userRepository.save(user);
			return true;
		}
		return false;
	}


	@Override
	public List<Movie> searchByMovieName(String name) {
		//String movieName = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
		List<Movie> movies = movieRepository.findAll();
		String movieName = name.toLowerCase();
		List<Movie> moviesByName = movies.stream().filter((movie) -> 
		movie.getId().getMovieName().contains(movieName)).collect(Collectors.toList());
		return moviesByName;
	}

	@Override
	public ResponseEntity<?> deleteMovie(String movieName, String theaterName) {
		movieRepository.deleteById(new Movie.MovieId(movieName, theaterName));
		return new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
	}

	@Override
	public boolean updateTicketRemains(MovieId key, int noOfTickets) throws MovieNotFoundException {
		Optional<Movie> movie = movieRepository.findById(key);
		if(!movie.isPresent()) {
			throw new MovieNotFoundException("Movie Not Found");
		}
		
		if(noOfTickets < movie.get().getAllotedSeats()) {
			int count = movie.get().getAllotedSeats() - noOfTickets;
			movie.get().setAllotedSeats(count);
			movieRepository.save(movie.get());
			return true;
		}
		return false;
	}

	

}
