<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/30
  Time: 23:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="head.jsp"/>


<div class="right_col" role="main">
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3>任务管理</h3>
            </div>

        </div>

        <div class="clearfix"></div>

        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_title">
                        <h2>查看/修改任务</h2>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                        <table id="editTask-taskList">
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
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<script>
    var TaskId = 0;
    function taskEdit() {
        $('#editTask-taskList').bootstrapTable('refresh');
    }
    $(function () {
        $('#editTask-taskList').bootstrapTable({
            afterLoad: function () {
                $.fn.editable.defaults.mode = 'popup';
            },
            method: 'get',
            idField: 'id',
            url: 'post/taskListPost',
            classes: 'table table-striped table-condensed table-hover',
            columns: [{
                field: 'id',
                title: 'ID',
                align: 'center',
                valign: 'middle',
                visible: false
            }, {
                field: 'taskName',
                title: '任务名称',
                align: 'center',
                valign: 'middle',
                editable: true,
                editableUrl: "post/editTask"
            }, {
                field: 'taskType',
                title: '任务类型',
                align: 'center',
                valign: 'middle',
                editable: false,
                editableUrl: "post/editTask",
            }, {
                field: 'deadline',
                title: '截止日期',
                align: 'center',
                valign: 'middle',
                editable: true,
                editableUrl: "post/editTask"
            }, {
                field: 'description',
                title: '任务描述',
                align: 'center',
                valign: 'middle',
                editable: true,
                editableUrl: "post/editTask"
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
                field: 'id',
                title: 'ID',
                align: 'center',
                valign: 'middle',
                visible: false
            }, {
                field: 'userName',
                title: '教师姓名',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'replyTime',
                title: '回复时间',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'type',
                title: '任务类型',
                align: 'center',
                valign: 'middle'
            },  {
                field: 'replyContent',
                title: '回复内容',
                align: 'center',
                valign: 'middle'
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
                    $('#AlertP').html(result.message);
                    $('#ErrorAlert').modal('show');
                }
                $('#teacherAdd-modal').modal("hide");
            });
        })
    });

    function showReplyMessage(taskId) {
        TaskId = taskId;
        $('#teacherAdd-modal-itemTemptList').bootstrapTable('refresh', {url: 'post/showReplyMessageStatus',query: {taskId: taskId}});
        $('#teacherAdd-modal-Name').html("查看任务完成情况");
        $('#teacherAdd-modal').modal('show');
    }

    function downloadReplyFile(taskQueueId) {
        $.get('post/downloadReplyFile', {
            tasksQueueId: taskQueueId
        }, function (data) {
            if (data.status == 0) {
                $('#errorAlert-content').html("下载失败：" + data.message);
                $('#errorAlert').modal('show');
            }
            else {
                var location = window.location,
                    url =location.protocol + '//' + location.host +
                        '/admin/post/downloadReplyFile?tasksQueueId=' + taskQueueId;
                window.open(url);
                taskEdit();
            }
        });
    }

    function downloadTaskFile(taskId) {
        $.get('post/downloadTaskFile', {
            taskId: taskId
        }, function (data) {
            if (data.status == 0) {
                $('#errorAlert-content').html("下载失败：" + data.message);
                $('#errorAlert').modal('show');
            }
            else {
                var location = window.location,
                    url =location.protocol + '//' + location.host +
                        '/admin/post/downloadTaskFile?taskId=' + taskId;
                window.open(url);
                taskEdit();
            }
        });
    }

    function taskEdit_delete(taskId) {
        var yes = function () {
            $.post('post/taskDelete', {
                taskId: taskId
            }, function (data) {
                if (data.status == 0) {
                    $('#errorAlert-content').html("删除失败：" + data.message);
                    $('#errorAlert').modal('show');
                }
                else taskEdit();
            });
        };
        $('#confirmBox-yes').unbind();
        $('#confirmBox-no').unbind();
        $('#confirmBox-yes').click(yes);
        $('#confirmBox-title').html("确认删除？");
        $('#confirmBox-content').html("是否删除该任务？");
        $('#confirmBox').modal('show');
    }

</script>
<jsp:include page="footer.jsp"/>
