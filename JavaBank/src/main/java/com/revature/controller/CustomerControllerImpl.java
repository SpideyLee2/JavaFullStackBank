package com.revature.controller;

import com.revature.models.User;
import com.revature.service.CustomerService;

import io.javalin.http.Context;

public class CustomerControllerImpl implements CustomerController {

	private CustomerService customerService;
	
	public CustomerControllerImpl(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@Override
	public void getBankAccounts(Context ctx) {
		String username = ctx.cookieStore("username");
		String password = ctx.cookieStore("password");
		
		System.out.println("Getting " + username + "'s bank accounts");
		
		ctx.status(200);
		User user = customerService.logIn(username, password);
		ctx.json(customerService.getBankAccounts(user));
	}
}
