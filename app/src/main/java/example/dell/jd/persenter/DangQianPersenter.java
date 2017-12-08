package example.dell.jd.persenter;

import java.io.IOException;

import example.dell.jd.Bean.DangQianGoodsBean;
import example.dell.jd.IActivity.IDangQianActivity;
import example.dell.jd.model.DangQianModel;
import example.dell.jd.net.CGSB;

/**
 * Created by Dell on 2017/11/30.
 */

public class DangQianPersenter {

    private final DangQianModel dangQianModel;
    private IDangQianActivity iDangQianActivity;


    public DangQianPersenter( IDangQianActivity iDangQianActivity) {
        this.iDangQianActivity=iDangQianActivity;
         dangQianModel=new DangQianModel();


    }
    public void getMP(String pid,String w){

         dangQianModel.getGoods("http://120.27.23.105/product/getProducts?pscid=" + pid + "&page=" + w, new CGSB<DangQianGoodsBean>() {
             @Override
             public void chengGong(DangQianGoodsBean dangQianGoodsBean) {
                 iDangQianActivity.ShowBean(dangQianGoodsBean);
             }

             @Override
             public void shiBai(IOException e) {

             }
         });

    }
}
