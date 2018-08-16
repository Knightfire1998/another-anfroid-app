package apps.araiz.com.fiberbasechat;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
public class PaintView extends View {
  public LayoutParams params;
  private Path path = new Path();
  private Paint brush = new Paint();
    public PaintView(Context context) {
        super(context);


        brush.setAntiAlias(true);
        brush.setColor(Color.MAGENTA);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(7f);


        params = new LayoutParams(LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //register the touches
        float pointX = event.getX();
        float pointy = event.getY();

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                path.moveTo(pointX,pointy);
                return true;

                case MotionEvent.ACTION_MOVE:
                    path.lineTo(pointX,pointy);
                    break;

                    default:
                        return false;




        }

          postInvalidate();
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path,brush);

    }

}
