package com.revature.service;

import java.util.List;

import com.revature.MainDriver;
import com.revature.models.BankAccount;
import com.revature.models.User;
import com.revature.repository.DBHandler;
import com.revature.util.MoneyUtils;

public class CustomerServiceImpl implements CustomerService {

	private DBHandler dbHandler;
	
	public CustomerServiceImpl(DBHandler dbHandler) {
		this.dbHandler = dbHandler;
	}
	
	@Override
	public boolean makeMoneyTransfer(User sender, BankAccount senderAcc, User receiver, BankAccount receiverAcc, double amount) {
		boolean receiverProcessed = false;
		
		amount = MoneyUtils.round(amount);
		boolean senderProcessed = changeBalance(senderAcc, sender, senderAcc.getBalance() - amount);
		if (senderProcessed) {
			receiverProcessed = changeBalance(receiverAcc, receiver, receiverAcc.getBalance() + amount);
		}
		
		if(senderProcessed && receiverProcessed) {
			MainDriver.loggy.info("Money transfer from User '" + sender.getUsername() + "' to User '" +  receiver.getUsername() 
				+ "' processed successfully.");
			return true;
		}
		else if (!senderProcessed) {
			MainDriver.loggy.info("Money transfer from User '" + sender.getUsername() + "' to User '" +  receiver.getUsername() 
			+ "' failed before transaction.");
		}
		else {
			MainDriver.loggy.info("Money transfer from User '" + sender.getUsername() + "' to User '" +  receiver.getUsername() 
				+ "' failed during transaction. Returning funds to User '" + sender.getUsername() + "'.");
			changeBalance(senderAcc, sender, senderAcc.getBalance() + amount);
		}
		return false;
	}

	@Override
	public boolean changeBalance(BankAccount acc, User user, double amount) {
		amount = MoneyUtils.round(amount);
		acc.setBalance(amount);
		return dbHandler.updateBalance(acc, user, amount);
	}

	@Override
	public boolean createNewUser(User user) {
		return dbHandler.insertNewUser(user);
	}

	@Override
	public BankAccount createNewBankAccount(User user, String name, double balance) {
		balance = MoneyUtils.round(balance);
		BankAccount newBankAccount = new BankAccount(0, user.getUsername(), name, balance, false);
		
		dbHandler.insertNewBankAccount(newBankAccount, user);
		newBankAccount = dbHandler.selectBankAccountByName(user, name);
		
		return newBankAccount;
	}
	
	@Override
	public User logIn(String username, String password) {
		User user = dbHandler.selectUserByUsernameAndPassword(username, password);
		if(user == null) {
			MainDriver.loggy.info("Unsuccessful login on User '" + username + "'.");
		}
		else {
			MainDriver.loggy.info("Successful login on User '" + username + "'.");
		}
		return user;
	}

	@Override
	public List<BankAccount> getBankAccounts(User user) {
		return dbHandler.selectBankAccountsByUserName(user.getUsername());
	}
}
