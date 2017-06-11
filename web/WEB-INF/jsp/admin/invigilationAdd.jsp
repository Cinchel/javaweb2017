<%--
  Created by IntelliJ IDEA.
  User: mzzhang
  Date: 2017/5/29
  Time: 下午11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="head.jsp"/>
<link rel='stylesheet' type='text/css' href='/public/css/bootstrap-datetimepicker.min.css'/>
<script src='/public/js/bootstrap-datetimepicker.min.js' type='text/javascript'></script>
<div class="right_col" role="main">
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3>监考管理</h3>
            </div>
        </div>

        <div class="clearfix"></div>

        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_title">
                        <h2>添加监考</h2>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                        <form class="form-horizontal form-label-left">
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">监考名称
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input type="text" id="name" required="required" class="form-control col-md-7 col-xs-12">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="number">监考人数</label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input class="form-control" type="text" id="number" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="date">监考日期</label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input class="form-control" type="text" id="date" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="examTime">监考时间
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <select class="form-control" type="text" id="examTime">
                                        <option value="c1">第一、二节</option>
                                        <option value="c2">第三、四节</option>
                                        <option value="c3">第五、六节</option>
                                        <option value="c4">第七、八节</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="startTime">开始时间
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input id="startTime" class="form-control col-md-7 col-xs-12" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="endTime">结束时间
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input id="endTime" class="form-control col-md-7 col-xs-12" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="Roomname">地点
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input id="Roomname" class="form-control col-md-7 col-xs-12" type="text">
                                </div>
                            </div>
                            <div class="ln_solid"></div>
                            <div class="form-group">
                                <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                    <button id="examAdd" class="btn btn-success col-sm-4" type="button">提交</button>
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
    function refresh() {
        var date = $('#date').val();
        if(date.length>1){
            $('#startTime').datetimepicker('setStartDate', date+' 7:00:00');
            $('#startTime').datetimepicker('setEndDate', date+' 23:55:00');
            $('#endTime').datetimepicker('setStartDate', date+' 7:00:00');
            $('#endTime').datetimepicker('setEndDate', date+' 23:55:00');
        }
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
    }
    $(function(){
        $('#date').datetimepicker({
            format: "yyyy-mm-dd",
            weekStart: 1,
            autoclose: true,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });

        $("#startTime").datetimepicker({
            format: "hh:ii:00",
            weekStart: 1,
            autoclose: true,
            todayHighlight: 1,
            startView: 0,
            minView: 0,
            maxView: 1,
            forceParse: 0
        });
        $("#endTime").datetimepicker({
            format: "hh:ii:00",
            weekStart: 1,
            autoclose: true,
            todayHighlight: 1,
            startView: 0,
            minView: 0,
            forceParse: 0
        });
        $('#examAdd').click(function () {
            if($('#date').val().length<1) {
                $('#errorAlert-content').html("请选择日期！");
                $('#errorAlert').modal('show');
                return;
            }
            else if($('#name').val().length<1) {
                $('#errorAlert-content').html("请输入考试名称！");
                $('#errorAlert').modal('show');
                return;
            }
            else if($('#Roomname').val().length<1) {
                $('#errorAlert-content').html("请输入考试地点！");
                $('#errorAlert').modal('show');
                return;
            }
            $.post('post/addInvigilation', {
                name: $('#name').val(),
                date: $('#date').val(),
                room: $('#Roomname').val(),
                startTime: $('#startTime').val(),
                endTime: $('#endTime').val(),
                number:$('#number').val()
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

        refresh();
        $('#examTime').change(function () {
            refresh();
        });
        $('#date').change(function () {
            refresh();
        });
    });
</script>
<jsp:include page="footer.jsp"/>