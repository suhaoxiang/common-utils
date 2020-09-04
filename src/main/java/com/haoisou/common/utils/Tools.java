package com.haoisou.common.utils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[0-9])|(15([0-3]|[5-9]))|(17[0-9])|(18[0-9])|(19[8|9]))\\d{8}$";
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static final String REGEX_CHINESE = "^[一-龥],{0,}$";
    public static final String REGEX_NUMBER = "^[0-9]*$";
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
    public static final String REGEX_URL = "http(s)://([\\w-]+\\.)+[\\w-]+(/[\\w- ./%&=]*)";
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]\\d)";
    public static final String REGEX_FD_CHN = "[一-龥]";
    public static final String REGEX_CHN = "[\\u4E00-\\u9FA5]+";
    public static final String REGEX_FD_ENG = "[a-zA-Z]";
    public static final String REGEX_ENG = "[a-zA-Z]+";
    public static final String REGEX_FD_NUM = "[0-9]";
    public static final String REGEX_NUM = "[0-9]+";

    public Tools() {
    }

    public static boolean isUsername(String username) {
        return Pattern.matches("^[a-zA-Z]\\w{5,20}$", username);
    }

    public static boolean isPassword(String password) {
        return Pattern.matches("^[a-zA-Z0-9]{6,20}$", password);
    }

    public static boolean isMobile(String mobile) {
        return Pattern.matches("^((13[0-9])|(14[0-9])|(15([0-3]|[5-9]))|(17[0-9])|(18[0-9])|(19[8|9]))\\d{8}$", mobile);
    }

    public static boolean isEmail(String email) {
        return Pattern.matches("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", email);
    }

    public static boolean isChinese(String chinese) {
        return Pattern.matches("^[一-龥],{0,}$", chinese);
    }

    public static boolean isIDCard(String idCard) {
        return Pattern.matches("(^\\d{18}$)|(^\\d{15}$)", idCard);
    }

    public static boolean isUrl(String url) {
        return Pattern.matches("http(s)://([\\w-]+\\.)+[\\w-]+(/[\\w- ./%&=]*)", url);
    }

    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]\\d)", ipAddr);
    }

    public static boolean isFindCHN(String chn) {
        return Pattern.compile("[一-龥]").matcher(chn).find();
    }

    public static boolean isCHN(String chn) {
        return Pattern.matches("[\\u4E00-\\u9FA5]+", chn);
    }

    public static boolean isFindENG(String eng) {
        return Pattern.compile("[a-zA-Z]").matcher(eng).find();
    }

    public static boolean isENG(String eng) {
        return Pattern.matches("[a-zA-Z]+", eng);
    }

    public static boolean isFindNUM(String num) {
        return Pattern.compile("[0-9]").matcher(num).find();
    }

    public static boolean isNUM(String num) {
        return Pattern.matches("[0-9]+", num);
    }

    public static boolean isHttpUrl(String httpUrl) {
        return httpUrl.contains("http:") || httpUrl.contains("https:") || httpUrl.contains("http://") || httpUrl.contains("https://");
    }

    public static int getRandomNum() {
        Random r = new Random();
        return r.nextInt(900000) + 100000;
    }

    public static boolean isEmpty(String s) {
        return s == null || "".equals(s) || s.length() == 0;
    }

    public static boolean notEmpty(String s) {
        return !isEmpty(s);
    }

    public static boolean isEmpty(Object o) {
        return o == null;
    }

    public static boolean notEmpty(Object o) {
        return !isEmpty(o);
    }

    public static boolean isEmpty(String[] s) {
        return s == null || s.length == 0;
    }

    public static boolean notEmpty(String[] s) {
        return !isEmpty(s);
    }

    public static boolean isEmpty(Integer[] i) {
        return i == null || i.length == 0;
    }

    public static boolean notEmpty(Integer[] i) {
        return !isEmpty(i);
    }

    public static boolean isEmpty(byte[] b) {
        return b == null || b.length == 0;
    }

    public static boolean notEmpty(byte[] b) {
        return !isEmpty(b);
    }

    public static boolean isEmpty(int[] i) {
        return i == null || i.length == 0;
    }

    public static boolean notEmpty(int[] i) {
        return !isEmpty(i);
    }

    public static boolean isEmpty(int i) {
        return i == -2147483648;
    }

    public static boolean notEmpty(int i) {
        return !isEmpty(i);
    }

    public static boolean isEmpty(char[] c) {
        return c == null || c.length == 0;
    }

    public static boolean notEmpty(char[] c) {
        return !isEmpty(c);
    }

    public static boolean isEmpty(float[] f) {
        return f == null || f.length == 0;
    }

    public static boolean notEmpty(float[] f) {
        return !isEmpty(f);
    }

    public static boolean isEmpty(Collection c) {
        return c == null || c.size() == 0 || c.isEmpty();
    }

    public static boolean notEmpty(Collection c) {
        return !isEmpty(c);
    }

    public static boolean isEmpty(StringBuffer sb) {
        return sb == null || sb.length() == 0;
    }

    public static boolean notEmpty(StringBuffer sb) {
        return !isEmpty(sb);
    }

    public static boolean isEmpty(Map m) {
        return m == null || m.isEmpty() || m.size() == 0;
    }

    public static boolean notEmpty(Map m) {
        return !isEmpty(m);
    }

    public static boolean isHave(String s, String i) {
        return s.indexOf(i) != -1;
    }

    public static boolean notHave(String s, String i) {
        return !isHave(s, i);
    }

    public static boolean isNum(String n) {
        return notEmpty(n) ? n.matches("^[0-9]*$") : false;
    }

    public static boolean notNum(String n) {
        return !isNum(n);
    }

    public static List<String> str2StrList(String str, String sp) {
        if (isEmpty(str)) {
            return null;
        } else {
            List<String> result = new ArrayList();
            String[] temp = str.split(sp);
            String[] var4 = temp;
            int var5 = temp.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String s = var4[var6];
                result.add(s);
            }

            return result;
        }
    }

    public static List<String> str2StrList(String str) {
        return str2StrList(str, ",");
    }

    public static String strList2Str(List<String> strList, String sp) {
        if (isEmpty((Collection)strList)) {
            return "";
        } else {
            String result = "";

            for(int i = 0; i < strList.size(); ++i) {
                if (i < strList.size() - 1) {
                    result = result + (String)strList.get(i) + sp;
                } else {
                    result = result + (String)strList.get(i);
                }
            }

            return result;
        }
    }

    public static String strList2Str(List<String> strList) {
        return strList2Str(strList, ",");
    }

    public static String[] str2StrArray(String str, String splitRegex) {
        return isEmpty(str) ? null : str.split(splitRegex);
    }

    public static String[] str2StrArray(String str) {
        return str2StrArray(str, ",");
    }

    public static String strArray2Str(String[] strArray, String splitRegex) {
        if (isEmpty(strArray)) {
            return "";
        } else {
            String result = "";

            for(int i = 0; i < strArray.length; ++i) {
                if (i < strArray.length - 1) {
                    result = result + strArray[i] + splitRegex;
                } else {
                    result = result + strArray[i];
                }
            }

            return result;
        }
    }

    public static String strArray2Str(String[] strArray) {
        return strArray2Str(strArray, ",");
    }

    public static Timestamp date2Tmp(Date date) {
        return Timestamp.valueOf(date2Str(date));
    }

    public static String date2Str(Date date) {
        return date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date str2Date(String date) {
        if (notEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                return sdf.parse(date);
            } catch (ParseException var3) {
                var3.printStackTrace();
                return new Date();
            }
        } else {
            return null;
        }
    }

    public static String date2Str(Date date, String format) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } else {
            return "";
        }
    }

    public static Timestamp getTimestamp() {
        return date2Tmp(new Date());
    }

    public static String getTimes(String StrDate) {
        String resultTimes = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date now = new Date();
            Date date = df.parse(StrDate);
            long times = now.getTime() - date.getTime();
            long day = times / 86400000L;
            long hour = times / 3600000L - day * 24L;
            long min = times / 60000L - day * 24L * 60L - hour * 60L;
            long sec = times / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
            StringBuffer sb = new StringBuffer();
            if (hour > 0L) {
                sb.append(hour + "小时前");
            } else if (min > 0L) {
                sb.append(min + "分钟前");
            } else {
                sb.append(sec + "秒前");
            }

            resultTimes = sb.toString();
        } catch (ParseException var16) {
            var16.printStackTrace();
        }

        return resultTimes;
    }

    public static boolean checkEmail(String email) {
        boolean flag = false;

        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception var5) {
            flag = false;
        }

        return flag;
    }

    public static boolean checkPhone(String phone) {
        boolean flag = false;

        try {
            String check = "^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(phone);
            flag = matcher.matches();
        } catch (Exception var5) {
            flag = false;
        }

        return flag;
    }

    public static boolean checkKey(String paraname, String FKEY) {
        paraname = null == paraname ? "" : paraname;
        return MD5.md5(paraname + DateUtil.getDays() + ",SP,").equals(FKEY);
    }

    public static String key2Path(String routeKey) {
        String path = routeKey;
        if (notEmpty(routeKey)) {
            if (!routeKey.startsWith("/")) {
                path = "/" + routeKey;
            }

            if (!routeKey.endsWith("/**") && !routeKey.endsWith("/*")) {
                if (!routeKey.endsWith("/")) {
                    if (routeKey.endsWith("*")) {
                        path = path.replace("*", "") + "/**";
                    } else {
                        path = path + "/**";
                    }
                } else {
                    path = path + "**";
                }
            } else if (routeKey.endsWith("/*")) {
                path = path + "*";
            }
        }

        return path;
    }

    public static String getPorjectPath() {
        return System.getProperty("user.dir") + "/";
    }

    public static boolean isHexStr(String s) {
        return s.matches("^[A-Fa-f0-9]+$");
    }

    public static String getContextPath(HttpServletRequest request) {
        String contextPath = request.getHeader("x-forwarded-prefix");
        return notEmpty(contextPath) ? contextPath : request.getContextPath();
    }

    public static String getServerPath(HttpServletRequest request) {
        String proto = request.getHeader("x-forwarded-proto");
        String host = request.getHeader("x-forwarded-host");
        String path = request.getHeader("x-forwarded-prefix");
        proto = notEmpty(proto) ? proto : request.getScheme();
        host = notEmpty(host) ? host : request.getHeader("host");
        host = notEmpty(host) ? host : request.getServerName() + ":" + request.getServerPort();
        path = notEmpty(path) ? path : request.getContextPath();
        return proto + "://" + host + path;
    }

    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }

        return isWindowsOS;
    }

    public static String getServerIp() {
        String ip = "localhost";
        if (isWindowsOS()) {
            try {
                InetAddress inet = InetAddress.getLocalHost();
                ip = inet.getHostAddress();
            } catch (UnknownHostException var2) {
                var2.printStackTrace();
            }

            return ip;
        } else {
            return getLinuxServerIP();
        }
    }

    private static String getLinuxServerIP() {
        String ip = "localhost";

        try {
            Enumeration en = NetworkInterface.getNetworkInterfaces();

            while(true) {
                NetworkInterface intf;
                String name;
                do {
                    do {
                        if (!en.hasMoreElements()) {
                            return ip;
                        }

                        intf = (NetworkInterface)en.nextElement();
                        name = intf.getName();
                    } while(name.contains("docker"));
                } while(name.contains("lo"));

                Enumeration enumIpAddr = intf.getInetAddresses();

                while(enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress)enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ipaddress = inetAddress.getHostAddress().toString();
                        if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
                            ip = ipaddress;
                        }
                    }
                }
            }
        } catch (SocketException var7) {
            var7.printStackTrace();
            return ip;
        }
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (notEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip.contains(",") ? ip.split(",")[0] : ip;
        } else {
            ip = request.getHeader("X-Real-IP");
            if (notEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            } else {
                if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }

                return ip;
            }
        }
    }

    public static String getMD5(InputStream is) throws NoSuchAlgorithmException, IOException {
        StringBuffer md5 = new StringBuffer();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] dataBytes = new byte[1024];
        boolean var4 = false;

        int nread;
        while((nread = is.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }

        byte[] mdbytes = md.digest();

        for(int i = 0; i < mdbytes.length; ++i) {
            md5.append(Integer.toString((mdbytes[i] & 255) + 256, 16).substring(1));
        }

        if (notEmpty((Object)is)) {
            is.close();
        }

        return md5.toString();
    }

    public static String findExceptionMessage(Throwable cause) {
        return notEmpty(cause.getMessage()) ? cause.getMessage() : findExceptionMessage(cause.getCause());
    }

    public static String findExceptionString(Throwable cause) {
        return notEmpty(cause.toString()) ? cause.toString() : findExceptionString(cause.getCause());
    }

    public static String parseRegPath(String path) {
        try {
            char[] chars = path.toCharArray();
            int len = chars.length;
            String reg = "";
            boolean preX = false;

            for(int i = 0; i < len; ++i) {
                if (chars[i] == '*') {
                    if (preX) {
                        reg = reg + ".*";
                        preX = false;
                    } else if (i + 1 == len) {
                        reg = reg + "[^/]*";
                    } else {
                        preX = true;
                    }
                } else {
                    if (preX) {
                        reg = reg + "[^/]*";
                        preX = false;
                    }

                    if (chars[i] == '?') {
                        reg = reg + '.';
                    } else {
                        reg = reg + chars[i];
                    }
                }
            }

            return reg;
        } catch (Exception var6) {
            var6.printStackTrace();
            return path;
        }
    }

    public static void clearMap(Map... maps) {
        if (notEmpty((Object)maps) && maps.length > 0) {
            Map[] var1 = maps;
            int var2 = maps.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Map map = var1[var3];
                if (notEmpty(map)) {
                    map.clear();
                    map = null;
                }
            }
        }

    }

    public static void clearList(List... lists) {
        if (notEmpty((Object)lists) && lists.length > 0) {
            List[] var1 = lists;
            int var2 = lists.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                List list = var1[var3];
                if (notEmpty((Collection)list)) {
                    list.clear();
                    list = null;
                }
            }
        }

    }

    public static void clearCollection(Collection... collections) {
        if (notEmpty((Object)collections) && collections.length > 0) {
            Collection[] var1 = collections;
            int var2 = collections.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Collection collection = var1[var3];
                if (notEmpty(collection)) {
                    collection.clear();
                    collection = null;
                }
            }
        }

    }

    public static String drawVerifyImg(ByteArrayOutputStream output) throws Exception {
        String code = "";

        for(int i = 0; i < 4; ++i) {
            code = code + randomChar();
        }

        int width = 70;
        int height = 25;
        BufferedImage bi = new BufferedImage(width, height, 5);
        Font font = new Font("Times New Roman", 0, 20);
        Graphics2D g = bi.createGraphics();
        g.setFont(font);
        Color color = new Color(66, 2, 82);
        g.setColor(color);
        g.setBackground(new Color(226, 226, 240));
        g.clearRect(0, 0, width, height);
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(code, context);
        double x = ((double)width - bounds.getWidth()) / 2.0D;
        double y = ((double)height - bounds.getHeight()) / 2.0D;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code, (int)x, (int)baseY);
        g.dispose();
        ImageIO.write(bi, "jpg", output);
        return code;
    }

    public static String getVerifyCode() {
        String code = "";

        for(int i = 0; i < 4; ++i) {
            code = code + randomChar();
        }

        return code;
    }

    public static char randomChar() {
        Random r = new Random();
        String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
        return s.charAt(r.nextInt(s.length()));
    }

    public static void main(String[] args) {
        System.out.println(strList2Str(str2StrList("1,2,3,4", ","), ","));
        System.out.println(getRandomNum());
    }
}
