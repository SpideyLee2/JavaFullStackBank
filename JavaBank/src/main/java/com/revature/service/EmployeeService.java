package com.revature.service;

import java.util.List;

import com.revature.models.BankAccount;
import com.revature.models.User;

public interface EmployeeService {
	
	public List<BankAccount> getBankAccountsAwaitingApproval();
	
	public List<BankAccount> getCustomerBankAccounts(User user);
	
	public boolean validateBankAccount(BankAccount acc, User employee);
	
	public List<User> getCustomersOrderedByLastName();
	
	public User getCustomerByUsername(String username);
}
