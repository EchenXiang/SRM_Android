package Object;

import java.io.Serializable;

/**
 * Created by luo on 2016/7/4.
 * Stock类 是库存订单的实体类 该类的属性对应表格的字段
 * 一个实例相当于表格的一行数据
 * Serializable 是一个流序列的接口主要用于把Object类型的数据转换成流在Activity 之间传输 这里可以不用
 * 我之前做测试用的
 */
public class Stock implements Serializable{
    private String id;
    private String host;
    private String host_number;
    private String provider;
    private String provider_number;
    private String warehouse;
    private String amount;
    private String price;
    private String unit;
    private String specification;
    private String storage_time;
    private String charge_number;
    private String remarks;
    private String material_number;
    private String material_name;
    public Stock() {
        // TODO Auto-generated constructor stub
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String gethost() {
        return host;
    }
    public void sethost(String host) {
        this.host = host;
    }
    public String gethost_number() {
        return host_number;
    }
    public void sethost_number(String host_number) {
        this.host_number = host_number;
    }

    public String getprovider() {
        return provider;
    }
    public void setprovider(String provider) {
        this.provider = provider;
    }
    public String getprovider_number() {
        return provider_number;
    }
    public void setprovider_number(String provider_number) {
        this.provider_number = provider_number;
    }
    public String getwarehouse() {
        return warehouse;
    }
    public void setwarehouse(String warehouse) {
        this.warehouse = warehouse;
    }
    public String getamount() {
        return amount;
    }
    public void setamount(String amount) {
        this.amount = amount;
    }
    public String getprice() {
        return price;
    }
    public void setprice(String price) {
        this.price = price;
    }
    public String getunit() {
        return unit;
    }
    public void setunit(String unit) {
        this.unit = unit;
    }
    public String getspecification() {
        return specification;
    }
    public void setspecification(String specification) {
        this.specification = specification;
    }

    public String getstorage_time() {
        return storage_time;
    }
    public void setstorage_time(String storage_time) {
        this.storage_time = storage_time;
    }
    public String getcharge_number() {
        return charge_number;
    }
    public void setcharge_number(String charge_number) {
        this.charge_number = charge_number;
    }

    public String getremarks() {
        return remarks;
    }
    public void setremarks(String remarks) {
        this.remarks = remarks;
    }
    public String getmaterial_number() {
        return material_number;
    }
    public void setmaterial_number(String material_number) {
        this.material_number = material_number;
    }
    public String getmaterial_name() {
        return material_name;
    }
    public void setmaterial_name(String material_name) {
        this.material_name = material_name;
    }
    /*@Override
    public String toString() {
        return "Stock [id=" + id + ", name=" + name + ", address=" + address
                + "]";
    }*/
    public Stock(String id, String host, String hostnumber, String provider, String provider_number, String warehouse
    , String amount, String price, String unit, String specification, String storage_time, String charge_number, String remarks, String material_number,
                 String material_name) {
        super();
        this.id = id;
        this.host = host;
        this.host_number = hostnumber;
    }
}
