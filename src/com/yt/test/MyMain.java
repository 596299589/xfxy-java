package com.yt.test;

import java.io.File;

import com.yt.test.utils.MyLog;
import com.yt.test.utils.NetFileManager;



public class MyMain {
	private static final String TAG = "MyMain";
	
	public static void main(String[] args) {

		MyLog.systemOutLog(TAG, "start init");
//		MyWindow myWindow = new MyWindow();
		
		String imageSourcePath = "C:\\Users\\yt\\Desktop\\test2.jpg";
		String imageOutPutPath = "C:\\Users\\yt\\Desktop\\test3.jpg";
//		ImageProcessingUtil.imageZoomFromWH(imageSourcePath, imageOutPutPath, 800, 1200);
//		ImageProcessingUtil.imageZoomFromScale(imageSourcePath, imageOutPutPath, 0.5f);
//		ImageProcessingUtil.imageZoomFromQuality(imageSourcePath, imageOutPutPath, 0.8f);

		
		File file = new File("C:\\Users\\yt\\Desktop\\qrcode_for_gh_3df911c478ff_258.jpg");
		String res = "";
		try {
			res = NetFileManager.uploadImg(file.getPath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyLog.systemOutLog(TAG, "uploadFile result = " + res);
		
	}
}
