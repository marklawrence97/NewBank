package newbank.server;

import newbank.client.ClientDisplay;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ClientInfoStatus;

public class NewBankClientHandler extends Thread{
	
	private newbank.server.NewBank bank;
	private BufferedReader in;
	private PrintWriter out;
	private Boolean menuSelect;
	private ClientDisplay clientDisplay;
	public NewBankClientHandler(Socket s) throws IOException {
		bank = newbank.server.NewBank.getBank();
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
	}
	
	public void run() {
		// keep getting requests from the client and processing them
		try {
			newbank.server.CustomerID customer;
			while (true){
				String userName = getUserInput("Enter Username");
				String password = getUserInput("Enter Password");
				out.println("Checking Details...");
				customer = bank.checkLogInDetails(userName, password);
				if (customer != null){
					break;
				}
				out.println("Log In Failed. Please re-enter your details");
			}
				//out.println("Log In Successful. What do you want to do?");
			while(true) {
				// if the user is authenticated then get requests from the user and process them
				for (String ins : clientDisplay.clientServiceMenu()){
					out.println(ins);
				}
				String request = in.readLine();
				System.out.println("Request from " + customer.getKey());
				String response = bank.processRequest(customer, request);
				out.println(response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

	public String getUserInput(String message) throws IOException{
		this.out.println(message);
		return this.in.readLine();
	}

}
