package example.dell.jd.IActivity;

import example.dell.jd.Bean.CountPrice;
import example.dell.jd.EventBus.MessageEvent3;

/**
 * Created by Dell on 2017/12/10.
 */

public interface ICart {
    /*
      传递数量和价格
     */
    void showCountPrice(CountPrice countPrice);
    /**
     * 传递全选的boolean值
     */
    void showMessageEvent3(MessageEvent3 event3);
}
