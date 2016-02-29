package com.seater.theater.data;


public class Seat {
	

	private int seatNumber;
	private boolean reserved;
	private boolean hold;
	private Long timeStamp;
	private String confCode;
	private String seatHoldCode;
	
	
	public Seat(int seatNumber, boolean reserved, boolean hold, Long timeStamp) {
		super();
		this.seatNumber = seatNumber;
		this.reserved = reserved;
		this.hold = hold;
		this.timeStamp = timeStamp;
	}
	public int getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	public boolean isReserved() {
		return reserved;
	}
	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}
	public boolean isHold() {
		return hold;
	}
	public void setHold(boolean hold) {
		this.hold = hold;
	}
	public Long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getConfCode() {
		return confCode;
	}
	public void setConfCode(String confCode) {
		this.confCode = confCode;
	}
	public String getSeatHoldCode() {
		return seatHoldCode;
	}
	public void setSeatHoldCode(String seatHoldCode) {
		this.seatHoldCode = seatHoldCode;
	}
	
	
}
