package example.dell.jd.net;


import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Dell on 2017/11/30.
 * 单列模式
 */

public class HttpUtils1 {
    private static HttpUtils1 httpUtils;
    private final OkHttpClient client;

    private HttpUtils1() {
        /**
         * 自定义拦截器上传公共参数
         */
        client = new OkHttpClient();
    }
    public static HttpUtils1 getHttpUtils(){
        if(httpUtils==null){
            synchronized (HttpUtils1.class){
                httpUtils=new HttpUtils1();
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
    public void uploadFile(HashMap<String, Object> paramsMap, String filePath, final CallBackListener listener) {
        try {
            String actionUrl = "file/upload";
            String sdcardPath = Environment.getExternalStorageDirectory().getPath();
            filePath = sdcardPath + filePath;
            final String requestUrl = String.format("%s%s", "http://120.27.23.105/", actionUrl);
            File file = new File(filePath);
            MultipartBody.Builder builder = new MultipartBody.Builder();
            //设置类型 表单
            builder.setType(MultipartBody.FORM);
            for (String key : paramsMap.keySet()) {
                Object object = paramsMap.get(key);
                builder.addFormDataPart(key, object.toString());
            }//image/jpeg
            // MediaType mediaType = MediaType.parse("application/octet-stream");
            //MediaType mediaType = MediaType.parse("image/jpeg");
            builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            //创建RequestBody
            RequestBody body = builder.build();
            //创建Request
            final Request request = new Request.Builder().url(requestUrl).post(body).build();
            //单独设置参数 比如读取超时时间
            final Call call = getOKHttpClient().newBuilder().build().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (null != listener) {
                        listener.onError("上传失败" + requestUrl + "|" + e.toString());
                    }
                    Log.e(TAG, "上传失败==" + e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        if (null != listener) {
                            listener.onSuccess("" + string + requestUrl);
                        }
                        Log.e(TAG, "上传成功==" + string);
                    } else {
                        Log.e(TAG, "上传失败------------");
                        if (null != listener) {
                            listener.onError("上传失败" + requestUrl);
                        }
                    }
                }
            });
        } catch (Exception e) {
            if (null != listener) {
                listener.onError("上传失败" + e.getMessage());
            }
        }
    }
    private OkHttpClient getOKHttpClient() {
        OkHttpClient client = new OkHttpClient
                .Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        return client;
    }
}
