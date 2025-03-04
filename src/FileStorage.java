import java.io.FileWriter;
import java.io.IOException;

public class FileStorage {
    public static void savePasswordToFile(String service, String username, String encryptedPassword) {
        String fileName = "passwords.txt";
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.append("Service: " + service + "\n");
            writer.append("Username: " + username + "\n");
            writer.append("Password: " + encryptedPassword + "\n");
            writer.append("------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
