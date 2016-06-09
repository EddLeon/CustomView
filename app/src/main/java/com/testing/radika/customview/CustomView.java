package com.testing.radika.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by greenapsis on 9/06/16.
 */
public class CustomView extends View {

    //circle and text colors
    private int circleCol, labelCol;
    //label text
    private String circleText;
    //paint for drawing custom view
    private Paint circlePaint;

    private int value=0;

    private Path bArrow = new Path();
    private Path sArrow = new Path();

    private static Paint mPaint;
    static {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
    }

    public CustomView(Context context) {
        super(context);
        setup(context,null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context,attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context,attrs);
    }

    private void setup(Context context, AttributeSet attrs) {
        //paint object for drawing in onDraw
        circlePaint = new Paint();

        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomView, 0, 0);

        try {
            //get the text and colors specified using the names in attrs.xml
            circleText = a.getString(R.styleable.CustomView_circleLabel);
            circleCol = a.getInteger(R.styleable.CustomView_circleColor, 0);//0 is default
            labelCol = a.getInteger(R.styleable.CustomView_labelColor, 0);
        } finally {
            a.recycle();
        }

        // Construct a wedge-shaped path
        /*sArrow.moveTo(0,-10);
        sArrow.lineTo(-20,60);
        sArrow.lineTo(0,60);
        sArrow.lineTo(-20,105);
        sArrow.lineTo(20,105);
        sArrow.lineTo(0,60);
        sArrow.lineTo(20,60);
        sArrow.close();*/


        //BUILDING THE INDICATOR
        sArrow.moveTo(0,0);
        sArrow.lineTo(45,-30);
        sArrow.lineTo(45,0);
        sArrow.lineTo(90,-30);
        sArrow.lineTo(45,-60);
        sArrow.lineTo(45,-30);
        sArrow.lineTo(0,-60);
        sArrow.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        //draw the view

        Path auxArrow = new Path();
        Path aux2Arrow = new Path();
        //get half of the width and height as we are working with a circle
        int viewWidthHalf = getWidth()/2;
        int viewHeightHalf = getHeight()/2;

        //get the radius as half of the width or height, whichever is smaller
        //subtract ten so that it has some space around it
        int radius = 0;
        if(viewWidthHalf>viewHeightHalf)
            radius=viewHeightHalf-10;
        else
            radius=viewWidthHalf-10;

        /*circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

        //set the paint color using the circle color specified
        circlePaint.setColor(circleCol);

        //canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, circlePaint);

        //set the text color using the color specified
        circlePaint.setColor(labelCol);

        //set text properties
        circlePaint.setTextAlign(Paint.Align.CENTER);
        circlePaint.setTextSize(50);

        //draw the text using the string attribute and chosen properties
        // canvas.drawText(circleText, viewWidthHalf, viewHeightHalf, circlePaint);
        */

        float x,y;
        x = (float) ((radius-90)*Math.cos(Math.toRadians(value))); //caclulate x, y coordinates of the perimeter of the circle
        y = (float) ((radius-90)*Math.sin(Math.toRadians(value)));
        Matrix mat = new Matrix();
        mat.setRotate(value, 0, -10);  // rotate matrix taking the base of of the path as pivot
        sArrow.transform(mat, auxArrow);
        sArrow.transform(mat, aux2Arrow);
        mat.setTranslate(viewWidthHalf, viewHeightHalf);  //translation to center of canvas
        auxArrow.transform(mat);
        aux2Arrow.transform(mat);
        mat.setTranslate(x, y);
        auxArrow.transform(mat);
        mat.setTranslate(-x, -y);
        aux2Arrow.transform(mat);
        canvas.drawPath(auxArrow, mPaint);
        canvas.drawPath(aux2Arrow, mPaint);
    }

    public int getCircleColor(){
        return circleCol;
    }

    public int getLabelColor(){
        return labelCol;
    }

    public String getLabelText(){
        return circleText;
    }

    public void setCircleColor(int newColor){
        //update the instance variable
        circleCol=newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }
    public void setLabelColor(int newColor){
        //update the instance variable
        labelCol=newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }

    public void setLabelText(String newLabel){
        //update the instance variable
        circleText=newLabel;
        //redraw the view
        invalidate();
        requestLayout();
    }
    public void setValue(int value){
        this.value = value;
        invalidate();
        requestLayout();
    }
}
