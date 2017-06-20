package app.ahiha.pro.ahihaapplication;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

import app.ahiha.pro.ahihaapplication.Config.Config_Insert_Url;
import app.ahiha.pro.ahihaapplication.Config.RequestConnection;

public class testactivity extends AppCompatActivity {
    EditText email,password;
    Button insert;
    EditText savesharedpref;
    Button savebtn,loadbtn;
    FirebaseAuth firebaseAuth;
    SharedPref sharedPref;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testactivity);
        email = (EditText)findViewById(R.id.editTextt);//email
        password = (EditText)findViewById(R.id.editText2t);
        insert = (Button)findViewById(R.id.inserttest);
        savesharedpref = (EditText)findViewById(R.id.storeagetext);
        savebtn = (Button)findViewById(R.id.storeage);
        loadbtn = (Button)findViewById(R.id.loaddata);
        textView = (TextView)findViewById(R.id.textView);
        sharedPref = new SharedPref(this);
        firebaseAuth = FirebaseAuth.getInstance();
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertintmysql();

            }
        });

    }



    public void insertintmysql(){

        class Adddata extends AsyncTask<Void,Void,String>{

            String email_stirng = email.getText().toString().trim();
            String password_s = password.getText().toString().trim();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(testactivity.this,"Adding Data please wait",Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(testactivity.this,"Success Mysql!",Toast.LENGTH_LONG).show();
                insertfirebase(email_stirng,password_s);
            }
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Config_Insert_Url.key_PASS,email_stirng);
                RequestConnection requestConnection = new RequestConnection();
                String put = requestConnection.sendPostRequest(Config_Insert_Url.url_test,hashMap);
                return put;
            }
        }

        Adddata adddata = new Adddata();
        adddata.execute();
    }

    public void insertfirebase(String email_f,String pass_f){

        Toast.makeText(testactivity.this,"Loading firebase...",Toast.LENGTH_LONG).show();
        firebaseAuth.createUserWithEmailAndPassword(email_f,pass_f).addOnCompleteListener(testactivity.this ,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(testactivity.this,"Loading insert fiewbase...",Toast.LENGTH_LONG).show();
                if (task.isSuccessful()){
                    Toast.makeText(testactivity.this,"Firebase Success!",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(testactivity.this,"error fireabse",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void SaveintSahredPref(View view) {
        String savefromedittext = savesharedpref.getText().toString();
        sharedPref.SaveData(savefromedittext);
    }

    public void LoadfromFile(View view) {
        String getdata = sharedPref.getData();
        textView.setText(getdata);
    }
}
