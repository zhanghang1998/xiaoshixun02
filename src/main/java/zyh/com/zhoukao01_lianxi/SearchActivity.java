package zyh.com.zhoukao01_lianxi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.EditText;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zyh.com.adapter.SearchAdapter;
import zyh.com.bean.Result;
import zyh.com.bean.SearchBean;
import zyh.com.core.DataCall;
import zyh.com.core.exception.ApiException;
import zyh.com.presenter.SearchPresenter;
import zyh.com.util.UIUtils;

public class SearchActivity extends AppCompatActivity implements XRecyclerView.LoadingListener{

    @BindView(R.id.editText_search)
    EditText textSousuo;
    private XRecyclerView xRecyclerView;
    private SearchPresenter searchPresenter;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        xRecyclerView = findViewById(R.id.xRecyclerView_search);

        //设置列表展示样式
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        xRecyclerView.setLayoutManager(gridLayoutManager);
        xRecyclerView.setLoadingListener(this);
        //适配器
        searchAdapter = new SearchAdapter(this);
        xRecyclerView.setAdapter(searchAdapter);
        //调用presenter层
        searchPresenter = new SearchPresenter(new searchData());

    }

    //下拉刷新 , 上拉加载
    @Override
    public void onRefresh() {
        if (searchPresenter.isRunnimg()) {
            xRecyclerView.refreshComplete();
            return;
        }
        String textKey = textSousuo.getText().toString().trim();
        searchPresenter.reqeust(true,textKey);
    }

    @Override
    public void onLoadMore() {
        if (searchPresenter.isRunnimg()) {
            xRecyclerView.loadMoreComplete();
            return;
        }
        //获取搜索输入框信息
        String textKey = textSousuo.getText().toString().trim();
        searchPresenter.reqeust(false,textKey);
    }

    public class searchData implements DataCall<Result<List<SearchBean>>> {

        @Override
        public void success(Result<List<SearchBean>> result) {

            xRecyclerView.refreshComplete();
            xRecyclerView.loadMoreComplete();
            if (result.getStatus().equals("0000")) {
                searchAdapter.clearAll();
                List<SearchBean> searchBeans = result.getResult();
                searchAdapter.addAll(searchBeans);
                searchAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+""+e.getDisplayMessage());
        }
    }

    //点击搜索 , 刷新数据
    @OnClick(R.id.textView_search_sousuo_refresh)
    public void searchKey(){
        xRecyclerView.refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchPresenter.unBind();
        finish();
    }
}
