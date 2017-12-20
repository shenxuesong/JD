package example.dell.jd.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import example.dell.jd.R;
import example.dell.jd.ydyBanner.GlideImageLoader;

/**
 * 引导页面
 */
public class YDYActivity extends AppCompatActivity  {

    private Banner mBanner;
    private List<String> list=new ArrayList<>();
    private TextView tv;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ydy);
        initView();

        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511773293015&di=de54ce4cb6eb95fe354de0015e8685f7&imgtype=0&src=http%3A%2F%2Fwww.jq960.com%2Fuploads%2Fallimg%2F170422%2F1P2094Q0-0.jpg");
        list.add("http://img.redocn.com/sheji/20150525/jingdong618guanggaohaibaomoban_4396830.jpg");
        list.add("http://uus-img8.android.d.cn/content_pic/201512/behpic/snapshot/914/2-2914/2-2914-2-2_0.8.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513141931403&di=2b8f692e722d1508b5f473ed358fa630&imgtype=0&src=http%3A%2F%2Fimg.sccnn.com%2Fbimg%2F337%2F31613.jpg");


        mBanner.setImages(list).isAutoPlay(true).setDelayTime(3000).setImageLoader(new GlideImageLoader()).setBannerAnimation(Transformer.DepthPage).start();
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(YDYActivity.this,ShowGoodsActivity.class));
                timer.cancel();
            }
        });
        tiaoZhuan();
        initState();

    }
     //查找组件
    private void initView() {
        mBanner = (Banner) findViewById(R.id.banner);
        tv = findViewById(R.id.tg);

    }
    //当跳转到登陆页面
    private void tiaoZhuan(){
       //定时器9秒后t
         timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // 需要做的事:发送消息
                startActivity(new Intent(YDYActivity.this,ShowGoodsActivity.class));
            }
        };
        timer.schedule(task,9000);

    }
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
