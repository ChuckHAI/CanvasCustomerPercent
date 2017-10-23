package clinet.iqianjin.canvasyuandemo2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by chuck on 2017/6/16.
 */

public class CircleNumberProgress extends View {


    /**
     * 进度条画笔的宽度（dp）
     */
    private int paintProgressWidth = 4;

    /**
     * 未完成进度条的颜色
     */
    private int paintUndoneColor = 0xffFFFFE0;

    /**
     * 已完成进度条的颜色
     */
    private int paintDoneColor = 0xffFF3030;

    /**
     * 设置进度条画笔的宽度(px)
     */
    private int paintProgressWidthPx;

    /**
     * Context上下文环境
     */
    private Context context;

    /**
     * 调用者设置的进程 0 - 100
     */
    private int progress;

    /**
     * 画未完成进度圆弧的画笔
     */
    private Paint paintUndone = new Paint();

    /**
     * 画已经完成进度条的画笔
     */
    private Paint paintDone = new Paint();

    /**
     * 画文字的画笔
     */
    private Paint paintText = new Paint();

    /**
     * 包围进度条圆弧的矩形
     */
    private RectF rectF = new RectF();

    /**
     * 百分比文字的颜色
     */
    private int paintTextColor = 0xffff0077;

    /**
     * 文字百分比的字体大小（sp）
     */
    private int paintTextSize = 15;

    /**
     * 扇形的直径
     */
    private int mDiameter;//直径px

    /**
     * handler消息标识
     */
    protected static final int WHAT_INCREASE = 1;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            progress++;
            setProgress(progress);
            handler.sendEmptyMessageDelayed(WHAT_INCREASE, 50);
            if (progress >= 100) {
                handler.removeMessages(WHAT_INCREASE);
                if (callBackForFinish != null) {
                    callBackForFinish.onIntentMethord();
                }
            }
        }

    };

    public CircleNumberProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        // 构造器中初始化数据
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        // 设置进度条画笔的宽度
        paintProgressWidthPx = Utils.dip2px(context, paintProgressWidth);
        //设置圆环的直径
        mDiameter = Utils.dip2px(context, 30);

        // 未完成进度圆环的画笔的属性
        paintUndone.setColor(paintUndoneColor);
        paintUndone.setStrokeWidth(paintProgressWidthPx);
        paintUndone.setAntiAlias(true);
        paintUndone.setStyle(Paint.Style.STROKE);

        // 已经完成进度条的画笔的属性
        paintDone.setColor(paintDoneColor);
        paintDone.setStrokeWidth(paintProgressWidthPx);
        paintDone.setAntiAlias(true);
        paintDone.setStyle(Paint.Style.STROKE);

        paintText.setColor(paintTextColor);
        paintText.setTextSize(Utils.sp2px(context, paintTextSize));
        paintText.setAntiAlias(true);
        paintText.setStyle(Paint.Style.STROKE);
        paintText.setTypeface(Typeface.DEFAULT);

    }


    private boolean isMeasured = false;
    private int textWidth;
    private int textHeight;
    private int mDistance = 50;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isMeasured) {
            getWidthAndHeight();
            isMeasured = true;
        }

    }

    private void getWidthAndHeight() {
        Rect rect = new Rect();
        paintText.getTextBounds("5s", 0, "5s".length(), rect);
        textWidth = rect.width();
        textHeight = rect.height();

        rectF.left = getMeasuredWidth() - mDiameter - mDistance;
        rectF.top = mDistance;
        rectF.right = getMeasuredWidth() - mDistance;
        rectF.bottom = mDiameter + mDistance;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画未完成进度的圆环
        canvas.drawArc(rectF, 0, 360, false, paintUndone);

        // 画已经完成进度的圆弧 从-90度开始，即从圆环顶部开始
        canvas.drawArc(rectF, -90, progress / 100.0f * 360, false, paintDone);

        //画文字
        canvas.drawText((5 - progress / 20) + "s", getMeasuredWidth()  - mDistance - mDiameter / 2 - textWidth / 2, mDiameter / 2 + mDistance + textHeight / 2, paintText);

    }


    /**
     * @param progress 设置当前进度,强制重绘
     */
    private void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    /**
     * 开始绘制
     */
    public void startProgress() {
        this.progress = 0;
        handler.removeMessages(WHAT_INCREASE);
        handler.sendEmptyMessage(WHAT_INCREASE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeMessages(WHAT_INCREASE);
                if (callBackForFinish != null) {
                    callBackForFinish.onIntentMethord();
                }
            }
        });
        return super.onTouchEvent(event);

    }

    public interface CallBackForFinish {
        void onIntentMethord();
    }

    CallBackForFinish callBackForFinish;

    public void setCallBack(CallBackForFinish callBackForFinish) {
        this.callBackForFinish = callBackForFinish;
    }

}