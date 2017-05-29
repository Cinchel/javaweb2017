<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table id="userAdmiEdit-usersList">
</table>
<script>
    function userAdminEdit(){
        $('#userAdmiEdit-usersList').bootstrapTable('refresh');
    }
    $($('#userAdmiEdit-usersList').bootstrapTable({
        afterLoad: function() {
            $.fn.editable.defaults.mode = 'popup';
        },
        method: 'get',
        idField: 'id',
        url:'rootPost/usersListPost',
        classes: 'table table-striped table-condensed table-hover',
        method: 'get',
        columns: [{
            field: 'id',
            title: 'ID',
            align: 'center',
            valign: 'middle',
            visible: false
        }, {
            field: 'userName',
            title: '姓名',
            align: 'center',
            valign: 'middle',
            editable:true,
            editableUrl: "rootPost/userAdminEdit"
        }, {
            field: 'title',
            title: '职称',
            align: 'center',
            valign: 'middle',
            editable:true,
            editableUrl: "rootPost/userAdminEdit",
            width: '15%'
        }, {
            field: 'phone',
            title: '电话',
            align: 'center',
            valign: 'middle',
            editable:true,
            editableUrl: "rootPost/userAdminEdit"
        },{
            field: 'role',
            title: '用户类型',
            align: 'center',
            valign: 'middle',
            width: '20%'
        }, {
            field: 'introduction',
            title: '简介',
            align: 'center',
            valign: 'middle',
            editable:true,
            editableUrl: "rootPost/userAdminEdit",
            editableType: "textarea"
        }, {
            field: 'delete',
            title: '删除用户',
            align: 'center',
            valign: 'middle',
            width: '5%'
        }],
        pagination: true,
        sidePagination: 'server',
        pageSize: 20
    }));

    function userAdminEdit_delete(userId) {
        var yes = function() {
            $.post('rootPost/userDelete', {
                userId : userId
            }, function (data) {
                if(data.status == 0) {
                    $('#errorAlert-content').html("删除失败："+data.message);
                    $('#errorAlert').modal('show');
                }
                else userAdminEdit();
            });
        };
        $('#confirmBox-yes').unbind();
        $('#confirmBox-no').unbind();
        $('#confirmBox-yes').click(yes);
        $('#confirmBox-title').html("确认删除？");
        $('#confirmBox-content').html("是否删除该用户？");
        $('#confirmBox').modal('show');
    }

    function userAdminEdit_toggleRole(userId) {
        $.post('rootPost/userToggleRole', {
            userId : userId
        }, function (data) {
            if(data.status == 1) userAdminEdit();
            else {
                $('#errorAlert-content').html("出现异常");
                $('#errorAlert').modal('show');
            }
        });
    }

</script>