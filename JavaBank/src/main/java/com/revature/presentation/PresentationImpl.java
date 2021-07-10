package com.revature.presentation;

import java.util.List;
import java.util.Scanner;

import com.revature.MainDriver;
import com.revature.models.BankAccount;
import com.revature.models.User;
import com.revature.service.Service;
import com.revature.util.MoneyUtils;

public class PresentationImpl implements Presentation {

	private Service service;
	private Scanner sc = new Scanner(System.in);
	private static boolean firstPickBankAccountLoop = true;
	
	public PresentationImpl(Service service) {
		this.service = service;
	}
	
	@Override
	public void welcomeMessage() {
		System.out.println("========== Welcome to Perry Bank! ==========");
	}

	@Override
	public User logInOrCreateNewUserPrompt() {
		User user = null;
		
		while(user == null) {
			System.out.println("How would you like to proceed?");
			System.out.println("(1) Log In\n"
					+ "(2) Create New Account\n"
					+ "(3) Exit Application");
			
			switch(sc.nextLine().toLowerCase()) {
				case "1":
					user = logInPrompt();
					break;
				case "2":
					createNewUserPrompt();
					break;
				case "3":
					exitApplicationLog("Anonymous User");
					System.exit(0);
				default:
					System.out.println("Invalid input. Please, try again.");
					break;
			}
		}
		return user;
	}
	
	@Override
	public User logInPrompt() {
		User user = null;
		boolean loop = true;
		
		while(true) {
			System.out.println("========== LOG IN ==========");
			System.out.print("Enter your username: ");
			String username = sc.nextLine();
			
			System.out.print("Enter your password: ");
			String password = sc.nextLine();
			
			if(service.isValidLogIn(username, password)) {
				user = service.logIn(username, password);
				if(user != null) {	// successful login
					return user;
				}
				else {	// unsuccessful login
					System.out.print("Incorrect information. ");
					while(loop) {
						System.out.println("Would you like to try to log in again? (y/n)");
						switch(sc.nextLine()) {
						case "y":
							loop = false;
							break;
						case "n":
							return null;
						default:
							loop = true;
							System.out.println("Invalid input. Please, try again.");
							break;
						}
					}
				}
			}
			else {
				System.out.println("Remember: Usernames and Passwords follow this format:\n"
						+ "VALID CHARACTERS: numbers, letters, spaces\n"
						+ "LENGTH: " + Service.MIN_CHARACTERS + "-" + Service.MAX_CHARACTERS + " characters\n");
			}	
		}
	}
	
	public void createNewUserPrompt() {
		boolean bigLoop = true;
		boolean loop = true;
		String username, password, passwordAgain, firstName, lastName;
		
		while(bigLoop) {
			loop = true;
			System.out.println("========== CREATE NEW ACCOUNT ==========");
			while(true) {
				System.out.println("Enter your username:\n"
						+ "VALID CHARACTERS: numbers, letters, spaces\n"
						+ "LENGTH: " + Service.MIN_CHARACTERS + "-" + Service.MAX_CHARACTERS + " characters");
				username = sc.nextLine().toLowerCase();
				if (service.isValidUsername(username)) {
					System.out.println();
					break;
				}
				else {
					System.out.println("Invalid input. Please, try again.\n");
				}
			}
			
			while(true) {
				while(true) {
					System.out.println("Enter your password:\n"
							+ "VALID CHARACTERS: numbers, letters, spaces\n"
							+ "LENGTH: " + Service.MIN_CHARACTERS + "-" + Service.MAX_CHARACTERS + " characters");
					password = sc.nextLine().toLowerCase();
					if (service.isValidPassword(password)) {
						System.out.println();
						break;
					}
					else {
						System.out.println("Invalid input. Please, try again.\n");
					}
				}

				System.out.println("Enter your password again:");
				passwordAgain = sc.nextLine().toLowerCase();
				if (service.passwordsMatch(password, passwordAgain)) {
					System.out.println();
					break;
				}
				else {
					System.out.println("Invalid input. Please, try again.\n");
				}
			}

			while(true) {
				System.out.println("Enter your first name:\n"
						+ "VALID CHARACTERS: letters\n"
						+ "LENGTH: Max " + Service.MAX_CHARACTERS + " characters");
				firstName = sc.nextLine().toLowerCase();
				if (service.isValidName(firstName)) {
					System.out.println();
					break;
				}
				else {
					System.out.println("Invalid input. Please, try again.\n");
				}
			}
			
			while(true) {
				System.out.println("Enter your last name:\n"
						+ "VALID CHARACTERS: letters\n"
						+ "LENGTH: Max " + Service.MAX_CHARACTERS + " characters");
				lastName = sc.nextLine().toLowerCase();
				if (service.isValidName(lastName)) {
					System.out.println();
					break;
				}
				else {
					System.out.println("Invalid input. Please, try again.\n");
				}
			}
			
			while(loop) {
				System.out.println("Is this information correct? (y/n):");
				System.out.println("If you would like to stop creating a new account, type 'stop'");
				
				System.out.println("Username: " + username + "\n"
						+ "Password: " + password + "\n"
						+ "First Name: " + firstName + "\n"
						+ "Last Name: " + lastName);
				
				switch(sc.nextLine().toLowerCase()) {
					case "y":
						service.createNewUser(new User(firstName, lastName, username, password, false));
						loop = false;
						bigLoop = false;
						System.out.println("Congratulations, " + firstName.toUpperCase() + ", on creating your new Perry Bank account!\n");
						break;
					case "n":
						loop = false;
						bigLoop = true;
						break;
					case "stop":
						loop = false;
						bigLoop = false;
						break;
					default:
						System.out.println("Invalid input. Please, try again.\n");
						break;
				}
			}
		}
	}

	@Override
	public BankAccount createNewBankAccountPrompt(User user) {
		String name = null;
		BankAccount newAcc = null;
		List<BankAccount> accs = service.getCustomerBankAccounts(user);
		
		System.out.println("========== CREATE NEW BANK ACCOUNT ==========");
		while(true) {
			System.out.println("Enter your desired bank account name\n"
					+ "VALID CHARACTERS: numbers, letters, spaces\n"
					+ "LENGTH: Max " + Service.MAX_CHARACTERS + " characters\n"
					+ "Enter 'back' to back out.");
			name = sc.nextLine().toLowerCase();
			if(name.equals("back")) {
//				System.out.println("Returning to main menu.");
				return null;
			}
			else if (containsAccountWithName(accs, name)) {
				System.out.println("You already have a bank account named '" + name + "'.\n");
			}
			else if (service.isValidBankAccountName(name)) {
				newAcc = service.createNewBankAccount(user, name, 0.00);
				initialDeposit(newAcc, user);
				System.out.println("Your new bank account, '" + name + "', has been created with a balance of " 
						+ MoneyUtils.toMoneyString(newAcc.getBalance()) + ". Please, await approval before trying to access it.\n");
				return newAcc;
			}
			else {
				System.out.println("Your account name does not meet the length requirement.\n");
			}
		}
	}

	@Override
	public void checkBalance(BankAccount acc) {
		System.out.println("The balance of bank account '" + acc.getName() + "' is " + MoneyUtils.toMoneyString(acc.getBalance()) + ".\n");
	}

	@Override
	public void deposit(BankAccount acc, User user) {
		String input = null;
		double inputNum = -1;
		
		while(true) {
			System.out.println("Enter the amount you would like to deposit.");
			System.out.println("Enter 'back' to back out.");
			
			input = sc.nextLine();
			if (input.equals("back")) {
				return;
			}
			try {
				inputNum = Double.parseDouble(input);
				if(inputNum < 0.00) {
					System.out.println("Cannot deposit a negative amount. Please, enter a positive amount.");
				}
				else {
					service.changeBalance(acc, user, acc.getBalance() + inputNum);
					System.out.println(MoneyUtils.toMoneyString(inputNum) + " has been successfully deposited into your "
					+ acc.getName() + " account!");
					return;
				}
			}
			catch(NumberFormatException e) {
				inputNum = -1;
				System.out.println("Invalid input. Please, try again.\n");
			}
		}
	}

	@Override
	public void withdraw(BankAccount acc, User user) {
		String input = null;
		double inputNum = -1;
		
		while(true) {
			System.out.println("Enter the amount you would like to deposit.");
			System.out.println("Enter 'back' to back out.");
			
			input = sc.nextLine();
			if (input.equals("back")) {
				return;
			}
			try {
				inputNum = Double.parseDouble(input);
				if(inputNum < 0.00) {
					System.out.println("Cannot withdraw a negative amount. Please, enter a positve amount.");
				}
				else if (inputNum > acc.getBalance()) {
					System.out.println("Cannot withdraw more than your account balance. Please, enter a smaller amount.");
				}
				else {
					service.changeBalance(acc, user, acc.getBalance()-inputNum);
					System.out.println("Here is your " + MoneyUtils.toMoneyString(inputNum) + "!\n");
					return;
				}
			}
			catch(NumberFormatException e) {
				inputNum = -1;
				System.out.println("Invalid input. Please, try again.\n");
			}
		}
	}

	@Override
	public void makeMoneyTransferToSelf(User user, BankAccount sendingAcc) {
		firstPickBankAccountLoop = true;
		while(true) {
			BankAccount receivingAcc = pickBankAccount(user, false);
			if(receivingAcc == null) {
				return;
			}
			else if (sendingAcc.equals(receivingAcc)) {
				System.out.println("Cannot make a money transfer to the same account.");
			}
			else {
				moneyTransferHelper(user, sendingAcc, user, receivingAcc);
				break;
			}
		}
	}
	
	public void makeMoneyTransferToOther(User sender, BankAccount sendingAcc) {
		int inputNum = -1;
		BankAccount receivingAcc = null;
		
		System.out.println("In order to make a money transfer to another account, you must provide the username and bank account name\n"
				+ "of the account you want to transfer money to.");
		
		while(true) {
			System.out.println("Please enter the receiving account's username");
			System.out.println("Enter 'back' to back out");
			
			String username = sc.nextLine().toLowerCase();
			if(username.equals("back")) {
//				System.out.println("Returning to previous menu");
				return;
			}
			else if (!service.stringMatchesPattern(username, Service.GENERAL_PATTERN)) {
				System.out.println("Remember: Usernames and Passwords follow this format:\n"
							+ "VALID CHARACTERS: numbers, letters, spaces\n"
							+ "LENGTH: " + Service.MIN_CHARACTERS + "-" + Service.MAX_CHARACTERS + " characters\n"
							+ "Please, try again.\n");
			}
			else {
				User receiver = service.getCustomerByUsername(username);
				if (receiver == null) {
					System.out.println("That user does not exist. Please, try again.\n");
				}
				else {
					List<BankAccount> receiverAccs = service.getCustomerBankAccounts(receiver);
					if (receiverAccs.isEmpty() || receiverAccs.equals(null) || allAccountsUnapproved(receiverAccs)) {
						System.out.println(username + " has no approved bank accounts to transfer to. Backing out...");
						return;
					}
					while(true) {
						System.out.println("Enter the index of one of the bank accounts owned by '" + username + "' to transfer money to");
						System.out.println("Enter 'back' to back out");
						printIndexedBankAccounts(receiverAccs);
						
						String input = sc.nextLine().toLowerCase();
						if (input.equals("back")) {
							return;
						}
						try {
							inputNum = Integer.parseInt(input);
							receivingAcc = receiverAccs.get(inputNum-1);
							moneyTransferHelper(sender, sendingAcc, receiver, receivingAcc);
							return;
						}
						catch(IndexOutOfBoundsException | NumberFormatException e) {
							inputNum = -1;
							System.out.println("Invalid input. Please, try again.");
						}
					}
				}
			}
		}
	}
	
	@Override
	public void displayCustomerOptions(User user) {
		boolean loop = true;
		
		System.out.println("\n========== WELCOME, " + user.getFirstName().toUpperCase() + "! ==========");
		
		while(loop) {
			System.out.println("Enter the index of the option you would like to pick.");
			System.out.println("(1) Select Bank Account\n"
					+ "(2) Create New Bank Account\n"
					+ "(3) Log Out\n"
					+ "(4) Exit Application");
			
			String input = sc.nextLine().toLowerCase();

			switch(input) {
			case "1":
				BankAccount acc = pickBankAccount(user, true);
				if(!(acc == null)) {
					displayBankAccountOptions(user, acc);
				}
				break;
			case "2":
				createNewBankAccountPrompt(user);
				break;
			case "3":
				userLoggedOutLog(user.getUsername());
				return;
			case "4":
				exitApplicationLog(user.getUsername());
				System.exit(0);
			default:
				System.out.println("Invalid input. Please, try again.\n");
				break;
			}
		}
	}
	

	@Override
	public void displayBankAccountOptions(User user, BankAccount acc) {
		boolean loop = true;
		
		while(loop) {
			System.out.println("Enter the index of the option you would like to pick.");
			System.out.println("Enter 'back' to back out.");
			System.out.println("(1) Check Balance\n"
					+ "(2) Make a Deposit\n"
					+ "(3) Make a Withdrawal\n"
					+ "(4) Make a Money Transfer to Another Bank Account");
			
			String input = sc.nextLine().toLowerCase();

			if(input.equals("back")) {
				return;
			}
			switch(input) {
			case "1":
				checkBalance(acc);
				break;
			case "2":
				deposit(acc, user);
				break;
			case "3":
				withdraw(acc, user);
				break;
			case "4":
				selectMoneyTransferType(user, acc);
				break;
			case "back":
				loop = false;
				break;
			default:
				System.out.println("Invalid input. Please, try again.\n");
			}
		}

	}
	
	@Override
	public BankAccount pickBankAccount(User user, Boolean displayMultipleTimes) {
		int inputNum = -1;
		BankAccount selectedBankAccount = null;
		
		List<BankAccount> accs = service.getCustomerBankAccounts(user);

		if(accs.isEmpty()) {
			System.out.println("You have no bank accounts, currently. Would you like to create one? (y/n)");
			String answer = sc.nextLine().toLowerCase();
			switch(answer) {
			case "y":
				createNewBankAccountPrompt(user);
				return null;
			case "n":
				return null;
			default:
				System.out.println("Invalid input. Please, try again.\n");
			}
		}
		
		if(displayMultipleTimes || !displayMultipleTimes && firstPickBankAccountLoop) {
			System.out.println("Enter the index of the bank account you would like to interact with. "
					+ "NOTE: You cannot interact with accounts that are awaiting approval.");
			System.out.println("Enter 'back' to back out.\n");
			printIndexedBankAccounts(accs);
			firstPickBankAccountLoop = false;
		}
		
		while(true) {			
			String input = sc.nextLine().toLowerCase();
			if (input.equals("back")) {
//				System.out.println("Returning to login menu.\n");
				return null;
			}
			try {
				inputNum = Integer.parseInt(input);
				selectedBankAccount = accs.get(inputNum-1);
				if(selectedBankAccount.isApproved()) {
					return selectedBankAccount;
				}
				System.out.println("Selected bank account is not approved, yet.");
			}
			catch(NumberFormatException | IndexOutOfBoundsException e) {
				inputNum = -1;
				System.out.println("Invalid input. Please, try again.");
			}
		}
	}
	
	@Override
	public void displayEmployeeOptions(User employee) {
		boolean loop = true;
		
		System.out.println("\n========== WELCOME, " + employee.getFirstName().toUpperCase() + "! ==========");
		
		while(loop) {
			System.out.println("Enter the index of the option you would like to pick.");
			System.out.println("(1) View Bank Accounts Awaiting Approval\n"
					+ "(2) View Customer's Bank Accounts\n"
					+ "(3) Log Out\n"
					+ "(4) Exit Application");
			
			String input = sc.nextLine().toLowerCase();

			switch(input) {
			case "1":
				approveBankAccountsPrompt(employee);
				break;
			case "2":
				viewCustomerBankAccountsPrompt(employee);
				break;
			case "3":
				userLoggedOutLog(employee.getUsername());
				return;
			case "4":
				exitApplicationLog(employee.getUsername());
				System.exit(0);
			default:
				System.out.println("Invalid input. Please, try again.\n");
			}
		}
	}


	@Override
	public void approveBankAccountsPrompt(User employee) {
		String input = null;
		int inputNum = -1;
		int numAccountsApproved = 0;
		List<BankAccount> awaitingApprovalList = service.getBankAccountsAwaitingApproval();
		
		System.out.println("Here are the bank accounts awaiting approval.");
		if (!awaitingApprovalList.isEmpty()) {
			System.out.println("Approve a bank account by entering its respective index. Type 'stop' to stop.");
			printIndexedBankAccountsWithUsername(awaitingApprovalList);

			input = sc.nextLine().toLowerCase();
			while(!input.equals("stop") && !awaitingApprovalList.isEmpty()) {
				try {
					if(awaitingApprovalList.isEmpty()) {
						System.out.println("There are no more bank accounts left to approve.");
						return;
					}
					inputNum = Integer.parseInt(input);
					BankAccount acc = awaitingApprovalList.get(inputNum-1);
					if(acc.isApproved()) {
						System.out.println("This account has already been approved.");
					}
					else {
						service.validateBankAccount(acc, employee);
						numAccountsApproved++;
						System.out.println(acc.getName() + " has been successfully approved.");
					}
				}
				catch(NumberFormatException e) {
					inputNum = -1;
					System.out.println("Invalid input. Please, try again.\n");
				}
				catch(IndexOutOfBoundsException e) {
					inputNum = -1;
					System.out.println("Invalid index. Please, try again.\n");
				}
				if(awaitingApprovalList.size() == numAccountsApproved) {
					System.out.println("There are no more bank accounts left to approve.\n");
					break;
				}
				input = sc.nextLine().toLowerCase();
			}
		}
	}
	
	@Override
	public void viewCustomerBankAccountsPrompt(User employee) {
		User customer = null;
		
		
		System.out.println("If you would like to see a list of all customers, ordered by last name, enter 'list'.");
		System.out.println("Enter the username of the customer whose bank accounts you would like to view.");
		System.out.println("Enter 'back' to back out.");
			
		while(true) {	
			String input = sc.nextLine().toLowerCase();
			
			if(input.equals("back")) {
				return;
			}
			else if(input.equals("list")) {
				List<User> customers = service.getCustomersOrderedByLastName();
				if (customers.isEmpty()) {
					System.out.println("We have no customers :(");
					break;
				}
				
				printCustomers(customers);
			}
			else {
				customer = service.getCustomerByUsername(input);
				if(customer == null) {
					System.out.println("No customer exists with username " + input + ". Please, try again.\n");
				}
				else {
					List<BankAccount> accs = service.getCustomerBankAccounts(customer);
					if(accs.isEmpty()) {
						System.out.println(input + " does not currently have any bank accounts.\n");
						break;
					}
					printBankAccounts(accs);
					break;
				}
			}
		}
	}
	
	private void printIndexedBankAccounts(List<BankAccount> accs) {
		for(int i = 0; i < accs.size(); i++) {
			System.out.println("(" + (i+1) + ")" + accs.get(i).toTabbedString());
		}
	}

	private void printIndexedBankAccountsWithUsername(List<BankAccount> accs) {
		for(int i = 0; i < accs.size(); i++) {
			System.out.println("(" + (i+1) + ")" + accs.get(i).toTabbedStringWithUsername());
		}
	}
	
	private void initialDeposit(BankAccount newAcc, User user) {
		boolean loop = true;
		
		System.out.println("Would you like to make an initial deposit into your new bank account? (y/n)");
		
		String input = sc.nextLine().toLowerCase();
		
		while(loop) {
			switch(input) {
			case "y":
				loop = false;
				deposit(newAcc, user);
				break;
			case "n":
				return;
			default:
				System.out.println("Invalid input. Please, try again.\n");
				break;
			}
		}
	}
	
	private void printCustomers(List<User> customers) {
		for(User customer : customers) {
			System.out.println(customer.toStringCustomers());
		}
	}
	
	private void printBankAccounts(List<BankAccount> accs) {
		for(BankAccount ba : accs) {
			System.out.println(ba.toString());
		}
	}
	
	private void selectMoneyTransferType(User user, BankAccount acc) {
		
		System.out.println("Select the type of money transfer you would like to make\n"
				+ "Enter 'back' to back out.\n"
				+ "(1) Transfer money to another one of your bank accounts\n"
				+ "(2) Transfer money to another user's bank account");
		
		String input = sc.nextLine().toLowerCase();
		
		switch(input) {
		case "1":
			makeMoneyTransferToSelf(user, acc);
			break;
		case "2":
			makeMoneyTransferToOther(user, acc);
			break;
		case "back":
			return;
		default:
			System.out.println("Invalid input. Please, try again.\n");
			break;
		}
	}
	
	private void moneyTransferHelper(User sender, BankAccount sendingAcc, User receiver, BankAccount receivingAcc) {
		double amount = -1.00;
		
		while(true) {
			System.out.println("Enter the amount of money you would like to transfer from " + sendingAcc.getName() + " to " + receivingAcc.getName());
			System.out.println("Enter 'back' to back out.");
			
			String input = sc.nextLine().toLowerCase();
			if(input.equals("back")) {
				return;
			}
			else {
				try {
					amount = Double.parseDouble(input);
					if(amount > 0) {
						if (sendingAcc.getBalance() >= amount) {
							service.makeMoneyTransfer(sender, sendingAcc, receiver, receivingAcc, amount);
							System.out.println("Successfully transferred " + MoneyUtils.toMoneyString(amount) + " from your '" 
									+ sendingAcc.getName() + "' account to the '" + receivingAcc.getName() + "' account of '" 
									+ receiver.getUsername() + "'.");
							break;
						}
						else {
							System.out.println("Your '" + sendingAcc.getName() + "' account does not contain enough funds "
									+ "to transfer " + MoneyUtils.toMoneyString(amount) + ".\n" 
									+ "'" + sendingAcc.getName() + "' currently only contains " 
									+ MoneyUtils.toMoneyString(sendingAcc.getBalance()) + ".\n");
						}
					}
					else {
						System.out.println("Cannot transfer a negative amount. Please, try again.");
					}
				}
				catch(NumberFormatException e) {
					amount = -1;
					if(input.equals("back")) {
						System.out.println("Invalid input. Please, try again.\n");
					}
				}
			}
		}
	}

	private boolean containsAccountWithName(List<BankAccount> accs, String name) {
		for(BankAccount ba : accs) {
			if(ba.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	private void userLoggedOutLog(String username) {
		MainDriver.loggy.info("User '" + username + "' logged out.");
	}
	
	private void exitApplicationLog(String username) {
		MainDriver.loggy.info("User '" + username + "' exited the application.");
	}
	
	private boolean allAccountsUnapproved(List<BankAccount> accs) {
		for(BankAccount ba : accs) {
			if (ba.isApproved()) {
				return false;
			}
		}
		return true;
	}
}
