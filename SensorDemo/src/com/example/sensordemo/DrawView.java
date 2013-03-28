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

	// ���W���C���y�C���g
	Paint paint = new Paint();

	// �����y�C���g
	Paint paintText = new Paint();

	// �����x�ʒu�y�C���g
	Paint paintALine = new Paint();

	// �����ʒu�y�C���g
	Paint paintOLine = new Paint();

	// �����x�ʒu�̂R���W
	private int xA = 0;
	private int yA = 0;
	private int zA = 0;

	// �����ʒu�̂R���W
	private int xO = 0;
	private int yO = 0;
	private int zO = 0;

	// �[�����T�C�Y
	private int screenWidth = 0;
	// �[���c�T�C�Y
	private int screenHeight = 0;

	// �[���̒������W
	private int centerX = 0;
	private int centerY = 0;

	// XY���W�̍ŏ��P��
	private int picecX = 0;
	private int picecY = 0;

	// �����~�̔��a
	int r = 0;

	public DrawView(Context context, int screenWidth, int screenHeight) {

		super(context);

		// ���W�y�C���g�ݒ�
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);// ���C���̃A���`�G�C���A�V���O��؂�ւ���
		PathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8 }, 1);// �_���C��
		paint.setPathEffect(effects);

		// �����y�C���g�ݒ�
		paintText.setColor(Color.BLACK);
		paintText.setTextSize(22);

		// �����x�y�C���g�ݒ�
		paintALine.setColor(Color.BLUE);
		paintALine.setAntiAlias(true);
		paintALine.setStyle(Style.STROKE);

		// �����y�C���g�ݒ�
		paintOLine.setColor(Color.GREEN);
		paintOLine.setAntiAlias(true);
		paintOLine.setStyle(Style.STROKE);

		// �[���̉��A�c�ݒ�
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;

		// �[���������W�ݒ�
		centerX = screenWidth / 2;
		centerY = screenHeight / 2;

		// ���W�̒P��
		picecX = centerX / 10;
		picecY = centerY / 10;

		// �����xX,Y���W�����l
		xA = centerX;
		yA = centerY;

		// �����ʒuX,Y���W�����l
		xO = centerX;
		yO = centerY;

		// �����~�̔��a
		r = screenWidth / 3;

	}

	/**
	 * �����x���W�̐ݒ�
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
	 * �������W�̐ݒ�
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
	 * �`��
	 */
	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {

		// ���W�̕`��
		canvas.drawLine(screenWidth / 2, 0, screenWidth / 2, screenHeight, paint);
		canvas.drawLine(0, screenHeight / 2, screenWidth, screenHeight / 2, paint);

		// �������̃��[�h
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gps);
		canvas.drawBitmap(bitmap, (centerX - xA * picecX - bitmap.getWidth() / 2), (centerY + yA * picecY - bitmap.getHeight() / 2), null);

		double x = 0;
		double alpha = 0;

		// �����~�̕`��
		for (int i = 0; i < 359; i++) {

			// �~��O�̊p���W
			alpha = Math.toRadians(i);
			x = Math.sin(alpha);
			double a1 = (-1) * x * r;
			x = Math.cos(alpha);
			double b1 = x * r;

			// �~�㎟�̊p���W
			alpha = Math.toRadians(i + 1);
			x = Math.sin(alpha);
			double a2 = (-1) * x * r;
			x = Math.cos(alpha);
			double b2 = x * r;

			// �~��O�̊p���玟�̊p�փ��C����`��
			canvas.drawLine((centerX - (int) a1), (centerY - (int) b1), (centerX - (int) a2), (centerY - (int) b2), paintOLine);
		}

		// �����Z���T�L���̏ꍇ
		if (xO != centerX) {

			// xyStaticShow(canvas);
			// �����̕`��
			directionStaticShow(canvas);
		}
	}

	/**
	 * �~�Œ�A���ړ��̏���
	 * 
	 * @param canvas
	 */
	private void xyStaticShow(Canvas canvas) {

		double x = 0;
		double r = 0;
		double alpha = 0;

		canvas.drawText("�k", screenWidth / 2, 20, paintText);
		canvas.drawText("��", screenWidth - 20, screenHeight / 2, paintText);
		canvas.drawText("��", 0, screenHeight / 2, paintText);
		canvas.drawText("��", screenWidth / 2, screenHeight - 55, paintText);

		alpha = Math.toRadians(xO);

		x = Math.sin(alpha);
		double a = (-1) * x * r;
		// System.out.println("a>>>>" + a);

		x = Math.cos(alpha);
		double b = x * r;
		// System.out.println("b>>>>" + b);

		canvas.drawLine(centerX, centerY, (centerX - (int) a), (centerY - (int) b), paintOLine);

		if (xO > 0 & xO < 90) {
			canvas.drawText("�k��", (centerX - (int) a), (centerY - (int) b), paintText);
		} else if (xO > 90 & xO < 180) {
			canvas.drawText("�쓌", (centerX - (int) a), (centerY - (int) b), paintText);
		} else if (xO > 180 & xO < 270) {
			canvas.drawText("�쐼", (centerX - (int) a), (centerY - (int) b), paintText);
		} else if (xO > 270 & xO < 360) {
			canvas.drawText("�k��", (centerX - (int) a), (centerY - (int) b), paintText);
		}

	}

	/**
	 * ���Œ�A�~�ړ��̏���
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

		// �k���W
		alpha = Math.toRadians((-1) * xO);
		x = Math.sin(alpha);
		na = (-1) * x * r;
		x = Math.cos(alpha);
		nb = x * r;

		// ����W
		sa = (-1) * na;
		sb = (-1) * nb;

		// �����W
		alpha = Math.toRadians((-1) * (xO + 90));
		x = Math.sin(alpha);
		wa = (-1) * x * r;
		x = Math.cos(alpha);
		wb = x * r;

		// �����W
		ea = (-1) * wa;
		eb = (-1) * wb;

		canvas.drawText("�k", (centerX - (int) na), (centerY - (int) nb), paintText);
		canvas.drawText("��", (centerX - (int) sa), (centerY - (int) sb), paintText);
		canvas.drawText("��", (centerX - (int) ea), (centerY - (int) eb), paintText);
		canvas.drawText("��", (centerX - (int) wa), (centerY - (int) wb), paintText);

	}

}
