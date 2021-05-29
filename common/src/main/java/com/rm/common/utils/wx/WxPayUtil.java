package com.rm.common.utils.wx;

import com.rm.common.utils.DateUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.InetAddress;
import java.util.*;

/**
 * Created by yucb in 11:32 2018/2/27.
 */
public class WxPayUtil {

    public static char[] CHAR_CONST = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static String getNonceStr() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 32; i++) {
            buffer.append(CHAR_CONST[random.nextInt(36)]);
        }
        return buffer.toString();
    }

    public static String mapToKeyValue(Map<String, Object> map) {
        if (map == null || map.size() == 0) {
            return "";
        }
        List<String> keyList = new ArrayList<>(map.keySet());
        Collections.sort(keyList);

        StringBuffer buffer = new StringBuffer();
        for (String key : keyList) {
            Object value = map.get(key);
            if (value != null) {
                buffer.append(key).append("=").append(value).append("&");
            }
        }
        String str = buffer.toString();
        if (str.endsWith("&")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static String sign(String paramsStr, String key) {
        paramsStr = paramsStr + "&key=" + key;
        return DigestUtils.md5Hex(paramsStr).toUpperCase();
    }

    public static String mapToXml(Map<String, Object> map) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<xml>\n");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            buffer.append("<").append(entry.getKey()).append(">").append(entry.getValue()).append("</").append(entry.getKey()).append(">\n");
        }
        buffer.append("</xml>");
        return buffer.toString();
    }

    public static String getIp() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证签名
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, Object> data, String key) throws Exception {
        if (!data.containsKey("sign")) {
            return false;
        }
        String sign = (String) data.get("sign");
        data.remove("sign");
        boolean isSucc = sign(mapToKeyValue(data), key).equals(sign);
        data.put("sign", sign);
        return isSucc;
    }

    /**
     * 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public static String getOutTradeNo() {
        String key = DateUtils.currentTime(DateUtils.PATTERN_yyyyMMddHHmmss);
        Random r = new Random();
        while (key.length() < 18) {
            key = key + r.nextInt(10);
        }
        return key;
    }

}
