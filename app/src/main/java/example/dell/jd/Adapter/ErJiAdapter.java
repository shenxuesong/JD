package example.dell.jd.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import example.dell.jd.Bean.GoodsFenLeiBean;
import example.dell.jd.R;
import example.dell.jd.view.DangQianFenLeiGoodsActivity;


/**
 * 二级列表的适配器
 * Created by Dell on 2017/12/3.
 */

public class ErJiAdapter extends BaseExpandableListAdapter {
    java.util.List<GoodsFenLeiBean.DataBean> grouplist;
    List<List<GoodsFenLeiBean.DataBean.ListBean>> chilist;
        Context context;

    public ErJiAdapter(List<GoodsFenLeiBean.DataBean> grouplist, List<List<GoodsFenLeiBean.DataBean.ListBean>> chilist, Context context) {
        this.grouplist = grouplist;
        this.chilist = chilist;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return grouplist.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return grouplist.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return chilist.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 一级的列表
     * @param i
     * @param b
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder1 holder1;
        if(view==null){
            view=View.inflate(context, R.layout.yjitem,null);
            holder1=new ViewHolder1();
              holder1.tv=view.findViewById(R.id.yjtitle);
            view.setTag(holder1);
        }else{
            holder1= (ViewHolder1) view.getTag();
        }
        String name = grouplist.get(i).getName();
        holder1.tv.setText(name);
        return view;
    }

    /**
     * 二级的列表
     * @param i
     * @param i1
     * @param b
     * @param view
     * @param viewGroup
     * @return
     */
    @SuppressLint("WrongViewCast")
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder2 holder2;
        if(view==null){
            view=View.inflate(context,R.layout.childitem,null);
            holder2=new ViewHolder2();
                holder2.rlv=view.findViewById(R.id.rgv);

            view.setTag(holder2);
        }else{
            holder2= (ViewHolder2) view.getTag();
        }

        final List<GoodsFenLeiBean.DataBean.ListBean> listBeans = chilist.get(i);
        holder2.rlv.setLayoutManager(new GridLayoutManager(context,3));

        ErJiRelAdapter erJiRelAdapter = new ErJiRelAdapter(listBeans, context);
        holder2.rlv.setAdapter(erJiRelAdapter);
       //给二级的子条目一个点击事件
        erJiRelAdapter.setOnItemClickLitener(new ErJiRelAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position,int a) {
              //  Toast.makeText(context, a+"", Toast.LENGTH_SHORT).show();
                 //给详情页传值
             //   EventBus.getDefault().postSticky(new MessageEvent1(a));
                //跳转到详情页
                Intent intent = new Intent(context, DangQianFenLeiGoodsActivity.class);
                intent.putExtra("pscid",a+"");
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    class ViewHolder1{
        TextView tv;
    }
    class ViewHolder2{
        RecyclerView rlv;

    }


}
