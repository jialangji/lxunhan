package com.ay.lxunhan.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/7/14
 */
public class WxOrderBean {


    /**
     * appid : wx08cbd3a5db2809a2
     * partnerid : 1600922445
     * prepayid : wx141553456144608fefc4f0b21351584600
     * noncestr : 5f0d648abb1b2
     * timestamp : 1594713226
     * package : Sign=WXPay
     * sign : 426E2EBFEA0776BD9F085734FF6CD13C
     */

    private String appid;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;
    @SerializedName("package")
    private String packageX;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
