package Object;

import java.io.Serializable;

/**
 * Created by acer on 2016/7/15.
 */
public class TenDayPurchasePlan  implements Serializable {
    private String id;
    private String provider_number;
    private String provider;
    private String material_number;
    private String material_name;
    private String unit;
    private String release_decad;
    private String day_stock;
    private String decad_require;
    private String execetive;
    private String company_code;
    private String input_time;



    public TenDayPurchasePlan() {
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

    public String getRelease_decad() {
        return release_decad;
    }

    public void setRelease_decad(String release_decad) {
        this.release_decad = release_decad;
    }

    public String getDay_stock() {
        return day_stock;
    }

    public void setDay_stock(String day_stock) {
        this.day_stock = day_stock;
    }

    public String getDecad_require() {
        return decad_require;
    }

    public void setDecad_require(String decad_require) {
        this.decad_require = decad_require;
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