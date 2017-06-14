<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="head.jsp"/>
<link rel='stylesheet' type='text/css' href='/public/css/bootstrap-datetimepicker.min.css'/>
<script src='/public/js/bootstrap-datetimepicker.min.js' type='text/javascript'></script>
<link rel='stylesheet' type='text/css' href='/public/css/fileinput.min.css'/>
<script src='/public/js/fileinput.min.js' type='text/javascript'></script>
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
                        <h2>添加任务</h2>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                        <form class="form-horizontal form-label-left">
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="taskName">任务名称
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input type="text" id="taskName" required="required" class="form-control col-md-7 col-xs-12">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="taskType">任务类型</label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <select id="taskType" class="form-control col-md-7 col-xs-12">
                                        <option value="replyTask">回复类任务</option>
                                        <option value="fileTask">文件类任务</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="description">任务描述
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <textarea id="description" class="form-control col-md-7 col-xs-12"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="deadline"  >截止时间
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <input id="deadline" class="form-control col-md-7 col-xs-12" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="replyMessage">回复内容
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12" >
                                    <textarea id="replyMessage" class="form-control col-md-7 col-xs-12"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="file">上传文件
                                </label>
                                <div class="col-md-6 col-sm-6 col-xs-12" >
                                    <input id="file" name="problemEditProblem-uploadData"
                                           multiple type="file">
                                </div>

                            </div>
                            <div class="ln_solid"></div>
                            <div class="form-group">
                                <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                    <button id="taskAdd" class="btn btn-success col-sm-4" type="button">提交</button>
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
    $(function() {
        $('#file').fileinput({
            uploadUrl: 'post/admin/problem/adminProblemUploadFilePost.php', //上传的地址
            allowedFileExtensions : ['in','out'],//接收的文件后缀
            showUpload: true, //是否显示上传按钮
            showCancel: false,
            showCaption: true,//是否显示标题
            showPreview: true,
            showRemove: false,
            browseClass: "btn btn-primary", //按钮样式
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        }).on('filebatchpreupload', function (event, data, previewId, index) {
            data.extra.problem = $('#problemEditProblem-uploadDataProblem').val();
            data.extra.type = "data";
        }).on('fileuploaded', function (event, data, previewId, index) {
            $('#problemEditProblem-uploadData').fileinput('clear').fileinput('enable');
            $('#problemEditProblem-dataList').bootstrapTable('refresh');
        });

        $("#deadline").datetimepicker({
            format: "yyyy-mm-dd hh:ii:00",
            weekStart: 1,
            autoclose: true,
            todayHighlight: 0,
            startView: 2,
            minView: 0,
            maxView: 4,
            forceParse: 0
        });

        $('#taskAdd').click(function () {
            if($('#deadline').val().length<1) {
                $('#errorAlert-content').html("请选择截止时间！");
                $('#errorAlert').modal('show');
                return;
            }
            else if($('#taskName').val().length<1) {
                $('#errorAlert-content').html("请输入任务名称！");
                $('#errorAlert').modal('show');
                return;
            }
            else if($('#description').val().length<1) {
                $('#errorAlert-content').html("请输入任务描述！");
                $('#errorAlert').modal('show');
                return;
            }
            $.post('post/addTask', {
                taskName: $('#taskName').val(),
                taskType: $('#taskType').val(),
                deadline: $('#deadline').val(),
                description: $('#description').val(),
            }, function (result) {
                if (result.status == 1) {
                    $('#errorAlert-content').html(result.message);
                    $('#errorAlert').modal('show');
                } else {
                    $('#errorAlert-content').html(result.message);
                    $('#errorAlert').modal('show');
                }
            });
            $.post('post/fileupload2', {
                file:$('#file').val()
            }, function (result) {
                if (result.status == 1) {
                    $('#errorAlert-content').html(result.message);
                    $('#errorAlert').modal('show');
                    $('#file').val('')
                } else {
                    $('#errorAlert-content').html(result.message);
                    $('#errorAlert').modal('show');
                    $('#file').val('')
                }
            });
        });
    });
</script>
<jsp:include page="footer.jsp"/>