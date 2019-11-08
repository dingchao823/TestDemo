package com.suiyi.main.utils

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.Path
import android.graphics.PathMeasure
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import com.suiyi.main.R

object AnimatorUtils {

    fun doCartAnimator(activity: Activity?, startView: View?,
                       fromParentView: View?, toView: View?, container : ViewGroup?,
                       listener: OnAnimatorListener?) {
        //第一步：
        //创造出执行动画的主题---imageView
        //代码new一个imageView，图片资源是上面的imageView的图片
        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
        if (activity == null
                || startView == null
                || toView == null
                || fromParentView == null
                || container == null) {
            return
        }
        val goods = ImageView(activity)
        goods.setPadding(1, 1, 1, 1)
        //图片切割方式
        goods.scaleType = ImageView.ScaleType.CENTER_CROP
        //获取图片资源
        goods.setImageResource(R.mipmap.ic_cart)
        //设置RelativeLayout容器(这里必须设置RelativeLayout 设置LinearLayout动画会失效)
        val params = RelativeLayout.LayoutParams(100, 100)
        //把动画view添加到动画层
        container.addView(goods, params)

        //第二步:
        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        val fromLocation = IntArray(2)
        //获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
        fromParentView.getLocationInWindow(fromLocation)
        val startLoc = IntArray(2)
        //获取商品图片在屏幕中的位置
        startView.getLocationInWindow(startLoc)
        //得到购物车图片的坐标(用于计算动画结束后的坐标)
        val endLoc = IntArray(2)
        toView.getLocationInWindow(endLoc)

        //第三步:
        //正式开始计算动画开始/结束的坐标
        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        val startX = (startLoc[0] - fromLocation[0] + startView.width / 2).toFloat()// 动画开始的X坐标
        val startY = (startLoc[1] - fromLocation[1] + startView.height / 2).toFloat()//动画开始的Y坐标

        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
        val toX = (endLoc[0] - fromLocation[0] + toView.width / 5).toFloat()
        val toY = (endLoc[1] - fromLocation[1]).toFloat()

        //第四步:
        //计算中间动画的插值坐标，绘制贝塞尔曲线
        val path = Path()
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY)
        //第一个起始坐标越大，贝塞尔曲线的横向距离就会越大 toX,toY:为终点
        path.quadTo((startX + toX) / 2, startY, toX, toY)
        val pathMeasure = PathMeasure(path, false)
        //实现动画具体博客可参考 鸿洋大神的https://blog.csdn.net/lmj623565791/article/details/38067475
        val valueAnimator = ValueAnimator.ofFloat(0f, pathMeasure.length)
        //设置动画时间
        valueAnimator.duration = 700
        //LinearInterpolator补间器:它的主要作用是可以控制动画的变化速率，比如去实现一种非线性运动的动画效果
        //具体可参考郭霖大神的：https://blog.csdn.net/guolin_blog/article/details/44171115
        valueAnimator.interpolator = LinearInterpolator()

        valueAnimator.addUpdateListener { animation ->
            //更新动画
            val value = animation.animatedValue as Float
            val currentPosition = FloatArray(2)
            pathMeasure.getPosTan(value, currentPosition, null)
            goods.translationX = currentPosition[0]//改变了ImageView的X位置
            goods.translationY = currentPosition[1]//改变了ImageView的Y位置
        }

        //第五步:
        //开始执行动画
        valueAnimator.start()

        //第六步:
        //对动画添加监听
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                //onAnimationStart()方法会在动画开始的时候调用
            }

            //onAnimationEnd()方法会在动画结束的时候调用
            override fun onAnimationEnd(animation: Animator) {
                //把移动的图片imageView从父布局里移除
                container.removeView(goods)
                listener?.onAnimationEnd(animation)
            }

            override fun onAnimationCancel(animation: Animator) {
                //onAnimationCancel()方法会在动画被取消的时候调用
            }

            override fun onAnimationRepeat(animation: Animator) {
                //onAnimationRepeat()方法会在动画重复执行的时候调用
            }
        })
    }

    //封装的旋转动画方法
    //    public static void viewRotate(Context context, View view) {
    //        if (view == null) {
    //            return;
    //        }
    //        //加载动画
    //        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_round_rotate);
    //        //LinearInterpolator补间器:它的主要作用是可以控制动画的变化速率，比如去实现一种非线性运动的动画效果
    //        //具体可参考郭霖大神的：https://blog.csdn.net/guolin_blog/article/details/44171115
    //        LinearInterpolator interpolator = new LinearInterpolator();
    //        animation.setInterpolator(interpolator);
    //        //设置动画
    //        view.startAnimation(animation);
    //    }

    interface OnAnimatorListener {
        fun onAnimationEnd(animator: Animator)
    }

}
