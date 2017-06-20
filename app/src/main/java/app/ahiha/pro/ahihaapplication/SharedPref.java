package app.ahiha.pro.ahihaapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by AF on 2/26/2017.
 */

public class SharedPref {

    SharedPreferences sharedPreferences;

    public SharedPref(Context context){
      sharedPreferences = context.getSharedPreferences("MyStoreage",Context.MODE_PRIVATE);
    }

    public void SaveData(String Email){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("myEmail",Email);
        editor.commit();
    }
    public String getData(){
        String getdata = sharedPreferences.getString("myEmail","no Email");
        return getdata;
    }

}
