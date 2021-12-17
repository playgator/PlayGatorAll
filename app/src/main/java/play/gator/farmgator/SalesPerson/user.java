package play.gator.farmgator.SalesPerson;

import java.util.ArrayList;
import java.util.List;

import play.gator.farmgator.Onboard.farmer;

public class user {
    String Name;
    String Email;
    String Aadhar;
    String Pass;
    String Mob_no;
    String id;
    List<farmer> farmerList;

    public user(String name, String email, String aadhar, String pass, String mob_no) {
        Name = name;
        Email = email;
        Aadhar = aadhar;
        Pass = pass;
        Mob_no = mob_no;
    }

    public user() {
    }

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setAadhar(String aadhar) {
        Aadhar = aadhar;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public void setMob_no(String mob_no) {
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

    public List<farmer> getFarmerList() {
        if(farmerList == null) return new ArrayList<>();
        else
            return farmerList;
    }

    public void setFarmerList(List<farmer> farmerList) {
        this.farmerList = farmerList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
