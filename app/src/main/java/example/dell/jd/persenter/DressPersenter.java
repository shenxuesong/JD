package example.dell.jd.persenter;

import java.io.IOException;

import example.dell.jd.Bean.DressBean;
import example.dell.jd.IActivity.IUserDress;
import example.dell.jd.model.DressModel;
import example.dell.jd.net.CGSB;

/**
 * Created by Dell on 2017/12/13.
 */

public class DressPersenter {
    private DressModel dressModel;
    private IUserDress iUserDress;
    public DressPersenter(IUserDress iUserDress) {
        this.iUserDress=iUserDress;
        dressModel=new DressModel();
    }
    public void getUserDress(String s){
         dressModel.getUserdrss("http://120.27.23.105/user/getAddrs?uid=" + s, new CGSB<DressBean>() {
             @Override
             public void chengGong(DressBean dressBean) {
                 DressBean.DataBean dataBean = dressBean.getData().get(2);
                 iUserDress.showDate( dataBean);
             }

             @Override
             public void shiBai(IOException e) {

             }
         });
    }
}
