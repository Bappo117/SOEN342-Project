package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/LessonBookingSystem?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // Your MySQL username
    private static final String PASSWORD = "Winnerman2012!"; // Your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
