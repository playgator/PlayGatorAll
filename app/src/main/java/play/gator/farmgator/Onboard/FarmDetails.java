package play.gator.farmgator.Onboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import play.gator.farmgator.R;

public class FarmDetails extends AppCompatActivity {
    private LinearLayout parentLinearLayout;
    private ArrayList<Model> modelList = new ArrayList<>();
    Spinner acresspin,kharifspin,rabispin;
    RadioGroup type;
    int PERMISSION_ID = 44;
    Button submit;
    private RadioButton typename;
    FusedLocationProviderClient mFusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_details);
        parentLinearLayout=(LinearLayout) findViewById(R.id.parent_linear_layout);

        submit=findViewById(R.id.button_submit_list);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelList.clear();
                // this counts the no of child layout
                // inside the parent Linear layout
                int count = parentLinearLayout.getChildCount();
                Log.e("count: ", String.valueOf(count));
                if(count<1)
                {
                    Toast.makeText(getApplicationContext(), "Add Farm First", Toast.LENGTH_SHORT).show();
                }
                    else {



                    for (int i = 0; i < count; i++) {
                        v = parentLinearLayout.getChildAt(i);

                        acresspin = v.findViewById(R.id.exp_spinner);
                        type = v.findViewById(R.id.typegroup);
                        kharifspin = v.findViewById(R.id.kharif_spinner);
                        rabispin = v.findViewById(R.id.rabi_spinner);

                        int selectedId = type.getCheckedRadioButtonId();
                        // create an object of Language class
                        //ArrayList<String> languages = new ArrayList<String>();
                        if(selectedId==-1){
                            Toast.makeText(FarmDetails.this, "Select Your Type First", Toast.LENGTH_SHORT).show();

                        }
                        else{

                            typename = (RadioButton) findViewById(selectedId);

                        Model model = new Model();
                        model.acres = acresspin.getSelectedItem().toString();
                        model.type = typename.getText().toString();
                        model.kharif = kharifspin.getSelectedItem().toString();
                        model.rabi = rabispin.getSelectedItem().toString();

                        // add the data to arraylist
                        modelList.add(model);

                        Log.e("Member acres: ", modelList.get(i).acres);
                        Log.e("Member type: ", modelList.get(i).type);
                        Log.e("Member kharif: ", modelList.get(i).kharif);
                        Log.e("Member rabi: ", modelList.get(i).rabi);
                        }
                    }
                }
            }
        });

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
                                    String Cordinates="Lattitude:"+ String.valueOf(location.getLatitude())+"Longitude:"+ String.valueOf(location.getLongitude());
                                    Toast.makeText(getApplicationContext(), Cordinates, Toast.LENGTH_SHORT).show();
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
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

}