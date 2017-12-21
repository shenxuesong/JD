package example.dell.jd.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import example.dell.jd.Bean.CartBean;
import example.dell.jd.Bean.CountPrice;
import example.dell.jd.EventBus.MessageEvent3;
import example.dell.jd.IActivity.ICart;
import example.dell.jd.R;

/**
 * Created by Dell on 2017/12/6.
 */

public class CartEJAdapter extends BaseExpandableListAdapter {
    private List<CartBean.DataBean> grouplist;
    private List<List<CartBean.DataBean.ListBean>> childlist;
    private Context context;
    private ICart iCart;
    public CartEJAdapter(List<CartBean.DataBean> grouplist, List<List<CartBean.DataBean.ListBean>> childlist, Context context,ICart iCart) {
        this.grouplist = grouplist;
        this.childlist = childlist;
        this.context = context;
        this.iCart=iCart;
    }

    @Override
    public int getGroupCount() {
        return grouplist.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childlist.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return grouplist.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childlist.get(i).get(i1);
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

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        final ViewHolder3 holder3;
          if(view==null){
          view=View.inflate(context, R.layout.cartyjlist,null);
          holder3=new ViewHolder3();
           holder3.tv=view.findViewById(R.id.cartyj);
              holder3.group_cb = (CheckBox) view.findViewById(R.id.gx_group);
          view.setTag(holder3);
          }else {
              holder3= (ViewHolder3) view.getTag();
          }
        final CartBean.DataBean dataBean = grouplist.get(i);
        String sellerName = dataBean.getSellerName();
        Log.i("SS",sellerName);
        holder3.tv.setText(sellerName);
        holder3.group_cb.setChecked(dataBean.isCheck());

        holder3.group_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBean.setCheck(holder3.group_cb.isChecked());
                //改变所有孩子的状态
                changeChildState(i,holder3.group_cb.isChecked());
                //计算价格和数量
                  iCart.showCountPrice(jS());
                //通过判断一级的checkbox判断全选的状态
                changeMianQXstatus(checkGroupAll());
                notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        final ViewHolder4 viewHolder4;
        if(view==null){
            view=View.inflate(context,R.layout.carterjilist,null);
                   viewHolder4=new ViewHolder4();

                         viewHolder4.tv= view.findViewById(R.id.titleNAME);
                         viewHolder4.tvprice=view.findViewById(R.id.yanse);
                         viewHolder4.sdv=view.findViewById(R.id.title_img);
                         viewHolder4.child_cb= view.findViewById(R.id.gouxuan_child);   //子勾选
                         viewHolder4.tvnum= view.findViewById(R.id.tv_num);           //数量
                         viewHolder4.jia= view.findViewById(R.id.jia );            //加号
                         viewHolder4.jian= view.findViewById(R.id.jian );              //减号
                         viewHolder4.del= view.findViewById(R.id.del );

                        view.setTag(viewHolder4);

        }else {
            viewHolder4= (ViewHolder4) view.getTag();
        }
        final CartBean.DataBean.ListBean listBean = childlist.get(i).get(i1);

        int bargainPrice = (int) listBean.getPrice();
        String images = listBean.getImages();
        String s = images.split("\\|")[0];
        Uri uri=Uri.parse(s);
        viewHolder4.sdv.setImageURI(uri);
        viewHolder4.tvprice.setText("¥："+bargainPrice+"元");
        viewHolder4.child_cb.setChecked(listBean.isCheck());
        //viewHolder4.tvnum.setText(listBean.getNum());
        viewHolder4.tv.setText(listBean.getTitle());
     //     listBean.setCou(Integer.parseInt(viewHolder4.tvnum.getText().toString()));
        //给子条目的checkBox点击事件
       viewHolder4.child_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean.setCheck(viewHolder4.child_cb.isChecked());
                 //计算已经勾选的价格
                // EventBus.getDefault().postSticky(jS());
                  iCart.showCountPrice(jS());
                //如果二级的checkBox选中，就让一级的checkBox勾选
                if(viewHolder4.child_cb.isChecked()) {

                    if (ischeckAllchild(i)) {
                        //改变一级checkBox的状态
                        changeGroupstatus(i,true);
                        //通过判断一级的CheckBox的状态来改变全选的状态
                        changeMianQXstatus(checkGroupAll());

                    }
                }else{
                    changeGroupstatus(i,false);
                    changeMianQXstatus(checkGroupAll());
                }
                notifyDataSetChanged();
              //  Toast.makeText(context, i+"", Toast.LENGTH_SHORT).show();
            }

        });
        //点击加加的功能
        viewHolder4.jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cou = listBean.getCou();
                viewHolder4.tvnum.setText(++cou+"");
                listBean.setCou(cou);
                if(viewHolder4.child_cb.isChecked()){
                //    EventBus.getDefault().post(jS());
                    iCart.showCountPrice(jS());
                }

            }
        });
        //点击减减的功能
        viewHolder4.jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cou = listBean.getCou();

                if(cou==1) {
                    Toast.makeText(context, "这已经是最小数量了", Toast.LENGTH_SHORT).show();
                    return;
                }
               viewHolder4.tvnum.setText(--cou+"");
                listBean.setCou(cou);
                if (viewHolder4.child_cb.isChecked()) {
                  //  EventBus.getDefault().post(jS());
                    iCart.showCountPrice(jS());
                }
            }
        });
        //点击删除
       viewHolder4.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CartBean.DataBean.ListBean> listBeans = childlist.get(i);
                CartBean.DataBean.ListBean listBean1 = listBeans.remove(i1);
                if(listBeans.size()==0){
                    childlist.remove(i1);
                    grouplist.remove(i);
                }
          //      EventBus.getDefault().post(jS());
                iCart.showCountPrice(jS());
                notifyDataSetChanged();

            }
        });



        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    class ViewHolder3{
       TextView tv;
       CheckBox group_cb;
    }
    class ViewHolder4 {
       TextView tv,tvprice,tvnum;
       SimpleDraweeView sdv;
       CheckBox child_cb;
       ImageView jia,jian,del;
    }
    /**
     * 判断全部的二级是CheckBox否选中
     *
     */
    private boolean ischeckAllchild(int g){
        List<CartBean.DataBean.ListBean> listBeen = childlist.get(g);
        for (int i = 0; i <listBeen.size() ; i++) {
            CartBean.DataBean.ListBean listBean = listBeen.get(i);
            if(!listBean.isCheck()){
                return false;
            }
        }
        return true;
    }

    /**
     * 计算数量和价格
     * 通过遍历一级的得到二级的CheckBox，如果是选择的选中状态的话，就统计它们的总计的数量和价格
     */
    private CountPrice jS(){
        int cou=0;
        int jiage=0;
        for (int i = 0; i <grouplist.size() ; i++) {
            for (int j = 0; j <childlist.get(i).size() ; j++) {
                CartBean.DataBean.ListBean listBean = childlist.get(i).get(j);
                if(listBean.isCheck()){
                    cou+=listBean.getCou();
                    jiage+=(int)listBean.getPrice()*listBean.getCou();
                }

            }

        }
        CountPrice countAndPrice = new CountPrice();
        countAndPrice.setCount(cou);
        countAndPrice.setPrice(jiage);
        return countAndPrice;
    }
    /**
     * 改变一级列表的状态
     */
    private void changeGroupstatus(int groupPosition,boolean flag ){
        CartBean.DataBean dataBean = grouplist.get(groupPosition);
        dataBean.setCheck(flag);
    }
    /**
     * 改变全选的状态
     *
     */
    private void changeMianQXstatus(boolean flag){
        MessageEvent3 messageEvent = new MessageEvent3(flag);

           iCart.showMessageEvent3(messageEvent);
     //   EventBus.getDefault().post(messageEvent);
    }
    /**
     * 判断一级的是否全部选中
     */
    private boolean checkGroupAll(){
        for (int i = 0; i < grouplist.size(); i++) {
          CartBean.DataBean dataBean = grouplist.get(i);
            if(!dataBean.isCheck()){
                return false;
            }
        }
        return true;
    }
    /**
     * 改变所有二级的状态
     */
    private void changeChildState(int grouPistion,boolean flag){
        List<CartBean.DataBean.ListBean> listBeen = childlist.get(grouPistion);
            for (int i = 0; i < listBeen.size(); i++) {
                listBeen.get(i).setCheck(flag);
        }
    }
    /**
     * 改变全选的
     */
    public void qx(boolean flag){
        for (int i = 0; i < grouplist.size(); i++) {
            changeGroupstatus(i, flag);
            changeChildState(i, flag);
        }
     //   EventBus.getDefault().post(jS());
        iCart.showCountPrice(jS());
        notifyDataSetChanged();
    }
}
