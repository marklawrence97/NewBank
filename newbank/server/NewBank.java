package newbank.server;

import java.util.HashMap;
import static java.lang.System.out;
public class NewBank {

	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;
	private HashMap<String,Double> account;

	private NewBank() {
		customers = new HashMap<>();
		account = new HashMap<>();
		addTestData();
	}

	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account("Main", 1000.0));
		bhagy.setPassword("bhagy123");
		customers.put("Bhagy", bhagy);
		account.put("Bhagy", 1000.0);

		Customer christina = new Customer();
		christina.addAccount(new Account("Main", 2000.0));
		christina.addAccount(new Account("Savings", 1500.0));
		christina.addAccount(new Account("Checking", 350.0));
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
				case "WITHDRAW" : return withdrawMoney(customer);
				case "DEPOSIT" : return depositMoney(customer);
				case "TRANSFERMONEYTOPERSONAL" : return transferMoneyToPersonal(customer);
				case "TRANSFERMONEYTOEXTERNALACCOUNT" : return transferMoneyToExternal(customer);
			default : return "FAIL";
			}
		}
		return "FAIL";
	}

	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

	private String payThirdParty(CustomerID customer, String request){
		try{
			String[] parts = request.split(" ");
			String customerToDeposit = parts[1];
			double amountToDeposit = Double.parseDouble(parts[2]);
			// Checks that the customerToDeposit exists in the bank and that the customer has enough money to transfer
			if(customers.containsKey(customerToDeposit) && customers.get(customer.getKey()).getMainAccount().removeFromAccount(amountToDeposit)){
				customers.get(customerToDeposit).getMainAccount().addToAccount(amountToDeposit);
				return ("SUCCESS");
			}
		}
		catch (Exception e){
			return("FAIL");
		}
		return ("FAIL");
	}

	private String addAccount(CustomerID customer) {
		if (showMyAccounts(customer).contains("<Name>")) {
			return "FAIL";
		} else {
			customers.get(customer.getKey()).addAccount(new Account("<Name>", 0.0));
			return "SUCCESS";
		}
	}

	private String withdrawMoney(CustomerID customer) {
		double balance = account.get(customer.getKey());
		String currentBalance = String.valueOf(balance);
		return "The new balance is:" + currentBalance;
	}

	private String depositMoney(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

	private String transferMoneyToPersonal(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

	private String transferMoneyToExternal(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}
}
