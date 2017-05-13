package Tools;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/12 0012.
 */
public class Notice_HttpRequest {
    public static boolean sendXML(String path, String xml)throws Exception{
        byte[] data = xml.getBytes();
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(5 * 1000);
        conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
        conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        OutputStream outStream = conn.getOutputStream();
        outStream.write(data);
        outStream.flush();
        outStream.close();
        if(conn.getResponseCode()==200){
            return true;
        }
        return false;
    }
    public static String sendGetRequest(String path, Map<String, String> params, String enc) throws IOException {
        /**
         * path传进来 先需要和参数 拼起来 下面的操作就是为了生成附带参数的URL  例如：
         * http:211.87.227.120/order/supplierOrderQuery?company_code=&company_name=丹阳市中远车灯有限公司&delivery_status=
         */
        StringBuilder sb = new StringBuilder(path);
        sb.append('?');
        if(params!=null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), enc)).append('&');
            }
            sb.deleteCharAt(sb.length() - 1);

        }


        try {
            URL url = new URL(sb.toString());
            HttpURLConnection conn = (HttpURLConnection)url.openConnection(); //打开连接
            conn.setRequestMethod("GET");    //设置方法为GET
            conn.setReadTimeout(5 * 1000);//设置过期时间为5秒
            conn.setDoInput(true);
            conn.connect();
            if(conn.getResponseCode() == 200) {  //如果成功返回
                InputStream in = conn.getInputStream();

                return changeInputString(in);      //调用changeInputString（）函数把输出转换成json 字符串
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();

            Log.e("Notice_HttpRequest", e.toString());
            return "";
        }
        return "";
    }

    public static boolean sendPostRequest(String path, Map<String, String> params, String enc) throws Exception{
        // title=dsfdsf&timelength=23&method=save
        StringBuilder sb = new StringBuilder();
        if(params!=null && !params.isEmpty()){
            for(Map.Entry<String, String> entry : params.entrySet()){
                sb.append(entry.getKey()).append('=')
                        .append(URLEncoder.encode(entry.getValue(), enc)).append('&');
            }
            sb.deleteCharAt(sb.length()-1);
        }
        byte[] entitydata = sb.toString().getBytes();//得到实体的二进制数据
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(5 * 1000);
        conn.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
        //Content-Type: application/x-www-form-urlencoded
        //Content-Length: 38
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));
        OutputStream outStream = conn.getOutputStream();
        outStream.write(entitydata);
        outStream.flush();
        outStream.close();
        if(conn.getResponseCode()==200){
            return true;
        }
        return false;
    }

    public static boolean sendRequestFromHttpClient(String path, Map<String, String> params, String enc) throws Exception{
        List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
        if(params!=null && !params.isEmpty()){
            for(Map.Entry<String, String> entry : params.entrySet()){
                paramPairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        UrlEncodedFormEntity entitydata = new UrlEncodedFormEntity(paramPairs, enc);//得到经过编码过后的实体数据
        HttpPost post = new HttpPost(path); //form
        post.setEntity(entitydata);
        DefaultHttpClient client = new DefaultHttpClient(); //浏览器
        HttpResponse response = client.execute(post);//执行请求
        if(response.getStatusLine().getStatusCode()==200){
            return true;
        }
        return false;
    }

    /**该函数作用就是把输出流数据转换成Json string
     * @param inputStream
     * @return
     */
    private static String changeInputString(InputStream inputStream) {

        String jsonString="";
        ByteArrayOutputStream outPutStream=new ByteArrayOutputStream();
        byte[] data=new byte[1024];
        int len=0;
        try {
            while((len=inputStream.read(data))!=-1){
                outPutStream.write(data, 0, len);
            }
            jsonString=new String(outPutStream.toByteArray());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonString;
    }
}
