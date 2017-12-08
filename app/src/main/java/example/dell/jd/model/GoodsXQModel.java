package example.dell.jd.model;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;

import example.dell.jd.Bean.GoodsXQBean;
import example.dell.jd.net.CGSB;
import example.dell.jd.net.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Dell on 2017/12/2.
 */

public class GoodsXQModel  {
    private Handler handler=new Handler(Looper.myLooper());

    public void getGoods(String url, final CGSB<GoodsXQBean> cgsb)
    {
        HttpUtils.getHttpUtils().doGet(url, new Callback() {
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
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final GoodsXQBean goodsXQBean = new Gson().fromJson(string, GoodsXQBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        cgsb.chengGong(goodsXQBean );
                    }
                });
            }
        });
    }
}
