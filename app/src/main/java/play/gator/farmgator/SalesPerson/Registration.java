package play.gator.farmgator.SalesPerson;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;

import play.gator.farmgator.FireStoreClass;
import play.gator.farmgator.R;
import play.gator.farmgator.UserSession.UserSession;

public class Registration extends AppCompatActivity {

    EditText name, mob_no, email_id, aadhar, pass, con_pass;
    Button signup;
    TextView login;
    String email, password, conf_pass, farmername, mobile, aadharnumber;
    UserSession session;
    CircularProgressBar circularProgressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        session = new UserSession(getApplicationContext());

        name = findViewById(R.id.sales_reg_name);
        mob_no = findViewById(R.id.sales_reg_mob_no);
        email_id = findViewById(R.id.sales_reg_email);
        aadhar = findViewById(R.id.sales_reg_aadhar);
        pass = findViewById(R.id.sales_reg_pass);
        //  con_pass = findViewById(R.id.sales_reg_con_pass);
        signup = findViewById(R.id.sales_signup_btn);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Sales_person");


        if (session.checkLogin())
            finish();
        login = findViewById(R.id.login_go);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                farmername = name.getText().toString();
                mobile = mob_no.getText().toString();
                email = email_id.getText().toString();
                aadharnumber = aadhar.getText().toString();
                password = pass.getText().toString();
                //  conf_pass = con_pass.getText().toString();
                if (validateUserDetails(aadharnumber, mobile, password, email)) {
                    if (farmername.equals("") || mobile.equals("") || email.equals("") || aadharnumber.equals("") || password.equals("")) {
                        Toast.makeText(Registration.this, "Please Fill All the fields", Toast.LENGTH_SHORT).show();

                    } else {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("tag", "User Created Successfully");
                                    circularProgressBar = (CircularProgressBar) findViewById(R.id.CircularProgressbar);
                                    circularProgressBar.setVisibility(View.VISIBLE);
                                    circularProgressBar.setColor(ContextCompat.getColor(Registration.this, R.color.brown));
                                    circularProgressBar.setBackgroundColor(ContextCompat.getColor(Registration.this, R.color.orange));
                                    circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.activity_horizontal_margin));
                                    circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.activity_vertical_margin));
                                    int animationDuration = 2500;
                                    circularProgressBar.setProgressWithAnimation(100, animationDuration);

                                    circularProgressBar.setVisibility(View.GONE);

//                                session.createUserLoginSession(farmername,
//                                        email,mobile,aadharnumber);
                                    user user = new user(name.getText().toString(), email_id.getText().toString(), aadhar.getText().toString(), pass.getText().toString(), mob_no.getText().toString());
                                    user.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    user.setFarmerList(new ArrayList<>());
                                    new FireStoreClass().registerSalesPerson(Registration.this, user);
                                    upload_user_data();
                                    Intent intent = new Intent(Registration.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                    Log.d("tag", "User Creation failed");
                                    Log.e("errorCode", errorCode);
                                    Toast.makeText(Registration.this, "User Registration Failed!!", Toast.LENGTH_SHORT).show();
//                                name.setText("");
//                                mob_no.setText("");
//                                email_id.setText("");
//                                aadhar.setText("");
//                                pass.setText("");
//                                con_pass.setText("");
                                }
                            }
                        });
                    }
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Registration.this, Login.class);
                startActivity(i);
                finish();

            }
        });
    }

    public void registerSalesPersonSuccess() {
        Log.d("tag", "User Created Successfully");
        //Toast.makeText(Registration.this, "User Successfully Registered", Toast.LENGTH_SHORT).show();
    }

    public void upload_user_data() {
        String Name = name.getText().toString();
        String Email = email_id.getText().toString();
        String Aadhar = aadhar.getText().toString();
        String Pass = pass.getText().toString();
        String Mob_no = mob_no.getText().toString();

        user user = new user(Name, Email, Aadhar, Pass, Mob_no);

        mDatabase.push().setValue(user);
        Toast.makeText(Registration.this, "User Successfully Registered", Toast.LENGTH_SHORT).show();

    }

    private boolean validateUserDetails(String aadharNumber, String phoneNumber, String password, String emailId) {
        if (password.length() < 6 || password.length() > 12) {
            pass.setError("Min password length should be six or Max 12 !");
            return false;
        } else if (aadharNumber.length() < 12) {
            aadhar.setError("Aadhar number should be of 12 digits !");
            return false;
        } else if (phoneNumber.length() < 10) {
            mob_no.setError("Phone number must be of 10 digits !");
            return false;
        } else if (!emailId.toLowerCase().contains("@")) {
            email_id.setError("Email must contain '@', Please Enter valid Id !");
            return false;
        }
        return true;
    }
}