package com.ay.lxunhan.http;



import com.ay.lxunhan.bean.AddFriendBean;
import com.ay.lxunhan.bean.AttentionBean;
import com.ay.lxunhan.bean.BillBean;
import com.ay.lxunhan.bean.ChannelBean;
import com.ay.lxunhan.bean.ChatImBean;
import com.ay.lxunhan.bean.ChatListBean;
import com.ay.lxunhan.bean.CoinBean;
import com.ay.lxunhan.bean.CommentBean;
import com.ay.lxunhan.bean.CountryBean;
import com.ay.lxunhan.bean.CreateBean;
import com.ay.lxunhan.bean.FriendBean;
import com.ay.lxunhan.bean.GiftBean;
import com.ay.lxunhan.bean.HeUserBean;
import com.ay.lxunhan.bean.HomeDetailBean;
import com.ay.lxunhan.bean.HomeQuizDetailBean;
import com.ay.lxunhan.bean.InviteBean;
import com.ay.lxunhan.bean.LbBean;
import com.ay.lxunhan.bean.LiveBean;
import com.ay.lxunhan.bean.LiveDetail;
import com.ay.lxunhan.bean.LiveDetailBean;
import com.ay.lxunhan.bean.LiveListBean;
import com.ay.lxunhan.bean.LoginBean;
import com.ay.lxunhan.bean.MultiItemBaseBean;
import com.ay.lxunhan.bean.NotationBean;
import com.ay.lxunhan.bean.NotationSystemBean;
import com.ay.lxunhan.bean.PeopleBean;
import com.ay.lxunhan.bean.PyqBean;
import com.ay.lxunhan.bean.PyqCommentBean;
import com.ay.lxunhan.bean.PyqDetailBean;
import com.ay.lxunhan.bean.RechargeBean;
import com.ay.lxunhan.bean.SingBean;
import com.ay.lxunhan.bean.TaskBean;
import com.ay.lxunhan.bean.TwoCommentBean;
import com.ay.lxunhan.bean.TypeBean;
import com.ay.lxunhan.bean.UserInfoBean;
import com.ay.lxunhan.bean.UserMediaListBean;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.bean.VideoDetailBean;
import com.ay.lxunhan.bean.WxOrderBean;
import com.ay.lxunhan.bean.model.AcceptModel;
import com.ay.lxunhan.bean.model.AttentionModel;
import com.ay.lxunhan.bean.model.AttentionsModel;
import com.ay.lxunhan.bean.model.ComplaintModel;
import com.ay.lxunhan.bean.model.CompleteInfoModel;
import com.ay.lxunhan.bean.model.LbModel;
import com.ay.lxunhan.bean.model.PublicModel;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.bean.model.SmallVideoWatchModel;
import com.ay.lxunhan.bean.model.WithdrawModel;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.GsonUtil;
import com.ay.lxunhan.utils.UserInfo;

import java.io.File;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    public static final int HTTP_LOGIN_BlACK = 4010;
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
     * 完善信息
     */
    public Flowable<Object> completeInfo(MultipartBody.Part part){
        Flowable<HttpResult<Object>> flowable=httpService.completeInfoImg(UserInfo.getInstance().getUserId(),part);
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
    public Flowable<List<VideoBean>> smallVideoList(int page,int id){
        Flowable<HttpResult<List<VideoBean>>> flowable=httpService.smallVideoList(UserInfo.getInstance().getUserId(),page,id);
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
    public Flowable<List<FriendBean>> friendList(String keys){
        Flowable<HttpResult<List<FriendBean>>> flowable=httpService.friendsList(UserInfo.getInstance().getUserId(),keys);
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
    public Flowable<AddFriendBean> addFriendBean(String user,int page){
        Flowable<HttpResult<AddFriendBean>> flowable=httpService.addFriends(UserInfo.getInstance().getUserId(),user,page);
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

    /***
     * 提问
     */
    public Flowable<Object> issue(String title, List<MultipartBody.Part> partList) {
        Flowable<HttpResult<Object>> flowable;
        if (partList != null) {
            flowable = httpService.issue(UserInfo.getInstance().getUserId(), title, partList);
        } else {
            flowable = httpService.issue(UserInfo.getInstance().getUserId(), title);
        }

        return compositeThread(flowable);
    }

    /**
     *朋友圈列表
     */
    public Flowable<List<PyqBean>> pyqList(int page){
        Flowable<HttpResult<List<PyqBean>>> flowable=httpService.pyqList(UserInfo.getInstance().getUserId(),page);
        return compositeThread(flowable);
    }

    /**
     * 聊天列表
     */
    public Flowable<List<ChatListBean>> chatList(int page){
        Flowable<HttpResult<List<ChatListBean>>> flowable=httpService.chatList(UserInfo.getInstance().getUserId(),page);
        return compositeThread(flowable);
    }

    /**
     * 我的资料
     */
    public Flowable<LoginBean> myInfo(){
        Flowable<HttpResult<LoginBean>> flowable=httpService.myinfo(UserInfo.getInstance().getUserId());
        return compositeThread(flowable);
    }

    /**
     * 用户详情和资料
     */
    public Flowable<HeUserBean> userInfo(String uzid,boolean isData){
        Flowable<HttpResult<HeUserBean>> flowable;
        if (isData){
            flowable=httpService.userInfoDetail(UserInfo.getInstance().getUserId(),uzid);
        }else {
            flowable=httpService.userInfoData(UserInfo.getInstance().getUserId(),uzid);
        }
        return compositeThread(flowable);
    }


    /**
     * 自媒体用户发布列表
     */
    public Flowable<List<UserMediaListBean>> userMediaList(String uzid,int page,int type){
        Flowable<HttpResult<List<UserMediaListBean>>> flowable=httpService.userMediaList(UserInfo.getInstance().getUserId(),uzid,page,type);
        return compositeThread(flowable);
    }

    /**
     *好友发布的动态
     */
    public Flowable<List<PyqBean>> userList(String uzid,int page){
        Flowable<HttpResult<List<PyqBean>>> flowable=httpService.userList(UserInfo.getInstance().getUserId(),uzid, page);
        return compositeThread(flowable);
     }

    /**
     * 朋友圈删除
     */
     public Flowable<Object> pyqDelete(String id){
        Flowable<HttpResult<Object>> flowable=httpService.pyqDelete(UserInfo.getInstance().getUserId(),id);
        return compositeThread(flowable);
     }

    /**
     * 粉丝列表
     */
     public Flowable<List<AttentionBean>> fansList(int page){
         Flowable<HttpResult<List<AttentionBean>>> flowable=httpService.fansList(UserInfo.getInstance().getUserId(),page);
         return compositeThread(flowable);
     }

    /**
     * 自媒体粉丝列表
     */
    public Flowable<List<AttentionBean>> mediaFansList(String uzid,int page){
        Flowable<HttpResult<List<AttentionBean>>> flowable=httpService.mediafansList(UserInfo.getInstance().getUserId(),uzid,page);
        return compositeThread(flowable);
    }

    /**
     * 关注列表
     */
    public Flowable<List<AttentionBean>> attentionList(int page){
        Flowable<HttpResult<List<AttentionBean>>> flowable=httpService.attentionList(UserInfo.getInstance().getUserId(),page);
        return compositeThread(flowable);
    }

    /**
     * 自媒体关注列表
     */
    public Flowable<List<AttentionBean>> mediaAttentionList(String uzid,int page){
        Flowable<HttpResult<List<AttentionBean>>> flowable=httpService.mediaAttentionList(UserInfo.getInstance().getUserId(),uzid,page);
        return compositeThread(flowable);
    }

    /**
     * 乐币展示
     */
    public Flowable<LbBean> lbShow(){
        Flowable<HttpResult<LbBean>> flowable=httpService.lbShow(UserInfo.getInstance().getUserId());
        return compositeThread(flowable);
    }

    /**
     * 乐币任务
     */
    public Flowable<List<TaskBean>> lbTask(){
        Flowable<HttpResult<List<TaskBean>>> flowable=httpService.lbTaskList(UserInfo.getInstance().getUserId());
        return compositeThread(flowable);
    }

    /**
     *乐币兑换
     */
    public Flowable<Object> lbExchange(LbModel lbModel){
        Flowable<HttpResult<Object>> flowable=httpService.lbExchange(modeBody(lbModel));
        return compositeThread(flowable);
    }

    /**
     * 乐币明细
     */
    public Flowable<List<CoinBean>> lbBill(int page){
        Flowable<HttpResult<List<CoinBean>>> flowable=httpService.lbBill(UserInfo.getInstance().getUserId(),page);
        return compositeThread(flowable);
    }

    /**
     * 添加收藏
     */
    public Flowable<Object> addCollect(String aid,int type){
        Flowable<HttpResult<Object>> flowable=httpService.addCollect(UserInfo.getInstance().getUserId(),aid, String.valueOf(type));
        return compositeThread(flowable);
    }

    /**
     *
     * 账单列表
     */
    public Flowable<BillBean> billList(int type,int timeType,int page){
        Flowable<HttpResult<BillBean>> flowable=httpService.billList(UserInfo.getInstance().getUserId(),type,timeType,page);
        return compositeThread(flowable);
    }

    /**
     * 朋友圈详情
     */
    public Flowable<PyqDetailBean> pyqDetail(String id){
        Flowable<HttpResult<PyqDetailBean>> flowable=httpService.pyqDetail(UserInfo.getInstance().getUserId(),id);
        return compositeThread(flowable);
    }

    /**
     * 朋友圈评论
     */
    public Flowable<List<PyqCommentBean>> pyqComment(String id,int page){
        Flowable<HttpResult<List<PyqCommentBean>>> flowable=httpService.pyqComment(UserInfo.getInstance().getUserId(),id,page);
        return compositeThread(flowable);
    }

    /**
     * 提现
     */
    public Flowable<Object> withdraw(WithdrawModel withdrawModel){
        Flowable<HttpResult<Object>> flowable=httpService.withdraw(modeBody(withdrawModel));
        return compositeThread(flowable);
    }

    /**
     * 通知
     */
    public Flowable<NotationBean> notation(int page){
        Flowable<HttpResult<NotationBean>> flowable=httpService.notation(UserInfo.getInstance().getUserId(),page);
        return compositeThread(flowable);
    }

    /**
     * 系统通知
     */
    public Flowable<List<NotationSystemBean>> notationSystem(int page){
        Flowable<HttpResult<List<NotationSystemBean>>> flowable=httpService.notationSystem(UserInfo.getInstance().getUserId(),page);
        return compositeThread(flowable);
    }

    /**
     * 历史
     */
    public Flowable<List<UserMediaListBean>> history(int page){
        Flowable<HttpResult<List<UserMediaListBean>>> flowable=httpService.history(UserInfo.getInstance().getUserId(),page);
        return compositeThread(flowable);
    }

    /**
     * 收藏
     */
    public Flowable<List<UserMediaListBean>> collect(int page){
        Flowable<HttpResult<List<UserMediaListBean>>> flowable=httpService.collect(UserInfo.getInstance().getUserId(),page);
        return compositeThread(flowable);
    }

    /**
     * 签到信息
     */
    public Flowable<SingBean> singInfo(){
        Flowable<HttpResult<SingBean>> flowable=httpService.singInfo(UserInfo.getInstance().getUserId());
        return compositeThread(flowable);
    }

    /***
     * 用户签到
     */
    public Flowable<String> userSing(){
        Flowable<HttpResult<String>> flowable=httpService.userSing(UserInfo.getInstance().getUserId());
        return compositeThread(flowable);
    }

    /**
     * 邀请记录
     */
    public Flowable<InviteBean> inviteList(int page){
        Flowable<HttpResult<InviteBean>> flowable=httpService.inviteList(UserInfo.getInstance().getUserId(),page);
        return compositeThread(flowable);
    }

    /**
     * 填写邀请码
     */
    public Flowable<Object> sendInviteCode(String code){
        Flowable<HttpResult<Object>> flowable=httpService.sendInviteCode(UserInfo.getInstance().getUserId(),code);
        return compositeThread(flowable);
    }

    /**
     * 聊天
     */
    public Flowable<ChatImBean> chatIm(String userid,int page){
        Flowable<HttpResult<ChatImBean>> flowable=httpService.chatImlist(UserInfo.getInstance().getUserId(),userid,page);
        return compositeThread(flowable);
    }

    /**
     * 分享
     */
    public Flowable<Object> share(int type,String aid){
        Flowable<HttpResult<Object>> flowable=httpService.share(UserInfo.getInstance().getUserId(),type,aid);
        return compositeThread(flowable);
    }

    /**
     * 获取直播类型
     */
    public Flowable<List<LiveBean>> getLiveType(int type){
        Flowable<HttpResult<List<LiveBean>>> flowable=httpService.liveType(type);
        return compositeThread(flowable);
    }

    /**
     * 直播列表
     */
    public Flowable<List<LiveListBean>> getLiveList(String type,int page){
        Flowable<HttpResult<List<LiveListBean>>> flowable=httpService.liveList(UserInfo.getInstance().getUserId(),type,page);
        return compositeThread(flowable);
    }

    /**
     * 判断用户是否存在
     */
    public Flowable<Object> userIsVail(String id){
        Flowable<HttpResult<Object>> flowable=httpService.userIsVail(UserInfo.getInstance().getUserId(),id);
        return compositeThread(flowable);
    }

    /**
     * 三方登录
     */
    public Flowable<LoginBean> threeLogin(String openid,String token,int type){
        Flowable<HttpResult<LoginBean>> flowable=httpService.threeLogin(openid, token, type);
        return compositeThread(flowable);
    }

    /**
     * 绑定手机
     */
    public Flowable<Object> bindPhone(String phone,String code){
        Flowable<HttpResult<Object>> flowable=httpService.bindThreePhone(UserInfo.getInstance().getUserId(),code,phone);
        return compositeThread(flowable);
    }

    /**
     * 充值
     */
    public Flowable<List<RechargeBean>> rechargeList(){
        Flowable<HttpResult<List<RechargeBean>>> flowable=httpService.rechargeList();
        return compositeThread(flowable);
    }

    /**
     * 充值
     */
    public Flowable<WxOrderBean> recharge(String id){
        Flowable<HttpResult<WxOrderBean>> flowable=httpService.recharge(UserInfo.getInstance().getUserId(),id);
        return compositeThread(flowable);
    }

    /**
     * 创建直播间
     */
    public Flowable<CreateBean> createLive(String title, String type, MultipartBody.Part part) {
        Flowable<HttpResult<CreateBean>> flowable;
        if (part != null) {
            flowable = httpService.createLive(UserInfo.getInstance().getUserId(), title,type, part);
        } else {
            flowable = httpService.createLive(UserInfo.getInstance().getUserId(), title,type);
        }

        return compositeThread(flowable);
    }

    /**
     * 直播间信息
     */
    public Flowable<LiveDetailBean> liveDetail(){
        Flowable<HttpResult<LiveDetailBean>> flowable=httpService.liveDetail(UserInfo.getInstance().getUserId());
        return compositeThread(flowable);
    }

    /**
     * 观看人数增减
     */
    public Flowable<Object> liveSeeCount(String lid,boolean isAdd){
        Flowable<HttpResult<Object>> flowable;
        if (isAdd){
            flowable=httpService.addLiveSeeCount(UserInfo.getInstance().getUserId(),lid);
        }else {
            flowable=httpService.deleteLiveSeeCount(UserInfo.getInstance().getUserId(),lid);
        }
        return compositeThread(flowable);
    }

    /**
     * 礼物列表
     */
    public Flowable<List<GiftBean>> giftList(String type){
        Flowable<HttpResult<List<GiftBean>>> flowable=httpService.liveGift(type);
        return compositeThread(flowable);
    }

    /**
     * 关闭直播间
     */
    public Flowable<Object> liveClose(String lid){
        Flowable<HttpResult<Object>> flowable=httpService.liveFinish(UserInfo.getInstance().getUserId(),lid);
        return compositeThread(flowable);
    }

    /**
     * 送礼物
     */
    public Flowable<Object> sendGift(String lid,String aid,String count){
        Flowable<HttpResult<Object>> flowable=httpService.sendGift(UserInfo.getInstance().getUserId(),aid,lid,count);
        return compositeThread(flowable);
    }

    /**
     * 直播间信息
     */
    public Flowable<LiveDetail> liveInfo(String lid){
        Flowable<HttpResult<LiveDetail>> flowable=httpService.liveInfo(UserInfo.getInstance().getUserId(),lid);
        return compositeThread(flowable);
    }

    /**
     * 打开直播

     */
    public Flowable<Object> openLive(String lid){
        Flowable<HttpResult<Object>> flowable=httpService.openLive(UserInfo.getInstance().getUserId(),lid);
        return compositeThread(flowable);
    }

    /**
     * 批量上传文件
     *
     * @param pathList 文件集合
     */
    public List<MultipartBody.Part> batchUploadFiles(List<String> pathList) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                //表单类型 
                .setType(MultipartBody.FORM);
        //多张图片  
        for (int i = 0; i < pathList.size(); i++) {
            File file = new File(pathList.get(i));//filePath 图片地址  
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), file);
            builder.addFormDataPart("image["+i+"]", file.getName(), imageBody);//"imgfile"+i 后台接收图片流的参数名  
        }
        return builder.build().parts();
    }



    private static RequestBody modeBody(Object model) {
        String route = GsonUtil.gsonString(model);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), route);
    }
}
