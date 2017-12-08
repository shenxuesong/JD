package example.dell.jd.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import example.dell.jd.Bean.SelectGoodsBean;
import example.dell.jd.R;

/**
 * Created by Dell on 2017/12/3.
 */

public class ListAdapter extends BaseAdapter
{
    private List<SelectGoodsBean.DataBean> list;
    private Context context;

    public ListAdapter(List<SelectGoodsBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null) {
          view= View.inflate(context, R.layout.listitem,null);
          holder=new ViewHolder();
          holder.textView=view.findViewById(R.id.sgname);
          holder.sdv=view.findViewById(R.id.sd);
          view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        SelectGoodsBean.DataBean dataBean = list.get(i);
        //加载图片
        String images = dataBean.getImages();
        Uri uri= Uri.parse(images);
        holder.sdv.setImageURI(uri);

        String title = dataBean.getTitle();
        int price = (int) dataBean.getPrice();
        String createtime = dataBean.getCreatetime();
        holder.textView.setText("名称："+title+"\n"+"价格："+price+"\n"+createtime);

        return view;
    }
    class ViewHolder {
        TextView textView;
        SimpleDraweeView sdv;
    }
}
