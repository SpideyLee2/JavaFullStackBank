package com.revature;

import org.apache.log4j.Logger;

import com.revature.models.User;
import com.revature.presentation.Presentation;
import com.revature.presentation.PresentationImpl;
import com.revature.repository.DBHandler;
import com.revature.repository.DBHandlerImpl;
import com.revature.service.Service;
import com.revature.service.ServiceImpl;

public class MainDriver {

	public static final Logger loggy = Logger.getLogger(MainDriver.class);
	
	public static void main(String[] args) {
		DBHandler dbHandler = new DBHandlerImpl();
		Service service = new ServiceImpl(dbHandler);
		Presentation presentation = new PresentationImpl(service);
		
		presentation.welcomeMessage();
		
		while(true) { // The application is running
			User user = presentation.logInOrCreateNewUserPrompt();
			// User has logged in.
			
			if(user.isEmployee()) { // User is an employee
				presentation.displayEmployeeOptions(user);
			}
			else { // User is a customer
				presentation.displayCustomerOptions(user);
			}
		}
	}
}
