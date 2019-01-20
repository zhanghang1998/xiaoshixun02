package zyh.com.bean.home;

import java.util.List;

public class HomeJson {

    private List<HomeListBean> rxxp;
    private List<HomeListBean> pzsh;
    private List<HomeListBean> mlss;

    public List<HomeListBean> getRxxp() {
        return rxxp;
    }

    public void setRxxp(List<HomeListBean> rxxp) {
        this.rxxp = rxxp;
    }

    public List<HomeListBean> getPzsh() {
        return pzsh;
    }

    public void setPzsh(List<HomeListBean> pzsh) {
        this.pzsh = pzsh;
    }

    public List<HomeListBean> getMlss() {
        return mlss;
    }

    public void setMlss(List<HomeListBean> mlss) {
        this.mlss = mlss;
    }
}
