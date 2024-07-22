
修改过的功能

1.修改测试报告的接口先后顺序显示问题-顺序排序（只针对reportng生成的测试报告）

包名：reportng-1.1.4.jar
类名：org.uncommons.reportng.TestResultComparator

    public int compare(ITestResult result1, ITestResult result2) {
        int longresult2 = false;
        byte longresult2;
        if (result1.getStartMillis() < result2.getStartMillis()) {
            longresult2 = -1;
        } else {
            longresult2 = 1;
        }
        return longresult2;
    }


