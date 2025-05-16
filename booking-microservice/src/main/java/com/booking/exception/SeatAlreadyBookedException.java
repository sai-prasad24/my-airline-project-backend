package com.booking.exception;

public class SeatAlreadyBookedException extends Exception
{
	public SeatAlreadyBookedException(String msg)
	{
		super(msg);
	}
}
