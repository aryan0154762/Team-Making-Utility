public enum Skill{
    Economics,
    Data_Analysis,
    Financial_Accounting,
    Negotiation,
    Sales_Marketing,
    Business_Management,
    Leadership,
    Effective_Communication,
    Delegation,
    Networking;

    public static Skill intToSkill(int skillNo) {
        switch(skillNo) {
            case 0:
                return Skill.Economics;
            case 1:
                return Skill.Data_Analysis;
            case 2:
                return Skill.Financial_Accounting;
            case 3:
                return Skill.Negotiation;
            case 4:
                return Skill.Sales_Marketing;
            case 5:
                return Skill.Business_Management;
            case 6:
                return Skill.Leadership;
            case 7:
                return Skill.Effective_Communication;
            case 8:
                return Skill.Delegation;
            case 9:
                return Skill.Networking;
        }
        return null;
    }

    public int skillToInt() {
        return this.ordinal();
    }

    public static int strToInt(String skill) {
        if (skill.matches(".*Economics.*") || skill.matches(".*economics.*")) {
            return 0;
        }
        else if (skill.matches(".*Data.*") || skill.matches(".*data.*")) {
            return 1;
        }
        else if (skill.matches(".*Financial.*") || skill.matches(".*financial.*")) {
            return 2;
        }
        else if (skill.matches(".*Negotiation.*") || skill.matches(".*negotiation.*")) {
            return 3;
        }
        else if (skill.matches(".*Sales.*") || skill.matches(".*sales.*")) {
            return 4;
        }
        else if (skill.matches(".*Business.*") || skill.matches(".*business.*")) {
            return 5;
        }
        else if (skill.matches(".*Leadership.*") || skill.matches(".*leadership.*")) {
            return 6;
        }
        else if (skill.matches(".*Communication.*") || skill.matches(".*communication.*")) {
            return 7;
        }
        else if (skill.matches(".*Delegation.*") || skill.matches(".*delegation.*")) {
            return 8;
        }
        else if (skill.matches(".*Networking.*") || skill.matches(".*networking.*")) {
            return 9;
        }

        return -1;
    }

    public static void printAll() {
        System.out.println("Economics");
        System.out.println("Data_Analysis");
        System.out.println("Financial_Accounting");
        System.out.println("Negotiation");
        System.out.println("Sales_Marketing");
        System.out.println("Business_Management");
        System.out.println("Leadership");
        System.out.println("Effective_Communication");
        System.out.println("Delegation");
        System.out.println("Networking");
    }

    public static void main(String[] args) { // tester
        System.out.println(Skill.intToSkill(Skill.strToInt("data")));
    }
}
