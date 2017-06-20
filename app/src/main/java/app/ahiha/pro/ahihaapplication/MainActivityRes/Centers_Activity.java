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

public class Centers_Activity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<HashMap<String,String>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centers_);

        listView = (ListView)findViewById(R.id.list_center);
        GetCenters();

    }

    public void SetCenters(String json){
        JSONObject jsonObject = null;
        arrayList = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(Json_getData.tag_json_center);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String nameofcenter = jsonObject1.getString(Json_getData.center_name);
                String cityofcenter = jsonObject1.getString(Json_getData.centercity);
                String regionofcenter = jsonObject1.getString(Json_getData.centerregion);
                String streetnamecentre = jsonObject1.getString(Json_getData.centerstreetname);
                String phoneofcenter = jsonObject1.getString(Json_getData.centerphone);

                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Json_getData.center_name,nameofcenter);
                hashMap.put(Json_getData.centercity,cityofcenter);
                hashMap.put(Json_getData.centerregion,regionofcenter);
                hashMap.put(Json_getData.centerstreetname,streetnamecentre);
                hashMap.put(Json_getData.centerphone,phoneofcenter);
                arrayList.add(hashMap);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter listAdapter = new SimpleAdapter(Centers_Activity.this,arrayList,R.layout.view_center
        ,new String[]{Json_getData.center_name,Json_getData.centercity,Json_getData.centerregion
        ,Json_getData.centerstreetname,Json_getData.centerphone},new int[]{R.id.namecenter,
        R.id.centercity,R.id.regionecenter,R.id.streetname,R.id.phonecenter});
        listView.setAdapter(listAdapter);
    }

    public void GetCenters(){

        class getcenters extends AsyncTask<Void,Void,String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(Centers_Activity.this,"جاري التحميل ...",Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                SetCenters(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestConnection requestConnection = new RequestConnection();
                String req = requestConnection.SendPostRequestUrl(Json_getData.getCenterurl);
                return req;
            }
        }
        getcenters getc = new getcenters();
        getc.execute();
    }

}
