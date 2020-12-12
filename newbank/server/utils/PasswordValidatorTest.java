package newbank.server.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class isValidPasswordTest {

    @Test
    public void shouldReturnFalseForInvalidPasswords() {
        String noUpperCase = "invalidpassword1";
        String noLowerCase = "INVALIDPASSWORD1";
        String noDigit = "invalidPASSWORD";
        String incorrectLength = "invalid";

        String[] invalidPasswords = new String[]{noUpperCase, noLowerCase, noDigit, incorrectLength};

        for (String invalidPassword: invalidPasswords) {
            assertFalse(PasswordValidator.isValidPassword(invalidPassword));
        }
    }

    @Test
    public void shouldReturnTrueForValidPasswords() {
        String validPassword = "validPasssword1";

        assertTrue(PasswordValidator.isValidPassword(validPassword));
    }
}