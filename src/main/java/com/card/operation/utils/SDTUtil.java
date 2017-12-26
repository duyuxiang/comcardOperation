package com.card.operation.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

/**
 * 公安部身份证接口
 * @author taochuang
 *@since 17.11.15
 */
public interface SDTUtil extends Library  {

	SDTUtil sdtApi = (SDTUtil)Native.loadLibrary("sdtapi", SDTUtil.class);

	/**
	 * 设置适配器最大通信
	 */
	public int SDT_SetMaxRFByte(int iport, char[] ucByte, int iIfOpen);
	/**
	 * 查看串口当前波特率
	 */
	public int  SDT_GetCOMBaud(int iPort, IntByReference puiBaudRate);
	/**
	 * 设置串口上SAM的波特率
	 */
	public int SDT_SetCOMBaud(int iport, int uiCurrBaud, int uiSetBaud);
	/**
	 * 打开串口
	 */
	public int SDT_OpenPort(int iPort);
	/**
	 * 关闭串口
	 */
	public int SDT_ClosePort(int iPort);
	/**
	 * 对SAM复位
	 */
	public int  SDT_ResetSAM(int iPort, int iIfOpen);
	/**
	 * 对SAM进行状态检测
	 */
	public int  SDT_GetSAMStatus(int iPort, int iIfOpen);
	/**
	 * 读取SAM的编号（十六进制）
	 */
	public int  SDT_GetSAMID(int iPort, char[] pucSAMID, int iIfOpen);
	/**
	 * 读取SAM的编号（字符串格式）
	 */
	public int  SDT_GetSAMIDToStr(int iPort, char[] pcSAMID, int iIfOpen);
	/**
	 * 开始找卡
	 */
	public int SDT_StartFindIDCard(int iPort, char[] pucIIN, int iIfOpen);
	/**
	 * 选卡
	 */
	public int SDT_SelectIDCard(int iPort, char[] pucSN, int iIfOpen);
	/**
	 * 读取ID卡内基本信息区域信息
	 */
	public int SDT_ReadBaseMsg(int iPort, char[] pucCHMsg, IntByReference puiCHMsgLen
            , char[] pucPHMsg, IntByReference puiPHMsgLen, int iIfOpen);
	/**
	 * 读取ID卡内IIN,SN, DN	
	 */
	public int  SDT_ReadIINSNDN(int iPort, char[] pucIINSNDN, int iIfOpen);
	/**
	 * 读取ID卡内基本信息区域信息，并把信息写入指定的文件中	
	 */
	public int  SDT_ReadBaseMsgToFile(int iPortID, String pcCHMsgFileName, IntByReference puiCHMsgFileLen,
                                      String pcPHMsgFileName, IntByReference puiPHMsgFileLen, int iIfOpen);
	/**
	 * 读取ID卡内IIN,SN, DN，并转换成ASCII形式
	 */
	public int  SDT_ReadSNDN(int iPort, char[] pucIINSNDN, int iIfOpen);
}
