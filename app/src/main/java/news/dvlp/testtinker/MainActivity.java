package news.dvlp.testtinker;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "我确认点击了", Toast.LENGTH_SHORT).show();
                // TODO Auto-generated method stub
                changeIcon();
//                setRoundIcon();
                Intent intent = new Intent(Intent.ACTION_MAIN);//模拟Home键点击
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });
        ((Button)findViewById(R.id.btn2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeIcon2();
            }
        });
    }

    private void changeIcon() {
        PackageManager pm = getApplicationContext().getPackageManager();
        //去除旧图标，否则会出现2个图标
//        pm.setComponentEnabledSetting(getComponentName(),
//                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
//                PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(getComponentName(),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        //显示新图标
        pm.setComponentEnabledSetting(new ComponentName(
                        getBaseContext(),
                        "news.dvlp.testtinker.MainActivity3"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void changeIcon2() {
        PackageManager pm = getApplicationContext().getPackageManager();
        //去除旧图标，否则会出现2个图标
        pm.setComponentEnabledSetting(getComponentName(),
                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
                PackageManager.DONT_KILL_APP);
        //显示新图标
        pm.setComponentEnabledSetting(new ComponentName(
                        getBaseContext(),
                        "news.dvlp.testtinker.MainActivity3"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }



    public static void openNoLauncherApk(String packageName,Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addCategory(packageName);

            List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent,
                    PackageManager.MATCH_ALL);

            for (ResolveInfo resolveInfo : resolveInfos){
                if (resolveInfo.activityInfo.packageName.equals(packageName)){
                    Intent intent1 = new Intent();
                    ComponentName componet = new ComponentName(resolveInfo.activityInfo.packageName,
                            resolveInfo.activityInfo.name);
                    intent1.setComponent(componet);
                    context.startActivity(intent1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }












    /**
     * 登录微信
     */
    // 微信登录
    private static IWXAPI WXapi;
    private String WX_APP_ID = "wx6397da1a5719b713";
    private void WXLogin() {
        WXapi = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
        WXapi.registerApp(WX_APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        WXapi.sendReq(req);

    }


}
