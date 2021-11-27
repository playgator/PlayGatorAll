package play.gator.farmgator.Onboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import play.gator.farmgator.R;
import play.gator.farmgator.SalesPerson.Registration;
import play.gator.farmgator.SplashScreen.Splash;

public class OnboardFirst extends AppCompatActivity  {


    LinearLayout Adharlayout,Farmerlayout,Banklayout;
    private static final int SELECT_PICTURE  = 100;
    Uri imageUri;
    ImageView Adharimage,farmerimage,bankimage;
    String layout;
    Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard_first);

        Adharlayout=findViewById(R.id.LinearLayout1);
        Farmerlayout=findViewById(R.id.LinearLayout2);
        Banklayout=findViewById(R.id.LinearLayout3);
        Adharimage=findViewById(R.id.Adharimage);
        farmerimage=findViewById(R.id.farmerimage);
        bankimage=findViewById(R.id.bankimage);
        Submit=findViewById(R.id.btn_onbo_submit);

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
                layout="Banklayout";
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OnboardFirst.this, FarmDetails.class);
                startActivity(i);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();

                if (layout=="Adharlayout"&& null != selectedImageUri) {
                    // update the preview image in the layout
                    Adharimage.setImageURI(selectedImageUri);
                }
               else if (layout=="Farmerlayout"&& null != selectedImageUri) {
                    // update the preview image in the layout
                    farmerimage.setImageURI(selectedImageUri);
                }
               else if (layout=="Banklayout" && null != selectedImageUri) {
                    // update the preview image in the layout
                    bankimage.setImageURI(selectedImageUri);
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
}