package newbank.server;

import newbank.server.utils.PasswordGenerator;
import java.util.ArrayList;

public class Customer {
	
	private ArrayList<Account> accounts;
	private String password;
	
	public Customer() {
		accounts = new ArrayList<>();
		password = PasswordGenerator.generate(8);
	}
	
	public String accountsToString() {
		String s = "";
		for(Account a : accounts) {
			s += a.toString() + "\n";
		}
		return s.trim();
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void addAccount(Account account) {
		accounts.add(account);		
	}
}
