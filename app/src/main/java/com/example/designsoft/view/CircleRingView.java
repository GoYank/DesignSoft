package com.example.designsoft.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.designsoft.R;

/**
 * author : Gyk
 * time   : 2020/10/10
 * todo   :圆环绘制
 */
public class CircleRingView extends View {

    private Paint mCircleInnerPaint;//内Paint
    private Paint mCircleOutsidePaint;//外Paint
    private Paint mTextPaint;//文字Paint

    private int mCircleWidth = 200;//圆环width
    private int mCircleHeight = 200;//圆环height
    private int progressText =10;//圆环开始进度
    private float angle = (360/progressText);//圆环开始角度
    private int mCircleInnerPaintColor = Color.BLUE;//圆环颜色
    private int mCircleInnerPaintWidth = 20;//画笔宽度
    private int mCircleTextPaintColor = Color.BLACK;//文字颜色
    private int mCircleTextSize = 30;//文字大小

    private onClickListener onClickListener;

    public void setOnClickListener(CircleRingView.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CircleRingView(Context context) {
        super(context);
    }

    public CircleRingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleRingView);
        mCircleInnerPaintColor = ta.getColor(R.styleable.CircleRingView_circleRingColor, Color.BLUE);
        mCircleInnerPaintWidth = (int)ta.getDimension(R.styleable.CircleRingView_circleRingWidth,10);
        mCircleTextPaintColor = ta.getColor(R.styleable.CircleRingView_circleRingTextColor,Color.BLACK);
        mCircleTextSize = (int) ta.getDimension(R.styleable.CircleRingView_circleRingTextSize,20f);
        progressText = (int) ta.getFloat(R.styleable.CircleRingView_circleRingStartProgress,10f);
        ta.recycle();

        mCircleInnerPaint = new Paint();
        mCircleInnerPaint.setAntiAlias(true);
        mCircleInnerPaint.setColor(mCircleInnerPaintColor);
        mCircleInnerPaint.setStyle(Paint.Style.STROKE);
        mCircleInnerPaint.setStrokeWidth(mCircleInnerPaintWidth);

        mCircleOutsidePaint = new Paint();
        mCircleOutsidePaint.setColor(Color.GREEN);
        mCircleOutsidePaint.setStyle(Paint.Style.STROKE);
        mCircleOutsidePaint.setStrokeWidth(20);

        mTextPaint = new Paint();
        mTextPaint.setColor(mCircleTextPaintColor);
        mTextPaint.setTextSize(mCircleTextSize);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }


    private void drawCircleRing(Canvas canvas) {
        mCircleWidth = getMeasuredWidth();
        mCircleHeight =getMeasuredHeight();
        String str= progressText+"%";
        float textWidth = mTextPaint.measureText(str);//获取文字宽度
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();//文字度量(获取绘制文字的各个属性)
        float baseLineY =(mCircleHeight/2)+ (metrics.bottom-metrics.top)/2 -metrics.bottom;//基线位置
        RectF rectF = new RectF(mCircleInnerPaintWidth, mCircleInnerPaintWidth, mCircleWidth-mCircleInnerPaintWidth, mCircleHeight-mCircleInnerPaintWidth);
        canvas.drawArc(rectF, 110, angle, false, mCircleInnerPaint);
        canvas.drawText(str, ((mCircleWidth / 2)- textWidth/2), baseLineY, mTextPaint);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircleRing(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
                valueAnimator.setDuration(1000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        progressText = (int)((float)valueAnimator.getAnimatedValue()*100);
                        angle = (float) (valueAnimator.getAnimatedValue())*360;
                        invalidate();
                    }
                });
                valueAnimator.start();
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
        }
        return true;
    }

    public interface onClickListener {
        void onClick();
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

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

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

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

    private   int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

}
