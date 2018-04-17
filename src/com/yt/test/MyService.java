package com.yt.test;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.yt.test.icomet.Channel;
import com.yt.test.icomet.ChannelAllocator;
import com.yt.test.icomet.ICometCallback;
import com.yt.test.icomet.ICometClient;
import com.yt.test.icomet.ICometConf;
import com.yt.test.icomet.IConnCallback;
import com.yt.test.icomet.message.Message;
import com.yt.test.icomet.message.Message.Content;
import com.yt.test.utils.MyLog;
import com.yt.test.utils.NetUtil;
import net.sf.json.JSONObject;

/**
 * Created by yt on 2018/3/15.
 */

public class MyService {

    private final String TAG = "MyService";

    private final String SERVER_IP = "112.74.41.147";
    private final String TOKEN_URL = "http://" + SERVER_IP + "/XfxyService/push/sign.php";

    private ICometClient mClient;

    private String mCname = "test";
    private String mToken;
    

    private static MyService mSe;

    public static MyService getInstance() {
        if (null == mSe) {
            mSe = new MyService();
        }
        return mSe;
    }

    public void init() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getToken();
                if (null != mToken && !mToken.isEmpty()) {
                	linkIcomet(mCname, mToken);
                }
            }
        };
        Thread thread = new Thread(runnable, "todd");
        thread.start();
    }

    private void getToken() {
        String result = NetUtil.sendPost(TOKEN_URL, "cname=test");
        if (result == null || result.isEmpty()) {
        	MyLog.systemOutLog(TAG, "token request fail");
        	return;
        }
        JSONObject jsonObject = JSONObject.fromObject(result);
        mToken = jsonObject.optString("token");
        MyLog.systemOutLog(TAG, "token:" + mToken);
    }

    private void linkIcomet(String cname, String token) {
        mClient = ICometClient.getInstance();
        ICometConf conf = new ICometConf();
        conf.host = SERVER_IP;
        conf.port = "8100";
        conf.url = "stream";
        conf.iConnCallback = new MyConnCallback();
        conf.iCometCallback = new MyCometCallback();
        conf.channelAllocator = new NoneAuthChannelAllocator(cname, token);
        mClient.prepare(conf);
        mClient.connect();
    }
    

    private class MyConnCallback implements IConnCallback {

        @Override
        public void onFail(String msg) {
            MyLog.systemOutLog(TAG, "connection fail:" + msg);
        }

        @Override
        public void onSuccess() {
            MyLog.systemOutLog(TAG, "connection ok");
            mClient.comet();
        }

        @Override
        public void onDisconnect() {
            MyLog.systemOutLog(TAG, "connection has been cut off");
        }

        @Override
        public void onStop() {
            MyLog.systemOutLog(TAG, "client has been stopped");
        }

        @Override
        public boolean onReconnect(int times) {
            MyLog.systemOutLog(TAG, "This is the " + times + "st times.");
            if (times >= 3) {
                return true;
            }
            return false;
        }

        @Override
        public void onReconnectSuccess(int times) {
            MyLog.systemOutLog(TAG, "onReconnectSuccess at " + times + "st time");
            mClient.comet();
        }

    }

    SimpleDateFormat df;
    private String getTime(){
    	Date day=new Date();
    	if (null == df) {
    		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	}
    	return df.format(day);
    }
    
    private class MyCometCallback implements ICometCallback {

        @Override
        public void onDataMsgArrived(Content content) {
            MyLog.systemOutLog(TAG, "data msg arrived: " + content);
        }

        @Override
        public void onMsgArrived(Message msg) {
        	
            MyLog.systemOutLog(TAG, getTime() + "：" + msg.content);

            String content = msg.content;

            JSONObject jsonObject = JSONObject.fromObject(content);
            String message = jsonObject.optString("content");
            if (null != mOnMessageListener) {
            	mOnMessageListener.onMessage(message);
            }
        }

        @Override
        public void onErrorMsgArrived(Message msg) {
            MyLog.systemOutLog(TAG, "error message arrived with type: " + msg.type);
        }

        @Override
        public void onMsgFormatError() {
            MyLog.systemOutLog(TAG, "message format error");
        }

    }

    private class NoneAuthChannelAllocator implements ChannelAllocator {

        private String cname;
        private String token;

        public NoneAuthChannelAllocator(String cname, String token) {
            this.cname = cname;
            this.token = token;
        }

        @Override
        public Channel allocate() {
            Channel channel = new Channel();
            channel.cname = cname;
            channel.token = token;
            channel.seq = 1;
            return channel;
        }
    }
    
    public void release(){
    	if (null != mClient) {
    		mClient.stopComet();
    		mClient.stopConnect();
    	}
    }
    
    private OnMessageListener mOnMessageListener;
    public void setOnMessageListener(OnMessageListener l) {
    	mOnMessageListener = l;
    }
    
    public interface OnMessageListener{
    	void onMessage(String message);
    }
}
