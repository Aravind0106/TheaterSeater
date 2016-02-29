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
public class TicketServiceExceptionMapper implements ExceptionMapper<TicketServiceException> 
{
    public Response toResponse(TicketServiceException arg0) {
    	TicketServiceError error = new TicketServiceError();
    	error.setCode(arg0.getError().getCode());
    	error.setDescription(arg0.getError().getDescription());
    	return Response.status(Status.BAD_REQUEST).entity(error).build();  
	}
}
