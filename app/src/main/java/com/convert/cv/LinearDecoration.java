package com.convert.cv;


import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class LinearDecoration extends RecyclerView.ItemDecoration {

    private final int[] label;
    private float dp;

    LinearDecoration(int[] label, float dp) {
        this.label = label;
        this.dp = dp;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int pos = parent.getChildAdapterPosition(view);

        int px1 = (int)Math.ceil(1.6 * dp);
        int px2 = (int)Math.ceil(16.6 * dp);



        if(label == null) return;

        for(int n : label)
            if(n == pos){
               if(pos == 0) outRect.set(0, 0, 0, px1);
               else outRect.set(0, px2, 0, px1);
            }

    }
}
