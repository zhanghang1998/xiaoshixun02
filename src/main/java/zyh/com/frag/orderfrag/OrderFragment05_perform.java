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

import zyh.com.adapter.orderadapter.OrderFrag02Adapter;
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

public class OrderFragment05_perform extends Fragment {

    private RecyclerView rOrderFrag05;
    private UserInfo userInfo;
    private OrderFrag05Adapter orderFrag05Adapter;
    private QueryOrderPresenter queryOrderPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag04_frag05_perform,container, false);
//获取控件
        rOrderFrag05 = view.findViewById(R.id.recyclerView_orderFrag05);

        //获取greendao数据库 ; 从而可以获取网络连接需要的参数
        UserInfoDao userInfoDao = DaoMaster.newDevSession(getActivity(),UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
        if (userInfos!=null&&userInfos.size()>0){
            //读取第一项
            userInfo = userInfos.get(0);
        }

        //订单列表样式设置
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rOrderFrag05.setLayoutManager(manager);
        //调用presenter 层
        queryOrderPresenter = new QueryOrderPresenter(new queryOrde05());

        //适配器
        orderFrag05Adapter = new OrderFrag05Adapter(getActivity());
        rOrderFrag05.setAdapter(orderFrag05Adapter);

        //开启数据请求
        queryOrderPresenter.reqeust(userInfo.getUserId(),userInfo.getSessionId(),1,1,5);

        return view;
    }

    public class queryOrde05 implements DataCall<Result<List<OrderListBean>>> {

        @Override
        public void success(Result<List<OrderListBean>> result) {

            if (result.getStatus().equals("0000")) {
                List<OrderListBean> orderListBeans = result.getOrderList();
                orderFrag05Adapter.addAll(orderListBeans);
                orderFrag05Adapter.notifyDataSetChanged();
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
