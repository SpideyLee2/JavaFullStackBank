package com.revature.controller;

import com.revature.service.AuthService;
import com.revature.service.AuthServiceImpl;

import io.javalin.http.Context;

public class AuthControllerImpl implements AuthController {

	private AuthService authService= new AuthServiceImpl();
	
	@Override
	public void login(Context ctx) {
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		
		if(authService.authenticateUser(username, password)) {
			
			ctx.status(200);
			ctx.redirect("customer-home.html");
			//if user doesn't exists you'd set it to 407 
		}
		else {
			ctx.status(507);
			ctx.redirect("login.html");
		}
		
		System.out.println(username);
		System.out.println(password);
		
	}

	
}
