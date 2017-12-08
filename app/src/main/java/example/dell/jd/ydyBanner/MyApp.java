package example.dell.jd.ydyBanner;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;

import example.dell.jd.MyEventBusIndex;

/**
 * Created by Dell on 2017/11/27.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        Config.DEBUG = true;
        UMShareAPI.get(this);
        EventBus eventBus = EventBus.builder().addIndex(new MyEventBusIndex()).build();
    }
    {
        PlatformConfig.setWeixin("", "");
        PlatformConfig.setQQZone("1106423171", "K3NEXOgW84ZeFH3M");
        PlatformConfig.setSinaWeibo("", "", "");

    }
}
