package zyh.com.adapter.orderadapter;

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

import zyh.com.bean.order.OrderFragbean;
import zyh.com.bean.order.OrderListBean;
import zyh.com.view.MyShoppingView;
import zyh.com.zhoukao01_lianxi.R;

public class OrderFrag05Adapter extends RecyclerView.Adapter<OrderFrag05Adapter.MyLoadler> {

    private List<OrderListBean> list = new ArrayList<>();
    private Context context;

    public OrderFrag05Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public OrderFrag05Adapter.MyLoadler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.frag04_orderfrag05_rlist_item, viewGroup, false);
        MyLoadler myLoadler = new MyLoadler(view);

        return myLoadler;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderFrag05Adapter.MyLoadler myLoadler, int i) {

        OrderListBean orderListBean = list.get(i);
        List<OrderFragbean> fragbeanList = orderListBean.getDetailList();

        myLoadler.textDanHao.setText(orderListBean.getOrderId()+"");

        for (int j = 0; j < fragbeanList.size(); j++) {

            if (j==0) {
                OrderFragbean orderFragbean = fragbeanList.get(0);
                //圈子图片需要切割一下子
                String[] imageMy = orderFragbean.getCommodityPic().split(",");
                myLoadler.simple01.setImageURI(Uri.parse(imageMy[0]));

                myLoadler.textTitle01.setText(orderFragbean.getCommodityName()+"");
                myLoadler.textPrice01.setText("￥"+orderFragbean.getCommodityPrice()+"");

            } else if (j==1) {
                OrderFragbean orderFragbean = fragbeanList.get(1);
                //圈子图片需要切割一下子
                String[] imageMy = orderFragbean.getCommodityPic().split(",");
                myLoadler.simple02.setImageURI(Uri.parse(imageMy[0]));

                myLoadler.textTitle02.setText(orderFragbean.getCommodityName()+"");
                myLoadler.textPrice02.setText("￥"+orderFragbean.getCommodityPrice()+"");
            }

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //添加集合
    public void addAll(List<OrderListBean> orderListBeans){
        if (orderListBeans!=null) {
            list.addAll(orderListBeans);
        }
    }

    /**
     * 内部类
     */
    public class MyLoadler extends RecyclerView.ViewHolder{

        private final SimpleDraweeView simple01;
        private final SimpleDraweeView simple02;
        private final TextView textPrice01;
        private final TextView textPrice02;
        private final TextView textTitle01;
        private final TextView textTitle02;

        private final TextView textDanHao;


        public MyLoadler(@NonNull View itemView) {
            super(itemView);

            simple01 = itemView.findViewById(R.id.frag04_orderfrag02_goods_item_img);
            simple02 = itemView.findViewById(R.id.frag04_orderfrag02_goods_item_img02);
            textPrice01 = itemView.findViewById(R.id.frag04_orderfrag02_goods_item_price);
            textPrice02 = itemView.findViewById(R.id.frag04_orderfrag02_goods_item_price02);
            textTitle01 = itemView.findViewById(R.id.frag04_orderfrag02_goods_item_title);
            textTitle02 = itemView.findViewById(R.id.frag04_orderfrag02_goods_item_title02);

            textDanHao = itemView.findViewById(R.id.text_frag04_orderfrag02_addNum);
        }
    }
}
