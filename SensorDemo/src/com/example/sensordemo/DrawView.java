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

	// 座標ラインペイント
	Paint paint = new Paint();

	// 文字ペイント
	Paint paintText = new Paint();

	// 加速度位置ペイント
	Paint paintALine = new Paint();

	// 方向位置ペイント
	Paint paintOLine = new Paint();

	// 加速度位置の３つ座標
	private int xA = 0;
	private int yA = 0;
	private int zA = 0;

	// 方向位置の３つ座標
	private int xO = 0;
	private int yO = 0;
	private int zO = 0;

	// 端末横サイズ
	private int screenWidth = 0;
	// 端末縦サイズ
	private int screenHeight = 0;

	// 端末の中央座標
	private int centerX = 0;
	private int centerY = 0;

	// XY座標の最少単位
	private int picecX = 0;
	private int picecY = 0;

	// 方向円の半径
	int r = 0;

	public DrawView(Context context, int screenWidth, int screenHeight) {

		super(context);

		// 座標ペイント設定
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);// ラインのアンチエイリアシングを切り替える
		PathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8 }, 1);// 点ライン
		paint.setPathEffect(effects);

		// 文字ペイント設定
		paintText.setColor(Color.BLACK);
		paintText.setTextSize(22);

		// 加速度ペイント設定
		paintALine.setColor(Color.BLUE);
		paintALine.setAntiAlias(true);
		paintALine.setStyle(Style.STROKE);

		// 方向ペイント設定
		paintOLine.setColor(Color.GREEN);
		paintOLine.setAntiAlias(true);
		paintOLine.setStyle(Style.STROKE);

		// 端末の横、縦設定
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;

		// 端末中央座標設定
		centerX = screenWidth / 2;
		centerY = screenHeight / 2;

		// 座標の単位
		picecX = centerX / 10;
		picecY = centerY / 10;

		// 加速度X,Y座標初期値
		xA = centerX;
		yA = centerY;

		// 方向位置X,Y座標初期値
		xO = centerX;
		yO = centerY;

		// 方向円の半径
		r = screenWidth / 3;

	}

	/**
	 * 加速度座標の設定
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setAPosition(int x, int y, int z) {
		this.xA = x;
		this.yA = y;
		this.zA = z;
	}

	/**
	 * 方向座標の設定
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setOPosition(int x, int y, int z) {
		this.xO = x;
		this.yO = y;
		this.zO = z;
	}

	/**
	 * 描画
	 */
	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {

		// 座標の描画
		canvas.drawLine(screenWidth / 2, 0, screenWidth / 2, screenHeight, paint);
		canvas.drawLine(0, screenHeight / 2, screenWidth, screenHeight / 2, paint);

		// 方向矢印のロード
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gps);
		canvas.drawBitmap(bitmap, (centerX - xA * picecX - bitmap.getWidth() / 2), (centerY + yA * picecY - bitmap.getHeight() / 2), null);

		double x = 0;
		double alpha = 0;

		// 方向円の描画
		for (int i = 0; i < 359; i++) {

			// 円上前の角座標
			alpha = Math.toRadians(i);
			x = Math.sin(alpha);
			double a1 = (-1) * x * r;
			x = Math.cos(alpha);
			double b1 = x * r;

			// 円上次の角座標
			alpha = Math.toRadians(i + 1);
			x = Math.sin(alpha);
			double a2 = (-1) * x * r;
			x = Math.cos(alpha);
			double b2 = x * r;

			// 円上前の角から次の角へラインを描画
			canvas.drawLine((centerX - (int) a1), (centerY - (int) b1), (centerX - (int) a2), (centerY - (int) b2), paintOLine);
		}

		// 方向センサ有効の場合
		if (xO != centerX) {

			// xyStaticShow(canvas);
			// 方向の描画
			directionStaticShow(canvas);
		}
	}

	/**
	 * 円固定、矢印移動の処理
	 * 
	 * @param canvas
	 */
	private void xyStaticShow(Canvas canvas) {

		double x = 0;
		double r = 0;
		double alpha = 0;

		canvas.drawText("北", screenWidth / 2, 20, paintText);
		canvas.drawText("東", screenWidth - 20, screenHeight / 2, paintText);
		canvas.drawText("西", 0, screenHeight / 2, paintText);
		canvas.drawText("南", screenWidth / 2, screenHeight - 55, paintText);

		alpha = Math.toRadians(xO);

		x = Math.sin(alpha);
		double a = (-1) * x * r;
		// System.out.println("a>>>>" + a);

		x = Math.cos(alpha);
		double b = x * r;
		// System.out.println("b>>>>" + b);

		canvas.drawLine(centerX, centerY, (centerX - (int) a), (centerY - (int) b), paintOLine);

		if (xO > 0 & xO < 90) {
			canvas.drawText("北東", (centerX - (int) a), (centerY - (int) b), paintText);
		} else if (xO > 90 & xO < 180) {
			canvas.drawText("南東", (centerX - (int) a), (centerY - (int) b), paintText);
		} else if (xO > 180 & xO < 270) {
			canvas.drawText("南西", (centerX - (int) a), (centerY - (int) b), paintText);
		} else if (xO > 270 & xO < 360) {
			canvas.drawText("北西", (centerX - (int) a), (centerY - (int) b), paintText);
		}

	}

	/**
	 * 矢印固定、円移動の処理
	 * 
	 * @param canvas
	 */
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

		// 北座標
		alpha = Math.toRadians((-1) * xO);
		x = Math.sin(alpha);
		na = (-1) * x * r;
		x = Math.cos(alpha);
		nb = x * r;

		// 南座標
		sa = (-1) * na;
		sb = (-1) * nb;

		// 西座標
		alpha = Math.toRadians((-1) * (xO + 90));
		x = Math.sin(alpha);
		wa = (-1) * x * r;
		x = Math.cos(alpha);
		wb = x * r;

		// 東座標
		ea = (-1) * wa;
		eb = (-1) * wb;

		canvas.drawText("北", (centerX - (int) na), (centerY - (int) nb), paintText);
		canvas.drawText("南", (centerX - (int) sa), (centerY - (int) sb), paintText);
		canvas.drawText("東", (centerX - (int) ea), (centerY - (int) eb), paintText);
		canvas.drawText("西", (centerX - (int) wa), (centerY - (int) wb), paintText);

	}

}
