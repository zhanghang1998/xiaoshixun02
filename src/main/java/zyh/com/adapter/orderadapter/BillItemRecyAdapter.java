package zyh.com.adapter.orderadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import zyh.com.bean.order.OrderFragbean;
import zyh.com.zhoukao01_lianxi.R;

public class BillItemRecyAdapter extends RecyclerView.Adapter<BillItemRecyAdapter.ViewHodler> {

    private List<OrderFragbean> list = new ArrayList<>();
    private Context context;
    private int status;

    public BillItemRecyAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<OrderFragbean> mlist) {
        if (mlist != null) {
            list.addAll(mlist);
        }
        notifyDataSetChanged();
    }

    public OrderFragbean getItem(int position) {
        return list.get(position);
    }


    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View payView = LayoutInflater.from(context).inflate(R.layout.recy_item_pay_item, viewGroup, false);
        return new ViewHodler(payView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler viewHodler, int i) {
        viewHodler.getdata(getItem(i), context, i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView text_name;
        private TextView text_price;
        private TextView text_num;
        private Button button_evaluate;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recy_item_bill_image);
            text_name = itemView.findViewById(R.id.recy_item_bill_text_name);
            text_price = itemView.findViewById(R.id.recy_item_bill_text_price);
            text_num = itemView.findViewById(R.id.recy_item_bill_text_num);
            //button_evaluate=itemView.findViewById(R.id.recy_item_bill_button_evaluate);
        }

        public void getdata(OrderFragbean item, Context context, int i) {

            String[] split = item.getCommodityPic().split("\\,");
            Glide.with(context).load(split[0]).into(imageView);
            text_name.setText(item.getCommodityName());
            text_price.setText("￥" + item.getCommodityPrice());
            if (text_num != null) {
                text_num.setText("* " + item.getCommodityCount() + " *");
            }
            if (button_evaluate != null) {
                button_evaluate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mClickEvaluate != null) {
                            mClickEvaluate.setEvaluat(list, getAdapterPosition());
                        }
                    }
                });
            }
        }
    }

    public ClickEvaluate mClickEvaluate;

    public void setEva(ClickEvaluate mClickEvaluate) {
        this.mClickEvaluate = mClickEvaluate;
    }

    public interface ClickEvaluate {
        void setEvaluat(List<OrderFragbean> list, int position);
    }
}
