package zyh.com.presenter.my;

import java.util.Map;

import io.reactivex.Observable;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;
import zyh.com.presenter.BasePresenter;

public class MyQueryNewAddressPresenter extends BasePresenter {


    public MyQueryNewAddressPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);

        return iRequest.newAddress((long)args[0],(String) args[1],(Map<String, String>) args[2]);
    }
}
