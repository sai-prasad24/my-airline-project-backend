package com.flight.controller;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flight.models.Flight;
import com.flight.service.FlightService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/admin")
public class FlightController {

	@Autowired
	private FlightService flightService;
	
	@GetMapping("/getAllFlights")
    public List<Flight> getAllFlights() 
	{
        return flightService.getFlights();
    }
	@GetMapping("/getFlightById/{flightId}")
    public Flight getFlightById(@PathVariable Long flightId) 
	{
        return flightService.getFlightById(flightId);
    }
	
	
	@PostMapping("/addFlight")
	public Flight addFlight(@RequestBody Flight flight)
	{
		return flightService.addFlights(flight);
	}

	@PutMapping("/updateFlight/{flightId}")
	public Flight updateFlight(@PathVariable Long flightId, @RequestBody Flight updatedFlight)
	{
		return flightService.updateFlight(flightId, updatedFlight);
	}

	@DeleteMapping("/deleteFlight/{flightId}")
	public String deleteFlight(@PathVariable Long flightId)
	{
		return flightService.deleteFlight(flightId);
	}
}
