package example.dell.jd.IActivity;

import java.util.List;

import example.dell.jd.Bean.GoodsFenLeiBean;
import example.dell.jd.Bean.ProdectBean;

/**
 * Created by Dell on 2017/12/2.
 */

public interface IProdect {
     void showData(List<ProdectBean.DataBean> list);
     void showList(   List<GoodsFenLeiBean.DataBean> list,   List< List<GoodsFenLeiBean.DataBean.ListBean>> chilist);
}
