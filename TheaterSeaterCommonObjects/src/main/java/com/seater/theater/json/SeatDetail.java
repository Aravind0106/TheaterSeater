package com.seater.theater.json;

public class SeatDetail{

	private int levelNumber;
	private String rowNumber;
	private int seatNumber;
	
	public SeatDetail() {
		
	}
	
	public SeatDetail(int levelNumber, String rowNumber, int seatNumber) {
		super();
		this.levelNumber = levelNumber;
		this.rowNumber = rowNumber;
		this.seatNumber = seatNumber;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}

	public String getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}
	public int getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public boolean equals(Object o) {
		
		SeatDetail seatDetailOutside = (SeatDetail)o;
		if(seatDetailOutside.levelNumber== this.levelNumber && seatDetailOutside.rowNumber.equals(this.rowNumber) && seatDetailOutside.seatNumber==this.seatNumber)
		{
			return true;
		}
		return false;
	
	}

}
