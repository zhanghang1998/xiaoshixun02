package zyh.com.zhoukao01_lianxi;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.BuildConfig;
import butterknife.ButterKnife;
import zyh.com.frag.Fragment01_home;
import zyh.com.frag.Fragment02_circle;
import zyh.com.frag.Fragment03_shop;
import zyh.com.frag.Fragment04_list;
import zyh.com.frag.Fragment05_my;

public class ShowActivity extends AppCompatActivity {

    @BindView(R.id.radioGroup_show)RadioGroup radioGroup;
    @BindView(R.id.radioButton01_show)RadioButton radioButton01;
    @BindView(R.id.radioButton02_show)RadioButton radioButton02;
    @BindView(R.id.radioButton03_show)RadioButton radioButton03;
    @BindView(R.id.radioButton04_show)RadioButton radioButton04;
    @BindView(R.id.radioButton05_show)RadioButton radioButton05;
    private Fragment01_home fragment01_home;
    private Fragment02_circle fragment02_circle;
    private Fragment03_shop fragment03_shop;
    private Fragment04_list fragment04_list;
    private Fragment05_my fragment05_my;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);

        //获取管理器
        manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragment01_home = new Fragment01_home();
        fragment02_circle = new Fragment02_circle();
        fragment03_shop = new Fragment03_shop();
        fragment04_list = new Fragment04_list();
        fragment05_my = new Fragment05_my();
        fragmentTransaction.add(R.id.fragLayout_show,fragment01_home);
        fragmentTransaction.add(R.id.fragLayout_show,fragment02_circle);
        fragmentTransaction.add(R.id.fragLayout_show,fragment03_shop);
        fragmentTransaction.add(R.id.fragLayout_show,fragment04_list);
        fragmentTransaction.add(R.id.fragLayout_show,fragment05_my);
        fragmentTransaction.show(fragment01_home).hide(fragment02_circle).hide(fragment03_shop).hide(fragment04_list).hide(fragment05_my);
        fragmentTransaction.commit();

        //按钮监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = manager.beginTransaction();
                switch (checkedId) {
                    case R.id.radioButton01_show:
                        transaction.show(fragment01_home).hide(fragment02_circle).hide(fragment03_shop).hide(fragment04_list).hide(fragment05_my);
                        break;
                    case R.id.radioButton02_show:
                        transaction.show(fragment02_circle).hide(fragment01_home).hide(fragment03_shop).hide(fragment04_list).hide(fragment05_my);
                        break;
                    case R.id.radioButton03_show:
                        transaction.show(fragment03_shop).hide(fragment02_circle).hide(fragment01_home).hide(fragment04_list).hide(fragment05_my);
                        break;
                    case R.id.radioButton04_show:
                        transaction.show(fragment04_list).hide(fragment02_circle).hide(fragment03_shop).hide(fragment01_home).hide(fragment05_my);
                        break;
                    case R.id.radioButton05_show:
                        transaction.show(fragment05_my).hide(fragment02_circle).hide(fragment03_shop).hide(fragment04_list).hide(fragment01_home);
                        break;
                }
                transaction.commit();
            }
        });
    }
}
