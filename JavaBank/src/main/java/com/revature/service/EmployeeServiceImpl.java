package com.revature.service;

import java.util.List;

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
}
