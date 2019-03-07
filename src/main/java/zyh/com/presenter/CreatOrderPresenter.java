package zyh.com.presenter;

import java.util.Map;

import io.reactivex.Observable;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;

public class CreatOrderPresenter extends BasePresenter{


    public CreatOrderPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);
        return iRequest.createOrder((long)args[0],(String) args[1],(Map<String, String>) args[2]);
    }
}
