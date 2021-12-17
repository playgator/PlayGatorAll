package play.gator.farmgator.BookOrder;

public class ExampleItem {

    private String Farmer,Adhar, Village,NoFarm;
    public ExampleItem(String farmer, String adhar, String village, String noFarm) {
        Farmer = farmer;
        Adhar = adhar;
        Village = village;
        NoFarm = noFarm;
    }


    public String getFarmer() {
        return Farmer;
    }

    public void setFarmer(String farmer) {
        Farmer = farmer;
    }

    public String getAdhar() {
        return Adhar;
    }

    public void setAdhar(String adhar) {
        Adhar = adhar;
    }

    public String getVillage() {
        return Village;
    }

    public void setVillage(String village) {
        Village = village;
    }

    public String getNoFarm() {
        return NoFarm;
    }

    public void setNoFarm(String noFarm) {
        NoFarm = noFarm;
    }
}
