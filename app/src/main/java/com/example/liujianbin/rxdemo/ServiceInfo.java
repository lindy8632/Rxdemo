package com.example.liujianbin.rxdemo;

import com.google.gson.annotations.Expose;

import java.util.List;

public class ServiceInfo {
    /**
     * limitVersion : 1.0
     * creditService : [{"name":"信用卡账单管理","title":"信用卡账单管理","img":"http://d.kkcredit.cn/rn/service/img/guanli@2x.png","url":"openview://guanli","sign":"16"},{"name":"信用卡办理","title":"信用卡办理","title1":"银行联系方式","img1":"http://d.kkcredit.cn/rn/service/img/bank2@2x.png","img":"http://d.kkcredit.cn/rn/service/img/banli@2x.png","sign":"17","url":"https://kkcredit.liexiong.cc","url1":"openview://bank"},{"name":"信用卡还款","title":"信用卡还款","img":"http://d.kkcredit.cn/rn/service/img/huankuan@2x.png","url":"openview://huankuan","sign":"15"}]
     * lifeService : [{"name":"租手机","img":"http://d.kkcredit.cn/rn/service/img/zushouji@2x.png","sign":"19","url":"http://life.kkcredit.cn/KKLifeDiscount/OutLineXianghuanji"},{"name":"卡卡信用分","img":"http://d.kkcredit.cn/rn/service/img/xinyongfen@2x.png","sign":"21","url":"openview://xinyongfen"},{"name":"征信查询","img":"http://d.kkcredit.cn/rn/service/img/zhengxin@2x.png","sign":"22","url":"openview://zhengxin"},{"name":"公积金查询","img":"http://d.kkcredit.cn/rn/service/img/gongjijin@2x.png","sign":"23","url":"openview://gongjijin"},{"name":"个税计算器","img":"http://d.kkcredit.cn/rn/service/img/geshui@2x.png","sign":"24","url":"openview://geshui"},{"name":"按揭计算器","img":"http://d.kkcredit.cn/rn/service/img/anjie@2x.png","sign":"25","url":"openview://anjie"},{"name":"贷款计算器","img":"http://d.kkcredit.cn/rn/service/img/daikuan@2x.png","sign":"26","url":"openview://daikuan"},{"name":"汇率计算器","img":"http://d.kkcredit.cn/rn/service/img/huilv@2x.png","sign":"27","url":"openview://huilv"},{"name":"大写数字转换","img":"http://d.kkcredit.cn/rn/service/img/daxieshuzi@2x.png","sign":"28","url":"openview://daxieshuzi"}]
     */

    @Expose
    private String limitVersion;
    @Expose
    private List<CreditServiceBean> creditService;
    @Expose
    private List<LifeServiceBean> lifeService;

    public String getLimitVersion() {
        return limitVersion;
    }

    public void setLimitVersion(String limitVersion) {
        this.limitVersion = limitVersion;
    }

    public List<CreditServiceBean> getCreditService() {
        return creditService;
    }

    public void setCreditService(List<CreditServiceBean> creditService) {
        this.creditService = creditService;
    }

    public List<LifeServiceBean> getLifeService() {
        return lifeService;
    }

    public void setLifeService(List<LifeServiceBean> lifeService) {
        this.lifeService = lifeService;
    }

    public static class CreditServiceBean {
        /**
         * name : 信用卡账单管理
         * title : 信用卡账单管理
         * img : http://d.kkcredit.cn/rn/service/img/guanli@2x.png
         * url : openview://guanli
         * sign : 16
         * title1 : 银行联系方式
         * img1 : http://d.kkcredit.cn/rn/service/img/bank2@2x.png
         * url1 : openview://bank
         */
        @Expose
        private String name;
        @Expose
        private String title;
        @Expose
        private String img;
        @Expose
        private String url;
        @Expose
        private String sign;
        @Expose
        private String title1;
        @Expose
        private String img1;
        @Expose
        private String url1;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTitle1() {
            return title1;
        }

        public void setTitle1(String title1) {
            this.title1 = title1;
        }

        public String getImg1() {
            return img1;
        }

        public void setImg1(String img1) {
            this.img1 = img1;
        }

        public String getUrl1() {
            return url1;
        }

        public void setUrl1(String url1) {
            this.url1 = url1;
        }
    }

    public static class LifeServiceBean {
        /**
         * name : 租手机
         * img : http://d.kkcredit.cn/rn/service/img/zushouji@2x.png
         * sign : 19
         * url : http://life.kkcredit.cn/KKLifeDiscount/OutLineXianghuanji
         */
        @Expose
        private String name;
        @Expose
        private String img;
        @Expose
        private String sign;
        @Expose
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
