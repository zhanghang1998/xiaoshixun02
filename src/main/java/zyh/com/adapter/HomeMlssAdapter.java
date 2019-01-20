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

public class HomeMlssAdapter extends RecyclerView.Adapter<HomeMlssAdapter.MyHoadler> {

    private Context context;
    private List<CommodityListBean> list = new ArrayList<>();

    public HomeMlssAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HomeMlssAdapter.MyHoadler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.frag01_moliss_rlist_item, viewGroup,false);
        MyHoadler myHoadler = new MyHoadler(view);
        return myHoadler;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeMlssAdapter.MyHoadler myHoadler, int i) {

        CommodityListBean commodityListBean = list.get(i);
        myHoadler.text01.setText(commodityListBean.getCommodityName()+"");
        myHoadler.text02.setText(commodityListBean.getPrice()+"");
        //图片加载
        myHoadler.imag01.setImageURI(Uri.parse(commodityListBean.getMasterPic()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHoadler extends RecyclerView.ViewHolder{

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

    //添加集合数据
    public void addAll(List<CommodityListBean> commodityList){
        if (commodityList!=null) {
            this.list.addAll(commodityList);
        }
    }

}
