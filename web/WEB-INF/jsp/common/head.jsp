<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${title}</title>

    <link rel='stylesheet' type='text/css' href='public/css/bootstrap.min.css'/>
    <link rel='stylesheet' type='text/css' href='public/bootstrap3-editable/css/bootstrap-editable.css'/>
    <link rel='stylesheet' type='text/css' href='public/css/font-awesome.min.css'/>
    <link rel='stylesheet' type='text/css' href='public/css/bootstrap-table.min.css'/>
    <link rel='stylesheet' type='text/css' href='public/css/buttons.css'/>
    <link rel='stylesheet' type='text/css' href='public/css/square/green.css'/>
    <link rel='stylesheet' type='text/css' href='public/css/common.css'/>
    <link rel='stylesheet' type='text/css' href='public/css/bootstrap-treeview.min.css'/>


    <script src='public/js/jquery-1.12.0.min.js' type='text/javascript'></script>
    <script src='public/js/bootstrap.min.js' type='text/javascript'></script>
    <!-- script src="common/Script/jquery-migrate-1.2.1.js" type="text/javascript"></script -->
    <script src="public/js/jquery.validate.min.js" type="text/javascript" charset="utf-8"></script>
    <script src='public/js/messages_zh.min.js' type='text/javascript'></script>
    <script src='public/js/icheck.min.js' type='text/javascript'></script>
    <script src='public/js/jquery.md5.js' type='text/javascript'></script>
    <script src='public/bootstrap3-editable/js/bootstrap-editable.js' type='text/javascript'></script>
    <script src='public/js/bootstrap-table.js' type='text/javascript'></script>
    <script src='public/js/bootstrap-table-editable.js' type='text/javascript'></script>
    <script src='public/js/common.js' type='text/javascript'></script>
    <script src='public/js/bootstrap-treeview.min.js' type='text/javascript'></script>
</head>
<nav class='main-navigation' id="main-navigation">
    <div class='container'>
        <div class='row'>
            <div class='col-sm-12'>
                <div class='' role='navigation' id='main-menu'>
                    <ul class='menu'>
                        <li role='presentation'><img src='public/img/logo.jpg' class='' style='height: 56px'></li>
                        <c:if test="${ not empty user }">

                            <li role='presentation' class='dropdown' style='float: right!important;margin-right: 0px;'>
                                <div class='dropdown'>
                                    <button class='btn btn-default dropdown-toggle' type='button' id='dropdownMenu1'
                                            data-toggle='dropdown' aria-haspopup='true' aria-expanded='true'>
                                            ${user.getUserName()}
                                        <span class='caret'></span>
                                    </button>
                                    <ul class='dropdown-menu' aria-labelledby='dropdownMenu1'>
                                        <li><a id='updatePassword' data-toggle='modal'
                                               data-target='#UpdatePassModel'>更改密码</a></li>
                                        <li role='separator' class='divider'></li>
                                        <li><a id='logout'>退出</a></li>
                                    </ul>
                                </div>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>

<div class="container">
    <div class="modal fade" id="UpdatePassModel" tabindex="-1" role="dialog" aria-labelledby="UpdatePassLabel"
         aria-hidden="true">
        <div class="modal-dialog ">
            <div class="modal-content">
                <div class='modal-body'>
                    <div class='row'>
                        <div class='col-sm-12 form-box'>
                            <div class='form-top'>
                                <div class='form-top-left'>
                                    <h2>更改密码</h2>
                                    <h5 id="updatePassword-alert"></h5>
                                </div>
                                <div class='form-top-right'>
                                    <i class='fa fa-user'></i>
                                </div>
                            </div>
                            <div class='form-bottom'>
                                <form role='form' action='' method='post' class='form-updatePassword'
                                      id='form-updatePassword'>
                                    <div class='form-group'>
                                        <div class="row">
                                            <label class="col-xs-3" for='updatePassword-old-password'>原密码：</label>
                                            <div class="col-xs-9">
                                                <input type='password' name='updatePassword-old-password'
                                                       placeholder='Old Password...'
                                                       class='form-control' id='updatePassword-old-password'>
                                            </div>
                                        </div>
                                    </div>
                                    <div class='form-group'>
                                        <div class="row">
                                            <label class="col-xs-3" for='updatePassword-new-password'>新密码：</label>
                                            <div class="col-xs-9">
                                                <input type='password' name='updatePassword-new-password'
                                                       placeholder='New Password...'
                                                       class='form-control' id='updatePassword-new-password'>
                                            </div>
                                        </div>
                                    </div>
                                    <div class='form-group'>
                                        <div class="row">
                                            <label class="col-xs-3"
                                                   for='updatePassword-repeat-new-password'>重复新密码：</label>
                                            <div class="col-xs-9">
                                                <input type='password' name='updatePassword-repeat-new-password'
                                                       placeholder='Repeat New Password...'
                                                       class='form-control' id='updatePassword-repeat-new-password'>
                                            </div>
                                        </div>
                                    </div>
                                    <div class='form-group'>
                                        <div class='row'>
                                            <div class='col-sm-1'></div>
                                            <div class='col-sm-4'>
                                                <button type='reset' id='updatePassword-reset' class='btn btn-default'>
                                                    重置
                                                </button>
                                            </div>
                                            <div class='col-sm-2'></div>
                                            <div class='col-sm-4'>
                                                <button type='button' id='updatePassword-submit'
                                                        class='btn btn-primary'>更新
                                                </button>
                                            </div>
                                            <div class='col-sm-1'>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</div>

<script type="text/javascript">

    $(function () {
        $('#logout').click(function () {
            $.post('loginPost.php?order=2', {}, function (data) {
                var result = JSON.parse(data);
                if (result.status == 1) {
                    window.location.href = "index.php";
                } else {
                    $('#errorAlert-content').html(reault.message);
                    $('#errorAlert').modal('show');
                }
            });
        });
    });

    $('#updatePassword-submit').click(function () {
        var oldPwd = $('#updatePassword-old-password').val();
        var newPwd = $('#updatePassword-new-password').val();
        var reNewPwd = $('#updatePassword-repeat-new-password').val();
        if (oldPwd == '' || newPwd == '' || reNewPwd == '') {
            clearAll();
            $('#UpdatePassModel').modal('hide');
            $('#errorAlert-content').html('信息不全！');
            $('#errorAlert').modal('show');
            return;
        }
        if (newPwd != reNewPwd) {
            clearAll();
            $('#UpdatePassModel').modal('hide');
            $('#errorAlert-content').html('两次密码不一致！');
            $('#errorAlert').modal('show');
            return;
        }
        $.post('modifyPasswordPost.php', {
            oldPwd: $.md5(oldPwd),
            newPwd: $.md5(newPwd)
        }, function (data) {
            var result = JSON.parse(data);
            if (result.status == 1) {
                clearAll();
                $('#UpdatePassModel').modal('hide');
                $('#errorAlert-content').html('更新成功！');
                $('#errorAlert').modal('show');
                $('#ErrorAlert').modal('show');
            } else {
                clearAll();
                $('#UpdatePassModel').modal('hide');
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
</script>
