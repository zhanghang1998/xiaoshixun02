package zyh.com.presenter;

import io.reactivex.Observable;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;

public class QueryShoppingPresenter extends BasePresenter{

    public QueryShoppingPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);



        return iRequest.queryShopping((long)args[0],(String)args[1]);
    }
}
