<%--
  Created by IntelliJ IDEA.
  User: mzzhang
  Date: 2017/5/29
  Time: 下午11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"/>
<link rel='stylesheet' type='text/css' href='/public/css/bootstrap-datetimepicker.min.css'/>
<script src='/public/js/bootstrap-datetimepicker.min.js' type='text/javascript'></script>
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
                            <label for="name" class="col-sm-2 text-right">监考名称：</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="name">
                            </div>
                        </div>
                        <div class="row row-margin-bottom">
                            <label for="date" class="col-sm-2 text-right">监考日期：</label>
                            <div class="col-sm-9" >
                                <input class="form-control" type="text"  id="date">
                            </div>
                        </div>

                        <div class="row row-margin-bottom">
                            <label for="examTime" class="col-sm-2 text-right">监考时间：</label>
                            <div class="col-sm-9">
                                <select class="form-control" type="text" id="examTime">
                                    <option value="c1">第一、二节</option>
                                    <option value="c2">第三、四节</option>
                                    <option value="c3">第五、六节</option>
                                    <option value="c4">第七、八节</option>
                                </select>
                            </div>
                        </div>

                        <div class="row row-margin-bottom">
                            <label for="startTime" class="col-sm-2 text-right">开始时间：</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="startTime">
                            </div>
                        </div>

                        <div class="row row-margin-bottom">
                            <label for="endTime" class="col-sm-2 text-right">结束时间：</label>
                            <div class="col-sm-9">
                            <input class="form-control" type="text" id="endTime">
                            </div>
                        </div>


                        <div class="row row-margin-bottom">
                            <label for="Roomname" class="col-sm-2 text-right">地点：</label>
                            <div class="col-sm-9">
                                <input class="form-control" id="Roomname">
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
    $(function(){
        $('#examAdd').click(function () {
            $.post('adminPost/addInvigilation', {
                name: $('#name').val(),
                date: $('#date').val(),
                startTime: $('#startTime').val(),
                endTime: $('#endTime').val(),
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
        $('#examTime').change(function () {
            if($('#examTime').val()=='c1') {
                $('#startTime').val('08:00:00');
                $('#endTime').val('10:00:00');
            } else if($('#examTime').val()=='c2') {
                $('#startTime').val('10:10:00');
                $('#endTime').val('12:10:00');
            } else if($('#examTime').val()=='c3') {
                $('#startTime').val('13:30:00');
                $('#endTime').val('15:30:00');
            } else if($('#examTime').val()=='c4') {
                $('#startTime').val('15:40:00');
                $('#endTime').val('17:40:00');
            }
        });
    });
</script>
<%--%--日期&ndash;%&gt;
<script src="../public/calendar/js/jquery-1.11.0.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../public/js/dcalendar.picker.js"></script>
<script type="text/javascript">
    $('#date').dcalendarpicker({
        format:'yyyy-mm-dd'
    });
</script>--%>

<script>

    $(function() {

        $("#date").datetimepicker({
            format: "yyyy-mm-dd",
            weekStart: 1,
            autoclose: true,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0,
            showMeridian: 1,
            initialDate: new Date()
        });

        $("#startTime").datetimepicker({
            format: "hh:ii:ss",
            weekStart: 1,
            autoclose: true,
            todayHighlight: 1,
            startView: 0,
            minView: 0,
            forceParse: 0,
            showMeridian: 1,
            initialDate: new Date()
        });
        $("#endTime").datetimepicker({
            format: "hh:ii:ss",
            weekStart: 1,
            autoclose: true,
            todayHighlight: 1,
            startView: 0,
            minView: 0,
            forceParse: 0,
            showMeridian: 1,
            initialDate: new Date()
        });
        $('#term-base').datetimepicker('setDaysOfWeekDisabled', [0,2,3,4,5,6]);

    });

</script>
<jsp:include page="footer.jsp"/>