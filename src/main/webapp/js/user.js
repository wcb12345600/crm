function queryUsersByParams() {
    $('#dg').datagrid('load',{
        userName: $("#userName").val(),
        email: $("#email").val(),
        phone: $("#phone").val()
    });
}

function openAddUserDailog() {
    openAddOrUpdateDlg("dlg","添加用户");
}

function closeDlg() {
    $("#dlg").dialog("close");
}

$(function () {

    /**
     * 关闭页面清除遗留数据
     */
    $("#dlg").dialog({
        "onClose":function () {
            //清空表单
            $("#fm").form('clear');
        }
    })
});

function saveOrUpdateUser() {
    saveOrUpdateData("fm",ctx+"/user/saveOrUpdateUser","dlg",queryUsersByParams);
}

function openModifyUserDialog() {
    openModifyDialog("dg","fm","dlg","修改用户");
}

function deleteUser() {
    deleteData("dg",ctx+"/user/deleteUsers",queryUsersByParams);
}