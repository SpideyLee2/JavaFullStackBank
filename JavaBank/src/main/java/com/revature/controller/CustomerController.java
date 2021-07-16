package com.revature.controller;

import io.javalin.http.Context;

public interface CustomerController {
	
	public void getBankAccounts(Context ctx);

	public void putDeposit(Context ctx);

	public void postNewBankAccount(Context ctx);
	
	public void postRegister(Context ctx);

	public void putTransfer(Context ctx);
}
