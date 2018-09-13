package Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wwp
 * DATE: 2018/5/3:18:08
 * Copyright: 中国自主招生网 All rights reserved
 * Description:
 */

public class VerticalScrollTextView extends android.support.v7.widget.AppCompatTextView {

    private float step = 0f;
    private float step2 = 0f;
    private Paint mPaint = new Paint();
    ;
    private String text;
    private float width;
    private List<String> textList = new ArrayList<String>();    //分行保存textview的显示信息。

    public VerticalScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public VerticalScrollTextView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("WWW---", "onMeasure");
        width = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("ScrollLayout only canmCurScreen run at EXACTLY mode!");
        }

        text = getText().toString();
        if (text == null | text.length() == 0) {

            return;
        }

        //下面的代码是根据宽度和字体大小，来计算textview显示的行数。

        textList.clear();
        StringBuilder builder = null;
        for (int j = 0; j < 12; j++) {
            for (int i = 0; i < text.length(); i++) {
                if (i % 20 == 0) {
                    builder = new StringBuilder();
                }
                if (i % 20 <= 19) {
                    builder.append(text.charAt(i));
                }
                if (i % 20 == 19) {
                    textList.add(builder.toString());
                }

            }
        }
        Log.e("textviewscroll", "" + textList.size());


    }


    //下面代码是利用上面计算的显示行数，将文字画在画布上，实时更新。
    @Override
    public void onDraw(Canvas canvas) {
        Log.e("WWW---", "canvas" + step);
        Paint mPaint = new Paint();
        if (textList.size() == 0) return;

        mPaint.setTextSize(40f);//设置字体大小
        for (int i = 0; i < textList.size(); i++) {
            canvas.drawText(textList.get(i), 0, this.getHeight() + (i + 1) * mPaint.getTextSize() - step + 30, mPaint);
        }
        invalidate();

        step = step + 6f;
        if (step >= this.getHeight() + textList.size() * mPaint.getTextSize()) {
            step = 0;
        }
    }

}
