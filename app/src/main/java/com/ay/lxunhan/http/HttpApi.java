package com.ay.lxunhan.http;


import com.ay.lxunhan.bean.AddFriendBean;
import com.ay.lxunhan.bean.ChannelBean;
import com.ay.lxunhan.bean.ChatListBean;
import com.ay.lxunhan.bean.CommentBean;
import com.ay.lxunhan.bean.CountryBean;
import com.ay.lxunhan.bean.FriendBean;
import com.ay.lxunhan.bean.HeUserBean;
import com.ay.lxunhan.bean.HomeDetailBean;
import com.ay.lxunhan.bean.HomeQuizDetailBean;
import com.ay.lxunhan.bean.LoginBean;
import com.ay.lxunhan.bean.MultiItemBaseBean;
import com.ay.lxunhan.bean.PeopleBean;
import com.ay.lxunhan.bean.PyqBean;
import com.ay.lxunhan.bean.TwoCommentBean;
import com.ay.lxunhan.bean.TypeBean;
import com.ay.lxunhan.bean.UserInfoBean;
import com.ay.lxunhan.bean.UserMediaListBean;
import com.ay.lxunhan.bean.VideoBean;
import com.ay.lxunhan.bean.VideoDetailBean;
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
    Flowable<HttpResult<List<CommentBean>>> getOneComment(@Query("uqid") String uqid,@Query("aid") String aid,
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
     * 视频推荐列表
     */
    @GET(UrlConfig.SMALL_VIDEO_LIST)
    Flowable<HttpResult<List<VideoBean>>> smallVideoList(@Query("uqid") String uqid,@Query("page") int page);


    /**
     * 视频推荐列表
     */
    @GET(UrlConfig.VIDEO_TYPE_LIST)
    Flowable<HttpResult<List<VideoBean>>> videoTypeList(@Query("uqid") String uqid,@Query("plate_id") String plate_id,@Query("page") int page);

    /**
     * 好友列表
     */
    @GET(UrlConfig.FRIEND_LIST)
    Flowable<HttpResult<List<FriendBean>>> friendsList(@Query("uqid") String uqid);

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
}




