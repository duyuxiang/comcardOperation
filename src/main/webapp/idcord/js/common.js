
  // 手机号码验证
$("input[name='phone']").blur(
  function (){ 
    var phone = $(this).val();
    if(!(/^1(3|4|5|7|8)\d{9}$/.test(phone))){ 
        var obj={
           type:"layer-shake",
           title:"特别提醒",
           content:"<div>手机号码有误，请重填",
           area:["500px","200px"]
       };
       method.msg_layer(obj);

        $(this).val("") ;
        return false; 
    } 
})
// 邮箱验证
  $("input[name='bookingEmail']").blur(function(){
   var email = $(this).val();
    if(!(/\w+[@]{1}\w+[.]\w+/.test(email))){  
        var obj={
           type:"layer-shake",
           title:"特别提醒",
           content:"<div>email信息有误，请重填",
           area:["500px","200px"]
       };
       method.msg_layer(obj);
        $(this).val("") ;
        return false; 
    } 
  });
// 获取前台业务员
  //  $.ajax({
  //   type: "get",
  //   url: "http://"+ip+"/user/find/user/msg/data.shtml",
  //   contentType:"application/json;charset=utf-8",
  //   dataType: "json",
  //   data:{"keyWord":"前台业务员"},
  //   success: function(data) {
  //    for (var i = 0; i < data.data.length; i++) {
  //        $("#dl").append("<option value="+data.data[i].userName+">"+data.data[i].userName+"</option>")
  //        $("#dl option[value='"+localStorage.getItem("dl")+"']").attr("selected","selected")
  //    } 
  //     }
  // });
  $("#dl").val(localStorage.getItem("dl"))

// 选项卡效果
    $("#titel_a").click(function(){
       $(this).removeClass("one")
       $(this).addClass("two")
       $("#titel_b").addClass("one")
       $("#titel_b").removeClass("two")
       $("#titel_c").addClass("one")
       $("#titel_c").removeClass("two") 
       $("#ydzl").css("display","block")
       $("#krzl").css("display","none")
       $("#txxl").css("display","none") 
       $(".oneBtn").css("display","block")
       $(".twoBtn").css("display","none")
       $(".threeBtn").css("display","none")   
    })
    $("#titel_b").click(function(){
        $(this).removeClass("one")
       $(this).addClass("two")
       $("#titel_a").addClass("one")
       $("#titel_a").removeClass("two")
       $("#titel_c").addClass("one")
       $("#titel_c").removeClass("two")
        $("#ydzl").css("display","none")
       $("#krzl").css("display","block")
       $("#txxl").css("display","none") 
       $(".oneBtn").css("display","none")
       $(".twoBtn").css("display","block")
       $(".threeBtn").css("display","none")   
    })
    $("#titel_c").click(function(){
         $(this).removeClass("one")
       $(this).addClass("two")
       $("#titel_b").addClass("one")
       $("#titel_b").removeClass("two")
       $("#titel_a").addClass("one")
       $("#titel_a").removeClass("two")
       $("#ydzl").css("display","none")
       $("#krzl").css("display","none")
       $("#txxl").css("display","block") 
       $(".oneBtn").css("display","none")
       $(".twoBtn").css("display","none")
       $(".threeBtn").css("display","block")   
    })
function alerts(a){
  var a=a;
  var obj={
           type:"layer-shake",
           title:"特别提醒",
           content:"<div>"+a,
           area:["500px","200px"]
       };
       method.msg_layer(obj);
}

  function confirms(a,b,c,d){
    var width=a;
    var height=b;
    var title=c;
     var url=d
  popWin.showWin(width,height,title,url);
  // return  preventDefault();
  }

  function closes(){
     var a=$('#maskTop', window.parent.document)
    var b=$('#mask', window.parent.document)
        a.fadeOut(function() {
        a.remove();
         });
         b.fadeOut(function() {
         b.remove();
        });
        parent.location.reload();
  }

