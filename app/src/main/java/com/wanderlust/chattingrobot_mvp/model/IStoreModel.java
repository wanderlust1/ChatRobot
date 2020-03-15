package com.wanderlust.chattingrobot_mvp.model;

import com.wanderlust.chattingrobot_mvp.model.implement.StoreModelImpl;
import com.wanderlust.chattingrobot_mvp.presenter.implement.ChatPresenterImpl;


/**
 * @author Wanderlust 2019.5.18
 *
 * 这个接口定义了Model层数据存储的功能。
 *
 * @see StoreModelImpl    这个接口在且只在这里被实现。
 * @see ChatPresenterImpl 这个接口被且只被它所持有。
 * */

public interface IStoreModel {

    void save(String text, int type);

    void loadAll();

    void delete(String text, int type);

    void deleteAll();
}
