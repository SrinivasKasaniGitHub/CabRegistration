package info.srinivas.com.driverregistration;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Dashboard extends AppCompatActivity {

    ImageView slot_booking, slot_edit, slot_pending, slot_status, image_slider, logout;

    public static String IMEI;
    GPSTracker gps;
    public static double lattitude_value = 0.0, longitude_value = 0.0;

    public static String check_Slot_or_Edit = "";
    boolean doubleBackToExitPressedOnce = false;

    public int currentimageindex = 0;

    private int[] IMAGE_IDS = {
            R.drawable.img_one, R.drawable.img_two, R.drawable.img_three, R.drawable.img_four, R.drawable.img_five,
            R.drawable.img_six, R.drawable.img_eight, R.drawable.img_nine, R.drawable.img_ten};


    private static final String[] requiredPermissions = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private final int REQUEST_CONTACT = 111;

    private static final int REQUEST_APP_SETTINGS = 168;

    TextView dome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        final Handler mHandler = new Handler();
        /**************Adding Shortcut of Application**************/
       /* SharedPreferences prefs = getSharedPreferences("ShortCutPrefs", MODE_PRIVATE);
        if (!prefs.getBoolean("isFirstTime", false)) {
           // addShortcut();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirstTime", true);
            editor.commit();
        }*/
        /**************Adding Shortcut of Application**************/

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA},
                    REQUEST_CONTACT);
        } else {
            gps = new GPSTracker(Dashboard.this);
            if (gps.canGetLocation()) {
                lattitude_value = gps.getLatitude();
                longitude_value = gps.getLongitude();
            }
            try {
                TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                IMEI = telephonyManager.getDeviceId();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        } else {
            showGPSDisabledAlertToUser();
        }

        slot_booking = (ImageView) findViewById(R.id.slot_booking);
        slot_edit = (ImageView) findViewById(R.id.slot_edit);
        logout = (ImageView) findViewById(R.id.btn_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        slot_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_Slot_or_Edit = "Book";
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                            new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CONTACT);
                } else {
                    gps = new GPSTracker(Dashboard.this);
                    if (gps.canGetLocation()) {
                        lattitude_value = gps.getLatitude();
                        longitude_value = gps.getLongitude();
                    }

                    try {
                        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling

                            return;
                        }
                        IMEI = telephonyManager.getDeviceId();

                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    Intent i = new Intent(Dashboard.this, SlotBooking.class);
                    startActivity(i);
                }
            }
        });

        slot_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Dashboard.this, Slot_Edit_Activity.class);
                startActivity(i);
            }
        });

        slot_pending = (ImageView) findViewById(R.id.bt_pending);
        slot_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pending = new Intent(getApplicationContext(), Pendding_ChallanActivity.class);
                startActivity(pending);
            }
        });
        slot_status = (ImageView) findViewById(R.id.btn_status);
        slot_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent status = new Intent(getApplicationContext(), ApplicationStatus_Activity.class);
                startActivity(status);
            }
        });

        // Create runnable for posting
        final Runnable mUpdateResults = new Runnable() {
            public void run() {
                AnimateandSlideShow();
            }
        };
        int delay = 3000; // delay for 1 sec.
        int period = 5000; // repeat every 4 sec.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                mHandler.post(mUpdateResults);
            }

        }, delay, period);
    }

    /**
     * Helper method to start the animation on the splash screen
     */
    private void AnimateandSlideShow() {
        image_slider = (ImageView) findViewById(R.id.image_slider);
        image_slider.setImageResource(IMAGE_IDS[currentimageindex % IMAGE_IDS.length]);
        currentimageindex++;
        Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        image_slider.startAnimation(rotateimage);
    }

    public boolean hasPermissions(String... permissions) {
        for (String permission : permissions)
            if (PackageManager.PERMISSION_GRANTED != checkCallingOrSelfPermission(permission))
                return false;
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        gps = new GPSTracker(Dashboard.this);
        // getLocation();
        if (gps.canGetLocation()) {
            lattitude_value = gps.getLatitude();
            longitude_value = gps.getLongitude();
        }

    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(" GPS is Disabled in your Device Please Enable GPS?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_APP_SETTINGS) {
            if (hasPermissions(requiredPermissions)) {
                showToast("All permissions granted!");
            } else {
                showToast("Permissions not granted!");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressWarnings("unused")
    private void showToast(String msg) {
        // TODO Auto-generated method stub
        Toast toast = Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        View toastView = toast.getView();

        ViewGroup group = (ViewGroup) toast.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setPadding(25, 0, 25, 0);
        // messageTextView.setTextSize(14);

        //toastView.setBackgroundResource(R.drawable.toast_background);
        toast.show();
    }

    public void AlertLogout() {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to Logout?")
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                       /* Intent CloseInt = new Intent(getApplicationContext(), Dashboard.class);
                        CloseInt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        CloseInt.putExtra("CloseApp", true);
                        startActivity(CloseInt);
                        System.exit(0);*/


                        finish();
                    }


                }).create().show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // showToast("Please Click on Logout Button");
        finish();
    }

}
