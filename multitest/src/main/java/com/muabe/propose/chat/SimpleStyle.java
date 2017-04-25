package com.muabe.propose.chat;

import android.graphics.Color;


public class SimpleStyle {
	private int color;
	private int backgroundColor;
	private float width;
	private float size;
	private float margin;
	private Align align;
	private float density=1f;
	public static enum Align{
		NONE,LEFT,CENTER,RIGHT
	}
	
	public SimpleStyle(int color, float width, float size, float margin) {
		this.color = color;
		this.width = width;
		this.size = size;
		this.margin = margin;
		this.backgroundColor = Color.parseColor("#00000000");
	}
	
	public SimpleStyle(int color, float width, float size, float margin, Align align) {
		this.color = color;
		this.width = width;
		this.size = size;
		this.margin = margin;
		this.align = align;
		this.backgroundColor = Color.parseColor("#00000000");
	}

	void setDensity(float density){
		this.density = density;
	}

	float getDensity(){
		return density;
	}

	public int getColor() {
		return color;
	}

	public SimpleStyle setColor(int color) {
		this.color = color;
		return this;
	}
	
	public int getBackgourndColor() {
		return backgroundColor;
	}

	public SimpleStyle setBackgourndColor(int color) {
		this.backgroundColor = color;
		return this;
	}

	public float getWidth() {
		return width*density;
	}

	public SimpleStyle setWidth(float width) {
		this.width = width;
		return this;
	}

	public float getSize() {
		return size*density;
	}

	public SimpleStyle setSize(int size) {
		this.size = size;
		return this;
	}

	public float getMargin() {
		return margin*density;
	}

	public SimpleStyle setMargin(float margin) {
		this.margin = margin;
		return this;
	}

	public Align getAlign() {
		return align;
	}

	public SimpleStyle setAlign(Align align) {
		this.align = align;
		return this;
	}
}
