package zyh.com.presenter;

import io.reactivex.Observable;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;

public class QueryOrderPresenter extends BasePresenter {
    public QueryOrderPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);
        return iRequest.queryOrderFrag((long) args[0], (String) args[1],(int)args[2],(int)args[3],(int)args[4]);

    }

}
