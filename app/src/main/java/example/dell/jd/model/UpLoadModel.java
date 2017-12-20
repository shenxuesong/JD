package example.dell.jd.model;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by Dell on 2017/12/15.
 */

public class UpLoadModel {
    private Handler handler=new Handler(Looper.myLooper());


  /*  public void  getUpLoad(HashMap<String,Object> paramsMap, final CGSB<UpLoadBean>cgsb){
        HttpUtils1.getHttpUtils().uploadFile(paramsMap, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final UpLoadBean upLoadBean = new Gson().fromJson(string, UpLoadBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        cgsb.chengGong(upLoadBean);
                    }
                });
            }
        });
    }*/
}
