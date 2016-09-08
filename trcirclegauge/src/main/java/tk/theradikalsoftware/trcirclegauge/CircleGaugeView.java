package tk.theradikalsoftware.trcirclegauge;

/*
*   03/09/2016 - 19:04
*   David Ochoa Gutierrez
*   NL - MX
*/


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
    float w, h; // Width / Height

    float center_x, center_y;
    final RectF rectF = new RectF();
    float sweep; //Color of draw on background
    float left, right;
    int bgColor;
    int sweepColor;
    int sweepStroke;
    int bgStroke;
    int arcInitial;
    int arcFinal;

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
        arcInitial = a.getInteger(R.styleable.CircleGaugeView_trcgArcInitial, 180);
        arcFinal = a.getInteger(R.styleable.CircleGaugeView_trcgArcFinal, 180);
    }


    private void init(AttributeSet attrs, int defStyle) {
        // Initialize variables
        paintArc = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(w == 0)
            w = getWidth();
        if(h == 0)
            h = getHeight();

        /* DRAW ARC */
        float radius;

        if (w > h) {
            //landscape
            radius = h / 2;
        } else {
            //portrait
            radius = w / 2;
        }

        paintArc.setAntiAlias(true);
        paintArc.setColor(bgColor);
        paintArc.setStrokeWidth(bgStroke);
        paintArc.setStrokeCap(Paint.Cap.ROUND);
        paintArc.setStyle(Paint.Style.STROKE);

        center_x = w / 2;
        center_y = h / 2;

        left = center_x - radius + bgStroke;
        float top = center_y - radius + bgStroke;
        right = center_x + radius - bgStroke;
        float bottom = center_y + radius - bgStroke;
        /*if(arcFinal >180){ //posible solución para dibujar media luna
            bottom = center_y + radius - bgStroke;
        }else{
            bottom = center_y;
        }*/


        rectF.set(left, top, right, bottom);
        //canvas.drawRect(rectF, paintArc);  //DEBUG: draw the rectangule host for the arc
        canvas.drawArc(rectF, arcInitial, arcFinal, false, paintArc); //ARC background //180,180

        /*
        DEBUG LOG
        Log.d("CIRCLEGAUGE", "width:"+w+" height:"+h+"radius:"+radius );
        Log.d("CIRCLEGAUGE", "top:"+top+" bottom:"+bottom+" left:"+left+" right:"+right );
        Log.d("CIRCLEGAUGE", "centerX: "+center_x+" centerY:" +center_y );*/

        //LOAD BACKGROUND COLOR (Background color)
        paintArc.setStrokeWidth(sweepStroke);
        paintArc.setStrokeCap(Paint.Cap.ROUND);
        paintArc.setColor(sweepColor);
        canvas.drawArc(rectF, 180, sweep, false, paintArc); //ARC progress background //sipongo true dibuja tipo manecillas
    }

    /* Public methods */
    public void SetSweepColor(String color){
        sweepColor = Color.parseColor(color);
    }
    public void SetArcBgColor(String color){ bgStroke = Color.parseColor(color); }
    public void SetArcWidth(int width){
        bgStroke = width;
    }
    public void SetArcSweepWidth(int width){
        sweepStroke = width;
    }
    public void SetValue(int val){
        sweep = val;
        invalidate();
    }
    public void SetAngles(int startAng, int finalAng){
        arcInitial = startAng;
        arcFinal = finalAng;
    }
    public float GetValue(){
        return sweep;
    }
}
