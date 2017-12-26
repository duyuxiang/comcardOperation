package com.card.operation.temic;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Temic卡(感应式)门锁接口
 * @author taochuang
 *@since 17.11.21
 */
public interface TemicUtil extends Library {


	/**
	 * <p>1.所有客人卡片都必须在门锁软件系统中写一遍客人卡。</P>
	 * <p>2.发卡原则，客人卡按酒店管理系统发行，而其他卡片均需在门锁软件系统发行。</P>
	 * <p>3.禁止多个门锁软件在不同卡片之间串发。</P>
	 * <p>4.正式开通使用，建议将所有卡片全部收回之后，
	 * 再发一张复位卡把所有门锁的数据复位，然后再进行发卡。</P>da
	 * <p>5.特别注意计算机时间一定不能出现混乱，保持时间正确，否则可能导致开门失败。</P>
	 * <p>6.如果某客人卡经门锁自身系统的注销或回收功能后，a
	 * 需将此卡再次发行一次客人卡，否则在接口软件中读写卡将失败。</P>a
	 */
	TemicUtil  temicUtil = (TemicUtil)Native.loadLibrary("F:/card/rfwrcom32", TemicUtil.class);

	/**
	 * 打开串口
	 * @param com	串口号
	 * @param baud	波特率，固定9600
	 * @return	0 正确; 其他 错误
	 */
	public int mif_selecom(int com, int baud);
	
	/**
	 * 读卡
	 * @param BH 门锁编号，4字符长度(不可见)
	 * @param buff 卡中日期时间,长度为10个字符，前8个为年月日时，
	 * 后两位为一小时内发卡序号，读出结果为作业流水号。每张卡的作业流水号是唯一的。
	 * @return 0 正确; 其他 错误
	 */
	public int tem_readmsdata(byte[] BH, byte[] buff);
	
	/**
	 * 读卡
	 * @param BH 门锁编号，8字符长度(可见)
	 * @param buff 卡中日期时间,长度为10个字符，前8个为年月日时，
	 * 后两位为一小时内发卡序号，读出结果为作业流水号。每张卡的作业流水号是唯一的。
	 * @return 0 正确; 其他 错误
	 */
	public int tem_readmsdata1(byte[] BH, byte[] buff);
	
	/**
	 * 写卡
	 * @param BH 不可见的门锁编号，4字符长度(ASC)
	 * @param fksj 发卡时间，为年月日时，每个两位，
	 * 比如发卡时间为2004年8月9日10点，fksj=“04080910”;
	 * <p>注意：如果一小时内的发卡序号超过63此发卡时间可以小时+1方式进行。
	 * 比如，当前时间是2004年08月09日10点，如在此10点内的发卡序号在0-63内则此参数值为04080910,
	 * 如在此10点内的发卡序号已超过63，则此参数值为04080910+1小时=04080911,
	 * 同时发卡序号再从0开始。
	 * 客人退房时仍可调用此函数进行退房写卡，此时的写卡参数中的BH=实际门锁编号，
	 * fksj=00000000,xh=0,sjdw=0,sjlength=1,gs=0即可达到退房功能。</p>
	 * @param xh 一小时内发卡序号，从0到63;
	 * <p>注意：此发卡序号意为当前写卡时间的小时内的第几次写卡，
	 * 如果在当前小时内的写卡次数超过64次(即发卡序号大于63),
	 * 则需要将fksj参数以小时数+1为新的fksj参数进行写卡，
	 * 同时此发卡序号复位为0开始(即新的fksj中的序号)。</p>
	 * @param sjdw 时间单位(小时/天/月/年)；0为小时，1为天，2为月，3为年。其它为天
	 * @param sjlength 时间长度；卡片有效时间。为几天或为几小时。
	 * 取值范围（0--63小时/ 0--63天/ 0--15月 /0--3年）
	 * @param gs 1为取消在用的客人卡（挂失），0为不取消（不挂失）。
	 * @return 0 正确; 其他 错误
	 */
	public int tem_writemsdata(byte[] BH, byte[] fksj, int xh, int sjdw, int sjlength, boolean gs);
	
	/**
	 * 写卡
	 * @param BH 可见的门锁编号，8字符长度(ASC)
	 * @see TemicUtil#tem_writemsdata(char[], char[], int, int, int, boolean)
	 * @return 0 正确; 其他 错误
	 */
	public int tem_writemsdata1(byte[] BH, byte[] fksj, int xh, int sjdw, int sjlength, boolean gs);
	
	/**
	 * 关闭串口
	 * <p>注：读写卡完成后必须调用此函数关闭串口，以便释放串口资源。</p>
	 * @return 0 正确; 其他 错误
	 */
	public int mif_closecom();
	
	/**
	 *  读时钟卡
	 *  <P>注意：必须在门锁管理软件中写过一次的时钟卡</P>
	 *  <P>注意：时钟中的年月日与流水号的年月日需相同</P>
	 * @param fksj 卡中日期时间,长度为10个字符，前8个为年月日时，
	 * 后两位为一小时内发卡序号，读出结果为作业流水号。
	 * @param lpTime 上次写卡的时钟,时钟格式为年+月+日+时+分, 长度为10个字符，
	 * 每段均占用2位，如0712081530表示2007年12月8日15点30分
	 * @return 0 正确; 其他 错误
	 */
	public int tem_readtimercard(byte[] fksj, byte[] lpTime);
	
	/**
	 * 写时钟卡
	 * <P>注意：时钟中的年月日与流水号的年月日需相同</P>
	 * @param fksj 发卡时间，长度为8个字符，分别年月日时，每个两位，
	 * 比如发卡时间为2004年8月9日10点，fksj=“04080910”
	 * @param nXh 一小时内发卡序号，从0到63
	 * @param lpTime 写入的时钟, 长度为10个字符(传递至门锁的时钟)，分别年月日时分，
	 * 每个两位,精确至分钟，如0408092052表示需要将门锁时钟调至2004年8月9日20点52分
	 * @return 0 正确; 其他 错误
	 */
	public int tem_writetimercard(byte[] fksj, int nXh, byte[] lpTime);
}
