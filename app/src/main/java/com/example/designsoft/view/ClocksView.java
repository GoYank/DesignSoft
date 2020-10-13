package com.example.designsoft.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.designsoft.R;

import java.util.Calendar;

/**
 * author : Gyk
 * time   : 2020/10/10
 * todo   :时钟绘制
 */
public class ClocksView extends View {

    private int clockWidth;
    private int clockHeight;
    private Paint mClockPaint;
    private Paint longCalibrationPaint;//长刻度
    private Paint shortCalibrationPaint;//短刻度

    private Paint hourPaint;//时针画笔
    private Paint minutesPaint;//分针画笔
    private Paint millisecondPaint;//秒针画笔
    private Paint timeTextPaint;//时间刻度画笔
    private Handler handler = new Handler();


    public ClocksView(Context context) {
        super(context);
    }

    public ClocksView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mClockPaint = new Paint();
        mClockPaint.setColor(Color.BLACK);
        mClockPaint.setStrokeWidth(5);
        mClockPaint.setStyle(Paint.Style.STROKE);

        longCalibrationPaint = new Paint();
        longCalibrationPaint.setColor(Color.BLACK);
        longCalibrationPaint.setStrokeWidth(5);

        shortCalibrationPaint = new Paint();
        shortCalibrationPaint.setColor(Color.BLACK);
        shortCalibrationPaint.setStrokeWidth(2);

        hourPaint = new Paint();
        hourPaint.setColor(Color.BLACK);
        hourPaint.setStrokeWidth(10);

        minutesPaint = new Paint();
        minutesPaint.setColor(Color.BLACK);
        minutesPaint.setStrokeWidth(7);

        millisecondPaint = new Paint();
        millisecondPaint.setColor(Color.BLACK);
        millisecondPaint.setStrokeWidth(5);

        timeTextPaint = new Paint();
        timeTextPaint.setColor(Color.BLACK);
        timeTextPaint.setTextSize(30);
        timeTextPaint.setTextAlign(Paint.Align.CENTER);
        timeTextPaint.setFakeBoldText(true);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(runnable, 1000);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawClock(canvas);
    }


    private void drawClock(Canvas canvas) {
        clockWidth = getMeasuredWidth();
        clockHeight = getMeasuredHeight();


        canvas.drawCircle(clockWidth / 2, clockHeight / 2, clockWidth / 2 - 5, mClockPaint);
        canvas.drawLine(clockWidth / 2, 5, clockWidth / 2, 50, longCalibrationPaint);
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(clockWidth / 2, 5, clockWidth / 2, 50, longCalibrationPaint);
                if (i==0){
                    canvas.drawText("12",clockWidth/2,80,timeTextPaint);
                }else {
                    canvas.drawText(i/5+"",clockWidth/2,80,timeTextPaint);
                }

            } else {
                canvas.drawLine(clockWidth / 2, 5, clockWidth / 2, 30, longCalibrationPaint);
            }
            canvas.rotate(6, clockWidth / 2, clockHeight / 2);
        }


//        for (int i = 0; i < 12; i++) {
//            String degree = (i + 1) + "";
//            float[] temp = calculatePoint((i + 1) * 30, clockWidth/2 - timeTextPaint.getTextSize() / 2 - 30f - 10);
//            Log.e("ClocksView","point--->"+temp[2]+"point3--->"+temp[3]);
//            canvas.drawText(degree, temp[2],temp[3] + timeTextPaint.getTextSize() / 2, timeTextPaint);
//        }
        Calendar calendar = Calendar.getInstance();

        //绘制时针
        canvas.save();
        canvas.rotate(30 * calendar.get(Calendar.HOUR_OF_DAY), clockWidth / 2, clockHeight / 2);
        canvas.drawLine(clockWidth / 2, clockHeight / 2 + 20, clockWidth / 2, clockHeight / 2 - 100, hourPaint);
        canvas.restore();

        //绘制分针
        canvas.save();
        canvas.rotate(6 * calendar.get(Calendar.MINUTE), clockWidth / 2, clockHeight / 2);
        canvas.drawLine(clockWidth / 2, clockHeight / 2 + 30, clockWidth / 2, clockHeight / 2 - 140, minutesPaint);
        canvas.restore();

        //绘制秒针
        canvas.save();
        canvas.rotate(6 * calendar.get(Calendar.SECOND), clockWidth / 2, clockHeight / 2);
        canvas.drawLine(clockWidth / 2, clockHeight / 2 + 30, clockWidth / 2, clockHeight / 2 - 150, millisecondPaint);
        canvas.restore();
    }

    //计算线段的起始坐标
    private float[] calculatePoint(float angle,float length){
        float[] points = new float[4];
        if(angle <= 90f){
            points[0] = -(float) Math.sin(angle*Math.PI/180) * 30f;
            points[1] = (float) Math.cos(angle*Math.PI/180) * 30f;
            points[2] = (float) Math.sin(angle*Math.PI/180) * length;
            points[3] = -(float) Math.cos(angle*Math.PI/180) * length;
        }else if(angle <= 180f){
            points[0] = -(float) Math.cos((angle-90)*Math.PI/180) * 50f;
            points[1] = -(float) Math.sin((angle-90)*Math.PI/180) * 50f;
            points[2] = (float) Math.cos((angle-90)*Math.PI/180) * length;
            points[3] = (float) Math.sin((angle-90)*Math.PI/180) * length;
        }else if(angle <= 270f){
            points[0] = (float) Math.sin((angle-180)*Math.PI/180) * 50f;
            points[1] = -(float) Math.cos((angle-180)*Math.PI/180) * 50f;
            points[2] = -(float) Math.sin((angle-180)*Math.PI/180) * length;
            points[3] = (float) Math.cos((angle-180)*Math.PI/180) * length;
        }else if(angle <= 360f){
            points[0] = (float) Math.cos((angle-270)*Math.PI/180) * 50f;
            points[1] = (float) Math.sin((angle-270)*Math.PI/180) * 50f;
            points[2] = -(float) Math.cos((angle-270)*Math.PI/180) * length;
            points[3] = -(float) Math.sin((angle-270)*Math.PI/180) * length;
        }
        return points;
    }
    private int measureWidth(int measureWidth) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureWidth);
        int specSize = MeasureSpec.getSize(measureWidth);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int measureHeight) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureHeight);
        int specSize = MeasureSpec.getSize(measureHeight);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
}
