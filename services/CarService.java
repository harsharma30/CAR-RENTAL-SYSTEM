package services;

import database.DatabaseConnection;
import models.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarService {
	
//	private static Connection getConnection() throws SQLException {
//        return DatabaseConnection.getConnection();
//    }

    public static List<Car> getAvailableCars() {
        List<Car> cars = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM cars WHERE available=true")) {

            while (rs.next()) {
                cars.add(new Car(
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getDouble("price"),
                        rs.getBoolean("available")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
    public static boolean bookCar(int carId, int userId, String returnDate) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement checkUserStmt = conn.prepareStatement(
                "SELECT userid FROM users WHERE userid = ?"
            );
            checkUserStmt.setInt(1, userId);
            ResultSet userResult = checkUserStmt.executeQuery();

            if (!userResult.next()) {
                System.out.println("❌ Error: User ID " + userId + " does not exist.");
                return false;
            }

            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO bookings (car_id, user_id, return_date) VALUES (?, ?, ?)"
            );

            stmt.setInt(1, carId);
            stmt.setInt(2, userId); 
            stmt.setDate(3, Date.valueOf(returnDate)); 

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Car booked successfully for User ID: " + userId);
                return true;
            } else {
                System.out.println("❌ Booking failed. Please try again.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("❌ SQL Error: " + e.getMessage());
            return false;
        }
    }






    public static List<Car> getUserBookings(int userId) {
        List<Car> bookedCars = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT c.id, c.model, c.price FROM cars c " +
                     "JOIN bookings b ON c.id = b.car_id WHERE b.user_id = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookedCars.add(new Car(
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getDouble("price"),
                        false
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookedCars;
    }

    public static boolean cancelBooking(int carId, int userId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if the booking exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM bookings WHERE car_id=? AND user_id=?");
            checkStmt.setInt(1, carId);
            checkStmt.setInt(2, userId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                return false; 
            }

            PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM bookings WHERE car_id=? AND user_id=?");
            deleteStmt.setInt(1, carId);
            deleteStmt.setInt(2, userId); 
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE cars SET available=true WHERE id=?");
            updateStmt.setInt(1, carId);

            return deleteStmt.executeUpdate() > 0 && updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean addCar(int carId, String model, double price, boolean isAvailable) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO cars (id, model, price, available) VALUES (?, ?, ?, ?)"
            );
            stmt.setInt(1, carId);
            stmt.setString(2, model);
            stmt.setDouble(3, price);
            stmt.setBoolean(4, isAvailable); 

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean editCar(int carId, String model, double price, boolean isAvailable) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "UPDATE cars SET model=?, price=?, available=? WHERE id=?"
            );
            stmt.setString(1, model);
            stmt.setDouble(2, price);
            stmt.setBoolean(3, isAvailable);
            stmt.setInt(4, carId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static List<String> getAllBookings() {
        List<String> bookings = new ArrayList<>();

        String query = "SELECT b.id, c.model, b.user_id, b.booking_date, b.return_date " +
                       "FROM bookings b " +
                       "JOIN cars c ON b.car_id = c.id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String bookingInfo = "Booking ID: " + rs.getInt("id") +
                                     ", Car Model: " + rs.getString("model") +
                                     ", User ID: " + rs.getInt("user_id") +
                                     ", Booking Date: " + rs.getDate("booking_date") +
                                     ", Return Date: " + rs.getDate("return_date");
                bookings.add(bookingInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }
    
    public static boolean deleteCar(int carId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM cars WHERE id = ?");
            stmt.setInt(1, carId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




}
