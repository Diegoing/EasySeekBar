package com.diegoing.view.seekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import android.view.MotionEvent;
import android.view.View;


/**
 * author:Diego
 * Date:2019/1/13
 * Description:
 */
public class EasySeekBar extends View {
    private Context context;

    private String TAG = "EasySeekBar";
    private Paint bgPaint;//seekbar背景
    private Paint progressPaint;//进度
    private Paint circlePaint;//控制小球
    private Point centrePoint;//中心坐标
    private Point WHPoint; //条形的长宽
    private int progress = 0; //进度
    private int value ;//值
    private int radian;//
    private int schedule;//调度



    private String configuration = "horizontal";//形状 vertical、horizontal、circle、semicircle
    private int bgColor;//背景颜色
    private int progressColor;//进度颜色
    private int circleR;//控制小球半径
    private int line;//规格
    private int maxProgress;//最大值
    private int minProgress;//最小值
    private int circleColor;//小球颜色
    private int strokeWidth;

    private boolean isTure;
   private EasySeekBarLister easySeekBarLister;
    public EasySeekBar(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public void setEasySeekBarLister(EasySeekBarLister easySeekBarLister) {
        this.easySeekBarLister = easySeekBarLister;
    }

    public EasySeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public EasySeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EasySeekBar);
        try {
            configuration = typedArray.getString(R.styleable.EasySeekBar_configuration);
            bgColor = typedArray.getColor(R.styleable.EasySeekBar_progress_bg_color,Color.parseColor("#999999"));
            progressColor = typedArray.getColor(R.styleable.EasySeekBar_progress_color,Color.parseColor("#ffffff"));
            circleR = typedArray.getDimensionPixelOffset(R.styleable.EasySeekBar_circle_r,40);
            line = typedArray.getDimensionPixelOffset(R.styleable.EasySeekBar_line,400);
            maxProgress = typedArray.getInt(R.styleable.EasySeekBar_max_progress,100);
            minProgress = typedArray.getInt(R.styleable.EasySeekBar_min_progress,0);
            circleColor = typedArray.getColor(R.styleable.EasySeekBar_circle_color,Color.parseColor("#ffffff"));
            strokeWidth = typedArray.getInt(R.styleable.EasySeekBar_progress_with,12);
        }finally {
            typedArray.recycle();
        }

        centrePoint = new Point();
        WHPoint = new Point();
        if (circleR<strokeWidth/2){
            centrePoint.x = line+strokeWidth/2;
            centrePoint.y = line+strokeWidth/2;
            schedule = strokeWidth/2;

            WHPoint.x = line+strokeWidth;
            WHPoint.y = strokeWidth;
        }else {
            centrePoint.x = line+circleR;
            centrePoint.y = line+circleR;
            schedule = circleR;

            WHPoint.x = line+2*circleR;
            WHPoint.y = 2*circleR;
        }
        setValue(minProgress);
    }

    private void initPaint() {
        //初始低画笔
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(bgColor);
        bgPaint.setStrokeWidth(strokeWidth-2);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);

        //初始进度画笔
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setAntiAlias(true);
        progressPaint.setColor(progressColor);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStrokeWidth(strokeWidth);

        //初始小球画笔
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(circleColor);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (configuration){
            case "horizontal":
                canvas.drawLine(WHPoint.y/2,WHPoint.y/2,line+WHPoint.y/2,WHPoint.y/2,bgPaint);
                canvas.drawLine(WHPoint.y/2,WHPoint.y/2,progress+WHPoint.y/2,WHPoint.y/2,progressPaint);
                canvas.drawCircle(progress+WHPoint.y/2,WHPoint.y/2,circleR,circlePaint);
                break;
            case "vertical":
                canvas.drawLine(WHPoint.y/2,WHPoint.y/2,WHPoint.y/2,line+WHPoint.y/2,bgPaint);
                canvas.drawLine(WHPoint.y/2,line+WHPoint.y/2,WHPoint.y/2,progress+WHPoint.y/2,progressPaint);
                canvas.drawCircle(WHPoint.y/2,progress+WHPoint.y/2,circleR,circlePaint);
                break;
            case "circle":
                bgPaint.setStyle(Paint.Style.STROKE);
                progressPaint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(centrePoint.x,centrePoint.y,line,bgPaint);
                canvas.drawArc(schedule,schedule,2*centrePoint.x-schedule,2*centrePoint.y-schedule,270,progress,false,progressPaint);
                canvas.drawCircle((float) Math.sin(progress*Math.PI/180)*line+schedule+line, (float) -(line*Math.cos(progress*Math.PI/180))+schedule+line,circleR,circlePaint);
                break;
            case "semicircle":
                bgPaint.setStyle(Paint.Style.STROKE);
                progressPaint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(schedule,schedule,2*centrePoint.x-schedule,2*centrePoint.y-schedule,180,180,false,bgPaint);
                canvas.drawArc(schedule,schedule,2*centrePoint.x-schedule,2*centrePoint.y-schedule,180,progress,false,progressPaint);
                canvas.drawCircle((float) Math.sin((progress-90)*Math.PI/180)*line+schedule+line, (float) -(line*Math.cos((progress-90)*Math.PI/180))+schedule+line,circleR,circlePaint);
                break;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (configuration){
            case "horizontal":
                horTouch(event);
                break;
            case "vertical":
                verTouch(event);
                break;
            case "circle":
                cirTouch(event);
                break;
            case "semicircle":
                semTouch(event);
                break;
        }

        return true;
    }

    private void semTouch(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
//                if (!isTure){
//
//                    radian = (int) getRadian(new Point(line + circleR, line + circleR), new Point((int)x, (int)y))+90;
//                    progress =  radian>360?radian-360:radian;
//                }

                break;
            case MotionEvent.ACTION_MOVE:
                radian = (int) getRadian(centrePoint, new Point((int)x, (int)y))+180;
                progress =  radian>360?radian-360:radian;
                if (progress >180&&progress<270){
                    progress = 180;
                }else if (progress>=270&&progress<=360){
                    progress = 0;

                }
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                if (easySeekBarLister !=null){
                    value = getSemValue(progress);
                    easySeekBarLister.onProgress(value);
                }

                break;

        }
        invalidate();
    }

    private void cirTouch(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
//                if (!isTure){
//
//                    radian = (int) getRadian(new Point(line + circleR, line + circleR), new Point((int)x, (int)y))+90;
//                    progress =  radian>360?radian-360:radian;
//                }

                break;
            case MotionEvent.ACTION_MOVE:
                radian = (int) getRadian(centrePoint, new Point((int)x, (int)y))+90;
                if (getAbs(progress - (radian>360?radian-360:radian))>100){
                }else {
                    progress =  (radian>360?radian-360:radian)-1;
                }
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                if (easySeekBarLister !=null){
                    value = getCirValue(progress);
                    easySeekBarLister.onProgress(value);
                }

                break;

        }
        invalidate();
    }




    private void horTouch(MotionEvent event) {
        float x = event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
//                if (!isTure){
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                    int point = ((int) x);
//                    if (point<0){
//                        point = 0;
//                    }else if (point>line){
//                        point = line;
//                    }
//                    progress = point;
//                }


                break;
            case MotionEvent.ACTION_MOVE:
                int pointX = ((int) x-circleR);
                if (pointX<0){
                    pointX = 0;
                }else if (pointX> line){
                    pointX = line;
                }
                progress = pointX;
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                if (easySeekBarLister !=null){
                    value = getVHValue(progress);
                    easySeekBarLister.onProgress(value);
                }

                break;

        }

        invalidate();
    }

    private void verTouch(MotionEvent event) {
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
//                if (isTure){
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                    int point = ((int) y);
//                    if (point<0){
//                        point = 0;
//                    }else if (point>line){
//                        point = line;
//                    }
//                    progress = point;
//                }

                break;
            case MotionEvent.ACTION_MOVE:
                int pointX = ((int) y-circleR);
                if (pointX<0){
                    pointX = 0;
                }else if (pointX> line){
                    pointX = line;
                }
                progress = pointX;

                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                if (easySeekBarLister !=null){
                    value = getVHValue(line-progress);
                    easySeekBarLister.onProgress(value);
                }

                break;

        }

        invalidate();
    }
    /**
     * @describe 触摸点与中心点之间直线与水平方向的夹角角度
     * @param a
     * @param b
     * @return
     */
    public static double getRadian(Point a, Point b) {
        float lenA = b.x - a.x;
        float lenB = b.y - a.y;
        float lenC = (float) Math.sqrt(lenA * lenA + lenB * lenB);
        float ang = (float) Math.acos(lenA / lenC);
        ang = ang * (b.y < a.y ? -1 : 1);
        return ang*180/Math.PI<0?ang*180/Math.PI+360:ang*180/Math.PI;
    }
    /**
     * @describe 绝对值
     * @param i
     * @return
     */
    private int getAbs(int i) {
        if (i<0){
            i = -i;
        }
        return i;
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value<minProgress){
            this.value = minProgress;
        }else if (value>maxProgress){
            this.value = maxProgress;
        }else {
            this.value = value;
        }

        progress = ValueToProgress(this.value);
        invalidate();
    }

    private int ValueToProgress(int value) {
        switch (configuration){
            case "horizontal":
                return progress = (value-minProgress)*line/(maxProgress - minProgress);
            case "vertical":
                return line-(value-minProgress)*line/(maxProgress - minProgress);
            case "circle":
               return (value-minProgress)*359/(maxProgress-minProgress);
            case "semicircle":
                return (value-minProgress)*180/(maxProgress-minProgress);

        }
        return minProgress;
    }

    private int getVHValue(int progress) {

        return progress*(maxProgress - minProgress) / line+minProgress;
    }
    private int getCirValue(int i) {
        return (maxProgress-minProgress)*i/359+minProgress;
    }
    private int getSemValue(int i) {
        return (maxProgress-minProgress)*i/180+minProgress;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        switch (configuration){
            case "horizontal":
                setMeasuredDimension(WHPoint.x,WHPoint.y);
                break;
            case "vertical":
                setMeasuredDimension(WHPoint.y,WHPoint.x);
                break;
            case "circle":
                setMeasuredDimension(2*centrePoint.x,2*centrePoint.y);
                break;
            case "semicircle":
                setMeasuredDimension(2*centrePoint.x,centrePoint.y+schedule);
                break;

        }
    }
    public interface EasySeekBarLister{
        void onProgress(int pro);
    }
}


