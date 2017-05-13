package Object;

/**
 * Created by luo on 2016/7/4.
 * 该实体类对应于供应商订单查询的表
 * 具体和Stock类是一样的
 */
public class OrderSelect {
    private String company_code;
    private String order_id;
    private String purchase_contact_name;
    private String contact;
    private String order_generate_day;
    private String provider_confirm;
    private String order_status;
    private String delivery_status;
    private String print_number;

    public OrderSelect() {
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
    public String getpurchase_contact_name() {
        return purchase_contact_name;
    }
    public void setpurchase_contact_name(String purchase_contact_name) {
        this.purchase_contact_name = purchase_contact_name;
    }

    public String getcontact() {
        return contact;
    }
    public void setcontact(String contact) {
        this.contact = contact;
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
    public String getprint_number() {
        return print_number;
    }
    public void setprint_number(String print_number) {
        this.print_number = print_number;
    }

}
