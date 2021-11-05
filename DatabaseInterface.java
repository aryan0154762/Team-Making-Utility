import java.io.*;
import java.util.*;
import java.sql.*;


class Connect {
    static Connection connect() {
        Connection conn = null;
        try {
            // creating a connection to the database
            conn = DriverManager.getConnection("jdbc:sqlite:memberData.db");

        } catch (SQLException e) { System.out.println(e.getMessage()); }

        return conn;
    }
}

public class DatabaseInterface {
    public static void printAllNames() {
        Connection link = Connect.connect();
        ResultSet st = null;
        try {
            // statement link to database
            Statement stmt=link.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            // to get the result set
            st = stmt.executeQuery("SELECT Name FROM Members");

            while (st.next()) {
                System.out.println(st.getString(1));
            }

            link.close();

        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }

    public static void main(String[] args) {
        DatabaseInterface.printAllNames();
    }
}
