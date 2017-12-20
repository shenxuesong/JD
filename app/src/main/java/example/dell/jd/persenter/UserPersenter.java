package example.dell.jd.persenter;

import java.io.IOException;

import example.dell.jd.Bean.UserBean;
import example.dell.jd.IActivity.IAddCart;
import example.dell.jd.IActivity.IUsers;
import example.dell.jd.model.UserModel;
import example.dell.jd.net.CGSB;

/**
 * Created by Dell on 2017/11/30.
 */

public class UserPersenter {


    private IAddCart iAddCart;
    private final UserModel userModel;
  private IUsers iUsers;

    public UserPersenter(IUsers iUsers) {
           this.iUsers=iUsers;
        userModel = new UserModel();



    }
    public void getMP(String uid){
     userModel.getUser("http://120.27.23.105/user/getUserInfo?uid=" + uid, new CGSB<UserBean>() {
         @Override
         public void chengGong(UserBean userBean) {
             iUsers.showBean(userBean);
         }

         @Override
         public void shiBai(IOException e) {

         }
     });


    }
}
