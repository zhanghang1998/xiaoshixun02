package zyh.com.adapter.myadapter;

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

import zyh.com.bean.my.DetailListBean;
import zyh.com.bean.my.MyFootmarkBean;
import zyh.com.zhoukao01_lianxi.R;

public class MyWalletAdapter extends RecyclerView.Adapter<MyWalletAdapter.MyLoadler> {

    private List<DetailListBean> list = new ArrayList<>();
    private Context context;

    public MyWalletAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyWalletAdapter.MyLoadler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_money_item, viewGroup, false);
        MyLoadler myLoadler = new MyLoadler(view);

        return myLoadler;
    }

    @Override
    public void onBindViewHolder(@NonNull MyWalletAdapter.MyLoadler myLoadler, int i) {

        DetailListBean footmarkBean = list.get(i);

        myLoadler.textTime.setText(footmarkBean.getCreateTime()+"");
        myLoadler.textPrice.setText(footmarkBean.getAmount()+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //添加集合
    public void addAll(List<DetailListBean> beanList){
        if (beanList!=null) {
            list.addAll(beanList);
        }
    }

    //清楚集合
    public void CallClear(){
        list.clear();
    }

    /**
     * 内部类
     */
    public class MyLoadler extends RecyclerView.ViewHolder{

        private final TextView textTime;
        private final TextView textPrice;

        public MyLoadler(@NonNull View itemView) {
            super(itemView);

            textTime = itemView.findViewById(R.id.item_money_time);
            textPrice = itemView.findViewById(R.id.item_money_price);


        }
    }
}
