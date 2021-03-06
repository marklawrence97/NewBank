package newbank.server;

import java.util.HashMap;
import static java.lang.System.out;
public class NewBank {

	private static final NewBank bank = new NewBank();
	public HashMap<String,Customer> customers;
	public HashMap<String,Double> account;

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
		account.put("Christina", 2000.0);

		Customer john = new Customer();
		john.addAccount(new Account("Checking", 250.0));
		john.setPassword("john123");
		customers.put("John", john);
		account.put("John", 1500.0);
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
		String command = request.split(" ")[0];

		if(customers.containsKey(customer.getKey())) {
			switch(command) {
				case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
				case "NEWACCOUNT" : return addAccount(customer,request);
				case "WITHDRAW" : return withdrawMoney(customer,request);
				case "DEPOSIT" : return depositMoney(customer, request);
				case "MOVE" : return transferMoneyToPersonal(customer);
				case "PAY" : return payThirdParty(customer,request);
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
			String customerToDeposit = request.split(" ")[1];
			double amountToDeposit = Double.parseDouble(request.split(" ")[2]);
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

	private String addAccount(CustomerID customer, String request) {
		try{
			String[] parts = request.split(" ");
			String accountName = parts[1];
			if (showMyAccounts(customer).contains(accountName)) {
				return "FAIL";
			} else {
				customers.get(customer.getKey()).addAccount(new Account(accountName, 0.0));
				return "SUCCESS";
			}
		}
		catch (Exception e){
			return ("FAIL");
		}
	}

	private String withdrawMoney(CustomerID customer, String request) {
		try{
			String[] inputRequest = request.split(" ");
			double withdrawalAmount = Double.parseDouble(inputRequest[1]);
			if (customers.get(customer.getKey()).getMainAccount().removeFromAccount(withdrawalAmount)){
				System.out.println("The amount withdrawn is: " + String.valueOf(withdrawalAmount));
				showMyAccounts(customer);
				return "SUCCESS";
			}
		}catch(Exception ex){
			System.out.println("Withdrawal Input wasn't successful " + ex);
			return "FAIL";
		}
		return "FAIL";
	}

	private String depositMoney(CustomerID customer, String request) {
		try{
			String[] inputRequest = request.split(" ");
			double depositAmount = Double.parseDouble(inputRequest[1]);
			customers.get(customer.getKey()).getMainAccount().addToAccount(depositAmount);
			return "SUCCESS";
		}catch(Exception ex){
			System.out.println("Deposit input wasn't successful " + ex);
			return "FAIL";
		}
	}

	private String transferMoneyToPersonal(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}
}
