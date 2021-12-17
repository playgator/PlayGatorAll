package play.gator.farmgator.SalesPerson;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import play.gator.farmgator.Dashboard.Dashboard;
import play.gator.farmgator.R;
import play.gator.farmgator.UserSession.UserSession;

public class Login extends AppCompatActivity {
    Button login;
    TextView register;
    EditText email_id,pass;
    String email,password;
    private FirebaseAuth mAuth;
    UserSession session;
    String Name;
    CircularProgressBar circularProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.loginbutton);
        register = findViewById(R.id.login_go);
        mAuth = FirebaseAuth.getInstance();
        email_id = findViewById(R.id.sales_log_email);
        pass = findViewById(R.id.sales_log_pass);
        session = new UserSession(getApplicationContext());

        circularProgressBar = (CircularProgressBar)findViewById(R.id.CircularProgressbar);
        circularProgressBar.setVisibility(View.VISIBLE);
        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.brown));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.activity_horizontal_margin));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.activity_vertical_margin));

        circularProgressBar.setVisibility(View.GONE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = email_id.getText().toString().trim();
                password = pass.getText().toString().trim();
                if(validateEmailAndPass(email,password)){
                    Log.e("adhar---", email);
                    Log.e("name---", password);
                    if(email.matches("")||password.matches(""))
                    {
                        Toast.makeText(Login.this, "Please Fill All the fields", Toast.LENGTH_SHORT).show();

                    }
                    else{

                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){



                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("Sales_person");
                                    Query queryRef = myRef.orderByChild("email").equalTo(email);

                                    queryRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                                                // TODO: handle the post
                                                user value = userSnapshot.getValue(user.class);
                                                Log.e("adhar---", value.getAadhar());
                                                Log.e("name---", value.getName());
                                                Log.e("email---", value.getEmail());
                                                Log.e("mobile---", value.getMob_no());

                                                Name=value.getName();
                                                session.createUserLoginSession(value.getName(),
                                                        value.getEmail(),value.getMob_no(),value.getAadhar());
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            // Getting Post failed, log a message
                                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                                            // ...
                                        }
                                    });
                                    Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(Login.this, Dashboard.class);
                                    i.putExtra(Intent.EXTRA_TEXT, Name);
                                    startActivity(i);
                                    finish();
                                }

                                else{
                                    Toast.makeText(Login.this, "You Entered Wrong Email or Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }

                }


            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Registration.class);
                startActivity(i);
            }
        });
    }
    public boolean validateEmailAndPass(String email, String password){
        if(!email.toLowerCase().contains("@")){
            email_id.setError("Email must contain '@', Please Enter valid Id !");
            return false;
        }else if (password.length() < 6 || password.length() > 12) {
            pass.setError("Min password length should be six or Max 12 !");
            return false;
        }
        return true;
    }
}