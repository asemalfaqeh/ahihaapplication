package app.ahiha.pro.ahihaapplication.MainActivityRes;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import app.ahiha.pro.ahihaapplication.R;

public class advicevoruser extends ActionBarActivity {

    private TextView textView1,textView2,textView3,textView4;
    private String adv1,adv2,adv3,adv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advicevoruser);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        textView1 = (TextView)findViewById(R.id.adv1);
        textView2 = (TextView)findViewById(R.id.adv2);
        textView3 = (TextView)findViewById(R.id.adv3);
        textView4 = (TextView)findViewById(R.id.adv4);
        adv1 = "الشخص البالغ يمكنه التبرع بنحو 450-500 مللي من دمه دون أي مخاوف أو أخطار على صحته، ويمكنه التبرع كل 3 أشهر للرجال،" +
                "\nو4 أشهر للسيدات، ويفضل ألا يزيد عدد مرات التبرع عن 4 سنويا.";
        adv2 = "لا توجد أي مشكلات قد تحدث للمتبرع بالدم،\n" +
                "طالما وقّع الطبيب الكشف الطبي على المتبرع،\n" +
                "وأكد أنه مناسب، ولا توجد أي موانع مرضية أو جسدية،\n" +
                "ونادرا ما تحدث أعراض بعد التبرع مثل الدوخة أو القيء وتزول تلقائيا بعد فترة وجيزة.";
        adv3 = "لا توجد إرشادات معينة قبل التبرع بالدم،\n" +
                "ولكن يفضل تناول وجبة مناسبة قبل الذهاب إلى المستشفى بساعتين،\n" +
                "والتوقف عن الإقلاع، كما يفضل اختيار مستشفى أو بنك الدم،\n" +
                "وبمعنى أدق مكان تثق أن الدم الذي ستتبرع به سيذهب إلى المحتاجين إليه،\n" +
                "كما أنه آمن تماما.";
        adv4 = "- ينصح بأخذ قسط من الراحة وتناول وجبة خفيفة وعصير،\n" +
                "ويمكن مغادرة مكان التبرع بعد 10 - 15 دقيقة.\n" +
                "- يفضل تجنب النشاط البدني الشاق أو رفع الأحمال الثقيلة لمدة 5 ساعات بعد التبرع.\n" +
                "- إذا كنت تشعر بعد التبرع بالدوار،\n" +
                "ينصح بالاستلقاء على الظهر مع رفع الساقين،\n" +
                "ويمكن الإكثار من تناول السوائل والعصائر الطبيعية.\n" +
                "- يجب البعد عن التدخين لمدة ساعتين بعد التبرع،\n" +
                "لأن استنشاق الدخان يحفز الدم للذهاب للرئتين،\n" +
                "ما يسبب حالة من الدوار والشحوب.";
        textView1.setText(adv1);
        textView2.setText(adv2);
        textView3.setText(adv3);
        textView4.setText(adv4);


    }
}
