package example.dell.jd.persenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import example.dell.jd.Bean.GoodsFenLeiBean;
import example.dell.jd.Bean.ProdectBean;
import example.dell.jd.IActivity.IProdect;
import example.dell.jd.model.GoodsFenLeiModel;
import example.dell.jd.model.ProdectModel;
import example.dell.jd.net.CGSB;

/**
 * Created by Dell on 2017/12/2.
 */

public class ProdectPersenter {

   public  ProdectModel prodectModel ;
    private static IProdect iProdect;
    private final GoodsFenLeiModel goodsFenLeiModel;

    public ProdectPersenter(IProdect iProdect) {
        this.iProdect=iProdect;
        prodectModel = new ProdectModel();
        goodsFenLeiModel = new GoodsFenLeiModel();


    }
    public void getProdect(){
        prodectModel.getProdect("http://120.27.23.105/product/getCatagory?", new CGSB<ProdectBean>() {
            @Override
            public void chengGong(ProdectBean prodectBean) {

                List<ProdectBean.DataBean> list = prodectBean.getData();

                iProdect.showData(list);


            }

            @Override
            public void shiBai(IOException e) {

            }
        });


    }
   public void getGood(final String cid){
       goodsFenLeiModel.getGoods("http://120.27.23.105/product/getProductCatagory?" + "cid=" + cid, new CGSB<GoodsFenLeiBean>() {
           @Override
           public void chengGong(GoodsFenLeiBean goodsFenLeiBean) {
               List<GoodsFenLeiBean.DataBean> grouplist = goodsFenLeiBean.getData();
               List< List<GoodsFenLeiBean.DataBean.ListBean>> chilist=new ArrayList<>();
               for (int i = 0; i <grouplist.size() ; i++) {
                   List<GoodsFenLeiBean.DataBean.ListBean> list = grouplist.get(i).getList();
                   chilist.add(list);
               }
               iProdect.showList(grouplist,chilist);
           }

           @Override
           public void shiBai(IOException e) {

           }
       });
   }

}
