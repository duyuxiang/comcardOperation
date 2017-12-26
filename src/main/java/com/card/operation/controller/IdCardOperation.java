package com.card.operation.controller;

import com.card.operation.mapper.RoomMapper;
import com.card.operation.pojo.Guest;
import com.card.operation.pojo.Quickbooking;
import com.card.operation.pojo.QuickbookingRoomno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by duyuxiang on 2017/12/22.
 * 身份证扫描接口类
 */
@RestController
public class IdCardOperation {

    @Autowired
    RoomMapper roomMapper;

    /**
     * 获取订单列表
     * @param <T>
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.GET)
    @RequestMapping("finance/booking/list")
    public <T> T getBookingList(){
        Map<String,Object> returnMap =new HashMap<String,Object>();
        try{
            List<Map> bookingList = roomMapper.getBookingList();
            returnMap.put("code","0");
            returnMap.put("data",bookingList);
             for(int i=0;i<bookingList.size();i++){
                 if(bookingList.get(i).get("guestname")==null){
                     bookingList.get(i).put("guestname","");
                 }else if(bookingList.get(i).get("teamname")==null){
                     bookingList.get(i).put("teamname","");
                 }
             }
        }catch (Exception e){
            e.printStackTrace();
            returnMap.put("code","50020");
            returnMap.put("data","数据库服务器错误");
        }
        return (T)returnMap;

    }

    /**
     * 获取预订单所有信息
     * @param bookid
     * @param <T>
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.GET)
    @RequestMapping("finance/booking/rooms")
    public <T> T getBookingRooms(String bookid){
        Map<String,Object> returnMap =new HashMap<String,Object>();
        if(bookid==null){
            returnMap.put("code","40010");
            returnMap.put("data","传入参数错误或缺少参数");
            return (T) returnMap;
        }
     try{
         Quickbooking quickbookingInfo = roomMapper.getQuickbookingInfo(bookid);
         if(quickbookingInfo==null){
             returnMap.put("code","40010");
             returnMap.put("data","预订单无效");
             return (T) returnMap;
         }
         List<QuickbookingRoomno> bookingRooms = roomMapper.getBookingRooms(bookid);
         quickbookingInfo.setRoomno(bookingRooms);

         returnMap.put("code","0");
         returnMap.put("data",quickbookingInfo);
     }catch (Exception e){
         e.printStackTrace();
         returnMap.put("code","50020");
         returnMap.put("data","数据库服务器错误");
     }
      return (T)returnMap;
    }


    /**
     * 上传身份信息
     * @param guest
     * @param <T>
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.POST)
    @RequestMapping("finance/upload/idcard")
    public <T> T uploadIdentity(@RequestBody Guest guest){
        Map<String,Object> returnMap =new HashMap<String,Object>();
        System.out.println(guest);
        if(guest==null){
            returnMap.put("code","40010");
            returnMap.put("data","传入参数错误或缺少参数");
            return (T) returnMap;
        }
        if(guest.getGuestName()==null|| guest.getDocumentNo()==null|| guest.getBookId()==null || guest.getRoomId()==null){
            returnMap.put("code","40010");
            returnMap.put("data","传入参数错误或缺少参数");
            return (T) returnMap;
        }
       try{
           List<String> documentnos = roomMapper.selectGuestDocumentnos(guest.getBookId());
           for(int i=0;i<documentnos.size();i++){
               if(guest.getDocumentNo().equals(documentnos.get(i))){
                   returnMap.put("code","40310");
                   returnMap.put("data","不可重复上传");
                   return (T) returnMap;
               }
           }

           Integer integer = roomMapper.insertGuestInfo(guest);
           returnMap.put("code","0");
           returnMap.put("data","上传成功");
       }catch (Exception e){
           e.printStackTrace();
           returnMap.put("code","50020");
           returnMap.put("data","数据库服务器错误");
       }

        return (T)returnMap;
    }


    /**
     * 查询订单  -- 未完成
     * @param queryNumber
     * @param <T>
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.GET)
    @RequestMapping("finance/rooms")
    public <T> T searchBookingInfo(String queryNumber){
        Map<String,Object> returnMap =new HashMap<String,Object>();

       if(queryNumber==null){
           returnMap.put("code","40031");
           returnMap.put("data","传入参数错误或缺少参数");
           return (T) returnMap;
       } else if (queryNumber != null && !"".equals(queryNumber) && queryNumber.length()==20) {   //通过预定单查询
           //查询预订ID
           String bookid = roomMapper.getBookingIdByNo(queryNumber);
           if(bookid==null){
               returnMap.put("code","40041");
               returnMap.put("data","无此预订单号");
               return (T) returnMap;
           }
          //查询预订单所有未入住房间
           List<Map> roomids = roomMapper.getUnCheckRoomIds(bookid);
           if(roomids.size()==0){
               returnMap.put("code","20041");
               returnMap.put("data","订单无可入住房间");
               return (T) returnMap;
           }
           returnMap.put("code","0");
           returnMap.put("data",roomids);
           return (T) returnMap;

       } else if (queryNumber != null && !"".equals(queryNumber)&& queryNumber.length()==4) {          //通过房间号查询




       }else{

       }
        return  null;
    }



}
