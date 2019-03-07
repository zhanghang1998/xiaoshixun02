package zyh.com.zhoukao01_lianxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zyh.com.adapter.myadapter.MyAddressAdapter;
import zyh.com.bean.Result;
import zyh.com.bean.UserInfo;
import zyh.com.bean.my.MyAddressBean;
import zyh.com.core.DataCall;
import zyh.com.core.db.DaoMaster;
import zyh.com.core.db.UserInfoDao;
import zyh.com.core.exception.ApiException;
import zyh.com.presenter.my.MyQueryAddressPresenter;
import zyh.com.util.UIUtils;

public class MyAddressActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView_address)
    RecyclerView recyclerView;
    private MyQueryAddressPresenter myQueryAddressPresenter;
    private MyAddressAdapter myAddressAdapter;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        ButterKnife.bind(this);
        //获取greendao数据库 ; 从而可以获取网络连接需要的参数
        UserInfoDao userInfoDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
        if (userInfos!=null&&userInfos.size()>0){
            //读取第一项
            userInfo = userInfos.get(0);
        }

        //调用presenter层
        myQueryAddressPresenter = new MyQueryAddressPresenter(new queryMyaddress());
        //列表样式
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //适配器
        myAddressAdapter = new MyAddressAdapter(this);
        recyclerView.setAdapter(myAddressAdapter);

        myQueryAddressPresenter.reqeust(userInfo.getUserId(),userInfo.getSessionId());

    }

    //点击跳转新增页面
    @OnClick(R.id.button_address_add)
    public void instants(){
        Intent intent = new Intent(this,AddMyAddressActivity.class);
        startActivity(intent);
        finish();
    }

    //点击完成按钮
    @OnClick(R.id.textView_myMessage_perfect)
    public void freshs(){
        finish();
    }

    public class queryMyaddress implements DataCall<Result<List<MyAddressBean>>> {

        @Override
        public void success(Result<List<MyAddressBean>> result) {


            if (result.getStatus().equals("0000")) {

                List<MyAddressBean> myAddressBeans = result.getResult();
                myAddressAdapter.allClear();//清空集合
                myAddressAdapter.addAll(myAddressBeans);
                myAddressAdapter.notifyDataSetChanged();

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
        myQueryAddressPresenter.unBind();
        finish();
    }
}
