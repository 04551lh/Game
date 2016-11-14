package com.feicui.android.game.Base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by Administrator on 2016/11/8.
 */
public  abstract class GameBase {
    public Bitmap[] bit;
    public int x, y;
    public int index;
    public int weight, height;
    public long time;

    public GameBase(Bitmap[] bit, int x, int y){
        this.bit = bit;
        this.x = x;
        this.y = y;
        this.weight = bit[0].getWidth();
        this.height = bit[0].getHeight();
    }
    public abstract void onDraw(Canvas canvas);
    public abstract void upData();
}
