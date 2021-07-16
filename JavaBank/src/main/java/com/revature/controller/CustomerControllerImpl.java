package com.revature.controller;

import java.util.List;

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
		List<BankAccount> accs = customerService.getBankAccounts(new User (ctx.cookieStore("username")));
		
		if (accs != null && !accs.isEmpty()) {
			ctx.status(200);
			ctx.json(accs);
		}
		else {
			ctx.status(407);
		}
	}

	@Override
	public void putDeposit(Context ctx) {		
		ObjectMapper om = new ObjectMapper();
		
		BankAccount acc;
		try {
			acc = om.readValue(ctx.body(), BankAccount.class);
			acc.setUsernameRef(ctx.cookieStore("username"));
			User mockUser = new User(ctx.cookieStore("username"));
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

	@Override
	public void postNewBankAccount(Context ctx) {
		ObjectMapper om = new ObjectMapper();
		
		BankAccount acc;
		try {
			acc = om.readValue(ctx.body(), BankAccount.class);
			User mockUser = new User(ctx.cookieStore("username"));
			if(customerService.createNewBankAccount(mockUser, acc.getName(), acc.getBalance()) != null) {
				ctx.status(200);
				ctx.redirect("http://localhost:7000/customer-home.html");
			}
			else {
				ctx.status(400);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			ctx.status(500);
		}
	}

	@Override
	public void postRegister(Context ctx) {
		ObjectMapper om = new ObjectMapper();
		
		User user;
		try {
			user = om.readValue(ctx.body(), User.class);
			if(customerService.createNewUser(user)) {
				ctx.status(200);
				ctx.redirect("login.html");
			}
			else {
				ctx.status(422);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			ctx.status(500);
		}
	}

	@Override
	public void putTransfer(Context ctx) {
		ObjectMapper om = new ObjectMapper();
		
		BankAccount[] accArray;
		try {
			accArray = om.readValue(ctx.body(), BankAccount[].class);
			User mockUser = new User(ctx.cookieStore("username"));
			if(customerService.changeBalance(accArray[0], mockUser, accArray[0].getBalance())) {
				if (customerService.changeBalance(accArray[1], mockUser, accArray[1].getBalance())) {
					ctx.status(200);
				}
				else {
					ctx.status(500);
				}
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
