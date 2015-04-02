/*
 *    Copyright (C) 2015 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.h6ah4i.android.colortransitiondrawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.animation.Interpolator;

public class ColorTransitionDrawable extends Drawable {
    private static final int DEFAULT_DURATION = 300;

    private int mFromColor;
    private int mToColor;
    private int mCurrentColor;
    private int mDuration = DEFAULT_DURATION;
    private int mOriginalDuration = DEFAULT_DURATION;
    private long mStartTimeMillis;
    private Interpolator mPhaseInterpolator;
    private int mAlpha = 255;
    private Paint mPaint;
    private ColorFilter mColorFilter;
    private boolean mIsInterpolationRunning;

    public ColorTransitionDrawable() {
        this(Color.TRANSPARENT);
    }

    public ColorTransitionDrawable(int color) {
        super();

        mFromColor = mToColor = mCurrentColor = color;
        mPaint = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        final boolean done;

        if (mIsInterpolationRunning) {
            done = updateCurrentColor();
            mIsInterpolationRunning = (!done);
        } else {
            done = true;
        }

        // draw
        mPaint.setAlpha(mAlpha);
        mPaint.setColor(mCurrentColor);
        mPaint.setColorFilter(mColorFilter);

        canvas.drawRect(getBounds(), mPaint);

        // schedule invalidate
        if (!done) {
            invalidateSelf();
        }
    }

    private boolean updateCurrentColor() {
        // interpolate color
        final long curTimeMillis = SystemClock.uptimeMillis();
        final long duration = mOriginalDuration;
        long diffTimeMillis = (curTimeMillis - mStartTimeMillis);

        diffTimeMillis = Math.max(diffTimeMillis, 0);
        diffTimeMillis = Math.min(diffTimeMillis, duration);

        final float linearPhase;

        if (duration != 0) {
            linearPhase = (float) diffTimeMillis / duration;
        } else {
            linearPhase = 1.0f;
        }

        final float interpolatedPhase;
        if (mPhaseInterpolator != null) {
            interpolatedPhase = mPhaseInterpolator.getInterpolation(linearPhase);
        } else {
            interpolatedPhase = linearPhase;
        }

        mCurrentColor = interpolateColor(mFromColor, mToColor, interpolatedPhase);

        final boolean done = (linearPhase >= 1.0f);

        return done;
    }

    private static int interpolateColor(int from, int to, float phase) {
        if (phase <= 0.0f) {
            return from;
        }
        if (phase >= 1.0f) {
            return to;
        }

        final int fa, fr, fg, fb;
        final int ta, tr, tg, tb;
        int a, r, g, b;

        fa = (from >>> 24);
        fr = (from >>> 16) & 0xff;
        fg = (from >>> 8) & 0xff;
        fb = from & 0xff;

        ta = (to >>> 24);
        tr = (to >>> 16) & 0xff;
        tg = (to >>> 8) & 0xff;
        tb = to & 0xff;

        final float iphase = 1.0f - phase;

        r = (int) ((tr * phase) + (fr * iphase));
        g = (int) ((tg * phase) + (fg * iphase));
        b = (int) ((tb * phase) + (fb * iphase));
        a = (int) ((ta * phase) + (fa * iphase));

        return Color.argb(a, r, g, b);
    }

    public void setInterpolator(Interpolator interpolator) {
        if (mPhaseInterpolator == interpolator) {
            return;
        }

        mPhaseInterpolator = interpolator;
        invalidateSelf();
    }

    public void setDuration(int duration) {
        duration = Math.max(0, duration);

        if (mDuration == duration) {
            return;
        }

        mDuration = duration;
    }

    public void setColor(int color) {
        setColor(color, true);
    }

    public void setColor(int color, boolean animate) {
        setColor(color, (animate) ? mDuration : 0);
    }

    public void setColor(int color, int duration) {
        if (duration > 0) {
            setColorAnimate(color, duration);
        } else {
            setColorImmediate(color);
        }
    }

    private void setColorAnimate(int color, int duration) {
        if (mToColor == color) {
            return;
        }

        mOriginalDuration = duration;

        mFromColor = mCurrentColor;
        mToColor = color;

        // special handling for completely transparent color
        if (Color.alpha(mFromColor) == 0) {
            mFromColor = mToColor & 0x00ffffff;
        }
        if (Color.alpha(mToColor) == 0) {
            mToColor = mFromColor & 0x00ffffff;
        }

        mStartTimeMillis = SystemClock.uptimeMillis();
        mIsInterpolationRunning = true;

        invalidateSelf();
    }

    private void setColorImmediate(int color) {
        if (mCurrentColor == color) {
            return;
        }

        mOriginalDuration = 0;
        mFromColor = mToColor = mCurrentColor = color;
        mIsInterpolationRunning = false;

        invalidateSelf();
    }

    public int getColor() {
        return mToColor;
    }

    public int getCurrentColor() {
        return mCurrentColor;
    }

    @Override
    public void setAlpha(int alpha) {
        if (mAlpha == alpha) {
            return;
        }

        mAlpha = alpha;
        invalidateSelf();
    }

    public int getAlpha() {
        return mAlpha;
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if (mColorFilter == cf) {
            return;
        }

        mColorFilter = cf;
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return Color.alpha(mCurrentColor);
    }
}