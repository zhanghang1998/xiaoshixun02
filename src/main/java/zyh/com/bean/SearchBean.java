package zyh.com.bean;

import java.util.List;

public class SearchBean {

    /**
     * commodityId : 109
     * commodityName : 新款奢华镶钻镜面iphone xs max手机壳苹果7plusl软边时尚保护套
     * masterPic : http://172.17.8.100/images/small/commodity/sjsm/sjpj/3/1.jpg
     * price : 89
     * saleNum : 0
     */

    private int commodityId;
    private String commodityName;
    private String masterPic;
    private int price;
    private int saleNum;

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getMasterPic() {
        return masterPic;
    }

    public void setMasterPic(String masterPic) {
        this.masterPic = masterPic;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }
}
