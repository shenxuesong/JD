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

import example.dell.jd.Bean.ProdectBean;
import example.dell.jd.R;

/**
 * Created by Dell on 2017/12/3.
 */

public class RelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProdectBean.DataBean> list;
    private Context context;

    public RelAdapter(List<ProdectBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recitem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ProdectBean.DataBean dataBean = list.get(position);
        final MyViewHolder vh= (MyViewHolder) holder;
        Uri uri=Uri.parse(dataBean.getIcon());
        vh.sdv.setImageURI(uri);
        vh.tv.setText(dataBean.getName());
        vh.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mOnItemClickLitener.onItemClick(vh.itemView, position);
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
            tv= itemView.findViewById(R.id.bt);
            sdv=itemView.findViewById(R.id.sdv);
            ll=itemView.findViewById(R.id.lin);
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
