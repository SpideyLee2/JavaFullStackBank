package com.revature.service;

import com.revature.models.User;
import com.revature.repository.DBHandler;
import com.revature.repository.DBHandlerImpl;

public class AuthServiceImpl implements AuthService {

	private DBHandler dbHandler;
	
	public AuthServiceImpl(DBHandler dbHandler) {
		this.dbHandler = dbHandler;
	}
	
	private CustomerService customerService = new CustomerServiceImpl(new DBHandlerImpl());

	@Override
	public boolean authenticateUser(User user) {
		if(user.isEmployee()) {
			System.out.println("User is an employee!");
			return true;
		}
		else {
			System.out.println("User is a customer!");
			return false;
		}
	}
	
	@Override
	public User getUser(String username, String password) {
		return customerService.logIn(username, password);
	}
	
	
}
