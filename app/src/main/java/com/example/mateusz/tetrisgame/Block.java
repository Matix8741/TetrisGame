package com.example.mateusz.tetrisgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mateusz on 2017-05-09.
 */

public class Block extends View {

    public Block(Context context) {
        super(context);
    }
    public Block(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setARGB(255,0,80,700);
        canvas.drawRect(new Rect(3, 3, 40, 40),paint);
    }
}
