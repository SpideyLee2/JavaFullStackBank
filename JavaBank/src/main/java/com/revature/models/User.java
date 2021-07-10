package com.revature.models;

public class User {
	
	private String firstName, lastName, username, password;
	private boolean isEmployee;
	public User(String username, String password, String firstName, String lastName, boolean isEmployee) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isEmployee = isEmployee;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public boolean isEmployee() {
		return isEmployee;
	}
	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}
	
	public boolean equals(User user) {
		return username.equals(user.getUsername());
	}
	
	@Override
	public String toString() {
		return "Name: " + firstName + lastName + "\n"
				+ "Username: " + username + "\n"
				+ "Password: " + password + "\n"
				+ "Employee? " + isEmployee + "\n";
	}
	public String toTabbedString() {
		return "\tName: " + firstName + " " + lastName + "\n"
				+ "\tUsername: " + username + "\n"
				+ "\tPassword: " + password + "\n"
				+ "\tEmployee? " + isEmployee + "\n";
	}
	public String toStringCustomers() {
		return "Name: " + firstName + " " + lastName + "\n"
				+ "Username: " + username + "\n"
				+ "Password: " + password + "\n";
	}
}
