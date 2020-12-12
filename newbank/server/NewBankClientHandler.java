package newbank.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NewBankClientHandler extends Thread{

	private NewBank bank;
	private BufferedReader in;
	private PrintWriter out;


	public NewBankClientHandler(Socket s) throws IOException {
		bank = NewBank.getBank();
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
	}

	public void run() {
		// keep getting requests from the client and processing them
		try {
			// ask for user name
			out.println("Welcome on New Bank");
			out.println("Enter 1 to log in");
			out.println("Enter 2 to Sign up");
			String connectPath = in.readLine();
			if (connectPath.equals("1")) {
				signIn();
			} else {
				out.println("Choose a username");
				String userName = in.readLine();
				String password = createPassword();
				out.println("thank you !");
				bank.addNewUser(userName);
				connect(userName, password);

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

	private void signIn(){
		try {
			out.println("Enter Username");
			String userName = in.readLine();
			// ask for password
			out.println("Enter Password");
			String password = in.readLine();
			out.println("Checking Details...");
			connect(userName, password);
		}
		catch (IOException e) {
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

	private void connect(String userName, String password){
		// authenticate user and get customer ID token from bank for use in subsequent requests
		CustomerID customer = bank.checkLogInDetails(userName, password);
		// if the user is authenticated then get requests from the user and process them
		if(customer != null) {
			out.println("Log In Successful. What do you want to do?");
			while(true) {
				try {
					String request = in.readLine();
					System.out.println("Request from " + customer.getKey());
					String responce = bank.processRequest(customer, request);
					out.println(responce);
				}		catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String createPassword(){
		String password = "password";
		String confirmation = "confirmation";
		try {
			while (!password.equals(confirmation)) {
				out.println("Choose a password");
				password = in.readLine();
				out.println("Confirm your password");
				confirmation = in.readLine();
				if (!password.equals(confirmation)) {
					out.println("the password and the confirmation does not match. Please retry.");
				}
			}
		}
		catch (IOException e) {
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
		return password;
	}


}
