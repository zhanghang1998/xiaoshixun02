package zyh.com.frag.orderfrag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import zyh.com.adapter.orderadapter.OrderFrag01Adapter;
import zyh.com.adapter.orderadapter.OrderFrag02Adapter;
import zyh.com.bean.Result;
import zyh.com.bean.UserInfo;
import zyh.com.bean.order.OrderListBean;
import zyh.com.core.DataCall;
import zyh.com.core.db.DaoMaster;
import zyh.com.core.db.UserInfoDao;
import zyh.com.core.exception.ApiException;
import zyh.com.presenter.QueryOrderPresenter;
import zyh.com.util.UIUtils;
import zyh.com.zhoukao01_lianxi.R;

public class OrderFragment01_all extends Fragment {

    private RecyclerView recyclerView;
    private UserInfo userInfo;
    private QueryOrderPresenter queryOrderPresenter;
    private OrderFrag01Adapter orderFrag01Adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag04_frag01_all, container, false);
        //获取控件
        recyclerView = view.findViewById(R.id.recyclerView_frag04_orderFrag01);
        //获取greendao数据库 ; 从而可以获取网络连接需要的参数
        UserInfoDao userInfoDao = DaoMaster.newDevSession(getActivity(),UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
        if (userInfos!=null&&userInfos.size()>0){
            //读取第一项
            userInfo = userInfos.get(0);
        }

        //订单列表样式设置
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        //调用presenter 层
        queryOrderPresenter = new QueryOrderPresenter(new queryAllOrde());

        //适配器
        orderFrag01Adapter = new OrderFrag01Adapter(getActivity());
        recyclerView.setAdapter(orderFrag01Adapter);

        //开启数据请求
        //queryOrderPresenter.reqeust(userInfo.getUserId(),userInfo.getSessionId(),0,1);

        return view;
    }

    private class queryAllOrde implements DataCall<Result<List<OrderListBean>>> {
        @Override
        public void success(Result<List<OrderListBean>> result) {
            if (result.getStatus().equals("0000")) {
                List<OrderListBean> orderListBeans = result.getOrderList();
                orderFrag01Adapter.addAll(orderListBeans);
                orderFrag01Adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+""+e.getDisplayMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        queryOrderPresenter.unBind();
    }
}
