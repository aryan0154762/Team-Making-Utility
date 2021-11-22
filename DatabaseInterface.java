import java.io.*;
import java.util.*;
import java.sql.*;


class Connect {
    static Connection connect() {
        Connection conn = null;
        try {
            // creating a connection to the database
            conn = DriverManager.getConnection("jdbc:sqlite:memberData.db");

        } catch (SQLException e) { System.out.println(e.getMessage()); e.printStackTrace(); }

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
            st = stmt.executeQuery("SELECT Name FROM Members ORDER BY Name");

            while (st.next()) {
                System.out.println(st.getString(1));
            }

            link.close();

        } catch (SQLException e) { System.out.println(e.getMessage()); e.printStackTrace(); }
    }

    public static ResultSet getAllIds() {
        Connection link = Connect.connect();
        ResultSet st = null;
        try {
            // statement link to database
            Statement stmt=link.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            // to get the result set
            st = stmt.executeQuery("SELECT Userid FROM Members");

            while (st.next()) {
                System.out.println(st.getString(1));
            }
            return st;

        } catch (SQLException e) { System.out.println(e.getMessage()); e.printStackTrace(); }

        return null;
    }

    public static void getMembers() throws Exception { // return IDs of the members with required skillset
        InputStreamReader stdinp = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(stdinp);
        
        System.out.print("Authenticate : ");
        if (!in.readLine().equals("admin")) {
            System.out.println("Incorrect Password!");
            return;
        }

        System.out.println("Enter the required skills :");
        System.out.print("Skill 1 : ");
        int skill1 = Skill.strToInt(in.readLine());
        System.out.print("Skill 2 : ");
        int skill2 = Skill.strToInt(in.readLine());
        System.out.print("Skill 3 : ");
        int skill3 = Skill.strToInt(in.readLine());
        System.out.println();
        System.out.print("How many members do you want in the group : ");
        int strength = Integer.parseInt(in.readLine());

        class Candidate implements Comparable<Candidate>{
            int userID;
            String name;
            int compatibility;

            Candidate(int userID, String name, int compatibility) {
                this.userID = userID;
                this.name = name;
                this.compatibility = compatibility;
            }

            public int compareTo(Candidate o) {
                return this.compatibility - o.compatibility;
            }
        }

        Connection link = Connect.connect();
        ResultSet st = null;
        try {
            // statement link to database
            Statement stmt=link.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            // to get the result set
            String updQ = "SELECT Userid, Name FROM Members WHERE" + " Skill1="+skill1+" or Skill2="+skill1+" or Skill3="+skill1+" or Skill1="+skill2+" or Skill2="+skill2+" or Skill3="+skill2+" or Skill1="+skill3+" OR Skill2="+skill3+" or Skill3="+skill3+"";
            st = stmt.executeQuery(updQ);

            PriorityQueue<Candidate> filter = new PriorityQueue<>();
            while (st.next()) {
                Candidate tmp = new Candidate(st.getInt(1), st.getString(2), MemoryInterface.getCompatibility(st.getInt(1)));
                filter.add(tmp);
            }

            for (int i=0; i<strength && filter.size() > 0; i++) {
                System.out.println(filter.peek().userID + "\t" + filter.poll().name);
            }

            link.close();

        } catch (SQLException e) { System.out.println(e.getMessage()); e.printStackTrace(); }       
    }

    public static void getName(int userID) {
        Connection link = Connect.connect();
        ResultSet st = null;
        try {
            // statement link to database
            Statement stmt=link.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            // to get the result set
            st = stmt.executeQuery("SELECT Name FROM Members WHERE Userid = " + userID);

            System.out.println(st.getString(1));

            link.close();

        } catch (SQLException e) { System.out.println(e.getMessage()); e.printStackTrace(); }
        
    }

    static void memberAdd() {
        Connection link = Connect.connect();
        ResultSet st = null;

        try {
            // statement link to database
            Statement stmt=link.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            // to add a new account
            MemberInterface member = new MemberInterface();
            member.inputHelper();
            
            String updQ = "INSERT INTO Members (Name, Skill1, Skill2, Skill3, Passhash) VALUES (\"" + member.name + "\", " + member.skill1.skillToInt() + ", " + member.skill2.skillToInt() + ", " + member.skill3.skillToInt() + ", \"" + member.passHash + "\")";
            stmt.executeUpdate(updQ);
            
            st = stmt.executeQuery("SELECT Userid FROM Members ORDER BY Userid DESC LIMIT 1");
            System.out.println();
            member.userID = Integer.parseInt(st.getString(1));
            System.out.println("Your User ID is : " + member.userID);

            InputStreamReader stdinp = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(stdinp);
            
            System.out.println("Adding Friends");
            System.out.println("Enter Friend's User IDs separated with spaces :");
            String[] temp = in.readLine().split(" ", 0);
            for (String id: temp) {
                MemoryInterface.addFriend(member.userID, Integer.parseInt(id));
            }

            System.out.println("Adding Foes");
            System.out.println("Enter Foe's User IDs separated with spaces :");
            temp = in.readLine().split(" ", 0);
            for (String id: temp) {
                MemoryInterface.addFoe(member.userID, Integer.parseInt(id));
            }
            
        } catch (Exception e) { System.out.println(e.getMessage()); e.printStackTrace(); }
    }

    public static void main(String[] args) throws Exception {
        if (args[0].equals("1")) {
            DatabaseInterface.getMembers();
        } else if (args[0].equals("2")) {
            MemberInterface member = new MemberInterface();
            member.init(Integer.parseInt(args[1]));
            if (!member.passHash.equals(MemberInterface.getHash(args[2]))) {
                System.out.println("Incorrect Password");
                return;
            }
            if (args[3].equals("friend")) {
                Scanner scn = new Scanner(System.in);
                System.out.print("Enter the User ID of your friend : ");
                MemoryInterface.addFriend(Integer.parseInt(args[1]), scn.nextInt());
            }
            else if (args[3].equals("foe")) {
                Scanner scn = new Scanner(System.in);
                System.out.print("Enter the User ID of your foe : ");
                MemoryInterface.addFoe(Integer.parseInt(args[1]), scn.nextInt());
            }
        } else if (args[0].equals("3")) {
            printAllNames();
        } else if (args[0].equals("4")) {
            MemoryInterface.printAllRelations();
        } else if (args[0].equals("5")) {
            DatabaseInterface.memberAdd();
        }
    }
}
