package com.seater.theater.error;

/**
 * 
 * @author aravind
 *THis class is used is used to send the Exception to the REST API users
 */
public class TicketServiceError {

	private int code;
	private String description;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}

