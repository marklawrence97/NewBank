package newbank.server.utils;

import java.util.regex.Pattern;

public class PasswordValidator {

    public static boolean isValidPassword(String password) {
        if(!Pattern.compile("[a-z]").matcher(password).find()) {
            return false;
        }

        if(!Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;
        }

        if(!Pattern.compile("\\d").matcher(password).find()) {
            return false;
        }

        if (password.length() < 8) {
            return false;
        }

        return true;
    }
}
