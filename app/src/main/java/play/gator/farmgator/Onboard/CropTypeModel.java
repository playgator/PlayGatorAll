package play.gator.farmgator.Onboard;

import java.util.List;

public class CropTypeModel {
    List<String> rabi;
    List<String> kharif;
    List<String> zaid;

    public List<String> getKharif() {
        return kharif;
    }

    public void setKharif(List<String> kharif) {
        this.kharif = kharif;
    }

    public List<String> getZaid() {
        return zaid;
    }

    public void setZaid(List<String> zaid) {
        this.zaid = zaid;
    }

    public List<String> getRabi() {
        return rabi;
    }

    public void setRabi(List<String> rabi) {
        this.rabi = rabi;
    }
}
