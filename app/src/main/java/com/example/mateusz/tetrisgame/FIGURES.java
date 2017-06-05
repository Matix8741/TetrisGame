package com.example.mateusz.tetrisgame;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Mateusz on 2017-05-09.
 */

public enum FIGURES {
    LINE,
    SQUARE,
    SIMILAR_TO_T,
    SNAKE,
    SHIP;


    public static boolean[][] getArray(FIGURES figures){
        boolean[][] result = null;
        switch (figures){
            case LINE:
                result = new boolean[][]{{true, true, true, true},
                        {false, false, false, false},
                        {false, false, false, false},
                        {false, false, false, false}};
                break;
            case SQUARE:
                result = new boolean[][]{{false, true, true, false},
                        {false, true, true, false},
                        {false, false, false, false},
                        {false, false, false, false}};
                break;
            case SIMILAR_TO_T:
                result = new boolean[][]{{false, true, true, false},
                        {false, true, false, false},
                        {false, true, false, false},
                        {false, false, false, false}};
                break;
            case SHIP:
                result = new boolean[][]{{false, true, false, false},
                        {true, true, true, false},
                        {false, false, false, false},
                        {false, false, false, false}};
                break;
            case SNAKE:
                result = new boolean[][]{{false, false, true, false},
                        {false, true, true, false},
                        {false, true, false, false},
                        {false, false, false, false}};
                break;
        }
        return result;
    }
    public static void setTetrisRects(FIGURES figures, int SIZE, CopyOnWriteArrayList<TetrisRect> rects, int start){
        boolean[][] blocks = FIGURES.getArray(figures);

        Rect rect = null;
        switch (figures){
            case LINE:
                for(int i = 0; i < blocks.length; i++){
                    for(int j =0; j < blocks.length; j++){
                        if(blocks[i][j]){
                            rects.add(new TetrisRect(new Rect(3+SIZE*(j+start), 3+SIZE*(i), SIZE*(j+1+start), SIZE*(1+i)),255,0, 0, 255));
                        }
                    }
                }
                break;
            case SIMILAR_TO_T:
                for(int i = 0; i < blocks.length; i++){
                    for(int j =0; j < blocks.length; j++){
                        if(blocks[i][j]){
                            rects.add(new TetrisRect(new Rect(3+SIZE*(j+start), 3+SIZE*(i), SIZE*(j+1+start), SIZE*(1+i)),255,0, 255, 0));
                        }
                    }
                }
                break;
            case SHIP:
                for(int i = 0; i < blocks.length; i++){
                    for(int j =0; j < blocks.length; j++){
                        if(blocks[i][j]){
                            rects.add(new TetrisRect(new Rect(3+SIZE*(j+start), 3+SIZE*(i), SIZE*(j+1+start), SIZE*(1+i)),255,255, 0, 0));
                        }
                    }
                }
                break;
            case SNAKE:
                for(int i = 0; i < blocks.length; i++){
                    for(int j =0; j < blocks.length; j++){
                        if(blocks[i][j]){
                            rects.add(new TetrisRect(new Rect(3+SIZE*(j+start), 3+SIZE*(i), SIZE*(j+1+start), SIZE*(1+i)),255,255, 255, 0));
                        }
                    }
                }
                break;
            case SQUARE:
                for(int i = 0; i < blocks.length; i++){
                    for(int j =0; j < blocks.length; j++){
                        if(blocks[i][j]){
                            rects.add(new TetrisRect(new Rect(3+SIZE*(j+start), 3+SIZE*(i), SIZE*(j+1+start), SIZE*(1+i)),255,255, 0, 102));
                        }
                    }
                }
                break;
        }
    }
    static public FIGURES getFigures(int i){
        switch (i){
            case 0:
                return LINE;
            case 1:
                return SHIP;
            case 2:
                return SIMILAR_TO_T;
            case 3:
                return SNAKE;
            case 4:
                return SQUARE;
            default:
                return LINE;
        }
    }
}
