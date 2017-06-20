package app.ahiha.pro.ahihaapplication.Auth;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.HashMap;

import app.ahiha.pro.ahihaapplication.Config.Config_Insert_Url;
import app.ahiha.pro.ahihaapplication.Config.RequestConnection;
import app.ahiha.pro.ahihaapplication.Config.regex;
import app.ahiha.pro.ahihaapplication.R;

public class ResetPassowrdActivity extends AppCompatActivity {
    TextInputLayout inputLayout_email;
    Button btn;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    LinearLayout linearLayout;
    regex reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_passowrd);
        inputLayout_email = (TextInputLayout)findViewById(R.id.reset_passowrd_input_layout_email);
        btn = (Button)findViewById(R.id.button8);
        linearLayout = (LinearLayout)findViewById(R.id.activity_reset_passowrd);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        firebaseAuth = FirebaseAuth.getInstance();
        reg = new regex(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             OnResetPassword();
            }
        });
    }

    public void OnResetPassword(){
        String get_reset_password = inputLayout_email.getEditText().getText().toString();
        if (TextUtils.isEmpty(get_reset_password) ){
            reg.showSnackBar("الرجاء ادخال بريد الإلكتروني",linearLayout);
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.sendPasswordResetEmail(get_reset_password).addOnCompleteListener(ResetPassowrdActivity.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()){
                    reg.showSnackBar("فشل عملية اعادة تعيين كلمة السر",linearLayout);
                }else {
                    reg.showSnackBar("تم ارسال اعادة تعيين كلمة السر الى البريد الإلكتروني بنجاح",linearLayout);
                }
                progressBar.setVisibility(View.GONE);
            }
        });

    }

}
