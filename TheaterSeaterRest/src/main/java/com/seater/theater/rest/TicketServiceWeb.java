package com.seater.theater.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seater.theater.exception.TicketServiceException;
import com.seater.theater.json.ReserveSeatInput;
import com.seater.theater.json.ReserveSeatOutput;
import com.seater.theater.json.SeatHold;
import com.seater.theater.json.SeatHoldInput;
import com.seater.theater.json.SeatsAvailOutput;
import com.theaterSeater.Interfaces.TicketService;

/**
 * A Rest API class that has path /ticketService to which one of the below paths should be appended to do get or Post operations.
 * getNumOfSeatsAvail -->Get Operation to get number of seats
 * holdSeatsinGivenLevels-->Post operation that posts the seats to be held.
 * reserveSeat-->This reserves the seats that are already held
 * @author aravind
 *
 */
@Component
@Path("/ticketService")
public class TicketServiceWeb {

  @Autowired
  TicketService ticketService;
  
  
  @GET
  @Path("/numOfSeatsAvailOnLevel/{levelNum}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getNumOfSeatsAvail(@PathParam("levelNum") int levelNum) throws TicketServiceException {
	  System.out.println("NumAVail Rest");
	  SeatsAvailOutput availOutput= new SeatsAvailOutput();
	  availOutput.numOfAvailSeats= ticketService.numSeatsAvailable(levelNum);
	  return Response.status(Status.OK).entity(availOutput).build();
	}
  
  @POST
  @Path("/holdSeats")
    @Produces(MediaType.APPLICATION_JSON)
	public Response holdSeatsinGivenLevels(String seatHoldInputString) throws JsonParseException, JsonMappingException, IOException, TicketServiceException {
	  System.out.println("SeatHold Rest");
	  ObjectMapper mapper = new ObjectMapper();
	  SeatHoldInput seatHoldInput= mapper.readValue(seatHoldInputString, SeatHoldInput.class);
	  SeatHold seatHold= ticketService.findAndHoldSeats(seatHoldInput.numSeats, seatHoldInput.minLevel, seatHoldInput.maxLevel, seatHoldInput.customerEmail);;
	  return Response.status(Status.OK).entity(seatHold).build();
	}

  @POST
  @Path("/reserveSeat")
    @Produces(MediaType.APPLICATION_JSON)
	public Response reserveSeat(String reserveSeatInputString) throws JsonParseException, JsonMappingException, IOException, TicketServiceException {
	  System.out.println("reserveSet Rest");
	  
	  ObjectMapper mapper = new ObjectMapper();
	  ReserveSeatInput reserveSeatInput= mapper.readValue(reserveSeatInputString, ReserveSeatInput.class);
	  ReserveSeatOutput reserveSeatOutput= new ReserveSeatOutput();
	  reserveSeatOutput.setConfirmationCode(ticketService.reserveSeats(reserveSeatInput.seatHoldCode, reserveSeatInput.customerEmail));
	  return Response.status(Status.OK).entity(reserveSeatOutput).build();
	  
	}
  


} 