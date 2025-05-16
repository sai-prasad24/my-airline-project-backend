package com.booking.serviceimpl;


import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.exception.FlightNotFoundException;
import com.booking.exception.InsufficientSeatsException;
import com.booking.exception.ResourceNotFoundException;
import com.booking.exception.SeatAlreadyBookedException;
import com.booking.exception.UserNameNotFoundException;
import com.booking.feignclients.FlightFeignClient;
import com.booking.feignclients.UserFeignClient;
import com.booking.models.Bookings;
import com.booking.models.Flight;
import com.booking.models.User;
import com.booking.repository.BookingsRepository;
import com.booking.service.BookingsService;


@Service
public class BookingsServiceImpl implements BookingsService
{
	
	@Autowired
	private BookingsRepository bookingsRepository;
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	@Autowired
	private FlightFeignClient flightFeignClient;

//	@Override
//	public Bookings bookTicket(String userName, Long flightId, int noOfSeats,List<String> selectedSeats)
//			throws FlightNotFoundException, UserNameNotFoundException,InsufficientSeatsException, SeatAlreadyBookedException
//	{
//		User user=userFeignClient.getUserByUserName(userName);
//		if(user==null)
//		{
//			throw new UserNameNotFoundException();
//		}
//		
//		Flight flight=flightFeignClient.getFlightById(flightId);
//		if(flight==null)
//		{
//			throw new FlightNotFoundException();
//		}
//		
//		if(flight.getAvailableSeats() < noOfSeats) 
//		{
//            throw new InsufficientSeatsException("Not enough seats available");
//        }
//		
//		
//		for(String seat : selectedSeats)
//		{
//            if (flight.getBookedSeats().contains(seat))
//            {
//                throw new SeatAlreadyBookedException("Seat " + seat + " is already booked");
//            }
//        }
//		
//		flight.getBookedSeats().addAll(selectedSeats);
//		flightFeignClient.updateFlightSeatsDuringBooking(flightId, noOfSeats);
//		
//		Bookings bookings=new Bookings();
//		double amount=flight.getPrice()*noOfSeats;
//		
//		bookings.setUserName(userName);
//		bookings.setFlightId(flightId);
//		bookings.setNoOfSeats(noOfSeats);
//		bookings.setAmount(amount);
//		bookings.setAirline(flight.getAirline());
//		bookings.setDestination(flight.getDestination());
//		bookings.setOrigin(flight.getOrigin());
//		
//
//		return bookingsRepository.save(bookings);
//		
//	}

	@Override
	public Bookings bookTicket(String userName, Long flightId, int noOfSeats, List<String> selectedSeats)
	        throws FlightNotFoundException, UserNameNotFoundException, InsufficientSeatsException, SeatAlreadyBookedException {

	    User user = userFeignClient.getUserByUserName(userName);
	    if (user == null) {
	        throw new UserNameNotFoundException();
	    }

	    Flight flight = flightFeignClient.getFlightById(flightId);
	    if (flight == null) {
	        throw new FlightNotFoundException();
	    }

	    // Initialize bookedSeats if null
	    if (flight.getBookedSeats() == null) {
	        flight.setBookedSeats(new HashSet<>());
	    }

	    if (flight.getAvailableSeats() < noOfSeats) {
	        throw new InsufficientSeatsException("Not enough seats available");
	    }

	    for (String seat : selectedSeats) {
	        if (flight.getBookedSeats().contains(seat)) {
	            throw new SeatAlreadyBookedException("Seat " + seat + " is already booked");
	        }
	    }

	    // Update booked seats and available seats
	    flight.getBookedSeats().addAll(selectedSeats);
	    flightFeignClient.updateFlightSeatsDuringBooking(flightId, noOfSeats,selectedSeats);

	    Bookings bookings = new Bookings();
	    double amount = flight.getPrice() * noOfSeats;

	    bookings.setUserName(userName);
	    bookings.setFlightId(flightId);
	    bookings.setNoOfSeats(noOfSeats);
	    bookings.setAmount(amount);
	    bookings.setAirline(flight.getAirline());
	    bookings.setDepartureTime(flight.getDepartureTime());
	    bookings.setArrivalTime(flight.getArrivalTime());
	    bookings.setDestination(flight.getDestination());
	    bookings.setOrigin(flight.getOrigin());
	    bookings.setBookedSeats(selectedSeats); // set bookedSeats in bookings

	    return bookingsRepository.save(bookings);
	}

	@Override
	public Bookings cancelTicket(Long bookingId) throws ResourceNotFoundException 
	{
        Bookings bookings = bookingsRepository.findById(bookingId).orElseThrow();
        //flightFeignClient.updateFlightSeatsDu(bookings.getFlightId(), bookings.getNoOfSeats());
        flightFeignClient.updateFlightSeatsDuringCancel(bookings.getFlightId(),bookings.getNoOfSeats(),bookings.getBookedSeats());
        bookingsRepository.delete(bookings);
        return bookings;
    }



	@Override
	public List<Bookings> getAllBookingsByUserName(String userName) 
	{
		List<Bookings> getAllBookingsList=bookingsRepository.findAllByUserName(userName);
		return getAllBookingsList;
	}

	@Override
	public List<Bookings> getAllBookings() 
	{
		List<Bookings> getAllBookingsList=bookingsRepository.findAll();
		return getAllBookingsList;
	}
	
}
