package zyh.com.presenter.my;

import io.reactivex.Observable;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;
import zyh.com.presenter.BasePresenter;

public class MyQueryAddressPresenter extends BasePresenter {


    public MyQueryAddressPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);

        return iRequest.queryAddress((long)args[0],(String)args[1]);
    }
}
