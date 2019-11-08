package com.example.base.burypoint.bean;

public class ExpoBean {

    /** 用于曝光埋点的唯一标识，注意唯一性 **/
    private int expoId;

    private String expoEventName;

    private String pageType;

    private String prePosition;

    private int page;

    private int specialTopic;

    private String activity;

    private int commodityID;

    private String commodityName;

    private double pricePerCommodity;

    private String floorName;

    private int floorRank;

    private int commodityRank;

    private String keyWord;

    private String searchID;

    private String markCode;

    /** 记录出现的系统时间 **/
    private long showTime;

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getPrePosition() {
        return prePosition;
    }

    public void setPrePosition(String prePosition) {
        this.prePosition = prePosition;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSpecialTopic() {
        return specialTopic;
    }

    public void setSpecialTopic(int specialTopic) {
        this.specialTopic = specialTopic;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(int commodityID) {
        this.commodityID = commodityID;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public double getPricePerCommodity() {
        return pricePerCommodity;
    }

    public void setPricePerCommodity(double pricePerCommodity) {
        this.pricePerCommodity = pricePerCommodity;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public int getFloorRank() {
        return floorRank;
    }

    public void setFloorRank(int floorRank) {
        this.floorRank = floorRank;
    }

    public int getCommodityRank() {
        return commodityRank;
    }

    public void setCommodityRank(int commodityRank) {
        this.commodityRank = commodityRank;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getSearchID() {
        return searchID;
    }

    public void setSearchID(String searchID) {
        this.searchID = searchID;
    }

    public String getMarkCode() {
        return markCode;
    }

    public void setMarkCode(String markCode) {
        this.markCode = markCode;
    }

    public long getShowTime() {
        return showTime;
    }

    public void setShowTime(long showTime) {
        this.showTime = showTime;
    }

    public int getExpoId() {
        return expoId;
    }

    public void setExpoId(int expoId) {
        this.expoId = expoId;
    }

    public String getExpoEventName() {
        return expoEventName;
    }

    public void setExpoEventName(String expoEventName) {
        this.expoEventName = expoEventName;
    }
}
