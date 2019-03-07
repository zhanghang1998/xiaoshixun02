package zyh.com.frag;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import zyh.com.adapter.QueryShoppingAdapter;
import zyh.com.bean.AddCarBean;
import zyh.com.bean.QueryShoppingBean;
import zyh.com.bean.Result;
import zyh.com.bean.UserInfo;
import zyh.com.core.DataCall;
import zyh.com.core.db.DaoMaster;
import zyh.com.core.db.UserInfoDao;
import zyh.com.core.exception.ApiException;
import zyh.com.presenter.AddShopPresenter;
import zyh.com.presenter.QueryShoppingPresenter;
import zyh.com.util.UIUtils;
import zyh.com.zhoukao01_lianxi.CloseActivity;
import zyh.com.zhoukao01_lianxi.R;

public class Fragment03_shop extends Fragment implements XRecyclerView.LoadingListener {

    private XRecyclerView xRecyclerView;
    private QueryShoppingPresenter queryShoppingPresenter;
    private QueryShoppingAdapter queryShoppingAdapter;
    private List<QueryShoppingBean> queryShoppingBeans = new ArrayList<>();
    private List<QueryShoppingBean> creation_bill;
    private UserInfo userInfo;
    private Button btu_close;
    private TextView price_num;
    private CheckBox check_all;
    private AddShopPresenter addShopPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag03_shop_item, container, false);

        //获取greendao数据库 ; 从而可以获取网络连接需要的参数
        UserInfoDao userInfoDao = DaoMaster.newDevSession(getActivity(), UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
        if (userInfos != null && userInfos.size() > 0) {
            //读取第一项
            userInfo = userInfos.get(0);
        }

        //获取控件
        xRecyclerView = view.findViewById(R.id.xRecyclerView_frag03_xlist);
        check_all = view.findViewById(R.id.checkBox_all_price);//全选
        price_num = view.findViewById(R.id.textView_frag03_Total);//总价格
        btu_close = view.findViewById(R.id.button_frag03_close);//去结算按钮

        //调用presenter层
        queryShoppingPresenter = new QueryShoppingPresenter(new queryShopping());
        addShopPresenter = new AddShopPresenter(new deleteKeys());
        //购物车列表样式设置
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        xRecyclerView.setLayoutManager(manager);
        xRecyclerView.setLoadingListener(this);
        //适配器
        queryShoppingAdapter = new QueryShoppingAdapter(getActivity());
        xRecyclerView.setAdapter(queryShoppingAdapter);
        //默认加载
        xRecyclerView.refresh();

        initClass();
        return view;
    }

    //点击和计算方法
    public void initClass() {

        //子条目点击事件,是否选中
        queryShoppingAdapter.setOnClick(new QueryShoppingAdapter.ShopClick() {
            @Override
            public void shopPrice(List<QueryShoppingBean> list) {

                //在这里重新遍历已经改变状态后的数据
                //这里不能break跳出，因为还有需要计算后面点击商品的价格和数量，所以必须跑完整个循环
                double totalPrice = 0;
                //勾选商品的数量，不是该商品购买的数量
                int num = 0;
                //所有商品总数，和上面的数量做比对，如果两者相等，则说明全选
                int totalNum = 0;
                for (int i = 0; i < list.size(); i++) {
                    totalNum = totalNum + list.get(i).getCount();
                    if (list.get(i).isIscheck()) {
                        totalPrice = totalPrice + list.get(i).getPrice() * list.get(i).getCount();
                        num = num + list.get(i).getCount();
                    }
                }
                if (num < totalNum) {
                    check_all.setChecked(false);
                } else {
                    check_all.setChecked(true);
                }
                price_num.setText("" + totalPrice);//总的计算价格
                btu_close.setText("去结算(" + num + ")");//去结算按钮

            }
        });

        //删除子条目
        queryShoppingAdapter.setRemove(new QueryShoppingAdapter.RemoveCallBack() {
            @Override
            public void removeposition(List<QueryShoppingBean> list, int position) {
                //在这里重新遍历已经改变状态后的数据
                //这里不能break跳出，因为还有需要计算后面点击商品的价格和数量，所以必须跑完整个循环
                double totalPrice = 0;
                //勾选商品的数量，不是该商品购买的数量
                int num = 0;
                //所有商品总数，和上面的数量做比对，如果两者相等，则说明全选
                int totalNum = 0;
                for (int i = 0; i < list.size(); i++) {
                    totalNum = totalNum + list.get(i).getCount();
                    if (list.get(i).isIscheck()) {
                        totalPrice = totalPrice + list.get(i).getPrice() * list.get(i).getCount();
                        num = num + list.get(i).getCount();
                    }

                }
                if (num < totalNum) {
                    check_all.setChecked(false);
                } else {
                    check_all.setChecked(true);
                }
                price_num.setText("" + totalPrice);
                btu_close.setText("去结算(" + num + ")");
                //添加购物车的集合
                List<AddCarBean> addlist = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    int commodityId = list.get(i).getCommodityId();
                    int count = list.get(i).getCount();
                    addlist.add(new AddCarBean(Integer.valueOf(commodityId), count));
                }
                //变成String类型
                Gson gson = new Gson();
                String s = gson.toJson(addlist);
                //删除接口调用
                addShopPresenter.reqeust(userInfo.getUserId(),userInfo.getSessionId(),s);
                if (list.size() == 0) {
                    check_all.setChecked(false);
                }

            }
        });

        //全选 , 反选
        check_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean allChecked = check_all.isChecked();

                double totalPrice=0;
                int num=0;

                for (int i=0;i<queryShoppingBeans.size();i++){
                    //遍历商品，改变状态
                    queryShoppingBeans.get(i).setIscheck(allChecked);
                    totalPrice=totalPrice+(queryShoppingBeans.get(i).getPrice()*queryShoppingBeans.get(i).getCount());
                    num=num+queryShoppingBeans.get(i).getCount();
                }

                if (allChecked){
                    price_num.setText(""+totalPrice);
                    btu_close.setText("去结算("+num+")");
                }else{
                    price_num.setText("0");
                    btu_close.setText("去结算");
                }
                queryShoppingAdapter.notifyDataSetChanged();
            }
        });

        //去结算 , 跳转页面
        btu_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取价格
                String toString = price_num.getText().toString();

                //判断价格
                if (!(toString.equals("0"))&&!(toString.equals("0.0"))){

                    Intent intent=new Intent(getActivity(),CloseActivity.class);

                    creation_bill = new ArrayList<>();
                    //判断商品是否被选中
                    //如果被选中就放到集合里，通过intent传到activity中
                    for (int i=0;i<queryShoppingBeans.size();i++){
                        //判断是否选中
                        if (queryShoppingBeans.get(i).isIscheck()) {
                            creation_bill.add(new QueryShoppingBean(
                                    queryShoppingBeans.get(i).getCommodityId(),
                                    queryShoppingBeans.get(i).getCommodityName(),
                                    queryShoppingBeans.get(i).getCount(),
                                    queryShoppingBeans.get(i).getPic(),
                                    queryShoppingBeans.get(i).getPrice()
                            ));
                        }
                    }
                    Log.v("zyh","closeBean>> bk"+creation_bill.toString());
                    intent.putExtra("creation_bill", (Serializable) creation_bill);
                    startActivity(intent);
                }//判断价格
            }
        });//去结算 , 跳转页面

    }

    @Override
    public void onRefresh() {
        //获取basepresenter里的布尔值
        if (queryShoppingPresenter.isRunnimg()) {
            xRecyclerView.refreshComplete();
            return;
        }
        //数据方法调用
        queryShoppingPresenter.reqeust(userInfo.getUserId(), userInfo.getSessionId());
    }

    @Override
    public void onLoadMore() {
        //获取basepresenter里的布尔值
        if (queryShoppingPresenter.isRunnimg()) {
            xRecyclerView.loadMoreComplete();
            return;
        }
        //数据方法调用
        queryShoppingPresenter.reqeust(userInfo.getUserId(), userInfo.getSessionId());
    }

    //查询购车 , 信息回传
    public class queryShopping implements DataCall<Result<List<QueryShoppingBean>>> {

        @Override
        public void success(Result<List<QueryShoppingBean>> result) {
            xRecyclerView.refreshComplete();
            xRecyclerView.loadMoreComplete();
            if (result.getStatus().equals("0000")) {
                //
                queryShoppingBeans = result.getResult();
                queryShoppingAdapter.ClearAll();
                queryShoppingAdapter.addAll(queryShoppingBeans);
                queryShoppingAdapter.notifyDataSetChanged();//刷新适配器
                //check_all.setChecked(false);
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode() + "" + e.getDisplayMessage());
        }
    }

    //删除成功回调的方法
    public class deleteKeys implements DataCall<Result>{

        @Override
        public void success(Result result) {

            if (result.getStatus().equals("0000")) {
                //吐司
                Toast.makeText(getActivity(), "删除成功",Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode() + "" + e.getDisplayMessage());
        }
    }

    //解绑queryShoppingPresenter
    @Override
    public void onDestroy() {
        super.onDestroy();
        queryShoppingPresenter.unBind();
    }
}
