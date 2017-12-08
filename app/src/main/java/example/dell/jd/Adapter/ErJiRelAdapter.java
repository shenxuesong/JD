package example.dell.jd.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import example.dell.jd.Bean.GoodsFenLeiBean;
import example.dell.jd.R;

/**
 * Created by Dell on 2017/12/3.
 */

public class ErJiRelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
   private   List<GoodsFenLeiBean.DataBean.ListBean> list;
    private Context context;

    public ErJiRelAdapter(  List<GoodsFenLeiBean.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.childreitme, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        GoodsFenLeiBean.DataBean.ListBean dataBean = list.get(position);

        final MyViewHolder vh= (MyViewHolder) holder;
        String icon = dataBean.getIcon();
        Uri uri=Uri.parse(icon);
        vh.sdv.setImageURI(uri);
        vh.tv.setText(dataBean.getName());

        //&#x83b7;&#x53d6;&#x5546;&#x54c1;&#x7684;pid
        final int pscid = dataBean.getPscid();
        //商品详情页


        vh.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mOnItemClickLitener.onItemClick(vh.itemView, position,pscid);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
         SimpleDraweeView sdv;
         TextView tv;
         LinearLayout ll;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv= itemView.findViewById(R.id.ejtitle);
            sdv=itemView.findViewById(R.id.sv);
            ll=itemView.findViewById(R.id.li);
        }
    }

    /**
     * 自定义监听事件
     */
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position,int i);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
