package example.dell.jd.Adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

import example.dell.jd.R;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author: lijuan
 * @description: 适配器
 * @date: 2016-12-04
 * @time: 17:04
 */
public class VideoAdapter extends CommonAdapter<String> {
    private Context mContext;

    public VideoAdapter(Context context, List<String> datas, int layoutId) {
        super(context, layoutId, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, String url, int position) {
        JCVideoPlayerStandard player = viewHolder.getView(R.id.player_list_video);
        if (player != null) {
            player.release();
        }
        boolean setUp = player.setUp(url, JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
        if (setUp) {
            Glide.with(mContext).load("http://a4.att.hudong.com/05/71/01300000057455120185716259013.jpg").into(player.thumbImageView);
        }
    }
}
