package zyh.com.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    public void onBindViewHolder(@NonNull final QueryShoppingAdapter.MyLoadler myLoadler, final int i) {

        final QueryShoppingBean circleBean = list.get(i);

        myLoadler.textTitle.setText(circleBean.getCommodityName()+"");
        myLoadler.textPrice.setText(circleBean.getPrice()+"");
        myLoadler.simple.setImageURI(Uri.parse(circleBean.getPic()));
        myLoadler.check.setChecked(circleBean.isIscheck());
        //加减器
        myLoadler.layout.setCount(circleBean.getCount());
        myLoadler.layout.setAddSubListener(new MyShoppingView.AddSubListener() {
            @Override
            public void addSub(int count) {
                if (mshopClick!=null){
                    mshopClick.shopPrice(list);
                }
            }
        });
        //改变复选框的状态
        myLoadler.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                circleBean.setIscheck(isChecked);
                if (mshopClick!=null){
                    mshopClick.shopPrice(list);
                }
            }
        });
        //删除子条目
        myLoadler.textDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(i);
                notifyDataSetChanged();
                if (mRemoveCallBack!=null){
                    mRemoveCallBack.removeposition(list,i);
                }
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

    //清空集合
    public void ClearAll(){
            list.clear();
    }

    /**
     * 内部类
     */
    public class MyLoadler extends RecyclerView.ViewHolder{

        private final SimpleDraweeView simple;
        private final TextView textPrice;
        private final CheckBox check;
        private final TextView textTitle,textDelete;
        private final MyShoppingView layout;

        public MyLoadler(@NonNull View itemView) {
            super(itemView);

            check = itemView.findViewById(R.id.frag03_shop_cart_goods_check_single);
            simple = itemView.findViewById(R.id.frag03_cart_goods_item_img);
            textPrice = itemView.findViewById(R.id.frag03_cart_goods_item_price);
            textTitle = itemView.findViewById(R.id.frag03_cart_goods_item_title);
            layout = itemView.findViewById(R.id.frag03_shop_add_sub_layout);
            textDelete = itemView.findViewById(R.id.item_text_delete);

        }
    }

    public ShopClick mshopClick;

    public void setOnClick(ShopClick mshopClick){
        this.mshopClick=mshopClick;
    }

    public interface ShopClick{
        public void shopPrice(List<QueryShoppingBean> list);
    }

    public RemoveCallBack mRemoveCallBack;

    public void setRemove(RemoveCallBack mRemoveCallBack){
        this.mRemoveCallBack=mRemoveCallBack;
    }

    public interface RemoveCallBack{
        public void removeposition(List<QueryShoppingBean> list,int position);
    }
}
