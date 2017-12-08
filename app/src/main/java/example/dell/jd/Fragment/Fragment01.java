package example.dell.jd.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jwkj.libzxing.OnQRCodeScanCallback;
import com.jwkj.libzxing.QRCodeManager;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import example.dell.jd.Adapter.RelAdapter;
import example.dell.jd.Adapter.TuiJianRelAdapter;
import example.dell.jd.Bean.LunBoTuBean;
import example.dell.jd.Bean.ProdectBean;
import example.dell.jd.EventBus.MessageEvent;
import example.dell.jd.IActivity.IProdect1;
import example.dell.jd.R;
import example.dell.jd.persenter.ProdectPersenter1;
import example.dell.jd.ydyBanner.GlideImageLoader;


/**
 * Created by Dell on 2017/11/30.
 */

public class Fragment01 extends Fragment implements IProdect1 {
    private ViewFlipper vp;
    private View view;
    private ImageView mIvSao;
    private Banner lb;
    private RecyclerView rlv;
    private RelAdapter relAdapter;
    private List<String> list=new ArrayList<>();
    private SimpleDraweeView sdv2,sdv4;
    private EditText selcontent;
    private ImageView selBu;
  private TextView tv;
  private XRecyclerView xRlv;
    List<LunBoTuBean.TuijianBean.ListBean> list1=new ArrayList<>();
    /**
     * 扫啊扫
     */

    private long mHour = 02;
    private long mMin = 15;
    private long mSecond = 36;
    private boolean isRun = true;
    String hourStr;
    String minuteStr;
    String secondStr;
    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1) {
                computeTime();
                if (mHour<10){
                    hourStr="0"+mHour+"";
                }else {
                    hourStr="0"+mHour+"";
                }
                if (mMin<10){
                    minuteStr=mMin+"";
                }else {
                    minuteStr=mMin+"";
                }
                if (mSecond<10){
                    secondStr="0"+mSecond+"";
                }else {
                    secondStr=mSecond+"";
                }
            }
            tv.setText("秒杀："+hourStr+"小时"+minuteStr+"分钟"+secondStr+"秒");
        }
    };
    private TuiJianRelAdapter tuiJianRelAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment01, container, false);

        initView(view);
        //调用persenter层
        ProdectPersenter1 prodectPersenter = new ProdectPersenter1(this);
        prodectPersenter.getProdect();
        prodectPersenter.tuiJian();
        /**
         * 条目监听
         */
       /* relAdapter.setOnItemClickLitener(new RelAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });*/

        /**
         * 图片的轮播
         */
        list.add("https://m.360buyimg.com/n0/jfs/t8461/5/1492479653/68388/7255e013/59ba5e84N91091843.jpg!q70.jpg");
        list.add("https://m.360buyimg.com/n0/jfs/t5698/110/2617517836/202970/c9388feb/593276b7Nbd94ef1f.jpg!q70.jpg");
        list.add("https://m.360buyimg.com/n0/jfs/t2068/298/2448145915/157953/7be197df/56d51a42Nd86f1c8e.jpg!q70.jpg");
        list.add("https://m.360buyimg.com/n0/jfs/t5815/178/2614671118/51656/7f52d137/593276c7N107b725a.jpg!q70.jpg");
        //设置轮播间隔时间
       /* mBanner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        mBanner.isAutoPlay(true);*/

        lb.setImages(list).isAutoPlay(true).setDelayTime(3000).setImageLoader(new GlideImageLoader()).setBannerAnimation(Transformer.DepthPage).start();

        /**
         * 加载图片
         */
        Uri uri=Uri.parse("https://img10.360buyimg.com/babel/s780x260_jfs/t13627/123/1496791549/21938/f4714ecb/5a20cf7aNbae69933.png!q90");
        sdv2.setImageURI(uri);

        Uri uri4=Uri.parse("https://img13.360buyimg.com/da/jfs/t3907/210/534434515/20553/eb8aefe8/58523baaNe06b870c.jpg!q90");
        sdv4.setImageURI(uri4);
        /**
         * 二维码的生成
         */
        mIvSao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QRCodeManager.getInstance()
                        .with(getActivity())
                        .setReqeustType(1)//可以不设置，默认是0
                        .scanningQRCode(new OnQRCodeScanCallback() {
                            @Override
                            public void onCompleted(String result) {//扫描成功之后回调，result就是扫描的结果
                            Log.i("AA" ,result);
                            }

                            @Override
                            public void onError(Throwable errorMsg) {//扫描出错的时候回调
                               // controlLog.append("\n\n(错误)" + errorMsg.toString());
                            }

                            @Override
                            public void onCancel() {//取消扫描的时候回调
                                //controlLog.append("\n\n(取消)扫描任务取消了");
                            }
                        });
            }
        });
        /**
         * 点击搜索加载搜索的数据
         */
        selBu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(getActivity(), "0", Toast.LENGTH_SHORT).show();
                //获取输入的内容
                String content = selcontent.getText().toString();
                //发生黏性事假
                 EventBus.getDefault().postSticky(new MessageEvent(content));
                //开始事务加载新的fragment
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                SelFragment selFragment = new SelFragment();
                 transaction.replace(R.id.fg, selFragment);


                // Commit the transaction
                transaction.commit();
            }
        });

        //秒杀倒计时
        startRun();


        return view;
    }



    private void initView(View view) {
        /**
         * 二维码扫描
         */
        mIvSao = (ImageView) view.findViewById(R.id.iv_sao);
        lb = view.findViewById(R.id.lb);
        rlv = view.findViewById(R.id.SYrlv);
        sdv2 = view.findViewById(R.id.sdv2);
        tv=view.findViewById(R.id.time);
        vp=view.findViewById(R.id.view_flipper);
        vp.startFlipping();
        /**
         * 搜索内容
         */
        selcontent = view.findViewById(R.id.selcontent);
        /**
         * 搜索按钮
         */
        selBu = view.findViewById(R.id.selBut);
        sdv4 = view.findViewById(R.id.sdv4);
        /**
         * 推荐的内容
         */
        xRlv=view.findViewById(R.id.tjrlv);
    }

    /**
     * 实现列表的分类
     * @param list
     */
    @Override
    public void showData(List<ProdectBean.DataBean> list) {
        rlv.setLayoutManager(new GridLayoutManager(getActivity(),6));
        relAdapter = new RelAdapter(list, getContext());
        rlv.setAdapter(relAdapter);

    }

    /**
     * 为您推荐
     * @param lunBoTuBean
     */
    @Override
    public void showBean(LunBoTuBean lunBoTuBean) {

        LunBoTuBean.TuijianBean tuijian = lunBoTuBean.getTuijian();
        final List<LunBoTuBean.TuijianBean.ListBean> list = tuijian.getList();

        xRlv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        tuiJianRelAdapter = new TuiJianRelAdapter(list, getActivity());
        xRlv.setAdapter(tuiJianRelAdapter);
        xRlv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                xRlv.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                for (int i = 0; i <list.size() ; i++) {
                    LunBoTuBean.TuijianBean.ListBean listBean = list.get(i);
                    list1.add(listBean);
                }
                if(tuiJianRelAdapter==null){
                    tuiJianRelAdapter=new TuiJianRelAdapter(list1,getActivity());
                }else{
                    tuiJianRelAdapter.notifyDataSetChanged();
                }


            }
        });
    }

    /**
     * 开启倒计时
     */
    private void startRun() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (isRun) {
                    try {
                        Thread.sleep(1000); // sleep 1000ms
                        Message message = Message.obtain();
                        message.what = 1;
                        timeHandler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 倒计时计算
     */
    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
            }
        }
    }

}
