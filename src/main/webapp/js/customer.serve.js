function saveCustomerServe() {
    $('#fm').form('submit',{
            url:ctx+"/customerServe/saveOrUpdateCustomerServe",
        onSubmit: function(){
           return $(this).form('validate');
    },
    success:function(data){
                data= JSON.parse(data);
                if (data.code == 200) {
                    $.messager.alert('来自Crm', data.msg, 'info', function () {
                        //清空表单
                        $("#fm").form("clear");
                    });
                } else {
                    $.messager.alert('来自Crm', data.msg, 'error');
                }
        }
    });

}

function resetValue() {
    $("#fm").form("clear");
}

function openCustomerServeAssignDialog() {
    var rows = $('#dg').datagrid("getSelections");
    if (rows.length == 0) {
        $.messager.alert('来自Crm', "请选择一条数据");
        return;
    }
    if (rows.length > 1) {
        $.messager.alert('来自Crm', "只能选择一条数据");
        return;
    }
    $('#fm').form('load',rows[0]);
    openAddOrUpdateDlg('dlg','操作');
}

function closeCustomerServeDialog() {
    $("#dlg").dialog("close");
}

function addCustomerServeAssign() {
    saveOrUpdateData("fm",ctx+"/customerServe/saveOrUpdateCustomerServe","dlg",function () {
        $("#dg").datagrid('load');
    })
}


function openCustomerServeProceDialog() {
    var rows = $('#dg').datagrid("getSelections");
    if (rows.length == 0) {
        $.messager.alert('来自Crm', "请选择一条数据");
        return;
    }
    if (rows.length > 1) {
        $.messager.alert('来自Crm', "只能选择一条数据");
        return;
    }
    $('#fm').form('load',rows[0]);
    openAddOrUpdateDlg('dlg','操作');
}

function addCustomerServeProce() {
    saveOrUpdateData("fm",ctx+"/customerServe/saveOrUpdateCustomerServe","dlg",function () {
        $("#dg").datagrid('load');
    })
}

function openCustomerServeFeedBackDialog() {
    var rows = $('#dg').datagrid("getSelections");
    if (rows.length == 0) {
        $.messager.alert('来自Crm', "请选择一条数据");
        return;
    }
    if (rows.length > 1) {
        $.messager.alert('来自Crm', "只能选择一条数据");
        return;
    }
    $('#fm').form('load',rows[0]);
    openAddOrUpdateDlg('dlg','操作');
}

function addCustomerServeFeedBack() {
    saveOrUpdateData("fm",ctx+"/customerServe/saveOrUpdateCustomerServe","dlg",function () {
        $("#dg").datagrid('load');
    })
}

function queryCustomerServesByParams() {
    $('#dg').datagrid('load',{
        customer: $("#cusName").val(),
        myd: $("#myd").combobox('getValue'),
        createDate: $("#time").datebox('getValue')
    });
}