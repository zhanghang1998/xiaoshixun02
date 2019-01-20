package zyh.com.presenter;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import zyh.com.bean.Result;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;

public class RequestPresenter extends BasePresenter{

    public RequestPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);
        return iRequest.login((String)args[0], (String) args[1]);
    }
}
