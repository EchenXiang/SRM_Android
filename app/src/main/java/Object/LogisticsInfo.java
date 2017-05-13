package Object;

import java.io.Serializable;

/**
 * Created by xiangyichen on 2017/4/26.
 */
public class LogisticsInfo implements Serializable {
    private String logistics_entname;
    private String logistics_number;
    private String logistics_company;
    private String logistics_company_tele;
    private String logistics_date;
    private String logistics_plan_date;
    private String logistics_status;

    public String getLogistics_entname() {
        return logistics_entname;
    }
    public void setLogistics_entname(String logistics_entname) {
        this.logistics_entname = logistics_entname;
    }

    public String getLogistics_number(){
        return logistics_number;
    }
    public void setLogistics_number(String logistics_number) {
        this.logistics_number = logistics_number;
    }

    public String getLogistics_company() {
        return logistics_company;
    }
    public void setLogistics_company(String logistics_company) {
        this.logistics_company = logistics_company;
    }

    public String getLogistics_date() {
        return logistics_date;
    }
    public void setLogistics_date(String logistics_date) {

        this.logistics_date = logistics_date;
    }

    public String getLogistics_plan_date() {
        return logistics_plan_date;
    }
    public void setLogistics_plan_date(String logistics_plan_date) {
        this.logistics_plan_date = logistics_plan_date;
    }

    public String getLogistics_company_tele() {
        return logistics_company_tele;
    }
    public void setLogistics_company_tele(String logistics_company_tele) {
        this.logistics_company_tele = logistics_company_tele;
    }

    public String getLogistics_status() {
        return logistics_status;
    }
    public void setLogistics_status(String logistics_confirm) {
        this.logistics_status = logistics_confirm;
    }
}
