package play.gator.farmgator.BookOrder;

import java.util.List;

import play.gator.farmgator.Onboard.farmer;
import play.gator.farmgator.SalesPerson.user;

/*
* If you want to add more list then add accordingly !
 */
    // What about date and time  of order ?
public class OrderModel {
    private String orderID;
    private String date;
    private farmer farmer;
    private int overAllTotal;
    private OrderPerFarmModel orderPerFarmModel;
    private String status;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public farmer getFarmer() {
        return farmer;
    }

    public void setFarmer(farmer farmer) {
        this.farmer = farmer;
    }

    public int getOverAllTotal() {
        return overAllTotal;
    }

    public OrderModel() {
    }

    public void setOverAllTotal(int overAllTotal) {
        this.overAllTotal = overAllTotal;
    }

    public OrderPerFarmModel getOrderPerFarmModel() {
        return orderPerFarmModel;
    }

    public void setOrderPerFarmModel(OrderPerFarmModel orderPerFarmModel) {
        this.orderPerFarmModel = orderPerFarmModel;
    }
}
