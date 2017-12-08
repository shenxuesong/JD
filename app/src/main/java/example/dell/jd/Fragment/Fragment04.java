package example.dell.jd.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import example.dell.jd.Adapter.CartEJAdapter;
import example.dell.jd.Adapter.TuiJianRelAdapter;
import example.dell.jd.Bean.CartBean;
import example.dell.jd.Bean.CountPrice;
import example.dell.jd.Bean.LunBoTuBean;
import example.dell.jd.Bean.ProdectBean;
import example.dell.jd.EventBus.MessageEvent1;
import example.dell.jd.EventBus.MessageEvent3;
import example.dell.jd.IActivity.IAddCart;
import example.dell.jd.IActivity.IProdect1;
import example.dell.jd.R;
import example.dell.jd.persenter.CartPersenter;
import example.dell.jd.persenter.ProdectPersenter1;
import example.dell.jd.view.MyExpanableListView;

/**
 * Created by Dell on 2017/11/30.
 */

public class Fragment04 extends Fragment implements IAddCart<CartBean>,IProdect1 {

    private String uid = "";
    private List<CartBean.DataBean> grouplist = new ArrayList<>();
    private List<List<CartBean.DataBean.ListBean>> childlist = new ArrayList<>();
    private MyExpanableListView elv;
    private CartPersenter cartPersenter;
    private View view;
    private MyExpanableListView mCartlistview;
    private CheckBox mQuanxuan;
    /**
     * 总价：0.0
     */
    private TextView mZongjia;
    /**
     * 共0件商品
     */
    private TextView mTvCount;
    private CartEJAdapter cartEJAdapter;
    private XRecyclerView  xRlv;
    private TuiJianRelAdapter tuiJianRelAdapter;
    private List<LunBoTuBean.TuijianBean.ListBean> list;
    private List<LunBoTuBean.TuijianBean.ListBean> list1=new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        new ProdectPersenter1(this).tuiJian();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment04, container, false);


        cartPersenter = new CartPersenter(this);
        elv = view.findViewById(R.id.cartlistview);


        //判断是否登陆，登陆完成就可以拿到uid的值
        if (uid == null | uid == "") {
            Toast.makeText(getActivity(), "请先登陆/注册", Toast.LENGTH_SHORT).show();

        } else {
            //uid不为空时，请求数据
            cartPersenter.getMP(uid);
        }
        initView(view);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMess(MessageEvent1 event) {
        uid = event.getUid();
        Log.i("CARTUID", uid);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showData(CartBean cartBean) {
        List<CartBean.DataBean> list = cartBean.getData();
        for (int i = 0; i < list.size(); i++) {
            CartBean.DataBean dataBean = list.get(i);
            List<CartBean.DataBean.ListBean> list1 = dataBean.getList();
            childlist.add(list1);

        }


        cartEJAdapter = new CartEJAdapter(list, childlist, getActivity());
        elv.setAdapter(cartEJAdapter);
        elv.setGroupIndicator(null);
        for (int i = 0; i < list.size(); i++) {
            elv.expandGroup(i);
        }
    }
    @de.greenrobot.event.Subscribe
    public void onMessga(MessageEvent3 event){
        //给全选设值
        mQuanxuan.setChecked(event.isCheck());
    }
    @de.greenrobot.event.Subscribe
    public void onMessga1(CountPrice cp){
        //给价格和数量设置
        mZongjia.setText("总价："+cp.getPrice());

    }
    private void initView(View view) {
        mCartlistview = (MyExpanableListView) view.findViewById(R.id.cartlistview);
        mQuanxuan = (CheckBox) view.findViewById(R.id.quanxuan);
        mZongjia = (TextView) view.findViewById(R.id.zongjia);
        mTvCount = (TextView) view.findViewById(R.id.tv_count);
        xRlv = view.findViewById(R.id.rrrlv);
    }

    @Override
    public void showData(List<ProdectBean.DataBean> list) {

    }

    @Override
    public void showBean(LunBoTuBean lunBoTuBean) {
        list = lunBoTuBean.getTuijian().getList();


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
                for (int i = 0; i < list.size() ; i++) {
                    LunBoTuBean.TuijianBean.ListBean listBean = list.get(i);
                    list1.add(listBean);
                }
                tuiJianRelAdapter.notifyDataSetChanged();



            }
        });
    }
}
