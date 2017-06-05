package com.example.mateusz.tetrisgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Mateusz on 2017-05-11.
 */

public class Board extends View implements Serializable {
    long mAnimStartTime;
    final int N = 20;
    final int M = 11;
    final int start = 4;
    int SIZE;

    GridLineView lineView;
    int screenX;
    int screenY;
    public void setScreenSize(int x, int y){
        screenX = x;
        screenY = y;
        if(x < y)SIZE = ( x - 3*(M-1))/(M-1);
        else SIZE = ( y - 3*N)/N;
    }
    CopyOnWriteArrayList<TetrisRect> rectArrayList = new CopyOnWriteArrayList<>();
    Paint paint = new Paint();

    Handler mHandler = new Handler();
    Runnable mTick = new Runnable() {
        public void run() {
            invalidate();
            mHandler.postDelayed(this, 20);
        }
    };
    private boolean isCurrentFigure = false;
    private boolean CanDraw = false;

    public Board(Context context) {
        super(context);
        lineView = new GridLineView(context);
    }

    public Board(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        lineView = new GridLineView(context);
    }
    @Override
    public void onDraw(Canvas canvas){
        if(CanDraw) {
            for (int i = 1; i <= lineView.getNumberOfColumns()+1; i++) {
                    canvas.drawLine((float) ((SIZE) * i), 0, (float) ((SIZE) * i), SIZE*N+3, lineView.getPaint());
            }
            for (int i = 0; i <= lineView.getNumberOfRows(); i++) {
                canvas.drawLine(SIZE, SIZE* i, SIZE*M-3, SIZE*i, lineView.getPaint());
            }
            for (TetrisRect rect : rectArrayList) {
                paint.setARGB(rect.a, rect.r, rect.b, rect.y);
                canvas.drawRect(rect.rect, paint);
            }
        }
    }

    public boolean currentFigureOnDown(){
        if(isCurrentFigure&&isCanGoDown()) {
            for (int i = rectArrayList.size() - 4; i < rectArrayList.size(); i++) {
                Rect rect = rectArrayList.get(i).rect;
                rect.bottom += SIZE;
                rect.top += SIZE;
            }
            return true;
        }
        isCurrentFigure = false;
        return false;
    }

    public boolean currentFigureOnRight(){
        if(isCurrentFigure&&isCanGoRight()) {
            for (int i = rectArrayList.size() - 4; i < rectArrayList.size(); i++) {
                Rect rect = rectArrayList.get(i).rect;
                rect.left += SIZE;
                rect.right += SIZE;
            }
            return true;
        }
        return false;
    }

    private boolean isFreeStart(FIGURES figures){
        Pair[] ar = new Pair[4];
        int j =0;
        boolean[][] blocks = FIGURES.getArray(figures);
        for(int k =0; k < blocks.length; k++){
            for (int l =0; l < blocks.length; l++){
                if(blocks[k][l]){
                    ar[j] = new Pair(k,l);
                    j++;
                }
            }
        }
        for (int i = rectArrayList.size()-4; i < rectArrayList.size(); i++){
            if(i < 0) return true;
            for(Pair pair : ar){
                Rect rect = rectArrayList.get(i).rect;
            if(rect.top == 3 + SIZE*pair.getJ()){
                if(rect.left == 3 + SIZE*pair.getI()*(start+1))
                    return false;
            }
            }
        }
        return true;
    }

    public boolean addFigures(FIGURES figures){
        if(isFreeStart(figures)){
            FIGURES.setTetrisRects(figures,SIZE,rectArrayList,start);
            isCurrentFigure = true;
            return true;
        }
        return false;
    }

    public boolean isCanGoDown(){
        if(rectArrayList.isEmpty()) return true;
        List<TetrisRect> rects;
        rects = rectArrayList.subList(rectArrayList.size()-4,rectArrayList.size());
        if(rectArrayList.size() < 5) {
            for (TetrisRect rect : rects){
                if((rect.rect.top+SIZE-3)/SIZE >= N)return false;
            }
            return true;
        }
        for(TetrisRect tetrisRect : rectArrayList.subList(0, rectArrayList.size()-3)){
            for (TetrisRect rect : rects){
                if((rect.rect.top+SIZE-3)/SIZE >= N)return false;
                if(rect == tetrisRect){
                    break;
                }
                if(tetrisRect.rect.top == rect.rect.top+SIZE){
                    if(tetrisRect.rect.left == rect.rect.left){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isCanGoRight(){
        if(rectArrayList.isEmpty()) return true;
        List<TetrisRect> rects;
        rects = rectArrayList.subList(rectArrayList.size()-4,rectArrayList.size());
        if(rectArrayList.size() < 5) {
            for (TetrisRect rect : rects){
                if((rect.rect.left+SIZE-3)/SIZE >= M)return false;
            }
            return true;
        }
        for(TetrisRect tetrisRect : rectArrayList.subList(0, rectArrayList.size()-3)){
            for (TetrisRect rect : rects){
                if((rect.rect.left+SIZE-3)/SIZE >= M)return false;
                if(rect == tetrisRect){
                    break;
                }
                if(tetrisRect.rect.top == rect.rect.top){
                    if(tetrisRect.rect.left == rect.rect.left+SIZE){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isCanGoLeft() {
        if(rectArrayList.isEmpty()) return true;
        List<TetrisRect> rects;
        rects = rectArrayList.subList(rectArrayList.size()-4,rectArrayList.size());
        if(rectArrayList.size() < 5) {
            for (TetrisRect rect : rects){
                if((rect.rect.left-SIZE-3) == 0)return false;
            }
            return true;
        }
        for(TetrisRect tetrisRect : rectArrayList.subList(0, rectArrayList.size()-3)){
            for (TetrisRect rect : rects){
                if((rect.rect.left-SIZE-3) == 0)return false;
                if(rect == tetrisRect){
                    break;
                }
                if(tetrisRect.rect.top == rect.rect.top){
                    if(tetrisRect.rect.left == rect.rect.left-SIZE){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void startAnimation() {
        CanDraw = true;
        mAnimStartTime = SystemClock.uptimeMillis();
        mHandler.removeCallbacks(mTick);
        mHandler.post(mTick);
    }

    public boolean ifLineisOver(){
        boolean result = false;
        ArrayList<Integer> tops = new ArrayList<>();
        for(int i = rectArrayList.size() -4; i < rectArrayList.size(); i++){
            boolean ifAdd = true;
            for (Integer in : tops){
                if (rectArrayList.get(i).rect.top == in) {
                    ifAdd = false;
                }
            }
            if(ifAdd) tops.add(rectArrayList.get(i).rect.top);
        }
        for (Integer in : tops){
            ArrayList<TetrisRect> forRemove = new ArrayList<>();
            for(TetrisRect rect : rectArrayList){
                if(rect.rect.top == in.intValue()){
                    forRemove.add(rect);
                }
            }
            if(forRemove.size() >= M-1){
                removeRects(forRemove);
            result = true;
            }
        }
        return result;
    }

    private void removeRects(ArrayList<TetrisRect> forRemove) {
        int top = forRemove.get(0).rect.top;
        for (TetrisRect rect : forRemove){
            rectArrayList.remove(rect);
        }
        for(TetrisRect rect : rectArrayList){
            if(rect.rect.top < top){
                rect.rect.top+=SIZE;
                rect.rect.bottom+=SIZE;
            }
        }
    }

    public void stopAnimation() {
        mHandler.removeCallbacks(mTick);
    }

    public boolean currentFigureOnLeft() {
        if(isCurrentFigure&&isCanGoLeft()) {
            for (int i = rectArrayList.size() - 4; i < rectArrayList.size(); i++) {
                Rect rect = rectArrayList.get(i).rect;
                rect.left -= SIZE;
                rect.right -= SIZE;
            }
            return true;
        }
        return false;

    }

    public boolean currentFigureTurn(){
            Matrix matrix = new Matrix();
            int minTop = Integer.MAX_VALUE, maxBot = 0, maxRight = 0, minLeft = Integer.MAX_VALUE;
            Rect Bot = null, Right = null;
            List<TetrisRect> subRects = rectArrayList.subList(rectArrayList.size() - 4, rectArrayList.size());
            for (TetrisRect rect : subRects) {
                if (rect.rect.top < minTop) {
                    minTop = rect.rect.top;
                }
                if (rect.rect.bottom > maxBot) {
                    maxBot = rect.rect.bottom;
                    Bot = rect.rect;
                }
                if (rect.rect.right > maxRight) {
                    maxRight = rect.rect.right;
                    Right = rect.rect;
                }
                if (rect.rect.left < minLeft) {
                    minLeft = rect.rect.left;
                }
            }
            float OX, OY;
            if (maxBot - minTop + 3 == 3 * SIZE) {
                OY = (float) (Bot.top - 1.5);
            } else if (maxBot - minTop + 3 == SIZE) {
                OY = (float) (Bot.top - 1.5);
            } else
                OY = (float) (minTop + maxBot) / 2;
            if (maxRight - minLeft + 3 == 3 * SIZE) {
                OX = (float) (Right.left - 1.5);
            } else if (maxRight - minLeft + 3 == SIZE) {
                OX = (float) (Right.left - 1.5);
            } else
                OX = (float) (minLeft + maxRight) / 2;
            for (TetrisRect rect : subRects) {
                RectF rectF = new RectF(rect.rect);
                matrix.setRotate(90, OX, OY);
                matrix.mapRect(rectF);
                if((rectF.left-3) <= 0)return false;
                if((rectF.left-3)/SIZE >= M)return false;
                if((rectF.top-3)/SIZE >= N)return false;
                for (TetrisRect rect1 : rectArrayList.subList(0, rectArrayList.size()-4)){
                    if(rectF.top == rect1.rect.top){
                        if(rectF.left == rect1.rect.left){
                            return false;
                        }
                    }
                }
            }
            for (TetrisRect rect : subRects ) {
                RectF rectF = new RectF(rect.rect);
                matrix.setRotate(90, OX, OY);
                matrix.mapRect(rectF);
                rectF.roundOut(rect.rect);
            }
        return true;
    }

}
