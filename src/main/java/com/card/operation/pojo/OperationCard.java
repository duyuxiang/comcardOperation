package com.card.operation.pojo;

/**
 * Created by duyuxiang on 2017/12/20.
 * 读写卡记录
 */
public class OperationCard {

  private int     id;
  private String  roomno;     //房号
  private String  guestName;  //客人名
  private String  time;       //时间
  private String  state;      //读写状态


    public OperationCard() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "OperationCard{" +
                "id=" + id +
                ", roomno='" + roomno + '\'' +
                ", guestName='" + guestName + '\'' +
                ", time='" + time + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
