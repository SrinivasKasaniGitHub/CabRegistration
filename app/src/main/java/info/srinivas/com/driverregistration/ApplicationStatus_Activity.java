package info.srinivas.com.driverregistration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ApplicationStatus_Activity extends AppCompatActivity {
    RadioGroup rg_edityesno ;
    RadioButton rd_editDl ,rd_editappID ;

    EditText edit_dl_number ;

    Button get_details ;
    LinearLayout applicationStatusCard , ll_Sticker_regNo ,ll_Sticker_valid ;
    public static String radio_btn_edit = "";

    TextView dlno,driverName,driverFname,AadharNo,dob,contactNo,contactNo1,email,aadress,city,stateName,pidcode,centerName,slotdt,
            addressCd,addressDet,stickerStatusCd,RegnNo,StickerValidity , tv_lcnce_Sticker_regNo;

    ImageView driverImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_application_status_);


        rg_edityesno=(RadioGroup)findViewById(R.id.rg_edityesno);
        rd_editDl =(RadioButton)findViewById(R.id.rd_editDl);
        rd_editappID =(RadioButton)findViewById(R.id.rd_editappID);

        edit_dl_number=(EditText)findViewById(R.id.edit_dl_number);
        get_details = (Button)findViewById(R.id.get_details_btn);

        applicationStatusCard=(LinearLayout)findViewById(R.id.rl_licences_rtadetails_xml);
        ll_Sticker_regNo=(LinearLayout)findViewById(R.id.ll_Sticker_regNo);
        ll_Sticker_valid=(LinearLayout)findViewById(R.id.ll_Sticker_valid);
        driverImg=(ImageView)findViewById(R.id.imgv_licence_spotchallan_xml);
        dlno=(TextView) findViewById(R.id.dl_no_spotchallan_xml);


        driverName=(TextView) findViewById(R.id.tvlcnceownername_spotchallan_xml);
        driverFname=(TextView) findViewById(R.id.tvlcnce_fname_spotchallan_xml);
        AadharNo=(TextView) findViewById(R.id.tvaadhra_spotchallan_xml);
        dob=(TextView) findViewById(R.id.tvdob_spotchallan_xml);
        contactNo=(TextView) findViewById(R.id.tvcontactNo_spotchallan_xml);
        contactNo1=(TextView) findViewById(R.id.tvcontactNo1_spotchallan_xml);
        email=(TextView) findViewById(R.id.tvmail_spotchallan_xml);
        aadress=(TextView) findViewById(R.id.tv_lcnce_Address_spotchallan_xml);
        city=(TextView) findViewById(R.id.tv_lcnce_city_spotchallan_xml);
        stateName=(TextView) findViewById(R.id.tv_lcnce_statesname_spotchallan_xml);
        pidcode=(TextView) findViewById(R.id.tv_lcnce_pincode_spotchallan_xml);
        centerName=(TextView) findViewById(R.id.tv_lcnce_centerName_spotchallan_xml);
        slotdt=(TextView) findViewById(R.id.tv_lcnce_slotdt_spotchallan_xml);
        addressCd=(TextView) findViewById(R.id.tv_lcnce_addressCd_spotchallan_xml);
        addressDet=(TextView) findViewById(R.id.tv_lcnce_addressDet_spotchallan_xml);
       // StickerStus=(TextView) findViewById(R.id.tv_lcnce_StickerStus_spotchallan_xml);
        RegnNo=(TextView) findViewById(R.id.tv_lcnce_RegnNo_spotchallan_xml);
        StickerValidity=(TextView) findViewById(R.id.tv_lcnce_StickerValidity_spotchallan_xml);
        tv_lcnce_Sticker_regNo=(TextView) findViewById(R.id.tv_lcnce_Sticker_regNo);

        rd_editDl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_btn_edit = "1";
                edit_dl_number.setHint("Enter Drivring License ");
                edit_dl_number.setText("");
            }
        });
        rd_editappID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_btn_edit = "0";
                edit_dl_number.setHint("Enter Application No");
                edit_dl_number.setText("");
            }
        });


        get_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    if ( (!rd_editDl.isChecked()) && (!rd_editappID.isChecked()) ){
                        showToast("Please Select Radio buttons");
                    }else if (edit_dl_number.getText().toString().equals("")){
                        edit_dl_number.setError("Please Enter Drivring License No");
                        edit_dl_number.requestFocus();
                    }else {
                        Asyn_ApplicationStatus appStatus= new Asyn_ApplicationStatus();
                        appStatus.execute();
                      //  applicationStatusCard.setVisibility(View.VISIBLE);
                    }
                }else{
                    showToast("Please Check Your Internet Connection");
                }
            }
        });

    }
    public class Asyn_ApplicationStatus extends AsyncTask<Void, Void , String> {
        ProgressDialog dialog = new ProgressDialog(ApplicationStatus_Activity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...!");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {

             ServiceHelper.getApplicationStatus( radio_btn_edit.trim(),edit_dl_number.getText().toString().trim() );

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();

            if (ServiceHelper.final_response_Status.equals("") && ServiceHelper.final_response_Status.equals("NA") &&
                    ServiceHelper.final_response_Status.equals("anyType{}")){
                showToast("No Data Found");
                applicationStatusCard.setVisibility(View.GONE);
            }else {
                try {

                applicationStatusCard.setVisibility(View.VISIBLE);
                driverName.setText(ServiceHelper.status_Master[1]);
                driverFname.setText(ServiceHelper.status_Master[2]);
                AadharNo.setText(ServiceHelper.status_Master[3]);
                dob.setText(ServiceHelper.status_Master[4]);
                contactNo.setText(ServiceHelper.status_Master[5]);
                contactNo1.setText(ServiceHelper.status_Master[6]);
                email.setText(ServiceHelper.status_Master[7]);
                aadress.setText(ServiceHelper.status_Master[8]);
                city.setText(ServiceHelper.status_Master[9]);
                stateName.setText(ServiceHelper.status_Master[10]);
                pidcode.setText(ServiceHelper.status_Master[11]);
                dlno.setText(ServiceHelper.status_Master[12]);
                centerName.setText(ServiceHelper.status_Master[13]);
                slotdt.setText(ServiceHelper.status_Master[14]);
                addressCd.setText(ServiceHelper.status_Master[15]);
                addressDet.setText(ServiceHelper.status_Master[16]);
                //StickerStus.setText(ServiceHelper.status_Master[17]);////

                    if (ServiceHelper.status_Master[17].equalsIgnoreCase("0")){

                        ll_Sticker_valid.setVisibility(View.GONE);
                        ll_Sticker_regNo.setVisibility(View.GONE);
                    }else {
                        ll_Sticker_valid.setVisibility(View.VISIBLE);
                        ll_Sticker_regNo.setVisibility(View.VISIBLE);
                        RegnNo.setText(ServiceHelper.status_Master[18]);
                        StickerValidity.setText(ServiceHelper.status_Master[19]);
                    }




                if (null != ServiceHelper.status_Master[21] && ServiceHelper.status_Master[22].equals("0")) {
                    driverImg.setImageResource(R.drawable.photo_logo);
                } else {
                    try {
                        byte[] decodestring = Base64.decode(ServiceHelper.status_Master[22],Base64.DEFAULT);
                        Bitmap decocebyte = BitmapFactory.decodeByteArray(decodestring, 0, decodestring.length);
                        driverImg.setImageBitmap(decocebyte);
                    } catch (Exception e) {
                        e.printStackTrace();
                        driverImg.setImageDrawable(getResources().getDrawable(R.drawable.photo_logo));
                    }
                }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
        Intent iback = new Intent(getApplicationContext(),Dashboard.class);
        iback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(iback);
    }
}
