package com.wanderlust.chattingrobot_mvp.model.implement;

import com.wanderlust.chattingrobot_mvp.model.IListModel;
import com.wanderlust.chattingrobot_mvp.model.beans.TextBean;
import com.wanderlust.chattingrobot_mvp.presenter.ModelCallback;

import java.util.ArrayList;
import java.util.List;

public class ListModelImpl implements IListModel {

    private List<TextBean> list;     //消息列表

    private ModelCallback mCallback; //回调处理接口

    public ListModelImpl(ModelCallback callback) {
        this.list = new ArrayList<>();
        this.mCallback = callback;
    }

    @Override
    public List<TextBean> getList() {
        return list;
    }

    @Override
    public void setList(List<TextBean> list) {
        this.list.addAll(list);
        mCallback.onListDataChange();
    }

    @Override
    public void clearList() {
        list.clear();
        mCallback.onListDataChange();

    }

    @Override
    public void add(String text, int type) {
        list.add(new TextBean(text, type));
        mCallback.onListDataChange();
    }

}
