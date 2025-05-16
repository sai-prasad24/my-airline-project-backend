package com.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.exception.FlightNotFoundException;
import com.booking.exception.InsufficientSeatsException;
import com.booking.exception.ResourceNotFoundException;
import com.booking.exception.SeatAlreadyBookedException;
import com.booking.exception.UserNameNotFoundException;
import com.booking.models.Bookings;
import com.booking.service.BookingsService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/book")
public class BookingsController 
{
	@Autowired
	private BookingsService bookingsService;
	
	
	@PostMapping("/bookings/{userName}/{flightId}/{noOfSeats}")
	public Bookings bookTicket(@PathVariable String userName,@PathVariable Long flightId,@PathVariable int noOfSeats, @RequestBody List<String> selectedSeats)
			throws FlightNotFoundException, UserNameNotFoundException,InsufficientSeatsException, SeatAlreadyBookedException
	{
		return bookingsService.bookTicket(userName, flightId, noOfSeats, selectedSeats);
	}
	
	@DeleteMapping("/cancelTicket/{bookingId}")
	public Bookings cancelTicket(@PathVariable Long bookingId) throws ResourceNotFoundException
	{
		return bookingsService.cancelTicket(bookingId);
	}
	
	@GetMapping("/getAllBookingsByUserName/{userName}")
	public List<Bookings> getAllBookingsByUserName(@PathVariable String userName) 
	{
		return bookingsService.getAllBookingsByUserName(userName);
	}
	
	@GetMapping("/getAllBookings")
	public List<Bookings> getAllBookings()
	{
		List<Bookings> getAllBookingList=bookingsService.getAllBookings();
		return getAllBookingList;
	}
	

	
   
	
	
}
