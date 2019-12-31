package com.suiyi.main.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import com.suiyi.main.R
import java.lang.ref.WeakReference


class CircleRectangleImageView : ImageView {

    var mContext: Context? = null
    var borderColor : Int = 0
    var borderWidth : Int = 0
    var mShape = 0
    var roundRadius = 0
    var leftTopRadius = 0
    var rightTopRadius = 0
    var rightBottomRadius = 0
    var leftBottomRadius = 0
    var onlyDrawBorder = false
    var shaderPaint: Paint? = null
    var mPaint: Paint? = null
    var shader: Shader? = null
    val mWeakBitmap: WeakReference<Bitmap>? = null

    constructor(context: Context) : super(context) {
        sharedConstructor(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        sharedConstructor(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        sharedConstructor(context, attrs)
    }

    private fun sharedConstructor(context: Context, attrs: AttributeSet?) {
        mContext = context
        shaderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        attrs?.let {
            val a = context.obtainStyledAttributes(it, R.styleable.csiv_CustomShapeImageView)
            mShape = a.getInt(R.styleable.csiv_CustomShapeImageView_csiv_shape, Shape.CIRCLE)
            borderColor = a.getColor(R.styleable.csiv_CustomShapeImageView_csiv_borderColor, Color.TRANSPARENT)
            borderWidth = a.getDimensionPixelSize(R.styleable.csiv_CustomShapeImageView_csiv_imageBorderWidth, 0)
            roundRadius = a.getDimensionPixelSize(R.styleable.csiv_CustomShapeImageView_csiv_roundRadius, 0)
            leftTopRadius = a.getDimensionPixelSize(R.styleable.csiv_CustomShapeImageView_csiv_leftTopRadius, -1)
            if (leftTopRadius == -1) {
                leftTopRadius = roundRadius
            }
            rightTopRadius = a.getDimensionPixelSize(R.styleable.csiv_CustomShapeImageView_csiv_rightTopRadius, -1)
            if (rightTopRadius == -1) {
                rightTopRadius = roundRadius
            }
            rightBottomRadius = a.getDimensionPixelSize(R.styleable.csiv_CustomShapeImageView_csiv_rightBottomRadius, -1)
            if (rightBottomRadius == -1) {
                rightBottomRadius = roundRadius
            }
            leftBottomRadius = a.getDimensionPixelSize(R.styleable.csiv_CustomShapeImageView_csiv_leftBottomRadius, -1)
            if (leftBottomRadius == -1) {
                leftBottomRadius = roundRadius
            }
            onlyDrawBorder = a.getBoolean(R.styleable.csiv_CustomShapeImageView_csiv_onlyDrawBorder, false)
            a.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (onlyDrawBorder) {
            super.onDraw(canvas)
        } else {
            val bmp = drawableToBitmap(drawable)
            if (bmp != null) {
                val bmpW = bmp.width.toFloat()
                val bmpH = bmp.height.toFloat()
                val h = height.toFloat()
                val w = width.toFloat()
                drawShader(canvas, bmp, bmpW, bmpH, w, h)
                drawShape(canvas, w, h)
            }
        }
        drawBorder(canvas)
    }

    private fun drawShader(canvas: Canvas, bmp: Bitmap, bmpW: Float, bmpH: Float, w: Float, h: Float) {
        shader = BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val matrix = Matrix()
        setMatrix(matrix, bmpW, bmpH, w, h)
        shader?.setLocalMatrix(matrix)
        shaderPaint?.color = Color.TRANSPARENT
        shaderPaint?.shader = shader
        canvas.drawPaint(shaderPaint)
    }

    fun setMatrix(matrix: Matrix, bmpW: Float, bmpH: Float, w: Float, h: Float) {
        val scaleType = scaleType
        if (scaleType == ScaleType.CENTER) {
            val dx = (w - bmpW) / 2
            val dy = (h - bmpH) / 2
            matrix.setTranslate(dx, dy)
        } else if (scaleType == ScaleType.CENTER_CROP) {
            val ratio = Math.max(w / bmpW, h / bmpH)
            val useWidth = bmpW * ratio
            val useHeight = bmpH * ratio
            matrix.setTranslate((w - useWidth) / 2, (h - useHeight) / 2)
            matrix.preScale(ratio, ratio)
        } else if (scaleType == ScaleType.CENTER_INSIDE) {
            var ratio = Math.min(w / bmpW, h / bmpH)
            if (ratio > 1) {
                ratio = 1f
            }
            val useWidth = bmpW * ratio
            val useHeight = bmpH * ratio
            matrix.setTranslate((w - useWidth) / 2, (h - useHeight) / 2)
            matrix.preScale(ratio, ratio)
        } else if (scaleType == ScaleType.FIT_CENTER) {
            val ratio = Math.min(w / bmpW, h / bmpH)
            val useWidth = bmpW * ratio
            matrix.setTranslate((w - useWidth) / 2, 0f)
            matrix.preScale(ratio, ratio)
        } else if (scaleType == ScaleType.FIT_END) {
            val ratio = Math.min(w / bmpW, h / bmpH)
            val useWidth = bmpW * ratio
            matrix.setTranslate(w - useWidth, 0f)
            matrix.preScale(ratio, ratio)
        } else if (scaleType == ScaleType.FIT_START) {
            val ratio = Math.min(w / bmpW, h / bmpH)
            matrix.setScale(ratio, ratio)
        } else if (scaleType == ScaleType.FIT_XY) {
            val wRatio = w / bmpW
            val hRatio = h / bmpH
            matrix.setScale(wRatio, hRatio)
        } else if (scaleType == ScaleType.MATRIX) { //do nothing
        }
    }

    private fun drawShape(canvas: Canvas, w: Float, h: Float) {
        val rectF = RectF()
        shaderPaint!!.color = Color.WHITE
        shaderPaint!!.isAntiAlias = true
        shaderPaint!!.style = Paint.Style.FILL
        when (mShape) {
            Shape.CIRCLE -> {
                val min = Math.min(w, h)
                rectF.left = (w - min) / 2 + borderWidth / 2
                rectF.top = (h - min) / 2 + borderWidth / 2
                rectF.right = w - (w - min) / 2 - borderWidth / 2
                rectF.bottom = h - (h - min) / 2 - borderWidth / 2
                canvas.drawArc(rectF, 0f, 360f, true, shaderPaint)
            }
            Shape.RECTANGLE -> {
                val path = Path()
                rectF.left = borderWidth / 2.toFloat()
                rectF.top = borderWidth / 2.toFloat()
                rectF.right = w - borderWidth / 2
                rectF.bottom = h - borderWidth / 2
                val rad = floatArrayOf(leftTopRadius.toFloat(), leftTopRadius.toFloat(), rightTopRadius.toFloat(), rightTopRadius.toFloat(), rightBottomRadius.toFloat(), rightBottomRadius.toFloat(), leftBottomRadius.toFloat(), leftBottomRadius.toFloat())
                path.addRoundRect(rectF, rad, Path.Direction.CW)
                canvas.drawPath(path, shaderPaint)
            }
            Shape.ARC -> {
                rectF.left = borderWidth / 2.toFloat()
                rectF.top = borderWidth / 2.toFloat()
                rectF.right = w - borderWidth / 2
                rectF.bottom = h - borderWidth / 2
                canvas.drawArc(rectF, 0f, 360f, true, shaderPaint)
            }
        }
    }

    private fun drawableToBitmap(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight
        val config = if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        val bitmap = Bitmap.createBitmap(w, h, config)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        drawable.draw(canvas)
        return bitmap
    }

    private fun drawBorder(canvas: Canvas) {
        if (borderWidth == 0 || borderColor == Color.TRANSPARENT) {
            return
        }
        mPaint!!.isAntiAlias = true
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeWidth = borderWidth.toFloat()
        mPaint!!.color = borderColor
        when (mShape) {
            Shape.CIRCLE -> {
                val radius = (Math.min(width, height) - borderWidth) / 2.toFloat()
                val cx = width / 2f
                val cy = height / 2f
                canvas.drawCircle(cx, cy, radius, mPaint)
            }
            Shape.RECTANGLE -> {
                val path = Path()
                val rectF = RectF()
                rectF.left = borderWidth / 2.toFloat()
                rectF.top = borderWidth / 2.toFloat()
                rectF.right = width - borderWidth / 2.toFloat()
                rectF.bottom = height - borderWidth / 2.toFloat()
                val rad = floatArrayOf(leftTopRadius.toFloat(), leftTopRadius.toFloat(), rightTopRadius.toFloat(), rightTopRadius.toFloat(), rightBottomRadius.toFloat(), rightBottomRadius.toFloat(), leftBottomRadius.toFloat(), leftBottomRadius.toFloat())
                path.addRoundRect(rectF, rad, Path.Direction.CW)
                canvas.drawPath(path, mPaint)
                return
            }
            Shape.ARC -> {
                val rectF2 = RectF()
                rectF2.left = borderWidth / 2.toFloat()
                rectF2.top = borderWidth / 2.toFloat()
                rectF2.right = width - borderWidth / 2.toFloat()
                rectF2.bottom = height - borderWidth / 2.toFloat()
                canvas.drawArc(rectF2, 0f, 360f, false, mPaint)
            }
        }
    }

    object Shape {
        const val CIRCLE = 1
        const val RECTANGLE = 2
        const val ARC = 3
    }

    companion object {
        private val TAG = CircleRectangleImageView::class.java.simpleName
    }
}
