package com.seater.theater.dataServices;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;

import com.seater.theater.data.AuditLevel;
import com.seater.theater.data.Auditorium;
import com.seater.theater.data.Row;
import com.seater.theater.data.Seat;
import com.seater.theater.exception.TicketServiceException;
import com.seater.theater.values.Level;
import com.seater.theater.values.Theater;
import com.theaterSeater.Interfaces.TheaterSeaterDataService;

/**
 * 
 * @author aravind
 *THis is the Database DAO Implementation class called by Dervice Layer.
 *This loads DB gets post constructed and loads Auditorium Object, which can be used by service layer to apply business logic
 */
@Component
public class TheaterSeaterDataServiceImpl implements TheaterSeaterDataService{
	
	final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TheaterSeaterDataServiceImpl.class);
	private Auditorium auditorium;
	public final static  String DATABASE_CONFIG_PATH= "/TheaterSeatsConfiguration.xml";
	
	
	public Auditorium getAuditoriumDetails(){
		return auditorium;
	}
	
	/**
	 * This method is post constructed, after the class is loaded
	 * @throws TicketServiceException
	 */
	@PostConstruct
	public void loadDB() throws TicketServiceException{
		JAXBContext jaxbContext;
		Theater theater = null;
		try {
			jaxbContext = JAXBContext.newInstance(Theater.class);
			 Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			 InputStream inpStream = this.getClass().getResourceAsStream((DATABASE_CONFIG_PATH));
			 theater = (Theater) jaxbUnmarshaller.unmarshal(inpStream);
		} catch (JAXBException e) {
			logger.error("There is a problem in reading TheaterSeatsConfiguration.xml file because of parsing ");
			throw new TicketServiceException(com.seater.theater.error.ErrorEnum.INTERNAL);
		}
		 auditorium = new Auditorium();
		List<AuditLevel> auditLevelList =  new ArrayList<AuditLevel>();
		int levelCounter=1;
		for(Level level : theater.getLevels().getLevel() ){
		AuditLevel auditLevel = new AuditLevel();
		List<Seat> seats = null;
		List<Row> auditRows= new ArrayList<Row>();
		for(int rowId= 1; rowId<=level.getRows(); rowId++){
			seats= new ArrayList<Seat>();
			for(int seatId = 1; seatId<=level.getSeatsInRow(); seatId++){
				seats.add(new Seat(seatId, false, false, null));
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
		logger.info("DataBase loaded");
		}
	@PreDestroy
	public void unloadDB(){
		auditorium=null;
	}
	   
	}
	

