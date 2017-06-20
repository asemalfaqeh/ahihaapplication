package app.ahiha.pro.ahihaapplication.MainActivityRes;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Set;

import app.ahiha.pro.ahihaapplication.Auth.LoginActivity;
import app.ahiha.pro.ahihaapplication.Config.Config_U_D;
import app.ahiha.pro.ahihaapplication.Config.RequestConnection;
import app.ahiha.pro.ahihaapplication.Config.regex;
import app.ahiha.pro.ahihaapplication.MainActivity;
import app.ahiha.pro.ahihaapplication.R;
import app.ahiha.pro.ahihaapplication.SharedPref;

public class Settings_Activity extends AppCompatActivity {
   Button update_email_vbtn,update_email_unvbtn,update_password_v,update_password_un;
   Button logout_btn,delete_profile_btn;
   TextInputLayout input_update_email_layout,input_password_layout;
    private FirebaseUser auth;
    LinearLayout linearLayout;
    private ProgressDialog progressDialog;
    EditText email,password;
    SharedPref sharedPref;
    String email_storeage;
    TextInputEditText update_em;
    private Toolbar toolbar;
    regex reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);

        update_email_vbtn = (Button)findViewById(R.id.update_email_btn);
        update_email_unvbtn = (Button)findViewById(R.id.update_email_click);
        linearLayout = (LinearLayout)findViewById(R.id.activity_settings_);
        update_em = (TextInputEditText)findViewById(R.id.update_email_edittext);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        reg = new regex(this);
        sharedPref = new SharedPref(this);
        email_storeage = sharedPref.getData();
        update_em.setText(email_storeage);
        //required text input
        input_update_email_layout = (TextInputLayout)findViewById(R.id.update_email_layout);

        input_password_layout = (TextInputLayout)findViewById(R.id.update_password_layout);
        auth = FirebaseAuth.getInstance().getCurrentUser();
        update_password_v = (Button)findViewById(R.id.update_passowrd_vbtn);
        update_password_un = (Button)findViewById(R.id.update_password_unvbtn);
        logout_btn = (Button)findViewById(R.id.Logout_btn);
        delete_profile_btn = (Button)findViewById(R.id.delete_profile);

        //toolbar
        setSupportActionBar(toolbar);
        toolbar.setTitle("الأعدادات");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentm = new Intent(Settings_Activity.this, MainActivity.class);
                startActivity(intentm);
                finish();
            }
        });

    }
    //visible button click to visible textinputlayout and btn
    public void Update_Email(View view) {
        update_email_unvbtn.setVisibility(View.VISIBLE);
        input_update_email_layout.setVisibility(View.VISIBLE);
        update_email_vbtn.setVisibility(View.GONE);//hide this button
        update_password_un.setVisibility(View.GONE);
        input_password_layout.setVisibility(View.GONE);
        update_password_v.setVisibility(View.VISIBLE);
    }
    //gone textinputlayout and btn for update email
    public void Update_E_Click(View view) {

        email = input_update_email_layout.getEditText();
        if (!reg.validateEmail(email)) {
            reg.showSnackBar("يرجى ادخال البريد الإلكتروني بشكل صحيح مثال على ذلك : abc@gamil.com", linearLayout);
        } else if (TextUtils.isEmpty(email.getText().toString())) {
                reg.showSnackBar("يرجى ادخال البريد الإلكتروني الجديد",linearLayout);
               return;}
            else {
            class Update_Email_Donor extends AsyncTask<Void, Void, String> {

                String email_success = email.getText().toString();

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressDialog = ProgressDialog.show(Settings_Activity.this, "", "جاري تحديث البريد الإلكتروني");
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    progressDialog.dismiss();
                    FirebaseUpdate(email_success);
                }

                @Override
                protected String doInBackground(Void... voids) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config_U_D.Key_Email, email_success);
                    hashMap.put(Config_U_D.W_Key_Email, email_storeage);
                    RequestConnection requestConnection = new RequestConnection();
                    String SuccessPost = requestConnection.sendPostRequest(Config_U_D.Url_Update, hashMap);
                    return SuccessPost;
                }
            }
            Update_Email_Donor update_email_donor = new Update_Email_Donor();
            update_email_donor.execute();
        }
    }

    //Delete Profiel
    public void Update_Password_Click(View view) {

        String validate_password = input_password_layout.getEditText().getText().toString();
        if (TextUtils.isEmpty(validate_password)){
            reg.showSnackBar("يرجى ادخال كلمة السر",linearLayout);
            return;
        }
        progressDialog = ProgressDialog.show(Settings_Activity.this,"","جاري تحديث كلمة السر ..");
        auth.updatePassword(validate_password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    AlertDialog_Success("تم تحديث كلمة السر بنجاح سيتم تسجيل الخروج");
                }else {
                    update_password_v.setVisibility(View.VISIBLE);
                    input_password_layout.setVisibility(View.GONE);
                    update_password_un.setVisibility(View.GONE);
                    reg.showSnackBar("لم يتم تحديث كلمة السر يرجى المحاولة مرة اخرى",linearLayout);
                    progressDialog.dismiss();
                }
            }
        });
    }
    public void Update_Password(View view) {
      input_password_layout.setVisibility(View.VISIBLE);
      update_password_un.setVisibility(View.VISIBLE);
      update_password_v.setVisibility(View.GONE);
      update_email_unvbtn.setVisibility(View.GONE);
      input_update_email_layout.setVisibility(View.GONE);
      update_email_vbtn.setVisibility(View.VISIBLE);
    }

    public void Delete_Profile(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogStyle);
        builder.setMessage("هل تريد الغاء الحساب ؟");
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            Delete_Profile();
            }
        });
        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();
        builder.setCancelable(true);
    }
    public void Delete_Profile(){
        class Delete_Donor extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(Settings_Activity.this, "", "جاري الغاء الحساب ..");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                FirebaseDelete();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Config_U_D.Key_Delete,email_storeage);
                RequestConnection requestConnection = new RequestConnection();
                String SuccessPost = requestConnection.sendPostRequest(Config_U_D.Url_Delete, hashMap);
                return SuccessPost;
            }
        }
        Delete_Donor delete_donor = new Delete_Donor();
        delete_donor.execute();
    }

    public void Logout(View view) {
        AlertDialog.Builder builderlogout = new AlertDialog.Builder(this,R.style.AlertDialogStyle);
        builderlogout.setCancelable(true);
        builderlogout.setMessage("هل انت متأكد من رغبتك بتسجيل خروجك ؟");
        builderlogout.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Logout_Activit();
            }
        });
        builderlogout.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builderlogout.create();
        builderlogout.show();
    }
    //override method
    public void Logout_Activit(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Settings_Activity.this, LoginActivity.class));
        finish();
    }
    public void AlertDialog_Success(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogStyle);
        builder.setMessage(msg);
        builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Logout_Activit();
            }
        });
        builder.create();
        builder.show();
        builder.setCancelable(false);
    }

    //update email on firebase
    public void FirebaseUpdate(String email_firebase){
        progressDialog =  ProgressDialog.show(Settings_Activity.this,"","جاري تحديث البريد الإلكتروني ..",false,false);
        auth.updateEmail(email_firebase).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    AlertDialog_Success("تم تحديث البريد الإلكتروني بنجاح سيتم تسجيل الخروج");
                }else {
                    progressDialog.dismiss();
                    update_email_vbtn.setVisibility(View.VISIBLE);
                    update_email_unvbtn.setVisibility(View.GONE);
                    input_update_email_layout.setVisibility(View.GONE);
                    reg.showSnackBar("لم يتم تحديث البريد الإلكتروني يرجى المحاولة مرة اخرى",linearLayout);

                }
            }
        });
    }
    public void FirebaseDelete(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser auth2 = firebaseAuth.getCurrentUser();
        progressDialog = ProgressDialog.show(Settings_Activity.this,"","سيتم تسجيل الخروج ..");
     auth2.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
         @Override
         public void onComplete(@NonNull Task<Void> task) {
             if (task.isSuccessful()){
                 progressDialog.dismiss();
                 Logout_Activit();
             }else{
                 progressDialog.dismiss();
                 Toast.makeText(Settings_Activity.this,"لقد حدث خطأ يرجى المحاولة مرة اخرى",Toast.LENGTH_LONG).show();
             }
         }
     });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentm = new Intent(Settings_Activity.this, MainActivity.class);
        startActivity(intentm);
        finish();
    }
}
