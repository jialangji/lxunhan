package com.ay.lxunhan.bean;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.bean
 * @date 2020/8/3
 */
public class FanyiBean {

    /**
     * tSpeakUrl : http://openapi.youdao.com/ttsapi?q=%ED%95%98%ED%95%98&langType=ko&sign=3F7BE7B654EA7C699B5D552B1606F5AF&salt=1596423806267&voice=4&format=mp3&appKey=7f617129b209df10
     * RequestId : b78993e1-38d4-4589-8feb-48a0979ff4f8
     * query : 哈哈
     * translation : ["하하"]
     * errorCode : 0
     * dict : {"url":"yddict://m.youdao.com/dict?le=eng&q=%E5%93%88%E5%93%88"}
     * webdict : {"url":"http://m.youdao.com/dict?le=eng&q=%E5%93%88%E5%93%88"}
     * l : zh-CHS2ko
     * isWord : false
     * speakUrl : http://openapi.youdao.com/ttsapi?q=%E5%93%88%E5%93%88&langType=zh-CHS&sign=48580299A2F14420A6EEF9B7A9F4F684&salt=1596423806267&voice=4&format=mp3&appKey=7f617129b209df10
     */

    private String tSpeakUrl;
    private String RequestId;
    private String query;
    private String errorCode;
    private DictBean dict;
    private WebdictBean webdict;
    private String l;
    private boolean isWord;
    private String speakUrl;
    private List<String> translation;

    public String getTSpeakUrl() {
        return tSpeakUrl;
    }

    public void setTSpeakUrl(String tSpeakUrl) {
        this.tSpeakUrl = tSpeakUrl;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String RequestId) {
        this.RequestId = RequestId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public DictBean getDict() {
        return dict;
    }

    public void setDict(DictBean dict) {
        this.dict = dict;
    }

    public WebdictBean getWebdict() {
        return webdict;
    }

    public void setWebdict(WebdictBean webdict) {
        this.webdict = webdict;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public boolean isIsWord() {
        return isWord;
    }

    public void setIsWord(boolean isWord) {
        this.isWord = isWord;
    }

    public String getSpeakUrl() {
        return speakUrl;
    }

    public void setSpeakUrl(String speakUrl) {
        this.speakUrl = speakUrl;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public static class DictBean {
        /**
         * url : yddict://m.youdao.com/dict?le=eng&q=%E5%93%88%E5%93%88
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class WebdictBean {
        /**
         * url : http://m.youdao.com/dict?le=eng&q=%E5%93%88%E5%93%88
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
