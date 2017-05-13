package Object;

/**
 * Created by ct on 2016/7/11.
 */
public class OrderStatistic {
    private String company_code;
    private String order_id;

    private String order_generate_day;
    private String provider_confirm;
    private String order_status;
    private String delivery_status;



    public OrderStatistic() {
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
    public String getprovider_confirm() {
        return provider_confirm;
    }
    public void setprovider_confirm(String provider_confirm) {
        this.provider_confirm = provider_confirm;
    }
    public String getorder_status() {
        return order_status;
    }
    public void setorder_status(String order_status) {
        this.order_status = order_status;
    }
    public String getdelivery_status() {
        return delivery_status;
    }
    public void setdelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }


}
