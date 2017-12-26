package com.card.operation.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * 身份证图片解码接口 
 * @author taochuang
 * @since 17.11.16
 */
public interface WltRSUtil extends Library {
	WltRSUtil wltRS = (WltRSUtil)Native.loadLibrary("WltRS", WltRSUtil.class);
	
	static final int RS_232C = 1;
	static final int USB = 2;
	
	public int GetBmp(String Wlt_File, int intf);
}
