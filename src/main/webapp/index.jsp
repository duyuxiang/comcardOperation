<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String path=request.getContextPath();
    String basePath = request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+path+"/" ;
%>
<html>
<head>
    <title>card operation</title>
    <style>
        .ntive{
            width: 60%;height: 30%;background: #0079D4;margin: 0 auto;margin-top: 10%;
            color: white;
            font-size: 40px;
            line-height: 110px;
            border-radius: 8px;
            cursor: pointer;
        }
        a{
            text-decoration:none;
        }
    </style>
</head>
<body style="text-align: center">

<div style="width: 500px;height: 120px; background: #0079D4;margin: 0 auto;text-align: center;">
    <div style="height: 10%;width: 60%" ></div>
    <div style="width: 60%;height: 60%;margin: 0 auto;background-repeat: no-repeat;background-size: 100% ;background-image: url('img/gd.png');" ></div>
    <div style="width: 50%;height: 30%;margin: 0 auto;background-repeat: no-repeat; background-size: 100% ;background-image: url('img/gudou hot spring.png')" ></div>
</div>
<div style="width: 498px;height: 400px;border: solid 1px #0079D4;text-align: center;margin: 0 auto">
   <a href="card"> <div class="ntive">
        房卡读写
   </div></a>
    <a href="certificates"><div class="ntive">
        证件扫描
    </div></a>

</div>


</body>
</html>
