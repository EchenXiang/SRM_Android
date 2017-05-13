package Object;

import java.io.Serializable;

/**
 * Created by 贾子夏 on 2016/7/15.
 */

public class DoubleWeekPurchasePlan implements Serializable {

    private String id;
    private String provider_number;
    private String provider;
    private String material_number;
    private String material_name;
    private String unit;
    private String release_week;
    private String day_stock;
    private String first_week_require;
    private String second_week_require;
    private String execetive;
    private String company_code;
    private String input_time;



    public DoubleWeekPurchasePlan() {
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

    public String getRelease_week() {
        return release_week;
    }

    public void setRelease_week(String release_week) {
        this.release_week = release_week;
    }

    public String getDay_stock() {
        return day_stock;
    }

    public void setDay_stock(String day_stock) {
        this.day_stock = day_stock;
    }

    public String getFirst_week_require() {
        return first_week_require;
    }

    public void setFirst_week_require(String first_week_require) {
        this.first_week_require = first_week_require;
    }

    public String getSecond_week_require() {
        return second_week_require;
    }

    public void setSecond_week_require(String second_week_require) {
        this.second_week_require = second_week_require;
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