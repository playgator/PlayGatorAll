package play.gator.farmgator.SalesPerson;

public class user {

    public user(String name, String email, String aadhar, String pass, String mob_no) {
        Name = name;
        Email = email;
        Aadhar = aadhar;
        Pass = pass;
        Mob_no = mob_no;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getAadhar() {
        return Aadhar;
    }

    public String getPass() {
        return Pass;
    }

    public String getMob_no() {
        return Mob_no;
    }

    String Name;
    String Email;
    String Aadhar;
    String Pass;
    String Mob_no;
}
