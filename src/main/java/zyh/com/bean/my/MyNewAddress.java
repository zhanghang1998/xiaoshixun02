package zyh.com.bean.my;

public class MyNewAddress {

    private String City;
    private String address;

    public MyNewAddress(String city, String address) {
        City = city;
        this.address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
