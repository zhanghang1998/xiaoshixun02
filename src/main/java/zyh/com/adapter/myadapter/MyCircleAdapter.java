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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zyh.com.bean.my.MyCircleBean;
import zyh.com.zhoukao01_lianxi.R;

public class MyCircleAdapter extends RecyclerView.Adapter<MyCircleAdapter.MyLoadler> {

    private List<MyCircleBean> list = new ArrayList<>();
    private Context context;

    public MyCircleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyCircleAdapter.MyLoadler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_select_circle, viewGroup, false);
        MyLoadler myLoadler = new MyLoadler(view);

        return myLoadler;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCircleAdapter.MyLoadler myLoadler, int i) {

        MyCircleBean myCircleBean = list.get(i);

        if (myCircleBean.getImage()!=null){
            String[] split = myCircleBean.getImage().split("\\,");
            myLoadler.image.setImageURI(Uri.parse(split[0]));
        }
        myLoadler.text_content.setText(myCircleBean.getContent()+"");
        myLoadler.text_num.setText(myCircleBean.getGreatNum()+"");
        String times = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(
                new Date(myCircleBean.getCreateTime()));
        myLoadler.text_time.setText(times);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //添加集合
    public void addAll(List<MyCircleBean> beanList){
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

        private final TextView text_content;
        private final TextView text_time;
        private final TextView text_num;
        private final SimpleDraweeView image;

        public MyLoadler(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recy_item_circle_image);
            text_content=itemView.findViewById(R.id.recy_item_circle_text_content);
            text_time=itemView.findViewById(R.id.recy_item_circle_text_time);
            text_num=itemView.findViewById(R.id.recy_item_circle_text_num);
        }
    }
}
