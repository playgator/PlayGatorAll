package play.gator.farmgator.Onboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import play.gator.farmgator.BookOrder.BookFarm;
import play.gator.farmgator.BookOrder.FinalBookOrder;
import play.gator.farmgator.Dashboard.Dashboard;
import play.gator.farmgator.FireStoreClass;
import play.gator.farmgator.R;
import play.gator.farmgator.SalesPerson.Registration;
import play.gator.farmgator.SalesPerson.user;
import play.gator.farmgator.SplashScreen.Splash;

public class FarmDetails extends AppCompatActivity {
    private LinearLayout parentLinearLayout;
    private List<Model> farmdata = new ArrayList<>();
    EditText acresspin;
    Spinner kharifspin,rabispin,zaidspin;
    RadioGroup type;
    int PERMISSION_ID = 44;
    Button submit;
    private RadioButton typename;
    FusedLocationProviderClient mFusedLocationClient;
    String Name;
    String Address;
    String Aadhar;
    String Mob_no;
    String Alt_no;
    String Whats_no;
    String Farmer_Id;

    String Adharpath;
    String Farmerpath;
    String Bankpath;
    LinearLayout rabilayout,khariflayout,zaidlayout;
    String latitude,salesManId ;
    String longitude;
    List<String> rabiCrop;
    List<String> kharifCrop;
    List<String> zaidCrop;

    String push_id;

    farmer farmer;

    List <String> mylist;

    user updatedUserDetails;
    String Kharif,Rabi,Zaid;
    int index=0;
    CropTypeModel cropTypeModel2;
    CircularProgressBar circularProgressBar;
    RelativeLayout farmaddlayout;
    EditText farmcountedittext;
    Button farmaddbutton;
    private DatabaseReference mDatabase;
    private FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_details);
        parentLinearLayout=(LinearLayout) findViewById(R.id.parent_linear_layout);
        mDatabase = FirebaseDatabase.getInstance().getReference("Farmers");

        new FireStoreClass().getCropList(this);
        circularProgressBar = (CircularProgressBar)findViewById(R.id.CircularProgressbar);
        circularProgressBar.setVisibility(View.VISIBLE);
        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.brown));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.activity_horizontal_margin));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.activity_vertical_margin));
        int animationDuration = 2500;
        circularProgressBar.setProgressWithAnimation(100, animationDuration);


        farmaddbutton=findViewById(R.id.addfarmbutton);
        farmcountedittext=findViewById(R.id.farmcount);
        farmaddlayout=findViewById(R.id.farmaddalllayout);






        Bundle bundle = getIntent().getExtras();
        Farmer_Id = String.valueOf(System.currentTimeMillis());
        salesManId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Aadhar = bundle.getString("Aadhar","Default");
        Name = bundle.getString("Name","Default");
        Address = bundle.getString("Address","Default");
        Mob_no = bundle.getString("Mob_no","Default");
        Alt_no = bundle.getString("Alt_no","Default");
        Whats_no = bundle.getString("Whats_no","Default");
        Adharpath = bundle.getString("Adharpath","Default");
        Farmerpath = bundle.getString("Farmerpath","Default");
        Bankpath = bundle.getString("Bankpath","Default");

        Log.e("Adharimg",Adharpath);
        Log.e("Farmerimg",Farmerpath);
        Log.e("Bankimg",Bankpath);
        new FireStoreClass().getSalesManDetails(this);

        submit=findViewById(R.id.button_submit_list);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(prepareFarmList(view)){
                    user user = new user();
                    user.setId(updatedUserDetails.getId());
                    user.setName(updatedUserDetails.getName());
                    user.setAadhar(updatedUserDetails.getAadhar());
                    user.setEmail(updatedUserDetails.getEmail());
                    user.setPass(updatedUserDetails.getPass());
                    user.setMob_no(updatedUserDetails.getMob_no());
                    List<farmer> farmerList = updatedUserDetails.getFarmerList();
                    Geocoder geocoder;
                    List<android.location.Address> addresses = new ArrayList<>();
                    geocoder = new Geocoder(FarmDetails.this, Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                    Log.e("Address :: ,",address);
                    Log.e("city :: ,",city);
                    Log.e("state :: ,",state);
                    Log.e("country :: ,",country);
                    Log.e("postalCode :: ,",postalCode);
                    Log.e("knownName :: ,",knownName);
                    farmer farmer = new farmer(salesManId,longitude,latitude,farmdata,Farmer_Id,Name,Aadhar,Address,Mob_no,Whats_no,Alt_no,Adharpath,Farmerpath,Bankpath);
                    farmerList.add(farmer);
                    user.setFarmerList(farmerList);
                    new FireStoreClass().addFarmDetailToFirebase(FarmDetails.this,user);
                }
            }
        });



    }
    private boolean prepareFarmList(View v) {
        boolean isAllSet = true;
        int count = parentLinearLayout.getChildCount();
        Log.e("count: ", String.valueOf(count));
        if (count < 1) {
            Toast.makeText(getApplicationContext(), "Add Farm First", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < count; i++) {
                v = parentLinearLayout.getChildAt(i);
                acresspin = v.findViewById(R.id.exp_spinner);
                type = v.findViewById(R.id.typegroup);
                kharifspin = v.findViewById(R.id.kharif_spinner);
                rabispin = v.findViewById(R.id.rabi_spinner);
                zaidspin = v.findViewById(R.id.zaid_spinner);
                int selectedId = type.getCheckedRadioButtonId();
                //ArrayList<String> languages = new ArrayList<String>();
                if (selectedId == -1) {
                    Toast.makeText(FarmDetails.this, "Select Your Type First", Toast.LENGTH_SHORT).show();
                    isAllSet = false;
                    farmdata.clear();
                    return false;

                } else {
                    typename = (RadioButton) findViewById(selectedId);
                    String Acre = acresspin.getText().toString();
                    String Type = typename.getText().toString();
                    Kharif = kharifspin.getSelectedItem().toString();
                    Rabi = rabispin.getSelectedItem().toString();
                    Zaid = zaidspin.getSelectedItem().toString();
                    if(Acre.equals("")){
                        Toast.makeText(FarmDetails.this,"Please enter number of acres !",Toast.LENGTH_SHORT).show();
                        isAllSet = false;
                        farmdata.clear();
                        return false;
                    }
                    if(Kharif.equals("Select Kharif Crop")&&Rabi.equals("Select Rabi Crop")&&Zaid.equals("Select Zaid Crop"))
                    {
                        Toast.makeText(getApplicationContext(), "You Have To Select At least One Crop in Every Farm.", Toast.LENGTH_SHORT).show();
                        isAllSet = false;
                        farmdata.clear();
                        return isAllSet;
                    }else {
                        if (Kharif.equals("Select Kharif Crop")) {
                            Kharif = "";
                            isAllSet = true;
                        }
                        if (Rabi.equals("Select Rabi Crop")) {
                            Rabi = "";
                            isAllSet = true;
                        }
                        if (Zaid.equals("Select Zaid Crop")) {
                            Zaid = "";
                            isAllSet = true;
                        }
                    }
                        // add the data to arraylist
                    if(isAllSet){
                       farmdata.add(new Model(String.valueOf(System.currentTimeMillis() + i),Type,Acre,Kharif,Rabi,Zaid));
                       Log.e("farmdatasizee", String.valueOf(farmdata.size()));
                    }


                }
            }

        }
        return isAllSet;
    }

    public void getCropList(CropTypeModel cropTypeModel){

        rabiCrop = cropTypeModel.getRabi();
        rabiCrop.add(0,"Select Rabi Crop");
        kharifCrop = cropTypeModel.getKharif();
        kharifCrop.add(0,"Select Kharif Crop");
        zaidCrop = cropTypeModel.getZaid();
        zaidCrop.add(0,"Select Zaid Crop");
        circularProgressBar.setVisibility(View.GONE);



    }



    public void AddAll(View v) {
        if(farmcountedittext.getText() == null || farmcountedittext.getText().equals("")){
            farmcountedittext.setError("Please enter no. of farms !");
            return;
        }

        String farmcount=farmcountedittext.getText().toString();
        int count = 0;
        if(!farmcount.equals("")) {
            try{
                count = Integer.parseInt(farmcount);
            }catch(Exception exception){
                Toast.makeText(FarmDetails.this,"Please enter Integer like 30, 40 etc !",Toast.LENGTH_LONG).show();
                return;
            }
            farmaddlayout.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);
            for (int i = 0; i < count; i++) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.field, null);
                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
                acresspin = rowView.findViewById(R.id.exp_spinner);
                type = rowView.findViewById(R.id.typegroup);
                kharifspin = rowView.findViewById(R.id.kharif_spinner);
                rabispin = rowView.findViewById(R.id.rabi_spinner);
                zaidspin = rowView.findViewById(R.id.zaid_spinner);
                rabilayout = rowView.findViewById(R.id.rabi_layout);
                khariflayout = rowView.findViewById(R.id.kharif_layout);
                zaidlayout = rowView.findViewById(R.id.zaid_layout);


                Log.e("Size ::", String.valueOf(rabiCrop.size()));
                Log.e("List ::", String.valueOf(rabiCrop));
                String[] wee = rabiCrop.toArray(new String[rabiCrop.size()]);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_spinner_item, wee);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                rabispin.setAdapter(spinnerArrayAdapter);
                String[] wee2 = kharifCrop.toArray(new String[kharifCrop.size()]);
                ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                        this, android.R.layout.simple_spinner_item, wee2);
                spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                kharifspin.setAdapter(spinnerArrayAdapter2);

                String[] wee3 = zaidCrop.toArray(new String[zaidCrop.size()]);
                ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                        this, android.R.layout.simple_spinner_item, wee3);
                spinnerArrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                zaidspin.setAdapter(spinnerArrayAdapter3);

                // create an object of Language class
                //ArrayList<String> languages = new ArrayList<String>();

            }
        }

    }
    public void getUpdatedUserDetails(user user){
        updatedUserDetails = user;
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                //LatLng mycordinates=new LatLng(location.getLatitude(),location.getLongitude());

                                if (location == null) {
                                    requestNewLocationData();
                                } else {

                                    Log.e("Lattitude: ", String.valueOf(location.getLatitude()));
                                    Log.e("Longitude", String.valueOf(location.getLongitude()));

                                    latitude = String.valueOf(location.getLatitude());
                                    longitude = String.valueOf(location.getLongitude());

                                    String Cordinates="Lattitude:"+ String.valueOf(location.getLatitude())+"Longitude:"+ String.valueOf(location.getLongitude());
                                    //   Toast.makeText(getApplicationContext(), Cordinates, Toast.LENGTH_SHORT).show();
                                    // latTextView.setText(location.getLatitude()+"");
                                    // lonTextView.setText(location.getLongitude()+"");

                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }
    public void onAddField(View v) {

        if (checkPermissions()) {
            if (isLocationEnabled()) {
                getLastLocation();
                LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView=inflater.inflate(R.layout.field, null);
                // Add the new row before the add field button.
                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
                int count = parentLinearLayout.getChildCount();
                Log.e("count ::", String.valueOf(count));
                for (int i = 0; i < count; i++) {
                    v = parentLinearLayout.getChildAt(i);

                    kharifspin = v.findViewById(R.id.kharif_spinner);
                    rabispin = v.findViewById(R.id.rabi_spinner);
                    zaidspin = v.findViewById(R.id.zaid_spinner);
                    rabilayout=v.findViewById(R.id.rabi_layout);
                    khariflayout=v.findViewById(R.id.kharif_layout);
                    zaidlayout=v.findViewById(R.id.zaid_layout);

                    Log.e("Size ::", String.valueOf(rabiCrop.size()));
                    Log.e("List ::",String.valueOf(rabiCrop));
                    String[] wee = rabiCrop.toArray(new String[rabiCrop.size()]);
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            this, android.R.layout.simple_spinner_item, wee);
                    spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                    rabispin.setAdapter(spinnerArrayAdapter);
                    String[] wee2 = kharifCrop.toArray(new String[kharifCrop.size()]);
                    ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                            this, android.R.layout.simple_spinner_item, wee2);
                    spinnerArrayAdapter2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                    kharifspin.setAdapter(spinnerArrayAdapter2);

                    String[] wee3 = zaidCrop.toArray(new String[zaidCrop.size()]);
                    ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                            this, android.R.layout.simple_spinner_item, wee3);
                    spinnerArrayAdapter3.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                    zaidspin.setAdapter(spinnerArrayAdapter3);
                    // create an object of Language class
                    //ArrayList<String> languages = new ArrayList<String>();



                }
                Log.e("method caled :","yes");

                Log.e("Size ::", String.valueOf(rabiCrop.size()));
                Log.e("List ::",String.valueOf(rabiCrop));

                submit.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(getApplicationContext(), "Please Turn On Your Location First", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Please Accept Permission Your Location First", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            // latTextView.setText(mLastLocation.getLatitude()+"");
            //  lonTextView.setText(mLastLocation.getLongitude()+"");


        }
    };

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }
    public void farmDetailsAddedSuccess(){
        Toast.makeText(this,"FarmDetails Added Successfully !",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(FarmDetails.this, Dashboard.class);
        startActivity(i);
        finish();
    }

}