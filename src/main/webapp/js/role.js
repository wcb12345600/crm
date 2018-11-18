/**
 * 条件查询
 */
function queryRolesByParams() {
    $('#dg').datagrid('load',{
        roleName: $("#roleName").val(),
        createDate: $("#time").datebox('getValue')
    });
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

function openAddRoleDailog() {
    openAddOrUpdateDlg("dlg","新增角色");
}

function saveOrUpdateRole() {
    saveOrUpdateData("fm",ctx+"/role/saveOrUpdateRole","dlg",queryRolesByParams);
}

function openModifyRoleDialog() {
    openModifyDialog("dg","fm","dlg","修改角色");
}

/**
 * 打开树
 */
function openRelationPermissionDialog() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择角色进行关联");
        return ;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","只能选择一个角色进行关联!");
        return ;
    }
    //打开弹窗
    $("#permissionDlg").dialog("open");

    //设置roleId值
    $("#roleId").val(rows[0].id);

    openTree(rows[0].id);
}

var treeObj;

function openTree(roleId) {
    $.ajax({
        url:ctx+"/module/queryModuleByRoleId?roleId="+roleId,
        type:"post",
        success:function (data) {
            var setting = {
                check: {
                    enable: true,
                    chkboxType :{ "Y" : "ps", "N" : "ps" }
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onCheck: zTreeOnCheck
                }
            };
            var zNodes = data;
            treeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            zTreeOnCheck();
        }

    });
}

function zTreeOnCheck() {
    var nodes = treeObj.getCheckedNodes(true);
    var nodelist = "";
    for (var i=0;i<nodes.length;i++){
        nodelist+="moduleIds="+nodes[i].id+"&";
    }
    $("#moduleIds").val(nodelist);
    //console.log(nodelist);
}

function doGrant() {
    var roleId = $("#roleId").val();
    var moduleIds = $("#moduleIds").val();
    $.ajax({
        url:ctx+"/role/doGrant?roleId="+roleId+"&"+moduleIds,
        success:function (data) {
            if(data.code==200){
                $("#permissionDlg").dialog("close");
            }else{
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    })
}

/**
 * 删除角色
 */
function deleteRole() {
    deleteData("dg",ctx+"/role/deleteRoleBatch",queryRolesByParams);
}

function closePermissionDlg() {
    $("#permissionDlg").dialog("close");
}

