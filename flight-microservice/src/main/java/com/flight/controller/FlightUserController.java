package com.flight.controller;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.flight.exception.ResourceNotFoundException;
import com.flight.models.Flight;
import com.flight.service.FlightService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("user")
public class FlightUserController{

	@Autowired
	private FlightService flightService;
	
	@GetMapping("/getFlightById/{flightId}")
    public Flight getFlightById(@PathVariable Long flightId) 
	{
        return flightService.getFlightById(flightId);
    }
//	@GetMapping("/searchFlight/{origin}/{destination}/{departureDate}")
//    public List<Flight> searchFlights(@PathVariable String origin,
//    		 @PathVariable String destination,
//    		 @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate departureDate) 
//	{
//		LocalDateTime departureDateTime = departureDate.atStartOfDay();
//        return flightService.searchFlights(origin, destination,departureDateTime);
//    }
	
	 @GetMapping("/searchFlight/{origin}/{destination}/{departureDate}")
	    public List<Flight> searchFlights(
	            @PathVariable String origin,
	            @PathVariable String destination,
	            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate) {
	        
	        // Create the range for the whole day
	        LocalDateTime startOfDay = departureDate.atStartOfDay();
	        LocalDateTime endOfDay = departureDate.atTime(23, 59, 59);
	        
	        // Call the service method with the date range
	        return flightService.searchFlights(origin, destination, startOfDay, endOfDay);
	    }
	
	@GetMapping("/getAllFlights")
    public List<Flight> getAllFlights() 
	{
        return flightService.getFlights();
    }
	
	
	@PutMapping("/updateSeats/{flightId}/{avaliableSeats}")
	public Flight updateFlightSeatsDuringBooking(@PathVariable Long  flightId,@PathVariable int avaliableSeats, @RequestBody List<String> seats) 
	{
		return flightService.updateFlightSeatsDuringBooking(flightId, avaliableSeats,seats);
	}
	
	@PutMapping("/FlightSeatsDuringCancel/{flightId}/{noOfSeats}")
	public void updateFlightSeatsDuringCancel(@PathVariable Long flightId,@PathVariable int noOfSeats,@RequestBody List<String> seats) throws ResourceNotFoundException
	{
		flightService.updateFlightSeatsDuringCancel(flightId, noOfSeats,seats);
	}
	
	
}
