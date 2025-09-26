package InnReservationSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomsRates {
    public static void displayRoomsAndRates() throws SQLException {
        String query = """
            SELECT 
                r.RoomCode, 
                r.RoomName, 
                r.Beds, 
                r.bedType, 
                r.maxOcc, 
                r.basePrice, 
                r.decor,
                ROUND(SUM(DATEDIFF(LEAST(NOW(), res.CheckOut), GREATEST(DATE_SUB(NOW(), INTERVAL 180 DAY), res.CheckIn))) / 180, 2) AS popularity_score,
                COALESCE(MIN(res.CheckOut), NOW()) AS next_available_date,
                DATEDIFF(MAX(res.CheckOut), MIN(res.CheckIn)) AS length_of_last_stay
            FROM lab7_rooms r
            LEFT JOIN lab7_reservations res ON r.RoomCode = res.Room
            WHERE res.CheckOut < NOW() OR res.CheckOut IS NULL
            GROUP BY r.RoomCode
            ORDER BY popularity_score DESC;
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("RoomCode | RoomName                     | Popularity | Next Check-In | Last Stay Length");
            System.out.println("-------------------------------------------------------------------------------------");

            while (rs.next()) {
                String roomCode = rs.getString("RoomCode");
                String roomName = rs.getString("RoomName");
                double popularity = rs.getDouble("popularity_score");
                String nextCheckIn = rs.getDate("next_available_date").toString();
                int lastStayLength = rs.getInt("length_of_last_stay");

                System.out.printf("%-8s | %-28s | %-10.2f | %-13s | %-16d%n",
                        roomCode, roomName, popularity, nextCheckIn, lastStayLength);
            }
        }
    }
}
