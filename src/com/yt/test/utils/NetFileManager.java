package com.yt.test.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class NetFileManager {

	private static final int TIME_OUT = 10 * 10000000; // 超时时间
	private static final String CHARSET = "utf-8"; // 设置编码
	private static final String UPLOAD_IMAGE_URL = ;

	 public static String uploadImg(String imgUrl) throws Exception {
	        File imgFile=new File(imgUrl);
	        if (imgFile.exists()) {
	        	URL url=new URL(UPLOAD_IMAGE_URL);
	        	HttpURLConnection conn=(HttpURLConnection) url.openConnection();
	        	conn.setConnectTimeout(10000);
	        	conn.setRequestMethod("POST");
	        	conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=----123456789");
	        	conn.setDoInput(true);
	        	conn.setDoOutput(true);
	        	
	        	OutputStream os=new DataOutputStream(conn.getOutputStream());
	        	StringBuilder body=new StringBuilder();
	        	body.append("------123456789\r\n");
	        	body.append("Content-Disposition: form-data; name='file'; filename='"+imgFile.getName()+"'\r\n");
	        	body.append("Content-Type: file/jpeg\r\n\r\n");
	        	os.write(body.toString().getBytes());
	        	
	        	InputStream is=new FileInputStream(imgFile);
	        	byte[] b=new byte[1024];
	        	int len=0;
	        	while((len=is.read(b))!=-1){
	        		os.write(b,0,len);
	        	}
	        	String end="\r\n------123456789--";
	        	os.write(end.getBytes());
	        	
	        	//输出返回结果
	        	InputStream input=conn.getInputStream();
	        	byte[] res=new byte[1024];
	        	int resLen=input.read(res);
	        	return new String(res,0,resLen);
	        } else {
	        	return "img file no exists";
	        }
	    }
}
