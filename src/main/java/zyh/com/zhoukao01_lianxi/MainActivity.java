package zyh.com.zhoukao01_lianxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zyh.com.bean.Result;
import zyh.com.bean.UserInfo;
import zyh.com.core.DataCall;
import zyh.com.core.WDApplication;
import zyh.com.core.db.DaoMaster;
import zyh.com.core.db.UserInfoDao;
import zyh.com.core.exception.ApiException;
import zyh.com.presenter.RequestPresenter;
import zyh.com.util.MD5Utils;
import zyh.com.util.UIUtils;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editText_phone)EditText mPhone;
    @BindView(R.id.editText_password)EditText mPwd;
    @BindView(R.id.checkBox_jizhu)CheckBox check;

    private RequestPresenter requestPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //获取greendao数据库 ; 从而可以获取网络连接需要的参数
        UserInfoDao userInfoDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();

        //自动登录
        if (!userInfos.isEmpty()){
            Intent intent = new Intent(this, ShowActivity.class);
            startActivity(intent);
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        ButterKnife.bind(this);

        requestPresenter = new RequestPresenter(new RegisterCall());

        boolean remPas = WDApplication.getShare().getBoolean("remPas",true);
        if (remPas){
            check.setChecked(true);
            mPhone.setText(WDApplication.getShare().getString("mobile",""));
            mPwd.setText(WDApplication.getShare().getString("pas",""));
        }

    }

    @OnClick(R.id.button_denglu)
    public void register(){
        String mobile = mPhone.getText().toString().trim();
        String pass = mPwd.getText().toString().trim();
        if (mobile.equals("")&&pass.equals("")) {
            Toast.makeText(MainActivity.this, "输入框不能为空!",Toast.LENGTH_SHORT).show();
            return;
        }
        if (check.isChecked()){
            WDApplication.getShare().edit().putString("mobile",mobile)
                    .putString("pas",pass).commit();
        }

        requestPresenter.reqeust(mobile,MD5Utils.md5(pass));
        //Log.v("zyh","<手机号:"+mobile+"><密码:"+MD5Utils.md5(pass)+">");
    }

    //数据回调 , 登录的
    public class RegisterCall implements DataCall<Result<UserInfo>> {

        @Override
        public void success(Result<UserInfo> data) {

            if (data.getStatus().equals("0000")) {
                //Log.v("zyh","<SessionId:"+data.getResult().getSessionId()+"><UserId:"+data.getResult().getUserId()+">");
                //把个人信息存入数据库(greendao)
                UserInfo result = data.getResult();
                result.setStatus(1);//设置登录状态，保存到数据库
                UserInfoDao userInfoDao = DaoMaster.newDevSession(getBaseContext(),UserInfoDao.TABLENAME).getUserInfoDao();
                userInfoDao.insertOrReplace(result);
                //吐司 , 登录成功
                Toast.makeText(getBaseContext(), data.getMessage(),Toast.LENGTH_SHORT).show();
                //跳转页面
                startActivity(new Intent(MainActivity.this, ShowActivity.class));
                finish();
            } else {
                Toast.makeText(MainActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }

    private boolean pasVisibile = false;
    //点击眼睛事件方法
    @OnClick(R.id.imageView_mian_eye)
    public void eyeMy(){
        if (pasVisibile){//密码显示，则隐藏
            mPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pasVisibile = false;
        }else{//密码隐藏则显示
            mPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            pasVisibile = true;
        }
    }

    //点击事件 , 存储记住密码按钮状态
    @OnClick(R.id.checkBox_jizhu)
    public void remPas(){
        WDApplication.getShare().edit().putBoolean("remPas",check.isChecked()).commit();
    }

    @OnClick(R.id.text_zhuce)
    public void zhuce(){
        //跳转到注册页面
        startActivity(new Intent(this, TwoActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        requestPresenter.unBind();
        finish();
    }
}
