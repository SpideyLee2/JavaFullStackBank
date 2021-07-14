package com.revature.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.revature.models.BankAccount;
import com.revature.models.User;

public interface CustomerService {

	public boolean makeMoneyTransfer(User sender, BankAccount senderAcc, User receiver, BankAccount receiverAcc, double amount);
	
	public boolean changeBalance(BankAccount acc, User user, double amount);
	
	public boolean createNewUser(User user);
	
	public User logIn(String username, String password);
	
	public BankAccount createNewBankAccount(User user, String name, double balance);

	public List<BankAccount> getBankAccounts(User user);
}
