package play.gator.farmgator.BookOrder;

import java.util.ArrayList;
import java.util.List;

public class ItemDatamodel {

    public int farmid;
    public String userName;
    public int total;


    public ItemDatamodel() {
        this.farmid = farmid;
        this.userName = userName;
        this.total = total;
    }

    public int getFarmid() {
        return farmid;
    }

    public void setFarmid(int farmid) {
        this.farmid = farmid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }



}