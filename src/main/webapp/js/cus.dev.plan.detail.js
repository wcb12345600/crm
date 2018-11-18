$(function () {
    var sid = $("#saleChanceId").val();
   // console.log(sid);
    $("#dg").edatagrid({
        url: ctx + "/cusDevPlan/queryCusDevPlansByParams?sid="+sid,
        saveUrl: ctx + "/cusDevPlan/saveOrUpdateCusDevPlan?sid="+sid,
        updateUrl: ctx + "/cusDevPlan/saveOrUpdateCusDevPlan?sid="+sid
    });

    var devResult = $("#devResult").val();
    if (devResult==2 || devResult==3){
        // 隐藏工具条
        $('#toolbar').hide();
        // 使表格不可编辑
        $('#dg').edatagrid("disableEditing");
    }
});

function addRow() {
    $('#dg').edatagrid("addRow");
}

function saveOrUpdateCusDevPlan() {
    $('#dg').edatagrid("saveRow");
}

function loadEdatagrld() {
    $("#dg").edatagrid("load");
}

/**
 * 删除客户开发计划
 */
function delCusDevPlan() {
    deleteData("dg",ctx + "/cusDevPlan/deleteCusDevPlan",loadEdatagrld);
}

/**
 * 更新营销计划状态
 * @param devResult
 */
function updateSaleChanceDevResult(devResult) {
    var sid = $("#saleChanceId").val();
    
    $.ajax({
        url:ctx+"/saleChance/updateSaleChanceDevResult",
        type:"post",
        data:{
            id:sid,
            devResult:devResult
        },
        success:function (data) {
            if (data.code == 200) {
                $.messager.alert('来自Crm', data.msg, 'info', function () {
                    // 隐藏工具条
                    $('#toolbar').hide();
                    // 使表格不可编辑
                    $('#dg').edatagrid("disableEditing");
                });
            } else {
                $.messager.alert('来自Crm', data.msg, 'error');
            }
        }
    })
    
}