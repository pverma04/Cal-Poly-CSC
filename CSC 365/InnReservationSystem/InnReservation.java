import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.util.Map;
import java.util.Scanner;
import java.util.LinkedHashMap;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

/*
Introductory JDBC examples based loosely on the BAKERY dataset from CSC 365 labs.

-- MySQL setup:
drop table if exists hp_goods, hp_customers, hp_items, hp_receipts;
create table hp_goods as select * from BAKERY.goods;
create table hp_customers as select * from BAKERY.customers;
create table hp_items as select * from BAKERY.items;
create table hp_receipts as select * from BAKERY.receipts;

grant all on amigler.hp_goods to hasty@'%';
grant all on amigler.hp_customers to hasty@'%';
grant all on amigler.hp_items to hasty@'%';
grant all on amigler.hp_receipts to hasty@'%';

-- Shell init:
export CLASSPATH=$CLASSPATH:mysql-connector-java-8.0.16.jar:.
export HP_JDBC_URL=jdbc:mysql://db.labthreesixfive.com/winter2020?autoReconnect=true\&useSSL=false
export HP_JDBC_USER=jmustang
export HP_JDBC_PW=...
 */
public class InnReservation {
    public static void main(String[] args) {
	try {
	    InnReservation ir = new InnReservation();
            int demoNum = Integer.parseInt(args[0]);
            
            switch (demoNum) {
            case 1: ir.demo1(); break;
            case 2: ir.demo2(); break;
            case 3: ir.demo3(); break;
            case 4: ir.demo4(); break;
            case 5: ir.demo5(); break;
            }
            
	} catch (SQLException e) {
	    System.err.println("SQLException: " + e.getMessage());
	} catch (Exception e2) {
            System.err.println("Exception: " + e2.getMessage());
        }
    }

    // Demo1 - Establish JDBC connection, execute DDL statement
    private void demo1() throws SQLException {

        System.out.println("demo1: Add AvailUntil column to hp_goods table\r\n");
        
	// Step 0: Load MySQL JDBC Driver
	// No longer required as of JDBC 2.0  / Java 6
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    System.out.println("MySQL JDBC Driver loaded");
	} catch (ClassNotFoundException ex) {
	    System.err.println("Unable to load JDBC Driver");
	    System.exit(-1);
	}

	// Step 1: Establish connection to RDBMS
	try (Connection conn = DriverManager.getConnection(System.getenv("HP_JDBC_URL"),
							   System.getenv("HP_JDBC_USER"),
							   System.getenv("HP_JDBC_PW"))) {
	    // Step 2: Construct SQL statement
	    String sql = "ALTER TABLE hp_goods ADD COLUMN AvailUntil DATE";

	    // Step 3: (omitted in this example) Start transaction

	    try (Statement stmt = conn.createStatement()) {

		// Step 4: Send SQL statement to DBMS
		boolean exRes = stmt.execute(sql);
		
		// Step 5: Handle results
		System.out.format("Result from ALTER: %b %n", exRes);
	    }

	    // Step 6: (omitted in this example) Commit or rollback transaction
	}
	// Step 7: Close connection (handled by try-with-resources syntax)
    }
    

    // Demo2 - Establish JDBC connection, execute SELECT query, read & print result
    private void demo2() throws SQLException {

        System.out.println("demo2: List content of lab7_rooms table\r\n");
        
	// Step 1: Establish connection to RDBMS
	try (Connection conn = DriverManager.getConnection(System.getenv("HP_JDBC_URL"),
							   System.getenv("HP_JDBC_USER"),
							   System.getenv("HP_JDBC_PW"))) {
	    // Step 2: Construct SQL statement
	    String sql = "SELECT * FROM lab7_rooms";

	    // Step 3: (omitted in this example) Start transaction
		
	    // Step 4: Send SQL statement to DBMS
	    try (Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)) {

			// Step 5: Receive results
			System.out.println("RoomCode | RoomName                     | Beds | BedType   | MaxOcc | BasePrice | Decor");
			System.out.println("--------------------------------------------------------------------------------------");

			while (rs.next()) {
				// Retrieve values from each column
				String roomCode = rs.getString("RoomCode");
				String roomName = rs.getString("RoomName");
				int beds = rs.getInt("Beds");
				String bedType = rs.getString("bedType");
				int maxOcc = rs.getInt("maxOcc");
				double basePrice = rs.getDouble("basePrice");
				String decor = rs.getString("decor");

				// Print formatted output
				System.out.printf("%-8s | %-28s | %-4d | %-9s | %-6d | $%-8.2f | %s%n",
						roomCode, roomName, beds, bedType, maxOcc, basePrice, decor);
			}
	    }

	    // Step 6: (omitted in this example) Commit or rollback transaction
	}
	// Step 7: Close connection (handled by try-with-resources syntax)
    }


    // Demo3 - Establish JDBC connection, execute DML query (UPDATE)
    // -------------------------------------------
    // Never (ever) write database code like this!
    // -------------------------------------------
    private void demo3() throws SQLException {

        System.out.println("demo3: Populate AvailUntil column using string concatenation\r\n");
        
	// Step 1: Establish connection to RDBMS
	try (Connection conn = DriverManager.getConnection(System.getenv("HP_JDBC_URL"),
							   System.getenv("HP_JDBC_USER"),
							   System.getenv("HP_JDBC_PW"))) {
	    // Step 2: Construct SQL statement
	    Scanner scanner = new Scanner(System.in);
	    System.out.print("Enter a flavor: ");
	    String flavor = scanner.nextLine();
	    System.out.format("Until what date will %s be available (YYYY-MM-DD)? ", flavor);
	    String availUntilDate = scanner.nextLine();

	    // -------------------------------------------
	    // Never (ever) write database code like this!
	    // -------------------------------------------
	    String updateSql = "UPDATE hp_goods SET AvailUntil = '" + availUntilDate + "' " +
		               "WHERE Flavor = '" + flavor + "'";

	    // Step 3: (omitted in this example) Start transaction
	    
	    try (Statement stmt = conn.createStatement()) {
		
		// Step 4: Send SQL statement to DBMS
		int rowCount = stmt.executeUpdate(updateSql);
		
		// Step 5: Handle results
		System.out.format("Updated all '%s' flavored pastries (%d records) %n", flavor, rowCount);		
	    }

	    // Step 6: (omitted in this example) Commit or rollback transaction
	    
	}
	// Step 7: Close connection (handled implcitly by try-with-resources syntax)
    }


    // Demo4 - Establish JDBC connection, execute DML query (UPDATE) using PreparedStatement / transaction    
    private void demo4() throws SQLException {

        System.out.println("demo4: Populate AvailUntil column using PreparedStatement\r\n");
        
	// Step 1: Establish connection to RDBMS
	try (Connection conn = DriverManager.getConnection(System.getenv("HP_JDBC_URL"),
							   System.getenv("HP_JDBC_USER"),
							   System.getenv("HP_JDBC_PW"))) {
	    // Step 2: Construct SQL statement
	    Scanner scanner = new Scanner(System.in);
	    System.out.print("Enter a flavor: ");
	    String flavor = scanner.nextLine();
	    System.out.format("Until what date will %s be available (YYYY-MM-DD)? ", flavor);
	    LocalDate availDt = LocalDate.parse(scanner.nextLine());
	    
	    String updateSql = "UPDATE hp_goods SET AvailUntil = ? WHERE Flavor = ?";

	    // Step 3: Start transaction
	    conn.setAutoCommit(false);
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
		
		// Step 4: Send SQL statement to DBMS
		pstmt.setDate(1, java.sql.Date.valueOf(availDt));
		pstmt.setString(2, flavor);
		int rowCount = pstmt.executeUpdate();
		
		// Step 5: Handle results
		System.out.format("Updated %d records for %s pastries%n", rowCount, flavor);

		// Step 6: Commit or rollback transaction
		conn.commit();
	    } catch (SQLException e) {
		conn.rollback();
	    }

	}
	// Step 7: Close connection (handled implcitly by try-with-resources syntax)
    }



    // Demo5 - Construct a query using PreparedStatement
    private void demo5() throws SQLException {

        System.out.println("demo5: Run SELECT query using PreparedStatement\r\n");
        
	// Step 1: Establish connection to RDBMS
	try (Connection conn = DriverManager.getConnection(System.getenv("HP_JDBC_URL"),
							   System.getenv("HP_JDBC_USER"),
							   System.getenv("HP_JDBC_PW"))) {
	    Scanner scanner = new Scanner(System.in);
	    System.out.print("Find pastries with price <=: ");
	    Double price = Double.valueOf(scanner.nextLine());
	    System.out.print("Filter by flavor (or 'Any'): ");
	    String flavor = scanner.nextLine();

	    List<Object> params = new ArrayList<Object>();
	    params.add(price);
	    StringBuilder sb = new StringBuilder("SELECT * FROM hp_goods WHERE price <= ?");
	    if (!"any".equalsIgnoreCase(flavor)) {
		sb.append(" AND Flavor = ?");
		params.add(flavor);
	    }
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sb.toString())) {
			int i = 1;
			for (Object p : params) {
				pstmt.setObject(i++, p);
			}

			try (ResultSet rs = pstmt.executeQuery()) {
				System.out.println("Matching Pastries:");
				int matchCount = 0;
				while (rs.next()) {
				System.out.format("%s %s ($%.2f) %n", rs.getString("Flavor"), rs.getString("Food"), rs.getDouble("price"));
				matchCount++;
				}
				System.out.format("----------------------%nFound %d match%s %n", matchCount, matchCount == 1 ? "" : "es");
			}
	    }
		//FR1 - rooms + rates
		// FR1: Rooms and Rates
		private void displayRoomsAndRates() throws SQLException {
			System.out.println("Rooms and Rates:\n");

			// SQL Query for Rooms and Rates
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

			try (Connection conn = DriverManager.getConnection(
					System.getenv("HP_JDBC_URL"),
					System.getenv("HP_JDBC_USER"),
					System.getenv("HP_JDBC_PW"));
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

				// Print Header
				System.out.println("RoomCode | RoomName                     | Popularity | Next Check-In | Last Stay Length");
				System.out.println("-------------------------------------------------------------------------------------");

				// Fetch and Print Results
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
    }
    

}
