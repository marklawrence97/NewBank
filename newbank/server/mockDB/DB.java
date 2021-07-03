package newbank.server.mockDB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DB {
    /**
     * This class provides a variety of helper methods for interacting with the DB.
     *
     * Key methods:
     *
     * createCustomer: Creates a new customer on the DB
     * createAccount: Creates a new account on the DB
     * getCustomer: queries the DB for the customer
     * getAllAccounts: queries the DB for all accounts
     *
     * This is a rudimentary method of introducing persistent data storage to the bank.
     **/

    public static void createCustomer(String username, String name, String password) {
        String customerID = generateUID();

        writeToCSV("customers", new String[] {username, name, password, customerID});
    }

    public static void createAccount(String customerID, String accountName, int initialBalance) {
        String accountID = generateUID();
        String balance = Integer.toString(initialBalance);

        writeToCSV("accounts", new String[]{accountName, accountID, customerID, balance});
    }

    public static String[] getCustomer(String username) {
/*      This method searches through the customer DB and either returns the Row containing the customer data.
*       If the DB does not contain the customer then will return an empty array. */

        try {
            String line;
            BufferedReader reader = readFromCSV("customers");
            do {
                line = reader.readLine();
                String[] row = CSVRowToStringArray(line);

                if (row[0].equals(username)) {
                    return row;
                }
            } while (line != null);
        } catch (Exception err) {
            err.printStackTrace();
        }

        return new String[]{};
    }

    public static List<String[]> getAllAccounts(String customerID) {
    /*  This method searches through the accounts DB and returns a list containing all of the accounts in the DB.
    If there are no accounts belonging to the user then it returns an empty list. */
        List accounts = new ArrayList();

        try {
            String line;
            BufferedReader reader = readFromCSV("accounts");
            line = reader.readLine();
            while (line != null){
                String[] row = CSVRowToStringArray(line);

                if (row[2].equals(customerID)) {
                    accounts.add(row);
                }

                line = reader.readLine();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }

        return accounts;
    }

    protected static void writeToCSV(String fileName, String[] data){
//        This method will append a new row to a given csv. If the file does not exist it will create it.

        String filePath = String.format("./newbank/server/mockDB/data/%s.csv", fileName);

        try {
            FileWriter writer = new FileWriter(filePath, true);
            String row = stringArrayToCSVRow(data);
            writer.write(row);
            writer.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    protected static BufferedReader readFromCSV(String fileName) throws IOException {
        String filePath = String.format("./newbank/server/mockDB/data/%s.csv", fileName);

        return new BufferedReader(new FileReader(filePath));
    }

    protected static String stringArrayToCSVRow(String[] data) {
/*      Takes an array of strings: ["array", "of", "strings"], and converts into csv row -> "array, of, strings".
        Returns an empty string if data is empty. */

        StringBuilder row = new StringBuilder();

        row.append(data[0]);
        for (int column = 1; column < data.length; column++) {
            row.append(',');
            row.append(data[column]);
        }

        row.append('\n');
        return data.length > 0 ? row.toString() : "";
    }

    protected static String[] CSVRowToStringArray(String row) {
/*      Takes a row from the CSV file and returns a list of strings. E.g. "list, of, strings," ->
        ["list", "of", "strings"]  */

        try {
            return row.split(",");
        } catch (Exception err) {
            return new String[]{};
        }
    }

    private static String generateUID() {
//        Generates random ID for that will act as an identifier for the customer.

        return UUID.randomUUID().toString();
    }
}

