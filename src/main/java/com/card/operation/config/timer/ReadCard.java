package com.card.operation.config.timer;

import com.card.operation.mapper.RoomMapper;
import com.card.operation.pojo.BookRoomInfo;
import com.card.operation.pojo.OperationCard;
import com.card.operation.pojo.Room;
import com.card.operation.temic.CardWebSocket;
import com.card.operation.temic.TemicService;
import com.card.operation.utils.StaticCaching;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.json.JsonObject;
import javax.smartcardio.Card;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by duyuxiang on 2017/12/9.
 * 读卡定时器
 */
@Configuration
@EnableScheduling
public class ReadCard {

    @Autowired
    private RoomMapper roomMapper;

    private SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 定时读卡,读到正确内容,推送给客户端
     */
    @Scheduled(cron = "0/2 * * * * ?")
    public void readCard(){
        List<String> strings=null;
        Map map=new HashMap();
        try {
           strings = TemicService.readVisibleData();
        }catch (Exception e){
            try {
                map.put("code","50010");
                map.put("data","未检测到读卡器");
                CardWebSocket.sendInfo(JSONObject.fromObject(map).toString());
                System.out.println("未检测到读卡器");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return ;
        }
            System.out.println(strings);
            if (strings!=null && strings.get(0)!=null && "正确".equals(strings.get(0))) {
                //通过门锁ID获取房间信息
                Room room = roomMapper.getRoomByLocakId(strings.get(1));
                if(room==null){
                    map.put("code","200400");
                    map.put("data","无房间信息");

                    try {
                        JSONObject jsonObject = JSONObject.fromObject(map);
                        CardWebSocket.sendInfo(jsonObject.toString());
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //通过房间号取订单信息
                BookRoomInfo roomBookInfo = roomMapper.getRoomBookInfo(room.getRoomNo());
                System.out.println(room);
                System.out.println(roomBookInfo);

                if(roomBookInfo==null){
                    map.put("code","200400");
                    map.put("data","此卡为"+room.getRoomNo()+"房间,房间无入住信息");

                }else{
                    boolean flag=false;
                    //判断是否重复读
                    if(StaticCaching.readCardRecord==null) {
                        flag=true;
                    }else{
                        if(!StaticCaching.readCardRecord.equals(room.getRoomNo())){
                        flag=true;
                        }
                    }
                    if(flag) {
                        //生成读卡记录信息
                        OperationCard operationCard = new OperationCard();
                        operationCard.setRoomno(room.getRoomNo());
                        operationCard.setGuestName(roomBookInfo.getGuestName());
                        operationCard.setTime(sim.format(new Date()));
                        operationCard.setState("读卡");
                        try {
                            //插入读卡记录信息
                            int i = roomMapper.insertOperationCard(operationCard);
                            if(i!=0){
                            StaticCaching.readCardRecord=room.getRoomNo();
                            }
                        }catch (Exception e){

                        }

                    }


                    map.put("code","0");
                    map.put("data",roomBookInfo);
                }
                try {
                JSONObject jsonObject = JSONObject.fromObject(map);
                CardWebSocket.sendInfo(jsonObject.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
                try {
                    map.put("code","50010");
                    map.put("data",strings.get(0));
                    CardWebSocket.sendInfo(JSONObject.fromObject(map).toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

}