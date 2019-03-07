package zyh.com.frag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import zyh.com.adapter.HomeMlssAdapter;
import zyh.com.adapter.HomePzshAdapter;
import zyh.com.adapter.HomeRexiaoAdapter;
import zyh.com.bean.JsonBanner;
import zyh.com.bean.Result;
import zyh.com.bean.home.CommodityListBean;
import zyh.com.bean.home.HomeJson;
import zyh.com.core.DataCall;
import zyh.com.core.exception.ApiException;
import zyh.com.presenter.BannerPresenter;
import zyh.com.presenter.HomePresenter;
import zyh.com.util.UIUtils;
import zyh.com.zhoukao01_lianxi.R;
import zyh.com.zhoukao01_lianxi.SearchActivity;
import zyh.com.zhoukao01_lianxi.ShopCount02Activity;

public class Fragment01_home extends Fragment implements View.OnClickListener {

    private Banner banner;
    private BannerPresenter presenter;
    private List<JsonBanner> imageList;
    private ArrayList<String> strings;
    private RecyclerView rHomeRexiao,rHomeMoli,rHomePinzhi;
    private HomeRexiaoAdapter rexiaoAdapter;
    private HomePresenter homePresenter;
    private HomeMlssAdapter homeMlssAdapter;
    private HomePzshAdapter homePzshAdapter;
    private TextView textIntent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag01_home_item, container, false);
        //获取控件
        banner = view.findViewById(R.id.banner_frag01);
        rHomeRexiao = view.findViewById(R.id.recyclerView_rexiao_home);
        rHomeMoli = view.findViewById(R.id.recyclerView_molio_home);
        rHomePinzhi = view.findViewById(R.id.recyclerView_pinzhi_home);
        view.findViewById(R.id.textView_home_sousuo_stat).setOnClickListener(this);
        //调用方法
        presenter = new BannerPresenter(new BannerData());
        homePresenter = new HomePresenter(new homeData());
        presenter.reqeust();
        homePresenter.reqeust();
        //设置
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        rHomeRexiao.setLayoutManager(manager);//热销新品
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rHomeMoli.setLayoutManager(layoutManager);//魔力时尚
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        rHomePinzhi.setLayoutManager(gridLayoutManager);

        //适配器
        rexiaoAdapter = new HomeRexiaoAdapter(getActivity());
        rHomeRexiao.setAdapter(rexiaoAdapter);//热销展示
        //热销展示的接口回调
        rexiaoAdapter.setOnItemClickListener(new HomeRexiaoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int cid) {
                Toast.makeText(getActivity(), cid + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ShopCount02Activity.class);
                intent.putExtra("goodsId", cid);
                startActivity(intent);
            }
        });
        homeMlssAdapter = new HomeMlssAdapter(getActivity());
        rHomeMoli.setAdapter(homeMlssAdapter);//魔力时尚
        homePzshAdapter = new HomePzshAdapter(getActivity());
        rHomePinzhi.setAdapter(homePzshAdapter);//品质生活
        //数据请求
        homePresenter.reqeust();

        return view;
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){//点击进入搜索页面
            case R.id.textView_home_sousuo_stat:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    //首页商品数据回传
    public class homeData implements DataCall<Result<HomeJson>>{

        @Override
        public void success(Result<HomeJson> data) {

            if (data.getStatus().equals("0000")) {
                //热销新品
                List<CommodityListBean> rxxpList = data.getResult().getRxxp().getCommodityList();
                rexiaoAdapter.addAll(rxxpList);
                rexiaoAdapter.notifyDataSetChanged();//刷新适配器
                //魔力时尚
                List<CommodityListBean> mlssList1 = data.getResult().getMlss().getCommodityList();
                homeMlssAdapter.addAll(mlssList1);
                homeMlssAdapter.notifyDataSetChanged();//刷新适配器
                //品质生活
                List<CommodityListBean> pzshList1 = data.getResult().getPzsh().getCommodityList();
                homePzshAdapter.addAll(pzshList1);
                homePzshAdapter.notifyDataSetChanged();//刷新适配器
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+""+e.getDisplayMessage());
        }
    }

    //轮播图方法
    public void banner(){
        //创建图片集合
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImages(strings);
        banner.setBannerAnimation(Transformer.FlipHorizontal);
        banner.setDelayTime(3000);
        banner.setImageLoader(new GlideImageLoader());
        banner .start();
    }

    //轮播图数据回调
    public class BannerData implements DataCall<Result<List<JsonBanner>>> {

        @Override
        public void success(Result<List<JsonBanner>> data) {

            if (data.getStatus().equals("0000")) {
                imageList = data.getResult();
                strings = new ArrayList<>();
                for (int i = 0; i < imageList.size(); i++) {
                    JsonBanner jsonBanner = imageList.get(i);
                    String imageUrl = jsonBanner.getImageUrl();
                    strings.add(imageUrl);
                }
                //调用轮播方法
                banner();
            }else{
                Toast.makeText(getContext(), "轮播图请求失败!",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+""+e.getDisplayMessage());
        }
    }

    //轮播图加载方法
    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unBind();
        homePresenter.unBind();
    }
}
