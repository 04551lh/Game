package com.feicui.android.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.feicui.android.game.Base.GameBase;

/**
 * Created by Administrator on 2016/11/8.
 */
public class GameBitmapType extends GameBase {

    public GameBitmapType(Bitmap[] bit, int x, int y) {
        super(bit, x, y);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(bit[index], x, y, null);
    }

    @Override
    public void upData() {
        if(time == 0){
            time = System.currentTimeMillis();
        }else if(System.currentTimeMillis() - time >= 200){
            index ++;
            time = 0;
        }
        if(index >= bit.length - 1){
            index = 0;
        }
    }
}
