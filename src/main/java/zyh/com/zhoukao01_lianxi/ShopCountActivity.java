package zyh.com.zhoukao01_lianxi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import zyh.com.bean.Result;
import zyh.com.bean.ShopCountBean;
import zyh.com.core.DataCall;
import zyh.com.core.exception.ApiException;
import zyh.com.presenter.ShopCountPresenter;

public class ShopCountActivity extends AppCompatActivity {

    private long num=980;
    private int cid;
    private ShopCountPresenter shopCountPresenter;
    private TextView textPrice,textNmae,textCount;
    private SimpleDraweeView imageSimp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_count);
        //获取控件
        textPrice = findViewById(R.id.textView_shopCount_price);
        imageSimp = findViewById(R.id.imageView_shopCount_zhanshi);
        textNmae = findViewById(R.id.textView_shopCount_name);
        textCount = findViewById(R.id.textView_shopCount_neirong);

        //调用
        shopCountPresenter = new ShopCountPresenter(new shopCount());
        Intent intent = getIntent();
        int intExtra = intent.getIntExtra("goodsId",cid);

        shopCountPresenter.reqeust(num,"1546909809148980",intExtra);
    }

    public class shopCount implements DataCall<Result<ShopCountBean>> {

        @Override
        public void success(Result<ShopCountBean> result) {

            ShopCountBean shopCountBean = result.getResult();
            textPrice.setText(shopCountBean.getPrice()+"");
            textNmae.setText(shopCountBean.getCategoryName()+"");
            textNmae.setText(shopCountBean.getDescribe()+"");
            //图片需要切割一下子
            String[] imageMy = shopCountBean.getPicture().split(",");
            imageSimp.setImageURI(Uri.parse(imageMy[0]));

        }

        @Override
        public void fail(ApiException e) {

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        shopCountPresenter.unBind();
        finish();
    }

}
