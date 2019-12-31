package com.suiyi.main.widget

import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import com.suiyi.main.R
import com.suiyi.main.SHApplication
import com.suiyi.main.utils.DimenUtils

class MyInsetDrawable(var mDrawable: Drawable) : Drawable(){

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bgDrawable = SHApplication.instance().resources.getDrawable(R.drawable.bg_home_default_picture)

    init {
        mDrawable.setVisible(isVisible, true)
        mDrawable.state = state
        mDrawable.level = level

        mPaint.color = Color.parseColor("#FFFFFF")
    }

    override fun onBoundsChange(bounds: Rect?) {
        var childBounds = Rect()
        bounds?.let {
            val fatherWidth = bounds.width()
            val fatherHeight = bounds.height()
            val defaultChildWidth = DimenUtils.dipTopx(110f)
            val defaultChildHeight = DimenUtils.dipTopx(42f)
            var lastWidth = -1
            var lastHeight = -1
            val px_17_5 = DimenUtils.dipTopx(17.5f)
            var paddingLeft  = 0
            var paddingTop = 0

            val widthDiff = fatherWidth - defaultChildWidth - 2 * px_17_5
            val heightDiff = fatherHeight - defaultChildHeight - 2 * px_17_5

            if (widthDiff >= 0){
                if (heightDiff < 0) {
                    lastHeight = fatherHeight - 2 * px_17_5
                    lastWidth = ((defaultChildWidth.toFloat() / defaultChildHeight.toFloat()) * lastHeight).toInt()
                    paddingTop = px_17_5
                    paddingLeft = ((fatherWidth - lastWidth) / 2f).toInt()
                }else{
                    lastWidth = defaultChildWidth
                    lastHeight = defaultChildHeight
                    paddingLeft = ((fatherWidth - defaultChildWidth) / 2f).toInt()
                    paddingTop = ((fatherHeight - defaultChildHeight) / 2f).toInt()
                }
            }else{
                if (heightDiff < 0) {
                    if (widthDiff <= heightDiff){
                        lastWidth = fatherWidth - 2 * px_17_5
                        lastHeight = ((defaultChildHeight.toFloat() / defaultChildWidth.toFloat()) * lastWidth).toInt()
                        paddingLeft = px_17_5
                        paddingTop = ((fatherHeight - lastHeight) / 2f).toInt()
                    }else{
                        lastHeight = fatherHeight - 2 * px_17_5
                        lastWidth = ((defaultChildWidth.toFloat() / defaultChildHeight.toFloat()) * lastHeight).toInt()
                        paddingTop = px_17_5
                        paddingLeft = ((fatherWidth - lastWidth) / 2f).toInt()
                    }
                }else{
                    lastWidth = fatherWidth - 2 * px_17_5
                    lastHeight = ((defaultChildHeight.toFloat() / defaultChildWidth.toFloat()) * lastWidth).toInt()
                    paddingLeft = px_17_5
                    paddingTop = ((fatherHeight - lastHeight) / 2f).toInt()
                }
            }
            childBounds.set(paddingLeft, paddingTop, paddingLeft + lastWidth, paddingTop + lastHeight)
            mDrawable.bounds = childBounds

            var bgBounds = Rect()
            bgBounds.set(0, 0, fatherWidth, fatherHeight)
            bgDrawable.bounds = bgBounds
        }
    }

    override fun getIntrinsicWidth(): Int {
        return -1
    }

    override fun getIntrinsicHeight(): Int {
        return -1
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
        invalidateSelf()
    }

    override fun getOpacity(): Int = mDrawable.opacity

    override fun getOutline(outline: Outline) = mDrawable.getOutline(outline)

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
        invalidateSelf()
    }

    override fun draw(canvas: Canvas) {
//        canvas.drawColor(Color.parseColor("#FFFFFF"))
        bgDrawable.draw(canvas)
        mDrawable.draw(canvas)
    }

    internal class InsetValue (val mFraction: Float = 0f, var mDimension: Int = 0) : Cloneable {

        fun getDimension(boundSize: Int): Int {
            return (boundSize * mFraction).toInt() + mDimension
        }

        override fun clone(): InsetValue {
            return InsetValue(mFraction, mDimension)
        }

    }
}