package zyh.com.bean.my;

import java.util.List;

public class MyWalletBean {

    private int balance;
    private List<DetailListBean> detailList;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<DetailListBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailListBean> detailList) {
        this.detailList = detailList;
    }
}
