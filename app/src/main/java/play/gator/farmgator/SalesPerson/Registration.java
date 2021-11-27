package play.gator.farmgator.SalesPerson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import play.gator.farmgator.R;
import play.gator.farmgator.SplashScreen.Splash;

public class Registration extends AppCompatActivity {

    EditText name,mob_no,email_id,aadhar,pass,con_pass;
    Button signup;
    TextView login;
    String email,password,conf_pass;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.sales_reg_name);
        mob_no = findViewById(R.id.sales_reg_mob_no);
        email_id = findViewById(R.id.sales_reg_email);
        aadhar = findViewById(R.id.sales_reg_aadhar);
        pass = findViewById(R.id.sales_reg_pass);
        con_pass = findViewById(R.id.sales_reg_con_pass);
        signup = findViewById(R.id.sales_signup_btn);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Sales_person");


        login=findViewById(R.id.login_go);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = email_id.getText().toString();
                password = pass.getText().toString();
                conf_pass = con_pass.getText().toString();


                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("tag","User Created Successfully");
                            Toast.makeText(Registration.this, "User Successfully Registered", Toast.LENGTH_SHORT).show();
                            upload_user_data();
                            Intent intent = new Intent(Registration.this,Login.class);
                            startActivity(intent);
                        }
                        else{
                            Log.d("tag","User Creation failed");
                            Toast.makeText(Registration.this, "User Registration Failed!!", Toast.LENGTH_SHORT).show();
                            name.setText("");
                            mob_no.setText("");
                            email_id.setText("");
                            aadhar.setText("");
                            pass.setText("");
                            con_pass.setText("");
                        }
                    }
                });
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Registration.this, Login.class);
                startActivity(i);

            }
        });
    }

    public void upload_user_data(){
        String Name = name.getText().toString();
        String Email = email_id.getText().toString();
        String Aadhar = aadhar.getText().toString();
        String Pass = pass.getText().toString();
        String Mob_no = mob_no.getText().toString();

        user user = new user(Name,Email,Aadhar,Pass,Mob_no);

        mDatabase.push().setValue(user);
        Toast.makeText(Registration.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();

    }
}