package com.example.lj.ljplayerhd.main.offline.dlna.center;

import android.content.Context;
import android.util.Log;


import com.example.lj.ljplayerhd.main.offline.dlna.utils.FileHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 媒体存储中心
 * @author UTSC0768
 *
 */
public class MediaStoreCenter implements IMediaScanListener{

	private static  MediaStoreCenter mInstance;
	private Context mContext;
	
	
	private String mShareRootPath = "";
	private String mImageFolderPath = "";
	private String mVideoFolderPath = "";
	private String mAudioFolderPath = "";
	
	private MediaScannerCenter mMediaScannerCenter;
	private Map<String, String> mMediaStoreMap = new HashMap<String, String>();
	
	
	private MediaStoreCenter(Context context) {
		mContext = context;
		
		initData();
	}

	public static synchronized MediaStoreCenter getInstance(Context context) {
		if (mInstance == null){
			mInstance  = new MediaStoreCenter(context);
		}
		return mInstance;
	}

	private void initData(){
		mShareRootPath = mContext.getFilesDir().getAbsolutePath()+"/" + "rootFolder";
		mImageFolderPath = mShareRootPath + "/" + "Image"+"/"+"Test";
		mVideoFolderPath = mShareRootPath + "/" + "Video";
		mAudioFolderPath = mShareRootPath + "/" + "Audio";
		mMediaScannerCenter = MediaScannerCenter.getSingleton(mContext);
	}
	
	public String getRootDir(){
		return mShareRootPath;
	}
	public void clearAllData(){
		stopScanMedia();
		clearMediaCache();
		clearWebFolder();
	}
	
	public boolean createWebFolder(){
		boolean ret = FileHelper.createDirectory(mShareRootPath);
		if (!ret){
			return false;
		}
		
		FileHelper.createDirectory(mImageFolderPath);
		FileHelper.createDirectory(mVideoFolderPath);
		FileHelper.createDirectory(mAudioFolderPath);
		
		return true;
	}
	
	public boolean clearWebFolder(){

		long time = System.currentTimeMillis();
		boolean ret = FileHelper.deleteDirectory(mShareRootPath);
		long time1 = System.currentTimeMillis();
		return ret;
	}

	public void clearMediaCache(){
		mMediaStoreMap.clear();
	}
	
	public void doScanMedia(){
		mMediaScannerCenter.startScanThread(this);
	}
	
	public void stopScanMedia(){
		mMediaScannerCenter.stopScanThread();
		while(!mMediaScannerCenter.isThreadOver()){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mediaScan(int mediaType, String mediaPath, String mediaName) {
		
		switch (mediaType) {
		case MediaScannerCenter.AUDIO_TYPE:
			mapAudio(mediaPath, mediaName);
			break;
		case MediaScannerCenter.VIDEO_TYPE:
			mapVideo(mediaPath, mediaName);
			break;
		case MediaScannerCenter.IMAGE_TYPE:
		    mapImage(mediaPath, mediaName);
			break;
		default:
			break;
		}
		
	}
	
	
	private void mapAudio( String mediaPath, String mediaName){
		if (!mediaName.endsWith(".mp3")) {
			mediaName=mediaName.substring(0, mediaName.indexOf("."))+".mp3";
			Log.e("info", "mediaName:"+mediaName);
		}
		String webPath = mAudioFolderPath + "/" + mediaName;
		mMediaStoreMap.put(mediaPath, webPath);
		softLinkMode(mediaPath, webPath);
	}
	
	private void mapVideo( String mediaPath, String mediaName){
		if (!mediaName.endsWith(".mp4")) {
			mediaName=mediaName.substring(0, mediaName.indexOf("."))+".mp4";
			Log.e("info", "mediaName:"+mediaName);
		}
		String webPath = mVideoFolderPath + "/" + mediaName;
		mMediaStoreMap.put(mediaPath, webPath);
		softLinkMode(mediaPath, webPath);
	}
	
	private void mapImage( String mediaPath, String mediaName){
		if (!mediaName.endsWith(".jpg")) {
			mediaName=mediaName.substring(0, mediaName.indexOf("."))+".jpg";
			Log.e("info", "mediaName:"+mediaName);
		}
		String webPath = mImageFolderPath + "/" + mediaName;
		mMediaStoreMap.put(mediaPath, webPath);
		softLinkMode(mediaPath, webPath);
	}
	
	/**
	 * 软链接模式
	 * @param localPath 本地多媒体文件地址
	 * @param webPath  目标多媒体文件地址
	 * @return
	 */
	private boolean softLinkMode(String localPath, String webPath){
		Process p = null;
		int status=0;
		try {
			//编码格式必须为"utf-8"
			//执行字符串与执行字符串数组的区别:
			//1)执行字符串是将字符串分割成字符串数组在执行,所以文件名不能存在空格
			String[] cmd ={ "ln","-s",localPath,webPath};
			p = Runtime.getRuntime().exec(cmd);
			releaseProcessStream(p);
			
			status = p.waitFor();		
			if (status == 0) {
				return true;//success
			} else {
				return false;
			}
		}catch (Exception e) {
			Log.e("info", "Exception:"+e.getMessage());
			return false;
		}
	}
	
	private void releaseProcessStream(Process p) throws IOException{
		InputStream stderr = p.getErrorStream();
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		while ( (line = br.readLine()) != null)
			System.out.println(line);
	}
}
