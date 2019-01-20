package zyh.com.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zyh.com.bean.CircleBean;
import zyh.com.bean.QueryShoppingBean;
import zyh.com.view.MyShoppingView;
import zyh.com.zhoukao01_lianxi.R;

public class QueryShoppingAdapter extends RecyclerView.Adapter<QueryShoppingAdapter.MyLoadler> {

    private List<QueryShoppingBean> list = new ArrayList<>();
    private Context context;

    public QueryShoppingAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public QueryShoppingAdapter.MyLoadler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.frag03_shop_xlist_item, viewGroup, false);
        MyLoadler myLoadler = new MyLoadler(view);

        return myLoadler;
    }

    @Override
    public void onBindViewHolder(@NonNull QueryShoppingAdapter.MyLoadler myLoadler, int i) {

        QueryShoppingBean circleBean = list.get(i);

        myLoadler.textTitle.setText(circleBean.getCommodityName()+"");
        myLoadler.textPrice.setText(circleBean.getPrice()+"");
        myLoadler.simple.setImageURI(Uri.parse(circleBean.getPic()));

        //加减器
        myLoadler.layout.setCount(circleBean.getCount());
        myLoadler.layout.setAddSubListener(new MyShoppingView.AddSubListener() {
            @Override
            public void addSub(int count) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //添加集合
    public void addAll(List<QueryShoppingBean> beanList){
        if (beanList!=null) {
            list.addAll(beanList);
        }
    }

    /**
     * 内部类
     */
    public class MyLoadler extends RecyclerView.ViewHolder{

        private final SimpleDraweeView simple;
        private final TextView textPrice;
        private final CheckBox check;
        private final TextView textTitle;
        private final MyShoppingView layout;

        public MyLoadler(@NonNull View itemView) {
            super(itemView);

            check = itemView.findViewById(R.id.frag03_shop_cart_goods_check_single);
            simple = itemView.findViewById(R.id.frag03_cart_goods_item_img);
            textPrice = itemView.findViewById(R.id.frag03_cart_goods_item_price);
            textTitle = itemView.findViewById(R.id.frag03_cart_goods_item_title);
            layout = itemView.findViewById(R.id.frag03_shop_add_sub_layout);

        }
    }
}
