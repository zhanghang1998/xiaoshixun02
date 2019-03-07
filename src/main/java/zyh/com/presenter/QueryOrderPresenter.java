package zyh.com.presenter;

import io.reactivex.Observable;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;

public class QueryOrderPresenter extends BasePresenter {

    private int page=1;

    public QueryOrderPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);

        //查询全部订单
        return iRequest.queryOrderFrag((long) args[0], (String) args[1],0,page,10);

    }

}
