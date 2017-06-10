<%--
  Created by IntelliJ IDEA.
  User: mzzhang
  Date: 2017/5/29
  Time: 下午11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"/>

<div class="right_col" role="main">
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3>监考管理</h3>
            </div>

            <div class="title_right">
                <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search for...">
                        <span class="input-group-btn">
                      <button class="btn btn-default" type="button">Go!</button>
                    </span>
                    </div>
                </div>
            </div>
        </div>

        <div class="clearfix"></div>

        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                    <div class="x_title">
                        <h2>添加监考信息</h2>
                        <ul class="nav navbar-right panel_toolbox">
                            <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                            </li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a href="#">Settings 1</a>
                                    </li>
                                    <li><a href="#">Settings 2</a>
                                    </li>
                                </ul>
                            </li>
                            <li><a class="close-link"><i class="fa fa-close"></i></a>
                            </li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">n
                        <div class="row row-margin-bottom">
                            <label for="name" class="col-sm-2 text-right">监考名称：</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="name">
                            </div>
                        </div>
                        <div class="row row-margin-bottom">
                            <label for="date1" class="col-sm-2 text-right">监考日期：</label>
                            <div class="col-sm-9" >

                                    <%--<input type='text' id='date' style="width: 590px;border: 0"/>--%>
                                    <input class="form-control" type="date" id="date1"/>


                            </div>
                        </div>

                        <div class="row row-margin-bottom">
                            <label for="examTime" class="col-sm-2 text-right">监考时间：</label>
                            <div class="col-sm-9">
                                <select class="form-control" type="text" id="examTime" onchange="chg(this);">
                                    <option value="c1">第一、二节</option>
                                    <option value="c2">第三、四节</option>
                                    <option value="c3">第五、六节</option>
                                    <option value="c4">第七、八节</option>
                                </select>
                            </div>
                        </div>

                        <div class="row row-margin-bottom">
                            <label for="startTime" class="col-sm-2 text-right">开始时间：</label>
                            <div class="col-sm-9">

                            <%--    <div class="form-control">
                                    <input class="time" type="text" size="8" readonly onclick="_SetTime(this)" id='startTime' style="width: 590px;border: 0"/>
                                </div>--%>
                                <select class="form-control" type="text" id="startTime" onchange="chg2(this)";>
                                    <option value="08:00:00">08:00:00</option>
                                    <option value="10:10:00">10:10:00</option>
                                    <option value="13:30:00">13:30:00</option>
                                    <option value="15:40:00">15:40:00</option>
                                </select>

                            </div>
                        </div>

                        <div class="row row-margin-bottom">
                            <label for="endTime" class="col-sm-2 text-right">结束时间：</label>
                            <<div class="col-sm-9">
                               <%-- <div class="form-control">
                                    <input class="time" type="text" size="8" readonly onclick="_SetTime(this)" id='endTime' style="width: 590px;border: 0"/>
                                </div>--%>
                            <select class="form-control" type="text" id="endTime">
                                <option value="10:00:00">10:00:00</option>
                                <option value="12:10:00">12:10:00</option>
                                <option value="15:30:00">15:30:00</option>
                                <option value="17:40:00">17:40:00</option>
                            </select>
                            </div>
                        </div>


                        <div class="row row-margin-bottom">
                            <label for="Roomname" class="col-sm-2 text-right">地点：</label>
                            <div class="col-sm-9">
                                <textarea class="form-control" id="Roomname"></textarea>
                            </div>
                        </div>
                        <div class="row row-margin-bottom" id="userAdminCreate-createSubmit">
                            <div class="col-sm-3 col-sm-offset-3">
                                <input class="form-control btn-primary" type="button" id="examAdd" value="添加">
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $('#examAdd').click(function () {
        $.post('adminPost/addInvigilation', {
            name: $('#name').val(),
            date: $('#date1').val(),
            startTime: $('#startTime').val(),
            endTime: $('#endTime').val(),
        }, function (result) {
            if (result.status == 1) {
                $('#errorAlert-content').html(result.message);
                $('#errorAlert').modal('show');
                     $('#name').val(),
                     $('#date').val(),
                     $('#startTime').val(),
                     $('#endTime').val()
            } else {
                $('#errorAlert-content').html(result.message);
                $('#errorAlert').modal('show');
            }
        });
    });
</script>
<%--日期--%>
<%--<script src="../public/calendar/js/jquery-1.11.0.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../public/calendar/js/dcalendar.picker.js"></script>
<script type="text/javascript">
    $('#date').dcalendarpicker({
        format:'yyyy-mm-dd'
    });
</script>--%>
<%--联动--%>
<%--<script>
    //声明省
    var pres = ["第一、二节", "第三、四节", "第五、六节","第七、八节"]; //直接声明Array
    //声明市
    var cities = [
        ["08:00:00"],
        ["10:10:00"],
        ["13:30:00"],
        ["15:40:00"]
    ];
    var areas = [
        [

            ["10:00:00"]
        ],
        [
            ["12:10:00"]
        ],
        [
            ["15:30:00"]
        ],
        [

            ["17:40:00"]
        ]
    ]
    //设置一个省的公共下标
    var pIndex = -1;
    var preEle = document.getElementById("examTime");
    var cityEle = document.getElementById("startTime");
    var areaEle = document.getElementById("endTime");
    //先设置省的值
    for (var i = 0; i < pres.length; i++) {
        //声明option.<option value="pres[i]">Pres[i]</option>
        var op = new Option(pres[i], i);
        //添加
        preEle.options.add(op);
    }
    function chg(obj) {
        if (obj.value == -1) {
            cityEle.options.length = 0;
            areaEle.options.length = 0;
        }
        //获取值
        var val = obj.value;
        pIndex = obj.value;
        //获取ctiry
        var cs = cities[val];
        //获取默认区
        var as = areas[val][0];
        //先清空市
        cityEle.options.length = 0;
        areaEle.options.length = 0;
        for (var i = 0; i < cs.length; i++) {
            var op = new Option(cs[i], i);
            cityEle.options.add(op);
        }
        for (var i = 0; i < as.length; i++) {
            var op = new Option(as[i], i);
            areaEle.options.add(op);
        }
    }
    function chg2(obj) {
        var val = obj.selectedIndex;
        var as = areas[pIndex][val];
        areaEle.options.length = 0;
        for (var i = 0; i < as.length; i++) {
            var op = new Option(as[i], i);
            areaEle.options.add(op);
        }
    }
</script>--%>
<jsp:include page="footer.jsp"/>