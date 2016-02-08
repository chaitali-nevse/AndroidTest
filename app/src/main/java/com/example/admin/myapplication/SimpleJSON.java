package com.example.admin.myapplication;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class SimpleJSON extends ActionBarActivity {

    Button btnNavigate;
    List<String> array_name =  new ArrayList<String>();
    List<String> array_id =  new ArrayList<String>();
    List<String> array_car =  new ArrayList<String>();
    List<String> array_train =  new ArrayList<String>();
    List<String> array_latitude =  new ArrayList<String>();
    List<String> array_longitude =  new ArrayList<String>();

    Spinner spinner;
    TextView tvCarVal, tvTrainVal;
    public static String LATITUDE;
    public static String LONGITUDE;

    private GoogleMap mMap;
    LatLng point=null;
    Marker DelMArker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_json);
        array_name.clear();

        btnNavigate=(Button)findViewById(R.id.btnNavigate);
        spinner=(Spinner)findViewById(R.id.spinner);
        tvCarVal=(TextView)findViewById(R.id.tvCarVal);
        tvTrainVal=(TextView)findViewById(R.id.tvTrainVal);


        String WebserviceURL = "http://express-it.optusnet.com.au/sample.json";
        new PopulateSpinner().execute(WebserviceURL);

        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mMap != null) {
                    mMap.clear();
                }

                FragmentManager myFragmentManager = getFragmentManager();
                MapFragment myMapFragment = (MapFragment) myFragmentManager.findFragmentById(R.id.map);
                mMap = myMapFragment.getMap();

                point = new LatLng(Double.parseDouble(LATITUDE), Double.parseDouble(LONGITUDE));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

                point = new LatLng(Double.valueOf(LATITUDE).doubleValue(), Double.valueOf(LONGITUDE).doubleValue());
                DelMArker = mMap.addMarker(new MarkerOptions().position(point).title("name").snippet("area"));

            }
        });
    }


    // Class with extends AsyncTask class

    private class PopulateSpinner  extends AsyncTask<String, Void, Void> {

        // Required initialization

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(SimpleJSON.this);
        String data = "";

        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //Start Progress Dialog (Message)

            Dialog.setMessage("Please wait..");
            Dialog.show();

            try {
                // Set Request parameter
                data += "&" + URLEncoder.encode("data", "UTF-8") + "=";// + serverText.getText();

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            /************ Make Post Call To Web Server ***********/
            BufferedReader reader = null;

            // Send data
            try {

                // Defined URL  where to send data
                URL url = new URL(urls[0]);

                // Send POST data request

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "  ");
                }

                // Append Server Response To Content String
                Content = sb.toString();
            } catch (Exception ex) {
                Error = ex.getMessage();
            } finally {
                try {

                    reader.close();
                } catch (Exception ex) {
                }
            }

            /*****************************************************/
            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.

            // Close progress dialog
            Dialog.dismiss();

            if (Error != null) {

            } else {
                /****************** Start Parse Response JSON Data *************/

                try {

                    /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                    JSONArray jsonResponse = new JSONArray(Content);

                    /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                    /*******  Returns null otherwise.  *******/
                    /*********** Process each JSON Node ************/

                    int lengthJsonArr = jsonResponse.length();

                    for (int i = 0; i < lengthJsonArr; i++) {
                        /****** Get Object for each JSON node.***********/
                        JSONObject jsonChildNode = jsonResponse.getJSONObject(i);

                        /******* Fetch node values **********/
                        String id = jsonChildNode.optString("id").toString();
                        String name = jsonChildNode.optString("name").toString();

                        String fromcentral = jsonChildNode.optString("fromcentral").toString();
                        String location = jsonChildNode.optString("location").toString();


                        JSONObject parentObject_fromcentral = new JSONObject(fromcentral);

                        String car = parentObject_fromcentral.optString("car").toString();
                        String train = parentObject_fromcentral.optString("train").toString();

                        JSONObject parentObject_location = new JSONObject(location);

                        String latitude = parentObject_location.optString("latitude").toString();
                        String longitude = parentObject_location.optString("longitude").toString();

                        array_name.add(name);
                        array_id.add(id);
                        array_car.add(car);
                        array_train.add(train);
                        array_latitude.add(latitude);
                        array_longitude.add(longitude);

                        //Toast.makeText(SimpleJSON.this, ""+ name +","+ id +","+ car + ","+ train +","+ latitude +","+longitude+"", Toast.LENGTH_LONG).show();

                    }
                    /****************** End Parse Response JSON Data *************/

                    // Application of the Array to the Spinner
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SimpleJSON.this,R.layout.spinner_item, array_name);
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
                    spinner.setAdapter(spinnerArrayAdapter);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            String mFileName=adapterView.getSelectedItem().toString();
                            // Toast.makeText(SimpleJSON.this,"Selected file " + mFileName,Toast.LENGTH_LONG).show();

                            String mid= array_id.get(i);
                            String mName= array_name.get(i);
                            String mCar= array_car.get(i);
                            String mTrain= array_train.get(i);
                            String mLatitude= array_latitude.get(i);
                            String mLongitude= array_longitude.get(i);

                            tvCarVal.setText(mCar);
                            tvTrainVal.setText(mTrain);
                            LATITUDE=mLatitude;
                            LONGITUDE=mLongitude;

                            //Toast.makeText(SimpleJSON.this, ""+ mName +","+ mid +","+ mCar + ","+ mTrain +","+ mLatitude +","+ mLongitude +"", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(SimpleJSON.this,Home.class);
        startActivity(intent);
        finish();
    }
}
