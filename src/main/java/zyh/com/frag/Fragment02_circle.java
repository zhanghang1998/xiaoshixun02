package zyh.com.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import io.reactivex.internal.operators.observable.ObservableDoAfterNext;
import zyh.com.adapter.CircleAdapter;
import zyh.com.bean.CircleBean;
import zyh.com.bean.Result;
import zyh.com.bean.UserInfo;
import zyh.com.core.DataCall;
import zyh.com.core.db.DaoMaster;
import zyh.com.core.db.UserInfoDao;
import zyh.com.core.exception.ApiException;
import zyh.com.presenter.CirclePresenter;
import zyh.com.util.UIUtils;
import zyh.com.zhoukao01_lianxi.AddCircleActivity;
import zyh.com.zhoukao01_lianxi.R;

public class Fragment02_circle extends Fragment implements XRecyclerView.LoadingListener,View.OnClickListener {

    public static boolean addCircle;
    private XRecyclerView xRecyListe;
    private CirclePresenter circlePresenter;
    private CircleAdapter circleAdapter;
    private UserInfo userInfo;
    private ImageView add_circle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag02_circle_item, container, false);
        //获取控件
        xRecyListe = view.findViewById(R.id.xRecyclerView_circle);
        view.findViewById(R.id.add_circle).setOnClickListener(this);
        //获取greendao数据库 ; 从而可以获取网络连接需要的参数
        UserInfoDao userInfoDao = DaoMaster.newDevSession(getActivity(),UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
        if (userInfos!=null&&userInfos.size()>0){
            //读取第一项
            userInfo = userInfos.get(0);
        }
        //设置列表展示样式
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        xRecyListe.setLayoutManager(gridLayoutManager);
        xRecyListe.setLoadingListener(this);
        //适配器
        circleAdapter = new CircleAdapter(getActivity());
        xRecyListe.setAdapter(circleAdapter);
        //调用
        circlePresenter = new CirclePresenter(new circleCall());

        xRecyListe.refresh();

        return view;
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        //获取basepresenter里的布尔值
        if (circlePresenter.isRunnimg()) {
            xRecyListe.refreshComplete();
            return;
        }
        circlePresenter.reqeust(true,userInfo.getUserId(),userInfo.getSessionId());
    }
    //上拉加载
    @Override
    public void onLoadMore() {
        //获取basepresenter里的布尔值
        if (circlePresenter.isRunnimg()) {
            xRecyListe.loadMoreComplete();
            return;
        }
        circlePresenter.reqeust(false,userInfo.getUserId(),userInfo.getSessionId());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (addCircle){//publish new message,so you have to refresh
            addCircle = false;
            xRecyListe.refresh();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_circle://跳转到发表朋友圈页面
                Intent intent = new Intent(getActivity(), AddCircleActivity.class);
                startActivity(intent);
                break;
        }
    }

    //数据回调
    public class circleCall implements DataCall<Result<List<CircleBean>>> {

        @Override
        public void success(Result<List<CircleBean>> data) {
            xRecyListe.refreshComplete();
            xRecyListe.loadMoreComplete();
            if (data.getStatus().equals("0000")) {
                //添加列表并刷新
                if (circlePresenter.getPage()==1){
                    circleAdapter.clearAll();
                }
                //添加列表并刷新
                List<CircleBean> result = data.getResult();
                circleAdapter.addAll(result);
                circleAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            xRecyListe.refreshComplete();
            xRecyListe.loadMoreComplete();
            UIUtils.showToastSafe(e.getCode()+""+e.getDisplayMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        circlePresenter.unBind();
    }
}
