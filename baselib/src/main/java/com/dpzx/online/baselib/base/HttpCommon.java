package com.dpzx.online.baselib.base;

import android.text.TextUtils;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by dingdongjin_dian91 on 2016/3/1.
 */
public class HttpCommon {

    //    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType JSON = MediaType.parse("application/x-www-form-urlencoded");
    public static final String CHARSET_UTF_8 = "UTF-8";

    /**
     * 通用的Http响应头
     */
    public static final String RESULT_CODE = "ResultCode";
    public static final String RESULT_MESSAGE = "ResultMessage";
    public static final String BODY_ENCRYPT_TYPE = "BodyEncryptType";
    private static final String TAG = "HttpCommon";

    //接口请求是否要使用https
    public static boolean IS_USE_HTTPS = false;

    //public static String HOST = Constants.IP_ADDR;
    /**
     * URL地址
     */
    private String url;

    /**
     * 编码格式
     */
    private String encoding = CHARSET_UTF_8;

    private ServerResultHeader csResult = new ServerResultHeader();

    private static HttpClient httpClient;

    /**
     * 在app在application中初始化
     */
    public static void initClient(HttpClient httpClient) {
        HttpCommon.httpClient = httpClient;
    }


//    private static final OkHttpClient sOkHttpClient = getDefaultHttpClient(IS_USE_HTTPS);

    public HttpCommon(String url, ServerResultInterceptor resultInterceptor) {
        this.url = NetLibUtil.utf8URLencode(url);
        if (resultInterceptor != null) {
            csResult.setInterceptor(resultInterceptor);
        }
    }

    public HttpCommon(String url, String encoding, ServerResultInterceptor resultInterceptor) {
        this.url = NetLibUtil.utf8URLencode(url);
        this.encoding = encoding;
        if (resultInterceptor != null) {
            csResult.setInterceptor(resultInterceptor);
        }
    }


    public static void appendAttrValue(StringBuffer sb, String key, String... values) {
        if (sb.indexOf("?" + key + "=") != -1 || sb.indexOf("&" + key + "=") != -1) {
            return;
        }
        for (String value : values) {
            if (sb.indexOf("?") == -1) {
                sb.append("?");
            } else {
                sb.append("&");
            }
            sb.append(key);
            sb.append("=");
            sb.append(value);
        }
    }

    private static OkHttpClient getDefaultHttpClient(boolean isHttps) {
        return isHttps? httpClient.getDefaultTLSOkHttpClient(): httpClient.getDefaultOkHttpClient();
    }

    private static int getShort(byte[] buffer, int off) {
        return (buffer[off] & 0xFF) | ((buffer[off + 1] & 0xFF) << 8);
    }

    public void setUrl(String url) {
        this.url = NetLibUtil.utf8URLencode(url);
    }

    public ServerResultHeader getResponseAsCsResultPost(HashMap<String, String> headerParamsMap, String jsonParams) {
        ServerResultHeader header = getResponseAsCsResult(HttpMethod.POST, headerParamsMap, jsonParams);
        return header;
    }

    public ServerResultHeader getResponseAsCsResult(String method, HashMap<String, String> headerParamsMap, String jsonParams) {
        csResult.setbNetworkProblem(false);
        String responseStr = null;
        InputStream is = null;
        OkHttpClient client = null;
        try {
            Request.Builder builder = new Request.Builder();

            RequestBody body = RequestBody.create(JSON, jsonParams);
            final String httpMethod = TextUtils.isEmpty(method) ? HttpMethod.POST : method.toUpperCase();
//            if (HttpMethod.POST.equalsIgnoreCase(httpMethod) || HttpMethod.PUT.equalsIgnoreCase(httpMethod)) {
//            }
            builder.url(url);
            builder.method(httpMethod, body);

            if (null != headerParamsMap) {
                for (String key : headerParamsMap.keySet()) {
                    builder.addHeader(key, headerParamsMap.get(key));
                }
            }
            client = getDefaultHttpClient(false);
            Request request = builder.build();
            Response response = client.newCall(request).execute();
//            int statusCode = response.code();
            Date date = response.headers().getDate("Date");
            if (date != null) {
                csResult.setServerTime(date.getTime());
            }
            if (response.isSuccessful()) {
                getCommonServerResult(response);
                int bodyEncryptType = csResult.getBodyEncryptType();
                if (bodyEncryptType == 0) {
                    responseStr = new String(response.body().bytes(), encoding);
                } else if (bodyEncryptType == 1) {
                    BufferedInputStream bis = null;
                    try {
                        is = response.body().byteStream();
                        bis = new BufferedInputStream(is);
                        bis.mark(2); // 取前两个字节
                        byte[] header = new byte[2];
                        int result = bis.read(header);
                        bis.reset(); // reset输入流到开始位置
                        if (result != -1 && getShort(header, 0) == GZIPInputStream.GZIP_MAGIC) {
                            is = new GZIPInputStream(bis);
                        } else {
                            is = bis;
                        }
                        responseStr = parse(response.body(), is, encoding);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                csResult.setbNetworkProblem(true);
            }
        } catch (Exception ex) {
            csResult.setbNetworkProblem(true);
            ex.printStackTrace();
        }
        //Log.d(TAG, "csResult = " + csResult);
        //Log.d(TAG, "response = " + responseStr);
        csResult.setResponseJson(responseStr);
        return csResult;
    }

    public ServerResultHeader getResponseAsCsResultPost(HashMap<String, String> headerParamsMap, String jsonParams, ServerResultInterceptor interceptor) {
        csResult.setInterceptor(interceptor);
        return getResponseAsCsResultPost(headerParamsMap, jsonParams);
    }

    public ServerResultHeader getResponseAsCsResultPostHttpConnection(HashMap<String, String> headerParamsMap, String jsonParams) {
        csResult.setbNetworkProblem(false);
        HttpURLConnection client = null;
        ByteArrayOutputStream baos = null;
        String responseStr = null;
        try {
            URL _url = new URL(url);
            client = (HttpURLConnection) _url.openConnection();
            client.setRequestMethod("POST");
            client.setDoOutput(true);
            client.setDoInput(true);

            if (null != headerParamsMap) {
                for (String key : headerParamsMap.keySet()) {
                    client.addRequestProperty(key, headerParamsMap.get(key));
                }
            }
            client.addRequestProperty("Content-Type", "text/plain; charset=UTF-8");
            client.getOutputStream().write(jsonParams.getBytes("UTF-8"));
            client.connect();
            int statusCode = client.getResponseCode();
            if (statusCode == 200) {
                String resultCode = client.getHeaderField(RESULT_CODE);
                if (!okhttpStringUtils.isEmpty(resultCode)) {
                    csResult.setResultCode(Integer.parseInt(resultCode));
                }
                String resultMessage = client.getHeaderField(RESULT_MESSAGE);
                if (!okhttpStringUtils.isEmpty(resultMessage)) {
                    csResult.setResultMessage(coverHeadResultMessge(resultMessage));
                }
                String encryptType = client.getHeaderField(BODY_ENCRYPT_TYPE);
                if (!okhttpStringUtils.isEmpty(encryptType)) {
                    csResult.setBodyEncryptType(Integer.parseInt(encryptType));
                }
                int bodyEncryptType = csResult.getBodyEncryptType();
                baos = new ByteArrayOutputStream();

                InputStream is = client.getInputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                if (bodyEncryptType == 0) {
                    responseStr = new String(baos.toByteArray(), encoding);
                } else if (bodyEncryptType == 1) {
                    BufferedInputStream bis = null;
                    try {
                        bis = new BufferedInputStream(is);
                        bis.mark(2); // 取前两个字节
                        byte[] header = new byte[2];
                        int result = bis.read(header);
                        bis.reset(); // reset输入流到开始位置
                        if (result != -1 && getShort(header, 0) == GZIPInputStream.GZIP_MAGIC) {
                            is = new GZIPInputStream(bis);
                        } else {
                            is = bis;
                        }
                        responseStr = parse(Integer.valueOf(client.getHeaderField("Content-Length")), is, encoding);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                csResult.setbNetworkProblem(true);
            }
        } catch (Exception ex) {
            csResult.setbNetworkProblem(true);
            ex.printStackTrace();
        } finally {
            if (client != null) {
                client.disconnect();
            }

            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //Log.d(TAG, "csResult = " + csResult);
        //Log.d(TAG, "response = " + responseStr);
        csResult.setResponseJson(responseStr);
        return csResult;
    }

    /**
     * 头部数据解析
     *
     * @param response
     */
    private void getCommonServerResult(Response response) {
        String resultCode = response.header(RESULT_CODE);
        if (!okhttpStringUtils.isEmpty(resultCode)) {
            csResult.setResultCode(Integer.parseInt(resultCode));
        }
        String resultMessage = response.header(RESULT_MESSAGE);
        if (!okhttpStringUtils.isEmpty(resultMessage)) {
            csResult.setResultMessage(coverHeadResultMessge(resultMessage));
        }
        String encryptType = response.header(BODY_ENCRYPT_TYPE);
        if (!okhttpStringUtils.isEmpty(encryptType)) {
            csResult.setBodyEncryptType(Integer.parseInt(encryptType));
        }
    }

    private String coverHeadResultMessge(String srcMessage) {
        try {
            return URLDecoder.decode(srcMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return srcMessage;
    }

    private String parse(ResponseBody body, InputStream instream, final String charset) throws IOException {
        if (instream == null || body == null) {
            return "";
        }
        int i = (int) body.contentLength();
        if (i < 0) {
            i = 4096;
        }
        Reader reader = new InputStreamReader(instream, charset);
        CharArrayBuffer buffer = new CharArrayBuffer(i);
        try {
            char[] tmp = new char[1024];
            int l;
            while ((l = reader.read(tmp)) != -1) {
                buffer.append(tmp, 0, l);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return buffer.toString();
    }

    private String parse(int contentLenght, InputStream instream, final String charset) throws IOException {
        if (instream == null || contentLenght <= 0) {
            return "";
        }
        int i = contentLenght;
        if (i < 0) {
            i = 4096;
        }
        Reader reader = new InputStreamReader(instream, charset);
        CharArrayBuffer buffer = new CharArrayBuffer(i);
        try {
            char[] tmp = new char[1024];
            int l;
            while ((l = reader.read(tmp)) != -1) {
                buffer.append(tmp, 0, l);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return buffer.toString();
    }

    /**
     * 获取get请求
     *
     * @param paramsMap
     * @return
     */
    public Response getResponseAsEntityGet(HashMap<String, String> paramsMap) {
        try {
            String getUrl = makeGetURL(paramsMap);
            getUrl = NetLibUtil.utf8URLencode(getUrl);
            OkHttpClient client = getDefaultHttpClient(false);
            Request request = new Request.Builder().url(getUrl).build();
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                return response;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取get请求
     *
     * @param paramsMap
     * @return
     */
    public Response getResponseGet(HashMap<String, String> paramsMap) {
        try {
            String getUrl = makeGetURL(paramsMap);
            getUrl = NetLibUtil.utf8URLencode(getUrl);
            OkHttpClient client = getDefaultHttpClient(false);
            Request request = new Request.Builder().url(getUrl).build();
            Response response = client.newCall(request).execute();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private String makeGetURL(HashMap<String, String> paramsMap) {
        if (paramsMap == null)
            return url;
        StringBuffer paramStr = new StringBuffer("");
        boolean hasQuestion = url.lastIndexOf("?") > 0 ? true : false;
        for (String key : paramsMap.keySet()) {
            paramStr.append("&").append(key).append("=").append(paramsMap.get(key));
        }
        if (!hasQuestion) {
            paramStr.replace(0, 1, "?");
        }
        return url + paramStr.toString();
    }


    /**
     * 通过url获取document对象
     *
     * @return Document
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws FactoryConfigurationError
     * @throws SAXException
     */
    public Document getDocument() throws IOException, ParserConfigurationException, FactoryConfigurationError, SAXException {
        Response response = getResponseAsEntityGet(null);
        if (response == null || response.body() == null)
            return null;
        InputStream stream = response.body().byteStream();
        if (stream == null) {
            return null;
        }
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource inputSource = new InputSource(stream);
        Document document = builder.parse(inputSource);
        stream.close();
        return document;
    }


}
