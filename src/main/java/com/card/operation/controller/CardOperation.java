package com.card.operation.controller;

import com.card.operation.mapper.RoomMapper;
import com.card.operation.pojo.OperationCard;
import com.card.operation.pojo.PageBean;
import com.card.operation.temic.TemicService;

import com.card.operation.utils.IDCard;
import com.card.operation.utils.IDCardService;
import com.card.operation.utils.StaticCaching;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by duyuxiang on 2017/12/8.
 */
@Controller
public class CardOperation {

    @Autowired
    private RoomMapper roomMapper;

    private SimpleDateFormat sf=new SimpleDateFormat("yyMMddHH");

    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 读写房卡
     * @param roomno
     * @param time
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.GET)
    @RequestMapping("writerCard")
    @ResponseBody
    String home(String roomno,Integer time){

        try {

            //写卡
            if (roomno != null && !"".equals(roomno) && time!=null && time!=0) {
                List<String> lockids = roomMapper.getLockid(roomno);
                byte[] lockid = lockids.get(0).getBytes();
                byte[] date = sf.format(new Date()).getBytes();
                String s="";
                try {
                     s = TemicService.writeData(lockid, date, 2, 1, time, false);
                }catch (Exception e){
                    return "未检测到读卡器";
                }
                System.out.println(s);
                if("正确".equals(s)){
                    boolean flag=false;
                    //判断重复写卡
                    if(StaticCaching.writeCardRecord==null){
                        flag=true;
                    }else{
                        if(!StaticCaching.writeCardRecord.equals(roomno)){
                            flag=true;
                        }
                    }
                    if(flag){
                        //获取客人名
                        List<String> guestName = roomMapper.getGuestName(roomno);
                        if(guestName==null || guestName.size()==0 ){
                            guestName.add("其他");
                        }
                        OperationCard operationCard=new OperationCard();
                        operationCard.setRoomno(roomno); operationCard.setGuestName(guestName.get(0));
                        operationCard.setTime(sdf.format(new Date()));operationCard.setState("写卡");
                        //插入写卡记录
                        roomMapper.insertOperationCard(operationCard);
                        StaticCaching.writeCardRecord=roomno;
                    }

                    return "写卡成功";
                }
                return s;
            } else {
                return "房间号或时间为空";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "写卡失败";
        }

    }


    /**
     * 获取读写卡记录信息
     * @param pageBean
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.GET)
    @RequestMapping("getOperationCardRecord")
    @ResponseBody
    public Object getOperationCardRecord (PageBean pageBean){
        Map paramMap =new HashMap();
        int operationCardsNumber = roomMapper.getOperationCardsNumber();
        pageBean.setRecordTotal(operationCardsNumber);
        paramMap.put("limit",pageBean.getCurrentPage()*pageBean.getPageSize()-pageBean.getPageSize());
        paramMap.put("length",pageBean.getPageSize());
        pageBean.setContent(roomMapper.getOperationCards(paramMap));
        return pageBean;
    }


    /**
     * 读身份证
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600, methods = RequestMethod.GET)
    @RequestMapping("readIdCard")
    @ResponseBody
    public Object readIdCard(){
        Map<String,Object> returnMap =new HashMap<String,Object>();

        try {
            IDCard idCard = IDCardService.readIDCard(3);
            if (idCard == null) {
                returnMap.put("code", "40021");
                returnMap.put("data", "未检测到证件");
                return returnMap;
            }else{
                returnMap.put("code", "0");
                returnMap.put("data",idCard.taskGuest());
                return returnMap;
            }

        }catch (Exception e){
            e.printStackTrace();
            returnMap.put("code", "50010");
            returnMap.put("data", "未检测到读卡器");
            return returnMap;
        }

    }


    /**
     * 读写房卡页面
     * @return
     */
    @RequestMapping("readAndWriteCardPage")
    public String readAndCardPage(){
        return "idcord/housecard.html";
    }

    /**
     * 证件扫描页面
     * @return
     */
    @RequestMapping("documentScanningPage")
    public String documentScanningPage(){
        return "idcord/idcord.html";
    }


    @RequestMapping("homePage")
    public String  getHomePage(){
        return "home.html";
    }
}
