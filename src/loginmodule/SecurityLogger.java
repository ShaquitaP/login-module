package loginmodule;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SecurityLogger implements ILogger{

    @Override
    public void logEvent(String eventType, String username, boolean success, String details) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("security.log", true));
            writer.println("[" + timestamp + "]" + eventType + ": " + username + " - " + success + " - " + details);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void logEvent(String eventType, String username, boolean success) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("security.log", true));
            writer.println("[" + timestamp + "]" + eventType + ": " + username + " - " + success);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
