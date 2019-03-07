package zyh.com.presenter.my;

import io.reactivex.Observable;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;
import zyh.com.presenter.BasePresenter;

public class MyQueryFootmarkPresenter extends BasePresenter {


    public MyQueryFootmarkPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);

        return iRequest.browseList((long)args[0],(String)args[1],1,5);
    }
}
