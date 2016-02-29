package com.seater.theater.rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.seater.theater.error.TicketServiceError;
import com.seater.theater.exception.TicketServiceException;

/**
 * This Mapper class maps all the exception from class TicketServiceException 
 * and produces a error code and description from class Error
 * @author aravind
 *
 */
@Provider
public class ALlOtherTicketServiceExceptionMapper implements ExceptionMapper<Throwable> 
{
    public Response toResponse(Throwable arg0) {
    	TicketServiceError error = new TicketServiceError();
    	error.setCode(500);
    	error.setDescription("INTERNAL Server Error");
    	return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();  
	}
}
