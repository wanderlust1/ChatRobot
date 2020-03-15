package com.wanderlust.chattingrobot_mvp.presenter;

import com.wanderlust.chattingrobot_mvp.model.beans.TextBean;
import com.wanderlust.chattingrobot_mvp.model.implement.NetworkModelImpl;
import com.wanderlust.chattingrobot_mvp.presenter.implement.ChatPresenterImpl;

import java.util.List;

/**
 * @author Wanderlust 2019.5.18
 *
 * 这个接口包含了M层通过P层向V层反馈请求结果的回调方法。
 *
 * @see ChatPresenterImpl 这个接口在且只在这里被实现。
 * @see NetworkModelImpl  这个接口被且只被它所持有。
 * */

public interface ModelCallback {

    /** 当从服务器上成功收到一条消息，应显示在RecyclerView列表中 */
    void onReceiveMessage(String text);

    /** 当从服务器上成功收到一条URL，应进行页面跳转 */
    void onReceiveIntent(String url);

    /** 当列表元素更新时，应对RecyclerView进行更新 */
    void onListDataChange();

    /** 当向服务器请求失败时，应返回对应信息 */
    void onFail(String text);

    void onLoad(List<TextBean> list);

}
