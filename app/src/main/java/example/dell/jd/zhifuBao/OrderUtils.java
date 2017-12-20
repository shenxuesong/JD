package example.dell.jd.zhifuBao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * Created by liqy on 2017/12/7.
 */

public class OrderUtils {

    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2016102802383554";
    /**
     * 商户私钥，pkcs8格式
     */
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALbUGZlfrFiw1h35" +
            "2TF1tBgw9OuNPCK3172WS3kYTFb0CKqiYf6NVrZWHBWZM4IzvYsXqmKqqVyX5Qsd" +
            "nSejGh/rJLoDjXyuo5gQNPkj+mmfb4F1/A/hWrFNjsjHTfFDqkU3Z/ajO2YSpVRQ" +
            "lPqYCUccnerZqOedVOwNd9gzXqbFAgMBAAECgYEAiI5HSI1zNxNt+hnBpfWaPG9k" +
            "oANvpSbXlx2I5bvGWTQQvRJoSy7aU3pho6g4Fsn6isd0VhnOQgCZ7QGDfjWOrn+n" +
            "rXo2730axUZMRkzSBOkWB0iX1yfveKNN04s2GUCDkU5YIBpYMFfTDphZmgJeJ8zd" +
            "5yOey1hBZCjch1iM8YkCQQDvX5ywzwUndslAaBSuNbVNsZmmuAy74v+KSTvbW2c6" +
            "c+sCnz8G+1BrfGOhdGBUsrJGo9jbEXJh6ezwiwFOrPi/AkEAw4cKgcILsFUYsXA3" +
            "flVKIIuPrw31Ryz9EXcysPehcQUkATXh3WmVuzmEOyZSd9hFDgLAdBsuh6IzTipk" +
            "tcqdewJBAMtEweZSpenRMS0ENSuKaB9FxViRyh5ysNVZQv8PKyWz8ckUOY1QNAZS" +
            "Zrhf/r1t0X2y/R9qPVtwLchGAiIxODkCQAC1A0+20O4BUMaLflfhnRQDDTD33vQz" +
            "8HJYuQE01AuhliC+/iMb16PGsHi6ScAPMyi6z9Fbq85nwsG8m4KgyfMCQHv5kjsW" +
            "CL/Cd30MGr5BMbZOPJwG+SD9tT04STQt9cjz9Y2ltGyGMMiK87sLze7Ha65aUwDr" +
            "MRCqT2VCBalK0yo=";

    /**
     * 构造支付订单参数列表
     *
     * @param app_id
     * @return
     */
    public static Map<String, String> buildOrderParamMap(String app_id) {
        Map<String, String> keyValues = new HashMap<String, String>();

        keyValues.put("app_id", app_id);

        keyValues.put("biz_content", "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"0.01\",\"subject\":\"1\",\"body\":\"我是测试数据\",\"out_trade_no\":\"" + getOutTradeNo() + "\"}");

        keyValues.put("charset", "utf-8");

        keyValues.put("method", "alipay.trade.app.pay");

        keyValues.put("sign_type", "RSA");

//        keyValues.put("sign_type","RSA2");

        keyValues.put("timestamp", "2016-07-29 16:55:53");

        keyValues.put("version", "1.0");

        return keyValues;
    }

    /**
     * 要求外部订单号必须唯一。
     *
     * @return
     */
    private static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * 构造支付订单参数信息
     *
     * @param map 支付订单参数
     * @return
     */
    public static String buildOrderParam(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, true));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, true));

        return sb.toString();
    }

    /**
     * 拼接键值对
     *
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * 对支付参数信息进行签名
     *
     * @param map 待签名授权信息
     * @return
     */
    public static String getSign(Map<String, String> map, String rsaKey, boolean rsa2) {
        List<String> keys = new ArrayList<String>(map.keySet());
        // key排序
        Collections.sort(keys);

        StringBuilder authInfo = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            authInfo.append(buildKeyValue(key, value, false));
            authInfo.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        authInfo.append(buildKeyValue(tailKey, tailValue, false));

        String oriSign = SignUtils.sign(authInfo.toString(), rsaKey, rsa2);
        String encodedSign = "";

        try {
            encodedSign = URLEncoder.encode(oriSign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "sign=" + encodedSign;
    }
}
