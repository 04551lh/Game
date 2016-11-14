package com.feicui.android.game.Base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import com.feicui.android.game.GameBitmapType;

/**
 * Created by Administrator on 2016/11/9.
 *
 */
public class ButtonDraw extends GameBitmapType {

    private int speed;
    private RectF rectF;
    public ButtonDraw(Bitmap[] bit, int x, int y, int speed) {
        super(bit, x, y);
        this.speed = speed;
    }

    @Override
    public void upData() {
        super.upData();
        y += speed;
        rectF = new RectF();
        rectF.top = y;
        rectF.right = weight + x;
        rectF.bottom = y + height;
        rectF.left = x;
    }

    public  boolean onClick(float x, float y){
        return rectF.contains(x, y);
    }

}
