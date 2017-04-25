package com.muabe.propose.chat;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import java.util.LinkedHashMap;
import java.util.Set;

public class GraphBoard extends View{
	protected float positionX;
	protected float maxTopMargin;
	protected float maxBottomMargin;
	protected int maxMeasure;
	private LinkedHashMap<String, GraphSkin> skins = new LinkedHashMap<>();
	private ScrollGestureDetector detector;
	protected int displayCount;
	protected boolean reverse = false;
	protected int paddingCount = 0;
	private ValueAnimator flingAnimator;
	private ValueAnimator centerAnimator;
	private boolean isLayoutChange = false;

	private GestureStatus gestureStatus = GestureStatus.NONE;
	private OnSelectListener onSelectListener;

	private int lastSelectIndex = 0;

	public interface OnSelectListener{
		void onSelected(int index);
		void onSelecting(int index);
	}

	enum GestureStatus{
		NONE,
		DOWN,
		FLING,
		SCROLL
	}

	public GraphBoard(Context context) {
		super(context);
	}

	public GraphBoard(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GraphBoard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public GraphBoard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public void init(final int displayCount, int maxMeasure){
		positionX = 0;
		lastSelectIndex = 0;
		maxBottomMargin = 0;
		this.maxMeasure = maxMeasure;
		this.displayCount=displayCount;
		skins.clear();
		detector = new ScrollGestureDetector(getContext(), new ScrollGestureListener(){
			@Override
			public boolean onDown(MotionEvent e) {
				gestureStatus = GestureStatus.DOWN;
				if(flingAnimator!=null && flingAnimator.isRunning()){
					flingAnimator.cancel();
				}
				if(centerAnimator!=null && centerAnimator.isRunning()){
					centerAnimator.cancel();
				}
				return true;
			}

			@Override
			public boolean onUp(MotionEvent event) {
				if(gestureStatus!= GestureStatus.FLING) {
					if(!startCenterAnim(lastSelectIndex)){
						return true;
					}
				}
				return super.onUp(event);
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				gestureStatus = GestureStatus.SCROLL;
				if(reverse){
					distanceX = -distanceX;
				}
				setPositionX(positionX - distanceX);
				invalidate();
				return true;
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				if(Math.abs(velocityX)<1000){
					return false;
				}
				gestureStatus = GestureStatus.FLING;
				final float max;
				long duration = (long)Math.abs(velocityX/30f);
				if(reverse) {
					max = -velocityX/80f;
					flingAnimator = ValueAnimator.ofFloat(0, -velocityX / 80f);
				}else{
					max = velocityX/80f;
					flingAnimator = ValueAnimator.ofFloat(0, velocityX / 80f);
				}
				flingAnimator.setInterpolator(null);
				flingAnimator.setDuration(duration);
				flingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						if(animation.getCurrentPlayTime()!=0) {
							float x = positionX+(max-(float) animation.getAnimatedValue());
							if(x<=0){
								x = 0f;
								setPositionX(x);
								animation.cancel();
								onSelected(0);
							}else if(x>=maxPositionX){
								x = maxPositionX;
								setPositionX(x);
								animation.cancel();
								onSelected(lastSelectIndex);
							}else{
								setPositionX(x);
							}
						}
					}
				});
				flingAnimator.addListener(new Animator.AnimatorListener() {
					boolean isCancel = false;
					@Override
					public void onAnimationStart(Animator animation) {

					}

					@Override
					public void onAnimationEnd(Animator animation) {
						if(gestureStatus != GestureStatus.NONE) {
							startCenterAnim(lastSelectIndex);
						}
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						gestureStatus = GestureStatus.NONE;
					}

					@Override
					public void onAnimationRepeat(Animator animation) {

					}
				});
				flingAnimator.start();
				return true;
			}

			@Override
			public boolean onSingleTapConfirmed(MotionEvent e) {
				return false;
			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}
		});
		detector.setIsLongpressEnabled(false);
		this.addOnLayoutChangeListener(new OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				isLayoutChange = true;
			}
		});
	}

	public void setOnSelectListener(OnSelectListener onSelectListener){
		this.onSelectListener = onSelectListener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return detector.onTouchEvent(event);
	}

	public void addSkin(String skinKey, GraphSkin skin){
		if(skin.getBoradTopMargin()>maxTopMargin){
			maxTopMargin = skin.getBoradTopMargin();
		}
		if(skin.getBoradBottomMargin()>maxBottomMargin){
			maxBottomMargin = skin.getBoradBottomMargin();
		}
		skins.put(skinKey,skin);
	}

	public void setMeasure(int maxMeasure){
		this.maxMeasure = maxMeasure;
	}

	public GraphSkin getSkin(String key){
		return skins.get(key);
	}
	
	public void setMaxTopMargin(int topMargin){
		maxTopMargin = topMargin;
	}
	public void setMaxBottomMargin(int bottomMargin){
		maxBottomMargin = bottomMargin;
	}

	public void setPaddingCount(int itemCount){
		this.paddingCount = itemCount;
	}

	float minPositionX = 0f;
	float maxPositionX = 0f;

	protected void setMinPositionX(float x){
		this.minPositionX = x;
	}

	protected void setMaxPositionX(float x){
		this.maxPositionX = x;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Set<String> set = skins.keySet();
		String[] keys = new String[set.size()];
		set.toArray(keys);
		if(isLayoutChange) {
			sizeReset();
			isLayoutChange = false;
		}
		for(String key : keys){
			GraphSkin skin = skins.get(key);
			skin.setGraphBoard(this);
			skin.draw(canvas);
		}
	}

	float padding = 0f;
	protected float getPadding(){
		return padding;
	}

	@Override
	public void invalidate() {
		sizeReset();
		super.invalidate();
	}

	private void sizeReset(){
		Set<String> set = skins.keySet();
		String[] keys = new String[set.size()];
		set.toArray(keys);
		for(String key : keys){
			GraphSkin skin = skins.get(key);
			skin.setGraphBoard(this);

			float tempPadding = skin.getItemWidth() * paddingCount;

			if(tempPadding > padding) {
				padding = tempPadding;
			}

			float max = skin.getWidth()-getWidth()+getPadding()*2;
			if(maxPositionX < max) {
				setMaxPositionX(max);
			}
		}
	}

	public void setPositionX(float x){
		if(x > minPositionX && x < maxPositionX) {
			this.positionX = x;
			invalidate();
		}else{
			if(x <= minPositionX && minPositionX != positionX){
				positionX = minPositionX;
				if(scrollListener!=null){
					scrollListener.onStart();
				}
				invalidate();
			}else if(x >= maxPositionX && maxPositionX != positionX){
				positionX = maxPositionX;
				if(scrollListener!=null){
					scrollListener.onEnd();
				}
				invalidate();
			}
		}
	}

	public float getPositionX(){
		return this.positionX;
	}

	OnScrollListener scrollListener;
	public void setOnScrollListener(OnScrollListener scrollListener){
		this.scrollListener = scrollListener;
	}
	public interface OnScrollListener{
		void onStart();
		void onEnd();
	}

	class ScrollGestureDetector extends GestureDetector {
		ScrollGestureListener listener;
		public ScrollGestureDetector(Context context, ScrollGestureListener listener) {
			super(context, listener);
			this.listener = listener;
		}


		@Override
		public boolean onTouchEvent(MotionEvent ev) {
			boolean result = super.onTouchEvent(ev);
			int action = ev.getAction();
			switch (action & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_UP: {
					result = listener.onUp(ev) || result;
					break;
				}
			}
			return result;
		}

	}

	class ScrollGestureListener extends GestureDetector.SimpleOnGestureListener{
		public boolean onUp(MotionEvent event){
			return false;
		}
	}

	public boolean startCenterAnim(final int index){
		float itemWidth = (float)getWidth()/(float)displayCount;
		float div = positionX%itemWidth;

		if(Math.abs(div)>itemWidth/2f){
			div = div-itemWidth;
		}
		if(Math.abs(div)<15){
			setPositionX(positionX-div);
			onSelected(index);
			return false;
		}
		float x = positionX-div;
		centerAnimator = ValueAnimator.ofFloat(positionX,x);
		centerAnimator.setInterpolator(new OvershootInterpolator());
		centerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float x = (float)animation.getAnimatedValue();
				setPositionX(x);
				if(x<=0){
					animation.cancel();
					onSelected(lastSelectIndex);
				}else if(x>=maxPositionX) {
					animation.cancel();
					onSelected(lastSelectIndex);
				}
			}
		});
		centerAnimator.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				if(gestureStatus != GestureStatus.NONE) {
					onSelected(index);
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				gestureStatus = GestureStatus.NONE;
			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
		centerAnimator
				.setDuration((long)Math.abs(div*5))
				.start();
		return true;
	}

	protected void onSelected(int index){
		gestureStatus = GestureStatus.NONE;
		if(onSelectListener!=null){
			onSelectListener.onSelected(index);
		}
	}

	protected void onSelecting(int index){
		lastSelectIndex = index;
		if(onSelectListener!=null){
			onSelectListener.onSelecting(index);
		}
	}
}
