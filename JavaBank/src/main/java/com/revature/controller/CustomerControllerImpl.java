package com.revature.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.BankAccount;
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

	@Override
	public void makeDeposit(Context ctx) {		
		ObjectMapper om = new ObjectMapper();
		
		BankAccount acc;
		try {
			acc = om.readValue(ctx.body(), BankAccount.class);
			acc.setUsernameRef(ctx.cookieStore("username"));
			User mockUser = new User(ctx.cookieStore("username"), null, null, null, false);
			if(customerService.changeBalance(acc, mockUser, acc.getBalance())) {
				ctx.status(200);
			}
			else {
				ctx.status(400);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			ctx.status(500);
		}
	}
}
