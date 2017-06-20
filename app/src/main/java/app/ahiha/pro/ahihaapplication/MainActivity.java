package app.ahiha.pro.ahihaapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import app.ahiha.pro.ahihaapplication.MainActivityRes.Centers_Activity;
import app.ahiha.pro.ahihaapplication.MainActivityRes.Donor_Profile;
import app.ahiha.pro.ahihaapplication.MainActivityRes.Calls_Activity;
import app.ahiha.pro.ahihaapplication.MainActivityRes.Settings_Activity;
import app.ahiha.pro.ahihaapplication.MainActivityRes.advicevoruser;

public class MainActivity extends AppCompatActivity {
    TextView ed,checkconnect;
    String email;
    private MenuInflater menuInflater;
    SharedPref sharedPref;
    private Menu menu;
    Button btn3_profile,viewhospital,advicebtn,exitbtn;
    NetworkInfo wifi,mobilenetwork;
    ConnectivityManager connectivityManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed = (TextView) findViewById(R.id.emailview);
        sharedPref = new SharedPref(this);
        email = sharedPref.getData();
        btn3_profile = (Button)findViewById(R.id.button3);
        viewhospital = (Button)findViewById(R.id.button11);
        advicebtn = (Button)findViewById(R.id.button12);
        exitbtn = (Button)findViewById(R.id.button13);
        checkconnect = (TextView)findViewById(R.id.checkconnection);
        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        mobilenetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected() || mobilenetwork.isConnected()){
            checkconnect.setText("متصل في شبكة الانترنت");
        }else{
            checkconnect.setText("لا يوجد اتصال في شبكة الانترنت");
        }

        ed.setText(email);


        //profile
        btn3_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Donor_Profile.class));
            }
        });
        viewhospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenth = new Intent(MainActivity.this, Centers_Activity.class);
                startActivity(intenth);
            }
        });
        advicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenth = new Intent(MainActivity.this,advicevoruser.class);
                startActivity(intenth);
            }
        });
        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
                finish();
            }
        });

}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i==R.id.settings){//open Settings Activity
           Intent i1 = new Intent(MainActivity.this, Settings_Activity.class);
            startActivity(i1);
            finish();
        }
        if (i == R.id.notifaction){// Just open Noti Activity
            Intent i2 = new Intent(MainActivity.this, Calls_Activity.class);
            startActivity(i2);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogStyle);
        builder.setMessage("هل تريد الخروج ؟");
        builder.setCancelable(true);
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(0);
                finish();
            }
        });
        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //empty
            }
        });
        builder.create();
        builder.show();
    }
}
