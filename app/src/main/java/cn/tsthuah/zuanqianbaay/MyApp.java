package cn.tsthuah.zuanqianbaay;

import android.app.Application;
import android.util.Log;

import com.umeng.commonsdk.UMConfigure;

public class MyApp extends Application {
    private static MyApp instance;
    public static MyApp getInstance() {

        if (instance == null) {
            synchronized (MyApp.class) {
                if (null == instance) {
                    instance = new MyApp();
                }
            }
        }

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "1fe6a20054bcef865eeb0991ee84525b");
        Log.e("wangying","初始化成功了吗？");
    }
}
