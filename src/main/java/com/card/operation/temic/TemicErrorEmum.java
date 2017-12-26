package com.card.operation.temic;

/**
 * 读卡器返回信息枚举
 * @author taochuang
 * @since 17.11.21
 *
 */
public enum TemicErrorEmum {
	i(-1, "warning", "传参错误"),
	zero(0, "safe", "正确"),
	I(1, "error", "通讯错误，通讯底层中断，请检查通讯硬件是否存在"),
	II(2, "error", "超时错误，内部错误，检查硬件及接口函数版本号"),
	III(3, "error", "数据区域错误，内部错误，检查硬件及接口函数版本号"),
	IV(4, "warning", "数据错误，读卡片数据发生错误或卡片没有插好或插入卡片不是客人卡"
			+ "，重新将卡片插好或再在门锁系统中重新写一次客人卡"),
	five(5, "error", "通讯错误，通讯口不正确，检查通讯串口号或通讯线"),
	XVI(16, "warning", "无卡，读写器上没有放置卡片，重新放置感应卡至读写器上"),
	XVII(17, "warning", "卡片没有上电，卡片没有放好，重新放置卡片至读写器上"),
	XVIII(18, "error", "密码错误，内部错误，检查硬件及接口函数版本号"),
	XVIIII(19, "warning", "坏卡，卡片可能已损坏，换一张新的感应卡重试。"),
	XX(20, "error", "功能错误，内部错误，检查硬件及接口函数版本号"),
	S(128, "error", "未授权"),
	T(256, "error", "系统错误，内部错误，检查硬件及接口函数版本号");
	
	//错误码
	private int code;

	//错误严重程度
	private String state;
	
	//错误信息
	private String message;
	
	private TemicErrorEmum(int code,String state, String message) {
		this.code = code;
		this.state = state;
		this.message = message;
	}
	
	public static String getMessage(int code) {
		for(TemicErrorEmum temicErrorEmum : values()) {
			if(temicErrorEmum.code == code) return temicErrorEmum.message;
		}
		return "未知错误";
	}
	
	public static String getstate(int code) {
		for(TemicErrorEmum temicErrorEmum : values()) {
			if(temicErrorEmum.code == code) return temicErrorEmum.state;
		}
		return "未知错误";
	}
	
	public Integer getCode() {
		return code;
	}
	
}
