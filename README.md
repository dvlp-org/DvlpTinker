![image](https://github.com/dvlp-org/DvlpTinker/blob/master/app/src/main/res/raw/banner.jpg?raw=true)

微信版集成文档：

https://mp.weixin.qq.com/s/9RPsUzcI8ZFheHdhU2C4tg


>微信公众号：**[DvlpNews](#jump_10)**
状态：近期天气炎热影响欧巴心情，感觉每一天都不适合工作只想撩妹（坏笑），由于UI妹子大概太迷恋我了（这里添加无奈的表情），很多文案处处给我藏情，以至于上线得到很多领导对我的慰问。让我甚是稀罕UI小姐姐（笔芯小手势）。
**[如果你觉得有帮助，欢迎star](#jump_20)[^1]**


`1.主要目的》`

那我们该如何表白UI小姐姐呢？呸...那我们该如何在不发版本的情况下解决线上文案错误的问题呢？这里选择 “热跟新”



备注:这篇文章只讲解集成和使用，涉及源码分析可以搜一下资源。（请给欧巴一些撩妹的时间）。



`2.集成开始`
`第一步，配置app下gradle`
```
defaultConfig节点添加以下 第一处-----------
    multiDexEnabled true
    ndk {
            abiFilters "armeabi"
        }

dependencies 节点添加以下引用 第二处-----------
    // 多dex配置
    compile 'com.android.support:multidex:1.0.1'
    //注释掉原有bugly的仓库
    //compile 'com.tencent.bugly:crashreport:latest.release'//其中latest.release指代最新版本号，也可以指定明确的版本号，例如1.3.4
    compile 'com.tencent.bugly:crashreport_upgrade:1.3.5'
    // 指定tinker依赖版本（注：应用升级1.3.5版本起，不再内置tinker）
    compile 'com.tencent.tinker:tinker-android-lib:1.9.6'

gradle文件最下面 引入插件  第三处-----------
    // 依赖插件脚本
    apply from: 'tinker-support.gradle'
```
备注：app目录下需要添加tinker-support.gradle文件（文章最后面）



第二步，初始化
```
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        Bugly.init(this, "f7091da9a6", true);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        Beta.installTinker();
    }
}
```

第三步，添加权限
```
//添加权限 第一处-----------
 <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_LOGS" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

//第二处 自定义application
<application
        android:name=".MyApplication"
      >
</application>

//第三处 添加activity
 <activity
            android:name="com.tencent.bugly.beta.ui.BetaActivity"
            android:configChanges="keyboardHidden|orientation|screenSize|locale"
            android:theme="@android:style/Theme.Translucent" />
```
按照官方到这里就算结束了。很多会问 难道没有《坑》？

答案很显然的了，忽略了签名的配置。签名一定要配置，配置，配置（重要事说三遍）。签名配置并不是本篇文章要讲解的，可以上网查下，或者看下demo。



3.热更新使用

第一步，打上线apk（基本包）

![image](https://github.com/dvlp-org/DvlpTinker/blob/master/app/src/main/res/raw/t1.jpg?raw=true)
成功之后会在build/outputs/bakApk路径下生成每次编译的基准包、混淆配置文件、资源Id文件，有可能也会没有mapping文件，如下图所示：

![image](https://github.com/dvlp-org/DvlpTinker/blob/master/app/src/main/res/raw/t2.jpg?raw=true)


备注：请留意 编译每次生成的报名 app-0209-16-24-07(补丁包需要这个)

到这里一个提供上线的apk 就可以上架市场或者发个UI小姐姐使用了。

那我们如何 利用apk表白小姐姐呢？



第二步.热更新-（补丁包）生成。

目的：每天悄悄的修改 小姐姐手机上app的名称“在吗”，“我喜欢你”，“你很丑”，额.....看你的脑洞有多大。



例如基本包名字之前是DvlpNews

```
<resources>
    <string name="app_name">DvlpNews</string>
</resources>
```

修改之后

```
<resources>
    <string name="app_name">我稀罕你</string>
</resources>
```
修改需要变更内容之后，开始正式打补丁包



修改tinker-support.gradle文件内容
```
def baseApkDir = "app-0710-18-38-23"//要修改成编译生成的包名
tinkerId = "6.6.6" //这个任意，保证每次打包唯一就行
```

执行构建补丁包的task

![image](https://github.com/dvlp-org/DvlpTinker/blob/master/app/src/main/res/raw/t6.jpg?raw=true)


生成的补丁包在build/outputs/patch目录下：

![image](https://github.com/dvlp-org/DvlpTinker/blob/master/app/src/main/res/raw/t3.jpg?raw=true)


上传补丁包到平台并下发编辑规则 （地址放在文章后面）
![image](https://github.com/dvlp-org/DvlpTinker/blob/master/app/src/main/res/raw/t4.jpg?raw=true)



备注：点击之后会有下发中的状态，当小姐姐打开app的时候，才会执行热更新下发请求。因为热更新的初始化我们放在了MyApplication类里面。



最终效果

![image](https://github.com/dvlp-org/DvlpTinker/blob/master/app/src/main/res/raw/t7.jpg?raw=true)






*******************************************

文章中涉及的文件，以及材料地址。



tinker-support.gradle文件:

https://github.com/dvlp-org/DvlpTinker/tree/master/app



bugly 下发平台：

https://bugly.qq.com/v2/



官方文档地址：

https://bugly.qq.com/docs/user-guide/instruction-manual-android-hotfix-demo/?v=20180709165613



demo地址 欢迎star：

https://github.com/dvlp-org/DvlpTinker.git



提示：公众回复“ 热更新地址” 支持链接跳转.......

>微信公众号：**[DvlpNews](#jump_10)**
本次项目为 热更新测试集成项目，集成过程进行简单记录，有可能在正式项目接入中存在差异，例如多渠道等等...集成中有遇到问题，请以官方文档为准。当然也可以文章留言区记录，或者公众号内提问。
看到这里了......有没有人关心我和UI妹子最后怎样？？？。








<p style="text-align:center;color:#1e819e;font-size:1.3em;font-weight: bold;">
</p>


![image](https://raw.githubusercontent.com/dvlp-org/DvlpTinker/master/app/src/main/res/raw/code_2.jpg?raw=true)



