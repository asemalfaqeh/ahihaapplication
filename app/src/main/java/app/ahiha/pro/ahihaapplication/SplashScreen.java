package app.ahiha.pro.ahihaapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import app.ahiha.pro.ahihaapplication.Auth.LoginActivity;

public class SplashScreen extends Activity {
    private String textanimate = "بضع دقائق من وقتك يمكن أن تنقذ أرواح أخرى.\n" +
            "أثناء قراءتك هذه السطور قد يكون هناك مريض يعاني من مرض خطير أو مصاب بحادث وقد تعتمد حياته على توفر وحدة دم مطابقة لنوع دمه.\n" +
            " وقد لا يحتاج معظمنا لنقل دم طيلة حياتنا، ولكن بالنسبة لآخرين قد تعني الفرق بين الحياة والموت.\n " +
            "لا يحتاج التبرع بالدم لأكثر من بضعة دقائق من وقتك. ";
    private Integer timedealy = 100;
    private Typewriter typewriter;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        typewriter = (Typewriter)findViewById(R.id.typewriter);
        typewriter.animateText(textanimate);
        typewriter.setCharacterDelay(timedealy);
        btn = (Button)findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashScreen.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
