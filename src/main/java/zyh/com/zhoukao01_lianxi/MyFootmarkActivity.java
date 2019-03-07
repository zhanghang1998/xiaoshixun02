package zyh.com.zhoukao01_lianxi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zyh.com.adapter.myadapter.MyFootmarkAdapter;
import zyh.com.bean.Result;
import zyh.com.bean.UserInfo;
import zyh.com.bean.my.MyFootmarkBean;
import zyh.com.core.DataCall;
import zyh.com.core.db.DaoMaster;
import zyh.com.core.db.UserInfoDao;
import zyh.com.core.exception.ApiException;
import zyh.com.presenter.my.MyQueryFootmarkPresenter;
import zyh.com.util.UIUtils;

public class MyFootmarkActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView_Footmark)
    RecyclerView recyclerView;
    private UserInfo userInfo;
    private MyFootmarkAdapter myFootmarkAdapter;
    private MyQueryFootmarkPresenter myQueryFootmarkPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footmark);
        ButterKnife.bind(this);

        //获取greendao数据库 ; 从而可以获取网络连接需要的参数
        UserInfoDao userInfoDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
        if (userInfos!=null&&userInfos.size()>0){
            //读取第一项
            userInfo = userInfos.get(0);
        }

        myQueryFootmarkPresenter = new MyQueryFootmarkPresenter(new queryFootmark());

        //足迹页面列表显示样式
        //2.声名为瀑布流的布局方式: 3列,垂直方向
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        //适配器
        myFootmarkAdapter = new MyFootmarkAdapter(this);
        recyclerView.setAdapter(myFootmarkAdapter);

        myQueryFootmarkPresenter.reqeust(userInfo.getUserId(),userInfo.getSessionId());

    }

    public class queryFootmark implements DataCall<Result<List<MyFootmarkBean>>> {

        @Override
        public void success(Result<List<MyFootmarkBean>> result) {
            if (result.getStatus().equals("0000")) {

                List<MyFootmarkBean> myFootmarkBeans = result.getResult();
                myFootmarkAdapter.CallClear();
                myFootmarkAdapter.addAll(myFootmarkBeans);
                myFootmarkAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myQueryFootmarkPresenter.unBind();
        finish();
    }
}
