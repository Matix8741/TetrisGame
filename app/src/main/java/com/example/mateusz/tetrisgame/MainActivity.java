package com.example.mateusz.tetrisgame;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.Random;

public class MainActivity extends Activity {

    private Random random = new Random();

    public Board addView;
    private ViewGroup insertPoint;

    public MainActivity getOwn(){ return this; }
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_main);
        insertPoint = (ViewGroup) findViewById(R.id.MainActivity);
        Board whiled = null;
        if(savedInstanceState!=null){
             whiled = (Board) findViewById(R.id.BoardGame);
            addView = (Board) savedInstanceState.getSerializable("Board");
        }else {
            addView = (Board) findViewById(R.id.BoardGame);
        }
        final Board finalWhiled = whiled;
        insertPoint.post(new Runnable() {
            @Override
            public void run() {
                Log.d("addView", String.valueOf(addView));
                if(finalWhiled != null){
                    insertPoint.removeView(finalWhiled);
                    insertPoint.addView(addView);
                }
                addView.setScreenSize(insertPoint.getWidth(), insertPoint.getHeight());
            }
        });
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        addView.bringToFront();
                        addView.startAnimation();
                    }
                });
                while (addView.addFigures(FIGURES.getFigures(random.nextInt(5)))){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while(addView.currentFigureOnDown()){
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if(addView.ifLineisOver()){
                    }
                }
                addView.stopAnimation();
            }
        });
        insertPoint.post(new Runnable() {
            @Override
            public void run() {
                thread.start();
            }
        });
    }

    ViewGroup getProParent(ViewGroup viewGroup){
        if(viewGroup.getParent()!=null)
            return getProParent((ViewGroup)viewGroup.getParent());
        else
            return (ViewGroup) viewGroup.getParent();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        insertPoint.removeView(addView);
        outState.putSerializable("Board", addView);
    }

    public void Clicker(View view) {
        addView.currentFigureOnRight();
    }

    public void Left(View view) {
        addView.currentFigureOnLeft();
    }

    public void Turn(View view) {
        addView.currentFigureTurn();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        addView.stopAnimation();
    }
}
