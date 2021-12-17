package play.gator.farmgator.BookOrder;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    public String id;
    public String customerName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public static List<Customer> fillCustomer(){
        List<Customer> customerList = new ArrayList<>();

        for(int i=0; i<1000; i++){
            Customer customer = new Customer();
            customer.setId(""+i);
            customer.setCustomerName(""+i);
            customerList.add(customer);
        }
        return customerList;

    }

    @Override
    public String toString() {
        return customerName;
    }
}