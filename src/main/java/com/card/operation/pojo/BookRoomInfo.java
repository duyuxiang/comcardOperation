package com.card.operation.pojo;

/**
 * Created by duyuxiang on 2017/12/21.
 * 读卡返回房间信息
 */
public class BookRoomInfo {

    private String bookingNo;       //预定单号
    private String guestName;       //客人名字
    private String phoneNo;         //手机号码
    private String startDate;       //入住时间
    private String endDate;         //离开时间
    private String roomNo;          //房间号
    private String typeName;        //房间类型




    public BookRoomInfo() {
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "BookRoomInfo{" +
                "bookingNo='" + bookingNo + '\'' +
                ", guestName='" + guestName + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", roomNo='" + roomNo + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
