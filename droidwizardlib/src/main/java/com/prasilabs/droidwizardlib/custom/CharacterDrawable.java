package com.prasilabs.droidwizardlib.custom;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;

import java.util.Random;

public class CharacterDrawable extends ColorDrawable {

    public static final int SHAPE_RECT = 1;
    public static final int SHAPE_CIRCLE = 2;

    private static final int STROKE_WIDTH = 10;
    private static final float SHADE_FACTOR = 0.9f;

    private final char character;
    private final Paint textPaint;
    private final Paint background;
    private int shape = SHAPE_CIRCLE;

    public CharacterDrawable(String name, int shape)
    {
        super();

        if(shape > 0) {
            this.shape = shape;
        }

        if(!TextUtils.isEmpty(name))
        {
            this.character = (name.toUpperCase().charAt(0));
        }
        else
        {
            this.character = 'O';
        }
        this.textPaint = new Paint();
        this.background = new Paint();

        // text paint settings
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);

        // border paint settings
        Random rnd = new Random();
        int[] goodcolors = {Color.argb(255, 26, 188, 156),
                Color.argb(255, 52, 152, 219),
                Color.argb(255, 155, 89, 182), Color.argb(255, 241, 196, 15),
                Color.argb(255, 230, 126, 34),
                Color.argb(255, 231, 76, 60),
                Color.argb(255, 41, 128, 185),
                Color.argb(255, 149, 165, 166), Color.argb(255, 192, 57, 43), Color.argb(255, 243, 156, 18)};
        int color = goodcolors[Math.abs(rnd.nextInt())%10];
        background.setColor(getDarkerShade(color));
        background.setStyle(Paint.Style.FILL);
        background.setStrokeWidth(STROKE_WIDTH);
    }

    private int getDarkerShade(int color) {
        return Color.rgb((int) (SHADE_FACTOR * Color.red(color)),
                (int) (SHADE_FACTOR * Color.green(color)),
                (int) (SHADE_FACTOR * Color.blue(color)));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // draw border
        Rect rect = getBounds();
        if(shape == SHAPE_RECT) {
            canvas.drawRect(getBounds(), background);
        } else {
            float centerX = rect.centerX();
            float centerY = rect.centerY();
            float radius = rect.height() / 2;
            canvas.drawCircle(centerX, centerY, radius, background);
        }
        // draw text
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        textPaint.setTextSize(height / 2);
        canvas.drawText(String.valueOf(character), width / 2, height / 2 - ((textPaint.descent() + textPaint.ascent()) / 2), textPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        textPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        textPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}