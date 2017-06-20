package app.ahiha.pro.ahihaapplication.MainActivityRes;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

import app.ahiha.pro.ahihaapplication.Auth.LoginActivity;
import app.ahiha.pro.ahihaapplication.Auth.RegisterMySqlActivity;
import app.ahiha.pro.ahihaapplication.Config.RequestConnection;
import app.ahiha.pro.ahihaapplication.Config.regex;
import app.ahiha.pro.ahihaapplication.R;
import app.ahiha.pro.ahihaapplication.SharedPref;

public class Update_DonorProfile extends AppCompatActivity {

    protected Button submit_update;
    protected TextInputLayout name_layout,phone_layout,region_layout,city_layout,datebirth_layout;
    protected EditText name,phone,region,city,dateofbirth;
    private Spinner spinner;
    private ArrayAdapter<String> arrayAdapter;
    private String SelectSpinerBlood;
    private String OptionMaleorFemale;
    private LinearLayout linearlayout;
    SharedPref sharedPref;
    private String send_email;
    regex reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__donor_profile);

        submit_update = (Button)findViewById(R.id.button10);

        //instance class
        reg = new regex(this);
        sharedPref = new SharedPref(this);

        name_layout = (TextInputLayout)findViewById(R.id.name_updatelayout);
        phone_layout = (TextInputLayout)findViewById(R.id.phone_updatelayout);
        region_layout = (TextInputLayout)findViewById(R.id.region_updatelayout);
        city_layout = (TextInputLayout)findViewById(R.id.city_updatelayout);
        datebirth_layout = (TextInputLayout)findViewById(R.id.date_updtelayout);
        linearlayout = (LinearLayout)findViewById(R.id.activity_update__donor_profile);
        send_email = sharedPref.getData();
        //Spinner
        spinner = (Spinner)findViewById(R.id.spinner_update);
        final String bloodtypes[] = {"AB", "AB-", "O+", "O-", "B+", "B-", "A+","A-"};
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,bloodtypes);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long l) {
                SelectSpinerBlood = bloodtypes[postion];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Update_DonorProfile.this,"الرجاء تحديد فصيل الدم",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void checkradiobtn(View view) {

        boolean Checked = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.radio_u_m:
                if (Checked){
                    OptionMaleorFemale = "ذكر";
                    break;
                }
            case R.id.radio_u_f:
                if (Checked){
                    OptionMaleorFemale = "انثى";
                    break;
                }
        }

    }

    public void Submit_Update(View view) {

        name = name_layout.getEditText();
        phone = phone_layout.getEditText();
        region = region_layout.getEditText();
        city = city_layout.getEditText();
        dateofbirth = datebirth_layout.getEditText();

        if (!reg.validateField(name)){
            reg.showSnackBar("يرجى ادخال الاسم",linearlayout);
        }else if (!reg.validateField(phone)){
            reg.showSnackBar("يرجى ادخال رقم الهاتف",linearlayout);
        }else if (!reg.validateField(region)){
            reg.showSnackBar("يرجى ادخال المنطقة",linearlayout);
        }else if (!reg.validateField(city)){
            reg.showSnackBar("يرجى ادخال المدينة",linearlayout);
        }else if (!reg.validateField(dateofbirth)){
            reg.showSnackBar("يرجى ادخال تاريخ الميلاد",linearlayout);
        }else {
            UpdateDonor();
        }
    }

    private void UpdateDonor() {

    class updatedonor extends AsyncTask<Void,Void,String>{

        String send_name = name.getText().toString();
        String send_phone = phone.getText().toString();
        String send_dateofbirth = dateofbirth.getText().toString();
        String send_region = region.getText().toString();
        String send_city = city.getText().toString();

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Update_DonorProfile.this,"","جاري تعديل البيانات...",false,false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            AlertDialogonUpdate();
        }

        @Override
        protected String doInBackground(Void... voids) {

            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put(Json_getData.name_donor,send_name);
            hashMap.put(Json_getData.phone,send_phone);
            hashMap.put(Json_getData.city_donor,send_city);
            hashMap.put(Json_getData.region_donor,send_region);
            hashMap.put(Json_getData.date_donor,send_dateofbirth);
            hashMap.put(Json_getData.sex_donor,OptionMaleorFemale);
            hashMap.put(Json_getData.bloodtype,SelectSpinerBlood);
            hashMap.put(Json_getData.email_donor,send_email);

            RequestConnection requestConnection = new RequestConnection();
            String send_data = requestConnection.sendPostRequest(Json_getData.updateDonor_URl,hashMap);

            return send_data;
        }
      }
        updatedonor updatedonor = new updatedonor();
        updatedonor.execute();
    }

    public void AlertDialogonUpdate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setCancelable(false);
        builder.setMessage("تم تعديل البيانات بنجاح ..");
        builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.create();
        builder.show();
    }

}
