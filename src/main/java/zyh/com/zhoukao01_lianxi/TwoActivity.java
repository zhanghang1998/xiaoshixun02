package zyh.com.zhoukao01_lianxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zyh.com.bean.Result;
import zyh.com.core.DataCall;
import zyh.com.core.exception.ApiException;
import zyh.com.presenter.RegisterPresenter;
import zyh.com.util.MD5Utils;
import zyh.com.util.UIUtils;

public class TwoActivity extends AppCompatActivity {

    @BindView(R.id.editText_phone_two)EditText mPhone;
    @BindView(R.id.editText_password_two)EditText mPwd;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        ButterKnife.bind(this);

        presenter = new RegisterPresenter(new register());

    }

    @OnClick(R.id.button_zhuce_two)
    public void requests(){

        String p = mPhone.getText().toString().trim();
        String w = mPwd.getText().toString().trim();
        if (p.equals("")&&w.equals("")) {
            Toast.makeText(this, "输入框不能为空!",Toast.LENGTH_SHORT).show();
            return;
        }
        presenter.reqeust(p,w);
    }

    public class register implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getBaseContext(), data.getMessage(),Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TwoActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }

    @OnClick(R.id.text_yiyou_two)
    public void yiyou(){
        //已有账户? 立即登录
        startActivity(new Intent(TwoActivity.this,MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unBind();
        finish();
    }

}
