package zyh.com.frag.orderfrag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

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

public class OrderFragment02_obligation extends Fragment {

    private RecyclerView rOrderFrag02;
    private QueryOrderPresenter queryOrderPresenter;
    private OrderFrag02Adapter orderFrag02Adapter;
    private UserInfo userInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag04_frag02_payment, container, false);
        //获取控件
        rOrderFrag02 = view.findViewById(R.id.recyclerView_orderFrag02);

        //获取greendao数据库 ; 从而可以获取网络连接需要的参数
        UserInfoDao userInfoDao = DaoMaster.newDevSession(getActivity(),UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
        if (userInfos!=null&&userInfos.size()>0){
            //读取第一项
            userInfo = userInfos.get(0);
        }

        //订单列表样式设置
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rOrderFrag02.setLayoutManager(manager);
        //调用presenter 层
        queryOrderPresenter = new QueryOrderPresenter(new queryOrde());

        //适配器
        orderFrag02Adapter = new OrderFrag02Adapter(getActivity());
        rOrderFrag02.setAdapter(orderFrag02Adapter);

        //开启数据请求
        queryOrderPresenter.reqeust(userInfo.getUserId(),userInfo.getSessionId(),2,1,510);

        return view;
    }

    public class queryOrde implements DataCall<Result<List<OrderListBean>>> {

        @Override
        public void success(Result<List<OrderListBean>> result) {

            if (result.getStatus().equals("0000")) {
                List<OrderListBean> orderListBeans = result.getOrderList();
                orderFrag02Adapter.addAll(orderListBeans);
                orderFrag02Adapter.notifyDataSetChanged();
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
