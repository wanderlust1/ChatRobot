package com.wanderlust.chattingrobot_mvp.model.implement;

import com.wanderlust.chattingrobot_mvp.model.IStoreModel;
import com.wanderlust.chattingrobot_mvp.model.beans.TextBean;
import com.wanderlust.chattingrobot_mvp.presenter.ModelCallback;

import org.litepal.LitePal;

import java.util.List;

import static com.wanderlust.chattingrobot_mvp.model.beans.TextBean.TYPE_RECEIVE;

public class StoreModelImpl implements IStoreModel {

    private ModelCallback mModelCallback;

    public StoreModelImpl(ModelCallback callback) {
        this.mModelCallback = callback;
        LitePal.getDatabase();
    }

    @Override
    public void save(String text, int type) {
        new TextBean(text, type).save();
    }

    @Override
    public void loadAll() {
        List<TextBean> list = LitePal.findAll(TextBean.class);
        if (list.isEmpty()) {
            TextBean bean1 = new TextBean("你好！我是图灵机器人！", TYPE_RECEIVE);
            TextBean bean2 = new TextBean("你好！我是图灵机器人！", TYPE_RECEIVE);
            list.add(bean1);
            list.add(bean2);
            bean1.save();
            bean2.save();
        }
        mModelCallback.onLoad(list);
    }

    @Override
    public void delete(String text, int type) {

    }

    @Override
    public void deleteAll() {
        LitePal.deleteAll(TextBean.class);
    }
}
