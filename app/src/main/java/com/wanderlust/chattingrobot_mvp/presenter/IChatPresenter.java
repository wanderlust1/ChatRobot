package com.wanderlust.chattingrobot_mvp.presenter;

import com.wanderlust.chattingrobot_mvp.model.beans.TextBean;
import com.wanderlust.chattingrobot_mvp.presenter.implement.ChatPresenterImpl;
import com.wanderlust.chattingrobot_mvp.view.implement.ChatActivity;

import java.util.List;

/**
 * @author Wanderlust 2019.5.18
 *
 * 这个接口包含了P层与V层交互的方法。
 *
 * @see ChatPresenterImpl 这个接口在且只在这里被实现。
 * @see ChatActivity      这个接口被且只被它所持有。
 * */

public interface IChatPresenter {

    /** 【调用Model层】获得列表成员变量 */
    List<TextBean> getList();

    void setList(List<TextBean> list);

    /** 【调用Model层】清除整个列表的元素 */
    void clearList();

    /** 【调用Model层】添加一个元素至列表 */
    void addToList(String text, int type);

    void save(String text, int type);

    /** 【控制View层】send按下send按钮后，触发网络请求消息 */
    void requestSendMessage(String msg);

    /** 【调用Model层】加载所有已存储的元素 */
    void loadAll();

}
