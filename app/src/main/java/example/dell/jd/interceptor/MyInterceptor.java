package example.dell.jd.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
      /*  Request ysRequest = chain.request();
        FormBody body = (FormBody) ysRequest.body();
        FormBody.Builder builder = new FormBody.Builder();

        for (int i = 0; i <body.size(); i++) {
            String name = body.name(i);
            String value = body.value(i);
            builder.add(name,value);
        }

        builder.add("source","android");
        FormBody formBody = builder.build();
        Request request = new Request.Builder().url(ysRequest.url()).post(formBody).build();*/
        //使用自定義 公共參數,拼接在接口后面
        Request request = chain.request();

        String url = request.url().toString() + "&source=android";

        Request.Builder builder = request.newBuilder();

        builder.get().url(url);

        Request request1 = builder.build();

        Response response = chain.proceed(request1);
        return response;

    }
}