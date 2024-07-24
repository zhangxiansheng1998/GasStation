
修改过的功能

1.ZTestReport：新增字段passRate

    double rawPassRate =  (double) testsPass / (testsPass + testsFail);
    result.put("passRate", String.format("%.1f%%", rawPassRate * 100));


2.template：报告模板中新增"成功率"

    <div class="form-group">
        <label class="col-sm-2 control-label text-navy">成功率:</label>
        <div class="col-sm-5">
            <span class="form-control" id="passRate"></span>
        </div>
    </div>

    $("#passRate").text(resultData["passRate"]);


3.pom：新增参数

A.忽略testng执行测试用例时报错，会继续执行后面的函数

    <testFailureIgnore>true</testFailureIgnore>

