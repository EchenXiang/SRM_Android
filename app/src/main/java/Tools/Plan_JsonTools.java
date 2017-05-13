package Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Object.ThreeDayPurchasePlan;
import Object.MonthPurchasePlan;
import Object.TenDayPurchasePlan;
import Object.YearPurchasePlan;
import Object.DoubleWeekPurchasePlan;
/**
 * Created by Administrator on 2016/7/9.
 */
public class Plan_JsonTools {
    /**此函数是读取Json字符串返回一个Stock实例
     * @param key
     * @param jsonString
     * @return
     */

    public static ThreeDayPurchasePlan getThreeplanpurchase (String key, String jsonString){

        ThreeDayPurchasePlan threeplanpurchase=new ThreeDayPurchasePlan();


        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            JSONObject threeplanpurchaseObject=jsonObject.getJSONObject(key);

            threeplanpurchase.setId(threeplanpurchaseObject.getString("id"));
            threeplanpurchase.setCompany_code(threeplanpurchaseObject.getString("company_code"));
            threeplanpurchase.setDay_stock(threeplanpurchaseObject.getString("day_stock"));
            threeplanpurchase.setExecetive(threeplanpurchaseObject.getString("execetive"));
            threeplanpurchase.setFirst_day_require(threeplanpurchaseObject.getString("first_day_require"));
            threeplanpurchase.setInput_time(threeplanpurchaseObject.getString("input_time"));
            threeplanpurchase.setMaterial_name(threeplanpurchaseObject.getString("material_name"));
            threeplanpurchase.setMaterial_number(threeplanpurchaseObject.getString("material_number"));
            threeplanpurchase.setProvider(threeplanpurchaseObject.getString("provider"));
            threeplanpurchase.setProvider_number(threeplanpurchaseObject.getString("provider_name"));
            threeplanpurchase.setRelease_date(threeplanpurchaseObject.getString("release_date"));
            threeplanpurchase.setSecond_day_require(threeplanpurchaseObject.getString("second_day_require"));
            threeplanpurchase.setThird_day_require(threeplanpurchaseObject.getString("Third_day_require"));
            threeplanpurchase.setUnit(threeplanpurchaseObject.getString("unit"));

        } catch (JSONException e) {
            // TODO: handle exception
        }


        return threeplanpurchase;
    }


    public static List<ThreeDayPurchasePlan> getThreeplanpurchases(String key, String jsonString){
        List<ThreeDayPurchasePlan> list=new ArrayList<ThreeDayPurchasePlan>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                ThreeDayPurchasePlan threeplanpurchase=new ThreeDayPurchasePlan();
                threeplanpurchase.setId(jsonObject2.getString("id"));

                // threeplanpurchase.setId(threeplanpurchaseObject.getString("id"));
                threeplanpurchase.setCompany_code(jsonObject2.getString("company_code"));
                threeplanpurchase.setDay_stock(jsonObject2.getString("day_stock"));
                threeplanpurchase.setExecetive(jsonObject2.getString("execetive"));
                threeplanpurchase.setFirst_day_require(jsonObject2.getString("first_day_require"));
                threeplanpurchase.setInput_time(jsonObject2.getString("input_time"));
                threeplanpurchase.setMaterial_name(jsonObject2.getString("material_name"));
                threeplanpurchase.setMaterial_number(jsonObject2.getString("material_number"));
                threeplanpurchase.setProvider(jsonObject2.getString("provider"));
                threeplanpurchase.setProvider_number(jsonObject2.getString("provider_number"));
                threeplanpurchase.setRelease_date(jsonObject2.getString("release_date"));
                threeplanpurchase.setSecond_day_require(jsonObject2.getString("second_day_require"));
                threeplanpurchase.setThird_day_require(jsonObject2.getString("third_day_require"));
                threeplanpurchase.setUnit(jsonObject2.getString("unit"));
                list.add(threeplanpurchase);

            }
        } catch (JSONException e) {
            // TODO: handle exception
        }

        return list;
    }

    public static DoubleWeekPurchasePlan getDoubleWeekPurchasePlan (String key, String jsonString){

        DoubleWeekPurchasePlan doubleWeekPurchasePlan=new DoubleWeekPurchasePlan();


        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            JSONObject doubleWeekPurchasePlanObject=jsonObject.getJSONObject(key);

            doubleWeekPurchasePlan.setId(doubleWeekPurchasePlanObject.getString("id"));
            doubleWeekPurchasePlan.setCompany_code(doubleWeekPurchasePlanObject.getString("company_code"));
            doubleWeekPurchasePlan.setDay_stock(doubleWeekPurchasePlanObject.getString("day_stock"));
            doubleWeekPurchasePlan.setExecetive(doubleWeekPurchasePlanObject.getString("execetive"));
            doubleWeekPurchasePlan.setFirst_week_require(doubleWeekPurchasePlanObject.getString("first_week_require"));
            doubleWeekPurchasePlan.setInput_time(doubleWeekPurchasePlanObject.getString("input_time"));
            doubleWeekPurchasePlan.setMaterial_name(doubleWeekPurchasePlanObject.getString("material_name"));
            doubleWeekPurchasePlan.setMaterial_number(doubleWeekPurchasePlanObject.getString("material_number"));
            doubleWeekPurchasePlan.setProvider(doubleWeekPurchasePlanObject.getString("provider"));
            doubleWeekPurchasePlan.setProvider_number(doubleWeekPurchasePlanObject.getString("provider_name"));
            doubleWeekPurchasePlan.setRelease_week(doubleWeekPurchasePlanObject.getString("release_week"));
            doubleWeekPurchasePlan.setSecond_week_require(doubleWeekPurchasePlanObject.getString("second_week_require"));
            doubleWeekPurchasePlan.setUnit(doubleWeekPurchasePlanObject.getString("unit"));

        } catch (JSONException e) {
            // TODO: handle exception
        }


        return doubleWeekPurchasePlan;
    }


    public static List<DoubleWeekPurchasePlan> getDoubleWeekPurchasePlans(String key, String jsonString){
        List<DoubleWeekPurchasePlan> list=new ArrayList<DoubleWeekPurchasePlan>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                DoubleWeekPurchasePlan doubleWeekPurchasePlan=new DoubleWeekPurchasePlan();
                doubleWeekPurchasePlan.setId(jsonObject2.getString("id"));

                // threeplanpurchase.setId(threeplanpurchaseObject.getString("id"));
                doubleWeekPurchasePlan.setCompany_code(jsonObject2.getString("company_code"));
                doubleWeekPurchasePlan.setDay_stock(jsonObject2.getString("day_stock"));
                doubleWeekPurchasePlan.setExecetive(jsonObject2.getString("execetive"));
                doubleWeekPurchasePlan.setFirst_week_require(jsonObject2.getString("first_week_require"));
                doubleWeekPurchasePlan.setInput_time(jsonObject2.getString("input_time"));
                doubleWeekPurchasePlan.setMaterial_name(jsonObject2.getString("material_name"));
                doubleWeekPurchasePlan.setMaterial_number(jsonObject2.getString("material_number"));
                doubleWeekPurchasePlan.setProvider(jsonObject2.getString("provider"));
                doubleWeekPurchasePlan.setProvider_number(jsonObject2.getString("provider_number"));
                doubleWeekPurchasePlan.setRelease_week(jsonObject2.getString("release_week"));
                doubleWeekPurchasePlan.setSecond_week_require(jsonObject2.getString("second_week_require"));

                doubleWeekPurchasePlan.setUnit(jsonObject2.getString("unit"));
                list.add(doubleWeekPurchasePlan);

            }
        } catch (JSONException e) {
            // TODO: handle exception
        }

        return list;
    }

    public static MonthPurchasePlan getMonthpurchaseplan (String key, String jsonString){

        MonthPurchasePlan monthPurchasePlan=new MonthPurchasePlan();


        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            JSONObject monthplanpurchaseObject=jsonObject.getJSONObject(key);

            monthPurchasePlan.setId(monthplanpurchaseObject.getString("id"));
            monthPurchasePlan.setCompany_code(monthplanpurchaseObject.getString("company_code"));
            monthPurchasePlan.setDay_stock(monthplanpurchaseObject.getString("day_stock"));
            monthPurchasePlan.setExecetive(monthplanpurchaseObject.getString("execetive"));
            monthPurchasePlan.setMonth_require(monthplanpurchaseObject.getString("month_require"));
            monthPurchasePlan.setInput_time(monthplanpurchaseObject.getString("input_time"));
            monthPurchasePlan.setMaterial_name(monthplanpurchaseObject.getString("material_name"));
            monthPurchasePlan.setMaterial_number(monthplanpurchaseObject.getString("material_number"));
            monthPurchasePlan.setProvider(monthplanpurchaseObject.getString("provider"));
            monthPurchasePlan.setProvider_number(monthplanpurchaseObject.getString("provider_name"));
            monthPurchasePlan.setRelease_month(monthplanpurchaseObject.getString("release_month"));
            monthPurchasePlan.setUnit(monthplanpurchaseObject.getString("unit"));

        } catch (JSONException e) {
            // TODO: handle exception
        }
        return monthPurchasePlan;
    }

    public static List<MonthPurchasePlan> getMonthpurchaseplans(String key, String jsonString){
        List<MonthPurchasePlan> list=new ArrayList<MonthPurchasePlan>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                MonthPurchasePlan monthpurchaseplan=new  MonthPurchasePlan();
                monthpurchaseplan.setId(jsonObject2.getString("id"));

                // threeplanpurchase.setId(threeplanpurchaseObject.getString("id"));
                monthpurchaseplan.setCompany_code(jsonObject2.getString("company_code"));
                monthpurchaseplan.setDay_stock(jsonObject2.getString("day_stock"));
                monthpurchaseplan.setExecetive(jsonObject2.getString("execetive"));
                monthpurchaseplan.setMonth_require(jsonObject2.getString("month_require"));
                monthpurchaseplan.setInput_time(jsonObject2.getString("input_time"));
                monthpurchaseplan.setMaterial_name(jsonObject2.getString("material_name"));
                monthpurchaseplan.setMaterial_number(jsonObject2.getString("material_number"));
                monthpurchaseplan.setProvider(jsonObject2.getString("provider"));
                monthpurchaseplan.setProvider_number(jsonObject2.getString("provider_number"));
                monthpurchaseplan.setRelease_month(jsonObject2.getString("release_month"));
                monthpurchaseplan.setUnit(jsonObject2.getString("unit"));
                list.add(monthpurchaseplan);

            }
        } catch (JSONException e) {
            // TODO: handle exception
        }

        return list;
    }

    public static TenDayPurchasePlan getTenplanpurchase (String key, String jsonString){

        TenDayPurchasePlan tenplanpurchase=new TenDayPurchasePlan();


        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            JSONObject tenplanpurchaseObject=jsonObject.getJSONObject(key);

            tenplanpurchase.setId(tenplanpurchaseObject.getString("id"));
            tenplanpurchase.setCompany_code(tenplanpurchaseObject.getString("company_code"));
            tenplanpurchase.setDay_stock(tenplanpurchaseObject.getString("day_stock"));
            tenplanpurchase.setExecetive(tenplanpurchaseObject.getString("execetive"));
            tenplanpurchase.setDecad_require(tenplanpurchaseObject.getString("decad_require"));
            tenplanpurchase.setInput_time(tenplanpurchaseObject.getString("input_time"));
            tenplanpurchase.setMaterial_name(tenplanpurchaseObject.getString("material_name"));
            tenplanpurchase.setMaterial_number(tenplanpurchaseObject.getString("material_number"));
            tenplanpurchase.setProvider(tenplanpurchaseObject.getString("provider"));
            tenplanpurchase.setProvider_number(tenplanpurchaseObject.getString("provider_name"));
            tenplanpurchase.setRelease_decad(tenplanpurchaseObject.getString("release_date"));
            tenplanpurchase.setUnit(tenplanpurchaseObject.getString("unit"));

        } catch (JSONException e) {
            // TODO: handle exception
        }


        return tenplanpurchase;
    }


    public static List<TenDayPurchasePlan> getTenplanpurchases(String key, String jsonString){
        List<TenDayPurchasePlan> list=new ArrayList<TenDayPurchasePlan>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                TenDayPurchasePlan tenplanpurchase=new TenDayPurchasePlan();
                tenplanpurchase.setId(jsonObject3.getString("id"));

                // tenplanpurchase.setId(tenplanpurchaseObject.getString("id"));
                tenplanpurchase.setCompany_code(jsonObject3.getString("company_code"));
                tenplanpurchase.setDay_stock(jsonObject3.getString("day_stock"));
                tenplanpurchase.setExecetive(jsonObject3.getString("execetive"));
                tenplanpurchase.setDecad_require(jsonObject3.getString("decad_require"));
                tenplanpurchase.setInput_time(jsonObject3.getString("input_time"));
                tenplanpurchase.setMaterial_name(jsonObject3.getString("material_name"));
                tenplanpurchase.setMaterial_number(jsonObject3.getString("material_number"));
                tenplanpurchase.setProvider(jsonObject3.getString("provider"));
                tenplanpurchase.setProvider_number(jsonObject3.getString("provider_number"));
                tenplanpurchase.setRelease_decad(jsonObject3.getString("release_decad"));
                tenplanpurchase.setUnit(jsonObject3.getString("unit"));
                list.add(tenplanpurchase);

            }
        } catch (JSONException e) {
            // TODO: handle exception
        }

        return list;
    }


    public static YearPurchasePlan yearplanpurchase (String key, String jsonString){

        YearPurchasePlan yearplanpurchase=new YearPurchasePlan();


        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            JSONObject yearplanpurchaseObject=jsonObject.getJSONObject(key);

            yearplanpurchase.setId(yearplanpurchaseObject.getString("id"));
            yearplanpurchase.setCompany_code(yearplanpurchaseObject.getString("company_code"));
            yearplanpurchase.setDay_stock(yearplanpurchaseObject.getString("day_stock"));
            yearplanpurchase.setExecetive(yearplanpurchaseObject.getString("execetive"));
            yearplanpurchase.setYear_require(yearplanpurchaseObject.getString("year_require"));
            yearplanpurchase.setInput_time(yearplanpurchaseObject.getString("input_time"));
            yearplanpurchase.setMaterial_name(yearplanpurchaseObject.getString("material_name"));
            yearplanpurchase.setMaterial_number(yearplanpurchaseObject.getString("material_number"));
            yearplanpurchase.setProvider(yearplanpurchaseObject.getString("provider"));
            yearplanpurchase.setProvider_number(yearplanpurchaseObject.getString("provider_name"));
            yearplanpurchase.setRelease_year(yearplanpurchaseObject.getString("release_year"));
            yearplanpurchase.setUnit(yearplanpurchaseObject.getString("unit"));

        } catch (JSONException e) {
            // TODO: handle exception
        }


        return yearplanpurchase;
    }


    public static List<YearPurchasePlan> getyearplanpurchases(String key, String jsonString){
        List<YearPurchasePlan> list=new ArrayList<YearPurchasePlan>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                YearPurchasePlan yearpurchase=new YearPurchasePlan();

                yearpurchase.setId(jsonObject2.getString("id"));
                yearpurchase.setCompany_code(jsonObject2.getString("company_code"));
                yearpurchase.setDay_stock(jsonObject2.getString("day_stock"));
                yearpurchase.setExecetive(jsonObject2.getString("execetive"));
                yearpurchase.setYear_require(jsonObject2.getString("year_require"));
                yearpurchase.setInput_time(jsonObject2.getString("input_time"));
                yearpurchase.setMaterial_name(jsonObject2.getString("material_name"));
                yearpurchase.setMaterial_number(jsonObject2.getString("material_number"));
                yearpurchase.setProvider(jsonObject2.getString("provider"));
                yearpurchase.setProvider_number(jsonObject2.getString("provider_number"));
                yearpurchase.setRelease_year(jsonObject2.getString("release_year"));
                yearpurchase.setUnit(jsonObject2.getString("unit"));
                list.add(yearpurchase);

            }
        } catch (JSONException e) {
            // TODO: handle exception
        }

        return list;
    }

}
