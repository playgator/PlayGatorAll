package play.gator.farmgator.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import play.gator.farmgator.Onboard.OnboardFirst;
import play.gator.farmgator.R;
import play.gator.farmgator.SalesPerson.Registration;
import play.gator.farmgator.SplashScreen.Splash;

public class Dashboard extends AppCompatActivity {

    RelativeLayout Onboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Onboard=findViewById(R.id.Onboard);

        Onboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, OnboardFirst.class);
                startActivity(i);

            }
        });
    }
}