package com.wanderlust.chattingrobot_mvp.presenter.implement;

import com.wanderlust.chattingrobot_mvp.model.IListModel;
import com.wanderlust.chattingrobot_mvp.model.IStoreModel;
import com.wanderlust.chattingrobot_mvp.model.implement.NetworkModelImpl;
import com.wanderlust.chattingrobot_mvp.model.INetworkModel;
import com.wanderlust.chattingrobot_mvp.model.beans.TextBean;
import com.wanderlust.chattingrobot_mvp.model.implement.ListModelImpl;
import com.wanderlust.chattingrobot_mvp.model.implement.StoreModelImpl;
import com.wanderlust.chattingrobot_mvp.presenter.ModelCallback;
import com.wanderlust.chattingrobot_mvp.presenter.IChatPresenter;
import com.wanderlust.chattingrobot_mvp.view.IChatView;

import java.util.List;

public class ChatPresenterImpl implements IChatPresenter {

    private INetworkModel mNetworkModel;  //Model层 网络请求
    private IStoreModel   mStoreModel;    //Model层 数据存储
    private IListModel    mListModel;     //Model层 列表管理
    private IChatView     mView;          //view层

    public ChatPresenterImpl(IChatView view) {
        this.mView = view;
        this.mListModel = new ListModelImpl(modelCallback);
        this.mNetworkModel = new NetworkModelImpl(modelCallback);
        this.mStoreModel = new StoreModelImpl(modelCallback);
    }

    @Override
    public List<TextBean> getList() {
        return mListModel.getList();
    }

    @Override
    public void setList(List<TextBean> list) {
        mListModel.setList(list);
    }

    @Override
    public void clearList() {
        mListModel.clearList();
        mStoreModel.deleteAll();
    }

    @Override
    public void addToList(String text, int type) {
        mListModel.add(text, type);
    }

    @Override
    public void save(String text, int type) {
        mStoreModel.save(text, type);
    }

    @Override
    public void requestSendMessage(String msg) {
        mNetworkModel.executeSendRequest(msg);
    }

    @Override
    public void loadAll() {
        mStoreModel.loadAll();
    }

    private ModelCallback modelCallback = new ModelCallback() {
        @Override
        public void onReceiveMessage(String text) {
            mView.showReceivedMessage(text);
        }

        @Override
        public void onReceiveIntent(String url) {
            mView.intent(url);
        }

        @Override
        public void onListDataChange() {
            mView.refreshList();
        }

        @Override
        public void onFail(String text) {
            mView.showToast(text);
        }

        @Override
        public void onLoad(List<TextBean> list) {
            mView.initList(list);
        }
    };

}
