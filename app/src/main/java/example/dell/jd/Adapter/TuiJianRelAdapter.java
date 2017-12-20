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

public class TuiJianRelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private   List<LunBoTuBean.TuijianBean.ListBean> list ;
    private Context context;

    public TuiJianRelAdapter(  List<LunBoTuBean.TuijianBean.ListBean> list , Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tuijianrecitem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        LunBoTuBean.TuijianBean.ListBean listBean = list.get(position);


        final MyViewHolder vh= (MyViewHolder) holder;
        String images = listBean.getImages();
        String s = images.split("\\|")[0];
        Uri uri=Uri.parse(s);
        vh.sdv.setImageURI(uri);
        String title = listBean.getTitle();
        double price = listBean.getPrice();

        vh.tv.setText(title);
        vh.tvprice.setText("￥："+price);
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
         TextView tv,tvprice;
         LinearLayout ll;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv= itemView.findViewById(R.id.tuibt);
            tvprice= itemView.findViewById(R.id.tuiprice);
            sdv=itemView.findViewById(R.id.tuisdv);
            ll=itemView.findViewById(R.id.tuilin);
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
