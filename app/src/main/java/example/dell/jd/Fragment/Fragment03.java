package example.dell.jd.Fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;

import example.dell.jd.Adapter.VideoAdapter;
import example.dell.jd.R;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Dell on 2017/11/30.
 */

public class Fragment03 extends Fragment {
    private String videoUrl = "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4";
    private ListView listView;
    private ArrayList<String> datas;
    private JCVideoPlayerStandard currPlayer;
    private VideoAdapter adapter;
    private AbsListView.OnScrollListener onScrollListener;
    private int firstVisible;//当前第一个可见的item
    private int visibleCount;//当前可见的item个数


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment03, container,false);
        listView = (ListView)view. findViewById(R.id.listview);
        initDatas();
        initListener();
        return view;
    }

    private void initDatas() {
        datas = new ArrayList<String>();

            datas.add(videoUrl);
            datas.add("http://pic.ibaotu.com/00/34/35/51S888piCamj.mp4_10s.mp4");
            datas.add("http://pic.ibaotu.com/00/40/10/72S888piCKhs.mp4_10s.mp4");
            datas.add("http://pic.ibaotu.com/00/35/14/27E888piCBfA.mp4_10s.mp4");
            datas.add("http://pic.ibaotu.com/00/39/57/12B888piCGDr.mp4_10s.mp4");
            datas.add("http://pic.ibaotu.com/00/39/80/37W888piCer2.mp4_10s.mp4");
            datas.add("http://pic.ibaotu.com/00/37/73/99c888piCMXs.mp4_10s.mp4");
            datas.add("http://pic.ibaotu.com/00/38/45/193888piCwt9.mp4_10s.mp4");
             datas.add("http://pic.ibaotu.com/00/34/21/317888piC5Mn.mp4_10s.mp4");
             datas.add("http://pic.ibaotu.com/00/44/96/93k888piCRAj.mp4_10s.mp4");
        adapter = new VideoAdapter(getActivity(), datas, R.layout.item_video);
        listView.setAdapter(adapter);
    }

    /**
     * 滑动监听
     */
    private void initListener() {
        onScrollListener = new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        break;

                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        //滑动停止自动播放视频
                        autoPlayVideo(view);
                        break;

                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisible == firstVisibleItem) {
                    return;
                }

                firstVisible = firstVisibleItem;
                visibleCount = visibleItemCount;
            }
        };

        listView.setOnScrollListener(onScrollListener);
    }

    /**
     * 滑动停止自动播放视频
     */
    private void autoPlayVideo(AbsListView view) {

        for (int i = 0; i < visibleCount; i++) {
            if (view != null && view.getChildAt(i) != null && view.getChildAt(i).findViewById(R.id.player_list_video) != null) {
                currPlayer = (JCVideoPlayerStandard) view.getChildAt(i).findViewById(R.id.player_list_video);
                Rect rect = new Rect();
                //获取当前view 的 位置
                currPlayer.getLocalVisibleRect(rect);
                int videoheight = currPlayer.getHeight();
                if (rect.top == 0 && rect.bottom == videoheight) {
                    if (currPlayer.currentState == JCVideoPlayer.CURRENT_STATE_NORMAL
                            || currPlayer.currentState == JCVideoPlayer.CURRENT_STATE_ERROR) {
                        currPlayer.startButton.performClick();
                    }
                    return;
                }
            }
        }
        //释放其他视频资源
        JCVideoPlayer.releaseAllVideos();
    }

 /*   @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }*/


}
