package com.seater.theater.exception;

import com.seater.theater.error.ErrorEnum;

/**
 * 
 * @author aravind
 *This class is used to send application fabricated exception in readab;e format.
 */
public class TicketServiceException extends Exception {

	private ErrorEnum error;
	
	public TicketServiceException(com.seater.theater.error.ErrorEnum error) {
		
		super(error.toString());
		this.error=error;
	}

	public ErrorEnum getError() {
		return error;
	}

	public void setError(ErrorEnum error) {
		this.error = error;
	}
}
