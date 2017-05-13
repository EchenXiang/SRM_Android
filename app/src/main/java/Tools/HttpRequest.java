package Tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;


public class HttpRequest {
    public static String sendGetRequest(String url, Map<String, String> params, String encode) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(url);
        stringBuilder.append('?');
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                stringBuilder.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), encode)).append('&');
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        HttpURLConnection conn=null;
        InputStream in=null;
        try {
            URL actualURL = new URL(stringBuilder.toString());
            conn = (HttpURLConnection) actualURL.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5*1000);
            conn.setDoInput(true);
            conn.connect();
            if(conn.getResponseCode()==200){
                in= conn.getInputStream();
                return ChangeToString(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    public static String ChangeToString(InputStream in){
        String jsonString=in.toString();
        ByteArrayOutputStream outPutStream=new ByteArrayOutputStream();
        byte[] data=new byte[1024];
        int length=0;
        try {
            while((length=in.read(data))!=-1){
                outPutStream.write(data, 0, length);
            }
            jsonString=new String(outPutStream.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonString;
    }

}
