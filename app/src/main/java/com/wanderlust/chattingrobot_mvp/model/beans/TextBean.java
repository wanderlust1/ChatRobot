package com.wanderlust.chattingrobot_mvp.model.beans;

import org.litepal.crud.LitePalSupport;

public class TextBean extends LitePalSupport {

    public static final int TYPE_SEND    = 0;
    public static final int TYPE_RECEIVE = 1;

    private String text;
    private int type;

    public TextBean(String text, int type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public int getType() {
        return type;
    }

}
