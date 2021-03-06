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
import com.ay.lxunhan.bean.FanyiBean;
import com.ay.lxunhan.bean.FriendBean;
import com.ay.lxunhan.bean.GiftBean;
import com.ay.lxunhan.bean.HeUserBean;
import com.ay.lxunhan.bean.HomeDetailBean;
import com.ay.lxunhan.bean.HomeQuizDetailBean;
import com.ay.lxunhan.bean.HotSearchBean;
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
import com.ay.lxunhan.bean.RecommendBean;
import com.ay.lxunhan.bean.SingBean;
import com.ay.lxunhan.bean.TaskBean;
import com.ay.lxunhan.bean.TwoCommentBean;
import com.ay.lxunhan.bean.TypeBean;
import com.ay.lxunhan.bean.UserInfoBean;
import com.ay.lxunhan.bean.UserMediaListBean;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.bean.VideoDetailBean;
import com.ay.lxunhan.bean.WxOrderBean;
import com.ay.lxunhan.http.config.UrlConfig;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface HttpApi {
    /**
     * 获取验证码
     */
    @GET(UrlConfig.GETCODE)
    Flowable<HttpResult<Object>> getCode(@Query("phone") String phone,@Query("type") int type);

    /**
     * 注册/找回密码
     */
    @POST(UrlConfig.REGISTER)
    Flowable<HttpResult<LoginBean>> register(@Body RequestBody body);

    /**
     * 拉黑
     */
    @GET(UrlConfig.PULL_BLACK)
    Flowable<HttpResult<Object>> pullBlack(@Query("uqid") String uqid,@Query("bid") String bid);

    /**
     * 拉黑
     */
    @GET(UrlConfig.UNLIKE_TEXT)
    Flowable<HttpResult<Object>> unlike(@Query("uqid") String uqid,@Query("aid") String aid);

    /**
     * 登录
     */
    @POST(UrlConfig.LOGIN)
    Flowable<HttpResult<LoginBean>> login(@Body RequestBody body);

    /**
     *完善信息
     */
    @POST(UrlConfig.COMPLETE_INFO)
    Flowable<HttpResult<Object>> completeInfo(@Body RequestBody body);

    /**
     *完善信息
     */
    @Multipart
    @POST(UrlConfig.COMPLETE_INFO)
    Flowable<HttpResult<Object>> completeInfoImg(@Query("uqid") String uqid,@Part MultipartBody.Part part);

    /**
     * 个人信息
     */
    @GET(UrlConfig.USER_INFO)
    Flowable<HttpResult<UserInfoBean>> getUserInfo(@Query("uqid") String uqid);

    /**
     * 首页列表
     */
    @GET(UrlConfig.HOME_LIST)
    Flowable<HttpResult<List<MultiItemBaseBean>>> getHomeList(@Query("uqid") String uqid, @Query("type") int type,
                                                             @Query("title") String title, @Query("page") int page,
                                                             @Query("limit") int limit);
    /**
     * 问答列表
     */
    @GET(UrlConfig.HOME_LIST_ASK)
    Flowable<HttpResult<List<MultiItemBaseBean>>> getHomeListAsk(@Query("uqid") String uqid, @Query("page") int page,
                                                              @Query("limit") int limit);

    /**
     * 投票列表
     */
    @GET(UrlConfig.HOME_LIST_QUIZ)
    Flowable<HttpResult<List<MultiItemBaseBean>>> getHomeListQuiz(@Query("uqid") String uqid, @Query("page") int page,
                                                                 @Query("limit") int limit);

    /**
     * 推荐列表
     */
    @GET(UrlConfig.HOME_LIST_ARTICLE)
    Flowable<HttpResult<List<MultiItemBaseBean>>> getHomeListArticle(@Query("uqid") String uqid,
                                                                     @Query("plate_id") String plateId,
                                                                     @Query("page") int page,
                                                                  @Query("limit") int limit);

    /**
     * 首页类型
     */
    @GET(UrlConfig.HOME_TYPE)
    Flowable<HttpResult<List<TypeBean>>> getHomeType(@Query("uqid") String uqid);

    /**
     * 视频类型
     */
    @GET(UrlConfig.VIDEO_TYPE)
    Flowable<HttpResult<List<TypeBean>>> getVideType(@Query("uqid") String uqid);

    /**
     * 地区列表
     */
    @GET(UrlConfig.ADDRESS)
    Flowable<HttpResult<List<CountryBean>>> getAddress(@Query("uqid") String uqid);

    /**
     * 首页详情
     */
    @GET(UrlConfig.HOME_DETAIL)
    Flowable<HttpResult<HomeDetailBean>> getHomeDetail(@Query("uqid") String uqid,@Query("type") int type,@Query("id") int id);

    /**
     * 投票详情
     */
    @GET(UrlConfig.HOME_DETAIL_QUIZ)
    Flowable<HttpResult<HomeQuizDetailBean>> getHomeQuizDetail(@Query("uqid") String uqid,@Query("id") int id);

    /**
     * 一级评论
     */
    @GET(UrlConfig.ONE_COMMENT)
    Flowable<HttpResult<CommentBean>> getOneComment(@Query("uqid") String uqid,@Query("aid") String aid,
                                                          @Query("type") int type,@Query("page") int page,@Query("limit") int limit);

    /**
     * 二级评论
     */
    @GET(UrlConfig.TWO_COMMENT)
    Flowable<HttpResult<TwoCommentBean>> getTwoComment(@Query("uqid") String uqid,@Query("id") String id
            ,@Query("page") int page,@Query("limit") int limit);

    /**
     * 频道管理
     */
    @GET(UrlConfig.CHANNELMANAGEMENT)
    Flowable<HttpResult<ChannelBean>> getChannelManagement(@Query("uqid") String uqid);



    /**
     * 添加频道
     */
    @POST(UrlConfig.ADDCHANNEL)
    Flowable<HttpResult<Object>> addChannel(@Body RequestBody body);

    /**
     * 删除频道
     */
    @DELETE(UrlConfig.DELETECHANNEL)
    Flowable<HttpResult<Object>> deleteChannel(@Query("uqid") String uid,@Query("plate_id") String plate_id);

    /**
     * 视频频道管理
     */
    @GET(UrlConfig.CHANNELVIDEOMANAGEMENT)
    Flowable<HttpResult<ChannelBean>> getVideoChannelManagement(@Query("uqid") String uqid);



    /**
     * 视频添加频道
     */
    @POST(UrlConfig.ADDVIDEOCHANNEL)
    Flowable<HttpResult<Object>> addVideoChannel(@Body RequestBody body);

    /**
     * 视频删除频道
     */
    @DELETE(UrlConfig.DELETEVIDEOCHANNEL)
    Flowable<HttpResult<Object>> deleteVideoChannel(@Query("uqid") String uid,@Query("plate_id") String plate_id);

    /**
     * 一级评论
     */
    @POST(UrlConfig.SEND_COMMENT_ONE)
    Flowable<HttpResult<Object>> sendOneComment(@Body RequestBody body);

    /**
     * 二级评论
     */
    @POST(UrlConfig.SEND_COMMENT_TWO)
    Flowable<HttpResult<Object>> sendTwoComment(@Body RequestBody body);

    /**
     * 点赞
     */
    @POST(UrlConfig.ADDLIKE)
    Flowable<HttpResult<Object>> addLike(@Body RequestBody body);

    /**
     * 评论点赞
     */
    @POST(UrlConfig.COMMENTLIKE)
    Flowable<HttpResult<Object>> commentLike(@Body RequestBody body);

    /**
     * 投票
     */
    @POST(UrlConfig.QUIZ)
    Flowable<HttpResult<Object>> quiz(@Body RequestBody body);

    /**
     * 采纳
     */
    @POST(UrlConfig.ACCEPT)
    Flowable<HttpResult<Object>> accept(@Body RequestBody body);

    /**
     * 投诉
     */
    @POST(UrlConfig.COMPLAINT)
    Flowable<HttpResult<Object>> complaint(@Body RequestBody body);

    /**
     * 视频推荐列表
     */
    @GET(UrlConfig.VIDEO_HOME)
    Flowable<HttpResult<List<VideoBean>>> videoHome(@Query("uqid") String uqid,@Query("title") String title,@Query("page") int page);

    /**
     * 最近观看列表
     */
    @GET(UrlConfig.VIDEO_RECORDS)
    Flowable<HttpResult<List<PeopleBean>>> videoRecords(@Query("uqid") String uqid);

    /**
     * 视频小列表
     */
    @GET(UrlConfig.SMALL_VIDEO_LIST)
    Flowable<HttpResult<List<VideoBean>>> smallVideoList(@Query("uqid") String uqid,@Query("page") int page,
                                                         @Query("id") int id);

    /**
     * 视频推荐列表
     */
    @GET(UrlConfig.VIDEO_TYPE_LIST)
    Flowable<HttpResult<List<VideoBean>>> videoTypeList(@Query("uqid") String uqid,@Query("plate_id") String plate_id,@Query("page") int page);

    /**
     * 好友列表
     */
    @GET(UrlConfig.FRIEND_LIST)
    Flowable<HttpResult<List<FriendBean>>> friendsList(@Query("uqid") String uqid,@Query("keys") String keys);

    /**
     * 小视频观看记录
     */
    @POST(UrlConfig.SMALL_VIDEO_WATCH)
    Flowable<HttpResult<Object>> smallVideoWatch(@Body RequestBody body);

    /**
     * 添加好友
     */
    @GET(UrlConfig.ADD_FRIEND)
    Flowable<HttpResult<AddFriendBean>> addFriends(@Query("uqid") String uqid,@Query("keys") String keys,
    @Query("page") int page);

    /**
     * 关注
     */
    @PUT(UrlConfig.ATTENTION)
    Flowable<HttpResult<Object>> attention(@Body RequestBody body);

    /**
     * 关注多人
     */
    @PUT(UrlConfig.MORE_ATTENTION)
    Flowable<HttpResult<Object>> moreAttention(@Body RequestBody body);

    /**
     * 视频详情
     */
    @GET(UrlConfig.VIDEO_DETAIL)
    Flowable<HttpResult<VideoDetailBean>> videoDetail(@Query("uqid") String uqid, @Query("id") String id);

    /**
     * 发布
     */
    @Multipart
    @POST(UrlConfig.PYQ_ISSUE)
    Flowable<HttpResult<Object>> issue(@Query("uqid") String uqid, @Query("title") String title,
                                          @Part List<MultipartBody.Part> cover_img);
    /**
     * 发布
     */
    @POST(UrlConfig.PYQ_ISSUE)
    Flowable<HttpResult<Object>> issue(@Query("uqid") String uqid,@Query("title") String title);

    /**
     * 朋友圈列表
     */
    @GET(UrlConfig.PYQ_LIST)
    Flowable<HttpResult<List<PyqBean>>> pyqList(@Query("uqid") String uqid,@Query("page") int page);

    /**
     * 聊天列表
     */
    @GET(UrlConfig.CHAT_LIST)
    Flowable<HttpResult<List<ChatListBean>>> chatList(@Query("uqid") String uqid,@Query("page") int page);

    /**
     * 我的资料
     */
    @GET(UrlConfig.MY_INFO)
    Flowable<HttpResult<LoginBean>> myinfo(@Query("uqid") String uqid);

    /**
     * 用户详情
     */
    @GET(UrlConfig.MEIDA_USER_INFO)
    Flowable<HttpResult<HeUserBean>> userInfoDetail(@Query("uqid") String uqid,@Query("uzid") String uzid);

    /**
     * 用户资料
     */
    @GET(UrlConfig.MEIDA_USER_DATA)
    Flowable<HttpResult<HeUserBean>> userInfoData(@Query("uqid") String uqid,@Query("uzid") String uzid);

    /**
     * 自媒体人发布列表
     */
    @GET(UrlConfig.MEIDA_LIST)
    Flowable<HttpResult<List<UserMediaListBean>>> userMediaList(@Query("uqid") String uqid, @Query("uzid") String uzid,
                                                                @Query("page") int page, @Query("type") int type);

    /**
     *好友详情动态
     */
    @GET(UrlConfig.USER_DYNAMIC_LIST)
    Flowable<HttpResult<List<PyqBean>>> userList(@Query("uqid") String uqid, @Query("uzid") String uzid,
                                                  @Query("page") int page);



    /**
     * 朋友圈删除
     */
    @DELETE(UrlConfig.PYQ_DELETE)
    Flowable<HttpResult<Object>> pyqDelete(@Query("uqid") String uqid, @Query("id") String id);

    /**
     * 朋友圈详情
     */
    @GET(UrlConfig.PYQ_DETAIL)
    Flowable<HttpResult<PyqDetailBean>> pyqDetail(@Query("uqid") String uqid, @Query("id") String id);


    /**
     * 粉丝列表
     */
    @GET(UrlConfig.FANS_LIST)
    Flowable<HttpResult<List<AttentionBean>>> fansList(@Query("uqid") String uqid,
                                                 @Query("page") int page);

    /**
     * 自媒体人粉丝列表
     */
    @GET(UrlConfig.MEDIA_FANS_LIST)
    Flowable<HttpResult<List<AttentionBean>>> mediafansList(@Query("uqid") String uqid, @Query("uzid") String uzid,
                                                      @Query("page") int page);


    /**
     * 关注列表
     */
    @GET(UrlConfig.ATTENTION_LIST)
    Flowable<HttpResult<List<AttentionBean>>> attentionList(@Query("uqid") String uqid,
                                                      @Query("page") int page);



    /**
     * 粉丝列表
     */
    @GET(UrlConfig.MEDIA_ATTENTION_LIST)
    Flowable<HttpResult<List<AttentionBean>>> mediaAttentionList(@Query("uqid") String uqid, @Query("uzid") String uzid,
                                                           @Query("page") int page);


    /**
     * 添加收藏
     */
    @GET(UrlConfig.ADD_COLLECT)
    Flowable<HttpResult<Object>> addCollect(@Query("uqid") String uqid,@Query("aid") String aid,@Query("type") String type);


    /**
     * 乐币展示
     */
    @GET(UrlConfig.LB_SELECT)
    Flowable<HttpResult<LbBean>> lbShow(@Query("uqid") String uqid);

    /**
     * 乐币任务列表
     */
    @GET(UrlConfig.LB_TASK_LIST)
    Flowable<HttpResult<List<TaskBean>>> lbTaskList(@Query("uqid") String uqid);


    /**
     * 乐币任务列表
     */
    @GET(UrlConfig.LB_DAY_TASK_LIST)
    Flowable<HttpResult<List<TaskBean>>> lbDayTaskList(@Query("uqid") String uqid);

    /***
     * 乐币兑换
     */
    @POST(UrlConfig.LB_EXCHANGE)
    Flowable<HttpResult<Object>> lbExchange(@Body RequestBody body);

    /**
     * 乐币明细
     */
    @GET(UrlConfig.LB_BILL_LIST)
    Flowable<HttpResult<List<CoinBean>>> lbBill(@Query("uqid") String uqid,@Query("page") int page);

    /**
     *账单列表
     */
    @GET(UrlConfig.BILL_LIST)
    Flowable<HttpResult<BillBean>> billList(@Query("uqid") String uqid,@Query("type") int type,
                                                  @Query("time_type") int time_type,@Query("page") int page);


    /**
     * 朋友圈评论
     */
    @GET(UrlConfig.PYQ_COMMENT)
    Flowable<HttpResult<PyqCommentBean>> pyqComment(@Query("uqid") String uqid,@Query("aid") String aid
            ,@Query("page") int page);

    /**
     * 提现
     */
    @POST(UrlConfig.WITHDRAW)
    Flowable<HttpResult<Object>> withdraw(@Body RequestBody body);

    /**
     * 通知消息列表
     */
    @GET(UrlConfig.NOTATION_LIST)
    Flowable<HttpResult<NotationBean>> notation(@Query("uqid") String uqid,@Query("page") int page);

    /**
     * 系统通知列表
     */
    @GET(UrlConfig.NOTATION_SYSTEM_LIST)
    Flowable<HttpResult<List<NotationSystemBean>>> notationSystem(@Query("uqid") String uqid,@Query("page") int page);

    /**
     * 历史
     */
    @GET(UrlConfig.HISTORY)
    Flowable<HttpResult<List<UserMediaListBean>>> history(@Query("uqid") String uqid,@Query("page") int page);


    /**
     * 收藏
     */
    @GET(UrlConfig.COLLECT)
    Flowable<HttpResult<List<UserMediaListBean>>> collect(@Query("uqid") String uqid,@Query("status") int status,@Query("page") int page);

    /**
     * 签到信息
     */
    @GET(UrlConfig.SING_INFO)
    Flowable<HttpResult<SingBean>> singInfo(@Query("uqid") String uqid);

    /**
     * 用户签到
     */
    @GET(UrlConfig.USER_SING)
    Flowable<HttpResult<String>> userSing(@Query("uqid") String uqid);

    /**
     * 邀请记录
     */
    @GET(UrlConfig.INVITE_LIST)
    Flowable<HttpResult<InviteBean>> inviteList(@Query("uqid") String uqid,@Query("page") int page);

    /**
     * 填写邀请码
     */
    @GET(UrlConfig.SEND_INVITE_CODE)
    Flowable<HttpResult<Object>> sendInviteCode(@Query("uqid") String uqid,@Query("code") String page);

    /**
     * 聊天详情
     */
    @GET(UrlConfig.CHAT_IM_LIST)
    Flowable<HttpResult<ChatImBean>> chatImlist(@Query("uqid") String uqid,@Query("uzid") String uzid,@Query("page") int page);

    /**
     * 分享
     */
    @GET(UrlConfig.SHARE)
    Flowable<HttpResult<Object>> share(@Query("uqid") String uqid,@Query("type") int type,@Query("aid") String aid);

    /**
     * 直播类型
     */
    @GET(UrlConfig.LIVE_TYPE)
    Flowable<HttpResult<List<LiveBean>>> liveType(@Query("type") int type);

    /**
     * 直播列表
     */
    @GET(UrlConfig.LIVE_LIST)
    Flowable<HttpResult<List<LiveListBean>>> liveList(@Query("uqid") String uqid,@Query("type") String type,@Query("page") int page,
                                                      @Query("name") String name);

    /**
     * 判断用户是否存在
     */
    @GET(UrlConfig.USER)
    Flowable<HttpResult<Object>> userIsVail(@Query("uqid") String uqid,@Query("user_id") String user_id);

    /**
     * 三方登录
     */
    @GET(UrlConfig.THREE_LOGIN)
    Flowable<HttpResult<LoginBean>> threeLogin(@Query("openid") String openid,@Query("token") String token,
                                               @Query("type") int type);


    /**
     * 绑定手机
     */
    @GET(UrlConfig.THREE_BIND_PHONE)
    Flowable<HttpResult<Object>> bindThreePhone(@Query("uqid") String uqid,@Query("code") String code,
                                                @Query("phone") String phone);

    /**
     *添加直播间观看人数
     */
    @GET(UrlConfig.ADD_LIVE_SEE_COUNT)
    Flowable<HttpResult<Object>> addLiveSeeCount(@Query("uqid") String uqid,@Query("lid") String lid);

    /**
     *减少直播间观看人数
     */
    @GET(UrlConfig.DELETE_LIVE_SEE_COUNT)
    Flowable<HttpResult<Object>> deleteLiveSeeCount(@Query("uqid") String uqid,@Query("lid") String lid);

    /**
     * 礼物列表
     */
    @GET(UrlConfig.LIVE_GIFT)
    Flowable<HttpResult<List<GiftBean>>> liveGift(@Query("type") String type);

    /**
     * 充值列表
     */
    @GET(UrlConfig.RECHARGE_LIST)
    Flowable<HttpResult<List<RechargeBean>>> rechargeList();

    /**
     * 充值
     */
    @GET(UrlConfig.RECHARGE_WX)
    Flowable<HttpResult<WxOrderBean>> recharge(@Query("uqid") String uqid,@Query("id") String id);

    /**
     * 创建直播间
     */
    @Multipart
    @POST(UrlConfig.CREATE_LIVE)
    Flowable<HttpResult<CreateBean>> createLive(@Query("uqid") String uqid, @Query("name") String name, @Query("type") String type,
                                                @Part MultipartBody.Part cover);
    /**
     * 创建直播间
     */
    @POST(UrlConfig.CREATE_LIVE)
    Flowable<HttpResult<CreateBean>> createLive(@Query("uqid") String uqid,@Query("name") String name, @Query("type") String type);

    /**
     * 直播详情
     */
    @GET(UrlConfig.LIVE_DETAIL)
    Flowable<HttpResult<LiveDetailBean>> liveDetail(@Query("uqid") String uqid);

    /**
     * 主播关闭直播间
     */
    @GET(UrlConfig.LIVE_FINISH)
    Flowable<HttpResult<Object>> liveFinish(@Query("uqid") String uqid,@Query("lid") String lid);

    /**
     * 送礼物
     */
    @GET(UrlConfig.SEND_GIFT)
    Flowable<HttpResult<Object>> sendGift(@Query("uqid") String uqid,@Query("gid") String gid,@Query("lid") String lid
    ,@Query("count") String count);

    /**
     * 获取直播间信息
     */
    @GET(UrlConfig.LIVE_INFO)
    Flowable<HttpResult<LiveDetail>> liveInfo(@Query("uqid") String uqid,@Query("lid") String lid);

    /**
     * 开启直播
     */
    @GET(UrlConfig.OPEN_LIVE)
    Flowable<HttpResult<Object>> openLive(@Query("uqid") String uqid,@Query("lid") String lid);

    /**
     * 翻译
     */
    @POST(UrlConfig.FANYI)
    Flowable<HttpResult<FanyiBean>> fanyi(@Query("q") String q);

    /**
     * 乐币任务完成
     */
    @GET(UrlConfig.LB_TASK_COMPLETE)
    Flowable<HttpResult<Object>> lbTaskComplete(@Query("uqid") String uqid,@Query("tid") String tid);

    /**
     * 乐币任务完成
     */
    @GET(UrlConfig.LB_DAY_TASK_COMPLETE)
    Flowable<HttpResult<Object>> lbDayTaskComplete(@Query("uqid") String uqid,@Query("tid") String tid);

    /**
     * 热门搜索
     */
    @GET(UrlConfig.SEARCH_HOT)
    Flowable<HttpResult<List<HotSearchBean>>> hotSearch();

    /**
     * 清空历史
     */
    @GET(UrlConfig.DELETE_HISTORY)
    Flowable<HttpResult<Object>> deleteHistory(@Query("uqid") String uqid);

    /**
     * 搜索
     */
    @GET(UrlConfig.SEARCH)
    Flowable<HttpResult<List<UserMediaListBean>>> search(@Query("uqid") String uqid,@Query("page") int page,@Query("type") int type,@Query("title") String title);

    /**
     * 清空收藏
     */
    @GET(UrlConfig.DELETE_COLLECT)
    Flowable<HttpResult<Object>> deleteCollect(@Query("uqid") String uqid,@Query("aid") String aid,@Query("type") String type);



    @GET(UrlConfig.CONFIG_INFO)
    Flowable<HttpResult<String>> getConfigShow(@Query("uqid") String uqid,@Query("key") String key);


    /**
     * 支付宝支付
     */
    @POST(UrlConfig.ALIPAY)
    Flowable<HttpResult<String>> alipay(@Query("uqid") String uqid,@Query("id") String id,@Query("pay_type") String pay_type);

    @GET(UrlConfig.RECOMMEND)
    Flowable<HttpResult<List<RecommendBean>>> recommend(@Query("plate_id") String plate_id,@Query("type") String type,@Query("aid") String aid);

}




