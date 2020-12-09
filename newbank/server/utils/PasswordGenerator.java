package newbank.server.utils;

import java.util.Random;

public class PasswordGenerator {
    private static final Random rand = new Random();
    private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     *
     * @param length length of the password, an int higher than 0.
     * @return a String password
     */
    public static String generate(int length){
        String password = "";
        while (!PasswordValidator.isValidPassword(password)){
            password = "";
            for (int i=0; i<length ; i++){
                int randomNumber = rand.nextInt(61);
                password += characters.charAt(randomNumber);
            }
        }
        return password;
    }
}
