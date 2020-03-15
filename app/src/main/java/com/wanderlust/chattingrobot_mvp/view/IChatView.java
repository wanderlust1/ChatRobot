package com.wanderlust.chattingrobot_mvp.view;

import com.wanderlust.chattingrobot_mvp.model.beans.TextBean;
import com.wanderlust.chattingrobot_mvp.presenter.implement.ChatPresenterImpl;
import com.wanderlust.chattingrobot_mvp.view.implement.ChatActivity;

import java.util.List;

/**
 * @author Wanderlust 2019.5.18
 *
 * 这个接口定义了View层的功能。
 *
 * @see ChatActivity      这个接口在且只在这里被实现。
 * @see ChatPresenterImpl 这个接口被且只被它所持有。
 * */

public interface IChatView {

    /** 展示一个Toast */
    void showToast(String text);

    /** 进行Intent跳转 */
    void intent(String url);

    /** 收到一条消息后，在页面中显示出来，以及滑动至列表最低部 */
    void showReceivedMessage(String text);

    /** 刷新RecyclerView的List */
    void refreshList();

    void initList(List<TextBean> list);

}
