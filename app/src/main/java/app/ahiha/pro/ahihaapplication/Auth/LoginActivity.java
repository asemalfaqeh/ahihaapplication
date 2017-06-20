package app.ahiha.pro.ahihaapplication.Auth;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import app.ahiha.pro.ahihaapplication.Config.regex;
import app.ahiha.pro.ahihaapplication.MainActivity;
import app.ahiha.pro.ahihaapplication.R;
import app.ahiha.pro.ahihaapplication.SharedPref;

public class LoginActivity extends Activity {

    private Button loginbtn,regbtn;
    private TextView forgotpassword;
    private TextInputLayout input_email,input_password;
    EditText input_edittext_email;
    LinearLayout linearLayout;
    private regex reg;
    private FirebaseAuth authlogin;
    private ProgressBar progressBar;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authlogin = FirebaseAuth.getInstance();
        if (authlogin.getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);
        loginbtn = (Button)findViewById(R.id.button4);
        regbtn = (Button)findViewById(R.id.button5);
        linearLayout = (LinearLayout)findViewById(R.id.activity_login_layout);
        forgotpassword = (TextView)findViewById(R.id.button2);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        sharedPref = new SharedPref(this);
        input_email = (TextInputLayout)findViewById(R.id.input_layout_email);
        input_edittext_email = (TextInputEditText)findViewById(R.id.input_email);
        input_password = (TextInputLayout)findViewById(R.id.password_input_layout);
        authlogin = FirebaseAuth.getInstance();

        reg = new regex(this);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentreg = new Intent(LoginActivity.this,RegisterMySqlActivity.class);
                startActivity(intentreg);
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentreset = new Intent(LoginActivity.this,ResetPassowrdActivity.class);
                startActivity(intentreset);
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            logincode();
            }
        });
    }


    public void logincode(){

         final String get_email = input_email.getEditText().getText().toString();//layout
         final String get_password = input_password.getEditText().getText().toString();//layout
        
        if (TextUtils.isEmpty(get_email)) {
           reg.showSnackBar("الرجاء ادخال البريد الالكتروني",linearLayout);
            return;
        }

        if (TextUtils.isEmpty(get_password)) {
           reg.showSnackBar("الرجاء ادخال كلمة السر",linearLayout);
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        authlogin.signInWithEmailAndPassword(get_email,get_password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //on loading
                progressBar.setVisibility(View.GONE);
            if (!task.isSuccessful()){
                //failed login
                    reg.showSnackBar("الرجاء ادخال البريد الالكتروني و كلمة السر بشكل صحيح",linearLayout);
            }else {
                //success login
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                sharedPref.SaveData(get_email);
                startActivity(intent);
                finish();
            }
            }
        });

    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }
}
