package zyh.com.presenter;

import io.reactivex.Observable;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;

public class RegisterPresenter extends BasePresenter{

    public RegisterPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);
        return iRequest.registe((String)args[0], (String) args[1]);
    }
}
