package example.dell.jd.model;

import android.os.Handler;

import com.google.gson.Gson;

import java.io.IOException;

import example.dell.jd.Bean.DressBean;
import example.dell.jd.net.CGSB;
import example.dell.jd.net.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Dell on 2017/12/13.
 */

public class DressModel {
        private Handler handler=new Handler() ;
    public void getUserdrss(String str, final CGSB<DressBean> cgsb){
        HttpUtils.getHttpUtils().doGet(str, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final DressBean dressBean = new Gson().fromJson(string, DressBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        cgsb.chengGong(dressBean);
                    }
                });
            }
        });
    }
}
