package com.flight.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flight.models.Flight;

public interface FlightRespository extends JpaRepository<Flight, Long>
{
	//List<Flight> findByOriginAndDestinationAndDepartureTime(String origin, String destination,LocalDateTime departureTime);
	List<Flight> findFlightsByOriginAndDestinationAndDepartureTimeBetween(
		    String origin, String destination, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
