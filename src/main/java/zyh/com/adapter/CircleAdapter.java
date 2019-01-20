package zyh.com.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zyh.com.bean.CircleBean;
import zyh.com.util.StringUtils;
import zyh.com.util.recyclerview.SpacingItemDecoration;
import zyh.com.zhoukao01_lianxi.R;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.MyLoadler> {

    private List<CircleBean> list = new ArrayList<>();
    private Context context;

    public CircleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CircleAdapter.MyLoadler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.frag02_circle_rlist_item, viewGroup, false);
        MyLoadler myLoadler = new MyLoadler(view);

        return myLoadler;
    }

    @Override
    public void onBindViewHolder(@NonNull MyLoadler myHolder, int i) {

        CircleBean circle = list.get(i);

        myHolder.avatar.setImageURI(Uri.parse(circle.getHeadPic()));
        myHolder.nickname.setText(circle.getNickName()+"");
        myHolder.time.setText(circle.getCreateTime()+"");
        myHolder.textZan.setText(circle.getGreatNum()+"");
        myHolder.text.setText(circle.getContent());

        if (StringUtils.isEmpty(circle.getImage())) {
            myHolder.gridView.setVisibility(View.GONE);
        } else {
            myHolder.gridView.setVisibility(View.VISIBLE);
            String[] images = circle.getImage().split(",");

//            int imageCount = (int)(Math.random()*9)+1;
            int imageCount = images.length;

            int colNum;//列数
            if (imageCount == 1) {
                colNum = 1;
            } else if (imageCount == 2 || imageCount == 4) {
                colNum = 2;
            } else {
                colNum = 3;
            }
            myHolder.imageAdapter.clear();//清空
//            for (int i = 0; i <imageCount ; i++) {
//                myHolder.imageAdapter.addAll(Arrays.asList(images));
//            }
            myHolder.imageAdapter.addAll(Arrays.asList(images));
            myHolder.gridLayoutManager.setSpanCount(colNum);//设置列数


            myHolder.imageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clearAll() {
        list.clear();
    }

    public void addAll(List<CircleBean> beanList) {
        if (beanList != null) {
            list.addAll(beanList);
        }
    }

    public class MyLoadler extends RecyclerView.ViewHolder {

        private final SimpleDraweeView avatar;
        private final TextView nickname;
        private final TextView time;
        private final TextView text;
        private final TextView textZan;
        private final RecyclerView gridView;
        private final GridLayoutManager gridLayoutManager;
        private final ImageAdapter imageAdapter;

        public MyLoadler(@NonNull View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);
            textZan = itemView.findViewById(R.id.textView_circle);
            nickname = itemView.findViewById(R.id.nickname);
            time = itemView.findViewById(R.id.time);
            gridView = itemView.findViewById(R.id.grid_view);
            imageAdapter = new ImageAdapter();
            int space = context.getResources().getDimensionPixelSize(R.dimen.dip_10);
            //图片间距
            gridLayoutManager = new GridLayoutManager(context, 3);
            gridView.addItemDecoration(new SpacingItemDecoration(space));
            gridView.setLayoutManager(gridLayoutManager);
            gridView.setAdapter(imageAdapter);
        }
    }
}
