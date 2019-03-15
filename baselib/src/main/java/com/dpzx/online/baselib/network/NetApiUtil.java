package com.dpzx.online.baselib.network;

import android.text.TextUtils;

import com.dpzx.online.baselib.base.ApiUrlManager;
import com.dpzx.online.baselib.base.HttpCommon;
import com.dpzx.online.baselib.base.HttpRequestParam;
import com.dpzx.online.baselib.base.ServerResult;
import com.dpzx.online.baselib.base.ServerResultHeader;
import com.dpzx.online.baselib.bean.VideoPaperBean;
import com.dpzx.online.baselib.config.Global;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @Description: 网络数据请求工具类</   br>
 * @author: cxy </br>
 * @date: 2017年05月04日 11:14.</br>
 * @update: </br>
 */

public class NetApiUtil {


    public static final ServerResult<VideoPaperBean> postLoginAccount(String account, String password, String imei) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("account", account);
        linkedHashMap.put("password", password);
        linkedHashMap.put("imei", imei);
        String httpStr = HttpRequestParam.parsePostData(linkedHashMap);
        HashMap<String, String> paramsMap = new HashMap<>();
        HttpRequestParam.addCommmonPostRequestValue(Global.getApplicationContext(), paramsMap);
        HttpCommon httpCommon = new HttpCommon(ApiUrlManager.API_LOGIN_URL, null);
        ServerResultHeader csResult = httpCommon.getResponseAsCsResultPost(paramsMap, httpStr);
        ServerResult<VideoPaperBean> resTagList = new ServerResult<VideoPaperBean>();
        if (csResult != null) {
            resTagList.setCsResult(csResult);
            String responseStr = csResult.getResponseJson();
            if (!TextUtils.isEmpty(responseStr)) {
                try {
                    JSONObject json = new JSONObject(responseStr);
                    int total = json.optInt("RecordCount");
                    String message = json.optString("message");
                    resTagList.getCsResult().setResultMessage(message);
                    JSONArray jsonArray = json.optJSONArray("ResList");
                    if (jsonArray != null && jsonArray.length() > 0) {
                        for (int i = 0, len = jsonArray.length(); i < len; i++) {
                            JSONObject jsonBean = jsonArray.optJSONObject(i);
//                            VideoPaperBean bean = NetDataParser.jsonToUnit(jsonBean, BaseConfig.LinkEncryptEnable);
//                            if (bean != null) {
//                                resTagList.itemList.add(bean);
//                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resTagList;
    }

    public static final ServerResult<VideoPaperBean> getPhoneExit(String account) {
        HashMap<String, String> paramsMap = new HashMap<>();
        HttpRequestParam.addCommmonGetRequestValue(Global.getApplicationContext(), paramsMap);
        HttpCommon httpCommon = new HttpCommon(ApiUrlManager.API_PHONE_EXIST_URL + account, null);
        ServerResultHeader csResult = httpCommon.getResponseAsCsResultGet(paramsMap, null);
        ServerResult<VideoPaperBean> resTagList = new ServerResult<VideoPaperBean>();
        if (csResult != null) {
            resTagList.setCsResult(csResult);
            String responseStr = csResult.getResponseJson();
            if (!TextUtils.isEmpty(responseStr)) {
                try {
                    JSONObject json = new JSONObject(responseStr);
                    int total = json.optInt("RecordCount");
                    String message = json.optString("message");
                    boolean datas = json.optBoolean("datas");
                    resTagList.getCsResult().setResultMessage(message);
                    resTagList.getCsResult().setDatas(datas);
                    JSONArray jsonArray = json.optJSONArray("ResList");
                    if (jsonArray != null && jsonArray.length() > 0) {
                        for (int i = 0, len = jsonArray.length(); i < len; i++) {
                            JSONObject jsonBean = jsonArray.optJSONObject(i);
//                            VideoPaperBean bean = NetDataParser.jsonToUnit(jsonBean, BaseConfig.LinkEncryptEnable);
//                            if (bean != null) {
//                                resTagList.itemList.add(bean);
//                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resTagList;
    }

    /**
     * 发送注册短信验证码
     */
    public static final ServerResult<VideoPaperBean> postPhonecode(String phone) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("phone", phone);
        String httpStr = HttpRequestParam.parsePostData(linkedHashMap);
        HashMap<String, String> paramsMap = new HashMap<>();
        HttpRequestParam.addCommmonPostRequestValue(Global.getApplicationContext(), paramsMap);
        HttpCommon httpCommon = new HttpCommon(ApiUrlManager.API_PHONE_CODE_URL, null);
        ServerResultHeader csResult = httpCommon.getResponseAsCsResultPost(paramsMap, httpStr);
        ServerResult<VideoPaperBean> resTagList = new ServerResult<VideoPaperBean>();
        if (csResult != null) {
            resTagList.setCsResult(csResult);
            String responseStr = csResult.getResponseJson();
            if (!TextUtils.isEmpty(responseStr)) {
                try {
                    JSONObject json = new JSONObject(responseStr);
                    int total = json.optInt("RecordCount");
                    String message = json.optString("message");
                    boolean datas = json.optBoolean("datas");
                    resTagList.getCsResult().setResultMessage(message);
                    resTagList.getCsResult().setDatas(datas);
                    //JSONArray jsonArray = json.optJSONArray("datas");
                    JSONObject datasJsonObj = json.optJSONObject("datas");
                    String identifyCodeId = datasJsonObj.optString("identifyCodeId");
                    resTagList.getCsResult().setResultMessage(identifyCodeId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resTagList;
    }

    /**
     * 商户注册
     */
    public static final ServerResult<VideoPaperBean> postUserRegister(String phone, String password, String identifyCodeId, String registerSource) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("account", phone);
        linkedHashMap.put("password", password);
        linkedHashMap.put("identifyCodeId", identifyCodeId);//短信流水号
        linkedHashMap.put("registerSource", registerSource);//注册来源,0其他，1邀请注册
        String httpStr = HttpRequestParam.parsePostData(linkedHashMap);
        HashMap<String, String> paramsMap = new HashMap<>();
        HttpRequestParam.addCommmonPostRequestValue(Global.getApplicationContext(), paramsMap);
        HttpCommon httpCommon = new HttpCommon(ApiUrlManager.API_USER_REGISTER_URL + registerSource, null);
        ServerResultHeader csResult = httpCommon.getResponseAsCsResultPost(paramsMap, httpStr);
        ServerResult<VideoPaperBean> resTagList = new ServerResult<VideoPaperBean>();
        if (csResult != null) {
            resTagList.setCsResult(csResult);
            String responseStr = csResult.getResponseJson();
            if (!TextUtils.isEmpty(responseStr)) {
                try {
                    JSONObject json = new JSONObject(responseStr);
                    int total = json.optInt("RecordCount");
                    String message = json.optString("message");
                    boolean datas = json.optBoolean("datas");
                    resTagList.getCsResult().setResultMessage(message);
                    resTagList.getCsResult().setDatas(datas);
                    JSONArray jsonArray = json.optJSONArray("ResList");
                    if (jsonArray != null && jsonArray.length() > 0) {
                        for (int i = 0, len = jsonArray.length(); i < len; i++) {
                            JSONObject jsonBean = jsonArray.optJSONObject(i);
//                            VideoPaperBean bean = NetDataParser.jsonToUnit(jsonBean, BaseConfig.LinkEncryptEnable);
//                            if (bean != null) {
//                                resTagList.itemList.add(bean);
//                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resTagList;
    }

}
