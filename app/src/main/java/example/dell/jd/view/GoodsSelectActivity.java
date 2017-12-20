package example.dell.jd.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.dell.jd.Adapter.GoodsSelectAdapter;
import example.dell.jd.R;

public class GoodsSelectActivity extends AppCompatActivity  {
    private List<String> pblist = new ArrayList<>();//记录瀑布流
    private List<String> list = new ArrayList<>();
    private ImageView mIvSao;
    /**
     * 都市丽人领劵满399减200
     */
    private EditText mSelcontent;
    private ImageView mSelBut;
    private ImageView mIvMsg;
    private int i=1;
    /**
     * 信息
     */
    private TextView mTvMsg;
    private RecyclerView mBpRlv;
    private RecyclerView mListRlv;
   private TextView del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_select);



        initView();
        list.add("内衣");

        //展示热搜的方法
        showPBL();

        //展示搜索历史记录的方法
        showList();
    }

    private void initView() {
        mIvSao = (ImageView) findViewById(R.id.iv_sao);  //二维码扫描
        mSelcontent = (EditText) findViewById(R.id.selcontent);//输入的内容
        mSelBut = (ImageView) findViewById(R.id.selBut);        //搜索的按钮

        del=(TextView)findViewById(R.id.clear);
        mBpRlv = (RecyclerView) findViewById(R.id.bpRlv);
        mListRlv = (RecyclerView) findViewById(R.id.listRlv);
    }

    /**
     * 设置瀑布流
     */
     private void showPBL(){
         //造数据
         pblist.add("笔记本");
         pblist.add("手机");
         pblist.add("干海参");
         pblist.add("游戏方向盘");
         pblist.add("门铃感应器");
         pblist.add("移动路由器");
         pblist.add("摄像头高清");
         pblist.add("不锈钢水壶");
         //瀑布流
         StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
                                 staggeredGridLayoutManager.setOrientation(staggeredGridLayoutManager.HORIZONTAL);
         mBpRlv.setLayoutManager(staggeredGridLayoutManager);
         GoodsSelectAdapter goodsSelectAdapter = new GoodsSelectAdapter(pblist,this);
         mBpRlv.setAdapter(goodsSelectAdapter);
         goodsSelectAdapter.setOnItemClick(new GoodsSelectAdapter.OnItemClickListener() {
             @Override
             public void OnItemClick(int positon) {
                 String s = pblist.get(positon);
                 list.add(s);
                 //搜索此内容
                 //跳转到selectActivity
                 Intent intent = new Intent(GoodsSelectActivity.this, SelectActivity.class);
                 intent.putExtra("content",s);
                 startActivity(intent);

             }
         });
     }
    /**
     * 展示搜索历史记录的方法
     */
    private void showList(){
        mSelBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = mSelcontent.getText().toString();
                list.add(s);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GoodsSelectActivity.this);
                mListRlv.setLayoutManager(linearLayoutManager);
                GoodsSelectAdapter goodsSelectAdapter= new GoodsSelectAdapter(list,GoodsSelectActivity.this);
                mListRlv.setAdapter(goodsSelectAdapter);

                goodsSelectAdapter.notifyDataSetChanged();

                //跳转到selectActivity
                Intent intent = new Intent(GoodsSelectActivity.this, SelectActivity.class);
                intent.putExtra("content",s);
                startActivity(intent);
            }

        });

        //点击清除内容
        del.setOnClickListener(new View.OnClickListener() {

            private AlertDialog dialog;

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GoodsSelectActivity.this)

                        .setMessage("确定清除搜索历史吗？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(list.size()!=0){
                                    list.clear();
                                }

                                 dialog.dismiss();
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss();
                            }
                        });

                dialog = builder.create();
                dialog.show();

            }
        });

    }
}
