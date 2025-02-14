package Main;

import services.CarService;
import utils.ScannerUtil;

import java.util.List;
import java.util.Scanner;

public class AdminDashboard {
    private static Scanner scanner = ScannerUtil.getScanner();

    public static void showAdminMenu(String adminUsername) {
        System.out.println("\nWelcome, Admin " + adminUsername + "!");
        
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Car");
            System.out.println("2. Edit Car");
            System.out.println("3. Delete Car");
            System.out.println("4. View All Bookings");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: addCar(); break;
                case 2: editCar(); break;
                case 3: deleteCar(); break;
                case 4: viewAllBookings(); break;
                case 5: System.out.println("Logging out..."); return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addCar() {
        System.out.print("Enter car ID to add: ");
        int carId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter car model: ");
        String model = scanner.nextLine();

        System.out.print("Enter car price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Is this car available? (yes/no): ");
        String availabilityInput = scanner.nextLine().trim().toLowerCase();
        boolean isAvailable = availabilityInput.equals("yes");

        if (CarService.addCar(carId, model, price, isAvailable)) {
            System.out.println("Car added successfully!");
        } else {
            System.out.println("Car addition failed. Car ID might already exist.");
        }
    }

    private static void editCar() {
        System.out.print("Enter car ID to edit: ");
        int carId = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Enter new car model: ");
        String model = scanner.nextLine();

        System.out.print("Enter new car price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Is this car available? (yes/no): ");
        String availabilityInput = scanner.nextLine().trim().toLowerCase();
        boolean isAvailable = availabilityInput.equals("yes");

        if (CarService.editCar(carId, model, price, isAvailable)) {
            System.out.println("Car updated successfully!");
        } else {
            System.out.println("Car update failed. Please check the Car ID.");
        }
    }
    private static void deleteCar() {
        System.out.print("Enter car ID to delete: ");
        int carId = scanner.nextInt();
        scanner.nextLine();

        if (CarService.deleteCar(carId)) {
            System.out.println("Car deleted successfully!");
        } else {
            System.out.println("Car deletion failed. Please check the Car ID.");
        }
    }

    private static void viewAllBookings() {
        List<String> bookings = CarService.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            bookings.forEach(System.out::println);  
        }
    }
}
