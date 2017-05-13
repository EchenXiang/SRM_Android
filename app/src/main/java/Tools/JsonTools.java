package Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Object.*;

/**
 * Created by luo on 2016/7/4.
 * 除了我注释的2个函数用到了之外 其他函数没有被调用 就没注释了
 */
public class JsonTools {
    /**此函数是读取Json字符串返回一个Stock实例
     * @param key
     * @param jsonString
     * @return
     */
    public static String getStaff_role(String key, String jsonString) {
       String staff_role ="" ;

        try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(key); //进入key对应的数据组
                for (int i = 0; i < jsonArray.length(); i++) {       //循环赋值 并装入list
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    staff_role = jsonObject2.getString("staff_role");
                }




        } catch (Exception e) {
            // TODO: handle exception
        }
        return staff_role;
    }

    public static String getuserna(String key, String jsonString) {
        String userna ="" ;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key); //进入key对应的数据组
            for (int i = 0; i < jsonArray.length(); i++) {       //循环赋值 并装入list
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                userna = jsonObject2.getString("userna");
            }



        } catch (Exception e) {
            // TODO: handle exception
        }
        return userna;
    }

    public static String getneoid( String jsonString) {
        String neoid ="" ;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            neoid = jsonObject.getString("neoid");

        } catch (Exception e) {
            // TODO: handle exception
        }
        return neoid;
    }
    public static List<PersonalInfo> getPersonalInfo(String key, String jsonString) {
        List<PersonalInfo> list = new ArrayList<PersonalInfo>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key); //进入key对应的数据组
            for (int i = 0; i < jsonArray.length(); i++) {       //循环赋值 并装入list
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                PersonalInfo personalInfo = new PersonalInfo();
                personalInfo.setid(jsonObject2.getString("id"));
                personalInfo.setrolename(jsonObject2.getString("rolename"));

                list.add(personalInfo);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    public static String getenterpriseId(String key, String jsonString) {
        String enterpriseId ="" ;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonObject2= jsonObject.getJSONObject(key);
                enterpriseId = jsonObject2.getString("id");



        } catch (Exception e) {
            // TODO: handle exception
        }
        return enterpriseId;
    }

    public static String getenterpriseName(String key, String jsonString) {
        String enterpriseName ="" ;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonObject2= jsonObject.getJSONObject(key); //进入key对应的数据组

                enterpriseName = jsonObject2.getString("mc");



        } catch (Exception e) {
            // TODO: handle exception
        }
        return enterpriseName;
    }

    public static String getmessage( String jsonString) {
        String message ="" ;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            message = jsonObject.getString("message");

        } catch (Exception e) {
            // TODO: handle exception
        }
        return message;
    }

    public static String getenterpriseMC(String key1, String key2, String jsonString) {

        String enterpriseMC = "";
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key1);
            /*JSONArray jsonArray1 = jsonArray.getJSONArray(0);*/
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);

               enterpriseMC = jsonObject2.getJSONObject(key2).getString("mc");



            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return enterpriseMC;
    }

    public static EmployeeInfo getEmployeeInfo(String key, String jsonString) {
        EmployeeInfo employeeInfo = new EmployeeInfo();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
                //循环赋值 并装入list
                JSONObject jsonObject2 = jsonObject.getJSONObject(key);

                employeeInfo.setcreateTime(jsonObject2.getString("createTime"));
                employeeInfo.setphone(jsonObject2.getString("phone"));
                employeeInfo.setaddress(jsonObject2.getString("address"));
                employeeInfo.setstatus(jsonObject2.getString("status"));
                employeeInfo.setemail(jsonObject2.getString("email"));
                employeeInfo.setname(jsonObject2.getString("name"));
                employeeInfo.setlastModifyTime(jsonObject2.getString("lastModifyTime"));



        } catch (Exception e) {
            // TODO: handle exception
        }
        return employeeInfo;
    }


    /**在得到Json格式的字符串数据之后，我们需要解析Json格式，并且用实体类（Stock）组来存放得到的数据
     * @param key          key值为Json格式的一级Key值，{total:""，data:["......."]}key为data时 就会访问data中的数据
     * @param jsonString   上一个函数取得的Json字符串
     * @return              返回值是一个Stock组
     */
    public static List<Stock> getStocks(String key, String jsonString) {
        List<Stock> list = new ArrayList<Stock>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key); //进入key对应的数据组
            for (int i = 0; i < jsonArray.length(); i++) {       //循环赋值 并装入list
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Stock stock = new Stock();
                stock.setId(jsonObject2.getString("id"));
                stock.sethost(jsonObject2.getString("host"));
                stock.sethost_number(jsonObject2.getString("host_number"));
                stock.setprovider(jsonObject2.getString("provider"));
                stock.setprovider_number(jsonObject2.getString("provider_number"));
                stock.setwarehouse(jsonObject2.getString("warehouse"));
                stock.setamount(jsonObject2.getString("amount"));
                stock.setprice(jsonObject2.getString("price"));
                stock.setunit(jsonObject2.getString("unit"));
                String s = jsonObject2.getString("specifications");
                stock.setspecification(jsonObject2.getString("specifications"));
                stock.setstorage_time(jsonObject2.getString("storage_time"));
                stock.setcharge_number(jsonObject2.getString("charge_number"));
                stock.setremarks(jsonObject2.getString("remarks"));
                stock.setmaterial_number(jsonObject2.getString("material_number"));
                stock.setmaterial_name(jsonObject2.getString("material_name"));
                list.add(stock);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    /**
     * Created by luo on 2016/7/4.
     * 负责对供应商订单查询接口返回的Json数据进行解析
     * 订单的返回数据格式有些不对 多了个二级KEY 因此需要根据实际路径进行相应调整
     * @param key1          一级KEY
     * @param key2          二级KEY
     * @param jsonString    传入的Json字符串
     * @return              返回OrderSelect类型的list实例
     */
    public static List<OrderSelect> getOrders(String key1, String key2, String jsonString) {
        List<OrderSelect> list = new ArrayList<OrderSelect>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key1);
            /*JSONArray jsonArray1 = jsonArray.getJSONArray(0);*/
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                OrderSelect orderSelect = new OrderSelect();
                orderSelect.setcompany_code(jsonObject2.getJSONObject(key2).getString("company_code"));
                orderSelect.setorder_id(jsonObject2.getJSONObject(key2).getString("order_id"));
                orderSelect.setpurchase_contact_name(jsonObject2.getJSONObject(key2).getString("purchase_contact_name"));
                orderSelect.setcontact(jsonObject2.getJSONObject(key2).getString("contact"));
                orderSelect.setorder_generate_day(jsonObject2.getJSONObject(key2).getString("order_generate_day"));
                orderSelect.setprovider_confirm(jsonObject2.getJSONObject(key2).getString("provider_confirm"));
                orderSelect.setorder_status(jsonObject2.getJSONObject(key2).getString("order_status"));
                orderSelect.setdelivery_status(jsonObject2.getJSONObject(key2).getString("delivery_status"));
                orderSelect.setprint_number(jsonObject2.getJSONObject(key2).getString("print_number"));

                list.add(orderSelect);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }


    /**对供应商订单追踪接口返回的Json进行解析
     * @param key
     * @param jsonString
     * @return          返回OrderTrack的列表
     */
    public static List<OrderTrack> getOrderTrack(String key, String jsonString) {
        List<OrderTrack> list = new ArrayList<OrderTrack>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key); //进入key对应的数据组
            for (int i = 0; i < jsonArray.length(); i++) {       //循环赋值 并装入list
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                OrderTrack orderTrack = new OrderTrack();
                orderTrack.setorder_status(jsonObject2.getString("order_status"));
                orderTrack.setcompany_code(jsonObject2.getString("company_code"));
                orderTrack.setprovider_name(jsonObject2.getString("provider_name"));
                orderTrack.setorder_id(jsonObject2.getString("order_id"));
                orderTrack.setmaterial_number(jsonObject2.getString("material_number"));
                orderTrack.setmaterial_name(jsonObject2.getString("material_name"));
                orderTrack.setorder_generate_day(jsonObject2.getString("order_generate_day"));
                orderTrack.setspecified_delivery(jsonObject2.getString("specified_delivery"));
                orderTrack.setorder_unit(jsonObject2.getString("order_unit"));

                orderTrack.setnumber(jsonObject2.getString("number"));
                orderTrack.sethad_ship_number(jsonObject2.getString("had_ship_number"));
                orderTrack.setdmark(jsonObject2.getString("dmark"));
                orderTrack.setdelivery_complete(jsonObject2.getString("delivery_complete"));

                list.add(orderTrack);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    /**对供应商订单统计接口返回的Json进行解析
     * @param key
     * @param jsonString
     * @return          返回OrderStatistic的列表
     */
    public static List<OrderStatistic> getOrderStatistic(String key, String jsonString) {
        List<OrderStatistic> list = new ArrayList<OrderStatistic>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key); //进入key对应的数据组
            for (int i = 0; i < jsonArray.length(); i++) {       //循环赋值 并装入list
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                OrderStatistic orderStatistic = new OrderStatistic();
                orderStatistic.setcompany_code(jsonObject2.getString("company_code"));
                orderStatistic.setorder_id(jsonObject2.getString("order_id"));
                orderStatistic.setorder_generate_day(jsonObject2.getString("order_generate_day"));
                orderStatistic.setprovider_confirm(jsonObject2.getString("provider_confirm"));
                orderStatistic.setorder_status(jsonObject2.getString("order_status"));
                orderStatistic.setdelivery_status(jsonObject2.getString("delivery_status"));


                list.add(orderStatistic);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    /**对供应商订单再计划接口返回的Json进行解析
     * @param key
     * @param jsonString
     * @return          返回OrderFeedback的列表
     */
    public static List<OrderFeedback> getOrderFeedback(String key, String jsonString) {
        List<OrderFeedback> list = new ArrayList<OrderFeedback>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key); //进入key对应的数据组
            for (int i = 0; i < jsonArray.length(); i++) {       //循环赋值 并装入list
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                OrderFeedback orderFeedback = new OrderFeedback();
                orderFeedback.setcompany_code(jsonObject2.getString("company_code"));
                orderFeedback.setorder_id(jsonObject2.getString("order_id"));
                orderFeedback.setmaterial_number(jsonObject2.getString("material_number"));
                orderFeedback.setmaterial_name(jsonObject2.getString("material_name"));
                orderFeedback.setorder_unit(jsonObject2.getString("order_unit"));
                orderFeedback.setnumber(jsonObject2.getString("number"));
                orderFeedback.setplan_supply_number(jsonObject2.getString("plan_supply_number"));
                orderFeedback.setplan_ship_day(jsonObject2.getString("plan_ship_day"));
                orderFeedback.setplan_delivery_day(jsonObject2.getString("plan_delivery_day"));

                orderFeedback.setorder_generate_day(jsonObject2.getString("order_generate_day"));
                orderFeedback.setspecified_delivery(jsonObject2.getString("specified_delivery"));


                list.add(orderFeedback);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    public static int getTotal(String key, String jsonString) {
        int max =0;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);


                max = jsonObject.getInt(key);

        } catch (Exception e) {
            // TODO: handle exception
        }
        return max;
    }

    public static List<String> getlistString(String key, String jsonString) {
        List<String> list = new ArrayList<String>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {

                String msg = jsonArray.getString(i);
                list.add(msg);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        return list;
    }

    public static List<Map<String,Object>> getlistMap(String key, String jsonString){
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Map<String,Object> map=new HashMap<String, Object>();
                Iterator<String> iterator=jsonObject2.keys();

                while(iterator.hasNext()){
                    String json_key=iterator.next();
                    Object json_value=jsonObject2.get(json_key);
                    if(json_value==null){
                        json_value="";
                    }
                    map.put(json_key, json_value);
                }
                list.add(map);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }


    public static Notice getNotice(String key, String jsonString) {
        Notice notice = new Notice();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject noticeObject = jsonObject.getJSONObject(key);

            notice.setid(noticeObject.getString("id"));
            notice.setreceiver_ent(noticeObject.getString("receiver_ent"));
            notice.setsender_ent(noticeObject.getString("sender_ent"));
            notice.setdate(noticeObject.getString("date"));
            notice.setcontent(noticeObject.getString("content"));
            notice.settitle(noticeObject.getString("title"));
            notice.setstatus(noticeObject.getString("status"));
            notice.setrole(noticeObject.getString("role"));

        } catch (Exception e) {
            // TODO: handle exception
        }
        return notice;
    }
    /**在得到Json格式的字符串数据之后，我们需要解析Json格式，并且用实体类（Notice）组来存放得到的数据
     * @param key          key值为Json格式的一级Key值，{total:""，data:["......."]}key为data时 就会访问data中的数据
     * @param jsonString   上一个函数取得的Json字符串
     * @return              返回值是一个Notice组
     */
    public static List<Notice> getNotices(String key, String jsonString) {
        List<Notice> list = new ArrayList<Notice>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key); //进入key对应的数据组
            for (int i = 0; i < jsonArray.length(); i++) {       //循环赋值 并装入list
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Notice notice = new Notice();
                notice.setid(jsonObject2.getString("id"));
                notice.setreceiver_ent(jsonObject2.getString("receiver_ent"));
                notice.setsender_ent(jsonObject2.getString("sender_ent"));
                notice.setdate(jsonObject2.getString("date"));
                notice.setcontent(jsonObject2.getString("content"));
                notice.settitle(jsonObject2.getString("title"));
                notice.setstatus(jsonObject2.getString("status"));
                notice.setrole(jsonObject2.getString("role"));

                list.add(notice);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    /**在得到Json格式的字符串数据之后，我们需要解析Json格式，并且用实体类（LogisticsInfo）组来存放得到的数据
     * @param key          key值为Json格式的一级Key值，{total:""，data:["......."]}key为data时 就会访问data中的数据
     * @param jsonString   上一个函数取得的Json字符串
     * @return              返回值是一个LogisticsInfo组
     */
    public static List<DeliveryInfo> getDeliveryInfo(String key, String jsonString) {
        List<DeliveryInfo> list = new ArrayList<DeliveryInfo>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key); //进入key对应的数据组
            for (int i = 0; i < jsonArray.length(); i++) {       //循环赋值 并装入list
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                DeliveryInfo deliveryInfo = new DeliveryInfo();

                deliveryInfo.setDate(jsonObject2.getString("sgoods_sdate"));
                deliveryInfo.setEntname(jsonObject2.getString("sgoods_entname"));
                deliveryInfo.setLogistics_number(jsonObject2.getString("sgoods_lognumber"));
                deliveryInfo.setNumber(jsonObject2.getString("sgoods_snumber"));
                deliveryInfo.setPlan_date(jsonObject2.getString("sgoods_rdate"));
                deliveryInfo.setRe_addr(jsonObject2.getString("sgoods_raddress"));
                deliveryInfo.setReceiver(jsonObject2.getString("sgoods_receiver"));
                deliveryInfo.setSender(jsonObject2.getString("sgoods_sender"));
                deliveryInfo.setStatus(jsonObject2.getString("sgoods_status"));
                deliveryInfo.setRe_tele(jsonObject2.getString("sgoods_rphone"));



                list.add(deliveryInfo);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }


    public static List<LogisticsInfo> getLogisticsInfo(String key, String jsonString) {
        List<LogisticsInfo> list = new ArrayList<LogisticsInfo>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key); //进入key对应的数据组
            for (int i = 0; i < jsonArray.length(); i++) {       //循环赋值 并装入list
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                LogisticsInfo logisticsInfo = new LogisticsInfo();
                logisticsInfo.setLogistics_date(jsonObject2.getString("logistics_date"));
                logisticsInfo.setLogistics_entname(jsonObject2.getString("logistics_entname"));
                logisticsInfo.setLogistics_number(jsonObject2.getString("logistics_lognumber"));
                logisticsInfo.setLogistics_company(jsonObject2.getString("logistics_supplyname"));
                logisticsInfo.setLogistics_status(jsonObject2.getString("logistics_status"));
                logisticsInfo.setLogistics_plan_date(jsonObject2.getString("logistics_rdate"));
                logisticsInfo.setLogistics_company_tele(jsonObject2.getString("logistics_rphone"));
                list.add(logisticsInfo);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }
}
