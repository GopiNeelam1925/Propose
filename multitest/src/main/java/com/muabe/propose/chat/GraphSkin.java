package com.muabe.propose.chat;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class GraphSkin<T> {
	private GraphBoard graphBoard;
	protected ArrayList<SimpleStyle> list;
	private float density=1f;
	private T[] items;
	private int selectIndex = -1;
	private int seletPivot = 3;

	protected GraphSkin(SimpleStyle defalutStyle){
		this.defalutStyle = defalutStyle;
		list = new ArrayList<>();
	}

	protected abstract void draw(Canvas canvas);

	protected float getDensity(){
		return density;
	}

	void setGraphBoard(GraphBoard graphBoard){
		this.graphBoard = graphBoard;
		density = graphBoard.getContext().getResources().getDisplayMetrics().density;
	}

	protected float getTopMargin() {
		return graphBoard.maxTopMargin*getDensity();
	}

	protected float getBottomMargin() {
		return graphBoard.maxBottomMargin*getDensity();
	}

	protected float getWidth(){
		return getItemWidth()* getItemLength();
	}

	protected float getHeight() {
		return graphBoard.getHeight()-getTopMargin()-getBottomMargin();
	}


	protected int getDisplayWidth() {
		return graphBoard.getWidth();
	}

	protected int getDisplayCount(){
		return graphBoard.displayCount;
	}

	protected int getMaxMeasure() {
		return graphBoard.maxMeasure;
	}

	protected float getBoradTopMargin(){
		return 0f;
	}
	protected float getBoradBottomMargin(){
		return 0;
	}

	protected void clearList() {
	    list.clear();
	}

	protected float getItemWidth(){
		return  ((float)getDisplayWidth())/((float)getDisplayCount());
	}

	protected float getPositionX(){
		return graphBoard.positionX;
	}

	protected float getMaxPositionX(){
		return getWidth() - getDisplayWidth();
	}

	public T[] getItems(){
		return items;
	}

	public int getItemLength(){
		return items.length;
	}

	public int getSelectIndex(){
		return this.selectIndex;
	}


	public void addItems(T[] array){
		if(array.length>0) {
			if(items==null || items.length==0){
				items = array;
			}else {
				T[] result = (T[]) Array.newInstance(array[0].getClass(), items.length + array.length);
				System.arraycopy(items, 0, result, 0, items.length);
				System.arraycopy(array, 0, result, items.length, array.length);
				items = result;
			}
        }
	}

	protected SimpleStyle defalutStyle;
	public void setDefalutStyle(SimpleStyle style){
		this.defalutStyle = style;
	}

	protected SimpleStyle getStyle(){
		defalutStyle.setDensity(density);
		return defalutStyle;
	}

	protected float getCoords(float x){
		if(graphBoard.reverse){
			return x- graphBoard.positionX+ graphBoard.getPadding();
		}else{
			return getWidth()-x+ graphBoard.positionX-getMaxPositionX()- graphBoard.getPadding();
		}
	}

	private boolean isDrawPosition(float startX, float endX){
		return (endX >= 0 && startX <= getDisplayWidth());
	}

	public void drawLine(boolean isSelect, int index, Canvas canvas, float startX, float startY, float stopX, float stopY, Paint paint){
		float x1 = getCoords(startX);
		float x2 = getCoords(stopX);
		float y1 = startY;
		float y2 = stopY;
		if(!graphBoard.reverse){
			x1 = getCoords(stopX);
			x2 = getCoords(startX);
			y1 = stopY;
			y2 = startY;
		}
		if(isDrawPosition(x1,x2)) {
			if(isSelect || !select(index, canvas, x1, x2)) {
				canvas.drawLine(x1, y1, x2, y2, paint);
			}
		}
	}
	public void drawLine(int index, Canvas canvas, float startX, float startY, float stopX, float stopY, Paint paint){
		this.drawLine(false, index, canvas, startX, startY, stopX, stopY, paint);
	}

	public void drawText(boolean isSelect, int index, Canvas canvas, String text, float strX, float endX, float y, float lineMargin, Paint paint){
		float x1 = getCoords(strX);
		float x2 = getCoords(endX);
		if(!graphBoard.reverse) {
			x1 = getCoords(endX);
			x2 = getCoords(strX);
		}
		if(isDrawPosition(x1,x2)) {
			paint.setTextAlign(Paint.Align.CENTER);
			float x = x1+(x2-x1)/2f;
			if(isSelect || !select(index, canvas, x1, x2)) {
				setMultiLine(canvas, text, x, y, lineMargin, paint);
			}
		}

	}

	public void drawText(int index, Canvas canvas, String text, float strX, float endX, float y, float lineMargin, Paint paint){
		this.drawText(false, index, canvas, text, strX, endX, y, lineMargin, paint);
	}

	private void setMultiLine(Canvas canvas, String text, float x, float y, float lineMargin, Paint paint){
		for (String line: text.split("\n")) {
			canvas.drawText(line, x, y, paint);
			y += paint.descent() - paint.ascent()+lineMargin/2f;
		}
	}

	public void drawRect(boolean isSelect, int index, Canvas canvas, int strX, int strY, int endX, int endY, Paint paint){
		float x1 = getCoords(strX);
		float x2 = getCoords(endX);
		if(!graphBoard.reverse){
			x1 = getCoords(endX);
			x2 = getCoords(strX);
		}
		if(isDrawPosition(x1,x2)) {
			Rect r = new Rect();
			r.setEmpty();
			r.set((int) x1, (int) strY, (int) x2, (int) endY);
			if(isSelect || !select(index, canvas, x1, x2)){
				canvas.drawRect(r, paint);
			}

		}
	}

	public void drawRect(int index, Canvas canvas, int strX, int strY, int endX, int endY, Paint paint){
		this.drawRect(false, index, canvas, strX, strY, endX, endY, paint);
	}

	public void drawRect(int index, Canvas canvas, float strX, float strY, float endX, float endY, Paint paint){
		this.drawRect(false, index, canvas, (int)strX, (int)strY, (int)endX, (int)endY, paint);
	}

	public void drawRect(boolean isSelect, int index, Canvas canvas, float strX, float strY, float endX, float endY, Paint paint){
		this.drawRect(isSelect, index, canvas, (int)strX, (int)strY, (int)endX, (int)endY, paint);
	}

	public void drawCircle(boolean isSelect, int index, Canvas canvas, float x, float y, float size, Paint paint){
		float x1 = getCoords(x);
		if(isDrawPosition(x1,x1)) {
			if(isSelect || !select(index, canvas, x1, x1)){
				canvas.drawCircle(x1, y, size, paint);
			}
		}

	}

	public void drawCircle(int index, Canvas canvas, float x, float y, float size, Paint paint){
		this.drawCircle(false, index, canvas, x, y, size, paint);
	}

	public boolean select(int index, Canvas canvas, float x1, float x2){
		float cellWidth = getItemWidth();
		float selectX1 = cellWidth*seletPivot;
		float selectX2 = selectX1+ cellWidth;
		float center= getCoords(index*getItemWidth()+getItemWidth()/2);

//		Paint paint = new Paint();
//		paint.setStrokeWidth(2);
//		paint.setColor(Color.RED);
//		canvas.drawLine(selectX1, 0, selectX1, getHeight(), paint);
//		canvas.drawLine(selectX2, 0, selectX2, getHeight(), paint);
//		paint.setColor(Color.parseColor("#3300ff00"));
//		canvas.drawLine(center, 0, center, getHeight(), paint);

		if (contains(center, selectX1, selectX2)) {
			if (selectIndex != index) {
				this.selectIndex = index;
			}
			if (getSelectStyle() != null) {
				selectDraw(index, canvas, getSelectStyle());
				graphBoard.onSelecting(index);
				return true;
			}
		}
		return false;
	}

	private boolean contains(float x1, float selectX1, float selectX2){
		boolean contain =  x1 >= selectX1 && x1 <= selectX2;
		return contain;
	}

	private boolean contains(float x1, float x2, float selectX1, float selectX2){
		boolean contain =  x1 >= selectX1 && x1 <= selectX2;
		if(contain){
			return true;
		}
		contain = x2 >= selectX1 && x2 < selectX2;
		return contain;
	}

	SimpleStyle selectStyle;
	public void setSelectStyle(SimpleStyle simpleStyle){
		this.selectStyle = simpleStyle;
	}
	public SimpleStyle getSelectStyle(){
		if(selectStyle!=null){
			selectStyle.setDensity(density);
		}
		return selectStyle;
	}

	public SimpleStyle getCustomStyle(SimpleStyle simpleStyle){
		simpleStyle.setDensity(density);
		return simpleStyle;
	}

	public void selectDraw(int index, Canvas canvas, SimpleStyle style){

	}
}
