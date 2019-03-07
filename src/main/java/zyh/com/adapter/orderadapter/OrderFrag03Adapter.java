package zyh.com.adapter.orderadapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import zyh.com.bean.order.OrderFragbean;
import zyh.com.bean.order.OrderListBean;
import zyh.com.view.MyShoppingView;
import zyh.com.zhoukao01_lianxi.R;

public class OrderFrag03Adapter extends RecyclerView.Adapter<OrderFrag03Adapter.MyLoadler> {

    private List<OrderListBean> list = new ArrayList<>();
    private Context context;

    public OrderFrag03Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public OrderFrag03Adapter.MyLoadler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.frag04_orderfrag03_rlist_item, viewGroup, false);
        MyLoadler myLoadler = new MyLoadler(view);

        return myLoadler;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderFrag03Adapter.MyLoadler myLoadler, int i) {

        final OrderListBean orderListBean = list.get(i);
        List<OrderFragbean> fragbeanList = orderListBean.getDetailList();

        myLoadler.task_text_orderId.setText(orderListBean.getOrderId());
        String times = new SimpleDateFormat("yyyy-MM-dd").format(
                new java.util.Date(orderListBean.getOrderTime()));
        myLoadler.task_text_time.setText(times);
        myLoadler.task_text_CompName.setText(orderListBean.getExpressCompName());
        myLoadler.task_text_expressSn.setText(orderListBean.getExpressSn());
        BillItemRecyAdapter billItemRecyAdapter = new BillItemRecyAdapter(context);
        billItemRecyAdapter.setList(fragbeanList);
        myLoadler.task_button_affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickNext!=null){
                    mClickNext.next(orderListBean.getOrderId());

                }
            }
        });

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

        private final TextView task_text_orderId;
        private final TextView task_text_time;
        private final RecyclerView task_recy;
        //快递公司
        private final TextView task_text_CompName;
        //快递单号
        private final TextView task_text_expressSn;
        //确认按钮
        private final Button task_button_affirm;

        public MyLoadler(@NonNull View itemView) {
            super(itemView);

            task_text_orderId=itemView.findViewById(R.id.xrecy_task_item_text_orderId);//
            task_text_time=itemView.findViewById(R.id.xrecy_task_item_text_orderTime);
            task_text_CompName=itemView.findViewById(R.id.xrecy_task_item_text_CompName);
            task_text_expressSn=itemView.findViewById(R.id.xrecy_task_item_text_expressSn);
            task_button_affirm=itemView.findViewById(R.id.xrecy_task_item_button_affirm);
            task_recy=itemView.findViewById(R.id.xrecy_task_item_recy);
        }
    }


    private ClickNext mClickNext;
    public void setNext(ClickNext mClickNext){
        this.mClickNext=mClickNext;
    }
    //确认收货的接口回调
    public interface ClickNext{
        void next(String orderId);
    }
}
