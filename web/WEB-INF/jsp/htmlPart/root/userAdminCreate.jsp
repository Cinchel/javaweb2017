<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row row-margin-bottom">
    <label for="userAdminCreate-userName" class="col-sm-2 text-right">姓名：</label>
    <div class="col-sm-9">
        <input class="form-control" type="text" id="userAdminCreate-userName">
    </div>
</div>

<div class="row row-margin-bottom">
    <label for="userAdminCreate-role" class="col-sm-2 text-right">账号类型：</label>
    <div class="col-sm-9">
        <select class="form-control" type="text" id="userAdminCreate-role">
            <option value="teacher">普通教师</option>
            <option value="admin">管理员</option>
        </select>
    </div>
</div>

<div class="row row-margin-bottom">
    <label for="userAdminCreate-phone" class="col-sm-2 text-right">电话：</label>
    <div class="col-sm-9">
        <input class="form-control" type="text" id="userAdminCreate-phone">
    </div>
</div>

<div class="row row-margin-bottom">
    <label for="userAdminCreate-title" class="col-sm-2 text-right">职称：</label>
    <div class="col-sm-9">
        <input class="form-control" type="text" id="userAdminCreate-title">
    </div>
</div>

<div class="row row-margin-bottom">
    <label for="userAdminCreate-phone" class="col-sm-2 text-right">简介：</label>
    <div class="col-sm-9">
        <textarea class="form-control" id="userAdminCreate-introduction"></textarea>
    </div>
</div>

<div class="row row-margin-bottom" id="userAdminCreate-createSubmit">
    <div class="col-sm-3 col-sm-offset-3">
        <input class="form-control btn-primary" type="button" id="userAdminCreate-create" value="添加">
    </div>
</div>

<script>
    $('#userAdminCreate-create').click(function () {
        $.post('rootPost/addUser', {
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