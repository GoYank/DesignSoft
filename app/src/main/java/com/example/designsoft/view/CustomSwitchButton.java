package com.example.designsoft.view;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.designsoft.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：CustomSwitchButton
 * 创建者：Gyk
 * 创建时间：2020/4/1 16:41
 * 描述：  自定义动画开关按钮
 */
public class CustomSwitchButton extends RelativeLayout {

    @BindView(R.id.rl_custom)
    RelativeLayout rl_custom;
    @BindView(R.id.img_custom_bg)
    ImageView mImgBg;
    @BindView(R.id.img_custom_bt)
    ImageView mImgBt;


    private int bgWidth;
    private int btWidth;
    private Context context;
    private String TAG = "CustomSwitchButton";
    private static ViewTreeObserver viewTreeObserver;
    private ObjectAnimator objectAnimatorA;

    public void setOnTurnStatusClickListener(CustomSwitchButton.onTurnStatusClickListener onTurnStatusClickListener) {
        this.onTurnStatusClickListener = onTurnStatusClickListener;
    }

    private onTurnStatusClickListener onTurnStatusClickListener;




    public CustomSwitchButton(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public CustomSwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public CustomSwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_button, this);
        ButterKnife.bind(view);
        mImgBg.setTag(R.mipmap.switch_off_bg);
        getBgWidth();
        getBtWidth();
    }

    @OnClick({R.id.rl_custom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_custom:
                if (((int) mImgBg.getTag()) == R.mipmap.switch_off_bg) {
                    if (onTurnStatusClickListener != null) {
                        onTurnStatusClickListener.onTurnOnClick();
                    }
                } else if (((int) mImgBg.getTag() == R.mipmap.switch_open_bg)) {
                    if (onTurnStatusClickListener != null) {
                        onTurnStatusClickListener.onTurnOffClick();
                    }
                }
                break;
        }
    }

    public interface onTurnStatusClickListener {
        void onTurnOnClick();
        void onTurnOffClick();
    }


    public void startOnAnimal() {
        mImgBg.setImageResource(R.mipmap.switch_open_bg);
        //使用Keyframe优化动画性能
        Keyframe k0 = Keyframe.ofFloat(0f, 0);//0ms初始化
        Keyframe k1 = Keyframe.ofFloat(1f, -(bgWidth-btWidth));//0ms-500s位置变化(此时的1f==设置的duration)
        k0.setInterpolator(new LinearInterpolator());//设置插值器
        k1.setInterpolator(new LinearInterpolator());//设置插值器
        PropertyValuesHolder p = PropertyValuesHolder.ofKeyframe("translationX", k0, k1);//PropertyValuesHolder这个类的意义就是，它其中保存了动画过程中所需要操作的属性和对应的值
        objectAnimatorA = ObjectAnimator.ofPropertyValuesHolder(mImgBt, p);
        objectAnimatorA.setDuration(300);
        objectAnimatorA.start();
        mImgBg.setTag(R.mipmap.switch_open_bg);//设置tag用来判断当前选中状态(设置为选中图片id)
    }

    public void startOffAnimal() {
        mImgBg.setImageResource(R.mipmap.switch_off_bg);
        Keyframe k0 = Keyframe.ofFloat(0f, -(bgWidth - btWidth));
        Keyframe k1 = Keyframe.ofFloat(1f, 0);
        k0.setInterpolator(new LinearInterpolator());
        k1.setInterpolator(new LinearInterpolator());
        PropertyValuesHolder p = PropertyValuesHolder.ofKeyframe("translationX", k0, k1);
        objectAnimatorA = ObjectAnimator.ofPropertyValuesHolder(mImgBt, p);
        objectAnimatorA.setDuration(300);
        objectAnimatorA.start();
        mImgBg.setTag(R.mipmap.switch_off_bg);
    }

    //获取mImgBg宽度
    private void getBgWidth() {
        viewTreeObserver = mImgBg.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bgWidth = mImgBg.getWidth();
            }
        });
    }
    //获取mImgBt宽度
    private void getBtWidth() {
        viewTreeObserver = mImgBt.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                btWidth = mImgBt.getWidth();
            }
        });
    }

}
