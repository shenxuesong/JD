package example.dell.jd.model;

import example.dell.jd.Bean.SelectGoodsBean;
import example.dell.jd.net.CGSB;

/**
 * Created by Dell on 2017/12/2.
 */

public interface SelectGoodsJieKou {
    public void getProdect(String url, CGSB<SelectGoodsBean> cgsb);
}
