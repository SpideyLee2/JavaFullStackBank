package com.revature.controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.BankAccount;
import com.revature.models.User;
import com.revature.service.EmployeeService;

import io.javalin.http.Context;

public class EmployeeControllerImpl implements EmployeeController {

	private EmployeeService employeeService;
	
	public EmployeeControllerImpl(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@Override
	public void getCustomerAccounts(Context ctx) {
		String username = ctx.cookieStore("customerUsername");
		ctx.removeCookie("customerUsername");
		List<BankAccount> accounts = employeeService.getCustomerBankAccounts(new User(username, null, null, null, false));
		if(accounts != null && !accounts.isEmpty()) {
			ctx.status(200);
			ctx.json(accounts);
		}
		else {
			ctx.status(407);
		}
	}

	@Override
	public void postCustomerAccounts(Context ctx) {
		String username = ctx.formParam("username");
		List<BankAccount> accounts = employeeService.getCustomerBankAccounts(new User(username, null, null, null, false));
		if(accounts != null && !accounts.isEmpty()) {
			ctx.cookieStore("customerUsername", username);
			ctx.status(200);
			ctx.json(accounts);
			ctx.redirect("employee-home.html");
		}
		else {
			ctx.removeCookie("customerUsername");
			ctx.status(407);
		}
	}

	@Override
	public void getAccountsAwaitingApproval(Context ctx) {
		List<BankAccount> accounts = employeeService.getBankAccountsAwaitingApproval();
		if(accounts != null && !accounts.isEmpty()) {
			System.out.println("getting accounts to be approved");
			ctx.status(200);
			ctx.json(accounts);
		}
		else {
			System.out.println("all accounts have been handled");
			ctx.status(407);
		}
	}

	@Override
	public void putApprovedAccount(Context ctx) {
		ObjectMapper om = new ObjectMapper();
		
		BankAccount acc;
		try {
			acc = om.readValue(ctx.body(), BankAccount.class);
			User employee = new User(ctx.cookieStore("username"), null, null, null, false);
			if(employeeService.approveBankAccount(acc, employee)) {
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
	public void deleteRejectedAccount(Context ctx) {
		ObjectMapper om = new ObjectMapper();
		
		BankAccount acc;
		try {
			acc = om.readValue(ctx.body(), BankAccount.class);
			User employee = new User(ctx.cookieStore("username"), null, null, null, false);
			if(employeeService.rejectBankAccount(acc, employee)) {
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
