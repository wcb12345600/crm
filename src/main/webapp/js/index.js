function login() {
    var userName= $("#username").val();
    var password= $("#password").val();

    if(isEmpty(userName)){
        alert("用户名为空！");
        return;
    }

    if(isEmpty(password)){
        alert("密码为空！");
        return;
    }

    $.ajax({
        url : ctx+"/user/login",
        type: "post",
        data : {"userName":userName,"userPwd":password},
        success : function (data) {
            if (data.code==200){
                alert(data.msg);
                //把用户信息存到cookie
                $.cookie("userIdStr",data.result.userIdStr);
                $.cookie("userName",data.result.userName);
                $.cookie("realName",data.result.realName);
                //跳转主页
                window.location.href = ctx+"/main";
            }else{
                alert(data.msg);
            }
        }
    })
}