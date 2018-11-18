/**
 * 格式化开发状态
 * @param value
 * @param row
 * @param index
 * @returns {string}
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
 * 格式化数据
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function formatterOp(value, row, index) {
    var devResult=row.devResult;
    if(devResult==0||devResult==1){
        var href="javascript:openSaleChanceInfoDialog("+'"开发机会数据"'+","+row.id+")";
        return "<a href='"+href+"'>开发</a>";
    }
    if(devResult==2||devResult==3){
        var href="javascript:openSaleChanceInfoDialog("+'"详情机会数据"'+","+row.id+")";
        return "<a href='"+href+"'>查看详情</a>";
    }
}

/**
 * 查询方法
 */
function querySaleChancesByParams() {
    $('#dg').datagrid('load',{
        customerName: $("#customerName").val(),
        devResult: $("#devResult").combobox('getValue'),
        createDate: $("#time").datebox('getValue')
    });
}

/**
 * 打开新的窗口
 */
function openSaleChanceInfoDialog(title,id) {
    /**
     * 打开新的选项卡
     */
    window.parent.openTab(title+"_"+id,ctx+"/cusDevPlan/index?sid="+id);
}