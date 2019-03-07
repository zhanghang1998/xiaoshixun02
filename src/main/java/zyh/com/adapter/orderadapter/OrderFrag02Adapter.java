package zyh.com.adapter.orderadapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import zyh.com.bean.QueryShoppingBean;
import zyh.com.bean.order.OrderFragbean;
import zyh.com.bean.order.OrderListBean;
import zyh.com.view.MyShoppingView;
import zyh.com.zhoukao01_lianxi.R;

public class OrderFrag02Adapter extends RecyclerView.Adapter<OrderFrag02Adapter.MyLoadler> {

    private List<OrderListBean> list = new ArrayList<>();
    private Context context;

    public OrderFrag02Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public OrderFrag02Adapter.MyLoadler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.frag04_orderfrag02_rlist_item, viewGroup, false);
        MyLoadler myLoadler = new MyLoadler(view);

        return myLoadler;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderFrag02Adapter.MyLoadler myLoadler, final int i) {

        final OrderListBean orderListBean = list.get(i);
        List<OrderFragbean> fragbeanList = orderListBean.getDetailList();

        myLoadler.pay_text_orderId.setText(orderListBean.getOrderId());
        String times = new SimpleDateFormat("yyyy-MM-dd").format(
                new java.util.Date(orderListBean.getOrderTime()));
        myLoadler.pay_text_time.setText(times);
        myLoadler.pay_text_allprice.setText(orderListBean.getPayAmount()+"");
        //设置数量
        int num=0;
        for (int y=0;y<orderListBean.getDetailList().size();y++){
            num+=orderListBean.getDetailList().get(y).getCommodityCount();
        }
        myLoadler.pay_text_allnum.setText(""+num);
        //删除订单
        myLoadler.pay_button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickDelete!=null){
                    mClickDelete.delete(orderListBean.getOrderId(),i);
                }

            }
        });
        //支付
        myLoadler.pay_button_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickGo!=null){
                    mClickGo.go(orderListBean.getOrderId(),orderListBean.getPayAmount()+"");
                }
            }
        });


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        myLoadler.pay_recy.setLayoutManager(linearLayoutManager);
        BillItemRecyAdapter billItemRecyAdapter=new BillItemRecyAdapter(context);
        billItemRecyAdapter.setList(orderListBean.getDetailList());
        myLoadler.pay_recy.setAdapter(billItemRecyAdapter);
        billItemRecyAdapter.setEva(new BillItemRecyAdapter.ClickEvaluate() {
            @Override
            public void setEvaluat(List<OrderFragbean> list, int position) {
                if (mClickEvaluate!=null){
                    mClickEvaluate.setEvaluat(list,position,orderListBean,i);
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

        private TextView pay_text_orderId;
        private TextView pay_text_time;
        private RecyclerView pay_recy;
        private TextView pay_text_allprice;
        private TextView pay_text_allnum;
        private Button pay_button_go;
        private Button pay_button_cancel;

        public MyLoadler(@NonNull View itemView) {
            super(itemView);

            pay_text_orderId=itemView.findViewById(R.id.xrecy_pay_item_text_orderId);
            pay_text_time=itemView.findViewById(R.id.xrecy_pay_item_text_orderTime);
            pay_text_allprice=itemView.findViewById(R.id.xrecy_pay_item_text_allprice);
            pay_text_allnum=itemView.findViewById(R.id.xrecy_pay_item_text_allnum);
            pay_button_go=itemView.findViewById(R.id.xrecy_pay_item_button_go);
            pay_button_cancel=itemView.findViewById(R.id.xrecy_pay_item_button_cancel);
            pay_recy=itemView.findViewById(R.id.xrecy_pay_item_recy);
        }
    }


    //支付
    private ClickGo mClickGo;
    public void setGo(ClickGo mClickGo){
        this.mClickGo=mClickGo;
    }
    public interface ClickGo{
        void go(String orderId,String all_price);
    }

    public ClickEvaluate mClickEvaluate;
    public void setEva(ClickEvaluate mClickEvaluate){
        this.mClickEvaluate=mClickEvaluate;
    }
    public interface ClickEvaluate{
        void setEvaluat(List<OrderFragbean> list, int position, OrderListBean item, int i);
    }

    private ClickDelete mClickDelete;
    public void setDelete(ClickDelete mClickDelete){
        this.mClickDelete=mClickDelete;
    }
    //删除时候的接口回调
    public interface ClickDelete{
        void delete(String orderId,int position);
    }

}
