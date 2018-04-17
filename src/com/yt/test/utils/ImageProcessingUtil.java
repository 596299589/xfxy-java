package com.yt.test.utils;

import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图片处理类
 * 
 * @author yt
 * 
 */
public class ImageProcessingUtil {

	private final static String TAG = "ImageProcessingUtil";

	/**
	 * 按宽高进行缩放
	 * 
	 * @param imageSourcePath
	 *            图片源路径
	 * @param imageOutPutPath
	 *            图片输出路径
	 * @param width
	 *            指定缩放的宽
	 * @param height
	 *            指定缩放的高
	 * @see 若图片横比width小，高比height小，不变 若图片横比width小，高比height大，高缩小到height，图片比例不变
	 *      若图片横比width大，高比height小，横缩小到width，图片比例不变
	 *      若图片横比width大，高比height大，图片按比例缩小，横为width或高为height
	 */
	public static void imageZoomFromWH(String imageSourcePath, String imageOutPutPath, int width, int height) {
		try {
			Thumbnails.of(imageSourcePath).size(width, height).toFile(imageOutPutPath);
		} catch (IOException e) {
			MyLog.systemOutLog(TAG, "IOException:" + e.getMessage());
		}
	}

	/**
	 * 按缩放比进行缩放
	 * 
	 * @param imageSourcePath
	 *            图片源路径
	 * @param imageOutPutPath
	 *            图片输出路径
	 * @param scale
	 *            缩放比取值范围0-1，0<scale<1为缩小，scale>1为放大，目前对此方法进行限制，不可放大
	 */
	public static void imageZoomFromScale(String imageSourcePath, String imageOutPutPath, float scale) {
		if (scale > 1) {
			return;
		}
		try {
			// 按照比例进行缩小和放大
			Thumbnails.of(imageSourcePath).scale(scale).toFile(imageOutPutPath);// 按比例缩小
		} catch (IOException e) {
			MyLog.systemOutLog(TAG, "IOException:" + e.getMessage());
		}
	}
	
	
	/**
	 * 图片尺寸不变，压缩图片文件大小
	 * 
	 * @param imageSourcePath
	 *            图片源路径
	 * @param imageOutPutPath
	 *            图片输出路径
	 * @param quality
	 *            图片质量参数，参数1为最高质量
	 */
	public static void imageZoomFromQuality(String imageSourcePath, String imageOutPutPath, float quality) {
		//图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
        try {
			Thumbnails.of(imageSourcePath).scale(1f).outputQuality(quality).toFile(imageOutPutPath);
		} catch (IOException e) {
			MyLog.systemOutLog(TAG, "IOException:" + e.getMessage());
		}
	}
}
