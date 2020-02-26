package com.example.moonandsun;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int FRAME_RATE = 20;
    private Point sprite1Velocity;
    private int sprite1MaxX;
    private int sprite1MaxY;
    String DayNight = "Night";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                initGfx();
            }
        }, 1000);
    }

    private Point getRandomVelocity() {
        Random r = new Random();
        int min = 1;
        int max = 5;
        int x = r.nextInt(max-min+1)+min;
        int y = r.nextInt(max-min+1)+min;
        return new Point (x,y);
    }

    synchronized public void initGfx() {

        sprite1MaxX = findViewById(R.id.the_canvas).getWidth() - ((GameBoard)findViewById(R.id.the_canvas)).getSprite1Width();
        sprite1MaxY = findViewById(R.id.the_canvas).getHeight() - ((GameBoard)findViewById(R.id.the_canvas)).getSprite1Height();

        sprite1Velocity = getRandomVelocity();

        frame.removeCallbacks(frameUpdate);
        ((GameBoard)findViewById(R.id.the_canvas)).invalidate();
        frame.postDelayed(frameUpdate, FRAME_RATE);
    }

    private Handler frame = new Handler();
    private Runnable frameUpdate = new Runnable() {
        @Override
        synchronized public void run() {

            Point sprite1 = new Point (((GameBoard)findViewById(R.id.the_canvas)).getSprite1X(),
                    ((GameBoard)findViewById(R.id.the_canvas)).getSprite1Y()) ;

            if (DayNight == "Day") {
                if(sprite1.x >= 900){
                    ((GameBoard) findViewById(R.id.the_canvas)).setBackground(0,0,0);
                }else {
                    ((GameBoard) findViewById(R.id.the_canvas)).setBackground(9, 130, 217);
                }
            }else{
                if(sprite1.x >= 900){
                    ((GameBoard) findViewById(R.id.the_canvas)).setBackground(9,130,217);
                }else {
                    ((GameBoard) findViewById(R.id.the_canvas)).setBackground(0, 0, 0);
                }
            }

            ((TextView) findViewById(R.id.the_other_label)).setText("View: "+DayNight);
            if (sprite1.x >= 1000) {
                    switch(DayNight){
                        case "Day":
                            ((GameBoard)findViewById(R.id.the_canvas)).setimageName(R.drawable.moon);
                            DayNight = "Night";
                            break;
                        case "Night":
                            ((GameBoard)findViewById(R.id.the_canvas)).setimageName(R.drawable.sun);
                            DayNight = "Day";
                            break;
                            default:
                                break;
                    }
                sprite1.x = -260;
                     initGfx();
            }

            frame.removeCallbacks(frameUpdate);

            sprite1.x = sprite1.x + 4;
            sprite1.y = sprite1.y + 2;

            ((GameBoard) findViewById(R.id.the_canvas)).setSprite1(sprite1.x, 560);
            ((GameBoard) findViewById(R.id.the_canvas)).invalidate();
            frame.postDelayed(frameUpdate, FRAME_RATE);
        }
    };
}
