package zyh.com.frag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import zyh.com.bean.MyBean;
import zyh.com.bean.Result;
import zyh.com.bean.UserInfo;
import zyh.com.core.DataCall;
import zyh.com.core.WDApplication;
import zyh.com.core.db.DaoMaster;
import zyh.com.core.db.UserInfoDao;
import zyh.com.core.exception.ApiException;
import zyh.com.presenter.MyPresenter;
import zyh.com.presenter.RequestPresenter;
import zyh.com.util.MD5Utils;
import zyh.com.util.UIUtils;
import zyh.com.zhoukao01_lianxi.MainActivity;
import zyh.com.zhoukao01_lianxi.MyAddressActivity;
import zyh.com.zhoukao01_lianxi.MyCircleActivity;
import zyh.com.zhoukao01_lianxi.MyFootmarkActivity;
import zyh.com.zhoukao01_lianxi.MyMessageActivity;
import zyh.com.zhoukao01_lianxi.MyWalletActivity;
import zyh.com.zhoukao01_lianxi.R;
import zyh.com.zhoukao01_lianxi.ShowActivity;

public class Fragment05_my extends Fragment implements View.OnClickListener {

    private MyBean result;
    private long num=980;
    private SimpleDraweeView myBack,myTou;
    private TextView myNmae;
    private MyPresenter myPresenter;
    private UserInfo userInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag05_my_item, container, false);
        //获取greendao数据库 ; 从而可以获取网络连接需要的参数
        UserInfoDao userInfoDao = DaoMaster.newDevSession(getActivity(),UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
        if (userInfos!=null&&userInfos.size()>0){
            //读取第一项
            userInfo = userInfos.get(0);
        }
        //获取控件
        view.findViewById(R.id.button_frag05_my_finish).setOnClickListener(this);
        view.findViewById(R.id.text_frag05_myAddress).setOnClickListener(this);
        view.findViewById(R.id.text_frag05_myCircle).setOnClickListener(this);
        view.findViewById(R.id.text_frag05_myWallet).setOnClickListener(this);
        view.findViewById(R.id.text_frag05_myFootprint).setOnClickListener(this);
        view.findViewById(R.id.text_frag05_myMessage).setOnClickListener(this);
        myBack = view.findViewById(R.id.me_bg);
        myTou = view.findViewById(R.id.me_avatar);
        myNmae = view.findViewById(R.id.me_nickname);

        //数据请求
        myPresenter = new MyPresenter(new registerCall());
        myPresenter.reqeust(userInfo.getUserId(),userInfo.getSessionId());

        return view;
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_frag05_my_finish://退出登录
                UserInfoDao userInfoDao = DaoMaster.newDevSession(getActivity(),UserInfoDao.TABLENAME).getUserInfoDao();
                userInfoDao.delete(userInfo);//删除用户
                SharedPreferences.Editor edit = WDApplication.getShare().edit();
                edit.clear().commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);//跳转登录页
                getActivity().finish();//本页面关闭
                break;
            case R.id.text_frag05_myMessage://跳转到个人信息页面

                //把个人信息传过去
                Intent intentMy = new Intent(getActivity(), MyMessageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("myBean",result);
                intentMy.putExtras(bundle);
                startActivity(intentMy);

                break;
            case R.id.text_frag05_myAddress://跳转到收货地址页面
                startActivity(new Intent(getActivity(),MyAddressActivity.class));
                break;
            case R.id.text_frag05_myFootprint://跳转到我的足迹页面
                startActivity(new Intent(getActivity(),MyFootmarkActivity.class));
                break;
            case R.id.text_frag05_myWallet://跳转到我的钱包页面
                startActivity(new Intent(getActivity(),MyWalletActivity.class));
                break;
            case R.id.text_frag05_myCircle://跳转到我的圈子页面
                startActivity(new Intent(getActivity(),MyCircleActivity.class));
                break;
        }
    }

    //数据回调 , 登录的
    public class registerCall implements DataCall<Result<MyBean>> {

        @Override
        public void success(Result<MyBean> data) {

            if (data.getStatus().equals("0000")) {
                result = data.getResult();
                myBack.setImageURI(Uri.parse(result.getHeadPic()));
                myTou.setImageURI(Uri.parse(result.getHeadPic()));
                myNmae.setText(result.getNickName());

            } else {
                Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+" "+e.getDisplayMessage());
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        myPresenter.unBind();
    }
}
