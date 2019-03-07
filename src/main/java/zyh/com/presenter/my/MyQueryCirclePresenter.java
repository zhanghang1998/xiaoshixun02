package zyh.com.presenter.my;

import io.reactivex.Observable;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;
import zyh.com.presenter.BasePresenter;

public class MyQueryCirclePresenter extends BasePresenter {


    public MyQueryCirclePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);

        return iRequest.getMyCircle((long)args[0],(String)args[1],1,10);
    }
}
