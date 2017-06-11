<%--
  Created by IntelliJ IDEA.
  User: mzzhang
  Date: 2017/5/29
  Time: 下午11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>管理页面</title>

    <!-- Bootstrap -->
    <link href="../public/css/bootstrap.min.css" rel="stylesheet">
    <link rel='stylesheet' type='text/css' href='../public/bootstrap3-editable/css/bootstrap-editable.css'/>
    <link rel='stylesheet' type='text/css' href='../public/css/bootstrap-table.min.css'/>
    <link rel='stylesheet' type='text/css' href='../public/css/buttons.css'/>
    <link rel='stylesheet' type='text/css' href='../public/css/square/green.css'/>
    <link rel='stylesheet' type='text/css' href='../public/css/common2.css'/>
    <!-- Font Awesome -->
    <link href="../public/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="../public/css/nprogress.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="../public/css/custom.min.css" rel="stylesheet">

    <!-- jQuery -->
    <script src="../public/js/jquery-1.12.0.min.js"></script>
    <!-- Bootstrap -->
    <script src="../public/js/bootstrap.js"></script>
    <script src='../public/js/jquery.md5.js' type='text/javascript'></script>
    <script src='../public/bootstrap3-editable/js/bootstrap-editable.js' type='text/javascript'></script>
    <script src='../public/js/bootstrap-table.js' type='text/javascript'></script>
    <script src='../public/js/bootstrap-table-editable.js' type='text/javascript'></script>


</head>
<div class="modal fade" id="errorAlert" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    Alert!
                </h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-10 col-sm-offset-1">
                        <strong><p id="errorAlert-content"></p></strong>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="ErrorAlertClose"
                        data-dismiss="modal">Close
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

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
</div><!-- /.modal -->
<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <div class="col-md-3 left_col">
            <div class="left_col scroll-view">
                <div class="navbar nav_title" style="border: 0;">
                    <a href="index.html" class="site_title"><span>&nbsp;&nbsp;软件工程管理系统</span></a>
                </div>

                <div class="clearfix"></div>

                <!-- menu profile quick info -->
                <div class="profile clearfix">
                    <div class="profile_info">
                        <span>&nbsp;&nbsp;欢迎你，&nbsp;${user.userName}</span>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <!-- /menu profile quick info -->

                <br />

                <!-- sidebar menu -->
                <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
                    <div class="menu_section">
                        <ul class="nav side-menu">
                            <li><a><i class="fa fa-user"></i> 用户编辑 <span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li><a href="userAdd">添加用户</a></li>
                                    <li><a href="userEdit">查看／编辑用户</a></li>
                                </ul>
                            </li>

                            <li><a><i class="fa fa-wrench"></i> 系统设置 <span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li><a href="termConfig">设置学期基点</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>

                </div>
                <!-- /sidebar menu -->

                <!-- /menu footer buttons -->
                <div class="sidebar-footer hidden-small">
                    <a data-toggle="tooltip" data-placement="top" title="Settings">
                        <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
                    </a>
                    <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                        <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
                    </a>
                    <a data-toggle="tooltip" data-placement="top" title="Lock">
                        <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
                    </a>
                    <a data-toggle="tooltip" data-placement="top" title="Logout" href="login.html">
                        <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
                    </a>
                </div>
                <!-- /menu footer buttons -->
            </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">
            <div class="nav_menu">
                <nav>
                    <div class="nav toggle">
                        <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                    </div>

                    <ul class="nav navbar-nav navbar-right">
                        <li class="">
                            <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                ${user.userName}
                                <span class=" fa fa-angle-down"></span>
                            </a>
                            <ul class="dropdown-menu dropdown-usermenu pull-right">
                                <li><a href="../profile"> 个人中心</a></li>
                                <li><a href="../logout"><i class="fa fa-sign-out pull-right"></i>退出登录</a></li>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
        <!-- /top navigation -->



