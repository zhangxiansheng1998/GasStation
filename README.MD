
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

A.忽略测试用例报错，继续执行后面的函数

    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.18.1</version>
        <configuration>
            <testFailureIgnore>true</testFailureIgnore>
        </configuration>
    </plugin>

B.执行函数

    <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>exec-maven-plugin</artifactId>
        <version>3.0.0</version>
        <executions>
            <execution>
                <id>run-ReportSummary</id>
                <phase>test</phase>
                <goals>
                    <goal>java</goal>
                </goals>
                <configuration>
                    <mainClass>tools.ReportSummary</mainClass>
                </configuration>
            </execution>
        </executions>
    </plugin>

