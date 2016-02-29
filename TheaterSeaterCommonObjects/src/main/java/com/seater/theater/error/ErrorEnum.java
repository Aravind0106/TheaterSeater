package com.seater.theater.error;

/**
 * 
 * @author aravind
 *This Enum class regulates other developers from creating or checking non existent exceptions
 */
public enum ErrorEnum {
	  INTERNAL(0, "An Internal error has occured.Please try again"),
	  RESERVATION_FULL(1, "THe Reservation is full for provided level. Please check another level or less number of seats"),
	  NO_SUCH_LEVEL(2, "Provided level does not exist."),
	  ALREADY_RESERVED(3, "This seat is already reserved"),
	  INVALID_SEATID(4, "There is no such seat with this ID");

	  private final int code;
	  private final String description;

	  private ErrorEnum(int code, String description) {
	    this.code = code;
	    this.description = description;
	  }

	  public String getDescription() {
	     return description;
	  }

	  public int getCode() {
	     return code;
	  }

	  @Override
	  public String toString() {
	    return code + ": " + description;
	  }
	}