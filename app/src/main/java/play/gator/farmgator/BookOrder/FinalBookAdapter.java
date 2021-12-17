package play.gator.farmgator.BookOrder;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import play.gator.farmgator.BookOrder.ProductAdapters.FertilizerAdapter;
import play.gator.farmgator.BookOrder.ProductAdapters.NutrientsAdapter;
import play.gator.farmgator.BookOrder.ProductAdapters.PesticideAdapter;
import play.gator.farmgator.R;

public class FinalBookAdapter extends RecyclerView.Adapter<FinalBookAdapter.UserViewHolder> {

    private Context mContext;
    List<ItemDatamodel> userList= new ArrayList<>();
    private ArrayAdapter<Customer> dataAdapter;
    FertilizerAdapter adapter;
    PesticideAdapter adapter2;
    NutrientsAdapter adapter3;

    List<ProductModel> list = new ArrayList<>();
    List<ProductModel> list2 = new ArrayList<>();
    List<ProductModel> list3 = new ArrayList<>();
    EditText etEmail;
    String finall,number;
    int sum1,sum2,sum3,lastsum=0;
    TextView carttotal;
    int total,quantity;
    String data = "";
    public FinalBookAdapter(Context context, List<ItemDatamodel> userList){
        this.mContext = context;
        this.userList = userList;

        String[] fertiproduct = context.getResources().getStringArray(R.array.item_fertilzer);
        String[] price = context.getResources().getStringArray(R.array.item_fertilzer_price);

        for (int i=0;i<6;i++){

            ProductModel productModel = new ProductModel(fertiproduct[i],price[i],number,total,false);
            list.add(productModel);
        }

        String[] prestproduct = mContext.getResources().getStringArray(R.array.item_pesticide);
        String[] prestprice = mContext.getResources().getStringArray(R.array.item_fertilzer_price);

        for (int i=0;i<6;i++){

            ProductModel productModel = new ProductModel(prestproduct[i],prestprice[i],number,total,false);
            list2.add(productModel);
        }
        String[] nutriproduct = mContext.getResources().getStringArray(R.array.item_nutrients);
        String[] nutriprice = mContext.getResources().getStringArray(R.array.item_fertilzer_price);

        for (int i=0;i<6;i++){

            ProductModel productModel = new ProductModel(nutriproduct[i],nutriprice[i],number,total,false);
            list3.add(productModel);
        }

    }


    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(view);


        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        ItemDatamodel itemDatamodel = userList.get(position);
        Integer farmid = new Integer( userList.get(position).getFarmid());
        farmid = farmid++;
        holder.tv_1.setText("Farm "+farmid);
        holder.tv_2.setText(userList.get(position).getUserName());



       holder.fertilizer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


              LayoutInflater inflater = LayoutInflater.from(mContext);
               View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);

               final RecyclerView recyclerView = alertLayout.findViewById(R.id.recycler_view_fertilizer);
               final TextView title = alertLayout.findViewById(R.id.toolbar_review1);


               title.setText("Fertlizer Products");
               recyclerView.setLayoutManager(new LinearLayoutManager(mContext));



               adapter = new FertilizerAdapter(mContext,list,number,finall);
               recyclerView.setAdapter(adapter);



               TextView textView = new TextView(mContext);
               textView.setText("Select Fertilizer");
               textView.setPadding(20, 30, 20, 30);
               textView.setTextSize(20F);
               textView.setBackgroundColor(Color.parseColor("#FD7810"));
               textView.setTextColor(Color.WHITE);
               AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
               alert.setCustomTitle(textView);
               // this is set the view from XML inside AlertDialog
               alert.setView(alertLayout);
               // disallow cancel of AlertDialog on click of back button and outside touch
               alert.setCancelable(true);
               alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       //  Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                       dialog.cancel();
                   }
               });
               alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       List<ProductModel> list_fertilizer = adapter.getFruitsList();


                       for (int i=0;i<list_fertilizer.size();i++){

                           ProductModel productModel = list_fertilizer.get(i);
                           sum1+=productModel.getTotal();
                           if (productModel.isSelected() == true){
                               data = data + "\n" + productModel.getName().toString() + "   " + productModel.getPrice().toString() + "   "
                                       + productModel.getNumber().toString() +  "   "+ productModel.getTotal() + "   "+ sum1;
                           }
                       }
                       lastsum=sum1+sum2+sum3;
                       itemDatamodel.setTotal(lastsum);
                       Toast.makeText(mContext,"Your Products lastsum : \n " + lastsum,Toast.LENGTH_SHORT)
                               .show();
                       Toast.makeText(mContext,"Your Products Selected : \n " + data,Toast.LENGTH_SHORT)
                               .show();
                       Toast.makeText(mContext,"Your Products total : \n " + sum1,Toast.LENGTH_SHORT)
                               .show();

                   }
               });
               AlertDialog dialog = alert.create();

               dialog.show();
               dialog.getWindow().clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
               dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
               Button buttonbackground = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
               buttonbackground.setTextColor(mContext.getResources().getColor(R.color.brown));
               Button buttonbackground2 = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
               buttonbackground2.setTextColor(mContext.getResources().getColor(R.color.orange));



           }
       });

        holder.pestiside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                View alertLayout = inflater.inflate(R.layout.layout_custom_dialog2, null);

                final RecyclerView recyclerView = alertLayout.findViewById(R.id.recycler_view_pesticide);
                final TextView title = alertLayout.findViewById(R.id.toolbar_review1);


                title.setText("Pestiside Products");
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));



                adapter2 = new PesticideAdapter(mContext,list2,number,finall);
                recyclerView.setAdapter(adapter2);

                TextView textView = new TextView(mContext);
                textView.setText("Select Pestiside");
                textView.setPadding(20, 30, 20, 30);
                textView.setTextSize(20F);
                textView.setBackgroundColor(Color.parseColor("#FD7810"));
                textView.setTextColor(Color.WHITE);
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setCustomTitle(textView);
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(true);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<ProductModel> list_fertilizer2 = adapter2.getFruitsList2();


                        for (int i=0;i<list_fertilizer2.size();i++){

                            ProductModel productModel = list_fertilizer2.get(i);
                            sum2+=productModel.getTotal();
                            if (productModel.isSelected() == true){
                                data = data + "\n" + productModel.getName().toString() + "   " + productModel.getPrice().toString() + "   "
                                        + productModel.getNumber().toString() +  "   "+ productModel.getTotal() + "   "+ sum2;
                            }
                        }
                        lastsum=sum1+sum2+sum3;
                        itemDatamodel.setTotal(lastsum);

                        Toast.makeText(mContext,"Your Products Selected : \n " + data,Toast.LENGTH_SHORT)
                                .show();
                        Toast.makeText(mContext,"Your Products total for pestiside : \n " + sum2,Toast.LENGTH_SHORT)
                                .show();

                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
                dialog.getWindow().clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                Button buttonbackground = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonbackground.setTextColor(mContext.getResources().getColor(R.color.brown));
                Button buttonbackground2 = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonbackground2.setTextColor(mContext.getResources().getColor(R.color.orange));

            }
        });

        holder.nutrients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                View alertLayout = inflater.inflate(R.layout.layout_custom_diolog3, null);

                final RecyclerView recyclerView = alertLayout.findViewById(R.id.recycler_view_nutrients);
                final TextView title = alertLayout.findViewById(R.id.toolbar_review1);


                title.setText("Nutrients Products");
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));




                adapter3 = new NutrientsAdapter(mContext,list3,number,finall);
                recyclerView.setAdapter(adapter3);

                TextView textView = new TextView(mContext);
                textView.setText("Select Nutrients");
                textView.setPadding(20, 30, 20, 30);
                textView.setTextSize(20F);
                textView.setBackgroundColor(Color.parseColor("#FD7810"));
                textView.setTextColor(Color.WHITE);
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setCustomTitle(textView);
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(true);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<ProductModel> list_fertilizer3 = adapter3.getFruitsList3();


                        for (int i=0;i<list_fertilizer3.size();i++){

                            ProductModel productModel = list_fertilizer3.get(i);
                            sum3+=productModel.getTotal();
                            if (productModel.isSelected() == true){
                                data = data + "\n" + productModel.getName().toString() + "   " + productModel.getPrice().toString() + "   "
                                        + productModel.getNumber().toString() +  "   "+ productModel.getTotal() + "   "+ sum3;
                            }
                        }
                        lastsum=sum1+sum2+sum3;
                        itemDatamodel.setTotal(lastsum);
                        Toast.makeText(mContext,"Your Products Selected : \n " + data,Toast.LENGTH_SHORT)
                                .show();
                        Toast.makeText(mContext,"Your Products total for pestiside : \n " + sum3,Toast.LENGTH_SHORT)
                                .show();

                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
                dialog.getWindow().clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                Button buttonbackground = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonbackground.setTextColor(mContext.getResources().getColor(R.color.brown));
                Button buttonbackground2 = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonbackground2.setTextColor(mContext.getResources().getColor(R.color.orange));

            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
    public class UserViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_1;
        public TextView tv_2,fertilizer,pestiside,nutrients;
        public Spinner spinner;

        public UserViewHolder(View itemView) {
            super(itemView);
            tv_1 = (TextView)itemView.findViewById(R.id.tv_1);
            tv_2 = (TextView)itemView.findViewById(R.id.tv_2);

            fertilizer = (TextView)itemView.findViewById(R.id.fertilizer);
            pestiside = (TextView)itemView.findViewById(R.id.pestiside);
            nutrients = (TextView)itemView.findViewById(R.id.nutrients);




        }
    }
}