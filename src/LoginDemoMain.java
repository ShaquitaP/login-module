import java.io.IOException;
import java.io.PrintWriter;

public class LoginDemoMain {

    public static void main(String[] args) {
        String[][] userDatabase = {
                {"scientist", "+1:d21jB4'v"},
                {"engineer", "G^5&hM52L94"},
                {"security", "wyD%Z$737cO"}
        };
        try {
            PrintWriter writer = new PrintWriter("database.txt");
            for (String[] userPair : userDatabase) {
                writer.println(userPair[0] + "," + userPair[1]);
            }
            writer.close();
            LoginGUI lg = new LoginGUI();
        } catch (IOException e) {
            System.out.println("Could not print credentials to User Database.");
        }

    }
}
