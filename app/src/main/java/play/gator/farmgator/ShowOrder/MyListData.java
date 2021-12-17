package play.gator.farmgator.ShowOrder;

import java.util.List;

import play.gator.farmgator.BookOrder.ProductModel;

public class MyListData {
    private String Farmername,Farmno,billamount,itemname,orderdate,orderstatus;
    private String farmerAddress, longitude, latitude;
    String orderID;

    public String getFarmerAddress() {
        return farmerAddress;
    }

    public void setFarmerAddress(String farmerAddress) {
        this.farmerAddress = farmerAddress;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public MyListData(String orderID , String farmerAddress, String longitude, String latitude, String farmername, String farmno, String billamount, String itemname, String orderdate, String orderstatus) {
        this.orderID = orderID;
        this.farmerAddress = farmerAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        Farmername = farmername;
        Farmno = farmno;
        this.billamount = billamount;
        this.itemname = itemname;
        this.orderdate = orderdate;
        this.orderstatus = orderstatus;
    }


    public String getBillamount() {
        return billamount;
    }

    public void setBillamount(String billamount) {
        this.billamount = billamount;
    }



    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }



    public String getFarmername() {
        return Farmername;
    }

    public void setFarmername(String farmername) {
        Farmername = farmername;
    }

    public String getFarmno() {
        return Farmno;
    }

    public void setFarmno(String farmno) {
        Farmno = farmno;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }
}
