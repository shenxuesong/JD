package example.dell.jd.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import example.dell.jd.IActivity.ICart;
import example.dell.jd.IActivity.IProdect1;
import example.dell.jd.R;
import example.dell.jd.persenter.CartPersenter;
import example.dell.jd.persenter.ProdectPersenter1;
import example.dell.jd.view.DressActivity;
import example.dell.jd.view.MyExpanableListView;
import example.dell.jd.view.XQActivity;

/**
 * 添加购物的接口、
 * Created by Dell on 2017/11/30.
 */

public class Fragment04 extends Fragment implements IAddCart<CartBean>,IProdect1,ICart {

    private String uid ;

    private List<List<CartBean.DataBean.ListBean>> childlist = new ArrayList<>();
    private MyExpanableListView elv;
    private CartPersenter cartPersenter;
    private View view;
    private MyExpanableListView mCartlistview;
    private CheckBox mQuanxuan;
    private Button JieSuan;
    /**
     * 总价：0.0
     */
    private TextView mZongjia;
    /**
     * 共0件商品
     */
    private TextView mTvCount;
    private CartEJAdapter cartEJAdapter;
    private XRecyclerView xRlv;
    private TuiJianRelAdapter tuiJianRelAdapter;
    private List<LunBoTuBean.TuijianBean.ListBean> list;
    private List<LunBoTuBean.TuijianBean.ListBean> list1=new ArrayList<>();
    private CartBean.DataBean dataBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment04, container, false);
        cartPersenter = new CartPersenter(this);
        elv = view.findViewById(R.id.cartlistview);

        //请求加载购物车下的为您推荐
        new ProdectPersenter1(this).tuiJian();
        initView(view);
        //判断是否登陆，登陆完成就可以拿到uid的值
        if (uid == null | uid == "") {
            Toast.makeText(getActivity(), "请先登陆/注册", Toast.LENGTH_SHORT).show();

        }else {

            //uid不为空时，请求数据
            cartPersenter.getCart(uid);
            Log.i("Gu",uid);
        }

        mQuanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartEJAdapter.qx(mQuanxuan.isChecked());
                cartEJAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }


    /**
     * 二级的购物车
     * @param cartBean
     */

    @Override
    public void showData(CartBean cartBean) {
        List<CartBean.DataBean> list = cartBean.getData();
        for (int i = 0; i < list.size(); i++) {
            dataBean = list.get(i);
            List<CartBean.DataBean.ListBean> list1 = dataBean.getList();
            childlist.add(list1);

        }

        cartEJAdapter = new CartEJAdapter(list, childlist, getActivity(),this);
        elv.setAdapter(cartEJAdapter);
        elv.setGroupIndicator(null);
        for (int i = 0; i < list.size(); i++) {
            elv.expandGroup(i);
        }

    }

    private void initView(View view) {
        mCartlistview = view.findViewById(R.id.cartlistview);
        mQuanxuan =  view.findViewById(R.id.quanxuan);
        mZongjia = view.findViewById(R.id.zongjia);
        mTvCount = view.findViewById(R.id.tv_count);
        xRlv = view.findViewById(R.id.rrrlv);
        JieSuan=  view.findViewById(R.id.jiesuan);
        JieSuan.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if(uid!=null|uid!=""){
                      Intent intent = new Intent(getActivity(), DressActivity.class);
                      intent.putExtra("uid",uid);
                  /*   if(dataBean.isCheck()){
                         intent.putExtra("dataBean",dataBean);
                     }*/

                      startActivity(intent);
                      return;
                  }else {
                      Toast.makeText(getActivity().getApplicationContext(), "请登录", Toast.LENGTH_SHORT).show();

                  }

              }
          });

}

    @Override
    public void showData(List<ProdectBean.DataBean> list) {

    }

    /**
     * 为您推荐的内容
     * @param lunBoTuBean
     */
    @Override
    public void showBean(LunBoTuBean lunBoTuBean) {
        list = lunBoTuBean.getTuijian().getList();


        xRlv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        tuiJianRelAdapter = new TuiJianRelAdapter(list, getActivity());

        xRlv.setAdapter(tuiJianRelAdapter);
        tuiJianRelAdapter.setOnItemClickLitener(new TuiJianRelAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                LunBoTuBean.TuijianBean.ListBean listBean = list.get(position);
                int pscid = listBean.getPscid();
                Intent intent = new Intent(getActivity(), XQActivity.class);
                intent.putExtra("pscid",pscid+"");
                startActivity(intent);
            }
        });

    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEvent(MessageEvent1 event){
        uid= event.getUid();

        Log.i("GWC",uid);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       EventBus.getDefault().unregister(this);
    }

    @Override
    public void showCountPrice(CountPrice cp) {
        //给价格和数量设置
        mZongjia.setText("总价："+cp.getPrice());
       // Log.i("PRICE",cp.getPrice()+"");
        mTvCount.setText("共（"+cp.getCount()+")件商品");

        cartEJAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessageEvent3(MessageEvent3 event3) {
        //给全选设值
        mQuanxuan.setChecked(event3.isCheck());

        cartEJAdapter.notifyDataSetChanged();
    }
}
