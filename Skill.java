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

    public static Skill converter(int skillNo) {
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

    public static void main(String[] args) { // tester
        System.out.println("this shouldn't be visible");
    }
}
