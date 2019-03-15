package com.dpzx.online.baselib.base;

import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;


/**
 * Created by dingdj on 2016/5/13.
 */
public class HttpRequestParam {

    public static String DivideVersion;
    public static String SupPhone;
    public static String SupFirm;
    public static String IMEI;
    public static String IMSI;
    public static String CUID;
    public static final String MT = "4";
    public static final String ProtocolVersion_2 = NetLibUtil.utf8URLencode("2.0");
    public static final String ProtocolVersion_3 = NetLibUtil.utf8URLencode("3.0");
    public static final String ProtocolVersion_4 = NetLibUtil.utf8URLencode("4.0");
    public static final String LAUNCHER_REQUEST_KEY = "27B1F81F-1DD8-4F98-8D4B-6992828FB6E2";
    public static final String PO_REQUEST_KEY = "2B1F781F-1D8D-984F-D84B-9826E6928FB2";
    public static final String REQUEST_KEY = "58D1BAC3-3477-4870-9AD4-4879259652B7";

//    public static void addGlobalLauncherRequestValue(HashMap<String, String> paramsMap, String jsonParams) {
////        String sessionID = getUserSession(Global.getApplicationContext()).sessionId;
////        if (sessionID == null) sessionID = "";
//        String sessionID = "";
//        addGlobalLauncherRequestValue(paramsMap, jsonParams, sessionID);
//    }


    public static void addCommmonPostRequestValue(Context context, HashMap<String, String> paramsMap) {
        paramsMap.put("client-info", "5.0,Android,5.0.1");
        paramsMap.put("Accept-Encoding", "gzip, deflate");
        paramsMap.put("accept", "*/*");
        paramsMap.put("Accept-Language", "zh-CN,zh;q=0.9");
        paramsMap.put("User-Agent", getUserAgent(context));
        paramsMap.put("imei", NetLibUtil.getIMEI(context));
    }

    public static void addCommmonGetRequestValue(Context context, HashMap<String, String> paramsMap) {
        paramsMap.put("client-info", "5.0,Android,5.0.1");
        paramsMap.put("Accept-Encoding", "gzip, deflate");
        paramsMap.put("accept", "*/*");
        paramsMap.put("Accept-Language", "zh-CN,zh;q=0.9");
        paramsMap.put("User-Agent", getUserAgent(context));
        paramsMap.put("imei", NetLibUtil.getIMEI(context));
    }

    public static String getUserAgent(Context context) {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String parsePostData(String... values) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            if (i % 2 == 0) {
                if (i == 0) {
                    stringBuffer.append(value);
                } else {
                    stringBuffer.append("&" + value);
                }
            } else {
                stringBuffer.append("=" + value);
            }
        }
        return stringBuffer.toString();
    }

    public static String parsePostData(LinkedHashMap linkedHashMap) {
        StringBuffer stringBuffer = new StringBuffer();
        if (linkedHashMap != null & linkedHashMap.size() > 0) {
            int i = 0;
            Set<String> keyset = linkedHashMap.keySet();
            for (Iterator<String> it = keyset.iterator(); it.hasNext(); ) {
                String key = it.next();
                String value = (String) linkedHashMap.get(key);
                if (i > 0) {
                    stringBuffer.append("&" + key + "=" + value);
                } else {
                    stringBuffer.append(key + "=" + value);
                }
                i++;
            }
        }
        return stringBuffer.toString();
    }


//
//    /**
//     * 添加桌面接口通用参数
//     *
//     * @param paramsMap
//     * @param jsonParams
//     */
//    public static void addGlobalLauncherRequestValue(HashMap<String, String> paramsMap, String jsonParams, String sessionId) {
//        Context ctx = Global.getApplicationContext();
//        if (paramsMap == null)
//            return;
//        if (jsonParams == null) {
//            jsonParams = "";
//        }
//        try {
//            if (null == DivideVersion)
//                DivideVersion = NetLibUtil.utf8URLencode(NetLibUtil.getDivideVersion(ctx));
//            if (null == SupPhone)
//                SupPhone = Des2.encodeHeader(Des2.HEADER_DES_KEY, Des2.HEADER_DESIV, NetLibUtil.utf8URLencode(NetLibUtil.getBuildMode()).getBytes());
//            if (null == SupFirm)
//                SupFirm = Des2.encodeHeader(Des2.HEADER_DES_KEY, Des2.HEADER_DESIV, NetLibUtil.utf8URLencode(NetLibUtil.getBuildVersion()).getBytes());
//            if (null == IMEI)
//                IMEI = Des2.encodeHeader(Des2.HEADER_DES_KEY, Des2.HEADER_DESIV, NetLibUtil.utf8URLencode(NetLibUtil.getIMEI(ctx)).getBytes());
//            if (null == IMSI)
//                IMSI = Des2.encodeHeader(Des2.HEADER_DES_KEY, Des2.HEADER_DESIV, NetLibUtil.utf8URLencode(NetLibUtil.getIMSI(ctx)).getBytes());
//            if (null == CUID)
//                CUID = URLEncoder.encode(NetLibUtil.getCUID(ctx), "UTF-8");
//
//            CUID = TextUtils.isEmpty(CUID) ? IMEI : CUID;
//            sessionId = sessionId == null ? "" : sessionId;
//
//            paramsMap.put("PID", BaseConfig.APPID + "");
//            paramsMap.put("MT", MT);
//            paramsMap.put("DivideVersion", DivideVersion);
//            paramsMap.put("SupPhone", SupPhone);
//            paramsMap.put("SupFirm", SupFirm);
//            paramsMap.put("IMEI", IMEI);
//            paramsMap.put("IMSI", IMSI);
//            paramsMap.put("SessionId", sessionId);
//            paramsMap.put("CUID", CUID);//通用用户唯一标识 NdAnalytics.getCUID(ctx)
//            paramsMap.put("ProtocolVersion", ProtocolVersion_3);
//            paramsMap.put("EncryptType", "100");
////            paramsMap.put("EnableStatus", "1");//值为1，表示支持H265格式
//            String Sign = DigestUtil.md5Hex(BaseConfig.APPID + MT + DivideVersion + SupPhone + SupFirm + IMEI + IMSI + sessionId + CUID + ProtocolVersion_3 + jsonParams + LAUNCHER_REQUEST_KEY);
//            paramsMap.put("Sign", Sign);
//            paramsMap.put("EnableStatus", getEnableStatus(ctx));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String getEnableStatus(Context context){
//        int enableStatus = 0;
//        boolean enableH265 = false;// 1:开启 0:未开启
//        boolean enableWallpaperService = VideoWallpaperTool.hasApplyLiveWallpaperBefore(context); // 2:开启 0:未开启
//        if (enableH265) {
//            enableStatus += 1;
//        }
//        if (enableWallpaperService) {
//            enableStatus += 2;
//        }
//        return String.valueOf(enableStatus);
//    }
//
//    /**
//     * 添加PO接口通用参数
//     *
//     * @param paramsMap
//     * @param jsonParams
//     */
//    public static void addGlobalPoRequestValue(HashMap<String, String> paramsMap, String jsonParams, String sessionId) {
//        Context ctx = Global.getApplicationContext();
//        if (paramsMap == null)
//            return;
//        if (jsonParams == null) {
//            jsonParams = "";
//        }
//        try {
//            if (null == DivideVersion)
//                DivideVersion = NetLibUtil.utf8URLencode(NetLibUtil.getDivideVersion(ctx));
//            if (null == SupPhone)
//                SupPhone = Des2.encodeHeader(Des2.HEADER_DES_KEY, Des2.HEADER_DESIV, NetLibUtil.utf8URLencode(NetLibUtil.getBuildMode()).getBytes());
//            if (null == SupFirm)
//                SupFirm = Des2.encodeHeader(Des2.HEADER_DES_KEY, Des2.HEADER_DESIV, NetLibUtil.utf8URLencode(NetLibUtil.getBuildVersion()).getBytes());
//            if (null == IMEI)
//                IMEI = Des2.encodeHeader(Des2.HEADER_DES_KEY, Des2.HEADER_DESIV, NetLibUtil.utf8URLencode(NetLibUtil.getIMEI(ctx)).getBytes());
//            if (null == IMSI)
//                IMSI = Des2.encodeHeader(Des2.HEADER_DES_KEY, Des2.HEADER_DESIV, NetLibUtil.utf8URLencode(NetLibUtil.getIMSI(ctx)).getBytes());
//            if (null == CUID)
//                CUID = URLEncoder.encode(NetLibUtil.getCUID(ctx), "UTF-8");
//
//            CUID = TextUtils.isEmpty(CUID) ? IMEI : CUID;
//            sessionId = sessionId == null ? "" : sessionId;
//            String resolution = ScreenUtil.getCurrentScreenWidth(ctx) + "x" + ScreenUtil.getCurrentScreenHeight(ctx);
//
//            paramsMap.put("PID", BaseConfig.PID);
//            paramsMap.put("MT", MT);
//            paramsMap.put("DivideVersion", DivideVersion);
//            paramsMap.put("SupPhone", SupPhone);
//            paramsMap.put("SupFirm", SupFirm);
//            paramsMap.put("IMEI", IMEI);
//            paramsMap.put("IMSI", IMSI);
//            paramsMap.put("SessionId", sessionId);
//            paramsMap.put("CUID", CUID);//通用用户唯一标识 NdAnalytics.getCUID(ctx)
//            paramsMap.put("ProtocolVersion", ProtocolVersion_3);
//            paramsMap.put("Resolution",resolution);
//            String Sign = DigestUtil.md5Hex(BaseConfig.APPID + MT + DivideVersion + SupPhone + SupFirm + IMEI + IMSI + sessionId + CUID + resolution + ProtocolVersion_3 + jsonParams + PO_REQUEST_KEY);
//            paramsMap.put("Sign", Sign);
//            paramsMap.put("ChannelID", ChannelUtil.getChannel(ctx));
//            paramsMap.put("EncryptType", "100");
//            paramsMap.put("EnableStatus",getEnableStatus(ctx));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static class UserSession {
//        public String appName;
//        public int appId;
//        public String nickName;
//        public String loginUin;
//        public String sessionId;
//    }

}
