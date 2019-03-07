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

import zyh.com.adapter.orderadapter.OrderFrag04Adapter;
import zyh.com.adapter.orderadapter.OrderFrag05Adapter;
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

public class OrderFragment04_comment extends Fragment {

    private RecyclerView rOrderFrag04;
    private UserInfo userInfo;
    private OrderFrag04Adapter orderFrag04Adapter;
    private QueryOrderPresenter queryOrderPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag04_frag04_comment, container, false);

        //获取控件
        rOrderFrag04 = view.findViewById(R.id.recyclerView_orderFrag04);

        //获取greendao数据库 ; 从而可以获取网络连接需要的参数
        UserInfoDao userInfoDao = DaoMaster.newDevSession(getActivity(),UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
        if (userInfos!=null&&userInfos.size()>0){
            //读取第一项
            userInfo = userInfos.get(0);
        }

        //订单列表样式设置
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rOrderFrag04.setLayoutManager(manager);
        //调用presenter 层
        queryOrderPresenter = new QueryOrderPresenter(new queryOrde04());

        //适配器
        orderFrag04Adapter = new OrderFrag04Adapter(getActivity());
        rOrderFrag04.setAdapter(orderFrag04Adapter);

        //开启数据请求
        queryOrderPresenter.reqeust(userInfo.getUserId(),userInfo.getSessionId(),4,1,10);

        return view;
    }

    public class queryOrde04 implements DataCall<Result<List<OrderListBean>>> {

        @Override
        public void success(Result<List<OrderListBean>> result) {

            if (result.getStatus().equals("0000")) {
                List<OrderListBean> orderListBeans = result.getOrderList();
                orderFrag04Adapter.addAll(orderListBeans);
                orderFrag04Adapter.notifyDataSetChanged();
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
