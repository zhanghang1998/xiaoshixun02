package zyh.com.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import zyh.com.frag.orderfrag.OrderFragment01_all;
import zyh.com.frag.orderfrag.OrderFragment02_obligation;
import zyh.com.frag.orderfrag.OrderFragment03_receive;
import zyh.com.frag.orderfrag.OrderFragment04_comment;
import zyh.com.frag.orderfrag.OrderFragment05_perform;
import zyh.com.zhoukao01_lianxi.R;

public class Fragment04_list extends Fragment implements View.OnClickListener {

    private ImageView imageView01,imageView02,imageView03,imageView04,imageView05;
    private ViewPager viewPagerFrag04;
    ArrayList<Fragment> listFrag = new ArrayList<>();
    private OrderFragment01_all orderFragment01_all;
    private OrderFragment02_obligation orderFragment02_obligation;
    private OrderFragment03_receive orderFragment03_receive;
    private OrderFragment04_comment orderFragment04_comment;
    private OrderFragment05_perform orderFragment05_perform;
    private Frag04HeaderAdapter frag04HeaderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag04_list_item, container, false);
        //获取控件
        viewPagerFrag04 = view.findViewById(R.id.viewPager_frag04_page);
        imageView01 = view.findViewById(R.id.imageView01_frag04_head);
        imageView02 = view.findViewById(R.id.imageView02_frag04_head);
        imageView03 = view.findViewById(R.id.imageView03_frag04_head);
        imageView04 = view.findViewById(R.id.imageView04_frag04_head);
        imageView05 = view.findViewById(R.id.imageView05_frag04_head);
        //设置点击事件
        imageView01.setOnClickListener(this);
        imageView02.setOnClickListener(this);
        imageView03.setOnClickListener(this);
        imageView04.setOnClickListener(this);
        imageView05.setOnClickListener(this);
        //获取fragment
        orderFragment01_all = new OrderFragment01_all();
        orderFragment02_obligation = new OrderFragment02_obligation();
        orderFragment03_receive = new OrderFragment03_receive();
        orderFragment04_comment = new OrderFragment04_comment();
        orderFragment05_perform = new OrderFragment05_perform();
        //添加到集合中
        listFrag.add(orderFragment01_all);
        listFrag.add(orderFragment02_obligation);
        listFrag.add(orderFragment03_receive);
        listFrag.add(orderFragment04_comment);
        listFrag.add(orderFragment05_perform);
        //获取管理器
        FragmentManager manager = getActivity().getSupportFragmentManager();
        frag04HeaderAdapter = new Frag04HeaderAdapter(manager);

        viewPagerFrag04.setAdapter(frag04HeaderAdapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView01_frag04_head:
                viewPagerFrag04.setCurrentItem(0);
                break;
            case R.id.imageView02_frag04_head:
                viewPagerFrag04.setCurrentItem(1);
                break;
            case R.id.imageView03_frag04_head:
                viewPagerFrag04.setCurrentItem(2);
                break;
            case R.id.imageView04_frag04_head:
                viewPagerFrag04.setCurrentItem(3);
                break;
            case R.id.imageView05_frag04_head:
                viewPagerFrag04.setCurrentItem(4);
                break;
        }
    }

    private class Frag04HeaderAdapter extends FragmentPagerAdapter {

        public Frag04HeaderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return listFrag.get(i);
        }

        @Override
        public int getCount() {
            return listFrag.size();
        }
    }
}
