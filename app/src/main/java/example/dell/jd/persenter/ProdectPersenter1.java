package example.dell.jd.persenter;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import example.dell.jd.Bean.LunBoTuBean;
import example.dell.jd.Bean.ProdectBean;
import example.dell.jd.IActivity.IProdect1;
import example.dell.jd.model.ProdectModel;
import example.dell.jd.model.TuijianModel;
import example.dell.jd.net.CGSB;

/**
 * Created by Dell on 2017/12/2.
 */

public class ProdectPersenter1 {

   public  ProdectModel prodectModel ;
    private static IProdect1 iProdect;
 public TuijianModel tuijianModel;

    public ProdectPersenter1(IProdect1 iProdect) {
        this.iProdect=iProdect;
        prodectModel = new ProdectModel();
       tuijianModel=new TuijianModel();


    }
    public void getProdect(){
        prodectModel.getProdect("http://120.27.23.105/product/getCatagory?", new CGSB<ProdectBean>() {
            @Override
            public void chengGong(ProdectBean prodectBean) {
                List<ProdectBean.DataBean> list = prodectBean.getData();
                Log.i("TAG",list.size()+"");
                iProdect.showData(list);


            }

            @Override
            public void shiBai(IOException e) {

            }
        });


    }
public  void tuiJian(){
        tuijianModel.getReg("http://120.27.23.105/ad/getAd", new CGSB<LunBoTuBean>() {
            @Override
            public void chengGong(LunBoTuBean lunBoTuBean) {
                iProdect.showBean(lunBoTuBean);
            }

            @Override
            public void shiBai(IOException e) {

            }
        });
}

}
