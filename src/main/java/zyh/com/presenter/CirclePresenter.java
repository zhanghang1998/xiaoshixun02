package zyh.com.presenter;

import io.reactivex.Observable;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;

public class CirclePresenter extends BasePresenter{

    private int page=1;

    public int getPage() {
        return page;
    }

    public CirclePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);
        boolean refresh = (boolean) args[0];
        if (refresh) {
            page = 1;
        } else {
            page++;
        }
        return iRequest.circleList((long)args[1],(String)args[2],page,10);
    }

}
