package com.muabe.propose.chat.skin;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.muabe.propose.chat.GraphSkin;
import com.muabe.propose.chat.SimpleStyle;


public class VLineSkin extends GraphSkin {

	private int lineLength;
	public VLineSkin(int lineLength, SimpleStyle style) {
		super(style);
		this.lineLength = lineLength;
	}

	@Override
	public void draw(Canvas canvas) {
		Paint Pnt = new Paint();
		Pnt.setAntiAlias(true);
		float itemWidth = getItemWidth();
		for(int i=0;i<lineLength;i++){
			SimpleStyle style = getStyle();
			Pnt.setColor(style.getColor());
			Pnt.setStrokeWidth(style.getWidth());
			float height = getHeight()+getTopMargin();
			float strX = itemWidth*i+itemWidth/2f;
			float endX = itemWidth*i+itemWidth/2f + style.getWidth()/2f;

			if(-itemWidth<endX) {
				drawLine(i, canvas, strX, getTopMargin(), strX, height, Pnt);
			}

		}
	}


	@Override
	public void selectDraw(int i, Canvas canvas, SimpleStyle style) {
		Paint Pnt = new Paint();
		Pnt.setAntiAlias(true);
		float itemWidth = getItemWidth();
		Pnt.setColor(style.getColor());
		Pnt.setStrokeWidth(style.getWidth());
		float height = getHeight()+getTopMargin();
		float strX = itemWidth*i+itemWidth/2f;
		float endX = itemWidth*i+itemWidth/2f + style.getWidth()/2f;
		if(-itemWidth<endX) {

			drawLine(true, i, canvas, strX, getTopMargin(), strX, height, Pnt);
		}
	}

	public void setLineLength(int lineLength){
		this.lineLength = lineLength;
	}

	@Override
	public int getItemLength() {
		return lineLength;
	}

	public static SimpleStyle getSimpleStyle(int color, float displayWidth){
		return new SimpleStyle(color, displayWidth,0,0);
	}
	
	public static SimpleStyle getSimpleStyle(int color,float displayWidth, SimpleStyle.Align align){
		return new SimpleStyle(color, displayWidth,0,0,align);
	}


}
