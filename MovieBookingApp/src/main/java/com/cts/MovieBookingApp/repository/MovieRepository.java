package com.cts.MovieBookingApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cts.MovieBookingApp.models.Movie;

@Repository
public interface MovieRepository extends MongoRepository<Movie, Movie.MovieId>{

}
