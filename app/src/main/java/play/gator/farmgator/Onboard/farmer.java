package play.gator.farmgator.Onboard;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList
        ;
import java.util.List;

public class farmer implements Serializable {
    String langitude = "",lattitude = "";
    List<Model> farmdata;
    String farmerid = "";
    String name = "";
    String aadhar = "";
    String address= "";
    String mob_no= "";
    String whats_no= "";
    String alt_no= "";
    String aadhar_img_uri= "";
    String Farmer_img_uri= "";
    String bnk_chk_img_uri= "";
    String salesManId;

    public farmer(){

    }
    public static class FarmData implements Serializable{
        public static List<Model> farmDataList;

        public List<Model> getFarmDataList() {
            return farmDataList;
        }

        public void setFarmDataList(List<Model> farmDataList) {
            this.farmDataList = farmDataList;
        }
    }

    public List<Model> getFarmdata() {
        return farmdata;
    }

    public void setFarmdata(List<Model> farmdata) {
        this.farmdata = farmdata;
    }

    public String getSalesManId() {
        return salesManId;
    }

    public void setSalesManId(String salesManId) {
        this.salesManId = salesManId;
    }

    public farmer( String salesManId,
                   String langitude, String lattitude, List<Model> farmdata, String farmerid,
                   String name, String aadhar, String address, String mob_no, String whats_no,
                   String alt_no, String aadhar_img_uri, String farmer_img_uri, String bnk_chk_img_uri) {
        this.salesManId = salesManId;
        this.langitude = langitude;
        this.lattitude = lattitude;
        this.farmdata = farmdata;
        FarmData.farmDataList = farmdata;
        this.farmerid = farmerid;
        this.name = name;
        this.aadhar = aadhar;
        this.address = address;
        this.mob_no = mob_no;
        this.whats_no = whats_no;
        this.alt_no = alt_no;
        this.aadhar_img_uri = aadhar_img_uri;
        Farmer_img_uri = farmer_img_uri;
        this.bnk_chk_img_uri = bnk_chk_img_uri;
    }


    public String getLangitude() {
        return langitude;
    }

    public void setLangitude(String langitude) {
        this.langitude = langitude;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }


    public String getFarmerid() {
        return farmerid;
    }

    public void setFarmerid(String farmerid) {
        this.farmerid = farmerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMob_no() {
        return mob_no;
    }

    public void setMob_no(String mob_no) {
        this.mob_no = mob_no;
    }

    public String getWhats_no() {
        return whats_no;
    }

    public void setWhats_no(String whats_no) {
        this.whats_no = whats_no;
    }

    public String getAlt_no() {
        return alt_no;
    }

    public void setAlt_no(String alt_no) {
        this.alt_no = alt_no;
    }

    public String getAadhar_img_uri() {
        return aadhar_img_uri;
    }

    public void setAadhar_img_uri(String aadhar_img_uri) {
        this.aadhar_img_uri = aadhar_img_uri;
    }

    public String getFarmer_img_uri() {
        return Farmer_img_uri;
    }

    public void setFarmer_img_uri(String farmer_img_uri) {
        Farmer_img_uri = farmer_img_uri;
    }

    public String getBnk_chk_img_uri() {
        return bnk_chk_img_uri;
    }

    public void setBnk_chk_img_uri(String bnk_chk_img_uri) {
        this.bnk_chk_img_uri = bnk_chk_img_uri;
    }

    public farmer(String langitude, String lattitude, String farmerid, String name, String aadhar, String address, String mob_no, String whats_no, String alt_no, String aadhar_img_uri, String farmer_img_uri, String bnk_chk_img_uri) {
        this.langitude = langitude;
        this.lattitude = lattitude;
        //  this.farmdata = farmdata;
        this.farmerid = farmerid;
        this.name = name;
        this.aadhar = aadhar;
        this.address = address;
        this.mob_no = mob_no;
        this.whats_no = whats_no;
        this.alt_no = alt_no;
        this.aadhar_img_uri = aadhar_img_uri;
        Farmer_img_uri = farmer_img_uri;
        this.bnk_chk_img_uri = bnk_chk_img_uri;
    }
    @Override
    public String toString() {
        return "farmer{" +
                "names='" + name + '\'' +
                ", address" +
                "='" + address + '\'' +
                '}';
    }
}
