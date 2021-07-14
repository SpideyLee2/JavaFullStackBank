package com.revature.controller;

import java.util.List;

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
		String username = ctx.formParam("username");
		List<BankAccount> accounts = employeeService.getCustomerBankAccounts(new User(username, "password", 
				"first name", "last name", false));
		if(accounts != null) {
			ctx.status(200);
			ctx.json(accounts);
		}
		else {
			ctx.status(407);
		}
	}
	

}
