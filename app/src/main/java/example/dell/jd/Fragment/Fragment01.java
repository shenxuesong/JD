package example.dell.jd.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jwkj.libzxing.OnQRCodeScanCallback;
import com.jwkj.libzxing.QRCodeManager;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import example.dell.jd.Adapter.MiaoShaRelAdapter;
import example.dell.jd.Adapter.RelAdapter;
import example.dell.jd.Adapter.TuiJianRelAdapter;
import example.dell.jd.Bean.LunBoTuBean;
import example.dell.jd.Bean.ProdectBean;
import example.dell.jd.IActivity.IProdect1;
import example.dell.jd.R;
import example.dell.jd.persenter.ProdectPersenter1;
import example.dell.jd.view.DangQianFenLeiGoodsActivity;
import example.dell.jd.view.GoodsSelectActivity;
import example.dell.jd.view.Web2Activity;
import example.dell.jd.view.Web3Activity;
import example.dell.jd.view.Web4Activity;
import example.dell.jd.view.WebActivity;
import example.dell.jd.ydyBanner.GlideImageLoader;


/**
 * Created by Dell on 2017/11/30.
 */

public class Fragment01 extends Fragment implements IProdect1, View.OnClickListener {
    private ViewFlipper vp;
    private View view;
    private ImageView mIvSao;
    private Banner lb;
    private RecyclerView rlv;
    private RelAdapter relAdapter;
    private List<String> list = new ArrayList<>();
    private SimpleDraweeView sdv2, sdv4;
    private TextView selcontent;
    private ImageView selBu;
    private TextView tv;
    private XRecyclerView xRlv;
    List<LunBoTuBean.TuijianBean.ListBean> list1 = new ArrayList<>();
    private String tuyi="https://m.360buyimg.com/babel/s380x420_jfs/t13240/53/1338844768/27711/f82d9617/5a1e8caeNbcdaf329.png!q90";
    private String tuer="https://m.360buyimg.com/babel/s380x420_jfs/t12388/203/1606628261/61541/fc5b1de6/5a25167fN160fd0b9.jpg!q90";
    private String tuyi1="https://img10.360buyimg.com/babel/s780x260_jfs/t12640/271/1804152794/16387/7a6c9196/5a29f71aN2d29d11e.jpg!q90";
    private String tuer2="https://img11.360buyimg.com/babel/s780x260_jfs/t14323/28/149812160/20113/dd22654b/5a24f1c7N3b9f3769.jpg!q90";
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
            if (msg.what == 1) {
                computeTime();
                if (mHour < 10) {
                    hourStr = "0" + mHour + "";
                } else {
                    hourStr = "0" + mHour + "";
                }
                if (mMin < 10) {
                    minuteStr = mMin + "";
                } else {
                    minuteStr = mMin + "";
                }
                if (mSecond < 10) {
                    secondStr = "0" + mSecond + "";
                } else {
                    secondStr = mSecond + "";
                }
            }
            tv.setText(hourStr + "小时" + minuteStr + "分钟" + secondStr + "秒");
        }
    };
    private TuiJianRelAdapter tuiJianRelAdapter;
    private RecyclerView mMsrlv;
    private SimpleDraweeView mTuyi,mTuyi1;
    private SimpleDraweeView mTuer,mTuer2;

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
        list.add("https://img14.360buyimg.com/da/jfs/t12214/350/1939719434/89490/ed985f5c/5a2e6772N36bcb9db.jpg");
        list.add("https://img14.360buyimg.com/da/jfs/t14671/46/464957194/85301/2c8dbf67/5a2dfd0aN1e13ac99.jpg");
        list.add("https://m.360buyimg.com/n0/jfs/t2068/298/2448145915/157953/7be197df/56d51a42Nd86f1c8e.jpg!q70.jpg");
        list.add("https://img14.360buyimg.com/babel/jfs/t13033/188/1703811097/88936/2777248e/5a268aeeNb3967ee2.jpg");
        list.add("https://img1.360buyimg.com/da/jfs/t15748/230/144390899/197195/5efcb669/5a28951eN6ef17ea4.jpg");
        list.add("https://img12.360buyimg.com/babel/jfs/t15268/289/474674896/195478/ec05ff81/5a2e6c87N1fd31469.jpg");
        list.add("https://img10.360buyimg.com/babel/jfs/t12217/355/1927538844/120779/dcd3879c/5a2dea77N6f4d1baf.jpg");
        //设置轮播间隔时间
       /* mBanner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        mBanner.isAutoPlay(true);*/

        lb.setImages(list).isAutoPlay(true).setDelayTime(3000).setImageLoader(new GlideImageLoader()).setBannerAnimation(Transformer.DepthPage).start();

        /**
         * 加载图片
         */
        Uri uri = Uri.parse("https://img10.360buyimg.com/babel/s780x260_jfs/t13627/123/1496791549/21938/f4714ecb/5a20cf7aNbae69933.png!q90");
        sdv2.setImageURI(uri);

        Uri uri4 = Uri.parse("https://img13.360buyimg.com/da/jfs/t3907/210/534434515/20553/eb8aefe8/58523baaNe06b870c.jpg!q90");
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
                                Log.i("AA", result);
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
         * 点击搜索框，跳转到搜索页面
         */
        selcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(getActivity(), GoodsSelectActivity.class));
            }
        });

        //秒杀倒计时
        startRun();

        // initState();
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
        tv = view.findViewById(R.id.time);
        vp = view.findViewById(R.id.view_flipper);
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
        sdv4.setOnClickListener(this);
        sdv2.setOnClickListener(this);
        /**
         * 推荐的内容
         */
        xRlv = view.findViewById(R.id.tjrlv);

        mMsrlv = (RecyclerView) view.findViewById(R.id.msrlv);


        mTuyi = (SimpleDraweeView) view.findViewById(R.id.tuyi);
        mTuer = (SimpleDraweeView) view.findViewById(R.id.tuer);
        mTuyi1 = (SimpleDraweeView) view.findViewById(R.id.tuyi1);
        mTuer2 = (SimpleDraweeView) view.findViewById(R.id.tuer2);
        Uri uri1=Uri.parse(tuyi);
        Uri uri2=Uri.parse(tuer);
        Uri uri3=Uri.parse(tuyi1);
        Uri uri4=Uri.parse(tuer2);
        mTuyi.setImageURI(uri1);
        mTuer.setImageURI(uri2);
        mTuer2.setImageURI(uri3);
        mTuyi1.setImageURI(uri4);
        mTuyi1.setOnClickListener(this);
        mTuer2.setOnClickListener(this);
    }

    /**
     * 实现列表的分类
     *
     * @param list
     */
    @Override
    public void showData(final List<ProdectBean.DataBean> list) {
        //网格布局
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        //横向滑动
        layoutManager.setOrientation(layoutManager.HORIZONTAL);
      /*  LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(layoutManager.HORIZONTAL);*/
        rlv.setLayoutManager(layoutManager);
        relAdapter = new RelAdapter(list, getContext());
        rlv.setAdapter(relAdapter);
        relAdapter.setOnItemClickLitener(new RelAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(), WebActivity.class));
            }
        });
    }

    /**
     * 为您推荐
     *
     * @param lunBoTuBean
     */
    @Override
    public void showBean(LunBoTuBean lunBoTuBean) {

        final LunBoTuBean.TuijianBean tuijian = lunBoTuBean.getTuijian();
        final List<LunBoTuBean.TuijianBean.ListBean> list = tuijian.getList();

        xRlv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        tuiJianRelAdapter = new TuiJianRelAdapter(list, getActivity());
        xRlv.setAdapter(tuiJianRelAdapter);
        xRlv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                xRlv.refreshComplete();
            }

            @Override
            public void onLoadMore() {

                for (int i = 0; i < list.size(); i++) {
                    LunBoTuBean.TuijianBean.ListBean listBean = list.get(i);
                    list1.add(listBean);
                }
                if (tuiJianRelAdapter == null) {
                    tuiJianRelAdapter = new TuiJianRelAdapter(list1, getActivity());
                    xRlv.setAdapter(tuiJianRelAdapter);
                } else {
                    tuiJianRelAdapter.notifyDataSetChanged();
                }


            }
        });
        /**
         * 展示详情页面
         */
        tuiJianRelAdapter.setOnItemClickLitener(new TuiJianRelAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                LunBoTuBean.TuijianBean.ListBean listBean = list.get(position);
                int pscid = listBean.getPscid();
                  Intent intent = new Intent(getActivity(), DangQianFenLeiGoodsActivity.class);
                  intent.putExtra("pscid", pscid + "");
                  startActivity(intent);

            }
        });

     //秒杀
        miaosha(lunBoTuBean);
    }

    /**
     * 首页的秒杀
     * @param lunBoTuBean
     */
    private void miaosha(LunBoTuBean lunBoTuBean) {
        LunBoTuBean.MiaoshaBean miaosha = lunBoTuBean.getMiaosha();
        final List<LunBoTuBean.MiaoshaBean.ListBeanX> list = miaosha.getList();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(gridLayoutManager.HORIZONTAL);
        mMsrlv.setLayoutManager(gridLayoutManager);
        MiaoShaRelAdapter miaoShaRelAdapter = new MiaoShaRelAdapter(list, getActivity());
        mMsrlv.setAdapter(miaoShaRelAdapter);
           miaoShaRelAdapter.setOnItemClickLitener(new MiaoShaRelAdapter.OnItemClickLitener() {
               @Override
               public void onItemClick(View view, int position) {
                   LunBoTuBean.MiaoshaBean.ListBeanX listBeanX = list.get(position);
                   int pscid = listBeanX.getPscid();
                   Intent intent = new Intent(getActivity(), DangQianFenLeiGoodsActivity.class);
                   intent.putExtra("pscid",pscid+"");
                   startActivity(intent);
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

    /**
     * 点击图片跳转
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sdv2:
                startActivity(new Intent(getActivity(), WebActivity.class));
                break;
            case R.id.sdv4:
                startActivity(new Intent(getActivity(), Web2Activity.class));
                break;
            case R.id.tuyi1:
                startActivity(new Intent(getActivity(), Web3Activity.class));
                break;
            case R.id.tuer2:
                startActivity(new Intent(getActivity(), Web4Activity.class));
                break;
        }
    }

    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }


}
