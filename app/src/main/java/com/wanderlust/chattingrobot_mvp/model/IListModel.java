package com.wanderlust.chattingrobot_mvp.model;

import com.wanderlust.chattingrobot_mvp.model.beans.TextBean;
import com.wanderlust.chattingrobot_mvp.model.implement.ListModelImpl;
import com.wanderlust.chattingrobot_mvp.presenter.implement.ChatPresenterImpl;

import java.util.List;

/**
 * @author Wanderlust 2019.5.18
 *
 * 这个接口定义了对RecyclerView的List对象的访问操作。
 *
 * @see ListModelImpl     这个接口在且只在这里被实现。
 * @see ChatPresenterImpl 这个接口被且只被它所持有。
 * */

public interface IListModel {

    /** 返回RecyclerView的列表 */
    List<TextBean> getList();

    /** 清空RecyclerView列表，并通知刷新 */
    void clearList();

    /** 添加一个元素至RecyclerView列表，并通知刷新 */
    void add(String text, int type);

    void setList(List<TextBean> list);

}
