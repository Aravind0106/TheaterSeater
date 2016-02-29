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

	@Test
	public void test0InvalidLevelCheck() throws TicketServiceException {
		//Mockito 
		stub(theatreDSI.getAuditoriumDetails()).toReturn(auditorium);
		ticketService.numSeatsAvailable(5);
		thrown.expect(TicketServiceException.class);
		thrown.expectMessage(ErrorEnum.NO_SUCH_LEVEL.toString());
		//assertEquals(1500,ticketService.numSeatsAvailable(4));
	}
	
	@Test
	public void test1NubOfSeatsAvailPerLevel() throws TicketServiceException {
		//Mockito 
		stub(theatreDSI.getAuditoriumDetails()).toReturn(auditorium);
		ticketService.numSeatsAvailable(1);
		try{
		assertEquals(1250, ticketService.numSeatsAvailable(1));
		assertEquals(2000,ticketService.numSeatsAvailable(2));
		assertEquals(1500,ticketService.numSeatsAvailable(3));
		assertEquals(1500,ticketService.numSeatsAvailable(4));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void test2SeatHoldSimpleAndTestAvailSeatsNumber() throws TicketServiceException {
		//This will check if 2 seats er
		//Mockito 
		stub(theatreDSI.getAuditoriumDetails()).toReturn(auditorium);
		SeatHold seatsHeld = ticketService.findAndHoldSeats(2, 1,1, "aravind0106@gmail.com");
		System.out.println("Test2:"+ seatsHeld.getSeatHoldCode());
		assertEquals(2,seatsHeld.getSeatsHeld().size());
	}
	
	
	@Test
	public void test3SeatsHoldManyMorethanLevel1And2() throws TicketServiceException {
		//Since Level1(1250 max) can accomodate , it should fill up seats in Level2, as it has 2000 seats
		//Mockito 
		stub(theatreDSI.getAuditoriumDetails()).toReturn(auditorium);
		SeatHold seatsHeld = ticketService.findAndHoldSeats(20, 1, 2, "aravind0106@gmail.com");
		System.out.println("Test3:"+ seatsHeld.getSeatHoldCode());
		assertNotNull(seatsHeld);
		assertEquals(20,seatsHeld.getSeatsHeld().size());
	}
	
	@Test
	public void test4HoldAndReserve() throws TicketServiceException {
		//Mockito 
		stub(theatreDSI.getAuditoriumDetails()).toReturn(auditorium);
		SeatHold seatsHeld = ticketService.findAndHoldSeats(2, 1, 1, "aravind0106@gmail.com");
		assertEquals(2,seatsHeld.getSeatsHeld().size());
		String confCode;
			confCode= ticketService.reserveSeats(seatsHeld.getSeatHoldCode(), "aravind0106@gmail.com");
			assertNotNull(confCode);
			System.out.println("Test4:"+ seatsHeld.getSeatHoldCode());
	}
	
	@Test
	public void test5SeatHoldMoreThanLevel4TOFail() throws TicketServiceException {
		// This will try to hold more seats than what what level 4 can accomodate
		//Mockito 
		stub(theatreDSI.getAuditoriumDetails()).toReturn(auditorium);
		SeatHold seatsHeld = ticketService.findAndHoldSeats(2000, 1, 4, "aravind0106@gmail.com");
		System.out.println("Test5:"+ seatsHeld.getSeatHoldCode());
		//assertNull(seatsHeld);
	}
	
	@Test
	public void test6SeatHoldAcrossLevel3and4() throws TicketServiceException {
		//This will check if the application handles the the booking across two rows 
		//Mockito 
		stub(theatreDSI.getAuditoriumDetails()).toReturn(auditorium);
		SeatHold seatsHeld = ticketService.findAndHoldSeats(30, 3, 4, "aravind0106@gmail.com");
		System.out.println("Test6:"+ seatsHeld.getSeatHoldCode());
		assertNotNull(seatsHeld.getSeatHoldCode());
	}
		
	}


