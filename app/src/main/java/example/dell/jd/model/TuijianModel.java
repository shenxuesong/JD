package example.dell.jd.model;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;

import example.dell.jd.Bean.LunBoTuBean;
import example.dell.jd.net.CGSB;
import example.dell.jd.net.HttpUtils1;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Dell on 2017/11/30.
 */

public class TuijianModel  {
    private Handler handler=new Handler(Looper.myLooper());

    public void getReg(String str, final CGSB<LunBoTuBean> cgsb) {
        HttpUtils1.getHttpUtils().doGet(str, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        cgsb.shiBai(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String string = response.body().string();
                final LunBoTuBean lunBoTuBean = new Gson().fromJson(string, LunBoTuBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        cgsb.chengGong(lunBoTuBean);
                    }
                });
            }
        });
    }
}
