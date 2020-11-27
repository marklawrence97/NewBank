package newbank.server.mockDB;

import java.util.List;

public class ExampleUsage {

    public static void main(String[] args) {
//      Create rows
        DB.createCustomer("LarryDavid123", "Larry David", "Password1");
        DB.createAccount("LarryDavid123", "Bank account", 1500);

//      Query DB
        String[] row = DB.getCustomer("LarryDavid123");

        System.out.println("User:");
        for (int column = 0; column < row.length; column++){
            System.out.println(row[column]);
        }

        System.out.println("Accounts:");
        List<String[]> accounts = DB.getAllAccounts("LarryDavid123");

        for (int account = 0; account < accounts.size(); account++){
            System.out.println(accounts.get(account)[0]);
        }

    }
}
