package com.revature;

import org.apache.log4j.Logger;

import com.revature.controller.AuthController;
import com.revature.controller.AuthControllerImpl;
import com.revature.models.User;

import io.javalin.Javalin;

public class MainDriver {

	private static final String LOGIN_PATH = "/login";
	private static AuthController authController = new AuthControllerImpl();
	public static final Logger loggy = Logger.getLogger(MainDriver.class);
	
	public static void main(String[] args) {
		Javalin app = Javalin.create(
				config -> {
					config.addStaticFiles("/public");
				}
			).start(7000);
	}
}
