package zyh.com.zhoukao01_lianxi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import zyh.com.adapter.myadapter.MyCircleAdapter;
import zyh.com.bean.Result;
import zyh.com.bean.UserInfo;
import zyh.com.bean.my.MyCircleBean;
import zyh.com.core.DataCall;
import zyh.com.core.db.DaoMaster;
import zyh.com.core.db.UserInfoDao;
import zyh.com.core.exception.ApiException;
import zyh.com.presenter.my.MyQueryCirclePresenter;
import zyh.com.util.UIUtils;
import zyh.com.zhoukao01_lianxi.R;

public class MyCircleActivity extends AppCompatActivity {

    private UserInfo userInfo;
    private MyQueryCirclePresenter myQueryCirclePresenter;
    private TextView textDelete;
    private RecyclerView rCircleList;
    private MyCircleAdapter myCircleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle);
        //获取greendao数据库 ; 从而可以获取网络连接需要的参数
        UserInfoDao userInfoDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
        if (userInfos!=null&&userInfos.size()>0){
            //读取第一项
            userInfo = userInfos.get(0);
        }
        //获取控件
        textDelete = findViewById(R.id.textView_circle_delete);
        rCircleList = findViewById(R.id.recyclerView_myCircle);
        //布局管理器
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        rCircleList.setLayoutManager(linearLayoutManager);
        //调用p层
        myQueryCirclePresenter = new MyQueryCirclePresenter(new getCircle());
        //适配器
        myCircleAdapter = new MyCircleAdapter(this);
        rCircleList.setAdapter(myCircleAdapter);

        myQueryCirclePresenter.reqeust(userInfo.getUserId(),userInfo.getSessionId());
    }

    public class getCircle implements DataCall<Result<List<MyCircleBean>>> {

        @Override
        public void success(Result<List<MyCircleBean>> result) {

            if (result.getStatus().equals("0000")) {

                List<MyCircleBean> circleBeanList = result.getResult();
                Log.v("zyh",circleBeanList.toString()+"");

                myCircleAdapter.CallClear();//清空
                myCircleAdapter.addAll(circleBeanList);
                myCircleAdapter.notifyDataSetChanged();//刷新适配器
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+""+e.getDisplayMessage());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        myQueryCirclePresenter.unBind();
        finish();
    }
}
