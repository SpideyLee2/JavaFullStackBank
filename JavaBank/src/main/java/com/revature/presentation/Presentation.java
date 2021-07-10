package com.revature.presentation;

import com.revature.models.BankAccount;
import com.revature.models.User;

public interface Presentation {

	// Everyone Methods
	
	public void welcomeMessage();
	
	public User logInOrCreateNewUserPrompt();
	
	public User logInPrompt();
	
	// Customer Methods
	
	public void createNewUserPrompt();
	
	public void displayCustomerOptions(User user);
	
	public void displayBankAccountOptions(User user, BankAccount acc);
	
	public BankAccount createNewBankAccountPrompt(User user);

	public BankAccount pickBankAccount(User user, Boolean displayMultipleTimes);
	
	public void checkBalance(BankAccount acc);
	
	public void deposit(BankAccount acc, User user);
	
	public void withdraw(BankAccount acc, User user);
	
	public void makeMoneyTransferToSelf(User user, BankAccount acc);
	
	public void makeMoneyTransferToOther(User user, BankAccount acc);
	
	// Employee Methods
	
	public void displayEmployeeOptions(User employee);
	
	public void approveBankAccountsPrompt(User employee);
	
	public void viewCustomerBankAccountsPrompt(User employee);
}
