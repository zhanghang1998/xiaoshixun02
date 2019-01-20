package zyh.com.presenter;

import java.util.List;

import io.reactivex.Observable;
import zyh.com.bean.Result;
import zyh.com.bean.SearchBean;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;

public class SearchPresenter extends BasePresenter{

    private int page;

    public SearchPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);

        boolean isflog = (boolean) args[0];
        if (isflog) {
            page = 1;
        } else {
            page++;
        }
        Observable<Result<List<SearchBean>>> resultObservable = iRequest.queryKey((String) args[1], page, 10);
        return resultObservable;
    }
}
