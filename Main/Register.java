package Main;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Register {
    private static final Connection conn = DatabaseConnection.getConnection();
    private static final Scanner scanner = new Scanner(System.in);

    public static void registerAsUser() {
        System.out.println("\n--- Register as User ---");
        System.out.print("Enter unique User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (userid, username, password) VALUES (?, ?, ?)");
            stmt.setInt(1, userId);
            stmt.setString(2, username);
            stmt.setString(3, password);
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("✅ User registered successfully.");
            } else {
                System.out.println("❌ User registration failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void registerAsAdmin() {
        System.out.println("\n--- Register as Admin ---");
        System.out.print("Enter unique Admin ID: ");
        int adminId = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter admin username: ");
        String adminUsername = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String adminPassword = scanner.nextLine();

        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO admins (admin_id, username, password) VALUES (?, ?, ?)");
            stmt.setInt(1, adminId);
            stmt.setString(2, adminUsername);
            stmt.setString(3, adminPassword);
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("✅ Admin registered successfully.");
            } else {
                System.out.println("❌ Admin registration failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
