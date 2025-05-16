package com.booking.service;

import java.util.List;

import com.booking.exception.FlightNotFoundException;
import com.booking.exception.InsufficientSeatsException;
import com.booking.exception.ResourceNotFoundException;
import com.booking.exception.SeatAlreadyBookedException;
import com.booking.exception.UserNameNotFoundException;
import com.booking.models.Bookings;

public interface BookingsService 
{
	public Bookings bookTicket(String userName, Long flightId, int noOfSeats,List<String> selectedSeats) throws FlightNotFoundException,
	UserNameNotFoundException,InsufficientSeatsException,SeatAlreadyBookedException;
	public Bookings cancelTicket(Long bookingId)throws ResourceNotFoundException ;
	
	
	//public List<Bookings> getAllBookingsByUserId(Long userId);//
	
	
	public List<Bookings> getAllBookingsByUserName(String userName);//for user
	
	//public Bookings getBookingByBookingId(Long bookingId);//admin
	public List<Bookings> getAllBookings();// for admin
	
	
	
}
