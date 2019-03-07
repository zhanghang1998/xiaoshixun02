package zyh.com.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zyh.com.bean.home.CommodityListBean;
import zyh.com.zhoukao01_lianxi.R;

public class HomeRexiaoAdapter extends RecyclerView.Adapter<HomeRexiaoAdapter.MyHoadler> {

    private Context context;
    private List<CommodityListBean> list = new ArrayList<>();

    public HomeRexiaoAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HomeRexiaoAdapter.MyHoadler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.frag01_rexiao_rlist_item, viewGroup, false);
        MyHoadler myHoadler = new MyHoadler(view);
        return myHoadler;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRexiaoAdapter.MyHoadler myHoadler, int i) {

        final CommodityListBean commodityListBean = list.get(i);
        myHoadler.text01.setText(commodityListBean.getCommodityName() + "");
        myHoadler.text02.setText(commodityListBean.getPrice() + "");
        //图片加载
        myHoadler.imag01.setImageURI(Uri.parse(commodityListBean.getMasterPic()));
        //条目点击
        myHoadler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(commodityListBean.getCommodityId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHoadler extends RecyclerView.ViewHolder {

        private final SimpleDraweeView imag01;
        private final TextView text01;
        private final TextView text02;

        public MyHoadler(@NonNull View itemView) {
            super(itemView);

            imag01 = itemView.findViewById(R.id.imageView01_home_rexiao_zhanshi);
            text01 = itemView.findViewById(R.id.textView01_home_rexiao_jieshao);
            text02 = itemView.findViewById(R.id.textView02_home_rexiao_price);
        }
    }

    //条目点击进入详情页面 接口回调
    //定义接口
    public interface OnItemClickListener {
        void onItemClick(int cid);
    }
    //方法名
    private OnItemClickListener onItemClickListener;

    //方法;设置点击方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //添加集合数据
    public void addAll(List<CommodityListBean> commodityList) {
        if (commodityList != null) {
            this.list.addAll(commodityList);
        }
    }

}
