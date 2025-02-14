package Main;

import services.CarService;
import models.Car;
import utils.ScannerUtil;
import java.util.List;
import java.util.Scanner;

public class UserDashboard {
    private static Scanner scanner = ScannerUtil.getScanner();

    public static void showUserMenu(String username, int userId) {
        System.out.println("\nWelcome, " + username + "!");

        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View Available Cars");
            System.out.println("2. Book Car");
            System.out.println("3. View My Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAvailableCars();
                    break;
                case 2:
                    bookCar(userId);
                    break;
                case 3:
                    getUserBookings(userId);
                    break;
                case 4:
                    cancelBooking(userId);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void viewAvailableCars() {
        List<Car> cars = CarService.getAvailableCars();
        if (cars.isEmpty()) {
            System.out.println("No cars available.");
        } else {
            System.out.println("\nAvailable Cars:");
            cars.forEach(System.out::println);
        }
    }
    

    private static void bookCar(int userId) {
        System.out.print("Enter Car ID to book: ");
        int carId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter return date (YYYY-MM-DD): ");
        String returnDate = scanner.nextLine();

        if (CarService.bookCar(carId, userId, returnDate)) {
            System.out.println("Car booking successful!");
        } else {
            System.out.println("Car booking failed. Check User ID and try again.");
        }
    }

    private static void getUserBookings(int userId) { 
        List<Car> bookings = CarService.getUserBookings(userId);
        if (bookings.isEmpty()) {
            System.out.println("You have no bookings.");
        } else {
            System.out.println("\nYour Bookings:");
            bookings.forEach(System.out::println);
        }
    }
    
// // In UserDashboard
//    private static void getUserBookings(int userId) {
//        List<Car> bookings = CarService.getUserBookings(userId);
//        if (bookings.isEmpty()) {
//            System.out.println("You have no bookings.");
//        } else {
//            bookings.forEach(System.out::println);  // Display each booking
//        }
//    }

    

    private static void cancelBooking(int userId) {
        System.out.print("Enter Car ID to cancel: ");
        int carId = scanner.nextInt();
        scanner.nextLine();
        List<Car> bookings = CarService.getUserBookings(userId);
        boolean bookingFound = false;
        for (Car car : bookings) {
            if (car.getId() == carId) {
                bookingFound = true;
                break;
            }
        }
        if (bookingFound) {
            if (CarService.cancelBooking(carId, userId)) {
                System.out.println("✅ Booking canceled successfully.");
            } else {
                System.out.println("❌ Failed to cancel booking.");
            }
        } else {
            System.out.println("⚠️ Booking ID not found.");
        }
    }
}
