package com.yt.test;

import com.yt.test.utils.ImageProcessingUtil;
import com.yt.test.utils.MyLog;



public class MyMain {
	private static final String TAG = "MyMain";
	
	public static void main(String[] args) {

		MyLog.systemOutLog(TAG, "start init");
		MyWindow myWindow = new MyWindow();
		
		String imageSourcePath = "C:\\Users\\yt\\Desktop\\test2.jpg";
		String imageOutPutPath = "C:\\Users\\yt\\Desktop\\test3.jpg";
//		ImageProcessingUtil.imageZoomFromWH(imageSourcePath, imageOutPutPath, 800, 1200);
//		ImageProcessingUtil.imageZoomFromScale(imageSourcePath, imageOutPutPath, 0.5f);
//		ImageProcessingUtil.imageZoomFromQuality(imageSourcePath, imageOutPutPath, 0.8f);

	}
}
