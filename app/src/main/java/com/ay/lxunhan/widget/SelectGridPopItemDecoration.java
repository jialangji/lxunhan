package com.ay.lxunhan.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SelectGridPopItemDecoration extends RecyclerView.ItemDecoration {

    private int left;
    private int right;
    private int middle;
    private int top;

    //leftRight为横向间的距离 topBottom为纵向间距离
    public SelectGridPopItemDecoration(int left, int right, int middle, int top) {
        this.left = left;
        this.right = right;
        this.middle = middle;
        this.top = top;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        if (null != layoutManager && layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            if (parent.getChildAdapterPosition(view) % 3 == 0) {
                //右侧一列
                outRect.right = right;
                outRect.left = middle;
            } else if (parent.getChildAdapterPosition(view) % 3 == 2) {
                //中间一列
                outRect.left = middle;
                outRect.right = middle;
            } else {
                outRect.left = left;
                outRect.right = middle;
            }
            outRect.top = top;
        }
    }
}


