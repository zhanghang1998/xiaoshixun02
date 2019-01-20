package zyh.com.presenter;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import zyh.com.core.DataCall;
import zyh.com.core.IRequest;
import zyh.com.https.NetWorkHttp;

/**
 * @author dingtao
 * @date 2018/12/28 11:23
 * qq:1940870847
 */
public class PublishCirclePresenter extends BasePresenter {

    public PublishCirclePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkHttp.instance().create(IRequest.class);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("content", (String)args[3]);
        builder.addFormDataPart("commodityId", "1");
        List<Object> list = (List<Object>) args[4];
        if (list.size()>1) {
            for (int i = 1; i < list.size(); i++) {
                File file = new File((String) list.get(i));
                builder.addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/octet-stream"),
                                file));
            }
        }
        return iRequest.releaseCircle((Long) args[0],
                (String)args[1],builder.build());
    }


}
