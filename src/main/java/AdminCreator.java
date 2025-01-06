import org.dealership.utils.PasswordUtil;

public class AdminCreator {
    public static void main(String[] args) {
        String username = "calingeorge";
        String password = "calinacho999";

        // Generate salt
        String salt = PasswordUtil.generateSalt();
        System.out.println("Salt: " + salt);

        // Hash the password with the generated salt
        String hashedPassword = PasswordUtil.hashPassword(password, salt);
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
