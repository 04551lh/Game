package com.feicui.android.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.View;

import com.feicui.android.game.Base.ButtonDraw;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Administrator on 2016/11/8.
 *
 */
public class GameView extends View implements Runnable{
    private int state;
    public static final int STATE_READLY = 0x000;
    public static final int STATE_GO = 0x001;
    public static final int STATE_START = 0x002;
    private int index, indexl;
    //背景
    private Bitmap back;
    private Bitmap key;
    private Bitmap music;
    private Bitmap line;
    private Bitmap button;
    private Bitmap acc;
    private Bitmap pre;
    private Bitmap miss;
    private Bitmap[] bit_frt = new Bitmap[3];
    private Bitmap[] bit_line = new Bitmap[2];
    private Bitmap[] bit_button = new Bitmap[2];
    private Bitmap[] num = new Bitmap[10];
    private Bitmap  ready, go;

    private GameBitmapType[] types = new GameBitmapType[2];

    private Resources resources;
    private float height, width;
    private  int x, y;
    private float touch_x, touch_y;
    private int but_Acton = -1;

    private boolean isOK;
    private boolean isMiss;
    private boolean broup;
    private boolean success = true;
    private Bitmap prefect;
    private Bitmap over;
    private  int grade;
    public static final int grap = 2;
    private int slip;

    private int[] but_x = new int[4];
    private ArrayList<ButtonDraw> list = new ArrayList<>();

    public GameView(Context context) {
        super(context);
        initBackgroup();
        new Thread(this).start();
    }

    private void initBackgroup(){
        resources = getResources();
        back = BitmapFactory.decodeResource(resources, R.drawable.back);
        ready = BitmapFactory.decodeResource(resources, R.mipmap.ready);
        go = BitmapFactory.decodeResource(resources, R.mipmap.go);
        key = BitmapFactory.decodeResource(resources, R.mipmap.key);
        music = BitmapFactory.decodeResource(resources, R.mipmap.frt);
        line = BitmapFactory.decodeResource(resources, R.mipmap.line);
        button = BitmapFactory.decodeResource(resources, R.mipmap.button);
        acc = BitmapFactory.decodeResource(resources, R.mipmap.acc);
        pre = BitmapFactory.decodeResource(resources, R.mipmap.pre);
        prefect = BitmapFactory.decodeResource(resources, R.mipmap.perfect);
        miss = BitmapFactory.decodeResource(resources, R.mipmap.miss);
        over = BitmapFactory.decodeResource(resources, R.mipmap.over);
        for (int i = 0; i < bit_frt.length; i++) {
            bit_frt[i] = Bitmap.createBitmap(music, 0, music.getHeight()/3 * i, music.getWidth(), music.getHeight()/3);
        }
        for (int i = 0; i < bit_line.length; i++) {
            bit_line[i] = Bitmap.createBitmap(line, i * line.getWidth() / 2, 0, line.getWidth() / 2, line.getHeight());
        }
        for (int i = 0; i < bit_button.length; i++) {
            bit_button[i] = Bitmap.createBitmap(button, i *  button.getWidth() / 2, 0, button.getWidth() / 2, button.getHeight());
        }
        for (int i = 0; i < 10; i++) {
           int id = getResources().getIdentifier("n" + i, "mipmap", getContext().getPackageName());
            num[i] = BitmapFactory.decodeResource(resources, id);
        }

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        float b_width = width / back.getWidth();
        float b_height = height / back.getHeight();
        Bitmap backgroup =  Bitmap.createBitmap(back, 0 , 0, back.getWidth(), back.getHeight(), matrix, true);
        matrix.postScale(b_height, b_width);
        canvas.drawBitmap(backgroup, 0, 0, null);
        initBitmap(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        state = STATE_GO;
        postInvalidate();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        state = STATE_START;
        postInvalidate();
        while (success){
            updata();
            onTouch();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            postInvalidate();
        }

    }

    private void initBitmap(Canvas canvas){
        switch (state){
            case STATE_GO:
                y = (int) ((height - go.getHeight()) / 2);
                x = (int) ((width - go.getWidth()) / 2);
                canvas.drawBitmap(go, x, y, null);
                break;
            case STATE_READLY:
                y = (int) ((height - ready.getHeight()) / 2);
                x = (int) ((width - ready.getWidth()) / 2);
                canvas.drawBitmap(ready, x, y, null);
                break;
            case STATE_START:
                y = (int) (height - key.getHeight());
                x = (int) ((width - key.getWidth()) / 2);
                canvas.drawBitmap(bit_frt[index], 0, height - bit_frt[0].getHeight(), null);
                canvas.drawBitmap(bit_frt[index], width - bit_frt[0].getWidth(), height - bit_frt[0].getHeight(), null);
                canvas.drawBitmap(key, x, y, null);
                canvas.drawBitmap(bit_line[indexl], x+key.getWidth()/4-bit_line[0].getWidth()/2, y, null);
                canvas.drawBitmap(bit_line[indexl], x+key.getWidth()/2 - bit_line[0].getWidth()/2, y, null);
                canvas.drawBitmap(bit_line[indexl], x+key.getWidth()/4*3 - bit_line[0].getWidth()/2, y, null);
                but_x[0] = (int) ((width - key.getWidth()) / 2);
                but_x[1] = x+key.getWidth()/4;
                but_x[2] = x+key.getWidth()/2;
                but_x[3] = x+key.getWidth()/4*3;
               if(list.size() > 0){
                   for (int i = 0; i < list.size(); i++) {
                       list.get(i).onDraw(canvas);
                   }
               }
                if(isOK){
                   if(touch_x >= but_x[0]){
                       x = but_x[0];
                   }
                    if(touch_x >= but_x[1]){
                        x = but_x[1];
                    }
                    if(touch_x >= but_x[2]){
                        x = but_x[2];
                    }
                    if(touch_x >= but_x[3]){
                        x = but_x[3];
                    }
                    canvas.drawBitmap(acc , x, touch_y-acc.getHeight()-bit_button[0].getHeight(), null);
                    canvas.drawBitmap(prefect, (width - prefect.getWidth())/2, (height - prefect.getHeight())/2, null );
                    isOK = false;
                }
                if(isMiss){
                    canvas.drawBitmap(miss, (width - miss.getWidth())/2, (height - miss.getHeight())/2, null);
                    isMiss = false;
                }
                if(broup){
                    if(touch_x >= but_x[0]){
                        x = but_x[0];
                    }
                    if(touch_x >= but_x[1]){
                        x = but_x[1];
                    }
                    if(touch_x >= but_x[2]){
                        x = but_x[2];
                    }
                    if(touch_x >= but_x[3]){
                        x = but_x[3];
                    }
                    canvas.drawBitmap(pre, x, y, null);
                    broup = false;
                }

                if(grade < 1000){
                    int g = grade%10;
                    int s = grade/10%10;
                    int b = grade/100%10;
                    int q = grade/1000%10;
                    canvas.drawBitmap(num[q], 2*num[0].getWidth(), 3*num[0].getWidth(), null);
                    canvas.drawBitmap(num[b], 3*num[0].getWidth(), 3*num[0].getWidth(), null );
                    canvas.drawBitmap(num[s], 4*num[0].getWidth(), 3*num[0].getWidth(), null);
                    canvas.drawBitmap(num[g], 5*num[0].getWidth(), 3*num[0].getWidth(), null);

                }
                else{
                    canvas.drawBitmap(over, (width - over.getWidth())/2, (height - over.getHeight())/2, null);
                    success = false;
                }
                break;
        }
    }

    private void updata(){
        if(index >= bit_frt.length - 1){
            index = 0;
        }
        index ++;
        if(indexl >= bit_line.length - 1){
            indexl = 0;
        }
        indexl ++;
        if(list.size() == 0){
            if(but_x[0]> 0){
                list.add(new ButtonDraw(bit_button, but_x[0],  y, 1));
            }
        }
        else{
            Random random = new Random();
            int k = random.nextInt(4);
            if(list.get(list.size() - 1).y > 400){
                list.add(new ButtonDraw(bit_button, but_x[k],  y, 1));
            }
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).upData();
            if(list.get(i).y > height + list.get(i).height){
                list.remove(i);
                slip = 0;
                isMiss = true;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        but_Acton = event.getAction();
        touch_x =  event.getX();
        touch_y = event.getY();
        return true;
    }

    private void onTouch(){
        switch (but_Acton){
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < list.size(); i++) {
                    if(touch_y > height - key.getHeight() + line.getHeight()
                            && touch_x > (width - key.getWidth())/2
                            && touch_y<height
                            && touch_x < width/2 + key.getWidth()/2){

                        if(list.get(i).onClick(touch_x, touch_y)){
                            list.remove(i);
                            grade = grade + slip + grap;
                            slip ++;
                            isOK = true;
                        }
                        broup = true;
                    }
                }
                break;
        }
    }

}
