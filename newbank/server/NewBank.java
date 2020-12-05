package newbank.server;

import java.util.HashMap;

public class NewBank {

	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;
	
	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}
	
	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account("Main", 1000.0));
		bhagy.setPassword("bhagy123");
		customers.put("Bhagy", bhagy);
		
		Customer christina = new Customer();
		christina.addAccount(new Account("Savings", 1500.0));
		christina.addAccount(new Account("Checking", 350.0));
		christina.addAccount(new Account("Main", 2000.0));
		christina.setPassword("christina123");
		customers.put("Christina", christina);
		
		Customer john = new Customer();
		john.addAccount(new Account("Checking", 250.0));
		john.setPassword("john123");
		customers.put("John", john);
	}
	
	public static NewBank getBank() {
		return bank;
	}

	// checks that the userName is in customers, and that the password given corresponds to that customer
	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if(customers.containsKey(userName) && customers.get(userName).getPassword().equals(password)) {
			return new CustomerID(userName);
		}
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {
		if(customers.containsKey(customer.getKey())) {
			switch(request) {
			case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
			case "NEWACCOUNT <Name>" : return addAccount(customer);

			default : return "FAIL";
			}
		}
		return "FAIL";
	}

	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

	private String addAccount(CustomerID customer) {
		if (showMyAccounts(customer).contains("<Name>")) {
			return "FAIL";
		} else {
			customers.get(customer.getKey()).addAccount(new Account("<Name>", 0.0));
			return "SUCCESS";
		}
	}

}
