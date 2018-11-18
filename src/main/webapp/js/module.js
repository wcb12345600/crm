function formateGrade(val) {
    if (val==0){
        return "根级目录";
    }
    if (val==1){
        return "一级目录";
    }
    if (val==2){
        return "二级目录";
    }
}

function queryModulesByParams() {
    $('#dg').datagrid('load',{
        moduleName: $("#moduleName").val(),
        parentId: $("#pid").val(),
        optValue: $("#optValue").val(),
        grade: $("#grade").combobox('getValue')
    });
}

function openAddModuleDailog(){
   //判断是否显示父级菜单
    $("#parentMenu").hide();
    openAddOrUpdateDlg("dlg","添加模块");
}

$(function () {
    $("#dlg").dialog({
        "onClose":function () {
            //清空表单
            $("#fm").form('clear');
        }
    });

    //给菜单层级加一个点击事件
    $('#grade02').combobox({
        onSelect : function (val) {
            var value = val.value;
            if (value==0){
                $("#parentMenu").hide();
            }else{
                $("#parentMenu").show();
                //发送ajax请求
                $('#parentId02').combobox({
                    url:ctx+'/module/queryModuleByGrade?grade='+(value-1),
                    valueField:'id',
                    textField:'moduleName'
                });

            }
        }
    });


});


function saveOrUpdateModule() {
    saveOrUpdateData("fm",ctx+"/module/saveOrUpdateModule","dlg",queryModulesByParams);
}

function openModifyModuleDialog() {
    openModifyDialog("dg","fm","dlg","修改模块");
}

function deleteModule() {
    deleteData("dg",ctx+"/module/deleteModuleByModuleId",queryModulesByParams);
}