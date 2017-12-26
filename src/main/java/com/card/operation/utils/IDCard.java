package com.card.operation.utils;

import com.card.operation.pojo.Guest;

/**
 * 身份证信息实体类
 * @author taochuang
 * @since 17.11.15
 */
public class IDCard {
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 民族
	 */
	private String nation;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 出生日期
	 */
	private String birthday;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 身份证号码
	 */
	private String cardNumber;
	/**
	 * 签发机关
	 */
	private String grantDept;
	/**
	 * 期限起始日期
	 */
	private String usefulLifeBegin;
	/**
	 * 期限结束日期
	 */
	private String usefulLifeEnd;
	/**
	 * 新住址
	 */
	private String newAddress;


	public Guest  taskGuest(){
		Guest guest=new Guest();
		guest.setGuestName(this.name);
		guest.setEthnic(this.nation);
		guest.setSex(this.sex);
		guest.setBirthDay(this.birthday);
		guest.setAddress(this.address);
		guest.setDocumentNo(this.cardNumber);
		guest.setVisaAgency(this.grantDept);

		return guest;
	}

	
	public IDCard(String name, String nation, String sex, String birthday, String address, String cardNumber,
			String grantDept, String usefulLifeBegin, String usefulLifeEnd, String newAddress) {
		super();
		this.name = name;
		this.nation = nation;
		this.sex = sex;
		this.birthday = birthday;
		this.address = address;
		this.cardNumber = cardNumber;
		this.grantDept = grantDept;
		this.usefulLifeBegin = usefulLifeBegin;
		this.usefulLifeEnd = usefulLifeEnd;
		this.newAddress = newAddress;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getGrantDept() {
		return grantDept;
	}
	public void setGrantDept(String grantDept) {
		this.grantDept = grantDept;
	}
	public String getUsefulLifeBegin() {
		return usefulLifeBegin;
	}
	public void setUsefulLifeBegin(String usefulLifeBegin) {
		this.usefulLifeBegin = usefulLifeBegin;
	}
	public String getUsefulLifeEnd() {
		return usefulLifeEnd;
	}
	public void setUsefulLifeEnd(String usefulLifeEnd) {
		this.usefulLifeEnd = usefulLifeEnd;
	}
	public String getNewAddress() {
		return newAddress;
	}
	public void setNewAddress(String newAddress) {
		this.newAddress = newAddress;
	}
	@Override
	public String toString() {
		return "IDCard [name=" + name + ", nation=" + nation + ", sex=" + sex + ", birthday=" + birthday + ", address="
				+ address + ", cardNumber=" + cardNumber + ", grantDept=" + grantDept + ", usefulLifeBegin="
				+ usefulLifeBegin + ", usefulLifeEnd=" + usefulLifeEnd + ", newAddress=" + newAddress + "]";
	}
	
	
}
