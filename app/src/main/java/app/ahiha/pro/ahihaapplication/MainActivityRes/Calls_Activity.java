package app.ahiha.pro.ahihaapplication.MainActivityRes;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.ahiha.pro.ahihaapplication.Config.RequestConnection;
import app.ahiha.pro.ahihaapplication.R;

public class Calls_Activity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<HashMap<String,String>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calls_);
        listView = (ListView)findViewById(R.id.list_calls);
        GetCallsMethod();
    }

    public void ViewCalls(String jsonstring){
        JSONObject jsonObject = null;
        arrayList = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(jsonstring);
            JSONArray jsonArray = jsonObject.getJSONArray(Json_getData.jsot_tag_calls);
            for (int i =0 ; i <jsonArray.length();i++){
            JSONObject datacalls = jsonArray.getJSONObject(i);
                //get data from json array
                String hospitaname = datacalls.getString(Json_getData.name_hospital);
                String patient_h = datacalls.getString(Json_getData.patient_health);
                String numblood = datacalls.getString(Json_getData.num_blood);
                String address = datacalls.getString(Json_getData.address);
                String tyblood = datacalls.getString(Json_getData.ty_blood);
                String startcall = datacalls.getString(Json_getData.start_date);
                String endcall = datacalls.getString(Json_getData.end_date);
                String phone = datacalls.getString(Json_getData.phonetocall);
                String note = datacalls.getString(Json_getData.notes);
                //set data in hashmap value
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Json_getData.name_hospital,hospitaname);
                hashMap.put(Json_getData.patient_health,patient_h);
                hashMap.put(Json_getData.num_blood,numblood);
                hashMap.put(Json_getData.address,address);
                hashMap.put(Json_getData.ty_blood,tyblood);
                hashMap.put(Json_getData.start_date,startcall);
                hashMap.put(Json_getData.end_date,endcall);
                hashMap.put(Json_getData.phonetocall,phone);
                hashMap.put(Json_getData.notes,note);
                arrayList.add(hashMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            e.getMessage();
            e.getStackTrace();
        }
        ListAdapter listAdapter = new SimpleAdapter(Calls_Activity.this,arrayList,R.layout.view_calls,
                new String[]{Json_getData.name_hospital,Json_getData.patient_health
                ,Json_getData.num_blood,Json_getData.address,
                        Json_getData.ty_blood,Json_getData.start_date,
                        Json_getData.end_date,
                        Json_getData.phonetocall,Json_getData.notes},new int[]{
                R.id.hpspitanameview,R.id.patienthealthview,
        R.id.numbloodview,R.id.addressview,R.id.tybloodview,R.id.startdateview,R.id.enddateview,
                R.id.phonetocall,R.id.notes});
        listView.setAdapter(listAdapter);
    }

    public void GetCallsMethod() {

        class GetCalls extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(Calls_Activity.this, "جاري التحميل ...", Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                ViewCalls(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestConnection requestConnection = new RequestConnection();
                String getconnection = requestConnection.SendPostRequestUrl(Json_getData.getCalls_Url);
                return getconnection;
            }
        }
        GetCalls getCalls = new GetCalls();
        getCalls.execute();
    }
}
