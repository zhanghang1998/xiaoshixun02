package zyh.com.presenter;

import io.reactivex.Observable;
import zyh.com.bean.Result;
import zyh.com.bean.home.HomeJson;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;

public class HomePresenter extends BasePresenter{

    public HomePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);
        return iRequest.homeList();
    }
}
