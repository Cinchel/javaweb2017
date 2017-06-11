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


<script>
    function examEdit(){
        $('#editInvigilation-examList').bootstrapTable('refresh');
    }
    $($('#editInvigilation-examList').bootstrapTable({
        afterLoad: function() {
            $.fn.editable.defaults.mode = 'popup';
        },
        method: 'get',
        idField: 'id',
        url:'post/examListPost',
        classes: 'table table-striped table-condensed table-hover',
        method: 'get',
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
            editable:true,
            editableUrl: "post/editInvigilation"
        }, {
            field: 'date',
            title: '日期',
            align: 'center',
            valign: 'middle',
            editable:true,
            editableUrl: "post/editInvigilation"
        }, {
            field: 'startTime',
            title: '开始时间',
            align: 'center',
            valign: 'middle',
            editable:true,
            editableUrl: "post/editInvigilation"
        },{
            field: 'endTime',
            title: '结束时间',
            align: 'center',
            valign: 'middle',
            editable:true,
            editableUrl: "post/editInvigilation"
        },{
            field: 'room',
            title: '考试地点',
            align: 'center',
            valign: 'middle',
            editable:true,
            editableUrl: "post/editInvigilation"
        },  {
            field: 'createAdmin',
            title: '创建者',
            align: 'center',
            valign: 'middle'
        }, {
            field: 'delete',
            title: '删除监考',
            align: 'center',
            valign: 'middle',
        }],
        pagination: true,
        sidePagination: 'server',
        pageSize: 20
    }));

    function examEdit_delete(examId) {
        var yes = function() {
            $.post('post/examDelete', {
                examId : examId
            }, function (data) {
                if(data.status == 0) {
                    $('#errorAlert-content').html("删除失败："+data.message);
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