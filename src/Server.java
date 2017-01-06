import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by 史书恒 on 2016/10/31.
 */
public class Server {
//    public static String HOST = "http://localhost";//本地服务器
    public static String HOST = "http://www.whathell.top";//网络服务器


    public static String getString(String webpath) {//获取服务器返回数据（JSON形式）
        try {
            URL url = new URL(webpath);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setReadTimeout(20000);
            connection.connect();
            InputStream is = connection.getInputStream();
            return inputStreamToString(is);
        }catch (Exception e) {
            return "failed";
        }
    }

    /*InputStream类转String类*/
    public static String inputStreamToString (InputStream is) {
        StringBuffer sb = new StringBuffer();
        String result = "";
        byte[] buffer = new byte[1024];
        try {
            for(int i = 0; (i = is.read(buffer)) != -1; i++) {
                sb.append(new String(buffer, 0, i));
            }
            result = new String(sb.toString().getBytes(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
