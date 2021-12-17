package play.gator.farmgator.BookOrder;

import java.util.List;

import play.gator.farmgator.Onboard.Model;

public class OrderPerFarmModel {
    private Model farmModel;
    private String farmId;
    private int perFarmTotal;
    List<ProductModel> overAllProductModelList;

    public int getPerFarmTotal() {
        return perFarmTotal;
    }

    public void setPerFarmTotal(int perFarmTotal) {
        this.perFarmTotal = perFarmTotal;
    }

    public OrderPerFarmModel() {
    }

    public List<ProductModel> getOverAllProductModelList() {
        return overAllProductModelList;
    }

    public void setOverAllProductModelList(List<ProductModel> overAllProductModelList) {
        this.overAllProductModelList = overAllProductModelList;
    }

    public OrderPerFarmModel(Model farmModel, String farmId, int perFarmTotal, List<ProductModel> overAllProductModelList) {
        this.farmModel = farmModel;
        this.farmId = farmId;
        this.perFarmTotal = perFarmTotal;
        this.overAllProductModelList = overAllProductModelList;
    }

    public Model getFarmModel() {
        return farmModel;
    }

    public void setFarmModel(Model farmModel) {
        this.farmModel = farmModel;
    }

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

}
