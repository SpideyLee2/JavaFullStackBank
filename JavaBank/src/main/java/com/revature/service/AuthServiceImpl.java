package com.revature.service;

import com.revature.models.User;
import com.revature.repository.DBHandler;
import com.revature.repository.DBHandlerImpl;

public class AuthServiceImpl implements AuthService {

	DBHandler dbHandler = new DBHandlerImpl();

	@Override
	public boolean authenticateUser(String username, String password) {
		try {
			User user = dbHandler.selectUserByUsername(username);
			
			//checking if user exists with that username
			if(user == null) {
				return false;
			}
			else {
				//checking if that user password matches.
				if(password.equals(user.getPassword())) {
					return true;
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
