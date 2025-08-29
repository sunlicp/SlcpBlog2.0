package com.slcp.devops.constant;

import lombok.experimental.UtilityClass;

/**
 * DevOps基本常量
 *
 * @author devops
 */
@UtilityClass
public class DevOpsConstant {

    /**
     * 应用版本号
     */
    public static final String DEVOPS_APP_VERSION = "3.1.1";

    /**
     * Spring 应用名 prop key
     */
    public static final String SPRING_APP_NAME_KEY = "spring.application.name";


    /**
     * 默认为空消息
     */
    public static final String DEFAULT_NULL_MESSAGE = "承载数据为空";
    /**
     * 默认成功消息
     */
    public static final String DEFAULT_SUCCESS_MESSAGE = "操作成功";
    /**
     * 默认失败消息
     */
    public static final String DEFAULT_FAIL_MESSAGE = "处理失败";
    /**
     * 树的根节点值
     */
    public static final Long TREE_ROOT = -1L;
    /**
     * 允许的文件类型，可根据需求添加
     */
    public static final String[] VALID_FILE_TYPE = {"xlsx", "zip", "pdf"};

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * json类型报文，UTF-8字符集
     */
    public static final String JSON_UTF8 = "application/json;charset=UTF-8";
    /**
     * 请求方式
     */
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_DELETE = "DELETE";

    /**
     * 随机图片
     */

    public static final String IMG_RANDOM = "https://source.unsplash.com/random/";
    public static final String IMG_RANDOM_D = "https://cdn.seovx.com/d/?mom=302";

    /**
     * 获取地理位置
     */
    public static final String CITY_JSON = "https://pv.sohu.com/cityjson?ie=utf-8";

    public static String ACCESS_KEY = "TiXGsgORx2_H4zv1lh6KQdQwX6K9ddTGIbDqv9-E";
    public static String SECRET_KEY = "BkucejcV5zZJpdLbZhQj00b4HBZEFb0YmxDdhhHB";
    /**
     * 空间名称
     */
    public static String BUCKET = "slcp-img";
    /**
     * 外链域名
     */
    public static String DOMAIN = "https://img.slcp.top/";

    /**
     * 获取诗路径
     */
    public static String POEM_PATH = "https://v2.jinrishici.com/sentence";

    /**
     * 获取名言
     */
    public static String FAMOUS_SAYING = "https://api.fghrsh.net/hitokoto/rand/?encode=jsc&uid=3335";
    /**
     * 诗 请求头-key
     */
    public static String POEM_KEY = "X-User-Token";

    /**
     * 诗 请求头-value
     */
    public static String POEM_VALUE = "h26Z72ohlh395e4uI+Tkq4QMTgEBLBsb";

    /**
     * 返回诗
     */
    public static String[] POEM_CONTENT = {"弃我去者，昨日之日不可留；乱我心者，今日之日多烦忧。"};

    /**
     * 返回keywords
     */
    public static String TAG_STR = "个人博客,Slcp,SpringBoot,SpringCloud,DDD,领域驱动设计,云原生,微服务,分布式,数据库,中间件,架构设计,网络,数据结构,算法,优化,java,JUC,css,html,jquery,javascript,Slcpの童话镇,Slcpの时光屋,Slcpの藏宝阁,Slcpの关于我,Slcpの留言板,Slcpの童话镇,Slcpの欢乐园,Slcpの朋友圈,Slcpの友人链,";
    /**
     * 获取ip路径
     */
    public static String IP_PATH = "http://ip-api.com/json/?lang=zh-CN";
    /**
     * 获取ip路径
     */
    public static String IP_JSON = "{\"status\":\"success\",\"country\":\"中国\",\"countryCode\":\"CN\",\"region\":\"HN\",\"regionName\":\"湖南\",\"city\":\"长沙\",\"zip\":\"\",\"lat\":28.2282,\"lon\":112.939,\"timezone\":\"Asia/Shanghai\",\"isp\":\"Chinanet\",\"org\":\"\",\"as\":\"AS4134 CHINANET-BACKBONE\",\"query\":\"218.77.104.248\"}";

    /**
     * 200状态码
     */
    public static int STATUS_200 = 200;
}
