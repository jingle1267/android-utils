/**
 * Copyright 2014 Zhenguo Jin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.worthed.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * @author jingle1267@163.com
 * 
 * @description Bitmap工具类主要包括获取Bitmap和对Bitmap的操作
 * 
 */
public class BitmapUtil {

	private final boolean DEBUG = false;
	private final String TAG = BitmapUtil.class.getSimpleName();

	/**
	 * 图片压缩处理（使用Options的方法）
	 * 
	 * <br>
	 * <b>说明</b> 使用方法：
	 * 首先你要将Options的inJustDecodeBounds属性设置为true，BitmapFactory.decode一次图片 。
	 * 然后将Options连同期望的宽度和高度一起传递到到本方法中。
	 * 之后再使用本方法的返回值做参数调用BitmapFactory.decode创建图片。
	 * 
	 * <br>
	 * <b>说明</b> BitmapFactory创建bitmap会尝试为已经构建的bitmap分配内存
	 * ，这时就会很容易导致OOM出现。为此每一种创建方法都提供了一个可选的Options参数
	 * ，将这个参数的inJustDecodeBounds属性设置为true就可以让解析方法禁止为bitmap分配内存
	 * ，返回值也不再是一个Bitmap对象， 而是null。虽然Bitmap是null了，但是Options的outWidth、
	 * outHeight和outMimeType属性都会被赋值。
	 * 
	 * @param reqWidth
	 *            目标宽度,这里的宽高只是阀值，实际显示的图片将小于等于这个值
	 * @param reqHeight
	 *            目标高度,这里的宽高只是阀值，实际显示的图片将小于等于这个值
	 */
	public static BitmapFactory.Options calculateInSampleSize(
			final BitmapFactory.Options options, final int reqWidth,
			final int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > 400 || width > 450) {
			if (height > reqHeight || width > reqWidth) {
				// 计算出实际宽高和目标宽高的比率
				final int heightRatio = Math.round((float) height
						/ (float) reqHeight);
				final int widthRatio = Math.round((float) width
						/ (float) reqWidth);
				// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
				// 一定都会大于等于目标的宽和高。
				inSampleSize = heightRatio < widthRatio ? heightRatio
						: widthRatio;
			}
		}
		// 设置压缩比例
		options.inSampleSize = inSampleSize;
		options.inJustDecodeBounds = false;
		return options;
	}

	/**
	 * 获取一个指定大小的bitmap
	 * 
	 * @param res
	 *            Resources
	 * @param resId
	 *            图片ID
	 * @param reqWidth
	 *            目标宽度
	 * @param reqHeight
	 *            目标高度
	 */
	public static Bitmap bitmapFromResource(Resources res, int resId,
			int reqWidth, int reqHeight) {
		// BitmapFactory.Options options = new BitmapFactory.Options();
		// options.inJustDecodeBounds = true;
		// BitmapFactory.decodeResource(res, resId, options);
		// options = BitmapHelper.calculateInSampleSize(options, reqWidth,
		// reqHeight);
		// return BitmapFactory.decodeResource(res, resId, options);

		// 通过JNI的形式读取本地图片达到节省内存的目的
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		InputStream is = res.openRawResource(resId);
		return bitmapFromStream(is, null, reqWidth, reqHeight);
	}

	/**
	 * 获取一个指定大小的bitmap
	 * 
	 * @param reqWidth
	 *            目标宽度
	 * @param reqHeight
	 *            目标高度
	 */
	public static Bitmap bitmapFromFile(String pathName, int reqWidth,
			int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);
		options = calculateInSampleSize(options, reqWidth, reqHeight);
		return BitmapFactory.decodeFile(pathName, options);
	}

	/**
	 * 获取一个指定大小的bitmap
	 * 
	 * @param data
	 *            Bitmap的byte数组
	 * @param offset
	 *            image从byte数组创建的起始位置
	 * @param length
	 *            the number of bytes, 从offset处开始的长度
	 * @param reqWidth
	 *            目标宽度
	 * @param reqHeight
	 *            目标高度
	 */
	public static Bitmap bitmapFromByteArray(byte[] data, int offset,
			int length, int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, offset, length, options);
		options = calculateInSampleSize(options, reqWidth, reqHeight);
		return BitmapFactory.decodeByteArray(data, offset, length, options);
	}

	/**
	 * 获取一个指定大小的bitmap<br>
	 * 实际调用的方法是bitmapFromByteArray(data, 0, data.length, w, h);
	 * 
	 * @param is
	 *            从输入流中读取Bitmap
	 * @param reqWidth
	 *            目标宽度
	 * @param reqHeight
	 *            目标高度
	 */
	public static Bitmap bitmapFromStream(InputStream is, int reqWidth,
			int reqHeight) {
		byte[] data = FileUtils.input2byte(is);
		return bitmapFromByteArray(data, 0, data.length, reqWidth, reqHeight);
	}

	/**
	 * 获取一个指定大小的bitmap
	 * 
	 * @param is
	 *            从输入流中读取Bitmap
	 * @param outPadding
	 *            If not null, return the padding rect for the bitmap if it
	 *            exists, otherwise set padding to [-1,-1,-1,-1]. If no bitmap
	 *            is returned (null) then padding is unchanged.
	 * @param reqWidth
	 *            目标宽度
	 * @param reqHeight
	 *            目标高度
	 */
	public static Bitmap bitmapFromStream(InputStream is, Rect outPadding,
			int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(is, outPadding, options);
		options = calculateInSampleSize(options, reqWidth, reqHeight);
		return BitmapFactory.decodeStream(is, outPadding, options);
	}

	public Bitmap combineImages(Bitmap bgd, Bitmap fg) {
		Bitmap bmp;

		int width = bgd.getWidth() > fg.getWidth() ? bgd.getWidth() : fg
				.getWidth();
		int height = bgd.getHeight() > fg.getHeight() ? bgd.getHeight() : fg
				.getHeight();

		bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Paint paint = new Paint();
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

		Canvas canvas = new Canvas(bmp);
		canvas.drawBitmap(bgd, 0, 0, null);
		canvas.drawBitmap(fg, 0, 0, paint);

		return bmp;
	}

	public Bitmap combineImagesToSameSize(Bitmap bgd, Bitmap fg) {
		Bitmap bmp;

		int width = bgd.getWidth() < fg.getWidth() ? bgd.getWidth() : fg
				.getWidth();
		int height = bgd.getHeight() < fg.getHeight() ? bgd.getHeight() : fg
				.getHeight();

		if (fg.getWidth() != width && fg.getHeight() != height) {
			fg = zoomBitmap(fg, width, height);
		}
		if (bgd.getWidth() != width && bgd.getHeight() != height) {
			bgd = zoomBitmap(bgd, width, height);
		}

		bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Paint paint = new Paint();
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

		Canvas canvas = new Canvas(bmp);
		canvas.drawBitmap(bgd, 0, 0, null);
		canvas.drawBitmap(fg, 0, 0, paint);

		return bmp;
	}

	public Bitmap processImage(Bitmap bitmap, float radiusParam) {
		Bitmap bmp;

		bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
				Bitmap.Config.ARGB_8888);
		BitmapShader shader = new BitmapShader(bitmap,
				BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);

		float radius = Math.min(bitmap.getWidth(), bitmap.getHeight())
				/ radiusParam;
		Canvas canvas = new Canvas(bmp);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(shader);

		RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
		canvas.drawRoundRect(rect, radius, radius, paint);

		return bmp;
	}

	/**
	 * 放大缩小图片
	 * 
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidht = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidht, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newbmp;
	}

	/**
	 * 将Drawable转化为Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;

	}

	/**
	 * 获得圆角图片的方法
	 * 
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 获得带倒影的图片方法
	 * 
	 * @param bitmap
	 * @return
	 */
	public Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	/**
	 * 压缩图片大小
	 * 
	 * @param image
	 * @return
	 */
	public Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 将彩色图转换为灰度图
	 * 
	 * @param img
	 *            位图
	 * @return 返回转换好的位图
	 */
	public Bitmap convertGreyImg(Bitmap img) {
		int width = img.getWidth(); // 获取位图的宽
		int height = img.getHeight(); // 获取位图的高

		int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

		img.getPixels(pixels, 0, width, 0, 0, width, height);
		int alpha = 0xFF << 24;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];

				int red = ((grey & 0x00FF0000) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);

				grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
				grey = alpha | (grey << 16) | (grey << 8) | grey;
				pixels[width * i + j] = grey;
			}
		}
		Bitmap result = Bitmap.createBitmap(width, height, Config.RGB_565);
		result.setPixels(pixels, 0, width, 0, 0, width, height);
		return result;
	}

	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	/**
	 * Returns a Bitmap representing the thumbnail of the specified Bitmap. The
	 * size of the thumbnail is defined by the dimension
	 * android.R.dimen.launcher_application_icon_size.
	 * 
	 * This method is not thread-safe and should be invoked on the UI thread
	 * only.
	 * 
	 * @param bitmap
	 *            The bitmap to get a thumbnail of.
	 * @param context
	 *            The application's context.
	 * 
	 * @return A thumbnail for the specified bitmap or the bitmap itself if the
	 *         thumbnail could not be created.
	 */
	public Bitmap createBitmapThumbnail(Bitmap bitmap, Context context) {
		int sIconWidth = -1;
		int sIconHeight = -1;
		if (sIconWidth == -1) {
			final Resources resources = context.getResources();
			sIconWidth = sIconHeight = (int) resources
					.getDimension(android.R.dimen.app_icon_size);
		}

		final Paint sPaint = new Paint();
		final Rect sBounds = new Rect();
		final Rect sOldBounds = new Rect();
		Canvas sCanvas = new Canvas();

		int width = sIconWidth;
		int height = sIconHeight;

		sCanvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG,
				Paint.FILTER_BITMAP_FLAG));

		final int bitmapWidth = bitmap.getWidth();
		final int bitmapHeight = bitmap.getHeight();

		if (width > 0 && height > 0) {
			if (width < bitmapWidth || height < bitmapHeight) {
				final float ratio = (float) bitmapWidth / bitmapHeight;

				if (bitmapWidth > bitmapHeight) {
					height = (int) (width / ratio);
				} else if (bitmapHeight > bitmapWidth) {
					width = (int) (height * ratio);
				}

				final Bitmap.Config c = (width == sIconWidth && height == sIconHeight) ? bitmap
						.getConfig() : Bitmap.Config.ARGB_8888;
				final Bitmap thumb = Bitmap.createBitmap(sIconWidth,
						sIconHeight, c);
				final Canvas canvas = sCanvas;
				final Paint paint = sPaint;
				canvas.setBitmap(thumb);
				paint.setDither(false);
				paint.setFilterBitmap(true);
				sBounds.set((sIconWidth - width) / 2,
						(sIconHeight - height) / 2, width, height);
				sOldBounds.set(0, 0, bitmapWidth, bitmapHeight);
				canvas.drawBitmap(bitmap, sOldBounds, sBounds, paint);
				return thumb;
			} else if (bitmapWidth < width || bitmapHeight < height) {
				final Bitmap.Config c = Bitmap.Config.ARGB_8888;
				final Bitmap thumb = Bitmap.createBitmap(sIconWidth,
						sIconHeight, c);
				final Canvas canvas = sCanvas;
				final Paint paint = sPaint;
				canvas.setBitmap(thumb);
				paint.setDither(false);
				paint.setFilterBitmap(true);
				canvas.drawBitmap(bitmap, (sIconWidth - bitmapWidth) / 2,
						(sIconHeight - bitmapHeight) / 2, paint);
				return thumb;
			}
		}

		return bitmap;
	}

	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	public Bitmap Bytes2Bimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/**
	 * create the bitmap from a byte array 生成水印图片
	 * 
	 * @param src
	 *            the bitmap object you want proecss
	 * @param watermark
	 *            the water mark above the src
	 * @return return a bitmap object ,if paramter's length is 0,return null
	 */
	public Bitmap createBitmap(Bitmap src, Bitmap watermark) {
		if (src == null) {
			return null;
		}

		int w = src.getWidth();
		int h = src.getHeight();
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();
		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
		// draw watermark into
		cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		return newb;
	}

	/**
	 * 重新编码Bitmap
	 * 
	 * @param src
	 *            需要重新编码的Bitmap
	 * 
	 * @param format
	 *            编码后的格式（目前只支持png和jpeg这两种格式）
	 * 
	 * @param quality
	 *            重新生成后的bitmap的质量
	 * 
	 * @return 返回重新生成后的bitmap
	 */
	public Bitmap codec(Bitmap src, Bitmap.CompressFormat format, int quality) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		src.compress(format, quality, os);

		byte[] array = os.toByteArray();
		return BitmapFactory.decodeByteArray(array, 0, array.length);
	}

	/**
	 * Stream转换成Byte
	 * 
	 * @param is
	 * @return
	 */
	public byte[] streamToBytes(InputStream is) {
		ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
		byte[] buffer = new byte[1024];
		int len;
		try {
			while ((len = is.read(buffer)) >= 0) {
				os.write(buffer, 0, len);
			}
		} catch (java.io.IOException e) {

		}
		return os.toByteArray();
	}

	/**
	 * 把一个View的对象转换成bitmap
	 * 
	 */
	public Bitmap getViewBitmap(View v) {

		v.clearFocus();
		v.setPressed(false);

		// 能画缓存就返回false
		boolean willNotCache = v.willNotCacheDrawing();
		v.setWillNotCacheDrawing(false);
		int color = v.getDrawingCacheBackgroundColor();
		v.setDrawingCacheBackgroundColor(0);
		if (color != 0) {
			v.destroyDrawingCache();
		}
		v.buildDrawingCache();
		Bitmap cacheBitmap = v.getDrawingCache();
		if (cacheBitmap == null) {
			if (DEBUG) {
				Log.e(TAG, "failed getViewBitmap(" + v + ")",
						new RuntimeException());
			}
			return null;
		}
		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
		// Restore the view
		v.destroyDrawingCache();
		v.setWillNotCacheDrawing(willNotCache);
		v.setDrawingCacheBackgroundColor(color);
		return bitmap;
	}

	/**
	 * 图片压缩方法：（使用compress的方法）
	 * 
	 * <br>
	 * <b>说明</b> 如果bitmap本身的大小小于maxSize，则不作处理
	 * 
	 * @param bitmap
	 *            要压缩的图片
	 * @param maxSize
	 *            压缩后的大小，单位kb
	 */
	public static void imageZoom(Bitmap bitmap, double maxSize) {
		// 将bitmap放至数组中，意在获得bitmap的大小（与实际读取的原文件要大）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 格式、质量、输出流
		bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
		byte[] b = baos.toByteArray();
		// 将字节换成KB
		double mid = b.length / 1024;
		// 获取bitmap大小 是允许最大大小的多少倍
		double i = mid / maxSize;
		// 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
		if (i > 1) {
			// 缩放图片 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
			// （保持宽高不变，缩放后也达到了最大占用空间的大小）
			bitmap = scale(bitmap, bitmap.getWidth() / Math.sqrt(i),
					bitmap.getHeight() / Math.sqrt(i));
		}
	}

	/***
	 * 图片的缩放方法
	 * 
	 * @param src
	 *            ：源图片资源
	 * @param newWidth
	 *            ：缩放后宽度
	 * @param newHeight
	 *            ：缩放后高度
	 */
	public static Bitmap scale(Bitmap src, double newWidth, double newHeight) {
		// 记录src的宽高
		float width = src.getWidth();
		float height = src.getHeight();
		// 创建一个matrix容器
		Matrix matrix = new Matrix();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 开始缩放
		matrix.postScale(scaleWidth, scaleHeight);
		// 创建缩放后的图片
		return Bitmap.createBitmap(src, 0, 0, (int) width, (int) height,
				matrix, true);
	}

	/**
	 * 图片的缩放方法
	 * 
	 * @param src
	 *            ：源图片资源
	 * @param scaleMatrix
	 *            ：缩放规则
	 */
	public static Bitmap scale(Bitmap src, Matrix scaleMatrix) {
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(),
				scaleMatrix, true);
	}

	/**
	 * 图片的缩放方法
	 * 
	 * @param src
	 *            ：源图片资源
	 * @param scaleX
	 *            ：横向缩放比例
	 * @param scaleY
	 *            ：纵向缩放比例
	 */
	public static Bitmap scale(Bitmap src, float scaleX, float scaleY) {
		Matrix matrix = new Matrix();
		matrix.postScale(scaleX, scaleY);
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(),
				matrix, true);
	}

	/**
	 * 图片的缩放方法
	 * 
	 * @param src
	 *            ：源图片资源
	 * @param scale
	 *            ：缩放比例
	 */
	public static Bitmap scale(Bitmap src, float scale) {
		return scale(src, scale, scale);
	}

	/**
	 * 旋转图片
	 * 
	 * @param angle
	 *            旋转角度
	 * @param bitmap
	 *            要旋转的图片
	 * @return 旋转后的图片
	 */
	public static Bitmap rotate(int angle, Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
	}

	/**
	 * View的背景虚化方法（imageview则设置src，其他则设置bg）
	 * 
	 * @param imageview
	 *            要显示虚化图片的控件（imageview则设置src，其他则设置bg）
	 * @param src
	 *            将要虚化的图片
	 */
	public static void SetMistyBitmap(View imageview, Bitmap src) {
		if (imageview == null || src == null)
			return;
		if (AppUtils.getSDKVersion() >= 18) {
			src = blur(src, imageview, 12);
		} else {
			if (imageview instanceof ImageView) {
				((ImageView) imageview).setImageBitmap(blur(src, 12));
			} else {
				imageview.setBackgroundDrawable(new BitmapDrawable(imageview
						.getResources(), blur(src, 12)));
			}
		}
	}

	/**
	 * 背景虚化方法，4.2系统以下的方法
	 * 
	 * @explain 通过解析bitmap，将bitmap的每一个像素点的rgb值获取到；以每一个像素点为圆心，根据radius模糊度半径，
	 *          求得每个像素点在半径区域中的平均rgb值， 保存为模糊后的该点的rgb值。
	 * @param bkg
	 *            将要虚化的图片
	 * @param radius
	 *            虚化度Supported range 0 < radius <= 25
	 */
	@Deprecated
	private static Bitmap blur(Bitmap bkg, int radius) {
		Bitmap bitmap = bkg.copy(bkg.getConfig(), true);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		int[] pix = new int[w * h]; // 像素点矩阵
		bitmap.getPixels(pix, 0, w, 0, 0, w, h); // 获取rgb颜色值，保存到pix中
		int wm = w - 1; // 宽度
		int hm = h - 1; // 高度
		int wh = w * h; // 总像素个数
		int div = radius + radius + 1; // 模糊度
		int r[] = new int[wh]; // 整个bitmap中每个像素点的R值
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)]; // 宽高中更大的一个作为边界
		int divsum = (div + 1) >> 1;
		divsum *= divsum; // 本来是πR的平方，但是忽略常量的变化
		int dv[] = new int[256 * divsum];
		for (i = 0; i < 256 * divsum; i++) {
			dv[i] = (i / divsum);
		}
		yw = yi = 0;
		int[][] stack = new int[div][3];
		int stackpointer;
		int stackstart;
		int[] sir;
		int rbs;
		int r1 = radius + 1;
		int routsum, goutsum, boutsum; // rgb输出时的值
		int rinsum, ginsum, binsum; // rgb读入时的值
		for (y = 0; y < h; y++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + Math.min(wm, Math.max(i, 0))]; // 返回在[0,wm]区间的值坐标
				sir = stack[i + radius];
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				rbs = r1 - Math.abs(i);
				rsum += sir[0] * rbs;
				gsum += sir[1] * rbs;
				bsum += sir[2] * rbs;
				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}
			}
			stackpointer = radius;
			for (x = 0; x < w; x++) {

				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);
				}
				p = pix[yw + vmin[x]];

				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[(stackpointer) % div];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi++;
			}
			yw += w;
		}
		for (x = 0; x < w; x++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;

				sir = stack[i + radius];

				sir[0] = r[yi];
				sir[1] = g[yi];
				sir[2] = b[yi];

				rbs = r1 - Math.abs(i);

				rsum += r[yi] * rbs;
				gsum += g[yi] * rbs;
				bsum += b[yi] * rbs;

				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}

				if (i < hm) {
					yp += w;
				}
			}
			yi = x;
			stackpointer = radius;
			for (y = 0; y < h; y++) {
				pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
						| (dv[gsum] << 8) | dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (x == 0) {
					vmin[y] = Math.min(y + r1, hm) * w;
				}
				p = x + vmin[y];

				sir[0] = r[p];
				sir[1] = g[p];
				sir[2] = b[p];
				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];
				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[stackpointer];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];
				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];
				yi += w;
			}
		}
		bitmap.setPixels(pix, 0, w, 0, 0, w, h);
		return bitmap;
	}

	/**
	 * 背景虚化方法，仅在API 17以上的系统中才能使用ScriptIntrinsicBlur类
	 * 
	 * @param bkg
	 *            将要虚化的图片
	 * @param imageView
	 *            要显示虚化图片的控件（imageview则设置src，其他则设置bg）
	 * @param radius
	 *            虚化度Supported range 0 < radius <= 25
	 */
	private static Bitmap blur(Bitmap bkg, View imageView, float radius) {
		imageView.measure(0, 0);
		Bitmap overlay = Bitmap.createBitmap(imageView.getMeasuredWidth(),
				imageView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(overlay);
		canvas.drawBitmap(bkg, -imageView.getLeft(), -imageView.getTop(), null);
		RenderScript rs = RenderScript.create(imageView.getContext());
		Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);
		ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs,
				overlayAlloc.getElement());
		blur.setInput(overlayAlloc);
		blur.setRadius(radius);
		blur.forEach(overlayAlloc);
		overlayAlloc.copyTo(overlay);
		if (imageView instanceof ImageView) {
			((ImageView) imageView).setImageBitmap(overlay);
		} else {
			imageView.setBackground(new BitmapDrawable(
					imageView.getResources(), overlay));
		}
		rs.destroy();
		return overlay;
	}

	/**
	 * 更改图片色系，变亮或变暗
	 * 
	 * @param delta
	 *            图片的亮暗程度值，越小图片会越亮，取值范围(0,24)
	 * @return
	 */
	public static Bitmap tone(Bitmap src, int delta) {
		if (delta >= 24 || delta <= 0) {
			return null;
		}
		// 设置高斯矩阵
		int[] gauss = new int[] { 1, 2, 1, 2, 4, 2, 1, 2, 1 };
		int width = src.getWidth();
		int height = src.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);

		int pixR = 0;
		int pixG = 0;
		int pixB = 0;
		int pixColor = 0;
		int newR = 0;
		int newG = 0;
		int newB = 0;
		int idx = 0;
		int[] pixels = new int[width * height];

		src.getPixels(pixels, 0, width, 0, 0, width, height);
		for (int i = 1, length = height - 1; i < length; i++) {
			for (int k = 1, len = width - 1; k < len; k++) {
				idx = 0;
				for (int m = -1; m <= 1; m++) {
					for (int n = -1; n <= 1; n++) {
						pixColor = pixels[(i + m) * width + k + n];
						pixR = Color.red(pixColor);
						pixG = Color.green(pixColor);
						pixB = Color.blue(pixColor);

						newR += (pixR * gauss[idx]);
						newG += (pixG * gauss[idx]);
						newB += (pixB * gauss[idx]);
						idx++;
					}
				}
				newR /= delta;
				newG /= delta;
				newB /= delta;
				newR = Math.min(255, Math.max(0, newR));
				newG = Math.min(255, Math.max(0, newG));
				newB = Math.min(255, Math.max(0, newB));
				pixels[i * width + k] = Color.argb(255, newR, newG, newB);
				newR = 0;
				newG = 0;
				newB = 0;
			}
		}
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	/**
	 * 将彩色图转换为黑白图
	 * 
	 * @param bmp
	 *            位图
	 * @return 返回转换好的位图
	 */
	public static Bitmap convertToBlackWhite(Bitmap bmp) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int[] pixels = new int[width * height];
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);

		int alpha = 0xFF << 24; // 默认将bitmap当成24色图片
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];

				int red = ((grey & 0x00FF0000) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);

				grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
				grey = alpha | (grey << 16) | (grey << 8) | grey;
				pixels[width * i + j] = grey;
			}
		}
		Bitmap newBmp = Bitmap.createBitmap(width, height, Config.RGB_565);
		newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
		return newBmp;
	}

	/**
	 * 读取图片属性：图片被旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return 旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

}
