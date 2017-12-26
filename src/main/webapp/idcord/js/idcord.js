$.ajax({
    type: "get",
    url: "http://"+ip+"/finance/booking/list",
    contentType:"application/json;charset=utf-8",
    dataType: "json",
    success: function(data) {
       if(data.code!='0'){
           return ;
       }

    var data=data.data;
   for (var i = 0; i < data.length; i++) {
        $("#house").append("<tr name="+data[i].id+">"+
            "<td >"+data[i].bookingno+"</td>"+
            "<td >"+data[i].guestname+"</td>"+
            "<td >"+data[i].teamname+"</td>"+
            "<td >"+data[i].startdate+"</td>"+
           "<td >"+data[i].enddate+"</td>"+
          "</tr>")
      }

      if(data.length!=0){
          infoma(data[0].id);
      }else{
          return ;
      }

      function infoma(a){
        var a=a;
        $.ajax({
    type: "get",
    url: "http://"+ip+"/finance/booking/rooms",
    contentType:"application/json;charset=utf-8",
    dataType: "json",
    data:{"bookid": a},
    success: function(data) {
        if(data.code!='0'){
            return ;
        }
     var data=data.data 
       for(var key in data){
              $("div[name="+key+"]").html(data[key])
            }

        for (var i = 0; i <data.roomno.length; i++) {
         $("#table").append("<tr name="+data.roomno[i].bookId+">"+
            "<td >"+data.roomno[i].roomNo+"</td>"+
            "<td >"+data.roomno[i].typeName+"</td>"+
            "<td >"+data.roomno[i].housePrices+"</td>"+
            "<td >"+data.roomno[i].doorLockId+"</td>"+
           "<td ><button class='button' name="+data.roomno[i].roomNo+" data-value="+data.roomno[i].bookId+" value="+data.roomno[i].roomId+">扫描</button></td>"+
          "</tr>")

        }
         $(".button").click(function(){
          $(this).attr('disabled',"true")
          var a=$(this).attr("name")
          var bookid=$(this).attr("data-value")
          var roomId=$(this).attr("value")
          var ab=$(this)
       $.ajax({
    type: "get",
    url: "http://"+ip+"/readIdCard",
    contentType:"application/json;charset=utf-8",
    dataType: "json",
    success: function(data) {

     ab.removeAttr("disabled")
     if (data.code==0) {
        $("#tables").append("<tr>"+
            "<td style='width:8%'>"+a+"</td>"+
            "<td style='width:8%' name='guestName' class='aa'>"+data.data.guestName+"</td>"+
            "<td style='width:8%' name='sex' class='aa'>"+data.data.sex+"</td>"+
            "<td style='width:8%' name='ethnic' class='aa'>"+data.data.ethnic+"</td>"+
             "<td style='width:10%' name='birthDay' class='aa'>"+data.data.birthDay+"</td>"+
             "<td style='width:25%' name='address' class='aa'>"+data.data.address+"</td>"+
             "<td style='width:20%' name='documentNo' class='aa'>"+data.data.documentNo+"</td>"+
           "<td style='width:13%'><button class='addmess' name="+bookid+" data-value="+roomId+" style='background-color: #00B6D5'>"+
           "上传</button>&nbsp;&nbsp;&nbsp;&nbsp;<button class='plus' >删除</button></td>"+
          "</tr>")
     }
     else{
      alerts(data.data)
     }
     $(".addmess").click(function(){
      if (confirm("确定上传！！！")) {
        var buickbooking = {}; 
             $(this).parent().parent().children(".aa").each(function() {
                 buickbooking[$(this).attr("name")]=$(this).html()   
                });
              buickbooking['roomId']=$(this).attr('data-value')
              buickbooking['bookId']=$(this).attr('name')
              console.log(data)
              var m= $(this).parent().parent()
               $.ajax({
             type: "post",
             url: "http://"+ip+"/finance/upload/idcard",
              contentType:"application/json;charset=utf-8",
             dataType: "json",
              data:JSON.stringify(buickbooking),
                success: function(data) {
                 if (data.code==0) {
                  alert(data.data)
                 m.css("backgroundColor","#ccc")
                 }
                 else{
                 alerts(data.data)
                 }
               }
             });    
      }
     
      return  preventDefault();
     })

      $(".plus").click(function(){
        
         if (confirm("确定删除！！！")) {
        $(this).parent().parent().remove()  
      }
      return  preventDefault();
     })

      }
       });
       return false;

        })

      }

       });
      }

      $("#house tr").click(function(){
        $("#table tr").remove()
       infoma($(this).attr("name"))
      })
  
      }

       });