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

import example.dell.jd.Bean.LunBoTuBean;
import example.dell.jd.R;

/**
 * Created by Dell on 2017/12/3.
 */

public class MiaoShaRelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<LunBoTuBean.MiaoshaBean.ListBeanX> list;
    private Context context;

    public MiaoShaRelAdapter(List<LunBoTuBean.MiaoshaBean.ListBeanX> list, Context context) {
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
        LunBoTuBean.MiaoshaBean.ListBeanX listBeanX = list.get(position);
        final MyViewHolder vh= (MyViewHolder) holder;
        String s = listBeanX.getImages().split("\\|")[0];
        Uri uri=Uri.parse(s);
        vh.sdv.setImageURI(uri);
        vh.tv.setText("￥："+listBeanX.getPrice()+"元");
      //  vh.tv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); //中间横线
        vh.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mOnItemClickLitener.onItemClick(vh.itemView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
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
