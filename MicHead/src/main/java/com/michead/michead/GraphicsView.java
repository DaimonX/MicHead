package com.michead.michead;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.nio.ByteBuffer;

/**
 * Created by Администратор on 17.04.2014.
 */
    public class GraphicsView extends View
    {
        /**
         * Container to hold the x1, y1, x2, y2 values, respectively
         */
        private float[] mCoordinates;

        /**
         * The paint with which the line will be drawn
         */
        private Paint mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        public GraphicsView(Context context) {
            super(context);
        }

        public GraphicsView (Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public GraphicsView (Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        /**
         * Set the color with which the line should be drawn
         * @param color the color to draw the line with
         */
        public void setLineColor (int color) {
            mLinePaint.setColor(color);
            invalidate();
        }

        public void setLine(int i) {
            mLinePaint.setStrokeWidth(i);
        }

        @Override
        public void setId(int id) {
            super.setId(id);
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);
            //canvas.drawLine(0,0, ByteBuffer.wrap(mCoordinates).getFloat(0), (float) mCoordinates[0],pt );
            canvas.drawLine(0,0,500,100, mLinePaint);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        }

    }

