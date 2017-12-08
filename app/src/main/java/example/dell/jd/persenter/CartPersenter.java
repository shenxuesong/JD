package example.dell.jd.persenter;

import java.io.IOException;

import example.dell.jd.Bean.CartBean;
import example.dell.jd.IActivity.IAddCart;
import example.dell.jd.model.CartModel;
import example.dell.jd.net.CGSB;

/**
 * Created by Dell on 2017/11/30.
 */

public class CartPersenter {

    private final CartModel cartModel;
    private IAddCart iAddCart;


    public CartPersenter(IAddCart iAddCart) {
           this.iAddCart=iAddCart;
           cartModel=new CartModel();


    }
    public void getMP(String uid){
      cartModel.getGoods("http://120.27.23.105/product/getCarts?uid=" + uid + "&", new CGSB<CartBean>() {
          @Override
          public void chengGong(CartBean cartBean) {
              iAddCart.showData(cartBean);
          }

          @Override
          public void shiBai(IOException e) {

          }
      });


    }
}
