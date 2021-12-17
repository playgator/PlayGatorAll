package play.gator.farmgator.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import play.gator.farmgator.BookOrder.BookOrder;
import play.gator.farmgator.FireStoreClass;
import play.gator.farmgator.Onboard.OnboardFirst;
import play.gator.farmgator.R;
import play.gator.farmgator.SalesPerson.Registration;
import play.gator.farmgator.SalesPerson.user;
import play.gator.farmgator.ShowOrder.ShowOrder;
import play.gator.farmgator.SplashScreen.Splash;
import play.gator.farmgator.UserSession.UserSession;

public class Dashboard extends AppCompatActivity {

    RelativeLayout Onboard;
    UserSession session;
    TextView userName;
    RelativeLayout bookorder;
    RelativeLayout vieworder;
    public user userDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Onboard=findViewById(R.id.Onboard);
        bookorder=findViewById(R.id.bookorder);
        vieworder=findViewById(R.id.ViewOrder);
        userName = findViewById(R.id.dash_username);
        session = new UserSession(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new FireStoreClass().getSalesManDetails(this);


        Onboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, OnboardFirst.class);
                startActivity(i);

            }
        });
        bookorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, BookOrder.class);
                startActivity(i);

            }
        });

        vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, ShowOrder.class);
                startActivity(i);

            }
        });
    }
    public void getUserDetails(user user){
        userDetails = user;
        userName.setText(user.getName());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logout:
                session.logoutUser();
                Toast.makeText(getApplicationContext(),"Logged Out Successfully.",Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
