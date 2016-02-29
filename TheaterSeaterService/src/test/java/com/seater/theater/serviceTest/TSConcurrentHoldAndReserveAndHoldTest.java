package com.seater.theater.serviceTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.stub;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.seater.theater.data.Auditorium;
import com.seater.theater.dataServices.TheaterSeaterDataServiceImpl;
import com.seater.theater.exception.TicketServiceException;
import com.seater.theater.json.SeatHold;
import com.seater.theater.services.TheaterSeaterServiceImpl;
import com.seater.theater.testUtil.TSTestUtil;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(MockitoJUnitRunner.class)
public class TSConcurrentHoldAndReserveAndHoldTest implements Runnable{

	private static Auditorium auditorium =null;
	static{
	try {
		auditorium  = TSTestUtil.loadDataBase();
	} catch (TicketServiceException e) {
		e.printStackTrace();
	}
}
	@Mock
	private TheaterSeaterDataServiceImpl theatreDSI;
	
	@InjectMocks
	private  static TheaterSeaterServiceImpl ticketService;

	@Before
	public void initializeMockito() throws Exception {
	
		MockitoAnnotations.initMocks(this);
	}
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void concurrentTest() throws InterruptedException{
		//This checks if 10 threads with each of 5  are executed , and the count of availability is check at the end
		stub(theatreDSI.getAuditoriumDetails()).toReturn(auditorium);
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            Runnable worker = new TSConcurrentHoldAndReserveAndHoldTest();
            executor.execute(worker);
        	}
        	Thread.sleep(8000);
            }
	
	public void run() {
		SeatHold seatsHeld = null;
		String confCode=null;
		try {
			ticketService.numSeatsAvailable(1);
			seatsHeld = ticketService.findAndHoldSeats(5, 1, 2, "aravind0106@gmail.com");
			assertEquals(seatsHeld.getSeatsHeld().size(), 5);
			confCode = ticketService.reserveSeats(seatsHeld.getSeatHoldCode(), "aravind0106@gmail.com");
			assertNotNull(confCode);
			seatsHeld = ticketService.findAndHoldSeats(5, 1, 2, "aravind0106@gmail.com");
			assertEquals(seatsHeld.getSeatsHeld().size(), 5);
			} catch (TicketServiceException e) {
				e.printStackTrace();
			}
	}
}
