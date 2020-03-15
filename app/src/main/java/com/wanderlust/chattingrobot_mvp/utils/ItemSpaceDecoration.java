package com.wanderlust.chattingrobot_mvp.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 用于设置 RecyclerView 的 item 之间的边距。
 */

public class ItemSpaceDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public ItemSpaceDecoration(int space) {
        this.space = space;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.bottom = space;

        //只在第一个项目上添加顶边距，以避免item之间的重复边距
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }

}