package com.flight.serviceimpl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flight.exception.ResourceNotFoundException;
import com.flight.models.Flight;
import com.flight.repository.FlightRespository;
import com.flight.service.FlightService;


@Service
public class FlightServiceImpl implements FlightService
{

	@Autowired
	private FlightRespository flightRespository;
	
	@Override
	public Flight getFlightById(Long flightId)
	{
		Flight flight=flightRespository.findById(flightId).get();
		return flight;
	}

	@Override
	public List<Flight> getFlights()
	{
		List<Flight> flightList=flightRespository.findAll();
		return flightList;
	}

	@Override
	public Flight addFlights(Flight flight)
	{
		Flight savedFlight=flightRespository.save(flight);
		return savedFlight;
	}

	@Override
	public Flight updateFlight(Long flightId, Flight updatedFlight)
	{
		Flight flight=flightRespository.findById(flightId).orElseThrow();
		flight.setAirline(updatedFlight.getAirline());
		flight.setOrigin(updatedFlight.getOrigin());
		flight.setDestination(updatedFlight.getDestination());
		flight.setArrivalTime(updatedFlight.getArrivalTime());
		flight.setDepartureTime(updatedFlight.getDepartureTime());
		flight.setAvailableSeats(updatedFlight.getAvailableSeats());
		flight.setPrice(updatedFlight.getPrice());
		return flightRespository.save(flight);
				
	}

	@Override
	public String deleteFlight(Long flightId) 
	{
		Flight flight=flightRespository.findById(flightId).orElseThrow();
		flightRespository.delete(flight);
		return "Sucessfully filght is deleted";
	}


	@Override
	public List<Flight> searchFlights(String origin, String destination, LocalDateTime startOfDay, LocalDateTime endOfDay) {
	    return flightRespository.findFlightsByOriginAndDestinationAndDepartureTimeBetween(
	        origin, destination, startOfDay, endOfDay);
	}


	@Override
	public Flight updateFlightSeatsDuringBooking(Long  flightId, int avaliableSeats,List<String> seats)
	{
		Flight flight=flightRespository.findById(flightId).orElseThrow();
		int totalSeats=flight.getAvailableSeats()-avaliableSeats;
		flight.setAvailableSeats(totalSeats);
		Set<String> set = flight.getBookedSeats();
		for (String string : seats) 
		{
			set.add(string);
			
		}
		flight.setBookedSeats(set);
		
		return flightRespository.save(flight);
	}
	
	@Override
	public void updateFlightSeatsDuringCancel(Long flightId, int noOfSeats, List<String> seats) throws ResourceNotFoundException {
	    Flight flight = flightRespository.findById(flightId)
	        .orElseThrow(() -> new ResourceNotFoundException("Flight Not Found"));
	    
	   
	    flight.setAvailableSeats(flight.getAvailableSeats() + noOfSeats);
	    
	   
	    Set<String> bookedSeats = flight.getBookedSeats();
	    for (String seat : seats) {
	        bookedSeats.remove(seat); // Remove the canceled seat
	    }
	    flight.setBookedSeats(bookedSeats);

	   
	    flightRespository.save(flight);
	}


}
