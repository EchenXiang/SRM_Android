package Object;

/**
 * Created by luo on 2016/7/11.
 */
public class OrderTrack {
    private String company_code;
    private String order_id;

    private String order_generate_day;

    private String order_status;
    private String provider_name;
    private String material_number;
    private String material_name;
    private String specified_delivery;
    private String order_unit;
    private String number;
    private String had_ship_number;
    private String dmark;
    private String delivery_complete;

    public OrderTrack() {
        // TODO Auto-generated constructor stub
    }
    public String getcompany_code() {
        return company_code;
    }
    public void setcompany_code(String company_code) {
        this.company_code = company_code;
    }
    public String getorder_id() {
        return order_id;
    }
    public void setorder_id(String order_id) {
        this.order_id = order_id;
    }




    public String getorder_generate_day() {
        return order_generate_day;
    }
    public void setorder_generate_day(String order_generate_day) {
        this.order_generate_day = order_generate_day;
    }

    public String getorder_status() {
        return order_status;
    }
    public void setorder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getprovider_name() {
        return provider_name;
    }
    public void setprovider_name(String provider_name) {
        this.provider_name = provider_name;
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

    public String getspecified_delivery() {
        return specified_delivery;
    }
    public void setspecified_delivery(String specified_delivery) {
        this.specified_delivery = specified_delivery;
    }

    public String getorder_unit() {
        return order_unit;
    }
    public void setorder_unit(String order_unit) {
        this.order_unit = order_unit;
    }

    public String getnumber() {
        return number;
    }
    public void setnumber(String number) {
        this.number = number;
    }

    public String gethad_ship_number() {
        return had_ship_number;
    }
    public void sethad_ship_number(String had_ship_number) {
        this.had_ship_number = had_ship_number;
    }

    public String getdmark() {
        return dmark;
    }
    public void setdmark(String dmark) {
        this.dmark = dmark;
    }

    public String getdelivery_complete() {
        return delivery_complete;
    }
    public void setdelivery_complete(String delivery_complete) {
        this.delivery_complete = delivery_complete;
    }


}