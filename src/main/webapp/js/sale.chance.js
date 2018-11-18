/**
 * 条件查询
 */
function querySaleChancesByParams() {
    $('#dg').datagrid('load',{
        customerName: $("#customerName").val(),
        state: $("#state").combobox('getValue'),
        devResult: $("#devResult").combobox('getValue'),
        createDate: $("#time").datebox('getValue')
    });
}

/**
 * 格式化开发状态
 */
function formatterState(value, row, index) {
    if (value == 0) {
            return "未分配";
    }
    if (value == 1) {
        return "已分配";
    }
}

/**
 * 格式化开发状态
 */
function formatterDevResult(value, row, index) {
    if (value == 0) {
        return "未开发";
    }
    if (value == 1) {
        return "开发中";
    }
    if (value == 2) {
        return "开发成功";
    }
    if (value == 3) {
        return "开发失败";
    }
}

/**
 * 颜色配置
 */
$(function () {
    $('#dg').datagrid({
        rowStyler: function(index,row){
            var devResult = row.devResult;
            if (devResult==0){
                return "background-color:#5bc0de;"; // 蓝色
            }else if (devResult==1){
                return "background-color:#f0ad4e;"; // 黄色
            }else if (devResult==2){
                return "background-color:#5cb85c;"; // 绿色
            }else if (devResult==3){
                return "background-color:#d9534f;"; // 红色
            }
        }
    });

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

//关闭框
function closeDlg() {
    $("#dlg").dialog("close");
}

//打开框
function openAddSaleChacneDialog() {
    $("#dlg").dialog("open").dialog("setTitle","添加营销机会");
}



//添加操作
function saveOrUpdateSaleChance() {
    console.log(111);
    $('#fm').form('submit',{
        url:ctx+"/saleChance/saveOrUpdateSaleChance",
        onSubmit: function(){
            console.log($(this).form('validate'));
            return $(this).form('validate');
        },
        success:function(data){
            data = JSON.parse(data);
            if (data.code==200){
                $.messager.alert("来自Crm",data.msg);
                //跳转到登陆页面
                $('#dg').datagrid('reload');
            }else {
                $.messager.alert("来自Crm",data.msg,"error");
            }
        }
    });
}

/**
 * 更新数据框
 */
function openModifySaleChanceDialog() {
    var rows = $("#dg").datagrid("getSelections");
    if (rows.length==0){
        $.messager.alert("来自Crm","至少选中一条记录进行更新");
        return;
    }
    if (rows.length>1){
        $.messager.alert("来自Crm","只能选中一条记录进行更新");
        $("#dg").datagrid("unselectAll");
        return;
    }

    /**
     * 1.回填表单数据
     * 2.显示弹窗
     */
    $("#fm").form('load',rows[0]);
    $("#dlg").dialog("open").dialog("setTitle","更新营销机会");
}

/**
 * 删除记录
 */
function deleteSaleChance() {
    var rows = $("#dg").datagrid("getSelections");
    if (rows.length==0){
        $.messager.alert("来自Crm","至少选中一条记录进行删除");
        return;
    }
    $.messager.confirm('来自Crm','您确认想要删除'+rows.length+'记录吗？',function(r){
        if (r){
            /**
             * 1.调后台接口删除
             * 2.刷新当前页面
             */
            var ids ="";
            for (var i=0;i<rows.length;i++){
                ids+="ids="+rows[i].id+"&";
            }
            console.log(ids);
            $.ajax({
                url : ctx+"/saleChance/deleteSaleChanceBatch?"+ids,
                type: "post",
                success : function (data) {
                    if (data.code==200){
                        $.messager.alert("来自Crm",data.msg);
                        //跳转到登陆页面
                        $('#dg').datagrid('reload');
                    }else{
                        alert(data.msg);
                    }
                }
            })
        }
    });
}

