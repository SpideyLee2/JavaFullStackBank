package com.revature;

import org.apache.log4j.Logger;

import com.revature.controller.AuthController;
import com.revature.controller.AuthControllerImpl;
import com.revature.controller.CustomerController;
import com.revature.controller.CustomerControllerImpl;
import com.revature.controller.EmployeeController;
import com.revature.controller.EmployeeControllerImpl;
import com.revature.models.User;
import com.revature.repository.DBHandler;
import com.revature.repository.DBHandlerImpl;
import com.revature.service.AuthServiceImpl;
import com.revature.service.CustomerServiceImpl;
import com.revature.service.EmployeeServiceImpl;

import io.javalin.Javalin;

public class MainDriver {

	private static final String LOGIN_PATH = "/login";
	private static final String ACCOUNTS_PATH = "/accounts";
	
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
		
		app.post(LOGIN_PATH, ctx -> authController.login(ctx));
		app.get("/logout", ctx -> authController.logout(ctx));
		app.get(ACCOUNTS_PATH, ctx -> customerController.getBankAccounts(ctx));
		app.get("/customerAccounts", ctx -> employeeController.getCustomerAccounts(ctx));
	}
}
