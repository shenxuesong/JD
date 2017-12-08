package example.dell.jd.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import example.dell.jd.Adapter.ListAdapter;
import example.dell.jd.Bean.SelectGoodsBean;
import example.dell.jd.EventBus.MessageEvent;
import example.dell.jd.IActivity.ISelectGoods;
import example.dell.jd.R;
import example.dell.jd.persenter.ProdectPersenter;
import example.dell.jd.persenter.SelectGoodsPersenter;
import example.dell.jd.view.MyExpanableListView;


/**
 * Created by Dell on 2017/11/30.
 */

public class SelFragment extends Fragment implements ISelectGoods {
    private View view;
    private RecyclerView mRvLeft;
    private Banner mBanner1;
    private MyExpanableListView mElv;
    private ProdectPersenter prodectPersenter;
    private List<String> list = new ArrayList<>();
    private ListView mLv;
    private SelectGoodsPersenter selectGoodsPersenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selfragmnet, container, false);
        selectGoodsPersenter = new SelectGoodsPersenter(this);


        initView(view);
        return view;
    }
@Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
public void onMessage(MessageEvent event){
        selectGoodsPersenter.getProdect(event.getName());

}

    @Override
    public void showData(List<SelectGoodsBean.DataBean> list) {
            mLv.setAdapter(new ListAdapter(list,getActivity()));
    }

    private void initView(View view) {
        mLv = (ListView) view.findViewById(R.id.lv);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}