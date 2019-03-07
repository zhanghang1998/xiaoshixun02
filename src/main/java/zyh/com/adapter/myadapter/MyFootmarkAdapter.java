package zyh.com.adapter.myadapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zyh.com.bean.my.MyAddressBean;
import zyh.com.bean.my.MyFootmarkBean;
import zyh.com.zhoukao01_lianxi.R;

public class MyFootmarkAdapter extends RecyclerView.Adapter<MyFootmarkAdapter.MyLoadler> {

    private List<MyFootmarkBean> list = new ArrayList<>();
    private Context context;

    public MyFootmarkAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyFootmarkAdapter.MyLoadler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.footmark_rlist_item, viewGroup, false);
        MyLoadler myLoadler = new MyLoadler(view);

        return myLoadler;
    }

    @Override
    public void onBindViewHolder(@NonNull MyFootmarkAdapter.MyLoadler myLoadler, int i) {

        MyFootmarkBean footmarkBean = list.get(i);

        myLoadler.image.setImageURI(Uri.parse(footmarkBean.getMasterPic()));
        myLoadler.textCount.setText(footmarkBean.getCommodityName()+"");
        myLoadler.textPrice.setText(footmarkBean.getPrice()+"");
        myLoadler.textNum.setText(footmarkBean.getBrowseNum()+"");
        myLoadler.textTime.setText(footmarkBean.getBrowseTime()+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //添加集合
    public void addAll(List<MyFootmarkBean> beanList){
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

        private final TextView textCount;
        private final TextView textPrice;
        private final SimpleDraweeView image;
        private final TextView textNum;
        private final TextView textTime;

        public MyLoadler(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.simpleDraweeView_footmark);
            textCount = itemView.findViewById(R.id.textAddress_count_myFootmark_rlist);
            textPrice = itemView.findViewById(R.id.textPrice_myFootmark_rlist);
            textNum = itemView.findViewById(R.id.textNum_myFootmark_rlist);
            textTime = itemView.findViewById(R.id.textTime_myFootmark_rlist);

        }
    }
}
