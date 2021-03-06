package zyh.com.adapter.myadapter;

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

import zyh.com.bean.QueryShoppingBean;
import zyh.com.bean.my.MyAddressBean;
import zyh.com.view.MyShoppingView;
import zyh.com.zhoukao01_lianxi.R;

public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.MyLoadler> {

    private List<MyAddressBean> list = new ArrayList<>();
    private Context context;

    public MyAddressAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyAddressAdapter.MyLoadler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.myaddress_rlist_item, viewGroup, false);
        MyLoadler myLoadler = new MyLoadler(view);

        return myLoadler;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAddressAdapter.MyLoadler myLoadler, int i) {

        MyAddressBean myAddressBean = list.get(i);

        myLoadler.textName.setText(myAddressBean.getRealName()+"");
        myLoadler.textPhone.setText(myAddressBean.getPhone()+"");
        myLoadler.textAddress.setText(myAddressBean.getAddress()+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //添加集合
    public void addAll(List<MyAddressBean> beanList){
        if (beanList!=null) {
            list.addAll(beanList);
        }
    }

    //清空集合数据
    public void allClear(){
        list.clear();
    }

    /**
     * 内部类
     */
    public class MyLoadler extends RecyclerView.ViewHolder{

        private final TextView textName;
        private final TextView textPhone;
        private final CheckBox check;
        private final TextView textAddress;
        private final TextView textView_updata;
        private final TextView textView_delete;

        public MyLoadler(@NonNull View itemView) {
            super(itemView);

            check = itemView.findViewById(R.id.checkBox_address);
            textName = itemView.findViewById(R.id.textName_myMessage_rlist);
            textPhone = itemView.findViewById(R.id.textPhone_myMessage_rlist);
            textAddress = itemView.findViewById(R.id.textAddress_count_myMessage_rlist);
            textView_updata = itemView.findViewById(R.id.textView_updata_address);
            textView_delete = itemView.findViewById(R.id.textView_delete_address);

        }
    }
}
