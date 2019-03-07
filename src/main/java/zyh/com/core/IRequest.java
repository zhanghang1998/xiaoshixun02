package zyh.com.core;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.internal.operators.observable.ObservableRange;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import zyh.com.bean.CircleBean;
import zyh.com.bean.MyBean;
import zyh.com.bean.QueryShoppingBean;
import zyh.com.bean.Result;
import zyh.com.bean.SearchBean;
import zyh.com.bean.ShopCountBean;
import zyh.com.bean.UserInfo;
import zyh.com.bean.home.HomeJson;
import zyh.com.bean.JsonBanner;
import zyh.com.bean.my.MyAddressBean;
import zyh.com.bean.my.MyCircleBean;
import zyh.com.bean.my.MyFootmarkBean;
import zyh.com.bean.my.MyWalletBean;
import zyh.com.bean.order.OrderListBean;

public interface IRequest {

    //登录
    @FormUrlEncoded
    @POST("user/v1/login")
    public Observable<Result<UserInfo>> login(
            @Field("phone") String m,
            @Field("pwd") String p);

    //注册
    @FormUrlEncoded
    @POST("user/v1/register")
    public Observable<Result> registe(
            @Field("phone") String m,
            @Field("pwd") String p);

    //轮播图
    @GET("commodity/v1/bannerShow")
    public Observable<Result<List<JsonBanner>>> banners();

    //首页列表信息
    @GET("commodity/v1/commodityList")
    public Observable<Result<HomeJson>> homeList();

    //圈子
    @GET("circle/v1/findCircleList")
    public Observable<Result<List<CircleBean>>> circleList(
            @Header("userId") long userid,
            @Header("sessionId") String sessionid,
            @Query("page") int pages,
            @Query("count") int counts);

    //首页搜索信息
    @GET("commodity/v1/findCommodityByKeyword")
    public Observable<Result<List<SearchBean>>> queryKey(
            @Query("keyword") String keywords,
            @Query("page") int pages,
            @Query("count") int counts);

    //我的信息
    @GET("user/verify/v1/getUserById")
    public Observable<Result<MyBean>> queryMy(
            @Header("userId") long user,
            @Header("sessionId") String session);

    //商品详情
    @GET("commodity/v1/findCommodityDetailsById")
    public Observable<Result<ShopCountBean>> shopCount(
            @Header("userId") long user,
            @Header("sessionId") String session,
            @Query("commodityId") int commodityId);

    //查询购物车
    @GET("order/verify/v1/findShoppingCart")
    public Observable<Result<List<QueryShoppingBean>>> queryShopping(
            @Header("userId") long users,
            @Header("sessionId") String sessions);

    //同步购物车数据
    @FormUrlEncoded
    @PUT("order/verify/v1/syncShoppingCart")
    public Observable<Result> myShopCar(
            @Header("userId") long userId,
            @Header("sessionId") String sessionId,
            @Field("data") String datas);

    //查询订单列表
    @GET("order/verify/v1/findOrderListByStatus")
    public Observable<Result<List<OrderListBean>>> queryOrderFrag(
            @Header("userId") long userId,
            @Header("sessionId") String sessionId,
            @Query("status") int status,
            @Query("page") int page,
            @Query("count") int count);

    /**
     * 发布圈子
     */
    @POST("circle/verify/v1/releaseCircle")
    Observable<Result> releaseCircle(
            @Header("userId") long userId,
            @Header("sessionId") String sessionId,
            @Body MultipartBody body);

    //查询收货地址
    @GET("user/verify/v1/receiveAddressList")
    public Observable<Result<List<MyAddressBean>>> queryAddress(
            @Header("userId") long users,
            @Header("sessionId") String sessions);

    /**
     * 我的足迹
     */
    @GET("commodity/verify/v1/browseList")
    Observable<Result<List<MyFootmarkBean>>> browseList(
            @Header("userId") long userId,
            @Header("sessionId") String sessionId,
            @Query("page") int page,
            @Query("count") int count);

    //新增收货地址
    @POST("user/verify/v1/addReceiveAddress")
    Observable<Result> newAddress(
            @Header("userId") long userId,
            @Header("sessionId") String sessionId,
            @QueryMap Map<String, String> parmas);

    //我的圈子
    @GET("circle/verify/v1/findMyCircleById")
    Observable<Result<List<MyCircleBean>>> getMyCircle(
            @Header("userId") long userId,
            @Header("sessionId") String sessionId,
            @Query("page") int parma,
            @Query("count") int count);

    //我的钱包
    @GET("user/verify/v1/findUserWallet")
    Observable<Result<MyWalletBean>> getMyWallet(
            @Header("userId") long userId,
            @Header("sessionId") String sessionId,
            @Query("page") int pages,
            @Query("count") int count);

    //创建订单
    @POST("order/verify/v1/createOrder")
    Observable<Result> createOrder(
            @Header("userId") long userId,
            @Header("sessionId") String sessionId,
            @QueryMap Map<String, String> parmas);

    //支付
    @POST("order/verify/v1/pay")
    Observable<Result> playment(
            @Header("userId") long userId,
            @Header("sessionId") String sessionId,
            @Query("orderId") String orderId,
            @Query("payType") int dis);

}
