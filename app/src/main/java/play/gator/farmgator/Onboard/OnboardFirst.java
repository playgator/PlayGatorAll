package play.gator.farmgator.Onboard;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import play.gator.farmgator.R;

public class OnboardFirst extends AppCompatActivity {


    private static final int SELECT_PICTURE = 100;
    LinearLayout Adharlayout, Farmerlayout, Banklayout;
    Uri Aadharfinal;
    String Storage_Path = "All_Image_Uploads/";

    // Root Database Name for Firebase Database.
    String Database_Path = "All_Image_Uploads_Database";

    ImageView Adharimage, farmerimage, bankimage;
    String layout, name, aadhar, address, mob_no, whats_no, alt_no;
    EditText Farmer_name, Farmer_aadhar, Farmer_address, Farmer_mob_no, Farmer_whats_no, Farmer_alt_no;
    Button Submit;
    CheckBox contact;
    String Farmer_Id = "000000";
    String aadharImgUri;
    String farmerImgUri;
    String bnkchkImgUri;
    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;
    String Adharpath, Bankpath, Farmerpath;
    String farmerid;
    String Name;
    String Address;
    String Aadhar;
    String Mob_no;
    String Alt_no;
    String Whats_no;
    String AadharImgUri;
    String FarmerImgUri;
    String BnkChkImgUri;
    ProgressDialog progressDoalog;
    CheckBox checkbox;
    private DatabaseReference mDatabase;
    private Uri adharUri, farmerUri, BankUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard_first);

        Adharlayout = findViewById(R.id.LinearLayout1);
        Farmerlayout = findViewById(R.id.LinearLayout2);
        Banklayout = findViewById(R.id.LinearLayout3);
        Adharimage = findViewById(R.id.Adharimage);
        farmerimage = findViewById(R.id.farmerimage);
        bankimage = findViewById(R.id.bankimage);
        checkbox = findViewById(R.id.chkBox1);
        Submit = findViewById(R.id.btn_onbo_submit);

        Farmer_name = findViewById(R.id.farmer_name);
        Farmer_aadhar = findViewById(R.id.farmer_aadhar);
        Farmer_address = findViewById(R.id.farmer_address);
        Farmer_mob_no = findViewById(R.id.farmer_mobno);
        Farmer_whats_no = findViewById(R.id.farmer_w_no);
        Farmer_alt_no = findViewById(R.id.farmer_alt_mobno);

        contact = findViewById(R.id.chkBox1);
        create_farmer_id();
        mDatabase = FirebaseDatabase.getInstance().getReference("Farmers");
        storageReference = FirebaseStorage.getInstance().getReference();

        checkbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    if (Farmer_mob_no.getText() != null && Farmer_mob_no.length() == 10) {
                        Farmer_whats_no.setText(Farmer_mob_no.getText().toString());
                        Farmer_whats_no.setEnabled(false);
                    }
                    else
                        Farmer_mob_no.setError("Please enter mob no first !");
                } else {
                    Farmer_whats_no.setText("");
                    Farmer_whats_no.setEnabled(true);
                }


            }
        });
        Adharlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                layout = "Adharlayout";
            }
        });
        Farmerlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                layout = "Farmerlayout";
            }
        });
        Banklayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                layout = "Banklayout";
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateDetails(Farmer_aadhar.getText().toString(), Farmer_address.getText().toString(), Farmer_name.getText().toString(),
                        Farmer_mob_no.getText().toString(), Farmer_whats_no.getText().toString(), Farmer_alt_no.getText().toString())) {

                    progressDoalog = new ProgressDialog(OnboardFirst.this);
                    progressDoalog.setMax(120);
                    progressDoalog.setMessage("Loading....");
                    progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDoalog.show();


                    Handler handle = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);

                            progressDoalog.incrementProgressBy(1);

                        }
                    };
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                while (progressDoalog.getProgress() <= progressDoalog
                                        .getMax()) {

                                    Thread.sleep(600);
                                    handle.sendMessage(handle.obtainMessage());
                                    if (progressDoalog.getProgress() == progressDoalog
                                            .getMax()) {
                                        progressDoalog.dismiss();


                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    upload_user_data();

                }
            }


        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();

                if (layout == "Adharlayout" && null != selectedImageUri) {
                    // update the preview image in the layout
                    Adharimage.setImageURI(selectedImageUri);
                    adharUri = selectedImageUri;
                    aadharImgUri = selectedImageUri.getEncodedPath();
                } else if (layout == "Farmerlayout" && null != selectedImageUri) {
                    // update the preview image in the layout
                    farmerimage.setImageURI(selectedImageUri);
                    farmerUri = selectedImageUri;
                    farmerImgUri = selectedImageUri.getEncodedPath();
                } else if (layout == "Banklayout" && null != selectedImageUri) {
                    // update the preview image in the layout
                    bankimage.setImageURI(selectedImageUri);
                    BankUri = selectedImageUri;
                    bnkchkImgUri = selectedImageUri.getEncodedPath();
                }

            }
        }

    }


    private void openGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    public void upload_user_data() {
        Name = Farmer_name.getText().toString();
        Address = Farmer_address.getText().toString();
        Aadhar = Farmer_aadhar.getText().toString();
        Mob_no = Farmer_mob_no.getText().toString();
        Alt_no = Farmer_alt_no.getText().toString();

        AadharImgUri = aadharImgUri;
        FarmerImgUri = farmerImgUri;
        BnkChkImgUri = bnkchkImgUri;

        if (!contact.isChecked()) {
            //Farmer_whats_no.setEnabled(false);
            Whats_no = Farmer_whats_no.getText().toString();
        } else if (!contact.isChecked() && Farmer_whats_no.getText().toString() == "") {
            Toast.makeText(getApplicationContext(), "Please Fill your What's App Number", Toast.LENGTH_SHORT).show();
        }
        if (Aadhar == "" || Name == "" || Address == "" || Mob_no == "") {
            Toast.makeText(getApplicationContext(), "Please Fill All details", Toast.LENGTH_SHORT).show();
        }
        if (adharUri != null && farmerUri != null && BankUri != null) {

            sendAdharUri();
        } else {

            progressDoalog.hide();
            Toast.makeText(OnboardFirst.this, "Please Select All 3 Images", Toast.LENGTH_LONG).show();

        }

    }

    private void sendAdharUri() {
        // Creating second StorageReference.


        StorageReference storageReference1st = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(adharUri));

        // Adding addOnSuccessListener to second StorageReference.
        storageReference1st.putFile(adharUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful()) ;
                        final Uri downloadUrl = urlTask.getResult();

                        Adharpath = downloadUrl.toString();
                        Log.e("Adhar---", Adharpath);
                        sendVBankUri();
                        //  Toast.makeText(getApplicationContext(), "adhar"+Adharpath, Toast.LENGTH_SHORT).show();


                    }
                });

    }

    private void sendVBankUri() {
        // Creating second StorageReference.
        StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(BankUri));

        // Adding addOnSuccessListener to second StorageReference.
        storageReference2nd.putFile(BankUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful()) ;
                        final Uri downloadUrl = urlTask.getResult();

                        Bankpath = downloadUrl.toString();
                        Log.e("Bankpath---", Bankpath);
                        //Toast.makeText(getApplicationContext(), "Bankpath"+Bankpath, Toast.LENGTH_SHORT).show();
                        sendFarmerUri();

                    }
                });
    }

    private void sendFarmerUri() {
        // Creating second StorageReference.
        StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(farmerUri));

        // Adding addOnSuccessListener to second StorageReference.
        storageReference2nd.putFile(adharUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful()) ;
                        final Uri downloadUrl = urlTask.getResult();

                        Farmerpath = downloadUrl.toString();
                        Log.e("Farmerpath---", Farmerpath);


                            if (adharUri != null && farmerUri != null && BankUri != null) {
                                Bundle bundle = new Bundle();
                                bundle.putString("Farmer_Id", Farmer_Id);
                                bundle.putString("Aadhar", Aadhar);
                                bundle.putString("Name", Name);
                                bundle.putString("Address", Address);
                                bundle.putString("Mob_no", Mob_no);
                                bundle.putString("Alt_no", Alt_no);
                                bundle.putString("Whats_no", Whats_no);
                                bundle.putString("Adharpath", Adharpath);
                                bundle.putString("Farmerpath", Farmerpath);
                                bundle.putString("Bankpath", Bankpath);
                                Intent intent = new Intent(OnboardFirst.this, FarmDetails.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }


                        //Toast.makeText(getApplicationContext(), "Farmerpath"+Farmerpath, Toast.LENGTH_SHORT).show();
                        //farmer farmer = new farmer(Farmer_Id,Name,Address,Aadhar,Mob_no,Whats_no,Alt_no,Adharpath,Farmerpath,Bankpath);

                        //mDatabase.push().setValue(farmer);
                        //farmerid = "" + mDatabase.push().getKey();

                    }
                });
    }

    private boolean validateDetails(String aadhar, String address, String name, String mob_no, String whats_no, String alt_no) {
        if (aadhar == null) {
            Farmer_aadhar.setError("It can't be empty !");
            return false;
        } else if (aadhar.length() < 12) {
            Farmer_aadhar.setError("It must be of 12 digits !");
            return false;
        } else if (address == null) {
            Farmer_address.setError("It can't be empty!");
            return false;
        } else if (name == null) {
            Farmer_name.setError("It can't be empty !");
            return false;
        } else if (mob_no == null) {
            Farmer_mob_no.setError("Please enter mobile no first !");
            return false;
        } else if (mob_no.length() != 10) {
            Farmer_mob_no.setError("Mobile no. must be of 10 digits !");
            return false;
        } else if (whats_no == null) {
            Farmer_mob_no.setError("Please enter whatsapp no first !");
            return false;
        } else if (whats_no.length() != 10) {
            Farmer_whats_no.setError("Whatsapp no. must be of 10 digits !");
            return false;
        } else if (alt_no != null && alt_no.length() != 10) {
            Farmer_alt_no.setError("Alternate no. must be of 10 digits !");
            return false;
        }
        return true;
    }

//    private void SendDetails() {
//
//        Bundle bundle = new Bundle();
//
//
//
//        bundle.putString("Farmer_Id",Farmer_Id);
//        bundle.putString("Aadhar",Aadhar);
//        bundle.putString("Name",Name);
//        bundle.putString("Address",Address);
//        bundle.putString("Mob_no",Mob_no);
//        bundle.putString("Alt_no",Alt_no);
//        bundle.putString("Whats_no",Whats_no);
//        bundle.putString("Adharpath",Adharpath);
//        bundle.putString("Farmerpath",Farmerpath);
//        bundle.putString("Bankpath",Bankpath);
//
//        Intent intent = new Intent(this, FarmDetails.class);
//        intent.putExtras(bundle);
//
//// starting the intent
//        startActivity(intent);
//    }


    private void create_farmer_id() {

        int id = Integer.parseInt(Farmer_Id);
        id = id + 1;

        Farmer_Id = String.format("%0" + Farmer_Id.length() + "d", id);
        //Toast.makeText(OnboardFirst.this, ""+Farmer_Id, Toast.LENGTH_SHORT).show();
    }

}