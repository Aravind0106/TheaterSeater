package com.seater.theater.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import com.seater.theater.data.AuditLevel;
import com.seater.theater.data.Auditorium;
import com.seater.theater.data.Row;
import com.seater.theater.data.Seat;
import com.seater.theater.error.ErrorEnum;
import com.seater.theater.exception.TicketServiceException;
import com.seater.theater.json.SeatDetail;
import com.seater.theater.json.SeatHold;

/**
 * This class provides all the helper methods to all the TheaterSeaterServiceImpl class methods
 * @author aravind
 *
 */
 	public class TicketServiceHelper {
	
	private static final long HOLD_TIME_OUT = 120000;
	final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TicketServiceHelper.class);
	
    
	public static int  numOfSeatsAvailable(int level , Auditorium auditorium) throws TicketServiceException{
			int availNum=0;
			if(auditorium!=null){
				if(level>0 && auditorium.getAuditLevels().size()>=level){
					for(Row row: auditorium.getAuditLevels().get(level-1).getRows()){
					  for(Seat seat : row.getSeats()){
						  if(!seat.isHold() && !seat.isReserved() && (seat.getTimeStamp()==null || (System.currentTimeMillis()-seat.getTimeStamp()<HOLD_TIME_OUT))){
							  availNum++; 
						  }
					  }
					}
				 }else{
					 throw new TicketServiceException(ErrorEnum.NO_SUCH_LEVEL);
				 }
			 }else{
				 logger.error("The auditorium object returned is null");	
				throw new TicketServiceException(ErrorEnum.INTERNAL);
			 }
		logger.info(String.format("Number of seats available : %s for level %s", availNum, level));	
		return availNum;
	}
	
	public static SeatHold findAndHoldSeats(int numSeats, int minLevel, int maxLevel,String emailId, Auditorium auditorium) 
			throws TicketServiceException{
			List<Seat> seatsInSeq=new ArrayList<Seat>();
			List<SeatDetail> heldList=new ArrayList<SeatDetail>();
			String uuID=null;
			//This should actually be read from the xml file.
			if(minLevel<0 || minLevel>4 || maxLevel<0 || maxLevel>4)
			{
				logger.error(String.format("min level : %s max level: %s", minLevel, maxLevel));	
				throw new TicketServiceException(ErrorEnum.NO_SUCH_LEVEL);
			}
			for(AuditLevel auditLevel: auditorium.getAuditLevels()){
				if(!(numOfSeatsAvailable(auditLevel.getLevelNumber(), auditorium)>=numSeats)){
					continue;
				}
				if(auditLevel.getLevelNumber()>=minLevel && auditLevel.getLevelNumber()<=maxLevel ){
					
					for(int rowCount=0;rowCount<auditLevel.getRows().size();rowCount++){
					  for(Seat seat : auditLevel.getRows().get(rowCount).getSeats()){
					    if((!seat.isHold() || (seat.getTimeStamp()==null || seat.getTimeStamp()-System.currentTimeMillis()>HOLD_TIME_OUT)) 
					    		&& (!seat.isReserved()) && (seat.getSeatHoldCode()==null)){
					    	seatsInSeq.add(seat);
								if(seatsInSeq.size()==numSeats){
									//Synchronization on Row starts, as below part updates the code.
									synchronized (auditLevel.getRows().get(rowCount)) {
										uuID=UUID.randomUUID().toString();
										int seatsFoundToHold=0;
										for(Seat seatToHold : seatsInSeq){
										//Double Check after Synchronization
										if((!seatToHold.isHold() || (seat.getTimeStamp()==null || seat.getTimeStamp()-System.currentTimeMillis()>HOLD_TIME_OUT))
												&& (!seatToHold.isReserved()) && (seatToHold.getSeatHoldCode()==null)){
											seatsFoundToHold++;
											heldList.add(new SeatDetail(auditLevel.getLevelNumber(),String.valueOf(rowCount+1), 
													seatToHold.getSeatNumber()));
										}else{
											seatsInSeq = new ArrayList<Seat>();
											heldList= new ArrayList<SeatDetail>();
											break;
										}
								}
								if(seatsFoundToHold==numSeats){
									setValuesToSeats(seatsInSeq,uuID);
									 if(heldList.size()==numSeats){
									 return new SeatHold(heldList,String.valueOf(uuID));
									 }
								}
							  }//Synchronization ends
					    	}
					    }else{
					    	seatsInSeq = new ArrayList<Seat>();
					    	heldList= new ArrayList<SeatDetail>();
					    }
					  }//for
					//  if(heldList.size()==numSeats){
					 // return new SeatHold(heldList,String.valueOf(uuID));
					//  }
					}
					}
			}
			if(heldList.size()==0){
				logger.info("Could not hold any seats as Reservation is full in the given levels");	
				throw new TicketServiceException(ErrorEnum.RESERVATION_FULL);
			}
			return null;
	}
	
	private static void setValuesToSeats(List<Seat> seatsInSeq, String uuid ){
		for(Seat seatToHold: seatsInSeq){
			seatToHold.setHold(true);
			seatToHold.setTimeStamp(System.currentTimeMillis());
			seatToHold.setSeatHoldCode(uuid);
		}
	}
	
	public static String reserveSeats(String seatHoldCode, String emailId, Auditorium auditorium) throws TicketServiceException{
			int numReserved=0;
			if(auditorium!=null){
			for(AuditLevel auditLevel: auditorium.getAuditLevels()){
					for(Row row: auditLevel.getRows()){
						Iterator<Seat> iter = row.getSeats().iterator();
					  while(iter.hasNext()){
						  Seat seat = iter.next();
						  if(seatHoldCode.equals(seat.getSeatHoldCode())){
							  if( seat.isHold() && !seat.isReserved() && (seat.getTimeStamp()!=null && (System.currentTimeMillis()-seat.getTimeStamp()
									  <HOLD_TIME_OUT))){
								//Synchronization on Row starts, as below part updates the code.
								  synchronized (row) {
									//Double Check after Synchronization
									  if( seat.isHold() && !seat.isReserved() && (seat.getTimeStamp()!=null && (System.currentTimeMillis()-seat.getTimeStamp()
											  <HOLD_TIME_OUT))){
										seat.setReserved(true);
										seat.setConfCode(emailId+seatHoldCode);
										numReserved++;
									  }
									  while(iter.hasNext()){
										  Seat updateSeat  = iter.next();
										  if( seatHoldCode.equals(updateSeat.getSeatHoldCode()) && updateSeat.isHold() && !
												  updateSeat.isReserved() && (updateSeat.getTimeStamp()!=null && (System.currentTimeMillis()-updateSeat.getTimeStamp()<HOLD_TIME_OUT))){
											  updateSeat.setReserved(true);
											  updateSeat.setConfCode(emailId+seatHoldCode);
												numReserved++;
											  }
									  }
								}//Sychronized
							  }
						  else{
							  logger.error("The seat provided already exists");
								throw new TicketServiceException(ErrorEnum.ALREADY_RESERVED);
						  }
					  }
					  }
					}
				}
			}
			logger.info("Num of seats reserved by this call: "+numReserved);
			if(numReserved>0){	
			return emailId+seatHoldCode;
			}else{
				logger.error("The Seat provided is invalid and does not exist");
				throw new TicketServiceException(ErrorEnum.INVALID_SEATID);
			}
	}
}
