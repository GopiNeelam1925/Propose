package com.muabe.propose.chat.skin;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.muabe.propose.chat.GraphSkin;
import com.muabe.propose.chat.SimpleStyle;


public class LineGraphSkin extends GraphSkin<Integer> {

	private CircleGraphEl circle;
	private LineGraphEl line;


	SimpleStyle cStyle, lStyle, selectCStyle, selectLStyle;

	public LineGraphSkin(Integer[] data,SimpleStyle circleStyle, SimpleStyle lineStyle){
		super(circleStyle);
		addItems(data);
		this.cStyle = circleStyle;
		this.lStyle = lineStyle;
		circle = new CircleGraphEl();
		line = new LineGraphEl();
	}

	@Override
	public void draw(Canvas canvas) {
		line.draw(canvas);
		circle.draw(canvas);
	}


	@Override
	public void selectDraw(int index, Canvas canvas, SimpleStyle style) {
		line.selectDraw(true, index, canvas, null);
		circle.selectDraw(index, canvas, style);
	}

	public static SimpleStyle getLineSimpleStyle(int color, float displayWidth){
		return new SimpleStyle(color, displayWidth,0,0);
	}
	public static SimpleStyle getCircleSimpleStyle(int backgroundColor, int color,float displayWidth, float circleSize){
		return new SimpleStyle(color, displayWidth,circleSize,0).setBackgourndColor(backgroundColor);
	}

	private class LineGraphEl {
		Paint paint;
		float preX=0;
		float preY=0;

		LineGraphEl(){
			paint = new Paint();
			paint.setAntiAlias(true);
		}
		public void draw(Canvas canvas) {
			preX=0;
			preY=0;
			for(int i=0;i<getItemLength();i++){
				selectDraw(false, i, canvas, null);
			}
		}

		public void selectDraw(boolean isSelect, int i, Canvas canvas, SimpleStyle seletStyle) {
			float itemWidth = getItemWidth();
			SimpleStyle style = getCustomStyle(lStyle);
			SimpleStyle circleStyle = getCustomStyle(cStyle);
			paint.setColor(style.getColor());
			paint.setStrokeWidth(style.getWidth());
			float height = getHeight();
			float x = itemWidth*i+itemWidth/2f;
			float y = height*(getMaxMeasure()-getItems()[i]) / getMaxMeasure() + getTopMargin();

			if(i!=0){
				float dx =x-preX;
				float dy = preY-y;
				float R = (float)Math.sqrt(dx*dx+dy*dy);
				float x0 =circleStyle.getSize()*dx/R;
				float y0 =circleStyle.getSize()*dy/R;
				drawLine(isSelect, i, canvas,preX+x0,preY-y0,x-x0,y+y0,paint);
			}
			preX = x;
			preY = y;
		}
	}

	private class CircleGraphEl{
		public void draw(Canvas canvas) {
			Paint paint = new Paint();
			paint.setAntiAlias(true);

            float itemWidth = getItemWidth();
			for(int i=0;i<getItemLength();i++){
				SimpleStyle style = getCustomStyle(cStyle);
				if(getMaxMeasure() == -1) continue;
				float height = getHeight();
				float x = itemWidth*i+itemWidth/2f;
				float y = height*(getMaxMeasure()-getItems()[i]) / getMaxMeasure()+getTopMargin();
                if(-itemWidth<x) {
					paint.setStrokeWidth(style.getWidth());
					paint.setColor(style.getBackgourndColor());
					paint.setStyle(Paint.Style.FILL);
					drawCircle(i, canvas, x, y, style.getSize(), paint);
					paint.setColor(style.getColor());
					paint.setStyle(Paint.Style.STROKE);
					drawCircle(i, canvas, x, y, style.getSize(), paint);
                }
			}
		}

		public void selectDraw(int i, Canvas canvas, SimpleStyle seletStyle) {
			Paint paint = new Paint();
			paint.setAntiAlias(true);

			float itemWidth = getItemWidth();
			SimpleStyle style = seletStyle;
			float height = getHeight();
			float x = itemWidth*i+itemWidth/2f;
			float y = height*(getMaxMeasure()-getItems()[i]) / getMaxMeasure()+getTopMargin();
			if(-itemWidth<x) {
				paint.setStrokeWidth(style.getWidth());
				paint.setColor(style.getBackgourndColor());
				paint.setStyle(Paint.Style.FILL);
				drawCircle(true, i, canvas, x, y, style.getSize(), paint);
				paint.setColor(style.getColor());
				paint.setStyle(Paint.Style.STROKE);
				drawCircle(true, i, canvas, x, y, style.getSize(), paint);
			}
		}
	}


}
