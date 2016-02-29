package com.seater.theater.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seater.theater.exception.TicketServiceException;
import com.seater.theater.json.SeatHold;
import com.seater.theater.util.TicketServiceHelper;
import com.theaterSeater.Interfaces.TheaterSeaterDataService;
import com.theaterSeater.Interfaces.TicketService;

/**
 * This Service impl provides 3 methods
 * @author aravind
 */
@Component
public class TheaterSeaterServiceImpl implements TicketService{

	final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TicketServiceHelper.class);
	@Autowired
	TheaterSeaterDataService dataService;
	/**
	 * @param venueLevel : The level which the user is expecting to see the seats
	 * @return number of seats in the provided level
	 * @throws TicketServiceException
	 */
	public int numSeatsAvailable(int venueLevel) throws TicketServiceException {
			logger.info("Performing numSeatsAvailable function for level : " + venueLevel);
			return TicketServiceHelper.numOfSeatsAvailable(venueLevel, dataService.getAuditoriumDetails());
	}
	/**
	 * @param numSeats : Number of seats user wants to hold
	 * @param minLevel : The Best/highest level user wants to hold
	 * @param maxLevel : THe least level user ever want to hold
	 * @param customerEmail : Customer Email to which the seat is held, is also assigned to each seat
	 * @return THis returns an object seat hold, in which all the seats reserved are put, and a unique hold code assigned to it
	 * @throws TicketServiceException
	 */
	public SeatHold findAndHoldSeats(int numSeats, int minLevel, int maxLevel,
			String customerEmail) throws TicketServiceException {
		logger.info("Performing findAndHoldSeats function for customer :" + customerEmail);
		return TicketServiceHelper.findAndHoldSeats(numSeats, minLevel, maxLevel, customerEmail, dataService.getAuditoriumDetails());
	}
	/**
	 * @param seatHoldId : the holdcode findAndHoldSeats responds with, will be used to reserve
	 * @param customerEmail : the email to which the seats are held.
	 * @return Returns confirmation code once they are reserved
	 * @throws TicketServiceException
	 */
	public String reserveSeats(String seatHoldId, String customerEmail) throws TicketServiceException {
		logger.info("Performing reserveSeats function for seatHoldId :"+ seatHoldId);
		String confCode = TicketServiceHelper.reserveSeats(seatHoldId, customerEmail, dataService.getAuditoriumDetails());
		/*Email service can be used to send email to the customer  */
		//EmailSender.sendEmail(customerEmail, confCode);
		return confCode; 
	}

}
