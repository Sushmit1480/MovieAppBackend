package com.cts.MovieBookingApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cts.MovieBookingApp.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, Integer>{
	public User findByEmail(String email);
}
