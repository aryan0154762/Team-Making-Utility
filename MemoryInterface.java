import java.io.*;
import java.util.*;


class RelationsGraph implements Serializable {
    HashMap<Integer, ArrayList<Integer>> friendIDs;
    HashMap<Integer, ArrayList<Integer>> foeIDs;
}


public class MemoryInterface {
    public static void backup(RelationsGraph rg) {
        try {
            FileOutputStream fileStream = new FileOutputStream("relations.ser");
            ObjectOutputStream os = new ObjectOutputStream(fileStream);
            os.writeObject(rg);
            os.close();

        } catch (Exception e) { System.out.println(e.getMessage()); e.printStackTrace(); }
    }

    static RelationsGraph restore() {
        RelationsGraph rg = null;
        try {
            FileInputStream fileStream = new FileInputStream("relations.ser");
            ObjectInputStream is = new ObjectInputStream(fileStream);
            rg = (RelationsGraph)is.readObject();

        } catch (Exception e) { System.out.println(e.getMessage()); e.printStackTrace(); }

        return rg;
    }

    public static int getCompatibility(int userID) {
        RelationsGraph rg = restore();
        if (rg.friendIDs.get(userID) != null && rg.foeIDs.get(userID) != null) {
            int friendCount = rg.friendIDs.get(userID).size();
            int foeCount = rg.foeIDs.get(userID).size();
            return friendCount - foeCount;
        }
        else {
            return 0;
        }
    }

    static void getRelations(int userID) {
        RelationsGraph rg = restore();
        ArrayList<Integer> freids = rg.friendIDs.get(userID);
        ArrayList<Integer> foeids = rg.foeIDs.get(userID);

        System.out.println ("The following are your Friend's UIDs : " + freids);
        System.out.println ("The following are your Foe's UIDs : " + foeids);
    }

    public static void printAllRelations() {
        RelationsGraph rg = restore();
        for (int id : rg.friendIDs.keySet()) {
            System.out.println(id);
            getRelations(id);
        }
    }
    public static void addFriend(int userID, int friendID) /*throws Exception*/ {
        RelationsGraph rg = restore();
        if (rg.friendIDs.get(userID) == null) {
            ArrayList<Integer> ids = new ArrayList<Integer>();
            System.out.println("adding friend");
            ids.add(friendID);
            rg.friendIDs.put(userID, ids);
            backup(rg);
        }
        else if (rg.friendIDs.get(userID).contains(friendID)) {
            System.out.println("he is already your friend");
        }
        else {
            System.out.println("adding friend");
            ArrayList<Integer> ids = rg.friendIDs.get(userID);
            ids.add(friendID);
            backup(rg);
        }
    }

    public static void addFoe(int userID, int foeID) /*throws Exception*/ {
        RelationsGraph rg = restore();
        if (rg.foeIDs.get(userID) == null) {
            ArrayList<Integer> ids = new ArrayList<Integer>();
            System.out.println("adding foe");
            ids.add(foeID);
            rg.foeIDs.put(userID, ids);
            backup(rg);
        }
        else if (rg.foeIDs.get(userID).contains(foeID)) {
            System.out.println("he is already your foe");
        }
        else {
            System.out.println("adding foe");
            ArrayList<Integer> ids = rg.foeIDs.get(userID);
            ids.add(foeID);
            backup(rg);
        }
    }

    public static void main(String[] args) {
        printAllRelations();
    }
}
