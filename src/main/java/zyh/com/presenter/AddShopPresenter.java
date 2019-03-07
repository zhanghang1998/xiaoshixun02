package zyh.com.presenter;

import java.util.List;

import io.reactivex.Observable;
import zyh.com.bean.Result;
import zyh.com.bean.SearchBean;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;

public class AddShopPresenter extends BasePresenter{

    private int page;

    public AddShopPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object...args) {
        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);
        Observable<Result> shopCar = iRequest.myShopCar((long) args[0], (String) args[1], (String) args[2]);
        return shopCar;
    }
}
