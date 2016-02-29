package com.seater.theater.testDB;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.seater.theater.data.Auditorium;
import com.theaterSeater.Interfaces.TheaterSeaterDataService;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This JUNIT class is executed to test the Data Layer alone. This checks if the parameters are rightly taken by the Data Service class from the xml.
 * @author aravind
 *
 */
@ContextConfiguration(locations = { "classpath:TheaterSeater-Data-Test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDB {
	
	@Autowired
	TheaterSeaterDataService theaterSeaterDataService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDB() {
		
		Auditorium auditorium= theaterSeaterDataService.getAuditoriumDetails(); 
		assertNotNull((auditorium.getAuditLevels().size()));
		assertNotNull((auditorium.getAuditLevels().get(0).getRows().get(0).getSeats().size()));
		assertNotNull((auditorium.getAuditLevels().get(0).getRows().size()));
	}
}
