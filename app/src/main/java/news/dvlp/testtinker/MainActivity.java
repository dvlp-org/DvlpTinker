package news.dvlp.testtinker;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                changeIcon();
                Intent intent = new Intent(Intent.ACTION_MAIN);//模拟Home键点击
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });
    }

    private void changeIcon() {
        PackageManager pm = getApplicationContext().getPackageManager();
        //去除旧图标，否则会出现2个图标
//        pm.setComponentEnabledSetting(getComponentName(),
//                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
//                PackageManager.DONT_KILL_APP);
        //显示新图标
        pm.setComponentEnabledSetting(new ComponentName(
                        getBaseContext(),
                        "news.dvlp.testtinker.MainActivity2"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

}
