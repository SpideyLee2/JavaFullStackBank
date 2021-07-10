package com.revature.repository;

import java.util.List;

import com.revature.models.BankAccount;
import com.revature.models.User;

public interface DBHandler {
	//CRUD
	
	//Create
	public boolean insertNewUser(User user);
	
	public boolean insertNewBankAccount(BankAccount acc, User user);
	
	//Read
	public BankAccount selectBankAccountByName(User user, String name);
	
	public List<BankAccount> selectBankAccountsByUserName(String username);
	
	public List<BankAccount> selectBankAccountsToBeApproved();
	
	public User selectUserByUsername(String username);
	
	public List<User> selectCustomersOrderedByLastName();
	
	public User selectCustomerByUsername(String username);
	
	public User selectUserByUsernameAndPassword(String username, String password);
	
	//Update
//	public void updatePassword(BankAccount acc);
	
	public boolean updateBalance(BankAccount acc, User user, double newBalance);
	
	public boolean updateBankAccountApproval(BankAccount acc, User employee);
	
	//Delete
//	public void deleteBankAccount(BankAccount acc);
}
