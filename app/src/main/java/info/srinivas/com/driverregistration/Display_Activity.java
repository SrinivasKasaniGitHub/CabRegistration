package info.srinivas.com.driverregistration;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Display_Activity extends AppCompatActivity {


    TextView txt_dl, appid, slotdt, txt_hind, txt_name;
    Button ok;
    ImageView img_get;
    private LinearLayout rootContent;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_);

        txt_dl = (TextView) findViewById(R.id.txt_dl);
        appid = (TextView) findViewById(R.id.txt_appid);
        slotdt = (TextView) findViewById(R.id.txt_slotdt);
        txt_hind = (TextView) findViewById(R.id.txt_hind);
        txt_name = (TextView) findViewById(R.id.txt_name);
        ok = (Button) findViewById(R.id.bt_ok);
        img_get = (ImageView) findViewById(R.id.img_get);

        if (ServiceHelper.final_response_data.equals("") &&
                ServiceHelper.final_response_data.equals("NA") &&
                ServiceHelper.final_response_data.equals("anyTypse{}")) {

            Toast.makeText(getApplicationContext(), "No Data Not Found", Toast.LENGTH_LONG).show();
        } else {

            txt_dl.setText( ServiceHelper.displayres_master[0].toString());
            appid.setText(ServiceHelper.displayres_master[1]);
            slotdt.setText(ServiceHelper.displayres_master[2]);
            txt_name.setText( SlotBooking.driver_name.getText().toString().trim());
        }


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iback = new Intent(getApplicationContext(), Dashboard.class);
                iback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(iback);
                showToast("Registration Successful");
                //Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();

            }
        });

    }


    private void showToast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        View toastView = toast.getView();
        ViewGroup group = (ViewGroup) toast.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setPadding(25,0,25,0);
        toastView.setBackgroundResource(R.drawable.toast_background);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent iback = new Intent(getApplicationContext(), Dashboard.class);
        iback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(iback);
    }
}
