package zyh.com.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import zyh.com.adapter.QueryShoppingAdapter;
import zyh.com.bean.QueryShoppingBean;
import zyh.com.bean.Result;
import zyh.com.bean.UserInfo;
import zyh.com.core.DataCall;
import zyh.com.core.db.DaoMaster;
import zyh.com.core.db.UserInfoDao;
import zyh.com.core.exception.ApiException;
import zyh.com.presenter.QueryShoppingPresenter;
import zyh.com.util.UIUtils;
import zyh.com.zhoukao01_lianxi.R;

public class Fragment03_shop extends Fragment {

    private XRecyclerView xRecyclerView;
    private QueryShoppingPresenter queryShoppingPresenter;
    private QueryShoppingAdapter queryShoppingAdapter;
    private UserInfo userInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag03_shop_item, container, false);

        //获取greendao数据库 ; 从而可以获取网络连接需要的参数
        UserInfoDao userInfoDao = DaoMaster.newDevSession(getActivity(),UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
        if (userInfos!=null&&userInfos.size()>0){
            //读取第一项
            userInfo = userInfos.get(0);
        }

        //获取控件
        xRecyclerView = view.findViewById(R.id.xRecyclerView_frag03_xlist);

        //调用presenter层
        queryShoppingPresenter = new QueryShoppingPresenter(new queryShopping());
        //购物车列表样式设置
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        xRecyclerView.setLayoutManager(manager);
        //适配器
        queryShoppingAdapter = new QueryShoppingAdapter(getActivity());
        xRecyclerView.setAdapter(queryShoppingAdapter);
        //数据方法调用
        queryShoppingPresenter.reqeust(userInfo.getUserId(),userInfo.getSessionId());

        return view;
    }

    //查询购车 , 信息回传
    public class queryShopping implements DataCall<Result<List<QueryShoppingBean>>> {

        @Override
        public void success(Result<List<QueryShoppingBean>> result) {

            if (result.getStatus().equals("0000")) {
                //
                List<QueryShoppingBean> queryShoppingBeans = result.getResult();
                queryShoppingAdapter.addAll(queryShoppingBeans);
                queryShoppingAdapter.notifyDataSetChanged();//刷新适配器
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+""+e.getDisplayMessage());
        }
    }

    //解绑queryShoppingPresenter
    @Override
    public void onDestroy() {
        super.onDestroy();
        queryShoppingPresenter.unBind();
    }
}
