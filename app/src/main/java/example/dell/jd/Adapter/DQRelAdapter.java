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

import example.dell.jd.Bean.DangQianGoodsBean;
import example.dell.jd.R;

/**
 * Created by Dell on 2017/12/3.
 */

public class DQRelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  List<DangQianGoodsBean.DataBean> list;
    private Context context;

    public DQRelAdapter( List<DangQianGoodsBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dqxrlitem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,  int position) {
        DangQianGoodsBean.DataBean dataBean = list.get(position);
        String s = dataBean.getImages().split("\\|")[0];
        final MyViewHolder vh= (MyViewHolder) holder;
        Uri uri=Uri.parse(s);
        vh.sdv.setImageURI(uri);
        vh.tv.setText(dataBean.getTitle()+"\n"+"京东价："+dataBean.getPrice()+"元");
        final int pid = dataBean.getPid();
        vh.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mOnItemClickLitener.onItemClick(vh.itemView,pid);
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
            tv= itemView.findViewById(R.id.dqbt);
            sdv=itemView.findViewById(R.id.dqsdv);
            ll=itemView.findViewById(R.id.dqll);
        }
    }

    /**
     * 自定义监听事件
     */
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int pid);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
