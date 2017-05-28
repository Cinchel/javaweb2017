<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="common/head.jsp"/>

<script language="JavaScript">
    var adminTreeViewData;
    function allHide() {
        $('#userAdminCreate').hide();

    }


    $(function () {
        allHide();
        adminTreeViewData =
            [
                {
                    "text": "用户管理",
                    "name": "userAdmin",
                    "selectable": false,
                    "nodes": [
                        {
                            "text": "添加用户",
                            "name": "userAdminCreate"
                        },
                        {
                            "text": "编辑用户",
                            "name": "userAdminEdit"
                        },
                        {
                            "text": "权限管理",
                            "name": "userAdminRole"
                        }
                    ]
                },
                {
                    "text": "学期配置",
                    "name": "weekAdmin",
                    "selectable": false,
                    "nodes": [{
                        "text": "你好",
                        "name": "weekAdminCreate"
                    }, {
                        "text": "你好",
                        "name": "weekAdminUpdate"
                    }
                    ]
                }
            ];
        $('#adminTreeView').treeview({
            data: adminTreeViewData,
            levels: 2,
            onNodeSelected: function (event, node) {
                allHide();
                $('#adminTitle').html(node.text);
                var cos = node.name;
                $('#' + cos).show();
                if (node.name == "userAdminCreate") {
                    $('#deptAdminCreate-createReset').click();
                    $('#deptAdminCreate-type').removeAttr("disabled");
                    $('#deptAdminCreate-updateSubmit').hide();
                    $('#deptAdminCreate-createSubmit').show();
                }
            },
            onNodeUnselected: function (event, node) {
            }
        });


    });
</script>
<div id="adminContainer" class="container">
    <div class="row">
        <div class="col-sm-3">
            <div id="adminTreeView" class="row">
            </div>
        </div>
        <div class="col-sm-9">
            <div id="" class="well well-sm text-center"><label style="font-size: 24px" id="adminTitle">管理界面</label>
            </div>

            <div id="userAdminCreate" style="display: none">
                <jsp:include page="htmlPart/root/userAdminCreate.jsp"/>
            </div>


        </div>
    </div>

</div>






