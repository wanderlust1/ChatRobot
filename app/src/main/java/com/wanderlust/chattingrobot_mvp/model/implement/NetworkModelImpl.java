package com.wanderlust.chattingrobot_mvp.model.implement;

import android.support.annotation.NonNull;

import com.wanderlust.chattingrobot_mvp.model.INetworkModel;
import com.wanderlust.chattingrobot_mvp.presenter.ModelCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkModelImpl implements INetworkModel {

    private final String USER_ID = "283552";
    private final String API_KEY = "fbcc560fa4fa454fa93a85cf0ad3d7f8";
    private final String URL     = "http://openapi.tuling123.com/openapi/api/v2";

    private JSONObject userInfo;  //用户信息

    private ModelCallback mCallback; //回调处理接口

    public NetworkModelImpl(ModelCallback callback) {
        this.mCallback = callback;
        setUserInfo();
    }

    @Override
    public void executeSendRequest(String text) {
        sendRequest(getJSONRequest(text));
    }

    /** 设置初始用户信息 */
    private void setUserInfo() {
        userInfo = new JSONObject();
        try {
            userInfo.put("apiKey", API_KEY);
            userInfo.put("userId", USER_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /** 创建OkHttp客户端并发送请求 **/
    private void sendRequest(JSONObject send) {
        OkHttpClient client = new OkHttpClient(); //创建okHttp客户端
        MediaType JSON = MediaType.parse("application/json; charset=utf-8"); //POST请求类型(JSON)
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(send)); //POST请求体
        Request request = new Request.Builder().url(URL).post(requestBody).build(); //POST请求

        //返回处理：
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mCallback.onFail("连接异常，请检查网络...");
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                handleResponse(Objects.requireNonNull(response.body()).string());
            }
        });
    }

    /** 【请求】将输入的文本转换为可post的JSON对象 **/
    private JSONObject getJSONRequest(String input) {
        JSONObject request = new JSONObject();      //request主体
        JSONObject perception = new JSONObject();   //request -> perception
        JSONObject inputText = new JSONObject();    //request -> perception -> inputText
        try {
            inputText.put("text", input);
            perception.put("inputText", inputText);
            request.put("reqType", 0);
            request.put("userInfo", userInfo);
            request.put("perception", perception);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    /** 【返回】处理请求并显示到RecyclerView上 **/
    private void handleResponse(String response) {
        try {
            JSONObject mainBody = new JSONObject(response);
            if (mainBody.has("results")) {
                JSONArray result = mainBody.getJSONArray("results");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject object = result.getJSONObject(i);
                    switch (object.getString("resultType")) { //对返回结果类型分别处理
                        case "url": {
                            mCallback.onReceiveIntent(
                                    object.getJSONObject("values").getString("url"));
                        }
                        case "voice": break;
                        case "video": break;
                        case "image": break;
                        case "news": break;
                        case "text": {
                            mCallback.onReceiveMessage(
                                    object.getJSONObject("values").getString("text"));
                        }
                        default: break;
                    }
                }
            } else {
                int code = mainBody.getJSONObject("intent").getJSONObject("code").getInt("code");
                handleError(code);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /** 处理错误码 **/
    private void handleError(int errorCode) {
        String text;
        switch (errorCode) {
            case 5000: text = "（错误码5000）暂无无解析结果"; break;
            case 6000: text = "（错误码6000）图灵机器人暂不支持该功能"; break;
            case 4002:
            case 4005: text = "（错误码4002&4005）您的图灵机器人没有该功能的权限"; break;
            case 4003: text = "（错误码4003）该ApiKey的可用请求次数已用完"; break;
            case 4400: text = "（错误码4400）UserId不正确"; break;
            case 4602: text = "（错误码4602）输入的文本过长(上限150)";  break;
            case 7002: text = "（错误码7002）上传消息失败";  break;
            case 8008: text = "（错误码8008）服务器异常"; break;
            default: text = "服务器出现未知错误..."; break;
        }
        mCallback.onFail(text);
    }
}
