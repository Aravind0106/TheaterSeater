package com.seater.theater.testUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.seater.theater.data.AuditLevel;
import com.seater.theater.data.Auditorium;
import com.seater.theater.data.Row;
import com.seater.theater.data.Seat;
import com.seater.theater.exception.TicketServiceException;
import com.seater.theater.values.Level;
import com.seater.theater.values.Theater;

/**
 * This utility call is used by test cases, to mock the DB object, to isolate service layer from Database layer
 * @author aravind
 *
 */
public class TSTestUtil {

	
public static Auditorium loadDataBase()  throws TicketServiceException{
		
		JAXBContext jaxbContext;
		Theater theater = null;
		try {
			jaxbContext = JAXBContext.newInstance(Theater.class);
			 Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			 theater = (Theater) jaxbUnmarshaller.unmarshal( new File("src/test/resources/TheaterSeatsConfiguration-Test.xml") );
		} catch (JAXBException e) {
			throw new TicketServiceException(com.seater.theater.error.ErrorEnum.INTERNAL);
		}
		 Auditorium auditorium = new Auditorium();
		List<AuditLevel> auditLevelList =  new ArrayList<AuditLevel>();
		int levelCounter=1;
		for(Level level : theater.getLevels().getLevel() ){
		AuditLevel auditLevel = new AuditLevel();
		List<Seat> seats = null;
		//Map<String,Row> auditMap= new ConcurrentHashMap<String,Row>();
		List<Row> auditRows= new ArrayList<Row>();
		for(int rowId= 1; rowId<=level.getRows(); rowId++){
			seats= new ArrayList<Seat>();
			for(int seatId = 1; seatId<=level.getSeatsInRow(); seatId++){
				seats.add(new Seat(seatId, false, false, System.currentTimeMillis()));
			}
			Row row = new Row();
			row.setSeats(seats);
			seats=null;
			auditRows.add(row);
			row= null;
		}
				auditLevel.setLevelName(level.getName());
				auditLevel.setRows(auditRows);
				auditRows= null;
				auditLevel.setPrice(level.getPrice());
				auditLevelList.add(auditLevel);
				auditLevel.setLevelNumber(levelCounter);
				levelCounter++;
				auditLevel= null;
		}
		auditorium.setAuditLevels(auditLevelList);
		return auditorium;
		}

}
