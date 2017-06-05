package com.example.mateusz.tetrisgame;

/**
 * Created by Mateusz on 2017-05-10.
 */

public class Pair {
    private int i;
    private int j;

    public Pair(int i, int j){
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }
}
