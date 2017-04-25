package com.muabe.propose.chat.skin;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.muabe.propose.chat.GraphSkin;
import com.muabe.propose.chat.SimpleStyle;


public class BarGraphSkin extends GraphSkin<Integer> {


	public BarGraphSkin(Integer[] data, SimpleStyle defalutStyle) {
		super(defalutStyle);
		addItems(data);
	}

	@Override
	public void draw(Canvas canvas) {
		if(getMaxMeasure()<=0)
			return;

		Paint Pnt = new Paint();
		Pnt.setAntiAlias(true);
		float itemWidth = getItemWidth();
		for(int i = 0; i< getItemLength(); i++){
			SimpleStyle style = getStyle();
			Pnt.setColor(style.getColor());
			Pnt.setStrokeWidth(style.getWidth());

			float strX = itemWidth*(i+0.5f) - style.getWidth()/2f;
			float endX = strX+ style.getWidth();
			float height = getHeight();
			float y = height*(getMaxMeasure()-getItems()[i])/getMaxMeasure()+getTopMargin();

			if(-itemWidth<endX) {
				drawRect(i, canvas, strX, y, endX, (height+getTopMargin()), Pnt);
			}
		}
	}

	public static SimpleStyle getSimpleStyle(int color, float displayWidth){
		return new SimpleStyle(color, displayWidth,0,0);
	}

	@Override
	public void selectDraw(int i, Canvas canvas, SimpleStyle style) {
		Paint Pnt = new Paint();
		Pnt.setAntiAlias(true);
		float itemWidth = getItemWidth();

		Pnt.setColor(style.getColor());
		Pnt.setStrokeWidth(style.getWidth());

		float strX = itemWidth*i+itemWidth/2f - style.getWidth()/2f;
		float endX = strX+ style.getWidth();
		float height = getHeight();
		float y = height*(getMaxMeasure()-getItems()[i])/getMaxMeasure()+getTopMargin();

		if(-itemWidth<endX) {
			drawRect(true, i, canvas, strX, y, endX, (height+getTopMargin()), Pnt);
		}
	}
}
