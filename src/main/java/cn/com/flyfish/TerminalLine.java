package cn.com.flyfish;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 检测终端之间通讯状态
 *
 * @author chenmy
 */
public class TerminalLine {
    /**
     * 调用API
     * @param ip
     * @param timeout
     * @return
     */
    public static boolean isAvaliable(String ip, int timeout) {
        try {
            InetAddress address = InetAddress.getByName(ip);// ping this IP
            if (address.isReachable(timeout)) {// 适合防火墙关闭的终端
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 调用ping命令包
     * @param ip
     * @param timeout
     * @return
     */
    public static boolean ping(String ip, long timeout,boolean isBrowser,String ad) {
        String line = null;
        boolean isok = false;
        try {

            Process pro = Runtime.getRuntime().exec("ping " + ip.trim());

            BufferedReader buf = new BufferedReader(new InputStreamReader(pro
                    .getInputStream()));
            while ((line = buf.readLine()) != null) {
                if (line.indexOf("TTL") > 0) {
                    openBrowser(isBrowser,ad); //打开浏览器
                    isok = true;
                    break;
                }
                Thread.sleep(timeout);
                isok = false;
            }
        } catch (Exception e) {
            return isok;
        }
        return isok;
    }
    /**
     * 打开指定的网址
     * @param isDefaultBrowser
     * @param address
     */
    public static void openBrowser (boolean isDefaultBrowser,String address) {
        if(isDefaultBrowser){
            try {
                URI uri = new URI(address);
                Desktop.getDesktop().browse(uri);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            String str = "cmd /c start iexplore "+address;
            try {
                Runtime.getRuntime().exec(str);

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}
