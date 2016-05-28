package tk.theradikalsoftware.trcirclegauge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

public class CircleGaugeView extends View {
    Paint paintArc;
    Paint paintText;
    Paint paintCircle;
    float w, h, r; // Width / Height / Radius

    float center_x, center_y;
    final RectF oval = new RectF();
    float sweep; //Color of draw on background
    float left, right;
    int bgColor;
    int sweepColor;
    int sweepStroke;
    int bgStroke;

    public CircleGaugeView(Context context) {
        super(context);
        init(null, 0);
    }

    public CircleGaugeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);

        //Load attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleGaugeView, 0, 0);

        w = a.getFloat(R.styleable.CircleGaugeView_trcgWidth, 0);
        h = a.getFloat(R.styleable.CircleGaugeView_trcgHeight, 0);
        bgColor =  a.getColor(R.styleable.CircleGaugeView_trcgBgColor, ContextCompat.getColor(context, android.R.color.black));
        sweepColor = a.getColor(R.styleable.CircleGaugeView_trcgSweepColor, ContextCompat.getColor(context, android.R.color.holo_red_dark));
        bgStroke = a.getInteger(R.styleable.CircleGaugeView_trcgStrokeWidthBg, 35);
        sweepStroke = a.getInteger(R.styleable.CircleGaugeView_trcgStrokeWidthSweep, 35);
    }


    private void init(AttributeSet attrs, int defStyle) {
        // LInitialize variables
        paintArc = new Paint();
        paintText = new Paint();
        paintCircle = new Paint();
        //rectF = new RectF(50, 20, 100, 80);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(w == 0)
            w = getWidth();
        if(h == 0)
            h = getHeight();

        //region ARC CODE
        /* DRAW ARC */
        float radius;

        if (w > h) {
            radius = h / 3;
        } else {
            radius = h / 4;
        }

        paintArc.setAntiAlias(true);
        paintArc.setColor(bgColor);
        paintArc.setStrokeWidth(bgStroke);
        paintArc.setStrokeCap(Paint.Cap.ROUND);
        paintArc.setStyle(Paint.Style.STROKE);

        center_x = w / 2;
        center_y = h / 2;

        left = center_x - radius;
        float top = center_y - radius;
        right = center_x + radius;
        float bottom = center_y + radius;

        oval.set(left, top, right, bottom);

        canvas.drawArc(oval, 180, 180, false, paintArc); //ARC background


        //LOAD BACKGROUND COLOR
        paintArc.setStrokeWidth(sweepStroke);
        paintArc.setStrokeCap(Paint.Cap.ROUND);
        paintArc.setColor(sweepColor);
        canvas.drawArc(oval, 180, sweep, false, paintArc); //ARC progress background
        //endregion

        //region CIRCLE CODE
        /* DRAW CIRCLE */

        //paintCircle.setStyle(Paint.Style.FILL);
        //paintCircle.setColor(Color.CYAN);
        //canvas.drawPaint(paintCircle);
        // Use Color.parseColor to define HTML colors
        //paintCircle.setColor(Color.parseColor("#CD5C5C"));
        //canvas.drawCircle(w / 2, h / 2, (radius-10), paintCircle);

        //endregion

        //region TEXT PRINCIPAL
        //paint.setColor(Color.WHITE);
        //paint.setStyle(Paint.Style.FILL);
        //canvas.drawPaint(paint);

        //paintText.setColor(Color.BLACK);
        //paintText.setTextSize(25);
        //canvas.drawText("$"+ GetValue(), w/2, h/2, paintText);
        //endregion
    }

    public void SetSweetColor(String color){
        sweepColor = Color.parseColor(color);
    }
    public void SetValue(int val){
        sweep = val;
        invalidate();
    }
    public float GetValue(){
        return sweep;
    }
}
