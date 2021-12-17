package play.gator.farmgator.Onboard;

import java.io.Serializable;

public class Model implements Serializable {
    private String farmId;
    private  String type;
    private  String noOfAcres;
    private String kharifCrop;
    private  String rabiCrops;
    private  String zaidCrops;
    public Model(String farmId,String type, String noOfAcres, String kharifCrop, String rabiCrops, String zaidCrops) {
        this.farmId = farmId;
        this.type = type;
        this.noOfAcres = noOfAcres;
        this.kharifCrop = kharifCrop;
        this.rabiCrops = rabiCrops;
        this.zaidCrops = zaidCrops;
    }

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNoOfAcres() {
        return noOfAcres;
    }

    public void setNoOfAcres(String noOfAcres) {
        this.noOfAcres = noOfAcres;
    }

    public String getKharifCrop() {
        return kharifCrop;
    }

    public void setKharifCrop(String kharifCrop) {
        this.kharifCrop = kharifCrop;
    }

    public String getRabiCrops() {
        return rabiCrops;
    }

    public void setRabiCrops(String rabiCrops) {
        this.rabiCrops = rabiCrops;
    }

    public String getZaidCrops() {
        return zaidCrops;
    }

    public void setZaidCrops(String zaidCrops) {
        this.zaidCrops = zaidCrops;
    }

    public Model(){

    }


}
