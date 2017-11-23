package dragonfly.exercisetracker.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import dragonfly.exercisetracker.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent showHomeActivityIntent = new Intent(this, MainActivity.class);
        this.startActivity(showHomeActivityIntent);
    }
}
