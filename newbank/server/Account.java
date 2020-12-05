package newbank.server;

public class Account {
	
	private String accountName;
	private double openingBalance;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
	}
	
	public String toString() {
		return (accountName + ": " + openingBalance);
	}

	public void addToAccount(double money){
		this.openingBalance += money;
	}

	public boolean removeFromAccount(double money){
		if (openingBalance >= money){
			this.openingBalance -= money;
			return true;
		}
		else{
			return false;
		}
	}
}
