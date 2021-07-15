package com.revature.controller;

import io.javalin.http.Context;

public interface CustomerController {
	
	public void getBankAccounts(Context ctx);

	public void makeDeposit(Context ctx);
}
