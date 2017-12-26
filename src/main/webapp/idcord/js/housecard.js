function message(a,b){
  var a=a;
  var b=b;
   $.ajax({
              type: "get",
              url: "http://127.0.0.1:8089/getOperationCardRecord",
              contentType:"application/json;charset=utf-8",
              dataType: "json",
              async: false,
              data:{"currentPage":a,"pageSize":b},
                success: function(data) {
                  num=data.pageTotal;
                 console.log(data)
                 for (var i = data.content.length - 1; i >= 0; i--) {
                   $("#content").append("<tr>"+
                       "<td>"+data.content[i].roomno+"</td>"+
                       "<td>"+data.content[i].guestName+"</td>"+
                       "<td>"+data.content[i].time+"</td>"+
                       "<td>"+data.content[i].state+"</td>"+
                  "</tr>")
                 }   
               }
        });
}
 message(1,15) 
  Page({
      num:num,         //页码数
      startnum:1,       //指定页码
      elem:$('#page1'),   //指定的元素
      callback:function(n){ //回调函数
         $("#content tr").remove()
           message(n,15) 
      }
    });

// 提交验证
  function submit(){
    
  if ($("input[name='days']").val()=="") {
       alerts("天数差必须")
  }
  else if($("input[name='roomno']").val()=="") {
       alerts("房间号")
  }
  else{
   $.ajax({
         type: "get",
         url: "http://127.0.0.1:8089/writerCard",
         async: false,
         data:{"roomno":$("input[name='roomNo']").val(),"time":$("input[name='days']").val()},
         success: function(data) {
             console.log(data)
             alerts(data) 
               }
        });
    return false
  }
}

//     function addplus(){
//        s1  =  $("input[name='startDate']").val()
//        s2  =  $("input[name='endDate']").val()
//        $("input[name='days']").val(DateDiff(s1,s2))
//     }
//    //计算天数差的函数，通用
//    function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2002-12-18格式
//        var  aDate,  oDate1,  oDate2,  iDays
//        aDate  =  sDate1.split("-")
//        oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2002格式
//        aDate  =  sDate2.split("-")
//        oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])
//        iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数
//        return  iDays
// }


var webSocket=null;
    //打开socket
    function openSokect() {
      $("#submit").css("display","none")
      $("#guestName").css("display","block")
      $("#bookingNo").css("display","block")
      $("#days").css("display","none")
        if('WebSocket' in window){
            webSocket = new WebSocket("ws://localhost:8089/cardWebSocket");
        } else{
            alert('Not support websocket')
        }

        webSocket.onerror = function(event) {
            onError(event)
        };

        webSocket.onopen = function(event) {
            onOpen(event)
        };

        webSocket.onmessage = function(event) {
            onMessage(event)
        };
    }


    //接收到服务端消息调用的方法
    function onMessage(event) {
       var a=JSON.parse(event.data)
       var data=a.data 
       console.log(a.code)
       if (a.code==0) {
       for(var key in data){
              $("input[name="+key+"]").val(data[key])
              $("select[name="+key+"]").val(data[key])
            }
            $("#messages").html("")
      }
      else{
      //   alerts(a.data)
      $("#messages").html(a.data)
      $("input[name=roomNo]").val("")
      $("input[name=startDate]").val("")
      $("input[name=endDate]").val("")
      $("input[name=bookingNo]").val("")
      $("input[name=guestName]").val("")
      }
    }
    //建立socket时调用的方法
    function onOpen(event) {
    }
    //socket连接异常调用的方法
    function onError(event) {
        alert("连接异常");
    }

   //关闭socket
    function closeSocket() {
      $("#submit").css("display","block")
      $("#guestName").css("display","none")
      $("#bookingNo").css("display","none")
       $("#days").css("display","block")
        if(webSocket!=null) {
            webSocket.close();
        }
        $("#messages").html("");

    }