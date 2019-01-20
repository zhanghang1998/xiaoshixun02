package zyh.com.presenter;

import io.reactivex.Observable;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;

public class ShopCountPresenter extends BasePresenter{


    public ShopCountPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);
        return iRequest.shopCount((long)args[0],(String)args[1],(int)args[2]);
    }
}
