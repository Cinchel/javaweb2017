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
                <h3>监考管理</h3>
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
                        <h2>添加监考信息</h2>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">n
                        <div class="row row-margin-bottom">
                            <label for="name" class="col-sm-2 text-right">监考名称：</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="name">
                            </div>
                        </div>

                        <div class="row row-margin-bottom">
                            <label for="startTime" class="col-sm-2 text-right">开始时间：</label>
                            <div class="col-sm-9">

                             <input class="form-control" type="datetime" id="startTime"/>
                            </div>
                        </div>

                        <div class="row row-margin-bottom">
                            <label for="endTime" class="col-sm-2 text-right">结束时间：</label>
                            <<div class="col-sm-9">
                            <input class="form-control" type="datetime" id="endTime"/>
                        </div>
                        </div>


                        <div class="row row-margin-bottom">
                            <label for="Roomname" class="col-sm-2 text-right">地点：</label>
                            <div class="col-sm-9">
                                <textarea class="form-control" id="Roomname"></textarea>
                            </div>
                        </div>
                        <div class="row row-margin-bottom" id="userAdminCreate-createSubmit">
                            <div class="col-sm-3 col-sm-offset-3">
                                <input class="form-control btn-primary" type="button" id="examAdd" value="添加">
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $('#examAdd').click(function () {
        $.post('post/addExam', {
            name: $('#name').val(),
            startTime: $('#startTime').val(),
            endTime: $('#endTime').val(),
            createAdmin: 14
            /*Roomname:$('#Roomname').val()*/
        }, function (result) {
            if (result.status == 1) {
                $('#errorAlert-content').html(result.message);
                $('#errorAlert').modal('show');
                $('#name').val(),
                    $('#date').val(),
                    $('#startTime').val(),
                    $('#endTime').val(),
                ${user.userName}
                /* $('#Roomname').val()*/
            } else {
                $('#errorAlert-content').html(result.message);
                $('#errorAlert').modal('show');
            }
        });
    });
</script>
<jsp:include page="footer.jsp"/>