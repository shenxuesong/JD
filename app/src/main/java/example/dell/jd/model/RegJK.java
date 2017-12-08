package example.dell.jd.model;

import example.dell.jd.Bean.RegBean;
import example.dell.jd.net.CGSB;

/**
 * Created by Dell on 2017/11/30.
 */

public interface RegJK {
    void getReg(String str, CGSB<RegBean> cgsb);
}
