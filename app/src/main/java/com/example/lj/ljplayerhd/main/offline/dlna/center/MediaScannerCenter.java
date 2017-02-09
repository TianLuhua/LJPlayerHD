package com.example.lj.ljplayerhd.main.offline.dlna.center;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;


public class MediaScannerCenter {

	public static final int AUDIO_TYPE = 0;
	public static final int VIDEO_TYPE = 1;
	public static final int IMAGE_TYPE = 2;

	String AUDIO_PATH = MediaStore.Audio.AudioColumns.DATA;
	String AUDIO_DISPLAYHNAME = MediaStore.Audio.AudioColumns.DISPLAY_NAME;
	String AUDIO_COLUMN_STRS[] = {AUDIO_PATH, AUDIO_DISPLAYHNAME};
	
	String VIDEO_PATH = MediaStore.Video.VideoColumns.DATA;
	String VIDEO_DISPLAYHNAME  = MediaStore.Video.VideoColumns.DISPLAY_NAME;
	String VIDEO_COLUMN_STRS[] = {VIDEO_PATH, VIDEO_DISPLAYHNAME};
	
	String IMAGE_PATH = MediaStore.Images.ImageColumns.DATA;
	String IMAGE_DISPLAYHNAME  = MediaStore.Images.ImageColumns.DISPLAY_NAME;
	String IMAGE_COLUMN_STRS[] = {IMAGE_PATH, IMAGE_DISPLAYHNAME};
	
	
	private static  MediaScannerCenter mInstance;
	private Context mContext;
	IMediaScanListener mListener;
	private ScanMediaThread mediaThread;
	
	private MediaScannerCenter(Context context) {
		mContext = context;
	}

	public static synchronized MediaScannerCenter getSingleton(Context context) {
		if (mInstance == null){
			mInstance  = new MediaScannerCenter(context);
		}
		return mInstance;
	}

	
	
	public synchronized boolean startScanThread(IMediaScanListener listener){
		if (mediaThread == null || !mediaThread.isAlive()){
			this.mListener=listener;
			mediaThread = new ScanMediaThread();
			mediaThread.start();
		}
		return true;
	}
	
	public synchronized void stopScanThread(){
		if (mediaThread != null){
			if (mediaThread.isAlive()){
				mediaThread.exit();
			}
			mediaThread = null;
		}
	}
	
	public synchronized boolean isThreadOver(){
		if (mediaThread != null && mediaThread.isAlive()){
			return false;
		}
		return true;
	}
	
	private  boolean scanMusic(ICancelScanMedia cancelObser) throws Exception {
		Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 
				AUDIO_COLUMN_STRS, 
				null, 
				null,
				AUDIO_DISPLAYHNAME);				

		if (cursor != null)
		{
			int count = cursor.getCount();
			if (count != 0)
			{
				int _name_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
	     		int _dir_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
	    		if (cursor.moveToFirst()) {  
	         		do { 
	         			if (cancelObser.ifCancel()){
	         				return false;
	         			}
	         			String srcpath = cursor.getString(_dir_index);
	         			String name = cursor.getString(_name_index);
	         			mListener.mediaScan(AUDIO_TYPE, srcpath, name);
	         		} while (cursor.moveToNext());  
	         	}  			
			}		
			cursor.close();
			return true;
		}

		return false;
	}
	
	private  boolean scanVideo(ICancelScanMedia cancelObser) throws Exception {
		
		Cursor cursor = mContext.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, 
				VIDEO_COLUMN_STRS, 
				null, 
				null,
				VIDEO_DISPLAYHNAME);				

		if (cursor != null)
		{
			int count = cursor.getCount();
			if (count != 0)
			{
				int _name_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
	     		int _dir_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
	    		if (cursor.moveToFirst()) {  
	         		do { 
	         			if (cancelObser.ifCancel()){
	         				return false;
	         			}
	         			String srcpath = cursor.getString(_dir_index);
	         			String name = cursor.getString(_name_index);
	         			mListener.mediaScan(VIDEO_TYPE, srcpath, name);
	         		} while (cursor.moveToNext());  
	         	}  			
			}		
			cursor.close();
			return true;
		}

		return false;
	}
	
	private  boolean scanImage(ICancelScanMedia cancelObser) throws Exception {
		
		Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 
				IMAGE_COLUMN_STRS, 
				null, 
				null,
				IMAGE_DISPLAYHNAME);				

		if (cursor != null)
		{
			int count = cursor.getCount();
			if (count != 0)
			{
				int _name_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
	     		int _dir_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    		if (cursor.moveToFirst()) {  
	         		do { 
	         			if (cancelObser.ifCancel()){
	         				return false;
	         			}
	         			String srcpath = cursor.getString(_dir_index);
	         			String name = cursor.getString(_name_index);
	         			mListener.mediaScan(IMAGE_TYPE, srcpath, name);
	         
	         		} while (cursor.moveToNext());  
	         	}  			
			}		
			cursor.close();
			return true;
		}
		return false;
	}
	
	
	public class ScanMediaThread extends Thread implements ICancelScanMedia{
		
		boolean exitFlag = false;
		
		public void exit(){
			exitFlag = true;
		}
		
		@Override
		public void run() {

			try {
				scanMusic(this);
				scanVideo(this);
				scanImage( this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			super.run();
		}

		@Override
		public boolean ifCancel() {
			return exitFlag;
		}	
	}
	
	
	public  interface ICancelScanMedia{
		public boolean ifCancel();
	}
	
	
}
