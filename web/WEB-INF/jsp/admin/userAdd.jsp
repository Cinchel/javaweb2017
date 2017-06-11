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
                <h3>用户编辑</h3>
            </div>
        </div>

        <div class="clearfix"></div>

        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_title">
                        <h2>添加用户</h2>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                        <form class="form-horizontal form-label-left">
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userAdminCreate-userName">姓名
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input type="text" id="userAdminCreate-userName" required="required" class="form-control col-md-7 col-xs-12">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userAdminCreate-role">账号类型</label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <select id="userAdminCreate-role" class="form-control col-md-7 col-xs-12">
                                        <option value="teacher">普通教师</option>
                                        <option value="admin">管理员</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userAdminCreate-phone">电话
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input id="userAdminCreate-phone" class="form-control col-md-7 col-xs-12" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userAdminCreate-title">职称
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input id="userAdminCreate-title" class="form-control col-md-7 col-xs-12" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="userAdminCreate-introduction">简介
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <textarea id="userAdminCreate-introduction" class="form-control col-md-7 col-xs-12"></textarea>
                                </div>
                            </div>
                            <div class="ln_solid"></div>
                            <div class="form-group">
                                <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                    <button id="userAdminCreate-create" class="btn btn-success col-sm-4" type="button">提交</button>
                                    <button class="btn btn-primary  col-sm-4 col-sm-offset-1" type="reset">重置</button>
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
    $('#userAdminCreate-create').click(function () {
        $.post('post/addUser', {
            userName: $('#userAdminCreate-userName').val(),
            phone: $('#userAdminCreate-phone').val(),
            title: $('#userAdminCreate-title').val(),
            introduction: $('#userAdminCreate-introduction').val(),
            role: $('#userAdminCreate-role').val()
        }, function (result) {
            if (result.status == 1) {
                $('#errorAlert-content').html(result.message);
                $('#errorAlert').modal('show');
                $('#userAdminCreate-userName').val('');
                $('#userAdminCreate-phone').val('');
                $('#userAdminCreate-title').val('');
                $('#userAdminCreate-introduction').val('');
            } else {
                $('#errorAlert-content').html(result.message);
                $('#errorAlert').modal('show');
            }
        });
    });
</script>
<jsp:include page="footer.jsp"/>