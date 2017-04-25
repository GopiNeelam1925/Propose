package com.muabe.propose.chat.skin;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;

import com.muabe.propose.chat.GraphSkin;
import com.muabe.propose.chat.SimpleStyle;


public class HLineSkin extends GraphSkin<Integer> {


	public HLineSkin(Integer[] lines, SimpleStyle style) {
		super(style);
		addItems(lines);
	}

	@Override
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);

		paint.setStyle(Paint.Style.STROKE);
		for(int i=0;i<getItemLength();i++){
			SimpleStyle style = getStyle();
			paint.setColor(style.getColor());
			paint.setStrokeWidth(style.getWidth());
			int index = getMaxMeasure()-getItems()[i];
			float height = getHeight();
			float gap = height*index/getMaxMeasure()+getTopMargin();
//			canvas.drawLine(0,gap, getDisplayWidth(),gap,paint);



			Path path = new Path();
			path.moveTo(0, gap);
			path.lineTo(getDisplayWidth(), gap);
			float[] intervals = new float[]{20.0f, 20.0f};
			float phase = 0;
			DashPathEffect dashPathEffect =  new DashPathEffect(intervals, phase);
			paint.setPathEffect(dashPathEffect);
			canvas.drawPath(path, paint);


//			float x = getDisplayWidth()/getDisplayCount()*3+(getDisplayWidth()/getDisplayCount())/2f;
//			paint.setColor(Color.GREEN);
//			canvas.drawLine(x,0,x,getHeight(),paint);
		}
	}

	public static SimpleStyle getSimpleStyle(int color,float width){
		return new SimpleStyle(color, width,0,0);
	}
}
