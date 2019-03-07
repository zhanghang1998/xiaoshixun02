package zyh.com.zhoukao01_lianxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zyh.com.adapter.CreatOrderAdapter;
import zyh.com.bean.AddCarBean;
import zyh.com.bean.QueryShoppingBean;
import zyh.com.bean.Result;
import zyh.com.bean.UserInfo;
import zyh.com.bean.my.MyAddressBean;
import zyh.com.bean.order.CreateBean;
import zyh.com.core.DataCall;
import zyh.com.core.db.DaoMaster;
import zyh.com.core.db.UserInfoDao;
import zyh.com.core.exception.ApiException;
import zyh.com.presenter.CreatOrderPresenter;
import zyh.com.presenter.my.MyQueryAddressPresenter;
import zyh.com.util.UIUtils;

//创建订单
public class CloseActivity extends AppCompatActivity {

    private ImageView image_pop;
    private PopupWindow popupWindow;
    private RecyclerView pop_recy,creation_recy;
    private TextView text_name;
    private TextView text_phone;
    private TextView text_address;
    private List<QueryShoppingBean> beanList;
    private TextView text_allnum;
    private TextView text_allprice;
    private TextView text_go;
    private CreatOrderAdapter creatOrderAdapter;
    private MyQueryAddressPresenter myQueryAddressPresenter;
    private CreatOrderPresenter creatOrderPresenter;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close);
        //获取greendao数据库 ; 从而可以获取网络连接需要的参数
        final UserInfoDao userInfoDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
        if (userInfos!=null&&userInfos.size()>0){
            //读取第一项
            userInfo = userInfos.get(0);
        }
        //获取控件
        text_name = findViewById(R.id.creation_name);
        text_phone = findViewById(R.id.creation_phone);
        text_address = findViewById(R.id.creation_address);
        image_pop = findViewById(R.id.creation_image_pop);
        creation_recy = findViewById(R.id.creation_shop_recy);
        text_allnum = findViewById(R.id.cretion_text_allnum);
        text_allprice = findViewById(R.id.cretion_text_allprice);
        text_go = findViewById(R.id.creation_text_go);
        //获取传过来的值
        Intent intent=getIntent();
        beanList = (List<QueryShoppingBean>) intent.getSerializableExtra("creation_bill");
        //调用p层
        myQueryAddressPresenter = new MyQueryAddressPresenter(new getAddress());
        creatOrderPresenter = new CreatOrderPresenter(new getBuy());
        //布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        creation_recy.setLayoutManager(manager);
        //数据请求
        myQueryAddressPresenter.reqeust(userInfo.getUserId(),userInfo.getSessionId());
        //适配器
        creatOrderAdapter = new CreatOrderAdapter(this);
        creation_recy.setAdapter(creatOrderAdapter);
        creatOrderAdapter.ClearAll();//清空
        creatOrderAdapter.addAll(beanList);
        creatOrderAdapter.notifyDataSetChanged();
        //计算价格
        getcount();
        //结算点击
        text_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交的字符串
                List<CreateBean> addlist=new ArrayList<>();

                for (int i = 0; i < beanList.size(); i++) {

                    QueryShoppingBean queryShoppingBean = beanList.get(i);
                    addlist.add(new CreateBean(queryShoppingBean.getCommodityId(),queryShoppingBean.getCount()));

                }
                String orderInfo = new Gson().toJson(addlist);
                //支付的总金额
                String totalPrice = text_allprice.getText().toString();
                Map<String,String> params=new HashMap<>();
                params.put("orderInfo",orderInfo);
                params.put("totalPrice",totalPrice);
                params.put("addressId",String.valueOf(addressId));
                creatOrderPresenter.reqeust(userInfo.getUserId(),userInfo.getSessionId(),params);
            }
        });
    }

    //计算价格
    public void getcount(){
        //价格
        double prices = 0;
        //数量
        int counts = 0;
        for (int i = 0; i < beanList.size() ; i++) {
            //获取一条数据
            QueryShoppingBean queryShoppingBean = beanList.get(i);
            //计算价格
            prices=prices+queryShoppingBean.getCount()*queryShoppingBean.getPrice();
            //计算数量
            counts = counts + queryShoppingBean.getCount();
        }
        text_allnum.setText(""+counts);
        text_allprice.setText(""+prices);
    }

    //地址id
    private int addressId;
    //创建订单中的地址请求
    public class getAddress implements DataCall<Result<List<MyAddressBean>>> {
        @Override
        public void success(Result<List<MyAddressBean>> result) {

            if (result.getStatus().equals("0000")) {
                List<MyAddressBean> myAddressBeans = result.getResult();
                MyAddressBean myAddressBean = myAddressBeans.get(1);
                addressId = myAddressBean.getId();
                text_name.setText(myAddressBean.getRealName());
                text_phone.setText(myAddressBean.getPhone());
                text_address.setText(myAddressBean.getAddress());
            }
        }
        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+";"+e.getDisplayMessage());
        }
    }

    //订单的成功是否的信息回调
    public class getBuy implements DataCall<Result>{
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                Toast.makeText(CloseActivity.this, result.getMessage()+"",Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+";"+e.getDisplayMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myQueryAddressPresenter.unBind();
        creatOrderPresenter.unBind();
        finish();
    }
}
