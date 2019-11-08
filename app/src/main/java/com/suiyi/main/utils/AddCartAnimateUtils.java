package com.suiyi.main.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

/**
 * 加购动画工具类
 *
 * @author 0004640
 */
public class AddCartAnimateUtils {

    private Context mContext;
    /** 动画层 **/
    private ViewGroup animateCanvas;
    /** 起始视图 **/
    private View startView;
    /** 终点视图：购物车图标 **/
    private View endView;
    /** 动画移动视图 **/
    private View moveView;
    /** 动画位移起点的坐标 **/
    private int[] startLocation;
    /** 动画位移起点的坐标 **/
    private int[] endLocation;
    /** 开始起点坐标 **/
    private int startX;
    /** 开始起点Y **/
    private int startY;
    /** 动画位移起点的X坐标 **/
    private int distanceX;
    /** 动画位移终点的y坐标 **/
    private int distanceY;
    /** 起點视图宽度 **/
    private int startWidth;
    /** 起點视图高度 **/
    private int startHeight;
    /** 终点视图宽度 **/
    private int endWidth;
    /** 终点视图高度 **/
    private int endHeight;
    /** 调试高度 **/
    private int xBugHeight;
    private int yBugHeight;
    /** 图片 **/
    private Drawable bitmap;
    public boolean from = false;
    /** 是否缩放 **/
    public boolean isScale = true;
    /** 动画执行时间 **/
    public long duration = -1;
    /** 是否x平移比y平移慢 **/
    public boolean xSlow = true;
    /** 加购动画结束回调 **/
    public AnimationEndListener mEndListener;

    public AddCartAnimateUtils(Activity activity) {
        this.mContext = activity;
    }

    /**
     * 设置属性
     *
     * @param startView /
     * @param endView /
     * @param widthOffset 调试宽度
     */
    public void setViewParam(View startView, View endView, int widthOffset) {
        this.startView = startView;
        this.endView = endView;

        startLocation = getLocation(startView);
        endLocation = getLocation(endView);

        distanceX = (int) (endLocation[0] - startLocation[0] + startWidth / 2f);
        distanceY = (int) (endLocation[1] - startLocation[1] + startHeight / 2f);

        endWidth = endView.getWidth();
        endHeight = endView.getHeight();

        startWidth = startView.getWidth();
        startHeight = startView.getHeight();

        this.xBugHeight = widthOffset;
    }

    /**
     * 设置属性
     *
     * @param startView /
     * @param endView /
     * @param widthOffset /
     * @param heightOffset /
     * @param bitmap /
     */
    public void setViewParam(View startView, View endView, int widthOffset, int heightOffset, Drawable bitmap) {
        this.startView = startView;
        this.endView = endView;
        startLocation = getLocation(startView);
        endLocation = getLocation(endView);

        endWidth = endView.getWidth();
        endHeight = endView.getHeight();

        startWidth = startView.getWidth();
        startHeight = startView.getHeight();

        startLocation[0] = (int) (startLocation[0] + startWidth / 2f);
        startLocation[1] = (int) (startLocation[1] + startHeight / 2f);

        endLocation[0] = (int) (endLocation[0] + endWidth / 2f);
        endLocation[1] = (int) (endLocation[1] + endHeight / 2f);

        distanceX = endLocation[0] - startLocation[0];
        distanceY = endLocation[1] - startLocation[1];

        this.xBugHeight = widthOffset;
        this.yBugHeight = heightOffset;

        this.bitmap = bitmap;
    }

    /**
     * 设置属性
     *
     * @param startView /
     * @param endView /
     * @param bitmap /
     * @param viewWithScale 加购动画完成时目标View产生晃动
     * @param scale 缩放比例
     * @param scaleDuration 缩放动画执行的时间
     */
    public void setViewParam(View startView, View endView, Drawable bitmap, View viewWithScale, float scale, int scaleDuration){
        setViewParam(startView, endView, 0, 0, bitmap);
        mEndListener = () -> {
            if (viewWithScale == null || scale < 0 || scaleDuration <= 0){
                return;
            }
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(viewWithScale, "scaleX", 1f, 1f * scale, 1f);
            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(viewWithScale, "scaleY", 1f, 1f * scale, 1f);
            animatorSet.play(scaleXAnimator).with(scaleYAnimator);
            animatorSet.setDuration(scaleDuration);
            animatorSet.start();
        };
    }

    /**
     * 设置属性
     *
     * @param startView /
     * @param point /
     * @param xBugHeight /
     * @param yBugHeight /
     * @param bitmap /
     */
    public void setViewParam(View startView, int[] point, int xBugHeight, int yBugHeight, Drawable bitmap) {
        this.startView = startView;
        this.endView = endView;
        startLocation = getLocation(startView);
        endLocation = point;

        distanceX = endLocation[0] - startLocation[0];
        distanceY = endLocation[1] - startLocation[1];

        if (!isScale) {
            endWidth = 0;
            endHeight = 0;
        }else {
            endWidth = 100;
            endHeight = 100;
        }

        startWidth = startView.getWidth();
        startHeight = startView.getHeight();

        this.xBugHeight = xBugHeight;
        this.yBugHeight = yBugHeight;

        this.bitmap = bitmap;

    }

    /**
     * 开始加入购物车动画
     */
    public void startAnimate() {

        // 初始化动画层
        animateCanvas = null;
        animateCanvas = createAnimLayout();

        // 创建动画视图
        View moveView = new View(mContext);
        moveView.setBackground(bitmap);
        moveView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        // 添加动画视图到动画层
        animateCanvas.addView(moveView);
        moveView.setPivotX((float) 0.5);
        moveView.setPivotY((float) 0.5);

        // 获得动画视图
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(50, 50);
        lp.leftMargin = (int) (startLocation[0]  - 25f);
        lp.topMargin = (int) (startLocation[1] - 25f);
        moveView.setLayoutParams(lp);

        ObjectAnimator translateXAnimator;
        ObjectAnimator translateYAnimator;
        ObjectAnimator scaleXAnimator;
        ObjectAnimator scaleYAnimator;

        translateXAnimator = ObjectAnimator.ofFloat(moveView,
                "translationX", 0, distanceX);
        translateYAnimator = ObjectAnimator.ofFloat(moveView,
                "translationY", 0, distanceY);
        // 形成左侧的贝塞尔曲线
        if (xSlow) {
            translateXAnimator.setInterpolator(new AccelerateInterpolator());
            translateYAnimator.setInterpolator(new LinearInterpolator());
        }else {
            translateXAnimator.setInterpolator(new LinearInterpolator());
            translateYAnimator.setInterpolator(new AccelerateInterpolator());
        }

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(moveView,
                "alpha", 1.0f, 0.5f);
        alphaAnimator.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        // 设置缩放
        if (isScale) {
            scaleXAnimator = ObjectAnimator.ofFloat(moveView,
                    "scaleX", 1.0f, 0.5f);
            scaleYAnimator = ObjectAnimator.ofFloat(moveView,
                    "scaleY", 1.0f, 0.5f);
            scaleXAnimator.setInterpolator(new LinearInterpolator());
            scaleYAnimator.setInterpolator(new LinearInterpolator());

            animatorSet.play(translateXAnimator).with(translateYAnimator)
                    .with(scaleXAnimator).with(scaleYAnimator).with(alphaAnimator);
        } else {
            animatorSet.play(translateXAnimator).with(translateYAnimator).with(alphaAnimator);
        }
        // 设置动画延时
        if (duration == -1) {
            animatorSet.setDuration(1000);
        } else {
            animatorSet.setDuration(duration);
        }
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                moveView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                moveView.setLayerType(View.LAYER_TYPE_NONE, null);
                moveView.setVisibility(View.GONE);
                animateCanvas.removeView(moveView);
                if (mEndListener != null){
                    mEndListener.onAddCartAnimationEnd();
                }
            }
        });
    }

    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) ((Activity) mContext).getWindow()
                .getDecorView();
        LinearLayout animLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private int[] getLocation(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location;
    }

    public interface AnimationEndListener{

        void onAddCartAnimationEnd();

    }
}
