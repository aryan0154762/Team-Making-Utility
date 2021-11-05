import java.io.*;
import java.util.*;
import java.sql.*;


public class MemberInterface {
    int userID;
    String name;
    Skill skill1;
    Skill skill2;
    Skill skill3;

    MemberInterface(int userID) {
        this.userID = userID;
        ResultSet st = null;
        Connection conn = null;
        try {
            // creating a connection to the database
            conn = DriverManager.getConnection("jdbc:sqlite:memberData.db");
            // statement link to database
            Statement stmt=conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            // to get the result set
            st = stmt.executeQuery("SELECT * FROM Members WHERE Userid=" + userID);

            this.name = st.getString(2);
            this.skill1 = Skill.converter(st.getInt(3));
            this.skill2 = Skill.converter(st.getInt(4));
            this.skill3 = Skill.converter(st.getInt(5));

            conn.close();

        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }
    
    public static void main(String[] args) throws Exception { // tester
        MemberInterface member = new MemberInterface(2);
        System.out.println(member.userID + " " + member.name);
        System.out.println(member.skill1 + "\t" + member.skill2 + "\t" + member.skill3);
    }
}
