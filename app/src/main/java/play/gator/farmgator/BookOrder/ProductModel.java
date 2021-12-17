package play.gator.farmgator.BookOrder;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import play.gator.farmgator.R;

public class ProductModel implements Serializable {

    String name;
    String price;
    String number;
    int total;
    boolean isSelected;
//     String finall,number1;
//     int quantity;
//     Context context;
//    List<ProductModel> list = new ArrayList<>();

    public  ProductModel(){

    }

    public ProductModel(String name, String price, String number, int total) {
        this.name = name;
        this.price = price;
        this.number = number;
        this.total = total;
    }
    public ProductModel(String name, String price, String number, int total, boolean isSelected) {
        this.name = name;
        this.price = price;
        this.number = number;
        this.total = total;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

  /*  public List<ProductModel> fillFertilizer(){
        List<ProductModel> productList = new ArrayList<>();

        String[] fertiproduct = context.getResources().getStringArray(R.array.item_fertilzer);
        String[] price = context.getResources().getStringArray(R.array.item_fertilzer_price);

        for (int i=0;i<6;i++){

            ProductModel productModel = new ProductModel(fertiproduct[i],price[i],number1,total,false);
            list.add(productModel);
        }

        return productList;

    }*/
}
