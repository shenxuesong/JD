package example.dell.jd.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Map;

import example.dell.jd.Bean.DressBean;
import example.dell.jd.Bean.LunBoTuBean;
import example.dell.jd.Bean.ProdectBean;
import example.dell.jd.GDDT.ShowMapActivity;
import example.dell.jd.IActivity.IProdect1;
import example.dell.jd.IActivity.IUserDress;
import example.dell.jd.R;
import example.dell.jd.persenter.DressPersenter;
import example.dell.jd.persenter.ProdectPersenter1;
import example.dell.jd.zhifuBao.OrderUtils;
import example.dell.jd.zhifuBao.PayResult;

/**
 * 请求用户的下单地址
 * 为了效果展示，用详情页的商品代替购物车的
 */
public class DressActivity extends AppCompatActivity implements IUserDress,IProdect1{

    private ImageView mFanhui;
    private TextView mBaoti;
    private TextView mNameAndph;
    private TextView mDress;
    private SimpleDraweeView mSdview;
    private TextView mGoodsname;
    private TextView mGoodsprice;
    private TextView mGoodscount;
    /**
     * 实付：00.0
     */
    private TextView mZfprice;
    private Button zf;
    private ImageView dingwei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dress);
        initView();
        //获取从购物车传过来的uid
        String uid = getIntent().getStringExtra("uid");

        new DressPersenter(this).getUserDress(uid);

        new ProdectPersenter1(this).tuiJian();

        //购物车的勾选的商品
      /*  CartBean.DataBean dataBean= (CartBean.DataBean) getIntent().getSerializableExtra("dataBean");
        List<CartBean.DataBean.ListBean> list = dataBean.getList();

            CartBean.DataBean.ListBean listBean = list.get(0);
           String s = listBean.getImages().split("\\|")[0];
           double price = listBean.getPrice();
           String title = listBean.getTitle();
           mNameAndph.setText(title+"\n"+"Y:"+price);
             Uri  uri= Uri.parse(s);
            mSdview.setImageURI(uri);
*/


        /**
         * 立即下单的点击事件,支付宝支付
         */
        final String orderInfo = getorderInfo();   // 订单信息
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(DressActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        zf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread=new Thread(payRunnable);
                thread.start();
            }
        });
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);

        mBaoti = (TextView) findViewById(R.id.baoti);
        mBaoti.setText("确认订单");
        mNameAndph = (TextView) findViewById(R.id.nameAndph);
        mDress = (TextView) findViewById(R.id.dress);
        mSdview = (SimpleDraweeView) findViewById(R.id.sdview);
        mGoodsname = (TextView) findViewById(R.id.goodsname);
        mGoodsprice = (TextView) findViewById(R.id.goodsprice);
        mGoodscount = (TextView) findViewById(R.id.goodscount);
        mZfprice = (TextView) findViewById(R.id.zfprice);
        zf = (Button)findViewById(R.id.zf);
        dingwei = findViewById(R.id.dingwei );
        //定位
        dingwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                             startActivity(new Intent(DressActivity.this, ShowMapActivity.class));
            }
        });
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DressActivity.this, ShowGoodsActivity.class));
            }
        });
    }

    @Override
    public void showDate(DressBean.DataBean dataBean) {
        String name = dataBean.getName();
        String mobile = String.valueOf(dataBean.getMobile());
        String s = mobile.replaceFirst("7045", "****");
        String addr = dataBean.getAddr();
        mNameAndph.setText(name+"       "+s);
           mDress.setText(addr);


    }

    @Override
    public void showData(List<ProdectBean.DataBean> list) {

    }

    @Override
    public void showBean(LunBoTuBean lunBoTuBean) {
        LunBoTuBean.TuijianBean tuijian = lunBoTuBean.getTuijian();
        LunBoTuBean.TuijianBean.ListBean listBean = tuijian.getList().get(0);
        String s = listBean.getImages().split("\\|")[0];
        double price = listBean.getPrice();
        String title = listBean.getTitle();
        Uri uri= Uri.parse(s);
        mSdview.setImageURI(uri);
        mGoodsname.setText(title);
        mGoodsprice.setText("💴"+price);
        mZfprice.setText("实付："+price);
    }


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);

            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // TODO 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                Toast.makeText(DressActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            } else {
                //TODO  该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    Toast.makeText(DressActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            }
        }
    };


    /**
     * 记得要看
     * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
     * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
     * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
     * <p>
     * orderInfo的获取必须来自服务端；
     */
    public String getorderInfo() {
        Map<String, String> params = OrderUtils.buildOrderParamMap(OrderUtils.APPID);
        String orderParam = OrderUtils.buildOrderParam(params);
        String sign = OrderUtils.getSign(params, OrderUtils.RSA_PRIVATE, false);
        final String orderInfo = orderParam + "&" + sign;
        return orderInfo;
    }

}
