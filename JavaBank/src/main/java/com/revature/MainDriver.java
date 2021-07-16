package com.revature;

import org.apache.log4j.Logger;

import com.revature.controller.AuthController;
import com.revature.controller.AuthControllerImpl;
import com.revature.controller.CustomerController;
import com.revature.controller.CustomerControllerImpl;
import com.revature.controller.EmployeeController;
import com.revature.controller.EmployeeControllerImpl;
import com.revature.repository.DBHandler;
import com.revature.repository.DBHandlerImpl;
import com.revature.service.AuthServiceImpl;
import com.revature.service.CustomerServiceImpl;
import com.revature.service.EmployeeServiceImpl;

import io.javalin.Javalin;

public class MainDriver {

	private static final String LOGIN_PATH = "/login";
	private static final String LOGOUT_PATH = "/logout";
	private static final String REGISTER_PATH = "/register";
	private static final String ACCOUNTS_PATH = "/accounts";
	private static final String EMPLOYEE_VIEW_ACCOUNTS_PATH = "/viewAccounts";
	private static final String APPROVAL_PATH = "/approvals";
	private static final String TRANSACTION_LOG_PATH = "/transactions";
	private static final String TRANSFER_PATH = "/transfer";
	
	public static final Logger loggy = Logger.getLogger(MainDriver.class);
	
	private static DBHandler dbHandler = new DBHandlerImpl();
	private static AuthController authController = new AuthControllerImpl(new AuthServiceImpl(dbHandler));
	private static CustomerController customerController = new CustomerControllerImpl(new CustomerServiceImpl(dbHandler));
	private static EmployeeController employeeController = new EmployeeControllerImpl(new EmployeeServiceImpl(dbHandler));
	
	public static void main(String[] args) {
		Javalin app = Javalin.create(
				config -> {
					config.addStaticFiles("/public");
				}
			).start(7000);
		
		// Login/Logout responses
		app.post(LOGIN_PATH, ctx -> authController.postLogin(ctx));
		app.get(LOGOUT_PATH, ctx -> authController.getLogout(ctx));
		
		// Register responses
		app.post(REGISTER_PATH, ctx -> customerController.postRegister(ctx));
		
		// Customer responses
		app.get(ACCOUNTS_PATH, ctx -> customerController.getBankAccounts(ctx));
		app.post(ACCOUNTS_PATH, ctx -> customerController.postNewBankAccount(ctx));
		app.put(ACCOUNTS_PATH, ctx -> customerController.putDeposit(ctx));
		
		app.put(TRANSFER_PATH, ctx -> customerController.putTransfer(ctx));
		
		// Employee responses
		app.get(EMPLOYEE_VIEW_ACCOUNTS_PATH, ctx -> employeeController.getCustomerAccounts(ctx));
		app.post(EMPLOYEE_VIEW_ACCOUNTS_PATH, ctx -> employeeController.postCustomerAccounts(ctx));
		
		app.get(APPROVAL_PATH, ctx -> employeeController.getAccountsAwaitingApproval(ctx));
		app.put(APPROVAL_PATH, ctx -> employeeController.putApprovedAccount(ctx));
		app.delete(APPROVAL_PATH, ctx -> employeeController.deleteRejectedAccount(ctx));
		
		app.get(TRANSACTION_LOG_PATH, ctx -> employeeController.getTransactionLog(ctx));
	}
}
