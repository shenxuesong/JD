package example.dell.jd.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import example.dell.jd.Fragment.Fragment01;
import example.dell.jd.Fragment.Fragment02;
import example.dell.jd.Fragment.Fragment03;
import example.dell.jd.Fragment.Fragment04;
import example.dell.jd.Fragment.Fragment05;
import example.dell.jd.R;

public class ShowGoodsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
   private ViewPager vp;
   private RadioGroup rg;
  private List<Fragment> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_goods);
        //查找组件
        vp=(ViewPager)findViewById(R.id.vp);
        rg=(RadioGroup)findViewById(R.id.rg);


        //造数据
        list.add(new Fragment01());
        list.add(new Fragment02());
        list.add(new Fragment03());
        list.add(new Fragment04());
        list.add(new Fragment05());
        //设置适配器

        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });


        //设置viewpager的监听事件，使vp与rg有联动效果
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rg.check(R.id.rb1);
                        break;
                    case 1:
                        rg.check(R.id.rb2);
                        break;
                    case 2:
                        rg.check(R.id.rb3);
                        break;
                    case 3:
                        rg.check(R.id.rb4);
                        break;
                    case 4:
                        rg.check(R.id.rb5);
                        break;


                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rg.setOnCheckedChangeListener(this);



    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb1:
                vp.setCurrentItem(0);
                break;
            case R.id.rb2:
                vp.setCurrentItem(1);
                break;
            case R.id.rb3:
                vp.setCurrentItem(2);
                break;
            case R.id.rb4:
                vp.setCurrentItem(3);
                break;
            case R.id.rb5:
                vp.setCurrentItem(4);
                break;
        }
    }
}
