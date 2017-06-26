<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/30
  Time: 23:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="head.jsp"/>
<link rel='stylesheet' type='text/css' href='/public/css/bootstrap-datetimepicker.min.css'/>
<script src='/public/js/bootstrap-datetimepicker.min.js' type='text/javascript'></script>
<link rel='stylesheet' type='text/css' href='/public/css/fileinput.min.css'/>
<script src='/public/js/fileinput.min.js' type='text/javascript'></script>
<style>
    #replyMessage{
        border-color:#e5e5e5;
        width:100%;
        max-width:100%;
        height: 40em;
    }
</style>

<div class="right_col" role="main">
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3>我的任务</h3>
            </div>

        </div>

        <div class="clearfix"></div>

        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_title">
                        <h2>查看/回复任务</h2>
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


<div class="modal fade" id="teacherTask" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width: 900px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h3 class="modal-title" id="myModalLabel">
                    <span id="teacherAdd-modal-Name">回复</span>
                </h3>
            </div>
            <div class="modal-body">
                <div id="teacherTaskContent" class="row row-margin-bottom">
                    <textarea name="" id="replyMessage"></textarea>
                </div>
                <div class="ln_solid"></div>
                <div class="row row-margin-bottom">
                    <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                        <button id="teacherTaskSubmit" class="btn btn-success col-sm-4" type="button" data-dismiss="modal">提交</button>
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
                editable: false,
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
                editable: false,
                editableUrl: "post/editTask"
            }, {
                field: 'description',
                title: '任务描述',
                align: 'center',
                valign: 'middle',
                editable: false,
                editableUrl: "post/editTask"
            }, {
                field: 'teacherOperation',
                title: '操作',
                align: 'center',
                valign: 'middle'
            }],
            pagination: true,
            sidePagination: 'server',
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

    function reply(taskId){
        $('#teacherAdd-modal-Name').html('回复');
        $('#teacherTaskContent').html('<textarea name="" id="replyMessage"></textarea>');
        $('#teacherTask').modal('toggle');
        var yes = function(){
            var replyMessage = $('#replyMessage').val();
            $.post('post/teacherReply', {
                taskId: taskId,
                replyMessage: replyMessage
            }, function(data) {
                if (data.status == 1){
                    $('#errorAlert-content').html("回复成功");
                    $('#errorAlert').modal('show');
                }
                else {
                    $('#errorAlert-content').html("回复失败：" + data.message);
                    $('#errorAlert').modal('show');
                }
            });
        }
        $('#teacherTaskSubmit').unbind();
        $('#teacherTaskSubmit').click(yes);
    }

    function upload(taskId) {
        var teacherTaskContentHTML = '<div class="form-group" id="fileTask-div">' +
            '<label class="control-label col-md-3 col-sm-3 col-xs-12" for="file">上传文件' +
            '</label>' +
            '<div class="col-md-6 col-sm-6 col-xs-12" >' +
            '<input id="file" name="file" multiple type="file">' +
            '</div></div>'
        ;
        taskId = parseInt(taskId);
        $('#teacherAdd-modal-Name').html('上传');
        $('#teacherTaskContent').html(teacherTaskContentHTML);

        $('#file').fileinput({
            uploadUrl: 'post/teacherUpload', //上传
            allowedFileExtensions : ['doc','docx','ppt','pptx','pdf','txt'],//接收的文件后缀
            showUpload: false, //是否显示上传按钮
            showCancel: false,
            showCaption: true,//是否显示标题
            showPreview: true,
            showRemove: false,
            maxFileCount: 1,
            fileActionSettings:{
                showUpload:false
            },
            autoReplace: true,
            browseClass: "btn btn-primary", //按钮样式
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        }).on('filebatchpreupload', function (event, data, previewId, index) {
            data.extra.taskId = taskId;
        }).on('fileuploaded', function (event, data, previewId, index) {
            $('#file').fileinput('clear').fileinput('enable');
            $('#errorAlert-content').html(data.response.message);
            $('#errorAlert').modal('show');
        });

        $('#teacherTaskSubmit').click(function () {
            $('#file').fileinput('upload');
        })
        $('#teacherTask').modal('toggle');

        /*$.post('post/teacherUpload', {
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
        });*/
    }

</script>
<jsp:include page="footer.jsp"/>
