<%--
  Created by IntelliJ IDEA.
  User: Libby
  Date: 2017/6/14
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="head.jsp"/>



<div class="right_col" role="main">
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3>监考信息</h3>
            </div>
        </div>

        <div class="clearfix"></div>

        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_title">
                        <h2>我的监考</h2>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                        <table id="examList">
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script>
    function examList() {
        $('#examList').bootstrapTable('refresh');
    }

    $($('#examList').bootstrapTable({
            afterLoad: function () {
                $.fn.editable.defaults.mode = 'popup';
            },
            method: 'get',
            idField: 'id',
            url: 'post/myExamListPost',
            classes: 'table table-striped table-condensed table-hover',
            columns: [{
                field: 'id',
                title: 'ID',
                align: 'center',
                valign: 'middle',
                visible: false
            }, {
                field: 'name',
                title: '考试名称',
                align: 'center',
                valign: 'middle'

            }, {
                field: 'number',
                title: '监考人数',
                align: 'center',
                valign: 'middle'

            }, {
                field: 'date',
                title: '日期',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'startTime',
                title: '开始时间',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'endTime',
                title: '结束时间',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'room',
                title: '考试地点',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'createAdmin',
                title: '创建者',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'teachers',
                title: '监考老师',
                align: 'center',
                valign: 'middle'
            }],
            pagination: true,
            sidePagination: 'server',
            pageSize: 20
        }));


</script>
<jsp:include page="footer.jsp"/>
