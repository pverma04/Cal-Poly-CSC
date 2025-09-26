package InnReservationSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Reservations {
    public static void makeReservation() throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.println("First name: ");
        String firstName = sc.nextLine();
        
        System.out.println("Last name: ");
        String lastname = sc.nextLine();

        System.out.print("Room Code (or 'Any'): ");
        String roomCode = sc.nextLine();

        System.out.print("Bed Type (or 'Any'): ");
        String bedType = sc.nextLine();

        System.out.print("Begin Date (YYYY-MM-DD): ");
        String beginDate = sc.nextLine();

        System.out.print("End Date (YYYY-MM-DD): ");
        String endDate = sc.nextLine();

        System.out.print("Number of Adults: ");
        int adults = sc.nextInt();

        System.out.print("Number of Children: ");
        int children = sc.nextInt();

        String query = """
            SELECT * FROM lab7_rooms r
            WHERE (? = 'Any' OR r.RoomCode = ?)
              AND (? = 'Any' OR r.bedType = ?)
              AND r.maxOcc >= ?
              AND NOT EXISTS (
                  SELECT 1 FROM lab7_reservations
                  WHERE r.RoomCode = Room
                    AND CheckIn < ?
                    AND CheckOut > ?
              )
            LIMIT 5;
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, roomCode);
            stmt.setString(2, roomCode);
            stmt.setString(3, bedType);
            stmt.setString(4, bedType);
            stmt.setInt(5, adults + children);
            stmt.setString(6, endDate);
            stmt.setString(7, beginDate);

            ResultSet rs = stmt.executeQuery();
            int option = 1;
            while (rs.next()) {
                System.out.printf("%d. Room Code: %s, Name: %s, Bed Type: %s, Base Price: %.2f%n",
                        option++, rs.getString("RoomCode"), rs.getString("RoomName"),
                        rs.getString("bedType"), rs.getDouble("basePrice"));
            }
        }
    }
    public static void cancelReservation() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Cancel a Reservation");
        System.out.print("Enter the reservation code: ");
        int reservationCode = scanner.nextInt();

        // Check if the reservation exists
        String checkQuery = "SELECT * FROM lab7_reservations WHERE CODE = ?";
        String deleteQuery = "DELETE FROM lab7_reservations WHERE CODE = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {

            // Check if the reservation exists
            checkStmt.setInt(1, reservationCode);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Reservation not found. Please try again.");
                    return;
                }

                // Confirm details before deleting
                System.out.printf("Reservation found for %s %s (Check-In: %s, Check-Out: %s).%n",
                        rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getDate("CheckIn"), rs.getDate("Checkout"));

                System.out.print("Are you sure you want to cancel this reservation? (yes/no): ");
                String confirmation = scanner.next();
                if (!confirmation.equalsIgnoreCase("yes")) {
                    System.out.println("Cancellation aborted.");
                    return;
                }
            }

            // Delete the reservation
            deleteStmt.setInt(1, reservationCode);
            int rowsAffected = deleteStmt.executeUpdate();

            // Provide feedback
            if (rowsAffected > 0) {
                System.out.println("Reservation successfully canceled.");
            } else {
                System.out.println("Failed to cancel reservation. Please try again.");
            }
        }
    }
}
