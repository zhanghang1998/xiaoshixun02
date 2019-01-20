package zyh.com.bean.home;

import java.util.List;

public class HomeListBean {

    /**
     * commodityList : [{"commodityId":27,"commodityName":"休闲马衔扣保暖绒里棉鞋懒人鞋毛毛鞋平底女雪地靴女短靴子豆豆鞋女鞋","masterPic":"http://172.17.8.100/images/small/commodity/nx/ddx/3/1.jpg","price":139,"saleNum":0},{"commodityId":21,"commodityName":"【加绒休闲 舒适轻便】秋冬增高休闲鞋厚底棉鞋运动户外通勤简约韩版女鞋","masterPic":"http://172.17.8.100/images/small/commodity/nx/bx/4/1.jpg","price":189,"saleNum":0},{"commodityId":5,"commodityName":"双头两用修容笔","masterPic":"http://172.17.8.100/images/small/commodity/mzhf/cz/3/1.jpg","price":39,"saleNum":156}]
     * id : 1002
     * name : 热销新品
     */

    private int id;
    private String name;
    private List<CommodityListBean> commodityList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CommodityListBean> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<CommodityListBean> commodityList) {
        this.commodityList = commodityList;
    }
}
