package example.dell.jd.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import example.dell.jd.R;

/**
 * Created by Dell on 2017/12/16.
 */

public class GoodsSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> list;
    private Context context;

    public GoodsSelectAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.goodsseleitem, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        String s = list.get(position);
        MyViewHolder vh= (MyViewHolder) holder;
        vh.tv.setText(s);
        vh.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.OnItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
              LinearLayout ll;
              TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
               ll=itemView.findViewById(R.id.goodssel_ll);
               tv=itemView.findViewById(R.id.tv_goods);
        }
    }
    public interface OnItemClickListener{
        void  OnItemClick(int positon);
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClick(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}
