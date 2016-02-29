package com.seater.theater.serviceTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.seater.theater.data.Auditorium;
import com.seater.theater.dataServices.TheaterSeaterDataServiceImpl;
import com.seater.theater.error.ErrorEnum;
import com.seater.theater.exception.TicketServiceException;
import com.seater.theater.json.SeatHold;
import com.seater.theater.services.TheaterSeaterServiceImpl;
import com.seater.theater.testUtil.TSTestUtil;

import static org.mockito.Mockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(MockitoJUnitRunner.class)
public class TSTest {

	//THis is used to mock DB, But the tests are still independent
	private static Auditorium auditorium =null;
	static{
	try {
		auditorium  = TSTestUtil.loadDataBase();
	} catch (TicketServiceException e) {
		e.printStackTrace();
	}
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Mock
	private TheaterSeaterDataServiceImpl theatreDSI;
	
	@InjectMocks
	private  TheaterSeaterServiceImpl ticketService;

	@Before
	public void initializeMockito() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected=TicketServiceException.class)
	public void test0InvalidLevelCheck() throws TicketServiceException {
		//Mockito 
		stub(theatreDSI.getAuditoriumDetails()).toReturn(auditorium);
		ticketService.numSeatsAvailable(5);
		thrown.expect(TicketServiceException.class);
		thrown.expectMessage(ErrorEnum.NO_SUCH_LEVEL.toString());
	}
	
	@Test
	public void test1NumOfSeatsAvailPerEachLevel() throws TicketServiceException {
		//Mockito 
		stub(theatreDSI.getAuditoriumDetails()).toReturn(auditorium);
		assertNotNull(ticketService.numSeatsAvailable(1));
		assertNotNull(ticketService.numSeatsAvailable(2));
		assertNotNull(ticketService.numSeatsAvailable(3));
		assertNotNull(ticketService.numSeatsAvailable(4));
	}
	
	@Test
	public void test2SeatsHoldInGivenLevels() throws TicketServiceException {
		//Mockito 
		stub(theatreDSI.getAuditoriumDetails()).toReturn(auditorium);
		SeatHold seatsHeld = ticketService.findAndHoldSeats(5, 1,2, "aravind0106@gmail.com");
		assertNotNull(seatsHeld);
		assertEquals(5,seatsHeld.getSeatsHeld().size());
	}
	
	@Test
	public void test3SeatHoldSimple() throws TicketServiceException {
		//Mockito 
		stub(theatreDSI.getAuditoriumDetails()).toReturn(auditorium);
		SeatHold seatsHeld = ticketService.findAndHoldSeats(2, 1,1, "aravind0106@gmail.com");
		assertEquals(2,seatsHeld.getSeatsHeld().size());
	}
	
	@Test(expected=TicketServiceException.class)
	public void test4SeatHoldInvalidLevels() throws TicketServiceException {
		//Mockito 
		stub(theatreDSI.getAuditoriumDetails()).toReturn(auditorium);
		ticketService.findAndHoldSeats(2, 5,0, "aravind0106@gmail.com");
		thrown.expect(TicketServiceException.class);
		thrown.expectMessage(ErrorEnum.NO_SUCH_LEVEL.toString());
	}

	@Test(expected=TicketServiceException.class)
	public void test6SeatHoldAlreadyReserved() throws TicketServiceException {
		//Mockito 
		stub(theatreDSI.getAuditoriumDetails()).toReturn(auditorium);
		SeatHold seatsHeld = ticketService.findAndHoldSeats(3, 1, 4, "aravind0106@gmail.com");
		 ticketService.reserveSeats(seatsHeld.getSeatHoldCode(), "aravind0106@gmail.com");
		 ticketService.reserveSeats(seatsHeld.getSeatHoldCode(), "aravind0106@gmail.com");
			thrown.expect(TicketServiceException.class);
			thrown.expectMessage(ErrorEnum.ALREADY_RESERVED.toString());
	}
	
	@Test
	public void test7FullFucntional() throws TicketServiceException {
		//Mockito 
		stub(theatreDSI.getAuditoriumDetails()).toReturn(auditorium);
		assertNotNull((ticketService.numSeatsAvailable(1)));
		SeatHold seatsHeld = ticketService.findAndHoldSeats(3, 1, 4, "aravind0106@gmail.com");
		assertEquals(3,seatsHeld.getSeatsHeld().size());
		String confCode = ticketService.reserveSeats(seatsHeld.getSeatHoldCode(), "aravind0106@gmail.com");
		assertNotNull(confCode);
	}
	
	@Test(expected=TicketServiceException.class)
	public void test9SeatHoldReservationFull() throws TicketServiceException {
		//Mockito 
		stub(theatreDSI.getAuditoriumDetails()).toReturn(auditorium);
		ticketService.findAndHoldSeats(2000, 1,1, "aravind0106@gmail.com");
		thrown.expect(TicketServiceException.class);
		thrown.expectMessage(ErrorEnum.RESERVATION_FULL.toString());
	}
	
		
	}


