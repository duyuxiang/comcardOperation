package com.card.operation.temic;

import java.util.ArrayList;
import java.util.List;

//import enums.TemicErrorEmum;
//import util.TemicUtil;

/**
 * 读卡器控制类
 * @author taochuang
 * @since 17.11.23
 *
 */
public class TemicService {
	private static final int SUCCESS_CODE = TemicErrorEmum.zero.getCode();
	/**
	 * 串口号
	 */
	private static int com = -1;
	/**
	 * 波特率
	 */
	private static final int BAUD_RATE=9600;
	
	/**
	 * 查找端口并打开连接
	 * @throws RuntimeException 表示打开端口失败
	 */
	private static void openCOM(){
		int code = -1;
		if(TemicService.com != -1) {
			code = TemicUtil.temicUtil.mif_selecom(com, BAUD_RATE);
			if(code != SUCCESS_CODE) throw new RuntimeException(TemicErrorEmum.getMessage(code));
			return;
		}
		//遍历端口时，可能会成功打开非本设备的串口，所以需进行读操作来进行验证，耗时约两秒
		for(int com = 0; com < 8; com++) {
			if(TemicUtil.temicUtil.mif_selecom(com, BAUD_RATE) == SUCCESS_CODE) {
				if(TemicUtil.temicUtil.tem_readmsdata(new byte[4], new byte[10]) != TemicErrorEmum.II.getCode()) {
					TemicService.com = com;
					return;
				}
				TemicUtil.temicUtil.mif_closecom();
			}
		}
		throw new RuntimeException("未找到端口");
	}
	
	/**
	 * 关闭端口
	 * @throws RuntimeException 表示关闭端口失败
	 */
	private static void closeCOM() {
		int code = TemicUtil.temicUtil.mif_closecom();
		if(code != SUCCESS_CODE) throw new RuntimeException(TemicErrorEmum.getMessage(code));
	}
	
	/**
	 * 读取卡中不可见的4字符门锁编号和日期时间信息
	 * @return 正常：list[0] 操作信息，list[1] 门锁编号(4字符)， list[2] 日期时间(10字符)
	 * <p>异常：list[0] 操作信息</p>
	 * <p>注意：日期时间为8个字符"年月日时"+2个字符"一小时内发卡序号"组成，日期为十进制，序号为十六进制</p>
	 */
	public static List<String> readInvisibleData() {
		return readData(4);
	}
	
	/**
	 * 读取卡信息
	 * @param length 门锁编号字符长度
	 * @return 正常：list[0] 操作信息，list[1] 门锁编号， list[2] 日期时间(10字符)
	 * <p>异常：list[0] 操作信息</p>
	 * <p>注意：日期时间为8个字符"年月日时"+2个字符"一小时内发卡序号"组成，日期为十进制，序号为十六进制</p>
	 * @throws RuntimeException 表示硬件上出现不可自动修复的问题
	 */
	private static List<String> readData(int length) {
		openCOM();
		ArrayList<String> dataList = new ArrayList<String>(3);
		//门锁编号
		byte[] doorNum = length == 4 ? new byte[4] : new byte[8];
		//日期时间序号
		byte[] dateTimeIndex = new byte[10];
		int code =  length == 4 ? TemicUtil.temicUtil.tem_readmsdata(doorNum, dateTimeIndex) 
				: length == 8 ? TemicUtil.temicUtil.tem_readmsdata1(doorNum, dateTimeIndex)
						: -1;
		closeCOM();
		String message  = TemicErrorEmum.getMessage(code);
		dataList.add(message);
		if(code != SUCCESS_CODE) {
			if(TemicErrorEmum.getstate(code).equals("error")) throw new RuntimeException(message);
			return dataList;
		}
		dataList.add(new String(doorNum));
		dataList.add(new String(dateTimeIndex));
		return dataList;
	}
	
	/**
	 * 读取卡中可见的8字符门锁编号和日期时间信息
	 * @return 正常：list[0] 操作信息，list[1] 门锁编号(8字符)， list[2] 日期时间(10字符)
	 * <p>异常：list[0] 操作信息</p>
	 * <p>注意：日期时间为8个字符"年月日时"+2个字符"一小时内发卡序号"组成，日期为十进制，序号为十六进制</p>
	 */
	public static List<String> readVisibleData() {
		return readData(8);
	}
	
	/**
	 * 写卡
	 * @param doorNum 门锁编号。长度为8（可见），或者为4（不可见）
	 * <p>注意：因dll本身问题，在长度为8时，第5位(0-7)上无论写什么参数，读出来的都变为0(长度为4时，第2位总是为0)</p>
	 * @param dateTime 发卡时间，长度为8
	 * <p>比如发卡时间为2004年8月9日10点，dateTime=“04080910”;</p>
	 * <p>注意：因dll本身问题，表示年份的两位只能在0-15进行循环，即写入"17"，读出"01"。建议使用2010+07表示2017年</p>
	 * @param index 一小时内发卡序号
	 * <p>注意：只能在0到63，如果在当前小时内的写卡次数超过64次(即发卡序号大于63)，
	 * 则需要将dateTime参数以小时数+1为新的dateTime参数进行写卡，
	 * 同时此发卡序号index复位为0开始(即新的dateTime中的序号)。</p>
	 * @param timeUnit 时间单位(小时/天/月/年)
	 * </p>0为小时，1为天，2为月，3为年</p>
	 * @param effectiveTime 卡片有效时间。
	 * <p>为几天或为几小时，取值范围（0--63小时/ 0--63天/ 0--15月 /0--3年）</p>
	 * @param identityCheck true:为取消在用的客人卡（挂失），false:为不取消（不挂失）
	 * @return "正确"为操作成功，其他字符串为操作失败
	 * @throws RuntimeException 表示硬件上出现不可自动修复的问题
	 */
	public static String writeData(byte[] doorNum, byte[] dateTime, int index, int timeUnit, int effectiveTime, boolean identityCheck){
		if(!checkWriteData(dateTime, index, timeUnit, effectiveTime)) return TemicErrorEmum.getstate(-1);
		openCOM();
		int code = doorNum.length == 4 ? TemicUtil.temicUtil.tem_writemsdata(doorNum, dateTime, index, timeUnit, effectiveTime, identityCheck)
				: doorNum.length == 8 ? TemicUtil.temicUtil.tem_writemsdata1(doorNum, dateTime, index, timeUnit, effectiveTime, identityCheck)
					: -1;
		closeCOM();
		String message  = TemicErrorEmum.getMessage(code);
		if(TemicErrorEmum.getstate(code).equals("error")) throw new RuntimeException(message);
		return message;
	}
	
	/**
	 * 参数检查
	 * @return true 参数正确；false 参数异常
	 */
	private static boolean checkWriteData(byte[] dateTime, int index, int timeUnit, int effectiveTime) {
		if(dateTime.length != 8) return false;
		if(index < 0 || index > 63) return false;
		switch(timeUnit) {
		case 0 : if(0 <= effectiveTime && effectiveTime <= 63) return true;
		case 1 : if(0 <= effectiveTime && effectiveTime <= 63) return true;
		case 2 : if(0 <= effectiveTime && effectiveTime <= 15) return true;
		case 3 : if(0 <= effectiveTime && effectiveTime <= 3) return true;
		default : return false;
		}
	}
	
}
