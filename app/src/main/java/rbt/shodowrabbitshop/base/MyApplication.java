package rbt.shodowrabbitshop.base;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Administrator on 2017/1/8.
 */
public class MyApplication extends Application {
    public static MyApplication instance;
    public static MyApplication getInstance() {
        return (MyApplication) instance;
    }
    public void onCreate() {
        super.onCreate();
        instance = this;
        AVOSCloud.initialize(this, "PEdm2eoYqYENv2JUYNg2t3pT-gzGzoHsz", "xxQnpEjYcgkNFLlBqK67dwbO");
    }
}
