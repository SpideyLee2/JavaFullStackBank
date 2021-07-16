package com.revature.controller;

import io.javalin.http.Context;

public interface EmployeeController {
	
	public void getCustomerAccounts(Context ctx);

	public void postCustomerAccounts(Context ctx);

	public void getAccountsAwaitingApproval(Context ctx);

	public void putApprovedAccount(Context ctx);

	public void deleteRejectedAccount(Context ctx);

	public void getTransactionLog(Context ctx);
}
