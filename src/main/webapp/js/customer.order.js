function formateState(val) {
    if (val==0){
        return "未支付";
    }else if (val==1){
        return "已支付";
    }
}

function formateOp(val, row) {
    var orderId = row.id;
    var href = "javascript:openOrderDeatilDialog(" + orderId + ")";
    return "<a href=" + href + ">查看详情</a>";
}


function openOrderDeatilDialog(orderId) {
    var rows = $("#dg").datagrid("getSelections");
    //打开窗口
    $("#dlg").dialog("open");
    //回显数据
    $("#fm").form("load",rows[0]);

    var orderId = rows[0].id;
    //订单详情显示
    $("#dg2").datagrid({
        url:ctx+"/orderDetails/queryOrderDetailsByParams?orderId="+orderId
    })


}