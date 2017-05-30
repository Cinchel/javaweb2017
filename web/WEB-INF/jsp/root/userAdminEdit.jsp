<%--
  Created by IntelliJ IDEA.
  User: mzzhang
  Date: 2017/5/29
  Time: 下午11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="head.jsp"/>



<div class="right_col" role="main">
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3>Plain Page</h3>
            </div>

            <div class="title_right">
                <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search for...">
                        <span class="input-group-btn">
                      <button class="btn btn-default" type="button">Go!</button>
                    </span>
                    </div>
                </div>
            </div>
        </div>

        <div class="clearfix"></div>

        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_title">
                        <h2>Plain Page</h2>
                        <ul class="nav navbar-right panel_toolbox">
                            <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                            </li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a href="#">Settings 1</a>
                                    </li>
                                    <li><a href="#">Settings 2</a>
                                    </li>
                                </ul>
                            </li>
                            <li><a class="close-link"><i class="fa fa-close"></i></a>
                            </li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                        <table id="userAdmiEdit-usersList">
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


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
<jsp:include page="footer.jsp"/>