package com.ay.lxunhan.http.config;


public class UrlConfig {
    public static final String BASE_URL = "http://hlx.51appdevelop.com";

    /**
     * 登录
     */
    public static final String LOGIN = "media/users/login";

    /**
     * 获取验证码
     */
    public static final String GETCODE = "media/users/send_phone";

    /**
     * 注册 /找回密码
     */
    public static final String REGISTER = "media/users/registered";

    /**
     * 完善信息
     */
    public static final String COMPLETE_INFO = "api/users/user_info_edit";

    /**
     * 个人信息
     */
    public static final String USER_INFO = "api/users/info";

    /**
     * 地区列表
     */
    public static final String ADDRESS = "api/users/address_list";

    /**
     * 首页列表
     */
    public static final String HOME_LIST = "api/homes/many_list";
    public static final String HOME_LIST_ARTICLE = "api/homes/article_list";
    public static final String HOME_LIST_ASK = "api/homes/answers_list";
    public static final String HOME_LIST_QUIZ = "api/homes/vote_list";

    /**
     * 首页类型
     */
    public static final String HOME_TYPE = "api/homes/home_section_list";
    /**
     * 首页详情
     */
    public static final String HOME_DETAIL = "api/homes/show";

    /**
     * 投票详情
     */
    public static final String HOME_DETAIL_QUIZ = "api/homes/vote_show";

    /**
     * 一级评论
     */
    public static final String ONE_COMMENT = "api/homes/one_comment_list";

    /**
     * 二级评论
     */
    public static final String TWO_COMMENT = "api/homes/two_comment_list";

    /**
     * 频道管理
     */
    public static final String CHANNELMANAGEMENT = "api/homes/channel_ment";

    /**
     * 频道添加
     */
    public static final String ADDCHANNEL = "api/homes/home_log_add";

    /**
     * 频道删除
     */
    public static final String DELETECHANNEL = "api/homes/home_log_del";

    /**
     * 视频频道管理
     */
    public static final String CHANNELVIDEOMANAGEMENT = "api/videos/video_sect";

    /**
     * 视频频道添加
     */
    public static final String ADDVIDEOCHANNEL = "api/videos/video_sect_add";

    /**
     * 视频频道删除
     */
    public static final String DELETEVIDEOCHANNEL = "api/videos/video_sect_del";

    /**
     * 发布一级评论
     */
    public static final String SEND_COMMENT_ONE = "api/homes/first_comment_add";

    /**
     * 发布二级评论
     */
    public static final String SEND_COMMENT_TWO = "api/homes/two_comment_add";

    /**
     * 点赞
     */
    public static final String ADDLIKE="api/homes/like_add";

    /**
     * 评论点赞
     */
    public static final String COMMENTLIKE="api/homes/comment_like";

    /**
     * 投票
     */
    public static final String QUIZ="api/homes/vote_log_add";

    /**
     * 采纳
     */
    public static final String ACCEPT="api/homes/adoption";

    /**
     * 投诉
     */
    public static final String COMPLAINT="api/homes/complaint";

    /**
     * 视频推荐
     */
    public static final String VIDEO_HOME="api/videos/recommend_list";

    /**
     * 最近观看
     */
    public static final String VIDEO_RECORDS="api/videos/video_records";

    /**
     * 视频详情
     */
    public static final String VIDEO_DETAIL="api/videos/video_show";

    /**
     * 小视频列表
     */
    public static final String SMALL_VIDEO_LIST="api/videos/short_video_list";

    /**
     * 视频列表
     */
    public static final String VIDEO_TYPE_LIST="api/videos/video_list";

    /**
     * 视频频道列表
     */
    public static final String VIDEO_TYPE="api/videos/video_sect_list";

    /***
     * 视频类型添加
     */
    public static final String VIDEO_CHANNEL_ADD="api/videos/video_sect_add";

    /***
     * 视频类型删除
     */
    public static final String VIDEO_CHANNEL_DELETE="api/videos/video_sect_add";

}
