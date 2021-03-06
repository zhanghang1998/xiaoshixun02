package zyh.com.presenter;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;

public class BannerPresenter extends BasePresenter{

    public BannerPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);

        return iRequest.banners();
    }
}
