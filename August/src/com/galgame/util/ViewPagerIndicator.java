package com.galgame.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ViewPagerIndicator extends View
{
	private int circleNum = 5;
	private int radius = 10;
	private Paint paint;
	private Paint mPaintSport;
	private float offset;

	public ViewPagerIndicator(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initPaint();
	}

	public void moveIndicator(int position)
	{
		offset = position * 3 * radius;
		invalidate();
	}

	public void moveIndicator(int position, float positionOffset)
	{
		offset = 3 * radius * (position + positionOffset);
		invalidate();
	}

	private void initPaint()
	{
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(0xff999999);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);

		mPaintSport = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintSport.setColor(0xffffffff);
		mPaintSport.setStyle(Paint.Style.FILL_AND_STROKE);

	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		int width = canvas.getWidth();
		canvas.translate(width / 2, radius * 2);
		for (int i = 0; i < circleNum; i++)
		{
			canvas.drawCircle((-1.5f * (circleNum - 1)) * radius + i * 3 * radius, 0, radius, paint);
		}

		canvas.drawCircle((-1.5f * (circleNum - 1)) * radius + offset, 0, radius, mPaintSport);

		super.onDraw(canvas);
	}

}
