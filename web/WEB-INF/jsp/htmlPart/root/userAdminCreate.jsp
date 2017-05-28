<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="userAdminCreate"  >
    <div class="row row-margin-bottom">
        <label for="userAdminCreate-name" class="col-sm-3 text-right">用户姓名：</label>
        <div class="col-sm-9">
            <input class="form-control" type="text" id="userAdminCreate-name">
        </div>
    </div>


    <div class="row row-margin-bottom" id="userAdminCreate-createSubmit">
        <div class="col-sm-3 col-sm-offset-4">
            <input class="form-control" type="button" id="userAdminCreate-create" value="添加">
        </div>
        <div class="col-sm-3">
            <input class="form-control" type="button" id="userAdminCreate-createReset" value="重置">
        </div>
    </div>
    <div class="row row-margin-bottom" id="userAdminCreate-updateSubmit">
        <div class="col-sm-2">
            <input class="form-control" type="hidden" id="userAdminCreate-userID" value="">
        </div>
        <div class="col-sm-3">
            <input class="form-control" type="button" id="userAdminCreate-update" value="保存更改">
        </div>
        <div class="col-sm-3">
            <input class="form-control" type="button" id="userAdminCreate-updateReset"
                   value="取消更改">
        </div>
        <div class="col-sm-3">
            <input class="form-control" type="button" id="userAdminCreate-updateCancel"
                   value="返回列表">
        </div>
    </div>
</div>
<script>
    $('#userAdminCreate-create').click(function () {
        $.post('admin/root/adminuserCreatePost.php', {
            name: $('#userAdminCreate-name').val()
        }, function (data) {
            var result = JSON.parse(data);
            if (result.status == 1) {
                $('#userAdminCreate-createReset').click();
                var node = $('#adminTreeView').treeview('search', ['编辑部门', {
                    ignoreCase: true,     // case insensitive
                    exactMatch: false,    // like or equals
                    revealResults: false  // reveal matching nodes
                }]);
                $('#adminTreeView').treeview('clearSearch');
                $('#adminTreeView').treeview('selectNode', node);
            } else {
                $('#errorAlert-content').html(result.message);
                $('#errorAlert').modal('show');
            }
        });
    });
    $('#userAdminCreate-createReset').click(function () {
        $('#userAdminCreate-name').val('');
    });
</script>