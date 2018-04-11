package info.srinivas.com.driverregistration;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by CNU on 12/4/2017.
 */

public class ServiceHelper {

    private static String NAMESPACE = "http://service.et.mtpv.com";

    private static String METHOD_NAME = "getRTADetailsByLicenceNo";
    private static String METHOD_NAME2 = "getDriverDetails";
    private static String METHOD_NAME3 = "getEditDriverDetails";
    private static String METHOD_NAME4 = "getUpdateDriverDetails";
    private static String METHOD_NAME5 = "getApplicationStatus";

    public static String SOAP_ACTION = NAMESPACE + METHOD_NAME;

    public static String SOAP_ACTION1 = NAMESPACE + METHOD_NAME2;

    public static String SOAP_ACTION3 = NAMESPACE + METHOD_NAME3;

    public static String SOAP_ACTION4 = NAMESPACE + METHOD_NAME4;
    public static String SOAP_ACTION5 = NAMESPACE + METHOD_NAME5;

    public static String licence_data, final_response_data, final_response_Edit, final_EDITresponse_data, final_response_Status;
    public static String[] displayres_master, Edit_master, final_EDIT_master, status_Master;

    public static void getLicenceDetails(String licenceNo) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("licenceNo", licenceNo);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransportSE = new HttpTransportSE(SlotBooking.URL);
            httpTransportSE.call(SOAP_ACTION, envelope);
            Object result = envelope.getResponse();
            try {
                if (null != result) {
                    licence_data = result.toString();
                } else {
                    licence_data = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
                licence_data = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            licence_data = "";
        }
    }

    public static void getDriverDetails(String dlno, String dob, String driverName, String driverFname,
                                        String doorno, String streetNo, String location, String city, String statecd,
                                        String statename, String pincode, String isitcorrentAddress, String address,
                                        String contactNo, String contactNo1, String email,
                                        String aadharno, String addressproofcd, String addressproofdet,
                                        String centercd, String centerName, String slotdt, String driverimg,
                                        String addressproofimg, String logitude, String latitude, String imei) {

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);
            request.addProperty("dlno", dlno);
            request.addProperty("dob", dob);
            request.addProperty("driverName", driverName);
            request.addProperty("driverFname", driverFname);
            request.addProperty("doorno", doorno);
            request.addProperty("streetNo", streetNo);
            request.addProperty("location", location);
            request.addProperty("city", city);
            request.addProperty("statecd", statecd);
            request.addProperty("statename", statename);
            request.addProperty("pincode", pincode);
            request.addProperty("isitcorrentAddress", isitcorrentAddress);
            request.addProperty("address", address);
            request.addProperty("contactNo", contactNo);
            request.addProperty("contactNo1", contactNo1);
            request.addProperty("email", email);
            request.addProperty("aadharno", aadharno);
            request.addProperty("addressproofcd", addressproofcd);
            request.addProperty("addressproofdet", addressproofdet);
            request.addProperty("centercd", centercd);
            request.addProperty("centerName", centerName);
            request.addProperty("slotdt", slotdt);
            request.addProperty("driverimg", driverimg);
            request.addProperty("addressproofimg", addressproofimg);
            request.addProperty("logitude", logitude);
            request.addProperty("latitude", latitude);
            request.addProperty("imei", imei);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransportSE = new HttpTransportSE(SlotBooking.URL);
            httpTransportSE.call(SOAP_ACTION1, envelope);
            Object result = envelope.getResponse();
            try{
                if (null!=result){
                    final_response_data = result.toString();
                    displayres_master = final_response_data.split("@");
                }else{
                    final_response_data ="";
                }
            }catch (Exception e){
                final_response_data ="";
            }



        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
            final_response_data ="";
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            final_response_data ="";
        } catch (IOException e) {
            e.printStackTrace();
            final_response_data ="";
        }

    }

    public static void getEditDriverDetails(String dlno, String applicationId) {

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME3);
            request.addProperty("dlno", dlno);
            request.addProperty("applicationId", applicationId);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransportSE = new HttpTransportSE(SlotBooking.URL);
            httpTransportSE.call(SOAP_ACTION3, envelope);
            Object result = envelope.getResponse();
            try {
                if (null != result) {
                    final_response_data = result.toString();
                    Edit_master = final_response_data.split("!");
                } else {
                    final_response_data = "0";
                }
            } catch (Exception e) {
                e.printStackTrace();
                final_response_data = "0";
            }
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
            final_response_data = "0";
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            final_response_data = "0";
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            final_response_data = "0";
        } catch (IOException e) {
            e.printStackTrace();
            final_response_data = "0";
        }

    }

    public static void getUpdateDriverDetails(String dlno, String dob, String driverName, String driverFname,
                                              String doorno, String streetNo, String location, String city, String statecd,
                                              String statename, String pincode, String isitcorrentAddress, String address,
                                              String contactNo, String contactNo1, String email,
                                              String aadharno, String addressproofcd, String addressproofdet,
                                              String centercd, String centerName, String slotdt, String driverimg,
                                              String addressproofimg, String logitude, String latitude, String imei, String driverId) {

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME4);
            request.addProperty("dlno", dlno);
            request.addProperty("dob", dob);
            request.addProperty("driverName", driverName);
            request.addProperty("driverFname", driverFname);
            request.addProperty("doorno", doorno);
            request.addProperty("streetNo", streetNo);
            request.addProperty("location", location);
            request.addProperty("city", city);
            request.addProperty("statecd", statecd);
            request.addProperty("statename", statename);
            request.addProperty("pincode", pincode);
            request.addProperty("isitcorrentAddress", isitcorrentAddress);
            request.addProperty("address", address);
            request.addProperty("contactNo", contactNo);
            request.addProperty("contactNo1", contactNo1);
            request.addProperty("email", email);
            request.addProperty("aadharno", aadharno);
            request.addProperty("addressproofcd", addressproofcd);
            request.addProperty("addressproofdet", addressproofdet);
            request.addProperty("centercd", centercd);
            request.addProperty("centerName", centerName);
            request.addProperty("slotdt", slotdt);
            request.addProperty("driverimg", driverimg);
            request.addProperty("addressproofimg", addressproofimg);
            request.addProperty("logitude", logitude);
            request.addProperty("latitude", latitude);
            request.addProperty("imei", imei);
            request.addProperty("driverId", driverId);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransportSE = new HttpTransportSE(SlotBooking.URL);
            httpTransportSE.call(SOAP_ACTION4, envelope);
            Object result = envelope.getResponse();
            try {
                if (null != result) {
                    final_EDITresponse_data = result.toString();
                    final_EDIT_master = final_EDITresponse_data.split("@");
                } else {
                    final_EDITresponse_data = "";
                }
            } catch (Exception e) {
                final_EDITresponse_data = "";
            }
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
            final_EDITresponse_data = "";
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            final_EDITresponse_data = "";
        } catch (IOException e) {
            e.printStackTrace();
            final_EDITresponse_data = "";
        }
    }


    public static void getApplicationStatus(String dlno, String applicationId) {

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME5);
            request.addProperty("dlno", dlno);
            request.addProperty("applicationId", applicationId);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransportSE = new HttpTransportSE(SlotBooking.URL);
            httpTransportSE.call(SOAP_ACTION5, envelope);
            Object result = envelope.getResponse();


            try {
                if (null != result) {
                    final_response_Status = result.toString();
                    status_Master = final_response_Status.split("!");
                } else {
                    final_response_Status = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
                final_response_Status = "";
            }
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
            final_response_Status = "";
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            final_response_Status = "";
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            final_response_Status = "";
        } catch (Exception e) {
            e.printStackTrace();
            final_response_Status = "";
        }

    }
}
