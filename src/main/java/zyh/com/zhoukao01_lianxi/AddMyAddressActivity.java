package zyh.com.zhoukao01_lianxi;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import zyh.com.bean.Result;
import zyh.com.bean.UserInfo;
import zyh.com.bean.my.MyNewAddress;
import zyh.com.core.DataCall;
import zyh.com.core.db.DaoMaster;
import zyh.com.core.db.UserInfoDao;
import zyh.com.core.exception.ApiException;
import zyh.com.presenter.my.MyQueryNewAddressPresenter;
import zyh.com.util.UIUtils;
import zyh.com.view.MyShoppingView;

public class AddMyAddressActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_name;
    private EditText edit_phone;
    private TextView text_city;
    private EditText edit_address;
    private EditText edit_zipCode;
    private ImageView image_open;
    private Button button_save;
    private String city;
    private MyQueryNewAddressPresenter myQueryNewAddressPresenter;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_address);

        //获取greendao数据库 ; 从而可以获取网络连接需要的参数
        UserInfoDao userInfoDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
        if (userInfos!=null&&userInfos.size()>0){
            //读取第一项
            userInfo = userInfos.get(0);
        }

        ButterKnife.bind(this);

        //获取资源ID
        edit_name = findViewById(R.id.new_address_edit_name);
        edit_phone = findViewById(R.id.new_address_edit_phone);
        text_city = findViewById(R.id.new_address_text_city);
        edit_address = findViewById(R.id.new_address_edit_address);
        edit_zipCode = findViewById(R.id.new_address_edit_zipCode);
        image_open = findViewById(R.id.new_address_image_open);
        button_save = findViewById(R.id.new_address_text_save);
        image_open.setOnClickListener(this);
        button_save.setOnClickListener(this);

        //调用p层
        myQueryNewAddressPresenter = new MyQueryNewAddressPresenter(new newAddress());


    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_address_image_open://选择地址按钮

                CityPicker cityPicker = new CityPicker.Builder(this)
                        .textSize(14)
                        .title("请选择所在地区")
                        .titleBackgroundColor("#FFFFFF")
                        .confirTextColor("#ff0000")
                        .cancelTextColor("#696969")
                        .province("北京市")
                        .city("北京市")
                        .district("昌平区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(false)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(7)
                        .itemPadding(10)
                        .onlyShowProvinceAndCity(false)
                        .build();
                cityPicker.show();
                //监听方法，获取选择结果
                cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        //省份
                        String province = citySelected[0];
                        //城市
                        String city = citySelected[1];
                        //区县（如果设定了两级联动，那么该项返回空）
                        String district = citySelected[2];
                        //邮编
                        String code = citySelected[3];
                        //为TextView赋值
                        text_city.setText(province + " " + city + " " + district + " ");
                    }
                });

                break;
            case R.id.new_address_text_save://保存信息

                String save_name = edit_name.getText().toString();
                String save_phone = edit_phone.getText().toString();
                String save_city = text_city.getText().toString();
                String save_add = edit_address.getText().toString();
                String save_zipCode = edit_zipCode.getText().toString();
                String city_add = save_city+","+save_add;
                //MyNewAddress myNewAddress = new MyNewAddress(save_city, save_add);
                //String save_address = new Gson().toJson(myNewAddress);
                Map<String,String> params=new HashMap<>();
                params.put("realName",save_name);
                params.put("phone",save_phone);
                params.put("address",city_add);
                params.put("zipCode",save_zipCode);

                //进行数据请求
                myQueryNewAddressPresenter.reqeust(userInfo.getUserId(),userInfo.getSessionId(),params);

                break;
        }
    }

    public class newAddress implements DataCall<Result> {

        @Override
        public void success(Result result) {


            if (result.getStatus().equals("0000")) {
                Toast.makeText(AddMyAddressActivity.this, result.getMessage() + "", Toast.LENGTH_SHORT).show();
                //回调数据成功进行跳转
                Intent intent = new Intent(AddMyAddressActivity.this, MyAddressActivity.class);
                startActivity(intent);
                finish();
            } else {
                //没有请求成功吐司
                Toast.makeText(AddMyAddressActivity.this, result.getMessage()+ "", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode() + " " + e.getDisplayMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myQueryNewAddressPresenter.unBind();//解绑
        finish();//页面销毁
    }

}
