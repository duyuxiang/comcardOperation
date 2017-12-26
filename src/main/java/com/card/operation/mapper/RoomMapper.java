package com.card.operation.mapper;

import com.card.operation.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 查询房间信息
 * Created by duyuxiang on 2017/12/9.
 */
public interface RoomMapper {

    //查询所有客房信息
    @Select("SELECT * FROM GS_ROOM")
    List<Room> getRooms();

    //门锁ID 获取房间信息
    @Select("SELECT * FROM GS_ROOM WHERE DOORLOCKID=#{doorLockId}")
    Room getRoomByLocakId(@Param("doorLockId") String doorLockId);

    //查询房间消费信息
    @Select("SELECT * FROM GS_KFCONSUMP_RECORD WHERE ROOMNO=#{roomno}")
    List<KfconsumpRecord> getRoomRecord(@Param("roomno") String roomno);

    //房间号 获取门锁id
    @Select("SELECT DOORLOCKID FROM GS_ROOM WHERE ROOMNO=#{roomno}")
    List<String> getLockid(@Param("roomno") String roomno);

    //插入读写卡记录
    @Insert("INSERT INTO OPERATIONCARD VALUES(null,#{roomno},#{guestName},#{time},#{state})")
    Integer insertOperationCard(OperationCard operationCard);

    //通过房间号读取客人名
    @Select("SELECT A.GUESTNAME FROM GS_GUEST A,GS_ROOM B WHERE A.ROOMID =B.ID AND B.ROOMNO=#{roomno}")
    List<String> getGuestName(@Param("roomno") String roomno );

    //获取读写卡记录
    @Select("SELECT *  FROM OPERATIONCARD ORDER BY TIME DESC limit #{limit},#{length}")
    List<OperationCard> getOperationCards(Map map);
    //获取读写卡记录总数 -- 分页
    @Select("SELECT count(1)  FROM OPERATIONCARD")
    int  getOperationCardsNumber();


    //通过房间号查询预订和房间信息
    @Select("select a.bookingNo,a.guestName,a.phoneNo,a.startDate,a.endDate,b.roomNo,b.typeName " +
            "from GS_QUICKBOOKING a,GS_QUICKBOOKING_ROOMNO b ,gs_room c " +
            "where a.id=b.bookid and b.roomid =c.id and b.roomno=#{roomno} " +
            "and (a.bookingflag='部分入住' or a.bookingflag='全部入住') and c.flag!='空房'")
    BookRoomInfo getRoomBookInfo(@Param("roomno") String roomno);


    //通过预定单号查询预订ID
    @Select("SELECT ID FROM GS_QUICKBOOKING WHERE BOOKINGNO=#{bookingno}")
    String getBookingIdByNo(@Param("bookingno") String bookingno);


    //查询预订单所有未入住房间
    @Select("select a.* from GS_QUICKBOOKING_ROOMNO a,GS_ROOM b where a.roomid=b.id and  a.bookid=#{bookid} and b.flag='空房'")
    List<Map> getUnCheckRoomIds(@Param("bookid") String bookid);

    //查询预订房间详细信息
    @Select(" select a.roomno,b.typename,a.doorlockid,a.id,b.typeid ,b.houseprices  from gs_room a,gs_roomtype b where a.roomtype=b.typeId and a.id=#{roomid}")
    Map getReserveRoomInfo(@Param("roomid") String roomid);

//    select a.roomno roomNo,a.typename typeName,a.doorlockid doorLockId,a.id roomId,
//        case when b.price is not null then b.price else a.houseprices end housePrices from (
//        select a.roomno,b.typename,a.doorlockid,a.id,b.typeid ,b.houseprices  from gs_room a,gs_roomtype b
//        where a.roomtype=b.typeId and a.id in
//                (${})
//            ) a
//    left join REALTIMEHOUSEPRICE b on  a.typeid=b.typeid and b.time=date_format(now(),'%Y-%m-%d')


    //查询明确预订 部分入住 预订单信息
    @Select("select id,bookingno,guestname,teamname,startdate,enddate,markets,bookingflag from GS_QUICKBOOKING where bookingflag='明确预定' or bookingflag='部分入住'")
    List<Map> getBookingList();


    //查询订单所有未入住房间信息
    @Select("select a.* from GS_QUICKBOOKING_ROOMNO a,gs_room b where a.roomid=b.id  and a.bookid=#{bookid} and b.flag='空房'")
    List<QuickbookingRoomno> getBookingRooms(@Param("bookid") String bookid);

    //查询预订单信息
    @Select("select *  from GS_QUICKBOOKING where id=#{bookid}")
    Quickbooking getQuickbookingInfo(String bookid);


    //上传身份证信息
    @Insert("insert into gs_guest values (#{id},#{guestName},#{nickName},#{englishName}," +
            "#{marital},#{nationality},#{languages},#{cardType},#{ethnic},#{documentNo}," +
            "#{nativePlace},#{sex},#{occupation},#{birthDay},#{workUnit},#{cityProper}," +
            "#{post},#{regional},#{stopCause},#{postalCode},#{address},#{officePhone}," +
            "#{phoneNumber},#{telePhone},#{visaAgency},#{visaType},#{visaNumber},#{visaDate}," +
            "#{periodValidity},#{entryTime},#{entryPorts},#{newsPaperType},#{religiousBelief}," +
            "#{secondCard},#{comeFrom},#{arrivalFlight},#{whereToGo},#{leaveFlight},#{positions}," +
            "#{carType},#{carPlateNumber},#{specialDemand},#{hobby},#{taboo},#{bookId},#{vipId},#{roomId},#{customertype},#{state})")
    Integer insertGuestInfo(Guest guest);


    //查询预订单所有身份证号码
    @Select("select documentno from gs_guest where bookid=#{bookid} and state='1'")
    List<String> selectGuestDocumentnos(@Param("bookid") String bookid);
}
