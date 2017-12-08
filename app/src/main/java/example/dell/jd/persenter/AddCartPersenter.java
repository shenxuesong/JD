package example.dell.jd.persenter;

import java.io.IOException;

import example.dell.jd.Bean.AddCart;
import example.dell.jd.IActivity.IAddCart;
import example.dell.jd.model.AddCartModel;
import example.dell.jd.net.CGSB;

/**
 * Created by Dell on 2017/11/30.
 */

public class AddCartPersenter {

    private final AddCartModel addCartModel;
    private IAddCart iAddCart;


    public AddCartPersenter( IAddCart iAddCart) {
           this.iAddCart=iAddCart;
           addCartModel=new AddCartModel();


    }
    public void getMP(String uid,String pid){
      addCartModel.getGoods("http://120.27.23.105/product/addCart?uid=" + uid + "&pid=" + pid+"&", new CGSB<AddCart>() {
          @Override
          public void chengGong(AddCart addCart) {
              iAddCart.showData(addCart);
          }

          @Override
          public void shiBai(IOException e) {

          }
      });


    }
}
