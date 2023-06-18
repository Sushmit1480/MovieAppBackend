package com.cts.MovieBookingApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cts.MovieBookingApp.models.Ticket;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, Integer>{

}
