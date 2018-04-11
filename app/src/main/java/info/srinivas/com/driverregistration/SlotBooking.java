package info.srinivas.com.driverregistration;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

import static android.app.PendingIntent.getActivity;

public class SlotBooking extends AppCompatActivity implements View.OnClickListener {

    public static Button get_datails, dob_btn, select_state, select_booking_center, select_slot_booking_date, select_booking_time, select_addressproof_type, cancel_btn, submit_btn; //licence_validity ,  select_idproof_type
    TextView response_test;

    public static EditText dl_number, driver_name, driver_father_name, driver_address, driver_door_no, driver_street_no, driver_location, select_city, driver_pincode, driver_aadhra, driver_mobile_no_one, driver_mobile_no_two, driver_email_id, address_proof_details;//id_proof_details
    LinearLayout ll_min, driver_address_get, driver_address_layout, ll_secondLayout;
    ImageView driver_image, address_proof_image, other_doc_image_one, other_doc_image_two; //id_proof_image

    //public static  EditText edit_dl_no ,edit_app_ID ;

    //static Button getappid ;
    public static String dob_DL = null, slotbooking = null;
    private int mYear, mMonth, mDay;
    Calendar cal;
    int present_year;
    int present_month;
    int present_day;
    SimpleDateFormat format;

    RadioGroup rd_yesno;
    RadioButton rd_yes, rd_no;

    CountDownTimer newtimer;


    ActionBar actionBar;
    public static final int TIME_DIALOG_ID = 0;
    final int PROGRESS_DIALOG = 2;

    final int PRESENT_DATE_PICKER = 1;
    public static String[] licence_details_master, address_splite;
    // public static  String[] display_master ;

    String dobcheck = "No";

    public static String img_photo = "NA";
    public static String img_add_proof = "NA";


    public static boolean fromFLG = false, toFLG = false, bookFLG = false;
    public static String image_FLG = "0", imageadd_FLG = "0";


    private static String NAMESPACE = "http://service.et.mtpv.com";
    private static String getBookingCenter = "getCenterMaster";
    private static String getSelect_state = "getStateMaster";
    //private static String getSelect_BookingSlot = "getCenterMaster()";//Ps Name
    private static String getSelect_Address_proof = "getAddressProofMaster";
    private static String getSelect_IDProof = "getIdProofMaster";

    public static String URL = "http://www.echallan.info/DriverRegnService/services/MobileDriverRegnServiceImp?wsdl";

    private static String SelPicId = "1";
    public static String imgString = "NA";

    private int mHour, mMinute;
    static String Opdata_State, Opdata_bookingSlot, Opdata_SelectaddressProof, Opdata_Select_IDProof;
    public static String[] psNames_master, psNameList;
    public static String psmater_resp = null;
    String[][] psname_name_code_arr;

    ArrayList<String> ps_names = null;
    HashMap<String, String> psMap = null;

    ArrayList<String> state_names = null;
    HashMap<String, String> stateMap = null;

    ArrayList<String> addproof = null;
    HashMap<String, String> proofMap;

    ArrayList<String> Idproof = null;
    HashMap<String, String> idproofMap;

    public static String selected_ps_code = "", selected_state_code = "", Selected_addressproof = "", Selected_idproof = "";


    public static byte[] photo_ImageByteArray = null;
    public static byte[] proof_Image_ByteArray = null;

    public static int selected_pointby_psname = -1;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;

    public static String radio_btn_value;
    boolean doubleBackToExitPressedOnce = false;

    VerhoeffCheckDigit ver = new VerhoeffCheckDigit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_slot_booking);
        ll_secondLayout = (LinearLayout) findViewById(R.id.ll_secondlayout);
        ll_min = (LinearLayout) findViewById(R.id.ll_main_layout);
        driver_address_layout = (LinearLayout) findViewById(R.id.driver_address_layout);
        driver_address_get = (LinearLayout) findViewById(R.id.driver_address_get);
        //app_edit_llayout = (LinearLayout)findViewById(R.id.app_edit_llayout);

        dl_number = (EditText) findViewById(R.id.dl_number);
        driver_name = (EditText) findViewById(R.id.driver_name);
        driver_father_name = (EditText) findViewById(R.id.driver_father_name);
        driver_address = (EditText) findViewById(R.id.driver_address);
        driver_door_no = (EditText) findViewById(R.id.driver_door_no);
        driver_street_no = (EditText) findViewById(R.id.driver_street_no);
        driver_location = (EditText) findViewById(R.id.driver_location);
        select_city = (EditText) findViewById(R.id.select_city_btn);
        driver_pincode = (EditText) findViewById(R.id.driver_pincode);
        driver_aadhra = (EditText) findViewById(R.id.driver_aadhra);
        driver_mobile_no_one = (EditText) findViewById(R.id.driver_mobile_no_one);
        driver_mobile_no_two = (EditText) findViewById(R.id.driver_mobile_no_two);
        driver_email_id = (EditText) findViewById(R.id.driver_email_id);
        address_proof_details = (EditText) findViewById(R.id.address_proof_details);
        //  id_proof_details = (EditText)findViewById(R.id.id_proof_details);

   /* ///Edit Screen Ui Compound
        edit_dl_no = (EditText)findViewById(R.id.edit_dl_no);
        edit_app_ID = (EditText)findViewById(R.id.edit_app_ID);

        getappid = (Button)findViewById(R.id.bt_getappid);*/

        rd_yesno = (RadioGroup) findViewById(R.id.rg_yesno);
        rd_yes = (RadioButton) findViewById(R.id.rd_yes);
        rd_no = (RadioButton) findViewById(R.id.rd_no);

        get_datails = (Button) findViewById(R.id.get_details_btn);
        // licence_validity = (Button)findViewById(R.id.licence_validity);

        select_state = (Button) findViewById(R.id.select_state);
        select_booking_center = (Button) findViewById(R.id.select_booking_center);
        select_booking_time = (Button) findViewById(R.id.select_booking_time);
        //select_idproof_type =  (Button)findViewById(R.id.select_idproof_type);
        select_addressproof_type = (Button) findViewById(R.id.select_addressproof_type);

        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        submit_btn = (Button) findViewById(R.id.submit_btn);
        dob_btn = (Button) findViewById(R.id.dob_btn);
        select_slot_booking_date = (Button) findViewById(R.id.select_booking_date);

        driver_image = (ImageView) findViewById(R.id.driver_image);
        address_proof_image = (ImageView) findViewById(R.id.address_proof_image);
        //id_proof_image = (ImageView) findViewById(R.id.id_proof_image);
        /*other_doc_image_one = (ImageView) findViewById(R.id.other_doc_image_one);
        other_doc_image_two= (ImageView) findViewById(R.id.other_doc_image_two);
*/
        ll_min.setVisibility(View.GONE);
        select_booking_time.setVisibility(View.GONE);

        driver_aadhra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (driver_aadhra.getText().toString().trim().length() > 11) {
                    if (!ver.isValid(driver_aadhra.getText().toString().trim())) {
                        showToast("Please Enter Valid Aadhaar Number");
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        dob_btn.setOnClickListener(this);
        get_datails.setOnClickListener(this);
        //licence_validity.setOnClickListener(this);
        select_city.setOnClickListener(this);
        select_state.setOnClickListener(this);
        select_booking_center.setOnClickListener(this);
        select_slot_booking_date.setOnClickListener(this);
        select_booking_time.setOnClickListener(this);
        select_addressproof_type.setOnClickListener(this);
        //select_idproof_type.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
        submit_btn.setOnClickListener(this);


        driver_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlotBooking.SelPicId = "1";
                selectImage();
            }
        });


        rd_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rd_yes.isChecked()) {
                    // ll_secondLayout.setVisibility(View.VISIBLE);
                    driver_address_get.setVisibility(View.VISIBLE);
                    driver_address_layout.setVisibility(View.GONE);

                } else {
                    driver_address_get.setVisibility(View.GONE);
                    driver_address_layout.setVisibility(View.VISIBLE);
                }
            }
        });
        rd_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rd_no.isChecked()) {
                    //  ll_secondLayout.setVisibility(View.GONE);
                    driver_door_no.setText("");
                    driver_street_no.setText("");
                    driver_location.setText("");
                    driver_address_get.setVisibility(View.GONE);
                    driver_address_layout.setVisibility(View.VISIBLE);
                } else {
                    driver_address_get.setVisibility(View.VISIBLE);
                    driver_address_layout.setVisibility(View.GONE);
                }
            }
        });

        address_proof_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlotBooking.SelPicId = "2";
                selectImage();
            }
        });

        /*id_proof_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlotBooking.SelPicId = "3";
                selectImage();
            }
        });*/

        /*other_doc_image_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlotBooking.SelPicId = "4";
                selectImage();
            }

        });*/

        /*other_doc_image_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlotBooking.SelPicId = "5";
                selectImage();
            }

        });*/


    }

    public class editSlotBookingValues extends AsyncTask<Void, Void, String> {
        ProgressDialog dialog = new ProgressDialog(SlotBooking.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...!");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {

            //  ServiceHelper.getEditDriverDetails(edit_dl_no.getText().toString().trim() , edit_app_ID.getText().toString().trim());

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();

            if (ServiceHelper.final_response_Edit.equals("") && ServiceHelper.final_response_Edit.equals("NA") && ServiceHelper.final_response_Edit.equals("anyTypse{}")) {
                showToast("No Data Found");
            } else if (!ServiceHelper.final_response_Edit.equals("")) {
                ll_min.setVisibility(View.VISIBLE);
            } else {
                //set Text to request filedes

               /* dl_number.setText(""+);
                driver_name.setText(""+);
                driver_father_name.setText(""+);
                driver_address.setText(""+);
                driver_door_no.setText(""+);
                driver_street_no.setText(""+);
                driver_location.setText(""+);
                select_city.setText(""+);
                driver_pincode.setText(""+);
                driver_aadhra.setText(""+);
                driver_mobile_no_one.setText(""+);
                driver_mobile_no_two.setText(""+);
                driver_email_id.setText(""+);
                address_proof_details.setText(""+);*/
            }
        }
    }

    //1
    protected void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(SlotBooking.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(SlotBooking.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);

        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        try {


            if ("1".equals(SlotBooking.SelPicId)) {
                driver_image.setImageBitmap(thumbnail);            //display.setRotation(90);
                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 60, stream1);
                photo_ImageByteArray = stream1.toByteArray();
                img_photo = getBase64EncodedString(photo_ImageByteArray);
                image_FLG = "1";
            } else if ("2".equals(SlotBooking.SelPicId)) {
                address_proof_image.setImageBitmap(thumbnail);
                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 60, stream1);
                proof_Image_ByteArray = stream1.toByteArray();
                img_add_proof = getBase64EncodedString(proof_Image_ByteArray);
                imageadd_FLG = "1";
            }
        } catch (Exception e) {
            e.printStackTrace();
            photo_ImageByteArray = null;
            proof_Image_ByteArray = null;
        }

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap mutableBitmap = null;
        if (data != null) {
            try {
                mutableBitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                if ("1".equals(SlotBooking.SelPicId)) {
                    driver_image.setImageBitmap(mutableBitmap);
                    ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                    mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream1);
                    photo_ImageByteArray = stream1.toByteArray();
                    image_FLG = "1";
                } else if ("2".equals(SlotBooking.SelPicId)) {
                    address_proof_image.setImageBitmap(mutableBitmap);
                    ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                    mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream1);
                    proof_Image_ByteArray = stream1.toByteArray();
                    imageadd_FLG = "1";
                }
            } catch (IOException e) {
                e.printStackTrace();
                showToast("Please select image properly");
            }
        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.dob_btn:

                fromFLG = true;
                toFLG = false;
                bookFLG = false;
                showDialog(PRESENT_DATE_PICKER);

                break;

            case R.id.get_details_btn:

                if (dl_number.getText().toString().trim().equals("")) {
                    //Toast.makeText(getApplicationContext(),"Please Enter Driving Licence Number",Toast.LENGTH_SHORT).show();
                    showToast("Please Enter Driving Licence Number");
                } else {
                    if (isOnline()) {
                        if (Dashboard.check_Slot_or_Edit.equals("Edit")) {
                            dl_number.setVisibility(View.GONE);
                            // app_edit_llayout.setVisibility(View.VISIBLE);
                            new editSlotBookingValues().execute();
                        } else if (Dashboard.check_Slot_or_Edit.equals("Book")) {
                            new Asyn_Task_get_DL_Details().execute();
                            rd_no.setChecked(true);
                        } else {
                            new Asyn_Task_get_DL_Details().execute();
                            rd_no.setChecked(true);
                        }
                    } else {
                        //  Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                        showToast("Please Check Your Internet Connection");
                    }
                    ll_min.setVisibility(View.VISIBLE);

                }
                break;

            /*case R.id.licence_validity :
                toFLG = true;
                fromFLG = false;
                bookFLG = false;
                showDialog(TIME_DIALOG_ID);
                break;*/

            case R.id.select_state:

                if (isOnline()) {
                    if (dobcheck.equalsIgnoreCase("Yes")) {

                        String todaysdate = new DateUtil().getTodaysDate();

                        long days = new DateUtil().DaysCalucate(dob_DL, todaysdate);

                        // Minimum Age should be 16
                        if (days > 5824) {
                            dob_btn.setText(dob_DL);
                            new select_State().execute();
                        } else {
                            showToast("Please select Date of Birth Atleast Person Should be Age Greater Than 18");
                        }

                    } else {
                        new select_State().execute();
                    }

                } else {
                    // Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                    showToast("Please Check Your Internet Connection");
                }
                break;

            case R.id.select_booking_center:
                if (isOnline()) {
                    new Select_BookingSlot().execute();
                } else {
                    // Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                    showToast("Please Check Your Internet Connection");
                }
                break;

            case R.id.select_booking_date:

                fromFLG = false;
                toFLG = false;
                bookFLG = true;
                showDialog(TIME_DIALOG_ID);

                break;

            case R.id.select_booking_time:
                if (view == select_booking_time) {

                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);

                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    select_booking_time.setText(hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
                break;

            case R.id.select_addressproof_type:
                if (isOnline()) {
                    new select_Address_Proof().execute();
                } else {
                    showToast("Please Check Your Internet Connection");
                    // Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.select_idproof_type:
                if (isOnline()) {
                    new select_ID_Proof().execute();
                } else {
                    showToast("Please Check Your Internet Connection");
                    //Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.cancel_btn:
                Intent back = new Intent(getApplicationContext(), Dashboard.class);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(back);
                break;

            case R.id.submit_btn:
                if (isOnline()) {
                    final_submit();
                    Log.i("Welcome", "**final_submit**");
                } else {
                    showToast("Please Check Your Internet Connection");
                    //Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        switch (id) {
            case PROGRESS_DIALOG:
                ProgressDialog pd = ProgressDialog.show(this, "", "", true);
                //pd.setContentView(R.layout.custom_progress_dialog);
                pd.setTitle("Please wait....");
                pd.setCancelable(false);

                return pd;
            case PRESENT_DATE_PICKER:
                Calendar c = Calendar.getInstance();
                int cyear = c.get(Calendar.YEAR);
                int cmonth = c.get(Calendar.MONTH);
                int cday = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dp_offence_date = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, md1, cyear, cmonth,
                        cday);
                dobcheck = "Yes";
                dp_offence_date.getDatePicker().setMaxDate(System.currentTimeMillis());
                return dp_offence_date;

            case TIME_DIALOG_ID:

                Calendar b = Calendar.getInstance();
                int byear = b.get(Calendar.YEAR);
                int bmonth = b.get(Calendar.MONTH);
                int bday = b.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dob_date = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, md2, byear, bmonth,
                        bday);
                 dob_date.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                return dob_date;

            default:
                break;
        }
        return super.onCreateDialog(id);
    }

    private void final_submit() {

        Boolean validationFlg = true;
        if (dl_number.getText().toString().trim().equals("")) {
            // Toast.makeText(getApplicationContext(),"Please Enter your licence Number",Toast.LENGTH_SHORT).show();
            showToast("Please Enter your licence Number");
            dl_number.setError(Html.fromHtml("<font color='white'>Please Enter your licence Number</font>"));
            dl_number.requestFocus();
        } else if (dob_btn.getText().toString().trim().equals("Select Date of Birth")) {
            //Toast.makeText(getApplicationContext(),"Please Select Date Of Birth ",Toast.LENGTH_SHORT).show();
            showToast("Please Select Date Of Birth");
        } else if (driver_name.getText().toString().trim().equals("")) {
            showToast("Please Enter Name");
            driver_name.setError(Html.fromHtml("<font color='white'>Please Enter Name</font>"));
            driver_name.requestFocus();
            // Toast.makeText(getApplicationContext(),"Please Enter Driver Name",Toast.LENGTH_SHORT).show();
        } else if (driver_father_name.getText().toString().trim().equals("")) {
            // Toast.makeText(getApplicationContext(),"Please Enter Driver Father Name",Toast.LENGTH_SHORT).show();
            showToast("Please Enter Driver Father Name");
            driver_father_name.setError(Html.fromHtml("<font color='white'>Please Enter Driver Father Name</font>"));
            driver_father_name.requestFocus();
        }/*else if (driver_address.getVisibility()==View.VISIBLE && driver_address.getText().toString().length()==0){
            driver_address.setError(Html.fromHtml("<font color='white'>Please Enter Current Address</font>"));
            driver_address.requestFocus();
        }*/ else if (driver_door_no.getVisibility() == View.VISIBLE && rd_no.isChecked() && driver_door_no.getText().toString().length() == 0) {
            //Toast.makeText(getApplicationContext(),"Please Enter Door Number",Toast.LENGTH_SHORT).show();
            showToast("Please Enter Door Number");
            driver_door_no.setError(Html.fromHtml("<font color='white'>Please Enter Door Number</font>"));
            driver_door_no.requestFocus();
        } else if (driver_street_no.getVisibility() == View.VISIBLE && rd_no.isChecked() && driver_street_no.getText().toString().length() == 0) {
            //  Toast.makeText(getApplicationContext(),"Please Enter your Street Number",Toast.LENGTH_SHORT).show();
            showToast("Please Enter your Street Number");
            driver_street_no.setError(Html.fromHtml("<font color='white'>Please Enter Street Number</font>"));
            driver_street_no.requestFocus();
        } else if (driver_location.getVisibility() == View.VISIBLE && rd_no.isChecked() && driver_location.getText().toString().length() == 0) {
            showToast("Please Enter Location");
            // Toast.makeText(getApplicationContext(),"Please Enter Location",Toast.LENGTH_SHORT).show();
            driver_location.setError(Html.fromHtml("<font color='white'>Please Enter Location</font>"));
            driver_location.requestFocus();
        } else if (select_city.getVisibility() == View.VISIBLE && select_city.getText().toString().length() == 0) {
            showToast("Please Enter City ");
            select_city.setError(Html.fromHtml("<font color='white'>Please Enter City</font>"));
            select_city.requestFocus();
        } else if (select_state.getText().toString().trim().equals("Select State")) {
            showToast("Please Select State");
        } else if (driver_pincode.getText().toString().trim().equals("")) {
            showToast("Please Enter Pincode");
            driver_pincode.setError(Html.fromHtml("<font color='white'>Please Enter Pincode</font>"));
            driver_pincode.requestFocus();
        } else if (image_FLG.equals("0")) {
            showToast("Please Upload Driver Photograph");

        } else if (driver_aadhra.getText().toString().trim().equals("")) {
            showToast("Please Enter Aadhar Number");
        } else if (driver_aadhra.getText().toString().trim().length() >= 1
                && driver_aadhra.getText().toString().length() != 12) {
            driver_aadhra.setError(Html.fromHtml("<font color='white'>Enter Valid 12 digit Aadhaar Number</font>"));
            driver_aadhra.requestFocus();
        } else if (driver_aadhra.getText().toString().trim().length() >= 1
                && driver_aadhra.getText().toString().length() == 12
                && !ver.isValid(driver_aadhra.getText().toString())) {
            driver_aadhra.setError(Html.fromHtml("<font color='white'>Enter Valid Aadhaar Number</font>"));
            driver_aadhra.requestFocus();
        } else if (driver_mobile_no_one.getText().toString().trim().equals("")) {
            showToast("Please Enter Mobile Number");
            driver_mobile_no_one.setError(Html.fromHtml("<font color='white'>Please Enter Mobile Number</font>"));
            driver_mobile_no_one.requestFocus();
        } else if (driver_mobile_no_one.getText().toString().trim().length() > 1 && driver_mobile_no_one.getText().toString().trim().length() != 10) {
            showToast("Please Enter 10 digit Mobile Number");
            driver_mobile_no_one.requestFocus();
        } else if (!validateMobileNo(driver_mobile_no_one.getText().toString().trim())) {
            showToast("Please Enter 10 digit Mobile Number");
            driver_mobile_no_one.requestFocus();
        } else if (select_addressproof_type.getText().toString().trim().equals("Select Address Proof")) {
            showToast("Please Select Address Proof");
        } else if (address_proof_details.getText().toString().trim().equals("")) {
            showToast("Please Enter Address Proof");
            address_proof_details.setError(Html.fromHtml("<font color='white'>Please Enter Address Proof</font>"));
            address_proof_details.requestFocus();
        } else if (imageadd_FLG.equals("0")) {
            showToast("Please Upload Address Proof Id Photograph");
        } else if (select_booking_center.getText().toString().trim().equals("Select Slot Booking Center")) {
            showToast("Please Select Slot Booking Center");
        } else if (select_slot_booking_date.getText().toString().trim().equals("Select Booking Date")) {
            showToast("Please Select Slot Booking Date");
        } else {
            if (isOnline()) {
                new Async_finalsubmit().execute();
            } else {
                Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    /***********************Mobile Number Validation Method***************************/
    protected boolean validateMobileNo(String mobileNo) {
        boolean flg = false;
        try {
            if (mobileNo != null && mobileNo.trim().length() == 10
                    && ("6".equals(mobileNo.trim().substring(0, 1))
                    || "7".equals(mobileNo.trim().substring(0, 1))
                    || "8".equals(mobileNo.trim().substring(0, 1))
                    || "9".equals(mobileNo.trim().substring(0, 1)))) {
                flg = true;
            } else if (mobileNo != null && mobileNo.trim().length() == 11 && "0".equals(mobileNo.trim().substring(0, 1))) {
                flg = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flg = false;

        }


        return flg;
    }

    /***********************Mobile Number Validation Method Ends***************************/


    public class Async_finalsubmit extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog = new ProgressDialog(SlotBooking.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please Wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
             /*  getDriverDetails(String dlno,String dob,String driverName,String driverFname,
                     String doorno,String streetNo,String location, String city,String statecd,
                     String statename,String pincode,String isitcorrentAddress,String address,String contactNo,String contactNo1,String email,

                     String aadharno,String addressproofcd,String addressproofdet,
                     String centercd,String centerName,String slotdt,String driverimg,String addressproofimg,String logitude,String latitude,String imei);*/
            if (isOnline()) {
                ServiceHelper.getDriverDetails(
                        "" + dl_number.getText().toString().trim(),
                        "" + dob_btn.getText().toString(),
                        "" + driver_name.getText().toString().trim(),
                        "" + driver_father_name.getText().toString().trim(),
                        "" + driver_door_no.getText().toString().trim(),
                        "" + driver_street_no.getText().toString().trim(),
                        "" + driver_location.getText().toString().trim(),
                        "" + select_city.getText().toString().trim(),
                        "" + selected_state_code,
                        "" + select_state.getText().toString().trim(),
                        "" + driver_pincode.getText().toString().trim(),
                        "" + radio_btn_value,
                        "" + driver_address.getText().toString().trim(),
                        "" + driver_mobile_no_one.getText().toString().trim(),
                        "" + driver_mobile_no_two.getText().toString().trim(),
                        "" + driver_email_id.getText().toString().trim(),
                        "" + driver_aadhra.getText().toString().trim(),
                        "" + select_addressproof_type.getText().toString().trim(),
                        "" + address_proof_details.getText().toString().trim(),
                        "" + selected_ps_code,
                        "" + select_booking_center.getText().toString().trim(),
                        "" + select_slot_booking_date.getText().toString().trim(),
                        "" + getBase64EncodedString(photo_ImageByteArray),
                        "" + getBase64EncodedString(proof_Image_ByteArray),
                        "" + Dashboard.longitude_value,
                        "" + Dashboard.lattitude_value,
                        "" + Dashboard.IMEI);

            } else {
                showToast("Please Check Your Internet Connection");

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();


            if (ServiceHelper.final_response_data.equals("") || ServiceHelper.final_response_data.equals("NA") ||
                    ServiceHelper.final_response_data.equals("0")
                    || ServiceHelper.final_response_data.equals("null") || ServiceHelper.final_response_data.equals("anyType{}")) {
                showToast("No Data Found");


            } else {
                if (ServiceHelper.displayres_master[0].equals("2")) {

                    TextView title = new TextView(SlotBooking.this);
                    title.setText("Driver Registration");
                    title.setBackgroundColor(Color.BLUE);
                    title.setGravity(Gravity.CENTER);
                    title.setTextColor(Color.WHITE);
                    title.setTextSize(26);
                    title.setTypeface(title.getTypeface(), Typeface.BOLD);
                    //title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.exit_logo, 0, R.drawable.exit_logo, 0);
                    title.setPadding(20, 0, 20, 0);
                    title.setHeight(70);


                    String otp_message = "\n Already Driver Registered  \n if any changes please check in Edit option \n";

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SlotBooking.this, AlertDialog.THEME_HOLO_LIGHT);
                    alertDialogBuilder.setCustomTitle(title);
                    alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                    alertDialogBuilder.setMessage(otp_message);
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    // finish();
                                    Intent men = new Intent(getApplicationContext(), Dashboard.class);
                                    men.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(men);
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    alertDialog.getWindow().getAttributes();

                    TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
                    textView.setTextSize(28);
                    textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                    textView.setGravity(Gravity.CENTER);


                    Button btn = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    btn.setTextSize(22);
                    btn.setTextColor(Color.WHITE);
                    btn.setTypeface(btn.getTypeface(), Typeface.BOLD);
                    btn.setBackgroundColor(Color.BLUE);


                } else {
                    Intent display = new Intent(getApplicationContext(), Display_Activity.class);
                    startActivity(display);

                }
            }
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            if (fromFLG) {
                dob_btn.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
            }
            /*if (toFLG) {
                licence_validity.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);

            }*/
            if (bookFLG) {
                select_slot_booking_date.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
            }
        }
    };

    public class Asyn_Task_get_DL_Details extends AsyncTask<Void, Void, String> {
        private android.content.Context yourContext;

        ProgressDialog pdia = new ProgressDialog(SlotBooking.this);

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            // showDialog(PROGRESS_DIALOG);
            pdia.setMessage("Please Wait...");
            pdia.setCancelable(false);
            pdia.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub

            ServiceHelper.getLicenceDetails("" + dl_number.getText().toString().trim());
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdia.dismiss();


            if (ServiceHelper.licence_data != null) {
                if (ServiceHelper.licence_data.equalsIgnoreCase("") || ServiceHelper.licence_data.equals("NA") || ServiceHelper.licence_data.equals("0") || "null".equalsIgnoreCase(ServiceHelper.licence_data)
                        || ServiceHelper.licence_data.equals("anyType{}")) {
                    driver_address_layout.setVisibility(View.VISIBLE);
                    rd_no.setChecked(true);
                    radio_btn_value = "0";

                } else {

                    licence_details_master = new String[0];
                    licence_details_master = ServiceHelper.licence_data.split("!");
                    driver_name.setText("" + licence_details_master[0]);
                    driver_father_name.setText("" + licence_details_master[1]);
                    select_city.setText("" + licence_details_master[3]);

                    if (licence_details_master[4] != "0") {
                        driver_address.setText("" + licence_details_master[4]);
                        driver_address_get.setVisibility(View.VISIBLE);
                        setAddressAlert(driver_address);

                    } else {
                        driver_address_layout.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                driver_address_layout.setVisibility(View.VISIBLE);
                showToast("No details Found");

            }
        }
    }

    //dialog box for address
    private void setAddressAlert(EditText driver_address) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SlotBooking.this);
        alertDialogBuilder.setTitle("DRIVER ADDRESS");
        alertDialogBuilder.setMessage(driver_address.getText().toString() + " Is this your Current Address?\n If Yes Click on 'Yes' Buttom or if No Click on 'No' button to Change!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                driver_address_get.setVisibility(View.VISIBLE);
                                driver_address_layout.setVisibility(View.GONE);
                                rd_yes.setChecked(true);
                                rd_no.setChecked(false);
                                radio_btn_value = "1";

                            }
                        });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        rd_no.setChecked(true);
                        rd_yes.setChecked(false);
                        driver_address_get.setVisibility(View.GONE);
                        driver_address_layout.setVisibility(View.VISIBLE);
                        radio_btn_value = "0";
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    //END address

    private class select_State extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog = new ProgressDialog(SlotBooking.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...!");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                SoapObject request = new SoapObject(NAMESPACE, getSelect_state);
                Log.i("request", "" + request);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
                httpTransportSE.call(NAMESPACE + getSelect_state, envelope);
                Object result = envelope.getResponse();
                try {
                    if (null != result) {
                        Opdata_State = result.toString();
                    } else {
                        Opdata_State = "";
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Opdata_State = "";
                }


            } catch (SoapFault soapFault) {
                soapFault.printStackTrace();
                Opdata_State = "";
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                Opdata_State = "";
            } catch (IOException e) {
                e.printStackTrace();
                Opdata_State = "";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if (Opdata_State != null && !Opdata_State.equals("")) {
                if (Opdata_State.length() > 0 || !"NA".equals(Opdata_State) || !"0".equals(Opdata_State)) {
                    String[] split_state = Opdata_State.split("\\!");
                    state_names = new ArrayList<>();
                    stateMap = new HashMap<>();

                    for (int i = 1; split_state.length > i; i++) {
                        Log.i("first_Split", "--->" + split_state[i]);
                        String[] temp_split = split_state[i].split("@");
                        state_names.add(temp_split[1]);
                        stateMap.put(temp_split[0], temp_split[1]);
                    }
                    displayListState(state_names);
                }
            } else {
                showToast("No Data Found");
            }
        }
    }

    DatePickerDialog.OnDateSetListener md1 = new DatePickerDialog.OnDateSetListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint({"SimpleDateFormat", "DefaultLocale"})
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;

            format = new SimpleDateFormat("dd-MMM-yyyy");
            dob_DL = format.format(new Date(present_year - 1900, (present_month), present_day));
            dob_btn.setText(dob_DL.toUpperCase());

        }
    };

    /* FOR SLOT BOOKING DATE */
    DatePickerDialog.OnDateSetListener md2 = new DatePickerDialog.OnDateSetListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint({"SimpleDateFormat", "DefaultLocale"})
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
            present_year = selectedYear;
            present_month = monthOfYear;
            present_day = dayOfMonth;
            format = new SimpleDateFormat("dd-MMM-yyyy");
            slotbooking = format.format(new Date(present_year - 1900, (present_month), present_day));
            select_slot_booking_date.setText(slotbooking.toUpperCase());
        }
    };


    public class Select_BookingSlot extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog = new ProgressDialog(SlotBooking.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...!");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                SoapObject request = new SoapObject(NAMESPACE, getBookingCenter);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
                httpTransportSE.call(NAMESPACE + getBookingCenter, envelope);
                Object result = envelope.getResponse();
                try {
                    if (null != result) {
                        Opdata_bookingSlot = result.toString();
                    } else {
                        Opdata_bookingSlot = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Opdata_bookingSlot = "";

                }


            } catch (SoapFault soapFault) {
                soapFault.printStackTrace();
                Opdata_bookingSlot = "";
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                Opdata_bookingSlot = "";
            } catch (IOException e) {
                e.printStackTrace();
                Opdata_bookingSlot = "";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if (Opdata_bookingSlot != null && !Opdata_bookingSlot.equals("")) {

                if (Opdata_bookingSlot.length() > 0 || !"NA".equals(Opdata_bookingSlot) || !"0".equals(Opdata_bookingSlot)) {
                    String[] split_one = Opdata_bookingSlot.split("\\!");
                    ps_names = new ArrayList<>();
                    psMap = new HashMap<>();

                    for (int i = 1; split_one.length > i; i++) {
                        String[] temp_split = split_one[i].split("@");
                        ps_names.add(temp_split[1]);
                        psMap.put(temp_split[0], temp_split[1]);
                    }
                    Log.i("outofloop-->", "" + psMap);
                    displayList(ps_names);
                }
            } else {
                showToast("No Data Found");
            }
        }
    }


    ///psname
    private void displayList(final ArrayList<String> ps_names) {
        Log.i("Enter-->", "**Entered Into Dialog**");
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Select Slot Booking Center");
        final ArrayAdapter<String> aa1 = new ArrayAdapter<>(getApplicationContext(), R.layout.list_row, ps_names);
        builder.setSingleChoiceItems(aa1, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                select_booking_center.setText(aa1.getItem(item));

                if (isOnline()) {

                    for (String idCode : psMap.keySet()) {

                        if (select_booking_center.getText().toString().equals(psMap.get(idCode))) {

                            selected_ps_code = idCode;
                            Log.i("Value_pscode--->", "" + selected_ps_code);
                        }
                    }
                }
                aa1.notifyDataSetChanged();

                dialog.dismiss();
            }
        });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }


    ////STATE
    private void displayListState(final ArrayList<String> state_names) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Select The State ");
        //
        //  builder.setInverseBackgroundForced("#4286f4");
        final ArrayAdapter<String> aa1 = new ArrayAdapter<>(getApplicationContext(), R.layout.list_row, state_names);
        builder.setSingleChoiceItems(aa1, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                select_state.setText(aa1.getItem(item));
                for (String idCode : stateMap.keySet()) {
                    if (select_state.getText().toString().equals(stateMap.get(idCode))) {
                        Log.i("Entered-->", "stated code");
                        selected_state_code = idCode;
                        Log.i("selected_state_code--->", "" + selected_state_code);
                    }
                }
                aa1.notifyDataSetChanged();
                dialog.dismiss();
            }

        });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    //////Address Proof Async Task ///

    private class select_Address_Proof extends AsyncTask<Void, Void, String> {
        ProgressDialog dialog = new ProgressDialog(SlotBooking.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...!");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                SoapObject request = new SoapObject(NAMESPACE, getSelect_Address_proof);
                Log.i("request", "" + request);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
                httpTransportSE.call(NAMESPACE + getSelect_Address_proof, envelope);
                Object result = envelope.getResponse();
                try {
                    if (null != result) {
                        Opdata_SelectaddressProof = result.toString();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Opdata_SelectaddressProof = "";
                }


            } catch (SoapFault soapFault) {
                soapFault.printStackTrace();
                Opdata_SelectaddressProof = "";
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                Opdata_SelectaddressProof = "";
            } catch (IOException e) {
                e.printStackTrace();
                Opdata_SelectaddressProof = "";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if (Opdata_SelectaddressProof != null && !Opdata_SelectaddressProof.equals("")) {
                if (Opdata_SelectaddressProof.length() > 0 || !"NA".equals(Opdata_SelectaddressProof) || !"0".equals(Opdata_SelectaddressProof)) {
                    String[] split_proof = Opdata_SelectaddressProof.split("\\!");
                    addproof = new ArrayList<>();
                    proofMap = new HashMap<>();

                    for (int i = 1; split_proof.length > i; i++) {
                        String[] temp_split = split_proof[i].split("@");
                        addproof.add(temp_split[1]);
                        proofMap.put(temp_split[0], temp_split[1]);
                    }
                    displayListAddress(addproof);
                }
            } else {
                showToast("No Data Found");
            }
        }
    }

    ///////Addresss Proof

    private void displayListAddress(final ArrayList<String> addproof) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Select Proof of Address & ID");
        final ArrayAdapter<String> aa1 = new ArrayAdapter<>(getApplicationContext(), R.layout.list_row, addproof);
        builder.setSingleChoiceItems(aa1, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                select_addressproof_type.setText(aa1.getItem(item));
                for (String idCode : proofMap.keySet()) {
                    if (select_addressproof_type.getText().toString().equals(proofMap.get(idCode))) {
                        Selected_addressproof = idCode;
                        Log.i("Seld_addressproof---> ", "" + Selected_addressproof);
                    }
                }
                aa1.notifyDataSetChanged();
                dialog.dismiss();
            }

        });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }


    private class select_ID_Proof extends AsyncTask<Void, Void, String> {
        ProgressDialog dialog = new ProgressDialog(SlotBooking.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...!");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                SoapObject request = new SoapObject(NAMESPACE, getSelect_IDProof);
                Log.i("request", "" + request);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
                httpTransportSE.call(NAMESPACE + getSelect_IDProof, envelope);
                Object result = envelope.getResponse();
                try {
                    if (null != result) {
                        Opdata_Select_IDProof = result.toString();
                    } else {
                        Opdata_Select_IDProof = "";
                    }
                } catch (Exception e) {
                    Opdata_Select_IDProof = "";
                }

            } catch (SoapFault soapFault) {
                soapFault.printStackTrace();
                Opdata_Select_IDProof = "";
            } catch (java.lang.NoClassDefFoundError t) {
                t.printStackTrace();
                Opdata_Select_IDProof = "";
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                Opdata_Select_IDProof = "";
            } catch (IOException e) {
                e.printStackTrace();
                Opdata_Select_IDProof = "";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if (Opdata_Select_IDProof != null) {
                if (!Opdata_Select_IDProof.equals("")) {
                    String[] split_idproof = Opdata_Select_IDProof.split("\\!");
                    Idproof = new ArrayList<>();
                    idproofMap = new HashMap<>();

                    for (int i = 1; split_idproof.length > i; i++) {
                        Log.i("first_Split", "--->" + split_idproof[i]);
                        String[] temp_split = split_idproof[i].split("@");
                        Idproof.add(temp_split[1]);
                        proofMap.put(temp_split[0], temp_split[0]);
                    }

                    //  displayListIDProof(Idproof);
                }
            } else {

                showToast("No Data Found!");
            }

        }
    }

    //Base64bit for image convert
    public String getBase64EncodedString(byte[] bs) {
        // TODO Auto-generated method stub
        String temp_img_str = "";
        try {
            if (bs != null) {
                temp_img_str = Base64.encodeToString(bs, Base64.NO_WRAP);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return temp_img_str.trim();
    }


    @SuppressWarnings("unused")
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
