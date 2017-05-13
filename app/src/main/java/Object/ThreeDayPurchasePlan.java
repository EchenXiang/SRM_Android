package Object;
import java.io.Serializable;
/**
 * Created by zhang on 2016/7/9.
 * Threeplanpurchase类 是三日采购计划的实体类 该类的属性对应表格的字段
 * 一个实例相当于表格的一行数据
 * Serializable 是一个流序列的接口主要用于把Object类型的数据转换成流在Activity 之间传输 这里可以不用，
 * 我之前做测试用的
 */
public class ThreeDayPurchasePlan implements Serializable {

    private String id;
    private String provider_number;
    private String provider;
    private String material_number;
    private String material_name;
    private String unit;
    private String release_date;
    private String day_stock;
    private String first_day_require;
    private String second_day_require;
    private String third_day_require;
    private String execetive;
    private String company_code;
    private String input_time;



    public ThreeDayPurchasePlan() {
        // TODO Auto-generated constructor stub
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getProvider_number() {
        return provider_number;
    }
    public void setProvider_number(String provider_number) {
        this.provider_number= provider_number;
    }
    public String getProvider(){return provider;}
    public void setProvider(String provider){this.provider=provider;}

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name=material_name;
    }

    public String getMaterial_number() {
        return material_number;
    }

    public void setMaterial_number(String material_number) {
        this.material_number = material_number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getDay_stock() {
        return day_stock;
    }

    public void setDay_stock(String day_stock) {
        this.day_stock = day_stock;
    }

    public String getFirst_day_require() {
        return first_day_require;
    }

    public void setFirst_day_require(String first_day_require) {
        this.first_day_require = first_day_require;
    }

    public String getSecond_day_require() {
        return second_day_require;
    }

    public void setSecond_day_require(String second_day_require) {
        this.second_day_require = second_day_require;
    }

    public String getThird_day_require() {
        return third_day_require;
    }

    public void setThird_day_require(String third_day_require) {
        this.third_day_require = third_day_require;
    }

    public String getExecetive() {
        return execetive;
    }

    public void setExecetive(String execetive) {
        this.execetive = execetive;
    }

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getInput_time() {
        return input_time;
    }

    public void setInput_time(String input_time) {
        this.input_time = input_time;
    }
}