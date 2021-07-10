package com.revature.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import com.revature.models.BankAccount;
import com.revature.models.User;
import com.revature.repository.DBHandler;

public class ServiceImplTest {
	
	static ServiceImpl service;
	static User testCustomer1;
	static User testCustomer2;
	static User testCustomer3;
	static User testEmployee;
	static User testDuplicateUser;
	static User uncreateduser;
	static BankAccount cust1Acc1;
	static BankAccount cust1Acc2;
	static BankAccount cust1Acc3;
	static BankAccount cust2Acc1;
	static BankAccount cust2Acc2;
	static BankAccount uncreatedAcc;
	static List<User> userDB;
	static List<BankAccount> accDB;
	
//	ServiceImpl service;
//	User testCustomer1, testCustomer2, testCustomer3, testEmployee, testDuplicateUser, uncreateduser;
//	BankAccount cust1Acc1, cust1Acc2, cust1Acc3, cust2Acc1, cust2Acc2, uncreatedAcc;
	
	@Mock
	static DBHandler fakeDBHandler;
	
	@BeforeClass
	public static void setupServiceTests() {
		fakeDBHandler = mock(DBHandler.class);
		service = new ServiceImpl(fakeDBHandler);
	}
	
	@BeforeClass
	public static void setupServiceTestObjects() {
		uncreateduser = new User("uncreateduser", "good p4ssword", "fake", "user", false);
		uncreatedAcc = new BankAccount(100, "uncreateduser", "uncreatedAcc", 0.00, false);
		
		testCustomer1 = new User("testcustomer1", "good p4ssword", "tester", "one", false);
		testCustomer2 = new User("testcustomer2", "good p4ssword", "tester", "two", false);
		testCustomer3 = new User("testcustomer3", "good p4ssword", "tester", "three", false);
		testEmployee = new User("testemployee", "good p4ssword", "tester", "four", true);
		
		testDuplicateUser = new User("testcustomer1", "good p4ssword", "tester", "one", false);
		
		cust1Acc1 = new BankAccount(1, "testcustomer1", "testacc1", 50.00, false);
		cust1Acc2 = new BankAccount(2, "testcustomer1", "testacc2", 20.00, false);
		cust1Acc3 = new BankAccount(3, "testcustomer1", "testacc3", 500.00, true);
		cust2Acc1 = new BankAccount(4, "testcustomer2", "testacc4", 20.00, true);
		cust2Acc2 = new BankAccount(5, "testcustomer2", "testacc5", 0.00, false);
		
		// Mimics the stored elements of a DB
		userDB = new ArrayList<User>(Arrays.asList(testCustomer1, testCustomer2, testCustomer3, testEmployee));
		accDB = new ArrayList<BankAccount>(Arrays.asList(cust1Acc1, cust1Acc2, cust1Acc3, cust2Acc1, cust2Acc2));
	}
	
	@Before
	public void setupMakeMoneyTransferAndChangeBalance() {
		when(fakeDBHandler.updateBalance(cust2Acc2, testCustomer2, 50.00)).thenReturn(true);
		when(fakeDBHandler.updateBalance(cust1Acc1, testCustomer1, 0.00)).thenReturn(true);
		when(fakeDBHandler.updateBalance(cust1Acc1, testCustomer1, 2000.00)).thenReturn(true);
	}
	
	@Before
	public void setupCreateNewUser() {
		when(fakeDBHandler.insertNewUser(uncreateduser)).thenReturn(!userDB.contains(uncreateduser));
	}
	
	@Before
	public void setupLogIn() {
		when(fakeDBHandler.selectUserByUsernameAndPassword(testCustomer1.getUsername(), testCustomer1.getPassword()))
			.thenReturn(testCustomer1);
		when(fakeDBHandler.selectUserByUsernameAndPassword(testCustomer2.getUsername(), testCustomer2.getPassword()))
			.thenReturn(testCustomer2);
		when(fakeDBHandler.selectUserByUsernameAndPassword(testCustomer3.getUsername(), testCustomer3.getPassword()))
			.thenReturn(testCustomer3);
		when(fakeDBHandler.selectUserByUsernameAndPassword(testEmployee.getUsername(), testEmployee.getPassword()))
			.thenReturn(testEmployee);
		when(fakeDBHandler.selectUserByUsernameAndPassword(uncreateduser.getUsername(), uncreateduser.getPassword()))
			.thenReturn(null);
	}
	
	// UNFINISHED
	@Before
	public void setupCreateNewBankAccount() {
		BankAccount newBankAcc = new BankAccount(6, "testcustomer1", "testacc6", 70.00, false);
		when(fakeDBHandler.insertNewBankAccount(newBankAcc, testCustomer1))
			.thenReturn(true);
		accDB.add(newBankAcc);
		when(fakeDBHandler.selectBankAccountByName(testCustomer1, "testacc6"))
			.thenReturn(getBankAccountFromAccDB("testacc6"));
	}
	
	private BankAccount getBankAccountFromAccDB(String accName) {
		for(BankAccount acc : accDB) {
			if(acc.getName().equals(accName)) {
				return acc;
			}
		}
		return null;
	}
	
	@Before
	public void setupValidateBankAccount() {
		when(fakeDBHandler.updateBankAccountApproval(cust1Acc1, testCustomer1)).thenReturn(true);
		when(fakeDBHandler.updateBankAccountApproval(cust1Acc2, testCustomer1)).thenReturn(true);
		when(fakeDBHandler.updateBankAccountApproval(cust2Acc2, testCustomer2)).thenReturn(true);
	}
	
	@Before
	public void setupGetCustomerByUsername() {
		when(fakeDBHandler.selectCustomerByUsername("testcustomer1")).thenReturn(getCustomerFromUserDB("testcustomer1"));
		when(fakeDBHandler.selectCustomerByUsername("testcustomer2")).thenReturn(getCustomerFromUserDB("testcustomer2"));
		when(fakeDBHandler.selectCustomerByUsername("testcustomer3")).thenReturn(getCustomerFromUserDB("testcustomer3"));
		when(fakeDBHandler.selectCustomerByUsername("uncreateduser")).thenReturn(getCustomerFromUserDB("uncreateduser"));
		when(fakeDBHandler.selectCustomerByUsername("testemployee")).thenReturn(getCustomerFromUserDB("testemployee"));
	}
	
	private User getCustomerFromUserDB(String username) {
		for(User user : userDB) {
			if(user.getUsername().equals(username) && user.isEmployee() == false) {
				return user;
			}
		}
		return null;
	}
	
	@Test
	public void testMakeMoneyTransfer() {
		assertEquals(true, service.makeMoneyTransfer(testCustomer1, cust1Acc1, testCustomer2, cust2Acc2, 50.00));
		assertEquals(false, service.makeMoneyTransfer(uncreateduser, uncreatedAcc, testCustomer2, cust2Acc2, 100.00));
		assertEquals(false, service.makeMoneyTransfer(testCustomer2, cust2Acc2, uncreateduser, uncreatedAcc, 200.00));
	}
	
	@Test
	public void testChangeBalance() {
		assertEquals(true, service.changeBalance(cust1Acc1, testCustomer1, 0.00));
		assertEquals(true, service.changeBalance(cust1Acc1, testCustomer1, 2000.00));
		assertEquals(false, service.changeBalance(uncreatedAcc, uncreateduser, 2000.00));
	}
	
	@Test
	public void testCreateNewUser() {
		assertEquals(true, service.createNewUser(uncreateduser));
		assertEquals(false, service.createNewUser(testDuplicateUser));
	}
	
	@Test
	public void testLogIn() {
		assertEquals(testCustomer1, service.logIn("testcustomer1", "good p4ssword"));
		assertEquals(testCustomer2, service.logIn("testcustomer2", "good p4ssword"));
		assertEquals(testCustomer3, service.logIn("testcustomer3", "good p4ssword"));
		assertEquals(testEmployee, service.logIn("testemployee", "good p4ssword"));
		assertEquals(null, service.logIn("testcustomer1", "incorrect password"));
		assertEquals(null, service.logIn("incorrect username", "good p4ssword"));
	}
	
	@Test
	public void testCreateNewBankAccount() {
		assertNotNull(service.createNewBankAccount(testCustomer1, "testacc6", 70.00));
	}
	
	@Test
	public void testGetBankAccountsAwaitingApproval() {
		assertNotNull(service.getBankAccountsAwaitingApproval());
	}
	
	@Test
	public void testGetCustomerBankAccounts() {
		assertEquals(Collections.EMPTY_LIST, service.getCustomerBankAccounts(testCustomer3));
		assertNotNull(service.getCustomerBankAccounts(testCustomer1));
		assertNotNull(service.getCustomerBankAccounts(testCustomer2));
	}
	
	@Test
	public void testValidateBankAccount() {
		assertEquals(true, service.validateBankAccount(cust1Acc1, testCustomer1));
		assertEquals(true, service.validateBankAccount(cust1Acc2, testCustomer1));
		assertEquals(true, service.validateBankAccount(cust2Acc2, testCustomer2));
	}
	
	@Test
	public void testGetCustomersOrderedByLastName() {
		assertNotNull(service.getCustomersOrderedByLastName());
	}
	
	@Test
	public void testGetCustomerByUsername() {
		assertEquals(testCustomer1, service.getCustomerByUsername("testcustomer1"));
		assertEquals(testCustomer2, service.getCustomerByUsername("testcustomer2"));
		assertEquals(testCustomer3, service.getCustomerByUsername("testcustomer3"));
		assertNull(service.getCustomerByUsername("uncreateduser"));
		assertNull(service.getCustomerByUsername("testemployee"));
	}
	
	@Test
	public void testIsValidUsername() {
		assertEquals(false, service.isValidUsername("bob"));
		assertEquals(true, service.isValidUsername("bobbathalonious"));
		assertEquals(true, service.isValidUsername("bobby rich boy"));
		assertEquals(true, service.isValidUsername("  bobby1rich2boy  "));
		assertEquals(false, service.isValidUsername("[bobby_rich_boy]"));
		assertEquals(false, service.isValidUsername("bobby is the richest rich boy in the whole world"));

	}
	
	@Test
	public void testIsValidPassword() {
		assertEquals(false, service.isValidPassword("bob"));
		assertEquals(true, service.isValidPassword("bobbathalonious"));
		assertEquals(true, service.isValidPassword("bobby rich boy"));
		assertEquals(true, service.isValidPassword("  bobby1rich2boy  "));
		assertEquals(false, service.isValidPassword("[bobby_rich_boy]"));
		assertEquals(false, service.isValidPassword("bobby is the richest rich boy in the whole world"));
	}
	
	@Test
	public void testPasswordsMatch() {
		assertEquals(true, service.passwordsMatch("validpassword1", "validpassword1"));
		assertEquals(false, service.passwordsMatch("validpassword1", "validpassword2"));
	}
	
	@Test
	public void testIsValidName() {
		assertEquals(true, service.isValidName("bob"));
		assertEquals(true, service.isValidName("bobbathalonious"));
		assertEquals(false, service.isValidName("bobby rich boy"));
		assertEquals(false, service.isValidName("  bobby1rich2boy  "));
		assertEquals(false, service.isValidName("[bobby_rich_boy]"));
		assertEquals(false, service.isValidName("bobby is the richest rich boy in the whole world"));
	}
	
	@Test
	public void testIsValidBankAccountName() {
		assertEquals(true, service.isValidBankAccountName("bob"));
		assertEquals(true, service.isValidBankAccountName("bobbathalonious"));
		assertEquals(true, service.isValidBankAccountName("bobby rich boy"));
		assertEquals(true, service.isValidBankAccountName("  bobby1rich2boy  "));
		assertEquals(false, service.isValidBankAccountName("[bobby_rich_boy]"));
		assertEquals(false, service.isValidBankAccountName("bobby is the richest rich boy in the whole world"));
	}
	
	@Test
	public void testIsValidLogIn() {
		assertEquals(true, service.isValidLogIn("bob", "bobpass"));
		assertEquals(true, service.isValidLogIn("bobbathalonious", "bobby rich boy"));
		assertEquals(true, service.isValidLogIn("bobbatha 123", "  bobby1rich2boy  "));
		assertEquals(false, service.isValidLogIn("[bobby_rich_boy]", "  bobby1rich2boy  "));
		assertEquals(true, service.isValidLogIn("bob", "bobby is the richest rich boy in the whole world"));
	}
	
	@Test
	public void testStringMatchesPattern() {
		assertEquals(true, service.stringMatchesPattern("bob", Service.GENERAL_PATTERN));
		assertEquals(true, service.stringMatchesPattern("bob", Service.NAME_PATTERN));
		assertEquals(true, service.stringMatchesPattern("bobbathalonious", Service.GENERAL_PATTERN));
		assertEquals(true, service.stringMatchesPattern("bobbathalonious", Service.NAME_PATTERN));
		assertEquals(true, service.stringMatchesPattern("bobby rich boy", Service.GENERAL_PATTERN));
		assertEquals(false, service.stringMatchesPattern("bobby rich boy", Service.NAME_PATTERN));
		assertEquals(true, service.stringMatchesPattern("  bobby1rich2boy  ", Service.GENERAL_PATTERN));
		assertEquals(false, service.stringMatchesPattern("  bobby1rich2boy  ", Service.NAME_PATTERN));
		assertEquals(false, service.stringMatchesPattern("[bobby_rich_boy]", Service.GENERAL_PATTERN));
		assertEquals(false, service.stringMatchesPattern("[bobby_rich_boy]", Service.NAME_PATTERN));
		assertEquals(true, service.stringMatchesPattern("bobby is the richest rich boy in the whole world", 
				Service.GENERAL_PATTERN));
		assertEquals(true, service.stringMatchesPattern("bobby is the richest rich boy in the whole world", 
				Service.GENERAL_PATTERN));
	}
}
