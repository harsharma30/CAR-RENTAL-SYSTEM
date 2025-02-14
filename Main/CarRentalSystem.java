package Main;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CarRentalSystem {
    private static final Connection conn = DatabaseConnection.getConnection();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("✅ Connected to database.");

            while (true) {
                System.out.println("\nSelect an option:");
                System.out.println("1. User Login");
                System.out.println("2. Admin Login");
                System.out.println("3. Register as User");
                System.out.println("4. Register as Admin");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        userLogin();
                        break;
                    case 2:
                        adminLogin();
                        break;
                    case 3:
                        Register.registerAsUser();
                        break;
                    case 4:
                        Register.registerAsAdmin();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void userLogin() {
        System.out.println("\n--- User Login ---");
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE userid=? AND username=? AND password=?");
            stmt.setInt(1, userId);
            stmt.setString(2, username);
            stmt.setString(3, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("✅ User Login successful.");
                UserDashboard.showUserMenu(username , userId); 
            } else {
                System.out.println("❌ Invalid User credentials.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void adminLogin() {
        System.out.println("\n--- Admin Login ---");
        System.out.print("Enter Admin ID: ");
        int adminId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter admin username: ");
        String adminUsername = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String adminPassword = scanner.nextLine();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM admins WHERE admin_id=? AND username=? AND password=?");
            stmt.setInt(1, adminId);
            stmt.setString(2, adminUsername);
            stmt.setString(3, adminPassword);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("✅ Admin Login successful.");
                AdminDashboard.showAdminMenu(adminUsername); 
            } else {
                System.out.println("❌ Invalid Admin credentials.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
