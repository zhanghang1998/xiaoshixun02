package zyh.com.presenter;

import java.util.List;

import io.reactivex.Observable;
import zyh.com.bean.MyBean;
import zyh.com.bean.Result;
import zyh.com.bean.SearchBean;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;

public class MyPresenter extends BasePresenter{

    public MyPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);

        return iRequest.queryMy((long)args[0],(String)args[1]);
    }
}
