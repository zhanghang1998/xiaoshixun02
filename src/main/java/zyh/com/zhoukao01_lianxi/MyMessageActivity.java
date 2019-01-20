package zyh.com.zhoukao01_lianxi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import zyh.com.bean.MyBean;

public class MyMessageActivity extends AppCompatActivity {

    @BindView(R.id.textView_myMessage_phone)
    TextView textPwd;
    @BindView(R.id.textView_myMessage_name)
    TextView textName;
    @BindView(R.id.imageView_myMessage_head)
    SimpleDraweeView image01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        //控件初始化
        ButterKnife.bind(this);

        //获取值
        Intent intent = this.getIntent();
        MyBean myBean = (MyBean) intent.getSerializableExtra("myBean");

        //赋值
        textPwd.setText(myBean.getPhone()+"");
        textName.setText(myBean.getNickName()+"");
        image01.setImageURI(Uri.parse(myBean.getHeadPic()));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
