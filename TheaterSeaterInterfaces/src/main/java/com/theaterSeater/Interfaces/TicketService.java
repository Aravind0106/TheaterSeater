package com.theaterSeater.Interfaces;

import com.seater.theater.exception.TicketServiceException;
import com.seater.theater.json.SeatHold;

/**
 * This Service Interface provides 3 methods
 * @author aravind
 *
 */
public interface TicketService {
	/**
	 * 
	 * @param venueLevel : The level which the user is expecting to see the seats
	 * @return number of seats in the provided level
	 * @throws TicketServiceException
	 */
	public int numSeatsAvailable(int venueLevel) throws TicketServiceException; 
		

	/**
	 * 
	 * @param numSeats : Numer of seats user wants to hold
	 * @param minLevel : The Best/highest leevl user wants to hold
	 * @param maxLevel : THe least level user ever want to hold
	 * @param customerEmail : Customer Email to which the seat is held, is also assigned to each seat
	 * @return THis returns an oject seat hold, in which all the seats reserved are put, and a unique hold code assigned to it
	 * @throws TicketServiceException
	 */
	public SeatHold findAndHoldSeats(int numSeats, int minLevel, int maxLevel,
			String customerEmail) throws TicketServiceException;  
	/**
	 * 
	 * @param seatHoldId : the holdcode findAndHoldSeats responds with, will be used to reserve
	 * @param customerEmail : the email to which the seats are held.
	 * @return Returs confirmation code once they are reserved
	 * @throws TicketServiceException
	 */
	public String reserveSeats( String seatHoldId, String customerEmail) throws TicketServiceException;
		
}
