package example.dell.jd.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import example.dell.jd.Adapter.ListAdapter;
import example.dell.jd.Adapter.SelectRelAdapter;
import example.dell.jd.Bean.SelectGoodsBean;
import example.dell.jd.IActivity.ISelectGoods;
import example.dell.jd.R;
import example.dell.jd.persenter.SelectGoodsPersenter;

public class SelectActivity extends AppCompatActivity implements ISelectGoods {
    List<SelectGoodsBean.DataBean> list1=new ArrayList<>();
    private RecyclerView mLv;
    private int i=1;
    private SelectGoodsPersenter selectGoodsPersenter;
    private String cotent;
    private ListAdapter listAdapter;
    private EditText mSelcontent;
    private TextView  paixun,saixun;
    private int jg=0;
    private ListView lv;
    private DrawerLayout dl;
    private List<String> pblist = new ArrayList<>();//记录瀑布流
    private int n=0;
    private CheckBox img;
    private int p=0;
    private  List<SelectGoodsBean.DataBean> data=new ArrayList<>();
    private boolean flag=false;
    private SelectRelAdapter selectRelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selfragmnet);
        Intent intent = getIntent();
        cotent = intent.getStringExtra("content");

        selectGoodsPersenter = new SelectGoodsPersenter(this);
        selectGoodsPersenter.getProdect(cotent,i);
        initView();
        /**
         * 设置允许上拉加载和下拉刷新
         */
    /*   mLv.setPullRefreshEnable(true);
        mLv.setPullLoadEnable(true);*/
    }

    private void initView() {
       lv = findViewById(R.id.cllv);
        img= findViewById(R.id.iv_msg);
        dl = findViewById(R.id.dlayout);
   //   dl.openDrawer(Gravity.LEFT);//侧滑打开  不设置则不会默认打开
        mLv =  findViewById(R.id.lv);
        paixun=(TextView)findViewById(R.id.px);
        saixun=(TextView)findViewById(R.id.saixuan);
        mSelcontent = (EditText) findViewById(R.id.selcontent);//输入的内容
        mSelcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectActivity.this,GoodsSelectActivity.class));
            }
        });



    }

    @Override
    public void showData(final List<SelectGoodsBean.DataBean> list) {

        for (int j = 0; j <list.size() ; j++) {
            SelectGoodsBean.DataBean dataBean = list.get(j);
            data.add(dataBean);
        }

        img.setChecked(flag);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             if(flag){
                 adapter();
                  img.setChecked(false);
                  flag=img.isChecked();
             }else {
                 adapter1();
                 img.setChecked(true);
                 flag=img.isChecked();
             }
            }
        });

        if(img.isChecked()){
            adapter();
        }else {
            adapter1();
        }
    /*    listAdapter = new ListAdapter(list, this);
        mLv.setAdapter(listAdapter);
         mLv.setXListViewListener(new XListView.IXListViewListener() {
             @Override
             public void onRefresh() {
                 i=1;
               mLv.stopRefresh();
             }

             @Override
             public void onLoadMore() {
                    i++;
                 selectGoodsPersenter.getProdect(cotent,i);
                 if(listAdapter==null){
                     for (int j = 0; j < list.size(); j++) {
                         list1.addAll(list);
                     }

                     listAdapter =new ListAdapter(list1,SelectActivity.this);

                 }else {

                     listAdapter.notifyDataSetChanged();
                 }
              mLv.stopLoadMore();
             }

         });*/

        /**
         * 价格的排序
         */
        paixun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jg++;
            }
        });

        if(jg%2==0){
            //按价格的正序排序
            Collections.sort(list, new Comparator<SelectGoodsBean.DataBean>() {
                @Override
                public int compare(SelectGoodsBean.DataBean dataBean, SelectGoodsBean.DataBean t1) {
                    if (dataBean.getPrice() > t1.getPrice()) {
                        return 1;
                    } else if (dataBean.getPrice() < t1.getPrice()) {
                        return -1;
                    }else {
                        return 0;
                    }

                }
            });

            selectRelAdapter.notifyDataSetChanged();
        }
        else {
            //按价格的倒序排序
            Collections.sort(list, new Comparator<SelectGoodsBean.DataBean>() {
                @Override
                public int compare(SelectGoodsBean.DataBean dataBean, SelectGoodsBean.DataBean t1) {
                  if (dataBean.getPrice() < t1.getPrice()) {
                        return -1;
                    }else {
                        return 0;
                    }

                }
            });
            selectRelAdapter.notifyDataSetChanged();
        }
        /**
         * 点击筛选拉出侧拉菜单
         */
        saixun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            n++;
               // Toast.makeText(SelectActivity.this, "0", Toast.LENGTH_SHORT).show();
            }
        });
        pblist.add("笔记本");
        pblist.add("手机");
        pblist.add("干海参");
        pblist.add("游戏方向盘");
        pblist.add("门铃感应器");
        pblist.add("移动路由器");
        pblist.add("摄像头高清");
        pblist.add("不锈钢水壶");


        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return pblist.size();
            }

            @Override
            public Object getItem(int i) {
                return pblist.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                MyViewHolder holder;
                if(view==null){
                    holder=new MyViewHolder();
                    view=View.inflate(SelectActivity.this,R.layout.goodsseleitem,null);
                    holder.tv=view.findViewById(R.id.tv_goods);
                    view.setTag(holder);
                }else{
                    holder= (MyViewHolder) view.getTag();
                }
                holder.tv.setText(pblist.get(i));
                return view;
            }

            class MyViewHolder {
                TextView tv;
            }
        });
        if(n%2==0){
            Toast.makeText(this, "0", Toast.LENGTH_SHORT).show();
            dl.openDrawer(lv);

        }else{
            Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();

            dl.closeDrawer(lv);
        }
    }

    private void  adapter(){
        mLv.setLayoutManager(new LinearLayoutManager(this));
        SelectRelAdapter selectRelAdapter = new SelectRelAdapter(data, this);
        mLv.setAdapter(selectRelAdapter);
        selectRelAdapter.setOnItemClickLitener(new SelectRelAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                int pid = data.get(position).getPid();
                Intent intent = new Intent(SelectActivity.this, XQActivity.class);
                intent.putExtra("pid",pid+"");
                startActivity(intent);
            }
        });

    }
    private void  adapter1(){
        mLv.setLayoutManager(new GridLayoutManager(this,2));
        selectRelAdapter = new SelectRelAdapter(data, this);
        mLv.setAdapter(selectRelAdapter);
        selectRelAdapter.setOnItemClickLitener(new SelectRelAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                int pid = data.get(position).getPid();
                Intent intent = new Intent(SelectActivity.this, XQActivity.class);
                intent.putExtra("pid",pid+"");
                startActivity(intent);
            }
        });
    }
}
