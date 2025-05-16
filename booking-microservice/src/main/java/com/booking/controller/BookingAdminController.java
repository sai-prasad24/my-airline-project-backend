package com.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.models.Bookings;
import com.booking.service.BookingsService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("admin/book")
public class BookingAdminController 
{
	@Autowired
	private BookingsService bookingsService;
	
	@GetMapping("/getAllBookings")
	public List<Bookings> getAllBookings()
	{
		List<Bookings> getAllBookingList=bookingsService.getAllBookings();
		return getAllBookingList;
	}
}
