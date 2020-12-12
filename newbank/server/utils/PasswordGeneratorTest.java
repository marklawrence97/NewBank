package newbank.server.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordGeneratorTest {

    @Test
    public void shouldGenerateValidPasswords() {
        for (int i=0; i<10; i++){
            String validPassword = PasswordGenerator.generate(8);
            assertTrue(PasswordValidator.isValidPassword(validPassword));
        }
        for (int i=0; i<10; i++){
            String validPassword = PasswordGenerator.generate(11);
            assertTrue(PasswordValidator.isValidPassword(validPassword));
        }
    }
}

