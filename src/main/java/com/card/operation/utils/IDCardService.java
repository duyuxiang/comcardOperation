package com.card.operation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.prosecenium.core.dllinterface.SDTUtil;
//import com.prosecenium.core.dllinterface.WltRSUtil;
//import com.prosecenium.core.enums.SexAndGender;
//import com.prosecenium.core.pojo.IDCard;
import com.sun.jna.ptr.IntByReference;

/**
 * 身份证数据读取类
 * @author taochuang
 * @since 17.11.16
 *
 */
public class IDCardService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IDCardService.class); 

	/**
	 * 图片路径
	 */
	public static final String PICTURE_PATH = "C:\\pic.bmp";

	private IDCardService() {}
	
	/**
	 * 打开端口
	 */
	private static int openPort() {
		for(int idPort = 1001; idPort < 1016; idPort++) {
			if(SDTUtil.sdtApi.SDT_OpenPort(idPort) == 144){
				return idPort;
			}
		}
		return -1;
//		throw new RuntimeException();
	}
	
	/**
	 * 关闭端口
	 */
	private static void closePort(int idPort) {
		SDTUtil.sdtApi.SDT_ClosePort(idPort);
	}
	
	/**
	 * 读取身份证数据
	 * @param timeOut 设置读取超时时间(单位/秒)
	 * @return null 读取失败
	 */
	public static IDCard  readIDCard(int timeOut) {
		String wltPicturePath = PICTURE_PATH.replaceAll(".bmp", ".wlt");
		char[] pucIIN = new char[4];
		char[] pucSN = new char[8];
		final int iIfOpen = 0;
		//文字信息
		char[] pucCHMsg = new char[256];
		//图片信息
		char[] pucPHMsg = new char[1024];
		//文字信息长度
		IntByReference puiCHMsgLen = new IntByReference();
		//图片信息长度
		IntByReference puiPHMsgLen = new IntByReference();
		int count = 0;
		int idPort = openPort();
		if(idPort == -1){
			throw new RuntimeException();
		}
		while(count < (timeOut << 2)) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
			System.out.println(count);
			if(SDTUtil.sdtApi.SDT_StartFindIDCard(idPort, pucIIN, iIfOpen) != 159) {
				continue;
			}
			if(SDTUtil.sdtApi.SDT_SelectIDCard(idPort, pucSN, iIfOpen) != 144) {
				continue;
			}
			if(SDTUtil.sdtApi.SDT_ReadBaseMsg(idPort, pucCHMsg, puiCHMsgLen, pucPHMsg, puiPHMsgLen, iIfOpen) == 144) {
				if(SDTUtil.sdtApi.SDT_ReadBaseMsgToFile(idPort, PICTURE_PATH.replaceAll(".bmp", ".txt"), puiCHMsgLen, wltPicturePath, puiPHMsgLen, iIfOpen) == 144 ){
					if(WltRSUtil.wltRS.GetBmp(wltPicturePath, WltRSUtil.USB) == 1) {
						closePort(idPort);
						return parseToIDCard(pucCHMsg);
					}
				}
			}
		}
		LOGGER.info("身份信息获取超时，超时时间" + timeOut + "秒");
		return null;
	}
	
	/**
	 * 身份证数据解析
	 * @param pucCHMsg 身份证信息字符组
	 * @return IDCard
	 */
	private static IDCard parseToIDCard(char[] pucCHMsg) {
		String name = String.copyValueOf(pucCHMsg, 0, 15).trim(); 
		String sex = SexAndGender.stateOf(String.copyValueOf(pucCHMsg, 15, 1));
		String nation = SexAndGender.stateOf(String.copyValueOf(pucCHMsg, 16, 2));
		String birthday = String.copyValueOf(pucCHMsg, 18, 8);
		String address = String.copyValueOf(pucCHMsg, 26, 35).trim();
		String cardNumber = String.copyValueOf(pucCHMsg, 61, 18);
		String grantDept = String.copyValueOf(pucCHMsg, 79, 15).trim();
		String usefulLifeBegin = String.copyValueOf(pucCHMsg, 94, 8);
		String usefulLifeEnd = String.copyValueOf(pucCHMsg, 102, 8);
		String newAddress = String.copyValueOf(pucCHMsg, 110, 35).trim();
		return new IDCard(name, nation, sex, birthday, address, cardNumber
				, grantDept, usefulLifeBegin, usefulLifeEnd, newAddress);
	}
	
}
