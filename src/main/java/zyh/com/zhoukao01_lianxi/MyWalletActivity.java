package zyh.com.zhoukao01_lianxi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import zyh.com.adapter.myadapter.MyWalletAdapter;
import zyh.com.bean.Result;
import zyh.com.bean.UserInfo;
import zyh.com.bean.my.DetailListBean;
import zyh.com.bean.my.MyWalletBean;
import zyh.com.core.DataCall;
import zyh.com.core.db.DaoMaster;
import zyh.com.core.db.UserInfoDao;
import zyh.com.core.exception.ApiException;
import zyh.com.presenter.my.MyQueryWalletPresenter;
import zyh.com.util.UIUtils;

public class MyWalletActivity extends AppCompatActivity {

    private TextView textViewNum;
    private MyQueryWalletPresenter myQueryWalletPresenter;
    private MyWalletAdapter myWalletAdapter;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wal);
        //获取greendao数据库 ; 从而可以获取网络连接需要的参数
        UserInfoDao userInfoDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
        if (userInfos!=null&&userInfos.size()>0){
            //读取第一项
            userInfo = userInfos.get(0);
        }
        //获取控件
        textViewNum = findViewById(R.id.textView_wallet_balance);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_wallet_listView);
        //调用p层
        myQueryWalletPresenter = new MyQueryWalletPresenter(new getWallet());
        //适配器
        myWalletAdapter = new MyWalletAdapter(this);
        recyclerView.setAdapter(myWalletAdapter);
        //请求数据
        myQueryWalletPresenter.reqeust(userInfo.getUserId(),userInfo.getSessionId());
    }


    public class getWallet implements DataCall<Result<MyWalletBean>> {

        @Override
        public void success(Result<MyWalletBean> result) {

            if (result.getStatus().equals("0000")) {

                MyWalletBean resultResult = result.getResult();
                textViewNum.setText(resultResult.getBalance()+"");
                myWalletAdapter.CallClear();//清空适配器中集合的数据
                List<DetailListBean> detailList = resultResult.getDetailList();
                myWalletAdapter.addAll(detailList);//添加

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
        myQueryWalletPresenter.unBind();
        finish();
    }
}
