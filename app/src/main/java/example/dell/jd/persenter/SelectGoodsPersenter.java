package example.dell.jd.persenter;

import java.io.IOException;
import java.util.List;

import example.dell.jd.Bean.SelectGoodsBean;
import example.dell.jd.IActivity.ISelectGoods;
import example.dell.jd.model.SelectGoodsModel;
import example.dell.jd.net.CGSB;

/**
 * Created by Dell on 2017/12/2.
 */

public class SelectGoodsPersenter {


    private static ISelectGoods iProdect;
    private final SelectGoodsModel selectGoodsModel;

    public SelectGoodsPersenter(ISelectGoods iProdect) {
        this.iProdect=iProdect;
        selectGoodsModel = new SelectGoodsModel();


    }
    public void getProdect(String s){
        selectGoodsModel.getProdect("http://120.27.23.105/product/searchProducts?keywords=" + s + "&page=1&", new CGSB<SelectGoodsBean>() {
            @Override
            public void chengGong(SelectGoodsBean selectGoodsBean) {
                List<SelectGoodsBean.DataBean> list = selectGoodsBean.getData();
                iProdect.showData(list);
            }

            @Override
            public void shiBai(IOException e) {

            }
        });
    }
}
