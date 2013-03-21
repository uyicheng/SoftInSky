package com.example.sensordemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Paint.Style;
import android.view.View;


public class DrawView extends View {

	Paint paint = new Paint();

	Paint paintText = new Paint();

	Paint paintALine = new Paint();

	Paint paintOLine = new Paint();

	private int xA = 0;
	private int yA = 0;
	private int zA = 0;

	private int xO = 0;
	private int yO = 0;
	private int zO = 0;

	private int screenWidth = 0;
	private int screenHeight = 0;

	private int centerX = 0;
	private int centerY = 0;

	private int picecX = 0;
	private int picecY = 0;
	
	int r = 0;

	public DrawView(Context context, int screenWidth, int screenHeight) {

		super(context);

		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		PathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8 }, 1);
		paint.setPathEffect(effects);

		paintText.setColor(Color.BLACK);
		paintText.setTextSize(22);

		paintALine.setColor(Color.BLUE);
		paintALine.setAntiAlias(true);
		paintALine.setStyle(Style.STROKE);

		paintOLine.setColor(Color.GREEN);
		paintOLine.setAntiAlias(true);
		paintOLine.setStyle(Style.STROKE);

		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;

		centerX = screenWidth / 2;
		centerY = screenHeight / 2;

		picecX = centerX / 10;
		picecY = centerY / 10;

		xA = centerX;
		yA = centerY;

		xO = centerX;
		yO = centerY;
		
		r = screenWidth / 3;

	}

	public void setAPosition(int x, int y, int z) {
		this.xA = x;
		this.yA = y;
		this.zA = z;
	}

	public void setOPosition(int x, int y, int z) {
		this.xO = x;
		this.yO = y;
		this.zO = z;
	}

	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {

		canvas.drawLine(screenWidth / 2, 0, screenWidth / 2, screenHeight, paint);
		canvas.drawLine(0, screenHeight / 2, screenWidth, screenHeight / 2, paint);

		//â¡ë¨ÉZÉìÉTÅ[ÉâÉCÉì
		//canvas.drawLine(centerX, centerY, (centerX - xA * picecX), (centerY - yA * picecY), paintALine);
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gps);
		//canvas.drawBitmap(bitmap, (centerX - bitmap.getWidth()/2), (centerY - bitmap.getHeight()/2),null);
		canvas.drawBitmap(bitmap, (centerX - xA * picecX - bitmap.getWidth()/2), (centerY + yA * picecY - bitmap.getHeight()/2),null);

		double x = 0;
		double alpha = 0;
		

		for (int i = 0; i < 359; i++) {

			alpha = Math.toRadians(i);
			x = Math.sin(alpha);
			double a1 = (-1) * x * r;
			x = Math.cos(alpha);
			double b1 = x * r;

			alpha = Math.toRadians(i + 1);
			x = Math.sin(alpha);
			double a2 = (-1) * x * r;
			x = Math.cos(alpha);
			double b2 = x * r;

			canvas.drawLine((centerX - (int) a1), (centerY - (int) b1), (centerX - (int) a2), (centerY - (int) b2), paintOLine);
		}

		if (xO != centerX) {

			//xyStaticShow(canvas);
			directionStaticShow(canvas);
		}
	}

	/**
	 * 
	 * @param canvas
	 */
	private void xyStaticShow(Canvas canvas) {

		double x = 0;
		double r = 0;
		double alpha = 0;
		
		canvas.drawText("ñk", screenWidth / 2, 20, paintText);
		canvas.drawText("ìå", screenWidth - 20, screenHeight / 2, paintText);
		canvas.drawText("êº", 0, screenHeight / 2, paintText);
		canvas.drawText("ìÏ", screenWidth / 2, screenHeight - 55, paintText);

		alpha = Math.toRadians(xO);

		x = Math.sin(alpha);
		double a = (-1) * x * r;
		// System.out.println("a>>>>" + a);

		x = Math.cos(alpha);
		double b = x * r;
		// System.out.println("b>>>>" + b);

		canvas.drawLine(centerX, centerY, (centerX - (int) a), (centerY - (int) b), paintOLine);

		if (xO > 0 & xO < 90) {
			canvas.drawText("ñkìå", (centerX - (int) a), (centerY - (int) b), paintText);
		} else if (xO > 90 & xO < 180) {
			canvas.drawText("ìÏìå", (centerX - (int) a), (centerY - (int) b), paintText);
		} else if (xO > 180 & xO < 270) {
			canvas.drawText("ìÏêº", (centerX - (int) a), (centerY - (int) b), paintText);
		} else if (xO > 270 & xO < 360) {
			canvas.drawText("ñkêº", (centerX - (int) a), (centerY - (int) b), paintText);
		}

	}

	private void directionStaticShow(Canvas canvas) {

		double x = 0;
		double alpha = 0;

		double na = 0;
		double nb = 0;

		double sa = 0;
		double sb = 0;

		double ea = 0;
		double eb = 0;

		double wa = 0;
		double wb = 0;

		// ñkç¿ïW
		alpha = Math.toRadians((-1) * xO);
		x = Math.sin(alpha);
		na = (-1) * x * r;
		x = Math.cos(alpha);
		nb = x * r;

		// ìÏç¿ïW
		sa = (-1) * na;
		sb = (-1) * nb;

		// êºç¿ïW
		alpha = Math.toRadians((-1) * (xO + 90));
		x = Math.sin(alpha);
		wa = (-1) * x * r;
		x = Math.cos(alpha);
		wb = x * r;

		// ìåç¿ïW
		ea = (-1) * wa;
		eb = (-1) * wb;

		canvas.drawText("ñk", (centerX - (int) na), (centerY - (int) nb), paintText);
		canvas.drawText("ìÏ", (centerX - (int) sa), (centerY - (int) sb), paintText);
		canvas.drawText("ìå", (centerX - (int) ea), (centerY - (int) eb), paintText);
		canvas.drawText("êº", (centerX - (int) wa), (centerY - (int) wb), paintText);

	}

}
