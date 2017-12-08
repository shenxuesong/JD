package example.dell.jd.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import example.dell.jd.Bean.ProdectBean;
import example.dell.jd.R;

/**
 * Created by Dell on 2017/12/3.
 */

public class RelAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProdectBean.DataBean> list;
    private Context context;

    public RelAdapter1(List<ProdectBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recitem1, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ProdectBean.DataBean dataBean = list.get(position);
        final MyViewHolder vh= (MyViewHolder) holder;

        vh.tv.setText(dataBean.getName());
        final boolean aTrue = dataBean.isTrue();
        if(aTrue==true){
            vh.ll.setBackgroundColor(Color.parseColor("#ff3660"));
        }
        vh.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mOnItemClickLitener.onItemClick(vh.itemView, position);
             dataBean.setTrue(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

         TextView tv;
         LinearLayout ll;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv= itemView.findViewById(R.id.bt);

            ll=itemView.findViewById(R.id.ln);
        }
    }

    /**
     * 自定义监听事件
     */
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
