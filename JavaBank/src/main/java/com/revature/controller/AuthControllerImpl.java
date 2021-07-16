package com.revature.controller;

import com.revature.models.User;
import com.revature.service.AuthService;

import io.javalin.http.Context;

public class AuthControllerImpl implements AuthController {

	private AuthService authService;
	
	public AuthControllerImpl(AuthService authService) {
		this.authService = authService;
	}
	
	@Override
	public void postLogin(Context ctx) {
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		
		User user = authService.getUser(username, password);
		
		if(user != null) {
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
			ctx.status(407);
			ctx.redirect("login.html");
		}
	}
	
	@Override
	public void getLogout(Context ctx) {
		ctx.clearCookieStore();
		ctx.redirect("login.html");
	}
}
