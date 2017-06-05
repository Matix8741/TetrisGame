package com.example.mateusz.tetrisgame;

/**
 * Created by Mateusz on 2017-05-14.
 */
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class GridLineView {

    private static final int DEFAULT_PAINT_COLOR = Color.BLACK;
    private static final int DEFAULT_NUMBER_OF_ROWS = 20;
    private static final int DEFAULT_NUMBER_OF_COLUMNS = 10;

    private boolean showGrid = true;
    private final Paint paint = new Paint();
    public Paint getPaint() { return paint; }
    private int numRows = DEFAULT_NUMBER_OF_ROWS, numColumns = DEFAULT_NUMBER_OF_COLUMNS;

    public GridLineView(Context context) {
        init();
    }

    public GridLineView(Context context, AttributeSet attrs) {
        init();
    }

    public GridLineView(Context context, AttributeSet attrs, int defStyle) {
        init();
    }

    private void init() {
        paint.setColor(DEFAULT_PAINT_COLOR);
    }

    public void setLineColor(int color) {
        paint.setColor(color);
    }

    public void setStrokeWidth(int pixels) {
        paint.setStrokeWidth(pixels);
    }

    public int getNumberOfRows() {
        return numRows;
    }

    public void setNumberOfRows(int numRows) {
        if (numRows > 0) {
            this.numRows = numRows;
        } else {
            throw new RuntimeException("Cannot have a negative number of rows");
        }
    }

    public int getNumberOfColumns() {
        return numColumns;
    }

    public void setNumberOfColumns(int numColumns) {
        if (numColumns > 0) {
            this.numColumns = numColumns;
        } else {
            throw new RuntimeException("Cannot have a negative number of columns");
        }
    }

    public boolean isGridShown() {
        return showGrid;
    }

}

