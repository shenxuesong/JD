package example.dell.jd.net;


import java.util.Map;

import example.dell.jd.interceptor.MyInterceptor;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Dell on 2017/11/30.
 * 单列模式
 */

public class HttpUtils {
    private static HttpUtils httpUtils;
    private final OkHttpClient client;

    private HttpUtils() {
        /**
         * 自定义拦截器上传公共参数
         */
        client = new OkHttpClient.Builder().addInterceptor(new MyInterceptor()).build();
    }
    public static HttpUtils getHttpUtils(){
        if(httpUtils==null){
            synchronized (HttpUtils.class){
                httpUtils=new HttpUtils();
            }
        }
        return httpUtils;
    }

    /**
     * get请求
     * @param str
     * @param callback
     */
    public void doGet(String str, Callback callback){
        Request build = new Request.Builder().url(str).build();
        client.newCall(build).enqueue(callback);
    }
    /**
     * post请求
     */
    public void doPost(String str, Map<String,String>map,Callback callback){
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String,String> entry:map.entrySet()
             ) {
            builder.add(entry.getKey(),entry.getValue());
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder().url(str).post(formBody).build();
        client.newCall(request).enqueue(callback);
    }
}
