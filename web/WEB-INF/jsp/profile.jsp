<%--
  Created by IntelliJ IDEA.
  User: Libby
  Date: 2017/6/14
  Time: 23:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="common/head.jsp"/>

<div class="right_col" role="main">
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3>个人中心</h3>
            </div>
        </div>

        <div class="clearfix"></div>

        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_title">
                        <h2>修改密码</h2>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                        <form class="form-horizontal form-label-left">
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for='updatePassword-old-password'>原密码：</label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input type='password' class='form-control col-md-7 col-xs-12' id='updatePassword-old-password'>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for='updatePassword-new-password'>新密码：</label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input type='password' class='form-control col-md-7 col-xs-12' id="updatePassword-new-password">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for='updatePassword-repeat-new-password'>重复新密码：</label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input type='password' class='form-control col-md-7 col-xs-12' id='updatePassword-repeat-new-password'>
                                </div>
                            </div>

                            <div class="ln_solid"></div>
                            <div class="form-group">
                                <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                    <button id="updatePassword-submit" class="btn btn-success col-sm-4" type="button">提交</button>
                                    <button class="btn btn-primary col-sm-4 col-sm-offset-1" type="reset">重置</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_title">
                        <h2>更新个人设置</h2>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                        <form class="form-horizontal form-label-left">
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userAdminCreate-userName">姓名
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input type="text" id="userAdminCreate-userName" required="required" class="form-control col-md-7 col-xs-12" value="${user.getUserName()}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userAdminCreate-phone">电话
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input id="userAdminCreate-phone" class="form-control col-md-7 col-xs-12" type="text" value="${user.getPhone()}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userAdminCreate-title">职称
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input id="userAdminCreate-title" class="form-control col-md-7 col-xs-12" type="text" value="${user.getTitle()}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userAdminCreate-introduction">简介
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <textarea id="userAdminCreate-introduction" class="form-control col-md-7 col-xs-12">${user.getIntroduction()}</textarea>
                                </div>
                            </div>
                            <div class="ln_solid"></div>
                            <div class="form-group">
                                <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                    <button id="userAdminCreate-create" class="btn btn-success col-sm-4" type="button">提交</button>
                                    <button class="btn btn-primary col-sm-4 col-sm-offset-1" type="reset">重置</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>


    </div>
</div>

<script>

    $('#updatePassword-submit').click(function () {
        var oldPwd = $('#updatePassword-old-password').val();
        var newPwd = $('#updatePassword-new-password').val();
        var reNewPwd = $('#updatePassword-repeat-new-password').val();
        if (oldPwd == '' || newPwd == '' || reNewPwd == '') {
            clearAll();
            $('#errorAlert-content').html('信息不全！');
            $('#errorAlert').modal('show');
            return;
        }
        if (newPwd != reNewPwd) {
            clearAll();
            $('#errorAlert-content').html('两次密码不一致！');
            $('#errorAlert').modal('show');
            return;
        }
        $.post('../modifyPassword', {
            oldPwd: $.md5(oldPwd),
            newPwd: $.md5(newPwd)
        }, function (result) {
            if (result.status == 1) {
                clearAll();
                $('#errorAlert-content').html('密码修改成功！');
                $('#errorAlert').modal('show');
                $('#ErrorAlert').modal('show');
            } else {
                clearAll();
                $('#errorAlert-content').html(result.message);
                $('#errorAlert').modal('show');
                $('#ErrorAlert').modal('show');
            }
        });
    });

    function clearAll() {
        $('#updatePassword-old-password').val('');
        $('#updatePassword-new-password').val('');
        $('#updatePassword-repeat-new-password').val('');
    }


    $('#userAdminCreate-create').click(function () {
        $.post('../updateUser', {
            userName: $('#userAdminCreate-userName').val(),
            phone: $('#userAdminCreate-phone').val(),
            title: $('#userAdminCreate-title').val(),
            introduction: $('#userAdminCreate-introduction').val(),
        }, function (result) {
            if (result.status == 1) {
                $('#errorAlert-content').html(result.message);
                $('#errorAlert').modal('show');
            } else {
                $('#errorAlert-content').html(result.message);
                $('#errorAlert').modal('show');
            }
        });
    });


</script>
<jsp:include page="common/footer.jsp"/>
