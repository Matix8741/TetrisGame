package com.example.mateusz.tetrisgame;

import android.graphics.Rect;
import android.widget.TextView;

/**
 * Created by Mateusz on 2017-05-11.
 */

public class TetrisRect {
    public int a, r, b, y;
    public Rect rect;

    public TetrisRect(Rect rect, int a, int r, int b, int y){
        this.rect = rect;
        this.a = a;
        this.r = r;
        this.b = b;
        this.y = y;
    }
}
