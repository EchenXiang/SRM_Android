package Object;

import java.io.Serializable;

/**
 * Created by xiangyichen on 2017/4/26.
 */
public class DeliveryInfo implements Serializable {
    private String sgoods_entname;
    private String sgoods_number;
    private String sgoods_logistics_number;
    private String sgoods_date;
    private String sgoods_plan_date;
    private String sgoods_sender;
    private String sgoods_receiver;
    private String sgoods_re_tele;
    private String sgoods_re_addr;
    private String sgoods_status;

    public String getEntname() {
        return sgoods_entname;
    }
    public void setEntname(String sgoods_entname){
        this.sgoods_entname = sgoods_entname;
    }

    public String getNumber() {
        return sgoods_number;
    }
    public void setNumber(String sgoods_number) {
        this.sgoods_number = sgoods_number;
    }

    public String getLogistics_number() {
        return sgoods_logistics_number;
    }
    public void setLogistics_number(String sgoods_logistics_number) {
        this.sgoods_logistics_number = sgoods_logistics_number;
    }

    public String getDate() {
        return sgoods_date;
    }
    public void setDate(String sgoods_date) {
        this.sgoods_date = sgoods_date;
    }

    public String getPlan_date() {
        return sgoods_plan_date;
    }
    public void setPlan_date(String sgoods_plan_date) {
        this.sgoods_plan_date = sgoods_plan_date;
    }

    public String getSender() {
        return sgoods_sender;
    }
    public void setSender(String sgoods_sender) {
        this.sgoods_sender = sgoods_sender;
    }

    public String getReceiver(){
        return sgoods_receiver;
    }
    public void setReceiver(String sgoods_receiver) {
        this.sgoods_receiver = sgoods_receiver;
    }

    public String getRe_tele() {
        return sgoods_re_tele;
    }
    public void setRe_tele(String sgoods_re_tele) {
        this.sgoods_re_tele = sgoods_re_tele;
    }

    public String getRe_addr() {
        return sgoods_re_addr;
    }
    public void setRe_addr(String sgoods_re_addr) {
        this.sgoods_re_addr = sgoods_re_addr;
    }

    public String getStatus() {
        return sgoods_status;
    }
    public void setStatus(String sgoods_status) {
        this.sgoods_status = sgoods_status;
    }
}