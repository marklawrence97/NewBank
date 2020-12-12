package newbank.client;

import java.util.ArrayList;
import java.util.List;
import static java.lang.System.out;

public class ClientDisplay {

    public ClientDisplay(){}

    public static void clientServiceIntroduction(){
        System.out.println("|-----------------------------------|");
        System.out.println("|Welcome to NewBank banking service.|");
        System.out.println("|-----------------------------------|");
    }

    public static void clientCreatedAnnouncement(String password){
        System.out.println("Congratulations, your account as been created !");
        System.out.println("£10 has been credited on this account as a welcome gift.");
        System.out.println("To connect later on, your password is: " + password);
    }

    public static List<String> clientServiceMenu(){
        List<String> instructionsList = new ArrayList<>();
        instructionsList.add("|----------------------------|");
        instructionsList.add(" Please SELECT a service by typing below:");
        instructionsList.add("|----------------------------|");
        instructionsList.add("1.SHOWMYACCOUNTS");
        instructionsList.add("2.WITHDRAW");
        instructionsList.add("3.DEPOSIT");
        instructionsList.add("4.TRANSFERMONEYTOPERSONAL");
        instructionsList.add("5.TRANSFERMONEYTOEXTERNALACCOUNT");
        return instructionsList;
    }

}
