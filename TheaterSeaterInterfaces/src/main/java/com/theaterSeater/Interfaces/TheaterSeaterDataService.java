package com.theaterSeater.Interfaces;

import com.seater.theater.data.Auditorium;

/**
 * THis interface is for the Data Layer, Any class which implements this Interface, should get the DB and return it as an object mentioned.
 * @author aravind
 *
 */
public interface TheaterSeaterDataService {

	
	public Auditorium getAuditoriumDetails();
	
	//The below methods would have been used if there was a DB connection.
	//public void saveData(Seat seat);
	//public void deleteData(Seat seat);
	//public void updateData(Seat seat);
	//public void fetchData(Seat seat);
	
}
