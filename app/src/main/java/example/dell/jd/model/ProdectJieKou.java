package example.dell.jd.model;

import example.dell.jd.Bean.ProdectBean;
import example.dell.jd.net.CGSB;

/**
 * Created by Dell on 2017/12/2.
 */

public interface ProdectJieKou {
    public void getProdect(String url,CGSB<ProdectBean> cgsb);
}
