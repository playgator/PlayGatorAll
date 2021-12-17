package play.gator.farmgator.BookOrder.ProductAdapters;

import java.util.List;

public class ProductTypeModel {
    List<String> Fertiliser;
    List<String> Implements;
    List<String> OrganicMA;
    List<String>  Pesticide;
    List<String> Speciality;

    //Add this
    public ProductTypeModel() {

    }

    public ProductTypeModel(List<String> fertiliser, List<String> anImplements, List<String> organicMA, List<String> pesticide, List<String> speciality) {
        Fertiliser = fertiliser;
        Implements = anImplements;
        OrganicMA = organicMA;
        Pesticide = pesticide;
        Speciality = speciality;
    }

    public List<String> getFertiliser() {
        return Fertiliser;
    }

    public void setFertiliser(List<String> fertiliser) {
        Fertiliser = fertiliser;
    }

    public List<String> getImplements() {
        return Implements;
    }

    public void setImplements(List<String> anImplements) {
        Implements = anImplements;
    }

    public List<String> getOrganicMA() {
        return OrganicMA;
    }

    public void setOrganicMA(List<String> organicMA) {
        OrganicMA = organicMA;
    }

    public List<String> getPesticide() {
        return Pesticide;
    }

    public void setPesticide(List<String> pesticide) {
        Pesticide = pesticide;
    }

    public List<String> getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(List<String> speciality) {
        Speciality = speciality;
    }
}
