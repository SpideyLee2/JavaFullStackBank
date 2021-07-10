package com.revature.exceptions;

public class DuplicateUsernameException extends Exception {
	
	public DuplicateUsernameException() {
		System.out.println("Username already exists. Please, choose a different username.");
	}
}
