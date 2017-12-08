package example.dell.jd.persenter;

import java.io.IOException;

import example.dell.jd.Bean.GoodsXQBean;
import example.dell.jd.IActivity.IGoodsXQActivity;
import example.dell.jd.model.GoodsXQModel;
import example.dell.jd.net.CGSB;

/**
 * Created by Dell on 2017/11/30.
 */

public class GoodsXQPersenter {

    private final GoodsXQModel goodsXQModel;
    private IGoodsXQActivity iGoodsXQActivity;


    public GoodsXQPersenter(IGoodsXQActivity iGoodsXQActivity) {
        this.iGoodsXQActivity=iGoodsXQActivity;
        goodsXQModel = new GoodsXQModel();


    }
    public void getMP(String pid){
      goodsXQModel.getGoods("http://120.27.23.105/product/getProductDetail?pid="+pid, new CGSB<GoodsXQBean>() {
          @Override
          public void chengGong(GoodsXQBean goodsXQBean) {
              iGoodsXQActivity.ShowBean(goodsXQBean);
          }

          @Override
          public void shiBai(IOException e) {

          }
      });


    }
}
