package com.example.moonandsun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard extends View {

    private Point sprite1;
    private Rect sprite1Bounds = new Rect(0,0,0,0);
    private Bitmap bm1 = null;
    private Paint p;
    private List<Point> starField = null;
    private int starAlpha = 80;
    private int starFade = 2;
    private static final int NUM_OF_STARS = 25;

    synchronized public int getSprite1X() {
        return sprite1.x;
    }
    synchronized public int getSprite1Y() {
        return sprite1.y;
    }
    synchronized public int getSprite1Height() {
        return sprite1Bounds.height();
    }

    synchronized public void setSprite1(int x, int y) {
        sprite1=new Point(x,y);
    }
    synchronized public void setimageName(int x) {
        bm1 = BitmapFactory.decodeResource(getResources(), x);
    }

    synchronized public void setBackground(int x,int y, int z) {
        p.setARGB(0,x,y,z);
    }

    synchronized public int getSprite1Width() {
        return sprite1Bounds.width();
    }

    public GameBoard(Context context, AttributeSet aSet) {
        super(context, aSet);
        p = new Paint();
        sprite1 = new Point(-260,560);
        bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.moon);
        sprite1Bounds = new Rect(0,0, bm1.getWidth(), bm1.getHeight());
    }

    synchronized private void initializeStars(int maxX, int maxY) {
        starField = new ArrayList<Point>();
        for (int i=0; i<NUM_OF_STARS; i++) {
            Random r = new Random();
            int x = r.nextInt(maxX-5+1)+5;
            int y = r.nextInt(maxY-5+1)+5;
            starField.add(new Point (x,y));
        }
    }

    @Override
    synchronized public void onDraw(Canvas canvas) {

//        p.setColor(Color.BLACK);
//        p.setARGB(0,3,211,252);
//        p.setARGB(0,0,0,0);
        p.setAlpha(255);
        p.setStrokeWidth(1);
        canvas.drawRect(0, 0, getWidth(), getHeight(), p);

        if (starField==null) {
            initializeStars(canvas.getWidth(), canvas.getHeight());
        }

        p.setColor(Color.CYAN);
        p.setAlpha(starAlpha+=starFade);
        if (starAlpha>=252 || starAlpha <=80) starFade=starFade*-1;
        p.setStrokeWidth(5);
        for (int i=0; i<NUM_OF_STARS; i++) {
            canvas.drawPoint(starField.get(i).x, starField.get(i).y, p);
        }

        canvas.drawBitmap(bm1, sprite1.x, sprite1.y, null);
    }

}
