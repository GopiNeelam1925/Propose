package com.muabe.propose.chat.skin;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

import com.muabe.propose.chat.GraphSkin;
import com.muabe.propose.chat.SimpleStyle;


public class TextArraySkin extends GraphSkin<String> {

	private float maxBoradBottomMargin;
	private int lineMargin = 0;
	
	public TextArraySkin(String[] textArray, SimpleStyle style) {
		super(style);
		addItems(textArray);
//		maxBoradBottomMargin = style.getSize()+style.getMargin()+style.getSize()/5;
		maxBoradBottomMargin = style.getMargin();
	}

	@Override
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		float itemWidth = getItemWidth();

		for(int i=0;i<getItemLength();i++){
			SimpleStyle style = getStyle();

			float strX =  itemWidth*i;
			float endX =  itemWidth*i + itemWidth;
			float strY = getHeight()+getTopMargin();
			float endY = getHeight()+getTopMargin()+getBottomMargin();

			paint.setColor(style.getColor());
			paint.setTextSize(style.getSize());
			int lineLength = getItems()[i].split("\n").length;
			float div = 1+lineLength;
			float y = strY+(endY-strY)/div+style.getSize()/div;
			drawText(i, canvas, getItems()[i], strX, endX, y, getMargin(), paint);
		}
	}

	@Override
	public void selectDraw(int i, Canvas canvas, SimpleStyle style) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		float itemWidth = getItemWidth();

		float strX =  itemWidth*i;
		float endX =  itemWidth*i + itemWidth;
		float strY = getHeight()+getTopMargin();
		float endY = getHeight()+getTopMargin()+getBottomMargin();

		drawRect(true, i, canvas, (int) strX, (int) strY, (int) endX, (int) endY, paint);

		paint.setColor(style.getColor());
		paint.setTextSize(style.getSize());
		int lineLength = getItems()[i].split("\n").length;
		float div = ((float)lineLength+1f);
		float y = strY+(endY-strY)/div+style.getSize()/div;
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		drawText(true, i, canvas, getItems()[i], strX, endX, y, getMargin(), paint);
	}

	@Override
	public float getBoradBottomMargin() {
		return maxBoradBottomMargin;
	}
	
	public static SimpleStyle getSimpleStyle(int color, float textSize, float topMargin){
		return new SimpleStyle(color,0,textSize,topMargin);
	}
	
	private Align getPaintAlign(SimpleStyle.Align al){
		if(al==null){
			return Align.CENTER;
		}else if(SimpleStyle.Align.LEFT == al){
			return Align.LEFT;
		}else if(SimpleStyle.Align.CENTER == al){
			return Align.CENTER;
		}else if(SimpleStyle.Align.RIGHT == al){
			return Align.RIGHT;
		}else{
			return Align.CENTER;
		}
	}

	public void setLineMargin(int margin){
		this.lineMargin = margin;
	}

	private float getMargin(){
		return (float)lineMargin*getDensity();
	}
}
