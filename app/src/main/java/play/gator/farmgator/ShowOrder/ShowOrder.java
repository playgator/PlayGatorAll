package play.gator.farmgator.ShowOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.List;

import play.gator.farmgator.BookOrder.OrderModel;
import play.gator.farmgator.FireStoreClass;
import play.gator.farmgator.Onboard.Model;
import play.gator.farmgator.Onboard.farmer;
import play.gator.farmgator.R;
import play.gator.farmgator.SalesPerson.user;

public class ShowOrder extends AppCompatActivity {
    private List<MyListData> exampleList;
    MyListAdapter adapter;
    CircularProgressBar circularProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);

        circularProgressBar = (CircularProgressBar)findViewById(R.id.CircularProgressbar);
        circularProgressBar.setVisibility(View.VISIBLE);
        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.brown));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.activity_horizontal_margin));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.activity_vertical_margin));
        int animationDuration = 2500;
        circularProgressBar.setProgressWithAnimation(100, animationDuration);

        new FireStoreClass().getSalesManDetails(this);



        exampleList = new ArrayList<>();


        EditText editText = findViewById(R.id.search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }
        });
    }
    public void getOrderDetailsV2(OrderModel orderModel){

        Log.e("RECEIVED MODEL ::",String.valueOf(orderModel));
        String itemName = "";
        for(int i =0 ; i<orderModel.getOrderPerFarmModel().getOverAllProductModelList().size(); i++){
            itemName = itemName + " , "  + orderModel.getOrderPerFarmModel().getOverAllProductModelList().get(i).getName();
        }
        exampleList.add(new MyListData(orderModel.getOrderID(),orderModel.getFarmer().getAddress(),orderModel.getFarmer().getLangitude(),orderModel.getFarmer().getLattitude(), orderModel.getFarmer().getName(),orderModel.getOrderPerFarmModel().getFarmModel().getNoOfAcres() + " acres",
                String.valueOf(orderModel.getOrderPerFarmModel().getPerFarmTotal()) + " Rs.",itemName,orderModel.getDate(),orderModel.getStatus()));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new MyListAdapter(exampleList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        circularProgressBar.setVisibility(View.GONE);

    }
    public void getUserDetails(user user){
        List<farmer>  farmerList = user.getFarmerList();
        for(int i = 0; i< farmerList.size(); i++) {
            List<Model> farmData = user.getFarmerList().get(i).getFarmdata();
            for(int j = 0 ; j< farmData.size(); j++) {
                new FireStoreClass().getAllOrderDetailsFromFirebase(this,
                        farmerList.get(i).getFarmerid(),
                        farmData.get(j).getFarmId());
            }
        }
    }

}