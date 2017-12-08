package example.dell.jd.model;

import example.dell.jd.Bean.GoodsFenLeiBean;
import example.dell.jd.net.CGSB;

/**
 * Created by Dell on 2017/11/30.
 */

public interface GoodsFenLeiJK {
    void getGoods(String str, CGSB<GoodsFenLeiBean> cgsb);
}
