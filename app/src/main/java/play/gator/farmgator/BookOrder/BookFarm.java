package play.gator.farmgator.BookOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import play.gator.farmgator.Onboard.Model;
import play.gator.farmgator.Onboard.farmer;
import play.gator.farmgator.R;

public class BookFarm extends AppCompatActivity {
    private LinearLayout parentLinearLayout;
    farmer farmer;
    List<Model> farmModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_farm);
        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);

        if(getIntent().hasExtra("farmerModel") && getIntent().hasExtra("farmDataList")){
            farmer = (farmer) getIntent().getSerializableExtra("farmerModel");
            farmModelList = (List<Model>) getIntent().getSerializableExtra("farmDataList");
        }

        for (int i = 0; i < farmModelList.size(); i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.itemproductcount, null);
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);

            final TextView farm = rowView.findViewById(R.id.farmno);
            int i1=i+1;
            String text = "Farm"+i1 + " " + "( " + farmModelList.get(i).getNoOfAcres() + " acres" + " )";
            farm.setText(text);
            final TextView farmitem = rowView.findViewById(R.id.farmitem);
            int finalI = i;
            farmitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BookFarm.this, FinalBookOrder.class);
                    intent.putExtra("farmerDetails",farmer);
                    intent.putExtra("farmDataDetails",farmModelList.get(finalI));
                    intent.putExtra("farmno", "Farm"+i1);  // pass your values and retrieve them in the other Activity using AnyKeyName
                    startActivity(intent);
                }
            });

        }
    }
}