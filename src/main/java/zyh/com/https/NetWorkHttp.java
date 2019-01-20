package zyh.com.https;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkHttp {

    private static NetWorkHttp netWorkHttp;
    private Retrofit retrofit;

    public NetWorkHttp() {
        initme();
    }

    public static NetWorkHttp instance(){
        if (netWorkHttp==null) {
            netWorkHttp = new NetWorkHttp();
        }
        return netWorkHttp;
    }

    private void initme(){
        //打印log日志
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(100,TimeUnit.SECONDS)
                .writeTimeout(100,TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://172.17.8.100/small/")//base_url:http+域名
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <T> T create(final Class<T> service){
        return retrofit.create(service);
    }

}
