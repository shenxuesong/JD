package example.dell.jd.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.umeng.socialize.utils.Log;

import java.util.ArrayList;
import java.util.List;

import example.dell.jd.Adapter.DQRelAdapter;
import example.dell.jd.Bean.DangQianGoodsBean;
import example.dell.jd.IActivity.IDangQianActivity;
import example.dell.jd.R;
import example.dell.jd.persenter.DangQianPersenter;

public class DangQianFenLeiGoodsActivity extends AppCompatActivity implements IDangQianActivity{

    private DangQianPersenter dangQianPersenter;
    private int w=1;
    private XRecyclerView xRlv;
    private String pscid;
    private  List<DangQianGoodsBean.DataBean> data=new ArrayList<>();
    private List<DangQianGoodsBean.DataBean> list;
    private DQRelAdapter dqRelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_qian_fen_lei_goods);
        dangQianPersenter = new DangQianPersenter(this);
        Intent intent = getIntent();
        pscid = intent.getStringExtra("pscid");
        String s = String.valueOf(w);
        dangQianPersenter.getMP(pscid,s);
        xRlv = findViewById(R.id.showrlv);


    }

    @Override
    public void ShowBean(DangQianGoodsBean dangQianGoodsBean) {
        list = dangQianGoodsBean.getData();
        Log.i("Goods", list.size()+"");
      xRlv.setLayoutManager(new LinearLayoutManager(this));
        dqRelAdapter = new DQRelAdapter(list, this);
        xRlv.setAdapter(dqRelAdapter);
        dqRelAdapter.setOnItemClickLitener(new DQRelAdapter.OnItemClickLitener() {
          @Override
          public void onItemClick(View view, int pid) {
              Intent intent = new Intent(DangQianFenLeiGoodsActivity.this, XQActivity.class);
              intent.putExtra("pid",pid+"");
              startActivity(intent);
          }
      });

      /*
          刷新，加载
       */
      xRlv.setLoadingListener(new XRecyclerView.LoadingListener() {
          //刷新
          @Override
          public void onRefresh() {
              xRlv.refreshComplete();
          }
          //加载更多
          @Override
          public void onLoadMore() {
              w++;
              String s = String.valueOf(w);
              dangQianPersenter.getMP(pscid,s);
              for (int i = 0; i <list.size() ; i++) {
                  data.add(list.get(i));
              }
              if (dqRelAdapter==null){

                  dqRelAdapter=new DQRelAdapter(data,DangQianFenLeiGoodsActivity.this);
                  xRlv.setAdapter(dqRelAdapter);
              }else {
                  dqRelAdapter.notifyDataSetChanged();
              }
              xRlv.loadMoreComplete();
          }

      });
    }


}
