package zyh.com.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import zyh.com.bean.CircleBean;
import zyh.com.bean.SearchBean;
import zyh.com.zhoukao01_lianxi.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyLoadlers> {

    private List<SearchBean> list = new ArrayList<>();
    private Context context;

    public SearchAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SearchAdapter.MyLoadlers onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_rlist_item, viewGroup, false);
        MyLoadlers myLoadler = new MyLoadlers(view);

        return myLoadler;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyLoadlers myLoadler, int i) {

        SearchBean searchBean = list.get(i);

        myLoadler.textPrice.setText(searchBean.getPrice()+"");
        myLoadler.textNum.setText(searchBean.getSaleNum()+"");
        myLoadler.textContent.setText(searchBean.getCommodityName()+"");
        myLoadler.simple.setImageURI(Uri.parse(searchBean.getMasterPic()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clearAll(){
        list.clear();
    }

    public void addAll(List<SearchBean> beanList){
        if (beanList!=null) {
            list.addAll(beanList);
        }
    }

    public class MyLoadlers extends RecyclerView.ViewHolder {

        private final SimpleDraweeView simple;
        private final TextView textContent;
        private final TextView textPrice;
        private final TextView textNum;

        public MyLoadlers(@NonNull View itemView) {
            super(itemView);

            simple = itemView.findViewById(R.id.imageView01_search_xlist_zhanshi);
            textContent = itemView.findViewById(R.id.textView01_search_xlist_count);
            textPrice = itemView.findViewById(R.id.textView_search_xlist_price);
            textNum = itemView.findViewById(R.id.textView_search_xlist_sell);
        }
    }

}
