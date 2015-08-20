package com.example.batterybindview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class BatteryImgView extends ImageView {
	Handler handler = new Handler() {
		public void handleMessage(Message paramAnonymousMessage) {
			invalidate();
		}
	};
	private float percent = 0.0F;
	private boolean bCharging = true;

	public BatteryImgView(Context cx) {
		super(cx);
	}

	public BatteryImgView(Context cx, AttributeSet attr) {
		super(cx, attr);
	}

	public void drawByScale(float percent) {
		if (percent < 0.0F) {
			percent = 0.0F;
		}

		this.percent = percent;
		this.handler.sendEmptyMessage(0);
		if (percent > 0.0F) {
		}
	}

	public float getPercent() {
		return this.percent;
	}

	public void setStatus(boolean charge) {
		bCharging = charge;
	}

	final float margin = 2f; // this is for the roundRect's Round, 这个为固定,就不允许修改了
	final float SPACE_LEFT = 25f; // 配置 电源在整个view中 左侧预留大小
	final float SPACE_RIGHT = 0f; // 配置电源在整个view中 右侧预留大小

	/**
	 * @return 返回指定笔和指定字符串的长度
	 */
	public static float getFontlength(Paint paint, String str) {
		return paint.measureText(str);
	}

	/**
	 * @return 返回指定笔的文字高度
	 */
	public static float getFontHeight(Paint paint) {
		FontMetrics fm = paint.getFontMetrics();
		return fm.descent - fm.ascent;
	}

	/**
	 * @return 返回指定笔离文字顶部的基准距离
	 */
	public static float getFontLeading(Paint paint) {
		FontMetrics fm = paint.getFontMetrics();
		return fm.leading - fm.ascent;
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint = new Paint();
		paint.setAntiAlias(true);// 设置画笔的锯齿效果
		paint.setStrokeWidth(1f);

		float w_Whole = getWidth();
		float h_Whole = getHeight();

		// bg
		// RectF rt_bg = new RectF(0f, 0f, w_Whole, h_Whole);
		// paint.setStyle(Paint.Style.FILL);
		// paint.setColor(Color.rgb(0, 0, 0));
		// canvas.drawRoundRect(rt_bg, 0, 0, paint);

		// 文字
		paint.setAntiAlias(true);
		// paint.setFilterBitmap(true);//对位图进行滤波处理
		String str = String.format("%d%%", (int) percent);
		float tX = (SPACE_LEFT - getFontlength(paint, str)) / 2;
		float tY = (h_Whole - getFontHeight(paint)) / 2 + getFontLeading(paint);
		paint.setStyle(Paint.Style.STROKE);
		paint.setTextSize(10);
		paint.setColor(Color.rgb(255, 255, 255));

		String familyName = "宋体";
		Typeface font = Typeface.create(familyName, Typeface.NORMAL);
		paint.setTypeface(font);

		canvas.drawText(str, tX, tY, paint);

		// body
		float body_left = margin + SPACE_LEFT;
		float body_right = w_Whole - /* body_left */margin - SPACE_RIGHT - 1; // 这儿的减1是为了让header更宽一点儿，看起来好看
		float body_bottom = h_Whole - margin;
		float body_top = margin;
		RectF rt_body = new RectF(body_left, body_top, body_right, body_bottom); // 左上右下（均空出
																					// margin）

		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.rgb(255, 255, 255));
		canvas.drawRoundRect(rt_body, 2, 2, paint);

		// header
		float w_header = 4f; // 固定宽度
		float h_header = h_Whole / 4f; // 高度计算固定
		float head_top = (h_Whole - h_header) / 2f;
		float head_left = body_right;
		float head_right = body_right + w_header;
		float head_bottom = head_top + h_header;
		RectF rt_header = new RectF(head_left, head_top, head_right,
				head_bottom); // 左上右下

		paint.setColor(Color.rgb(255, 255, 255));
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRoundRect(rt_header, 2, 2, paint);

		// fill, 填充的要在原body内部进行，上下左右分别隔2个dp
		float fill_margin = 2f;
		float fill_left = body_left + fill_margin;
		float fill_top = body_top + fill_margin;
		float fill_bottom = body_bottom - fill_margin;
		float fill_right = fill_left
				+ (body_right - body_left - fill_margin * 2) * percent * 0.01f;
		RectF rt_fill = new RectF(fill_left, fill_top, fill_right, fill_bottom);

		paint.setStyle(Paint.Style.FILL);
		if (bCharging) {
			paint.setColor(Color.rgb(0, 255, 0));
		} else {
			if (percent < 20) {
				paint.setColor(Color.rgb(255, 0, 0));
			} else {
				paint.setColor(Color.rgb(255, 255, 255));
			}
		}

		canvas.drawRoundRect(rt_fill, 1, 1, paint);
	}

}
