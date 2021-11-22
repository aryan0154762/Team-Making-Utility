import java.io.*;
import java.util.*;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MemberInterface {
    int userID;
    String name;
    Skill skill1;
    Skill skill2;
    Skill skill3;
    String passHash;

    void init(int userID) {
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
            this.skill1 = Skill.intToSkill(st.getInt(3));
            this.skill2 = Skill.intToSkill(st.getInt(4));
            this.skill3 = Skill.intToSkill(st.getInt(5));
            this.passHash = st.getString(6);
            conn.close();

        } catch (SQLException e) { System.out.println(e.getMessage()); e.printStackTrace(); }
    }

    public void seeProfile() {

    }

    public void inputHelper() throws Exception {
        InputStreamReader stdinp = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(stdinp);
        System.out.println("Enter the following details :");

        System.out.print("Name : ");
        this.name = in.readLine();

        System.out.println();
        System.out.println("The Following Skill sets are possible:");
        Skill.printAll();
        System.out.println("No need to enter accurately, appropriate skill will be grabbed automatically");
        System.out.println();
        System.out.print("Primary Skill : ");
        this.skill1 = Skill.intToSkill(Skill.strToInt(in.readLine()));

        System.out.print("Secondary Skill : ");
        this.skill2 = Skill.intToSkill(Skill.strToInt(in.readLine()));

        System.out.print("Tertiary Skill : ");
        this.skill3 = Skill.intToSkill(Skill.strToInt(in.readLine()));

        System.out.print("Make Password : ");
        passHash = getHash(in.readLine());

        System.out.println();
        System.out.println("Do you confirm the following details");
        System.out.println("Name : " + this.name);
        System.out.println("Primary Skill : " + this.skill1);
        System.out.println("Secondary Skill : " + this.skill2);
        System.out.println("Tertiary Skill : " + this.skill3);
        System.out.println("y/n?");

        if (in.read() == 'n') {
            inputHelper();
        }
    }

    public static String getHash(String password) {
        String passwordToHash = password;
        String generatedPassword = null;

        try 
        {
            // Create MessageDigest instance for MD4
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Add password bytes to digest
            md.update(passwordToHash.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest();

            // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
              sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            // Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    
    public static void main(String[] args) throws Exception { // tester
        MemberInterface member = new MemberInterface();
        member.init(Integer.parseInt(args[0]));

        System.out.println("The below is all your information in the system");
        System.out.println("User ID : " + member.userID);
        System.out.println("Name : " + member.name);
        System.out.println("Primary Skill : " + member.skill1);
        System.out.println("Secondary Skill : " + member.skill2);
        System.out.println("Tertiary Skill : " + member.skill3);
        MemoryInterface.getRelations(Integer.parseInt(args[0]));

    }
}
