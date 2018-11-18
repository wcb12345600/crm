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
    closeDlgData("dlg");
}

//打开框
function openAddSaleChacneDialog() {
    openAddOrUpdateDlg("dlg","添加营销机会");
}



//添加操作
function saveOrUpdateSaleChance() {
    saveOrUpdateData("fm",ctx+"/saleChance/saveOrUpdateSaleChance","dlg",querySaleChancesByParams);
}

/**
 * 更新数据框
 */
function openModifySaleChanceDialog() {
    openModifyDialog("dg","fm","dlg","修改营销机会");
}

/**
 * 删除记录
 */
function deleteSaleChance() {
    deleteData("dg",ctx+"/saleChance/deleteSaleChanceBatch",querySaleChancesByParams);
}

