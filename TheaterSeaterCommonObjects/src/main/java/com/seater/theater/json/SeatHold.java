package com.seater.theater.json;

import java.util.List;

/* This class is the List of all the 
 * seats held , and yet to be reserved
 */
public class SeatHold {

	public SeatHold(){
		
	}
	public SeatHold(List<SeatDetail> seatsHeld, String seatHoldCode) {
		super();
		this.seatsHeld = seatsHeld;
		SeatHoldCode = seatHoldCode;
	}

	private List<SeatDetail> seatsHeld;
	private String SeatHoldCode;
	
	public String getSeatHoldCode() {
		return SeatHoldCode;
	}

	public void setSeatHoldCode(String seatHoldCode) {
		SeatHoldCode = seatHoldCode;
	}

	public List<SeatDetail> getSeatsHeld() {
		return seatsHeld;
	}

	public void setSeatsHeld(List<SeatDetail> seatsHeld) {
		this.seatsHeld = seatsHeld;
	}

	
}
