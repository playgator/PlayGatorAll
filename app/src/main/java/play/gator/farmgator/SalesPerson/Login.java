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

import play.gator.farmgator.Dashboard.Dashboard;
import play.gator.farmgator.R;

public class Login extends AppCompatActivity {
    Button login;
    TextView register;
    EditText email_id,pass;
    String email,password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.loginbutton);
        register = findViewById(R.id.login_go);
        mAuth = FirebaseAuth.getInstance();
        email_id = findViewById(R.id.sales_log_email);
        pass = findViewById(R.id.sales_log_pass);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = email_id.getText().toString();
                password = pass.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Login.this, Dashboard.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(Login.this, "Login Failed!!", Toast.LENGTH_SHORT).show();
                            email_id.setText("");
                            pass.setText("");
                        }
                    }
                });



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
}