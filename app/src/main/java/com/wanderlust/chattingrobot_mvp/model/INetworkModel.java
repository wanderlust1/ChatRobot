package com.wanderlust.chattingrobot_mvp.model;

import com.wanderlust.chattingrobot_mvp.model.implement.NetworkModelImpl;
import com.wanderlust.chattingrobot_mvp.presenter.implement.ChatPresenterImpl;

/**
 * @author Wanderlust 2019.5.18
 *
 * 这个接口定义了Model层网络请求的功能。
 *
 * @see NetworkModelImpl      这个接口在且只在这里被实现。
 * @see ChatPresenterImpl 这个接口被且只被它所持有。
 * */

public interface INetworkModel {

    /** 处理待发送的消息 */
    void executeSendRequest(String text);

}
