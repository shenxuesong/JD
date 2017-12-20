package example.dell.jd.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import example.dell.jd.Bean.AddCart;
import example.dell.jd.Bean.GoodsXQBean;
import example.dell.jd.EventBus.MessageEvent1;
import example.dell.jd.IActivity.IAddCart;
import example.dell.jd.IActivity.IGoodsXQActivity;
import example.dell.jd.R;
import example.dell.jd.persenter.AddCartPersenter;
import example.dell.jd.persenter.GoodsXQPersenter;
import example.dell.jd.ydyBanner.GlideImageLoader;

/**
 *详情页面
 */
public class XQActivity extends AppCompatActivity implements IGoodsXQActivity, View.OnClickListener ,IAddCart<AddCart>{

    private GoodsXQPersenter goodsXQPersenter;
    private ImageView mFanhui;
    private TextView mBaoti;
    private Banner mXqbanner;
    private TextView mXqbiaoti;
    private TextView mXqprcie;
    private List<String> list=new ArrayList<>();
    private Button addCart;
    private Button showCart;
    private String uid="";
    private String pid;
    private AddCartPersenter addCartPersenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xqfragment);
        EventBus.getDefault().register(this);
        initView();

        goodsXQPersenter = new GoodsXQPersenter(this);
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        //调用P层的方法，传pid
        goodsXQPersenter.getMP(pid);


        addCartPersenter = new AddCartPersenter(this);


    }

    /**
     * 订阅黏性事件
     *
     *
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMess(MessageEvent1 event) {
        uid = event.getUid();
    //    Log.i("ww1",uid);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      EventBus.getDefault().unregister(this);
    }

    @Override
    public void ShowBean(GoodsXQBean goodsXQBean) {
        GoodsXQBean.DataBean data = goodsXQBean.getData();

        String images = data.getImages();
        if(images!=null){
            String[] split = images.split("\\|");
            for (int i = 0; i <split.length; i++) {
                String s = split[i];
                list.add(s);
            }
        }

       mXqbanner.setImages(list).setImageLoader(new GlideImageLoader()).setDelayTime(1000).isAutoPlay(true).start();

        mBaoti.setText("商品详情页面");
        mXqbiaoti.setText(data.getTitle());//商品的标题
        mXqprcie.setText("￥："+data.getPrice());
    }

    private void initView() {
        mFanhui = (ImageView) findViewById(R.id.fanhui);
        mFanhui.setOnClickListener(this);
        mBaoti = (TextView) findViewById(R.id.baoti);
        mXqbanner = (Banner) findViewById(R.id.xqbanner);
        mXqbiaoti = (TextView) findViewById(R.id.xqbiaoti);
        mXqprcie = (TextView) findViewById(R.id.xqprcie);
        addCart = findViewById(R.id.addcart);

        addCart.setOnClickListener(this);
        //showCart.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.fanhui:
                //返回图片的点击事件
                startActivity(new Intent(this,ShowGoodsActivity.class));
                break;
            case R.id.addcart:

                //添加购物车
                if(uid==null|uid==""){
                     Toast.makeText(this, "请先登陆您的账户", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(this,LoginActivity.class));

                }else{
                    addCartPersenter.getMP(uid,pid);
               //     EventBus.getDefault().postSticky(new MessageEvent2(uid));
                }

                break;

        }
    }

    @Override
    public void showData(AddCart addCart) {
        Toast.makeText(this,addCart.getMsg(), Toast.LENGTH_SHORT).show();
    }
}
