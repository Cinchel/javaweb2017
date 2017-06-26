<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<head>
	<meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1'/>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" >
	<title>软件工程专业工作管理系统</title>
	<link rel='stylesheet' type='text/css' href='public/css/bootstrap.min.css'/>
	<link rel='stylesheet' type='text/css' href='public/css/font-awesome.min.css'/>
	<link rel='stylesheet' type='text/css' href='public/css/buttons.css'/>
	<link rel='stylesheet' type='text/css' href='public/css/square/green.css'/>
	<link rel='stylesheet' type='text/css' href='public/css/common.css'/>

	<script src='public/js/jquery-1.12.0.min.js' type='text/javascript'></script>
    <script src='public/js/bootstrap.min.js' type='text/javascript'></script>
	<script src='public/js/jquery.md5.js' type='text/javascript'></script>
	<script src='public/js/icheck.min.js' type='text/javascript'></script>
	<script src="public/js/common.js" type="text/javascript"></script>
</head>
<body>
<!--alert bootstrap-->
<div class="modal fade" id="ErrorAlert" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="dialog-close-button">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					Alert!
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<strong><p id="AlertP"></p></strong>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" id="ErrorAlertClose" data-dismiss="modal" >Close
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!--confirm bootstrap-->
<div class="modal fade" id="confirmBox" tabindex="-1" role="dialog"
	 aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="confirmBox-title">
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<strong><p id="confirmBox-content"></p></strong>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" id="confirmBox-yes"
						data-dismiss="modal">确定
				</button>
				<button type="button" class="btn btn-success" id="confirmBox-no"
						data-dismiss="modal">关闭
				</button>
			</div>
		</div>
	</div>
</div><!-- /.modal --><header id='index-logo' class='main-header'>
	<div class='container'>
		<div class='row'>
			<div class='col-sm-12'>
				<img src='public/img/logo.png' class='img-responsive' style="display: inline">
			</div>
		</div>
	</div>
</header>
<div class="container">
	<div class="row row-margin-top row-margin-bottom"></div>
	<div class="row row-margin-top text-center center-block">
		<div class="col-sm-6">
			<img src='public/img/int.jpg' class='img-responsive center-block img-circle' style="display: inline;opacity:0.8;width: 100%; ">
		</div>
		<div class="col-sm-6 row-margin-top">
                <span class="button-wrap">
                    <a id="login" class="button button-pill button-raised button-primary button-jumbo">登录系统</a>
                </span>
		</div>
	</div>
</div>

<div class='modal fade' id='LoginModel' tabindex='-1' role='dialog' aria-labelledby='LoginModel' aria-hidden='true'>
	<div class='modal-dialog'>
		<div class='modal-content'>
			<div class='modal-body'>
				<div class='row'>
					<div class='col-sm-12 form-box'>
						<div class='form-top'>
							<div class='form-top-left'>
								<h3>欢迎登录系统</h3>
								<p>请输入您的用户名和密码:</p>
							</div>
							<div class='form-top-right'>
								<i class='fa fa-key'></i>

							</div>
						</div>
						<div class='form-bottom'>
							<form role='form' method='post' action='' class='login-form'
								  id='form-login'>
								<div class='form-group'>
									<label class='sr-only' for='login-username'>用户名</label>
									<input type='text' name='login-username' placeholder='用户名...'
										   class='form-username form-control' id='login-username'>
								</div>
								<div class='form-group'>
									<label class='sr-only' for='login-password'>密码</label>
									<input type='password' name='login-password' placeholder='密码...'
										   class='form-password form-control' id='login-password'>
								</div>
								<div class='form-group'>
									<div class="row row-margin-top">
										<div class='col-sm-4 col-sm-offset-8 text-center'>
											<button type='button' id='login-submit' class='btn btn-primary'>登录</button>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script type="text/javascript">
    $(function () {
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
            increaseArea: '20%' // optional
        });
        $('#login').click(function(){
            $('#LoginModel').modal('show');
        });
        $('#login-submit').click(function () {
            $('#LoginModel').modal('hide');
            $.post('loginPost', {
                userName: $('#login-username').val(),
                password: $.md5($('#login-password').val()),
                role: $('input:radio[name="login-role"]:checked').val()
            }, function (result) {
                if (result.status == 1) {
                    if(result.message=='class com.entity.Root')
                        window.location.href = "root/welcome";
                    else if(result.message=='class com.entity.Admin')
                        window.location.href = "admin/welcome";
                    else if(result.message=='class com.entity.Teacher')
                        window.location.href = "teacher/welcome";
                    else {
                        $('#login-password').val('');
                        $('#AlertP').html('错误的返回: '+result.message);
                        $('#ErrorAlert').modal('show');
                    }
                }
                else {
                    $('#login-password').val('');
                    $('#AlertP').html(result.message);
                    $('#ErrorAlert').modal('show');
                }
            });
        });


    });
</script>
</body>
</html>