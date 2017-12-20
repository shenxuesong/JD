package example.dell.jd.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import example.dell.jd.Adapter.ErJiAdapter;
import example.dell.jd.Adapter.RelAdapter1;
import example.dell.jd.Bean.GoodsFenLeiBean;
import example.dell.jd.Bean.ProdectBean;
import example.dell.jd.IActivity.IProdect;
import example.dell.jd.R;
import example.dell.jd.persenter.ProdectPersenter;
import example.dell.jd.view.MyExpanableListView;
import example.dell.jd.ydyBanner.GlideImageLoader;


/**
 * Created by Dell on 2017/11/30.
 */

public class Fragment02 extends Fragment implements IProdect{
    private View view;
    private RecyclerView mRvLeft;
    private Banner mBanner1;
    private MyExpanableListView mElv;
    private ProdectPersenter prodectPersenter;
    private    List<String> list=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment02, container, false);
        initView(view);
        prodectPersenter = new ProdectPersenter(this);

           prodectPersenter.getProdect();
           prodectPersenter.getGood("1");
        //轮播的方法
        playauto();


        return view;
    }

    private void  playauto() {

        list.add("https://img14.360buyimg.com/da/s386x260_jfs/t11557/108/1694383202/26652/af70899d/5a06a140N398122fd.jpg!q90");
        list.add("https://img10.360buyimg.com/da/s386x260_jfs/t5641/221/8330511649/44900/b9c53b8d/597944aeNd644dbd8.jpg!q90");
        list.add("https://img20.360buyimg.com/da/s386x260_jfs/t3067/166/4805041472/57028/d78eeb8e/58578e88Nf8df4b48.jpg!q90");
        list.add("https://img13.360buyimg.com/babel/s386x520_jfs/t11539/176/2118107629/67529/34d59f1f/5a138438N19d98566.jpg!q90");
        list.add("https://img11.360buyimg.com/babel/jfs/t13813/43/1412941980/95046/899a3f7/5a1f9807N0b322a60.jpg");
        list.add("https://img12.360buyimg.com/da/jfs/t5182/69/462910155/142359/66551a05/58ffffd8N892ce4a8.jpg");
        list.add("https://img10.360buyimg.com/babel/jfs/t7981/79/4315744338/149072/35fa3f77/5a1f68c9N92ce9f5b.jpg");
        list.add("https://img14.360buyimg.com/cms/jfs/t11845/54/2485618341/294752/2d728b02/5a1802a8Na0dfe4ea.jpg");
        mBanner1.setImages(list).isAutoPlay(true).setDelayTime(3000).setImageLoader(new GlideImageLoader()).setBannerAnimation(Transformer.DepthPage).start();
    }

    private void initView(View view) {
        /**
         * 分类的列表
         */
        mRvLeft = (RecyclerView) view.findViewById(R.id.lv_left);
        mBanner1 = (Banner) view.findViewById(R.id.banner1);
        /**
         * 二级的分类的列表
         */
        mElv = (MyExpanableListView) view.findViewById(R.id.elv);
    }

    @Override
    public void showData(final List<ProdectBean.DataBean> list) {
        mRvLeft.setLayoutManager(new LinearLayoutManager(getActivity()));
        RelAdapter1 relAdapter = new RelAdapter1(list, getActivity());
        mRvLeft.setAdapter(relAdapter);
         relAdapter.setOnItemClickLitener(new RelAdapter1.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                int cid = list.get(position).getCid();
              //  Toast.makeText(getContext(),cid+"", Toast.LENGTH_SHORT).show();
                //把点击的cid 传给二级的分类
               prodectPersenter.getGood(cid+"");
            }
        });
    }

    /**
     * 二级的商品分类
     * @param grouplist
     * @param chilist
     */
    @Override
    public void showList(List<GoodsFenLeiBean.DataBean> grouplist, List<List<GoodsFenLeiBean.DataBean.ListBean>> chilist) {
        ErJiAdapter erJiAdapter = new ErJiAdapter(grouplist, chilist, getActivity());
        mElv.setAdapter(erJiAdapter);
        mElv.setGroupIndicator(null);
        for (int i = 0; i <grouplist.size() ; i++) {
            mElv.expandGroup(i);
        }


    }


}
