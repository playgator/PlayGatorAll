package play.gator.farmgator.BookOrder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import play.gator.farmgator.BookOrder.ProductAdapters.FertilizerAdapter;
import play.gator.farmgator.BookOrder.ProductAdapters.NutrientsAdapter;
import play.gator.farmgator.BookOrder.ProductAdapters.PesticideAdapter;
import play.gator.farmgator.BookOrder.ProductAdapters.ProductTypeModel;
import play.gator.farmgator.BookOrder.ProductAdapters.SpecialAdapter;
import play.gator.farmgator.BookOrder.ProductAdapters.SupplimentAdapter;
import play.gator.farmgator.Dashboard.Dashboard;
import play.gator.farmgator.FireStoreClass;
import play.gator.farmgator.Onboard.CropTypeModel;
import play.gator.farmgator.Onboard.Model;
import play.gator.farmgator.Onboard.farmer;
import play.gator.farmgator.R;
import play.gator.farmgator.SalesPerson.Registration;
import play.gator.farmgator.SalesPerson.user;
import play.gator.farmgator.SplashScreen.Splash;


import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class FinalBookOrder extends AppCompatActivity {

    Spinner spinner;
    private Context mContext;
    private RecyclerView recycler;
    private LinearLayout parentLinearLayout;
    String finall,number;
    int sum1=0,sum2=0,sum3=0,sum4=0,sum5=0,lastsum=0;
    TextView carttotal;
    int total,quantity;
    String data1="Fertilizer",data2="Pesticide",data3="Implements",data4="Special",data5="Organic";
    List<ProductModel> list = new ArrayList<>();
    List<ProductModel> list2 = new ArrayList<>();
    TextView fertilizer,totalfinal;
    FertilizerAdapter adapter;
    PesticideAdapter adapter2;
    NutrientsAdapter adapter3;
    SpecialAdapter adapter4;
    SupplimentAdapter adapter5;
    List<ProductModel> list3 = new ArrayList<>();
    List<ProductModel> list4 = new ArrayList<>();
    List<ProductModel> list5 = new ArrayList<>();
    String farmno;
    TextView farmnotext;
    String finalitems="";
    Button submitButton;
    int orderPerFarmTotal;

    farmer clickedFarmer;
    List<Model> farmModelList;
    OrderModel previousOrderDetails;

    List<String> fertiliserlist = new ArrayList<>();
    List<String> implementslist = new ArrayList<>();
    List<String> organiclist = new ArrayList<>();
    List<String> pestisidelist = new ArrayList<>();
    List<String> speciallist = new ArrayList<>();
    List<ProductModel> fertiliserlists = new ArrayList<>();
    List<ProductModel> implementslists = new ArrayList<>();
    List<ProductModel> organiclists = new ArrayList<>();
    List<ProductModel> pestisidelists = new ArrayList<>();
    List<ProductModel> speciallists = new ArrayList<>();
    List<ProductModel> overAllProductModelList = new ArrayList<>();
    Model clickedFarmData;
    user updatedUserDetails;
    CircularProgressBar circularProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_book_order);
        mContext = this;
        parentLinearLayout=(LinearLayout) findViewById(R.id.parent_linear_layout);
        fertilizer=findViewById(R.id.fertilizer);
        totalfinal=findViewById(R.id.totalExpense);
         farmnotext=findViewById(R.id.farmno);

        submitButton = findViewById(R.id.submitexpenses);

        new FireStoreClass().getOrderDetailsFromFirebase(this);

        new FireStoreClass().getSalesManDetails(this);


        new FireStoreClass().getProductList(this);

        circularProgressBar = (CircularProgressBar)findViewById(R.id.CircularProgressbar);
        circularProgressBar.setVisibility(View.VISIBLE);
        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.brown));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.activity_horizontal_margin));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.activity_vertical_margin));
        int animationDuration = 2500;
        circularProgressBar.setProgressWithAnimation(100, animationDuration);
        farmno = getIntent().getExtras().getString("farmno");
        Toast.makeText(getApplicationContext(), "farm no."+farmno, Toast.LENGTH_SHORT).show();


//        List<ItemDatamodel> userList = new ArrayList<>();
//        for(int i = 0; i< farmModelList.size(); i++){
//            ItemDatamodel user = new ItemDatamodel();
//            Log.e("No of Acre ::",farmModelList.get(i).getNoOfAcres());
//            user.setTotal(Integer.parseInt(farmModelList.get(i).getNoOfAcres()));
//            userList.add(user);
//        }

        if(getIntent().hasExtra("farmDataDetails")){
            clickedFarmData = (Model) getIntent().getSerializableExtra("farmDataDetails");
        }
        if(getIntent().hasExtra("farmerDetails")){
            clickedFarmer = (farmer) getIntent().getSerializableExtra("farmerDetails");
        }




            LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView=inflater.inflate(R.layout.recycler_item, null);
            TextView noOfAcres = rowView.findViewById(R.id.tv_2);
            noOfAcres.setText(clickedFarmData.getNoOfAcres());
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
            farmnotext=rowView.findViewById(R.id.farmno);
            farmnotext.setText(farmno);
            final TextView fertilizer =  rowView.findViewById(R.id.fertilizer);
            fertilizer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);

                    final TextView title = alertLayout.findViewById(R.id.toolbar_review1);
                    final RecyclerView recyclerView = alertLayout.findViewById(R.id.recycler_view_fertilizer);
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
                            fertiliserlists = adapter.getFruitsList();



                            for (int i=0;i<list_fertilizer.size();i++){

                                ProductModel productModel = list_fertilizer.get(i);
                                sum1+=productModel.getTotal();
                                if (productModel.isSelected() == true){
                                    data1= data1 +"\n"+productModel.getName().toString();
                                    overAllProductModelList.add(new ProductModel(productModel.getName(), productModel.getPrice(), productModel.getNumber(), productModel.getTotal()));
                                }
                            }


//                            finalitems=data1+data2+data3;
                            lastsum=sum1+sum2+sum3+sum4+sum5;
                            totalfinal.setText(String.valueOf(lastsum));
                            Toast.makeText(mContext,"Your Products lastsum : \n " + lastsum,Toast.LENGTH_SHORT)
                                    .show();
//                            Toast.makeText(mContext,"Your Products Selected : \n " + finalitems,Toast.LENGTH_SHORT)
//                                    .show();
                            Log.e("Selected Item Ferti",data1);
                            Toast.makeText(mContext,"Your Products total : \n " + sum1,Toast.LENGTH_SHORT)
                                    .show();
                            fertilizer.setText("Selected");
                            fertilizer.setEnabled(false);

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

            final TextView prestiside =  rowView.findViewById(R.id.pestiside);

            prestiside.setOnClickListener(new View.OnClickListener() {
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
                            pestisidelists = adapter2.getFruitsList2();


                            for (int i=0;i<list_fertilizer2.size();i++){

                                ProductModel productModel = list_fertilizer2.get(i);
                                sum2+=productModel.getTotal();
                                if (productModel.isSelected() == true){
                                    data2 = data2+"\n"+ productModel.getName().toString() ;
                                    overAllProductModelList.add(new ProductModel(productModel.getName(), productModel.getPrice(), productModel.getNumber(), productModel.getTotal()));
                                }
                            }

                            lastsum=sum1+sum2+sum3+sum4+sum5;
                            totalfinal.setText(String.valueOf(lastsum));
                            Log.e("Selected Item presti",data2);
                            prestiside.setText("Selected");
                            prestiside.setEnabled(false);
//                            Toast.makeText(mContext,"Your Products Selected : \n " + data2,Toast.LENGTH_SHORT)
//                                    .show();
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


        final TextView nutrients =  rowView.findViewById(R.id.nutrients);

        nutrients.setOnClickListener(new View.OnClickListener() {
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
                textView.setText("Select Implement");
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
                        implementslists = adapter3.getFruitsList3();


                        for (int i=0;i<list_fertilizer3.size();i++){

                            ProductModel productModel = list_fertilizer3.get(i);
                            sum3+=productModel.getTotal();
                            if (productModel.isSelected() == true){
                                data3 =  data3 +"\n"+productModel.getName().toString() ;
                                overAllProductModelList.add(new ProductModel(productModel.getName(), productModel.getPrice(), productModel.getNumber(), productModel.getTotal()));
                            }
                        }



                        //finalitems= finalitems.concat(data3);
                        lastsum=sum1+sum2+sum3+sum4+sum5;
                        totalfinal.setText(String.valueOf(lastsum));
                        Toast.makeText(mContext,"Your Products lastsum : \n " + lastsum,Toast.LENGTH_SHORT)
                                .show();
//                        Toast.makeText(mContext,"Your Products Selected : \n " + data3,Toast.LENGTH_SHORT)
//                                .show();
                        Log.e("Selected Item nutrients",data3);
                        Toast.makeText(mContext,"Your Products total : \n " + sum1,Toast.LENGTH_SHORT)
                                .show();
                        nutrients.setText("Selected");
                        nutrients.setEnabled(false);

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


        final TextView special =  rowView.findViewById(R.id.special);

        special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                View alertLayout = inflater.inflate(R.layout.layout_custom_dialog4, null);

                final RecyclerView recyclerView = alertLayout.findViewById(R.id.recycler_view_special);
                final TextView title = alertLayout.findViewById(R.id.toolbar_review1);


                title.setText("Special Products");
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));




                adapter4 = new SpecialAdapter(mContext,list4,number,finall);
                recyclerView.setAdapter(adapter4);

                TextView textView = new TextView(mContext);
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
                        List<ProductModel> list_fertilizer3 = adapter4.getFruitsList4();
                        speciallists = adapter4.getFruitsList4();


                        for (int i=0;i<list_fertilizer3.size();i++){

                            ProductModel productModel = list_fertilizer3.get(i);
                            sum4+=productModel.getTotal();
                            if (productModel.isSelected() == true){
                                data4 =  data4 +"\n"+productModel.getName().toString() ;
                                overAllProductModelList.add(new ProductModel(productModel.getName(), productModel.getPrice(), productModel.getNumber(), productModel.getTotal()));
                            }
                        }



                        //finalitems= finalitems.concat(data3);
                        lastsum=sum1+sum2+sum3+sum4+sum5;
                        totalfinal.setText(String.valueOf(lastsum));
                        Toast.makeText(mContext,"Your Products lastsum : \n " + lastsum,Toast.LENGTH_SHORT)
                                .show();
//                        Toast.makeText(mContext,"Your Products Selected : \n " + data4,Toast.LENGTH_SHORT)
//                                .show();
                        Log.e("Selected Item special",data4);

                        special.setText("Selected");
                        special.setEnabled(false);

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

        final TextView suppliment =  rowView.findViewById(R.id.supliments);

        suppliment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                View alertLayout = inflater.inflate(R.layout.layout_custom_dialog5, null);

                final RecyclerView recyclerView = alertLayout.findViewById(R.id.recycler_view_suppliment);
                final TextView title = alertLayout.findViewById(R.id.toolbar_review1);


                title.setText("Organic MA");
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));




                adapter5 = new SupplimentAdapter(mContext,list5,number,finall);
                recyclerView.setAdapter(adapter5);

                TextView textView = new TextView(mContext);
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
                        List<ProductModel> list_fertilizer3 = adapter5.getFruitsList5();
                        organiclists = adapter5.getFruitsList5();


                        for (int i=0;i<list_fertilizer3.size();i++){

                            ProductModel productModel = list_fertilizer3.get(i);
                            sum5+=productModel.getTotal();
                            if (productModel.isSelected() == true){
                                data5 =  data5 +"\n"+productModel.getName().toString() ;
                                overAllProductModelList.add(new ProductModel(productModel.getName(), productModel.getPrice(), productModel.getNumber(), productModel.getTotal()));
                            }
                        }



                        //finalitems= finalitems.concat(data3);
                        lastsum=sum1+sum2+sum3+sum4+sum5;
                        totalfinal.setText(String.valueOf(lastsum));
                        Toast.makeText(mContext,"Your Products lastsum : \n " + lastsum,Toast.LENGTH_SHORT)
                                .show();

                        Log.e("Selected Item suppliment",data5);

                        suppliment.setText("Selected");
                        suppliment.setEnabled(false);

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


        submitButton.setOnClickListener(v->{
            prepareSubmitModel();
        });



//        recycler = (RecyclerView)findViewById(R.id.recycler);
//        recycler.setLayoutManager(new LinearLayoutManager(mContext));
//        FinalBookAdapter finalBookAdapter = new FinalBookAdapter(mContext, ItemDatamodel.getUserList());
//        recycler.setAdapter(finalBookAdapter);
    }
    public void getUpdatedUserDetails(user user){
        updatedUserDetails = user;
    }
    public void getProductList(ProductTypeModel productTypeModel){

        fertiliserlist = productTypeModel.getFertiliser();
        implementslist = productTypeModel.getImplements();
        organiclist = productTypeModel.getOrganicMA();
        pestisidelist = productTypeModel.getPesticide();
        speciallist = productTypeModel.getSpeciality();

        Log.e("GetProductListfinal :", String.valueOf(productTypeModel));
        String[] price = getResources().getStringArray(R.array.item_fertilzer_price);


            for (int i=0;i<fertiliserlist.size();i++){

                ProductModel productModel = new ProductModel(fertiliserlist.get(i),price[i],number,total,false);
                list.add(productModel);
            }



            for (int i = 0; i < pestisidelist.size(); i++) {

                ProductModel productModel = new ProductModel(pestisidelist.get(i), price[i], number, total, false);
                list2.add(productModel);
            }


            for (int i = 0; i < implementslist.size(); i++) {

                ProductModel productModel = new ProductModel(implementslist.get(i), price[i], number, total, false);
                list3.add(productModel);
            }



            for (int i = 0; i < speciallist.size(); i++) {

                ProductModel productModel = new ProductModel(speciallist.get(i), price[i], number, total, false);
                list4.add(productModel);
            }


            for (int i = 0; i < organiclist.size(); i++) {

                ProductModel productModel = new ProductModel(organiclist.get(i), price[i], number, total, false);
                list5.add(productModel);
            }
        circularProgressBar.setVisibility(View.GONE);



    }

    public void prepareSubmitModel(){

        if(data1.matches("Fertilizer")&&data2.matches("Pesticide")&&data3.matches("Implements")&&
                data4.matches("Special")&&data5.matches("Organic")){
            Toast.makeText(getApplicationContext(), "Please Choose At least One Category", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.e("printOrderModel :",String.valueOf(overAllProductModelList.get(0).getName()));

            int prevTotal = 0;
            if(previousOrderDetails != null && previousOrderDetails.getOverAllTotal() != 0){
                prevTotal = previousOrderDetails.getOverAllTotal();
            }
            OrderModel orderModel = new OrderModel();
            orderModel.setOrderID(String.valueOf(System.currentTimeMillis()));
            orderModel.setFarmer(clickedFarmer);
            orderModel.setStatus("Pending!");
            orderModel.setOverAllTotal(prevTotal + Integer.parseInt( totalfinal.getText().toString()));
            Log.e("OrderModel :",String.valueOf(overAllProductModelList.get(0).getName() + " : " + overAllProductModelList.get(0).getTotal() + " : "));

            orderModel.setOrderPerFarmModel(new OrderPerFarmModel(clickedFarmData,clickedFarmData.getFarmId(),Integer.parseInt(totalfinal.getText().toString()),overAllProductModelList));
            orderModel.setDate(getFormattedDate());


            new FireStoreClass().putOrderDetailsToFirebase(this,orderModel,clickedFarmer.getFarmerid(),clickedFarmData.getFarmId());
            new FireStoreClass().putSalesManDetailsToFirebase(this,updatedUserDetails);
            farmer.FarmData model = new farmer.FarmData();
            model.farmDataList = clickedFarmer.getFarmdata();
            new FireStoreClass().putFarmDetailsToFirebase(this,model,clickedFarmer.getFarmerid());
        }

    }
    public void getOrderDetails(OrderModel orderModel){
        previousOrderDetails= orderModel;
    }
    public void successfullyPlaced(){
        Toast.makeText(this,"Order Placed Successfully ! Thank You ",Toast.LENGTH_LONG).show();
        Intent i = new Intent(FinalBookOrder.this, Dashboard.class);
        startActivity(i);
        finish();
    }
    public void successfullyAddedUser(){
        //Toast.makeText(this,"Added User Successfully ! Thank You ",Toast.LENGTH_LONG).show();
        Log.e("Order Placed Successfully ! Thank You ","Order Placed Successfully ! Thank You ");
    }
    private String getFormattedDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Log.e("Current Date :",df.format(c));
        return df.format(c);

    }
}