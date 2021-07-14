package com.revature.controller;

import com.revature.models.User;
import com.revature.repository.DBHandlerImpl;
import com.revature.service.AuthService;
import com.revature.service.AuthServiceImpl;

import io.javalin.http.Context;

public class AuthControllerImpl implements AuthController {

	private AuthService authService;
	
	public AuthControllerImpl(AuthService authService) {
		this.authService = authService;
	}
	
	@Override
	public void login(Context ctx) {
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		
		User user = authService.getUser(username, password);
		
		if(user != null) {
			System.out.println("User exists!");
			ctx.status(200);
			ctx.cookieStore("username", username);
			ctx.cookieStore("password", password);
			
			if (authService.authenticateUser(user)) {
				ctx.redirect("employee-home.html");
			}
			else {
				ctx.redirect("customer-home.html");
			}

		}
		else {
			System.out.println("User entered incorrect info");
			ctx.status(407);
			ctx.redirect("login.html");
		}
		
		System.out.println(username);
		System.out.println(password);
		
	}
	
	@Override
	public void logout(Context ctx) {
		System.out.println("User logged out");
		ctx.clearCookieStore();
		ctx.redirect("login.html");
	}
}
