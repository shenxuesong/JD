package example.dell.jd.model;

import example.dell.jd.Bean.LoginBean;
import example.dell.jd.net.CGSB;

/**
 * Created by Dell on 2017/11/30.
 */

public interface LoginJK {
    void getLogin(String str, CGSB<LoginBean> cgsb);
}
