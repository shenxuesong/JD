package example.dell.jd.persenter;

import java.io.IOException;

import example.dell.jd.Bean.LoginBean;
import example.dell.jd.Bean.RegBean;
import example.dell.jd.IActivity.RegActivity;
import example.dell.jd.model.LoginModel;
import example.dell.jd.model.RegModel;
import example.dell.jd.net.API;
import example.dell.jd.net.CGSB;

/**
 * Created by Dell on 2017/11/30.
 */

public class RegPersenter {

    private final RegModel regModel;
   private RegActivity regActivity;
    private final LoginModel loginModel;

    public RegPersenter( RegActivity regActivity) {
        this.regActivity=regActivity;
        regModel = new RegModel();
        loginModel = new LoginModel();

    }
    public void getMP(String hm,String mm){
        regModel.getReg(API.zc + "?mobile=" + hm  +"&password=" + mm, new CGSB<RegBean>() {
            @Override
            public void chengGong(RegBean regBean) {
                regActivity.ShowBean(regBean.getMsg());
            }

            @Override
            public void shiBai(IOException e) {

            }
        });
    }
    public void getLogin(String hm,String mm) {

            loginModel.getLogin(API.login + "?mobile=" + hm + "&password=" + mm, new CGSB<LoginBean>() {
                @Override
                public void chengGong(LoginBean loginBean) {
                    LoginBean.DataBean data = loginBean.getData();
                    regActivity.ShowStr( loginBean.getMsg(),data);
                }

                @Override
                public void shiBai(IOException e) {

                }
            });

    }
}
