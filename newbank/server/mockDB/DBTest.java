package newbank.server.mockDB;

import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// This annotation is for using non-static properties in tear down.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DBTest {
    String fileName = "test";
    String filePath = String.format("./newbank/server/mockDB/data/%s.csv", fileName);
    File file = new File(filePath);

    @DisplayName("writeToCSV should handle writing multiple records to a file")
    @Test
    public void testWriteToCSV() {
        try {
            file.createNewFile();
        } catch (IOException err){}

        String[] testAccount = new String[]{"John1", "John Hamm", "12345", "Password1"};
        String[] testAccount2 = new String[]{"Larry", "Larry David", "12345", "Password1"};
        String[] testAccount3 = new String[]{"Jeff", "Jeff Greene", "12345", "Password1"};

        DB.writeToCSV(fileName, testAccount);
        DB.writeToCSV(fileName, testAccount2);
        DB.writeToCSV(fileName, testAccount3);

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));

            String line = reader.readLine();
            assertEquals("John1,John Hamm,12345,Password1", line);
            line = reader.readLine();
            assertEquals("Larry,Larry David,12345,Password1", line);
            line = reader.readLine();
            assertEquals("Jeff,Jeff Greene,12345,Password1", line);

        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    @AfterAll
    public void tearDown() {
        file.delete();
    }
}