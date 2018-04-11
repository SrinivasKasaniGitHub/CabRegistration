package info.srinivas.com.driverregistration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                Intent i = new Intent(SplashScreen.this, Dashboard.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    /**************Adding Shortcut of Application**************/
    SharedPreferences prefs = getSharedPreferences("ShortCutPrefs", MODE_PRIVATE);
	    if(!prefs.getBoolean("isFirstTime", false)){
        Log.i("**addShortcut------>", "**********addShortcut*******");
       // addShortcut();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isFirstTime", true);
        editor.commit();
    }
    /**************Adding Shortcut of Application**************/
    }


}
