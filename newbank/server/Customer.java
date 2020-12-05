package newbank.server;

import java.util.ArrayList;
import java.util.Random;

public class Customer {
	
	private ArrayList<Account> accounts;
	private String password;
	
	public Customer() {
		accounts = new ArrayList<>();
		Random rand = new Random();
		password = String.valueOf(rand.nextInt(10));
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

	// Assumes the first account in the list is the main one
	public Account getMainAccount(){
		if (accounts.size()>0){
			return accounts.get(0);
		}
		return null;
	}
}
