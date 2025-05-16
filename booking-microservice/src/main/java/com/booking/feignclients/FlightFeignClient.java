package com.booking.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.booking.exception.ResourceNotFoundException;
import com.booking.models.Flight;



@FeignClient("FLIGHT-MICROSERVICE")
public interface FlightFeignClient 
{
	@GetMapping("user/getFlightById/{flightId}")
	public Flight getFlightById(@PathVariable Long flightId);
	
	@PutMapping("user/updateSeats/{flightId}/{avaliableSeats}")
	public Flight updateFlightSeatsDuringBooking(@PathVariable Long  flightId,@PathVariable int avaliableSeats,@RequestBody List<String> seats);
	
	@PutMapping("user/FlightSeatsDuringCancel/{flightId}/{noOfSeats}")
	public void updateFlightSeatsDuringCancel(@PathVariable Long flightId,@PathVariable int noOfSeats,@RequestBody List<String> seats) throws ResourceNotFoundException;
}
