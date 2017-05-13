package Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import Object.*;

public class JsonTool {

    public static List<MaterialScore> getMaterialScore(String keyFirst,String jsonString){
        List<MaterialScore> list=new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(keyFirst);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                MaterialScore materialScore=new MaterialScore();
                materialScore.setHost(object.getString("host"));
                materialScore.setHost_number(object.getString("host_number"));
                materialScore.setOrder_number(object.getString("order_number"));
                materialScore.setMaterial_number(object.getString("material_number"));
                materialScore.setPurchase_number(object.getString("purchase_number"));
                materialScore.setConfirm_score(object.getString("confirm_score"));
                materialScore.setDelivery_score(object.getString("delivery_score"));
                materialScore.setReceive_score(object.getString("receive_score"));
                materialScore.setTotal_score(object.getString("total_score"));
                materialScore.setScore_time(object.getString("score_time"));
                list.add(materialScore);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<ProviderScore> getProviderScore(String keyFirst,String jsonString){
        List<ProviderScore> list=new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(keyFirst);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                ProviderScore providerScore=new ProviderScore();
                providerScore.setHost(object.getString("host"));
                providerScore.setHost_number(object.getString("host_number"));
                providerScore.setPurchase_number(object.getString("purchase_number"));
                providerScore.setScore(object.getString("score"));
                providerScore.setScore_time(object.getString("score_time"));
                list.add(providerScore);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<ProviderHandScore> getProviderHandScore(String keyFirst, String jsonString){
        List<ProviderHandScore> list=new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(keyFirst);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                ProviderHandScore providerHandScore=new ProviderHandScore();
                providerHandScore.setHost(object.getString("host"));
                providerHandScore.setHost_number(object.getString("host_number"));
                providerHandScore.setAccess_start_time(object.getString("access_start_time"));
                providerHandScore.setAccess_end_time(object.getString("access_end_time"));
                providerHandScore.setScore_time(object.getString("score_time"));
                providerHandScore.setAccess_charger(object.getString("access_charger"));
                providerHandScore.setPurchase_score(object.getString("purchase_score"));
                providerHandScore.setPro_manage_score(object.getString("pro_manage_score"));
                providerHandScore.setService_score(object.getString("service_score"));
                providerHandScore.setQuality_score(object.getString("quality_score"));
                providerHandScore.setTotal_score(object.getString("total_score"));
                list.add(providerHandScore);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
