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

        </div>

        <div class="clearfix"></div>

        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_title">
                        <h2>查看/修改监考</h2>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                        <table id="editInvigilation-examList">
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="teacherAdd-modal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width: 900px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h3 class="modal-title" id="myModalLabel">
                    <span id="teacherAdd-modal-Name">teacherAdd-modal-Name</span>
                    <input class="form-control" type="hidden"
                           id="teacherAdd-modal-markedUser"
                           value="">
                    <input class="form-control" type="hidden"
                           id="teacherAdd-modal-markedUserItem" value="">
                </h3>
            </div>
            <div class="modal-body">
                <div class="row row-margin-bottom"
                     id="teacherAdd-modal-itemTemptListTable">
                    <div class="col-sm-12">
                        <table id="teacherAdd-modal-itemTemptList">
                        </table>
                    </div>
                </div>
                <div class="ln_solid"></div>
                <div class="row row-margin-bottom">
                    <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                        <button id="teacherAdd-modal-submit" class="btn btn-success col-sm-4" type="button" data-dismiss="modal">提交</button>
                        <button class="btn btn-primary col-sm-4 col-sm-offset-1" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<script>
    var ChooseExamId = 0;
    function chooseTeacher(examId,modal_title) {
        ChooseExamId = examId;
        $('#teacherAdd-modal-itemTemptList').bootstrapTable('refresh', {url: 'post/invigilationTeacherSelectTableList',query: {examId: examId}});
        $('#teacherAdd-modal-Name').html(modal_title);
        $('#teacherAdd-modal').modal('show');
    }

    function examEdit() {
        $('#editInvigilation-examList').bootstrapTable('refresh');
    }
    $(function () {
        $('#editInvigilation-examList').bootstrapTable({
            afterLoad: function () {
                $.fn.editable.defaults.mode = 'popup';
            },
            method: 'get',
            idField: 'id',
            url: 'post/examListPost',
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
                valign: 'middle',
                editable: true,
                editableUrl: "post/editInvigilation"
            }, {
                field: 'number',
                title: '监考人数',
                align: 'center',
                valign: 'middle',
                editable: true,
                editableUrl: "post/editInvigilation"
            }, {
                field: 'date',
                title: '日期',
                align: 'center',
                valign: 'middle',
                editable: true,
                editableUrl: "post/editInvigilation"
            }, {
                field: 'startTime',
                title: '开始时间',
                align: 'center',
                valign: 'middle',
                editable: true,
                editableUrl: "post/editInvigilation"
            }, {
                field: 'endTime',
                title: '结束时间',
                align: 'center',
                valign: 'middle',
                editable: true,
                editableUrl: "post/editInvigilation"
            }, {
                field: 'room',
                title: '考试地点',
                align: 'center',
                valign: 'middle',
                editable: true,
                editableUrl: "post/editInvigilation"
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
            }, {
                field: 'status',
                title: '状态',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'operation',
                title: '操作',
                align: 'center',
                valign: 'middle'
            }],
            pagination: true,
            sidePagination: 'server',
            pageSize: 20
        });
        $('#teacherAdd-modal-itemTemptList').bootstrapTable({
            afterLoad: function() {
                $.fn.editable.defaults.mode = 'popup';
            },
            method: 'get',
            idField: 'id',
            classes: 'table table-striped table-condensed table-hover',
            columns: [{
                field: 'state',
                title: '选中',
                checkbox: true,
                align: 'center',
                valign: 'middle'
            }, {
                field: 'id',
                title: 'ID',
                align: 'center',
                valign: 'middle',
                visible: false
            }, {
                field: 'userName',
                title: '姓名',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'title',
                title: '职称',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'phone',
                title: '电话',
                align: 'center',
                valign: 'middle'
            },{
                field: 'introduction',
                title: '简介',
                align: 'center',
                valign: 'middle',
            },{
                field: 'futureExamCount',
                title: '已有监考',
                align: 'center',
                valign: 'middle',
                sortable: true
            }],
            pagination: true,
            sidePagination: 'client',
            pageSize: 20
        });
        $('#teacherAdd-modal-submit').click(function() {
            $.post('post/modifyExamTeachers', {
                examId: ChooseExamId,
                teachers: $('#teacherAdd-modal-itemTemptList').bootstrapTable('getAllSelections').length == 0 ? '' : JSON.stringify($('#teacherAdd-modal-itemTemptList').bootstrapTable('getAllSelections')),
            }, function (result) {
                if (result.status == 1) {
                    examEdit();
                } else {
                    $('#errorAlert-content').html(result.message);
                    $('#errorAlert').modal('show');
                }
                $('#teacherAdd-modal').modal("hide");
            });
        })
    });

    function examEdit_delete(examId) {
        var yes = function () {
            $.post('post/examDelete', {
                examId: examId
            }, function (data) {
                if (data.status == 0) {
                    $('#errorAlert-content').html("删除失败：" + data.message);
                    $('#errorAlert').modal('show');
                }
                else examEdit();
            });
        };
        $('#confirmBox-yes').unbind();
        $('#confirmBox-no').unbind();
        $('#confirmBox-yes').click(yes);
        $('#confirmBox-title').html("确认删除？");
        $('#confirmBox-content').html("是否删除该监考？");
        $('#confirmBox').modal('show');
    }

</script>
<jsp:include page="footer.jsp"/>