package app.ahiha.pro.ahihaapplication.Auth;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

import app.ahiha.pro.ahihaapplication.Config.Config_Insert_Url;
import app.ahiha.pro.ahihaapplication.Config.RequestConnection;
import app.ahiha.pro.ahihaapplication.Config.regex;
import app.ahiha.pro.ahihaapplication.R;

public class RegisterMySqlActivity extends AppCompatActivity {

    private Button btnnexttofirebase;
    private TextInputLayout email_input,name_input,phone_input,city_input,region_input
            ,date_input,password_input_layout;
    private EditText email,name,phone,city,region,date,password;
    private String radioselectitem;//female or male
    private Spinner spinner;
    regex reg;
    FirebaseAuth firebaseAuth;
    String getpositionbloodtype;
    ProgressDialog progressDialog2;
    LinearLayout linearLayout;
    final String bloodtypes[] = {"AB", "AB-", "O+", "O-", "B+", "B-", "A+","A-"};
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_my_sql);
        btnnexttofirebase = (Button)findViewById(R.id.button7);
        linearLayout = (LinearLayout)findViewById(R.id.activity_register_my_sql);
        reg = new regex(this);
        firebaseAuth = FirebaseAuth.getInstance();
        //TextEditTextInput
        email_input = (TextInputLayout)findViewById(R.id.email_input_layout);
        name_input = (TextInputLayout)findViewById(R.id.usernname_input_layout);
        phone_input = (TextInputLayout)findViewById(R.id.phone_layout_input);
        city_input = (TextInputLayout)findViewById(R.id.city_input_layout);
        region_input = (TextInputLayout)findViewById(R.id.region_input_layout);
        date_input = (TextInputLayout)findViewById(R.id.date_input_layout);
        password_input_layout = (TextInputLayout)findViewById(R.id.password_input_layout);
        spinner = (Spinner)findViewById(R.id.spinner);
        //EditText
        email = (TextInputEditText)findViewById(R.id.email_input);
        name = (TextInputEditText)findViewById(R.id.username_input);
        phone = (TextInputEditText)findViewById(R.id.password_input);
        city = (TextInputEditText)findViewById(R.id.city_input);
        region = (TextInputEditText)findViewById(R.id.region_input);
        date = (TextInputEditText)findViewById(R.id.date_input);
        password = (TextInputEditText)findViewById(R.id.password_input);

        //End get Text from Edittext
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,bloodtypes);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int positon, long l) {
                getpositionbloodtype = bloodtypes[positon];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
             Toast.makeText(RegisterMySqlActivity.this,"رجاء تحديد فصيل الدم",Toast.LENGTH_LONG).show();
            }
        });
    }
    //choice radio click male or female
    public void checkradiobtn(View v){
        boolean checked = ((RadioButton)v).isChecked();
        switch (v.getId()){
            case R.id.radio_m:
                if (checked)
                    radioselectitem = "ذكر";
                break;
            case R.id.radio_f:
                if (checked)
                radioselectitem = "انثى";
                break;
        }
    }
    public void RegisterClick(View v){
        email = email_input.getEditText();
        name = name_input.getEditText();
        phone = phone_input.getEditText();
        city = city_input.getEditText();
        region = region_input.getEditText();
        date = date_input.getEditText();
        password = password_input_layout.getEditText();
        if (!reg.validateEmail(email)){
            email.setError("*مطلوب");
        reg.showSnackBar("يرجى ادخال البريد الإلكتروني بشكل صحيح مثال على ذلك : abc@gamil.com",linearLayout);
        }else if(!reg.validateField(password)) {
            reg.showSnackBar("يرجى ادخال كلمة السر",linearLayout);
        }else if(!reg.validateField(name)){
         reg.showSnackBar("يرجى ادخال الاسم",linearLayout);
        }else if (!reg.validateField(phone)){
            reg.showSnackBar("يرجى ادخال رقم الهاتف",linearLayout);
        }else if (!reg.validateField(city)){
            reg.showSnackBar("يرجى ادخال المدينة",linearLayout);
        }else if (!reg.validateField(region)){
            reg.showSnackBar("يرجى ادخال المنطقة",linearLayout);
        }else if (!reg.validateField(date)){
            reg.showSnackBar("يرجى ادخال تاريخ الميلاد",linearLayout);
        }else {
            AddDonor();
        }
    }
    public void AddDonor(){

     class AddDonor extends AsyncTask<Void,Void,String>{

         String email_value = email.getText().toString();
         String name_value = name.getText().toString();
         String phone_value = phone.getText().toString();
         String password_value = password.getText().toString();
         String city_value = city.getText().toString();
         String region_value = region.getText().toString();
         String date_value = date.getText().toString();

         ProgressDialog progressDialog;

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             progressDialog = ProgressDialog.show(RegisterMySqlActivity.this,"","يرجى الانتظار جاري اضافة بياناتك ...",false,false);
         }

         @Override
         protected void onPostExecute(String s) {
             super.onPostExecute(s);
             progressDialog.dismiss();
             RegisterDonortoFirebase(email_value,password_value);
         }

         @Override
         protected String doInBackground(Void... voids) {
             HashMap<String, String> hashMap = new HashMap<>();
             hashMap.put(Config_Insert_Url.KEY_EMAIL_DONOR,email_value);
             hashMap.put(Config_Insert_Url.KEY_NAME_DONOR,name_value);
             hashMap.put(Config_Insert_Url.KEY_DATEOFBIRTH_DONOR,date_value);
             hashMap.put(Config_Insert_Url.KEY_CITY_DONOR,city_value);
             hashMap.put(Config_Insert_Url.KEY_REGION_DONOR,region_value);
             hashMap.put(Config_Insert_Url.KEY_BLOODTYPE_DONOR,getpositionbloodtype);
             hashMap.put(Config_Insert_Url.KEY_SEX_DONOR,radioselectitem);
             hashMap.put(Config_Insert_Url.KEY_PHONE_DONOR,phone_value);
             RequestConnection requestConnection = new RequestConnection();
             String putDataofDonor = requestConnection.sendPostRequest(Config_Insert_Url.url_insert,hashMap);
             return putDataofDonor;
         }
     }

        AddDonor addDonor = new AddDonor();
        addDonor.execute();

    }
    public void AlertDialogonAddData(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogStyle);
        builder.setCancelable(false);
        builder.setMessage("تم انشاء حسابك بنجاح ..");
        builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(RegisterMySqlActivity.this,LoginActivity.class);
                FirebaseAuth.getInstance().signOut();
                startActivity(intent);
                finish();
            }
        });
        builder.create();
        builder.show();
    }
    //register email and password into firebase
    public void RegisterDonortoFirebase(String auth_email,String auth_password){

        progressDialog2 =  ProgressDialog.show(RegisterMySqlActivity.this,"","جاري التسجيل ..",false,false);
        firebaseAuth.createUserWithEmailAndPassword(auth_email,auth_password).addOnCompleteListener(RegisterMySqlActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Dialog show progress
                if (!task.isSuccessful()){
                    //progress show failed msg
                    progressDialog2.dismiss();
                    Toast.makeText(getApplicationContext(),"لا يوجد اتصال يرجى المحاولة مرة اخرى",Toast.LENGTH_LONG).show();
                }else {
                    //Success register
                    AlertDialogonAddData();
                }
            }
        });
    }

}
