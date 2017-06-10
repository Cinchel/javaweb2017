<%--
  Created by IntelliJ IDEA.
  User: mzzhang
  Date: 2017/5/29
  Time: 下午11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"/>

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

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $('#userAdminCreate-create').click(function () {
        $.post('adminPost/addUser', {
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