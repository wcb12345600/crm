
function queryCustomerLossByParams() {
    $('#dg').datagrid('load',{
        cusName: $("#cusName").val(),
        cusNo: $("#cusNo").val(),
       createDate: $("#time").datebox('getValue')
    });
}

function formateState(val) {
    if (val==0){
        return "暂缓流失";
    }
    if (val==1){
        return "确认流失";
    }
}

function formateOp(val,row) {
    var state = row.state;
    if (state==0){
        var  href="javascript:openAddReprieveDataTab("+row.id+")";
        return "<a href="+href+">添加暂缓<a>";
    }else{
        return "确认流失";
    }
}

function openAddReprieveDataTab(lossId) {
    console.log(lossId);
    window.parent.openTab("添加暂缓措施_"+lossId,ctx+"/customerReprieve/index?lossId="+lossId);
}

