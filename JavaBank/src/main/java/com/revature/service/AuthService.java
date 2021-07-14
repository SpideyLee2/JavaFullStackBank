package com.revature.service;

import com.revature.models.User;

public interface AuthService {

	public boolean authenticateUser(User user);

	User getUser(String username, String password);
}
