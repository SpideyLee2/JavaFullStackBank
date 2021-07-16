package com.revature.controller;

import io.javalin.http.Context;

public interface AuthController {

	public void postLogin(Context ctx);
	
	public void getLogout(Context ctx);
}
