package com.ay.lxunhan.http;



import com.ay.lxunhan.bean.AddFriendBean;
import com.ay.lxunhan.bean.ChannelBean;
import com.ay.lxunhan.bean.CommentBean;
import com.ay.lxunhan.bean.CountryBean;
import com.ay.lxunhan.bean.FriendBean;
import com.ay.lxunhan.bean.HomeDetailBean;
import com.ay.lxunhan.bean.HomeQuizDetailBean;
import com.ay.lxunhan.bean.LoginBean;
import com.ay.lxunhan.bean.MultiItemBaseBean;
import com.ay.lxunhan.bean.PeopleBean;
import com.ay.lxunhan.bean.TwoCommentBean;
import com.ay.lxunhan.bean.TypeBean;
import com.ay.lxunhan.bean.UserInfoBean;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.bean.VideoDetailBean;
import com.ay.lxunhan.bean.model.AcceptModel;
import com.ay.lxunhan.bean.model.AttentionModel;
import com.ay.lxunhan.bean.model.AttentionsModel;
import com.ay.lxunhan.bean.model.ComplaintModel;
import com.ay.lxunhan.bean.model.CompleteInfoModel;
import com.ay.lxunhan.bean.model.PublicModel;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.bean.model.SmallVideoWatchModel;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.GsonUtil;
import com.ay.lxunhan.utils.UserInfo;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.ay.lxunhan.http.config.UrlConfig.BASE_URL;
import static com.ay.lxunhan.utils.Contacts.LIMIT;
import static com.ay.lxunhan.utils.Contacts.NETWORK_INTERCEPTOR_TYPE_LOG;


public class HttpMethods {
    public static final int HTTP_SUCCESS = 0;
    public static final int HTTP_NOT_LOGIN = 4011;
    private HttpApi httpService;
    private OkHttpClient okhttpclient;

    private HttpMethods() {
        Retrofit retrofit = getRetrofit(getOkHttpClient());
        httpService = retrofit.create(HttpApi.class);
    }

    private OkHttpClient getOkHttpClient() {
        if (okhttpclient == null) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            OkHttpUtil.setHttpConfig(httpClientBuilder);
            httpClientBuilder.addInterceptor(OkHttpUtil.getInterceptor(NETWORK_INTERCEPTOR_TYPE_LOG));
            okhttpclient = httpClientBuilder.build();
        }
        return okhttpclient;
    }


    private Retrofit getRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(CustomizeGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

    }

    private <T> Flowable<T> compositeThread(Flowable<HttpResult<T>> o) {
        return o.onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<>());
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Function<HttpResult<T>, T> {
        @Override
        public T apply(HttpResult<T> httpResult) {
            return httpResult.getData();
        }
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /**
     * 注册/找回密码
     */
    public Flowable<LoginBean> register(PublicModel publicModel){
        Flowable<HttpResult<LoginBean>> flowable=httpService.register(modeBody(publicModel));
        return compositeThread(flowable);
    }

    /**
     * 登录
     */
    public Flowable<LoginBean> login(PublicModel publicModel){
        Flowable<HttpResult<LoginBean>> flowable=httpService.login(modeBody(publicModel));
        return compositeThread(flowable);
    }

    /**
     * 获取验证码
     */
    public Flowable<Object> getCode(String phone,int type){
        Flowable<HttpResult<Object>> flowable=httpService.getCode(phone,type);
        return compositeThread(flowable);
    }

    /**
     * 完善信息
     */
    public Flowable<Object> completeInfo(CompleteInfoModel completeInfoModel){
        Flowable<HttpResult<Object>> flowable=httpService.completeInfo(modeBody(completeInfoModel));
        return compositeThread(flowable);
    }

    /**
     * 个人信息
     */
    public Flowable<UserInfoBean> getUserInfo(){
        Flowable<HttpResult<UserInfoBean>> flowable=httpService.getUserInfo(UserInfo.getInstance().getUserId());
        return compositeThread(flowable);
    }

    /**
     * 首页列表
     */
    public Flowable<List<MultiItemBaseBean>> getHomeList(int type,String title,int page){
        Flowable<HttpResult<List<MultiItemBaseBean>>> flowable=httpService.getHomeList(UserInfo.getInstance().getUserId(),type,title,page, Contacts.LIMIT);
        return compositeThread(flowable);
    }
    /**
     * 推荐列表
     */
    public Flowable<List<MultiItemBaseBean>> getHomeListArticle(String id,int page){
        Flowable<HttpResult<List<MultiItemBaseBean>>> flowable=httpService.getHomeListArticle(UserInfo.getInstance().getUserId(),id,page, Contacts.LIMIT);
        return compositeThread(flowable);
    }
    /**
     * 问答列表
     */
    public Flowable<List<MultiItemBaseBean>> getHomeListAsk(int page){
        Flowable<HttpResult<List<MultiItemBaseBean>>> flowable=httpService.getHomeListAsk(UserInfo.getInstance().getUserId(),page, Contacts.LIMIT);
        return compositeThread(flowable);
    }
    /**
     * 投票列表
     */
    public Flowable<List<MultiItemBaseBean>> getHomeListQuiz(int page){
        Flowable<HttpResult<List<MultiItemBaseBean>>> flowable=httpService.getHomeListQuiz(UserInfo.getInstance().getUserId(),page, Contacts.LIMIT);
        return compositeThread(flowable);
    }

    /**
     * 首页类型
     */
    public Flowable<List<TypeBean>> getHomeType(){
        Flowable<HttpResult<List<TypeBean>>> flowable=httpService.getHomeType(UserInfo.getInstance().getUserId());
        return compositeThread(flowable);
     }

    /**
     * 视频类型
     */
    public Flowable<List<TypeBean>> getVideoType(){
        Flowable<HttpResult<List<TypeBean>>> flowable=httpService.getVideType(UserInfo.getInstance().getUserId());
        return compositeThread(flowable);
    }

    /**
     * 地区列表
     */
    public Flowable<List<CountryBean>> getAddress(){
        Flowable<HttpResult<List<CountryBean>>> flowable=httpService.getAddress(UserInfo.getInstance().getUserId());
        return compositeThread(flowable);
    }

    /**
     * 详情
     */
    public Flowable<HomeDetailBean> getHomeDetail(int type,int id){
        Flowable<HttpResult<HomeDetailBean>> flowable=httpService.getHomeDetail(UserInfo.getInstance().getUserId(),type,id);
        return compositeThread(flowable);
    }

    /**
     *投票详情
     */
    public Flowable<HomeQuizDetailBean> getHomeQuizDetail(int id){
        Flowable<HttpResult<HomeQuizDetailBean>> flowable=httpService.getHomeQuizDetail(UserInfo.getInstance().getUserId(),id);
        return compositeThread(flowable);
    }

    /**
     * 一级评论
     */
    public Flowable<List<CommentBean>> getOneComment(String aid,int type,int page){
        Flowable<HttpResult<List<CommentBean>>> flowable=httpService.getOneComment(UserInfo.getInstance().getUserId(),aid,type,page,Contacts.LIMIT);
        return compositeThread(flowable);
    }

    /**
     * 二级评论
     */
    public Flowable<TwoCommentBean> getTwoComment(String id,int page){
        Flowable<HttpResult<TwoCommentBean>> flowable=httpService.getTwoComment(UserInfo.getInstance().getUserId(),id,page,LIMIT);
        return compositeThread(flowable);
    }

    /**
     *频道管理
     */
    public Flowable<ChannelBean> getChannelManagement(){
        Flowable<HttpResult<ChannelBean>> flowable=httpService.getChannelManagement(UserInfo.getInstance().getUserId());
        return compositeThread(flowable);
    }


    /**
     * 添加频道
     */
    public Flowable<Object> addChannel(String id){
        CompleteInfoModel completeInfoModel=new CompleteInfoModel(UserInfo.getInstance().getUserId(),id);
        Flowable<HttpResult<Object>> flowable=httpService.addChannel(modeBody(completeInfoModel));
        return compositeThread(flowable);
    }

    /**
     * 删除频道
     */
    public Flowable<Object> deleteChannel(String id){
        Flowable<HttpResult<Object>> flowable=httpService.deleteChannel(UserInfo.getInstance().getUserId(),id);
        return compositeThread(flowable);
    }

    /**
     *视频频道管理
     */
    public Flowable<ChannelBean> getChannelVideoManagement(){
        Flowable<HttpResult<ChannelBean>> flowable=httpService.getVideoChannelManagement(UserInfo.getInstance().getUserId());
        return compositeThread(flowable);
    }


    /**
     * 视频添加频道
     */
    public Flowable<Object> addVideoChannel(String id){
        CompleteInfoModel completeInfoModel=new CompleteInfoModel(UserInfo.getInstance().getUserId(),id);
        Flowable<HttpResult<Object>> flowable=httpService.addVideoChannel(modeBody(completeInfoModel));
        return compositeThread(flowable);
    }

    /**
     * 视频删除频道
     */
    public Flowable<Object> deleteVideoChannel(String id){
        Flowable<HttpResult<Object>> flowable=httpService.deleteVideoChannel(UserInfo.getInstance().getUserId(),id);
        return compositeThread(flowable);
    }

    /**
     * 一级评论
     */
    public Flowable<Object> sendOneComment(SendCommentModel model){
        Flowable<HttpResult<Object>> flowable=httpService.sendOneComment(modeBody(model));
        return compositeThread(flowable);
    }

    /**
     * 二级评论
     */
    public Flowable<Object> sendTwoComment(SendCommentModel model){
        Flowable<HttpResult<Object>> flowable=httpService.sendTwoComment(modeBody(model));
        return compositeThread(flowable);
    }

    /**
     * 点赞
     */
    public Flowable<Object> addLike(SendCommentModel model){
        Flowable<HttpResult<Object>> flowable=httpService.addLike(modeBody(model));
        return compositeThread(flowable);
    }

    /**
     * 评论点赞
     */
    public Flowable<Object> commentLike(SendCommentModel model){
        Flowable<HttpResult<Object>> flowable=httpService.commentLike(modeBody(model));
        return compositeThread(flowable);
    }

    /***
     * 投票
     */
    public Flowable<Object> quiz(SendCommentModel model){
        Flowable<HttpResult<Object>> flowable=httpService.quiz(modeBody(model));
        return compositeThread(flowable);
    }

    /**
     * 采纳
     */
    public Flowable<Object> accept(AcceptModel acceptModel){
        Flowable<HttpResult<Object>> flowable=httpService.accept(modeBody(acceptModel));
        return compositeThread(flowable);
    }

    /**
     * 投诉
     */
    public Flowable<Object> complaint(ComplaintModel complaintModel){
        Flowable<HttpResult<Object>> flowable=httpService.complaint(modeBody(complaintModel));
        return compositeThread(flowable);
    }

    /**
     * 视频推荐列表
     */
    public Flowable<List<VideoBean>> videoHome(String title,int page){
        Flowable<HttpResult<List<VideoBean>>> flowable=httpService.videoHome(UserInfo.getInstance().getUserId(),title,page);
        return compositeThread(flowable);
    }

    /**
     * 小视频
     */
    public Flowable<List<VideoBean>> smallVideoList(int page){
        Flowable<HttpResult<List<VideoBean>>> flowable=httpService.smallVideoList(UserInfo.getInstance().getUserId(),page);
        return compositeThread(flowable);
    }

    /**
     * 视频类型列表
     */
    public Flowable<List<VideoBean>> videoTypeList(String plate_id,int page){
        Flowable<HttpResult<List<VideoBean>>> flowable=httpService.videoTypeList(UserInfo.getInstance().getUserId(),plate_id,page);
        return compositeThread(flowable);
    }

    /**
     * 最近观看
     */
    public Flowable<List<PeopleBean>> videoRecord(){
        Flowable<HttpResult<List<PeopleBean>>> flowable=httpService.videoRecords(UserInfo.getInstance().getUserId());
        return compositeThread(flowable);
    }

    /**
     * 好友列表
     */
    public Flowable<List<FriendBean>> friendList(){
        Flowable<HttpResult<List<FriendBean>>> flowable=httpService.friendsList(UserInfo.getInstance().getUserId());
        return compositeThread(flowable);
    }


    /**
     * 小视频观看
     */
    public Flowable<Object> smallVideoWatch(int id){
        SmallVideoWatchModel smallVideoWatchModel=new SmallVideoWatchModel(id);
        Flowable<HttpResult<Object>> flowable=httpService.smallVideoWatch(modeBody(smallVideoWatchModel));
        return compositeThread(flowable);
    }

    /**
     * 添加好友
     */
    public Flowable<AddFriendBean> addFriendBean(String user){
        Flowable<HttpResult<AddFriendBean>> flowable=httpService.addFriends(UserInfo.getInstance().getUserId(),user);
        return compositeThread(flowable);
    }

    /**
     * 关注
     */
    public Flowable<Object> attention(String uid){
        AttentionModel attentionModel=new AttentionModel(uid);
        Flowable<HttpResult<Object>> flowable=httpService.attention(modeBody(attentionModel));
        return compositeThread(flowable);
    }

    /**
     * 关注
     */
    public Flowable<Object> attentions(List<String> uid){
        AttentionsModel attentionModel=new AttentionsModel(uid);
        Flowable<HttpResult<Object>> flowable=httpService.moreAttention(modeBody(attentionModel));
        return compositeThread(flowable);
    }

    /**
     * 视频详情
     */
    public Flowable<VideoDetailBean> videoDetail(String id){
        Flowable<HttpResult<VideoDetailBean>> flowable=httpService.videoDetail(UserInfo.getInstance().getUserId(),id);
        return compositeThread(flowable);
    }

    private static RequestBody modeBody(Object model) {
        String route = GsonUtil.gsonString(model);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), route);
    }
}
