package com.revature.service;

import java.util.List;
import java.util.regex.Pattern;
import com.revature.models.BankAccount;
import com.revature.models.User;

public interface Service {
	
	public static final int MIN_CHARACTERS = 10;
	public static final int MAX_CHARACTERS = 30;
	public static final Pattern GENERAL_PATTERN = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
	public static final Pattern NAME_PATTERN = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
	
	// Customer methods
	
	public boolean makeMoneyTransfer(User sender, BankAccount senderAcc, User receiver, BankAccount receiverAcc, double amount); //
	
	public boolean changeBalance(BankAccount acc, User user, double amount); //
	
	public boolean createNewUser(User user); //
	
	public User logIn(String username, String password); //
	
	public BankAccount createNewBankAccount(User user, String name, double balance); //
	
	
	// Employee methods
	
	public List<BankAccount> getBankAccountsAwaitingApproval();
	
	public List<BankAccount> getCustomerBankAccounts(User user);
	
	public boolean validateBankAccount(BankAccount acc, User employee);
	
	public List<User> getCustomersOrderedByLastName();
	
	public User getCustomerByUsername(String username);

	
	// Helper methods
	
	public boolean isValidUsername(String username);

	public boolean isValidPassword(String username);

	public boolean passwordsMatch(String password1, String password2);

	public boolean isValidName(String name);

	public boolean isValidBankAccountName(String name);

	public boolean isValidLogIn(String username, String password);
	
	public boolean stringMatchesPattern(String str, Pattern p);
}