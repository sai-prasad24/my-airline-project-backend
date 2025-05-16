package com.flight.service;

import java.time.LocalDateTime;
import java.util.List;

import com.flight.exception.ResourceNotFoundException;
import com.flight.models.Flight;

public interface FlightService 
{
	public Flight getFlightById(Long flightId);
	public List<Flight> getFlights(); 
	public Flight addFlights(Flight flight);
	public Flight updateFlight(Long flightId, Flight updatedFlight);
	public String deleteFlight(Long flightId);
	//public List<Flight> searchFlights(String origin, String destination,LocalDateTime departureTime);
	public List<Flight> searchFlights(String origin, String destination, LocalDateTime startOfDay, LocalDateTime endOfDay);
	public Flight updateFlightSeatsDuringBooking(Long flightId,int avaliableSeats,List<String> seats);
	public void updateFlightSeatsDuringCancel(Long flightId, int noOfSeats,List<String> seats) throws ResourceNotFoundException;
}