package app.ahiha.pro.ahihaapplication.MainActivityRes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

import app.ahiha.pro.ahihaapplication.Config.RequestConnection;
import app.ahiha.pro.ahihaapplication.R;
import app.ahiha.pro.ahihaapplication.SharedPref;

public class Donor_Profile extends AppCompatActivity {
    SharedPref sharedPref;
    TextView textViewname,dateofbirthview,cityview,regionview,phonenumberview,type_bloodview,sexdonorview;
    Button btnupdate;
    String email;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor__profile);

        btnupdate = (Button)findViewById(R.id.button9);
        textViewname = (TextView)findViewById(R.id.textView8);
        dateofbirthview = (TextView)findViewById(R.id.textView10);
        cityview = (TextView)findViewById(R.id.textView15);
        regionview = (TextView)findViewById(R.id.textView17);
        phonenumberview = (TextView)findViewById(R.id.textView19);
        type_bloodview = (TextView)findViewById(R.id.textView21);
        sexdonorview= (TextView)findViewById(R.id.textView24);

        sharedPref = new SharedPref(this);
        email = sharedPref.getData();

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(Donor_Profile.this,Update_DonorProfile.class));
            }
        });
     getDonor();
    }

    protected void getDonor(){
        class GetDonor extends AsyncTask<Void,Void,String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(Donor_Profile.this,"","جاري التحميل ...");
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                ShowDonor(s);
            }
            @Override
            protected String doInBackground(Void... voids) {
                RequestConnection requestConnection = new RequestConnection();
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Json_getData.email_post,email);
                String req = requestConnection.sendPostRequest(Json_getData.getDonor_url,hashMap);
                return req;
            }
        }
        GetDonor getDonor = new GetDonor();
        getDonor.execute();
    }
    public void ShowDonor(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(Json_getData.tag_json_array);
            JSONObject j = jsonArray.getJSONObject(0);
            String donorname = j.getString(Json_getData.tag_name);
            String jdatebirh = j.getString(Json_getData.tag_dateofbirth);
            String jcity = j.getString(Json_getData.tag_city);
            String jregion = j.getString(Json_getData.tag_region);
            String jphonenumber = j.getString(Json_getData.tag_phone);
            String jbloodtype = j.getString(Json_getData.tag_bloodtype);
            String jsexdonor = j.getString(Json_getData.tag_sex_donor);
            textViewname.setText(donorname);
            cityview.setText(jcity);
            regionview.setText(jregion);
            dateofbirthview.setText(jdatebirh);
            phonenumberview.setText(jphonenumber);
            type_bloodview.setText(jbloodtype);
            sexdonorview.setText(jsexdonor);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
