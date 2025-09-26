package InnReservationSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            System.getenv("HP_JDBC_URL"),
            System.getenv("HP_JDBC_USER"),
            System.getenv("HP_JDBC_PW")
        );
    }
}