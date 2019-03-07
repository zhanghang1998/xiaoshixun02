package zyh.com.presenter;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zyh.com.bean.Result;
import zyh.com.core.DataCall;
import zyh.com.core.exception.CustomException;
import zyh.com.core.exception.ResponseTransformer;

public abstract class BasePresenter {

    private DataCall dataCall;
    private boolean runnimg;

    public BasePresenter(DataCall dataCall) {
        this.dataCall = dataCall;
    }

    protected abstract Observable observable(Object...args);

    public void reqeust(Object...args){

        if (runnimg) {
            return;
        }
        runnimg = true;
        observable(args)
                .compose(ResponseTransformer.handleResult())
                .compose(new ObservableTransformer() {
                    @Override
                    public ObservableSource apply(Observable upstream) {
                        return upstream.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        runnimg=false;
                        dataCall.success(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        runnimg=false;
                        dataCall.fail(CustomException.handleException(throwable));
                    }
                });
    }

    public boolean isRunnimg(){
        return runnimg;
    }
    //解绑方法
    public void unBind(){
        dataCall=null;
    }
}
