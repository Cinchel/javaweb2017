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
                <h3>系统设置</h3>
            </div>
        </div>

        <div class="clearfix"></div>

        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_title">
                        <h2>设置学期基点</h2>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                        <form class="form-horizontal form-label-left">
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="term-select">选择学期
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <select class="form-control" id="term-select">
                                        ${term_select}
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="term-base">选择学期基点
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input class="form-control" type="text" readonly id="term-base">
                                </div>
                            </div>
                            <div class="ln_solid"></div>
                            <div class="form-group">
                                <div class="col-md-6 col-sm-6 col-xs-12 col-sm-offset-3">
                                    <button id="save" class="btn btn-success col-sm-12" type="button">保存</button>
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
    str = $("#term-select").val();
    obj = str.split(" - ");
    $('#term-base').datetimepicker('setStartDate', obj[0]+'-01-01');
    $('#term-base').datetimepicker('setEndDate', obj[1]+'-12-31');
    $.post('post/getTermConfig', {
        term: str
    }, function (result) {
        $('#term-base').val(result);
    });
}
$(function() {
    $("#save").click(function(){
        $.post('post/setTermConfig', {
            term: $("#term-select").val(),
            baseDate: $("#term-base").val()
        }, function (result) {
            $('#errorAlert-content').html(result.message);
            $('#errorAlert').modal('show');
        })
    });
    $("#term-base").datetimepicker({
        format: "yyyy-mm-dd",
        weekStart: 1,
        autoclose: true,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        initialDate: new Date()
    });
    $('#term-base').datetimepicker('setDaysOfWeekDisabled', [0,2,3,4,5,6]);

    $('#term-select').change(function () {
        refresh();
    });
    refresh();
});

</script>
<jsp:include page="footer.jsp"/>