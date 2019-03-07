package zyh.com.zhoukao01_lianxi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import zyh.com.bean.AddCarBean;
import zyh.com.bean.QueryShoppingBean;
import zyh.com.bean.Result;
import zyh.com.bean.ShopCountBean;
import zyh.com.bean.UserInfo;
import zyh.com.core.DataCall;
import zyh.com.core.db.DaoMaster;
import zyh.com.core.db.UserInfoDao;
import zyh.com.core.exception.ApiException;
import zyh.com.presenter.AddShopPresenter;
import zyh.com.presenter.QueryShoppingPresenter;
import zyh.com.presenter.ShopCountPresenter;
import zyh.com.util.UIUtils;

public class ShopCount02Activity extends AppCompatActivity implements View.OnClickListener {


    private int cid;
    private ShopCountBean shopCountBean;
    private ShopCountPresenter shopCountPresenter;
    private TextView textPrice,textNmae,textCount;
    private SimpleDraweeView imageSimp;
    private SimpleDraweeView imageSimp02;
    private Banner banner;
    private List<AddCarBean> addCarBean = new ArrayList<AddCarBean>();
    private LinearLayout linearLayout;
    private UserInfo userInfo;
    private AddShopPresenter addShopPresenter;
    private QueryShoppingPresenter queryShoppingPresenter;
    private List<QueryShoppingBean> queryShoppingBeans;
    private int intExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_count02);
        //获取greendao数据库 ; 从而可以获取网络连接需要的参数
        UserInfoDao userInfoDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
        if (userInfos!=null&&userInfos.size()>0){
            //读取第一项
            userInfo = userInfos.get(0);
        }
        //获取控件
        banner = findViewById(R.id.goodsContentPage_ViewPager);
        textPrice = findViewById(R.id.goodsContent_goods_Price);
        imageSimp = findViewById(R.id.goodsContent_product_contentImg);
        imageSimp02 = findViewById(R.id.goodsContent_product_introductionImg);
        textNmae = findViewById(R.id.goodsContent_product_introductionText);
        textCount = findViewById(R.id.goodsContent_goods_title);
        findViewById(R.id.linearLayout_che_addbur).setOnClickListener(this);
        //调用
        shopCountPresenter = new ShopCountPresenter(new shopCount());
        addShopPresenter = new AddShopPresenter(new addShop());
        queryShoppingPresenter = new QueryShoppingPresenter(new selectshop());
        //获取传送过来的商品id
        Intent intent = getIntent();
        intExtra = intent.getIntExtra("goodsId",cid);
        //根据商品id查询详细信息
        shopCountPresenter.reqeust(userInfo.getUserId(),userInfo.getSessionId(),intExtra);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearLayout_che_addbur://添加购物车
                //查询购物车
                queryShoppingPresenter.reqeust(userInfo.getUserId(),userInfo.getSessionId());

                break;
        }
    }

    //同步购物车前的 , 查询购物车操作
    public class selectshop implements DataCall<Result<List<QueryShoppingBean>>>{

        @Override
        public void success(Result<List<QueryShoppingBean>> result) {

            if (result.getStatus().equals("0000")) {
                queryShoppingBeans = result.getResult();
                //遍历放到添加购物车的集合中
                for (int i=0;i<queryShoppingBeans.size();i++){
                    addCarBean.add(new AddCarBean(queryShoppingBeans.get(i).getCommodityId(),queryShoppingBeans.get(i).getCount()));
                }
                //添加购物车时进行判断
                addShopList(addCarBean);
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+""+e.getDisplayMessage());
        }
    }

    //拼接要同步购物车的数据
    public void addShopList(List<AddCarBean> beans){

        for (int i=0;i<beans.size();i++){
            //判断如果加入商品的id和集合里有相同的就count+1
            if (Integer.valueOf(intExtra)==beans.get(i).getCommodityId()){
                int count = beans.get(i).getCount();
                count++;
                beans.get(i).setCount(count);
                break;
                //如果遍历完毕没有相同的商品，就把当前的商品加入到购物车
            }else if (i==beans.size()-1){
                addCarBean.add(new AddCarBean(Integer.valueOf(intExtra),1));
                break;
            }
        }
        //把集合转换成String类型 , 然后进行同步
        Gson gson = new Gson();
        String s = gson.toJson(addCarBean);
        addShopPresenter.reqeust(userInfo.getUserId(),userInfo.getSessionId(),s);

    }

    //同步购物车方法回传
    public class addShop implements DataCall<Result>{

        @Override
        public void success(Result result) {
            //吐司
            Toast.makeText(ShopCount02Activity.this, result.getMessage()+"",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+""+e.getDisplayMessage());
        }
    }

    //商品详情数据
    public class shopCount implements DataCall<Result<ShopCountBean>> {

        @Override
        public void success(Result<ShopCountBean> result) {

            shopCountBean = result.getResult();
            textPrice.setText(shopCountBean.getPrice()+"");
            textCount.setText(shopCountBean.getCategoryName()+"");
            textNmae.setText(shopCountBean.getDescribe()+"");
            //图片需要切割一下子
            String[] imageMy = shopCountBean.getPicture().split(",");
            imageSimp.setImageURI(Uri.parse(imageMy[0]));
            imageSimp02.setImageURI(Uri.parse(imageMy[2]));

        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+""+e.getDisplayMessage());
        }
    }

    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        shopCountPresenter.unBind();
        addShopPresenter.unBind();
        queryShoppingPresenter.unBind();
        finish();
    }

}
