function openTab(text, url, iconCls){
    if($("#tabs").tabs("exists",text)){
        $("#tabs").tabs("select",text);
    }else{
        var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='" + url + "'></iframe>";
        $("#tabs").tabs("add",{
            title:text,
            iconCls:iconCls,
            closable:true,
            content:content
        });
    }
}

/**
 * 安全退出操作
 */
function logout() {
    $.messager.confirm('来自Crm','您确认想要安全退出吗？',function(r){
        if (r){
            //清除cookie
            $.removeCookie("userIdStr");
            $.removeCookie("userName");
            $.removeCookie("realName");
            //跳转到登陆页面
            window.location.href= ctx +"/index";
        }
    });
}

/**
 * 打开修改框
 */
function openPasswordModifyDialog() {
    $("#dlg").dialog("open");
}

/**
 * 关闭修改框
 */
function closePasswordModifyDialog() {
    $("#dlg").dialog("close");
}

/**
 * 修改密码，保存按钮
 */
function modifyPassword() {
    $('#fm').form({
            url:ctx+"/user/updPassword",
            onSubmit: function(){
                console.log($(this).form('validate'));
                return $(this).form('validate');
            },
        success:function(data){
            data = JSON.parse(data);
                if (data.code==200){
                    $.messager.alert("来自Crm",data.msg,"info",function () {
                        //清空cookie
                        $.removeCookie("userIdStr");
                        $.removeCookie("userName");
                        $.removeCookie("realName");
                        //跳转到登陆页面
                        window.location.href= ctx +"/index";
                    })
                }else {
                    $.messager.alert("来自Crm",data.msg,"error");
                }
            }
    });

    $('#fm').submit();
}

