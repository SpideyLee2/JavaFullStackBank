package com.revature.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.models.BankAccount;
import com.revature.models.User;
import com.revature.repository.DBHandler;

public class EmployeeServiceImpl implements EmployeeService {

	private DBHandler dbHandler;
	
	public EmployeeServiceImpl(DBHandler dbHandler) {
		this.dbHandler = dbHandler;
	}
	
	@Override
	public List<BankAccount> getBankAccountsAwaitingApproval() {
		return dbHandler.selectBankAccountsToBeApproved();
	}
	
	@Override
	public List<BankAccount> getCustomerBankAccounts(User user) {
		return dbHandler.selectBankAccountsByUserName(user.getUsername());
	}
	
	@Override
	public boolean approveBankAccount(BankAccount acc, User employee) {
		return dbHandler.updateBankAccountApproval(acc, employee);
	}
	
	@Override
	public boolean rejectBankAccount(BankAccount acc, User employee) {
		return dbHandler.deleteBankAccount(acc, employee);
	}

	@Override
	public List<User> getCustomersOrderedByLastName() {
		return dbHandler.selectCustomersOrderedByLastName();
	}

	@Override
	public User getCustomerByUsername(String username) {
		return dbHandler.selectCustomerByUsername(username);
	}

	@Override
	public List<String> getTransactionLogList() {
		List<String> transactionLog = new ArrayList<String>();
		try {
			Scanner sc = new Scanner(new File("C:\\Users\\spide\\Desktop\\Revature Documents\\Projects\\Project 1\\Full-Stack-Banking-Project\\JavaBank\\log4j-application.log"));
			while(sc.hasNextLine()) {
				transactionLog.add(sc.nextLine());
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return transactionLog;
	}
}
