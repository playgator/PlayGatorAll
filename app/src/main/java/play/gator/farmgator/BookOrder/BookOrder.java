package play.gator.farmgator.BookOrder;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import play.gator.farmgator.FireStoreClass;
import play.gator.farmgator.Onboard.farmer;
import play.gator.farmgator.R;

public class BookOrder extends AppCompatActivity {
    private ShowBookAdapter adapter;
    private List<farmer> exampleList;
    CircularProgressBar circularProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_order);

        circularProgressBar = (CircularProgressBar)findViewById(R.id.CircularProgressbar);
        circularProgressBar.setVisibility(View.VISIBLE);
        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.brown));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.activity_horizontal_margin));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.activity_vertical_margin));
        int animationDuration = 2500;
        circularProgressBar.setProgressWithAnimation(100, animationDuration);
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
        new FireStoreClass().getFarmDetailsV2(this);
        //setSupportActionBar(toolbar);
//        fillExampleList();
        // setUpRecyclerView();
    }
    public void getFarmerDetailsList(List<farmer> list){
       circularProgressBar.setVisibility(View.GONE);
        Log.e("Farmerlist", Arrays.toString(list.toArray()));
        RecyclerView recyclerView = findViewById(R.id.rvItems);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        this.exampleList = list;
        int listSize = exampleList.size();

        for (int i = 0; i<listSize; i++){
            Log.e("exampleList name: ", String.valueOf(exampleList.get(i)));
        }

        adapter=new ShowBookAdapter(BookOrder.this,list);
        recyclerView.setAdapter(adapter);
    }
    private void fillExampleList() {



        final DatabaseReference nm= FirebaseDatabase.getInstance().getReference("Farmers");

        nm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
//                        String value = null;
//                        value = npsnapshot.getValue(String.class);
                        String name = (String) npsnapshot.child("name").getValue();
                        String langitude = (String) npsnapshot.child("langitude").getValue();
                        farmer l=new farmer(langitude,"","",name,"","","","","","","","");

                        exampleList.add(l);
                    }
                    Log.d("list", exampleList.toString());
//                    adapter=new ShowBookAdapter(BookOrder.this,exampleList);
//                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        exampleList = new ArrayList<>();
//        exampleList.add(new ExampleItem("Rajender","1235648975","Ramgadh","2"));
//        exampleList.add(new ExampleItem("Rahul","2535648975","Mandhana","1"));
//        exampleList.add(new ExampleItem("Ravi","7835648975","Rambagh","4"));
//        exampleList.add(new ExampleItem("Shyam","2635648975","Sampark","3"));
//        exampleList.add(new ExampleItem("Aakash","2935648975","Yuvender","1"));
//        exampleList.add(new ExampleItem("Vikas","3535648975","Manav","4"));

    }
    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rvItems);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ShowBookAdapter(BookOrder.this,exampleList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}