package tools;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static tools.SendEmailWithHtmlAttachment.readFileToString;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReportSummary {
    static String htmlFilePath = "/Users/macbook_air/Desktop/Backup/Automation/GasStation/report.html";
    public static String testAll = null;
    public static String testPass = null;
    public static String testFail = null;
    public static String testSkip = null;
    public static String passRate = null;
    public static String beginTime = null;
    public static String totalTime = null;

    public static void main(String[] args) throws IOException {
        String htmlContent = readFileToString(htmlFilePath);

        // 定义正则表达式
        String testAll_regex = "\"testAll\":\\s+(\\d+)";
        Pattern testAll_pattern = Pattern.compile(testAll_regex);
        Matcher testAll_matcher = testAll_pattern.matcher(htmlContent);

        String testPass_regex = "\"testPass\":\\s+(\\d+)";
        Pattern testPass_pattern = Pattern.compile(testPass_regex);
        Matcher testPass_matcher = testPass_pattern.matcher(htmlContent);

        String testFail_regex = "\"testFail\":\\s+(\\d+)";
        Pattern testFail_pattern = Pattern.compile(testFail_regex);
        Matcher testFail_matcher = testFail_pattern.matcher(htmlContent);

        String testSkip_regex = "\"testSkip\":\\s+(\\d+)";
        Pattern testSkip_pattern = Pattern.compile(testSkip_regex);
        Matcher testSkip_matcher = testSkip_pattern.matcher(htmlContent);

        String beginTime_regex = "\"beginTime\": \"(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})\"";
        Pattern beginTime_pattern = Pattern.compile(beginTime_regex);
        Matcher beginTime_matcher = beginTime_pattern.matcher(htmlContent);

        String totalTime_regex = "\"totalTime\": \"([^\"]+)\"";
        Pattern totalTime_pattern = Pattern.compile(totalTime_regex);
        Matcher totalTime_matcher = totalTime_pattern.matcher(htmlContent);


        while (testAll_matcher.find()) {
            testAll = testAll_matcher.group(1);
            System.out.println("用例总数: " + testAll);
        }

        while (testPass_matcher.find()) {
            testPass = testPass_matcher.group(1);
            System.out.println("用例通过: " + testPass);
        }

        while (testFail_matcher.find()) {
            testFail = testFail_matcher.group(1);
            System.out.println("用例失败: " + testFail);
        }

        while (testSkip_matcher.find()) {
            testSkip = testSkip_matcher.group(1);
            System.out.println("用例跳过: " + testSkip);
        }

        if (Integer.parseInt(testSkip) == 0) {
            double rawPassRate = ((double) Integer.parseInt(testPass) / Integer.parseInt(testAll));
            passRate = String.format("%.1f%%", rawPassRate * 100); // 存储格式化的结果
            System.out.println("成功率: " + passRate);
        } else {
            double rawPassRate = ((double) Integer.parseInt(testPass) / (Integer.parseInt(testAll) - Integer.parseInt(testSkip)));
            passRate = String.format("%.1f%%", rawPassRate * 100); // 存储格式化的结果
            System.out.println("成功率: " + passRate);
        }

        while (beginTime_matcher.find()) {
            beginTime = beginTime_matcher.group(1);
            System.out.println("开始时间: " + beginTime);
        }

        while (totalTime_matcher.find()) {
            totalTime = totalTime_matcher.group(1);
            System.out.println("运行时间: " + totalTime);
        }

        ModifyEmail();
        System.out.println("已经执行ReportSummary类");
    }
    public static void ModifyEmail(){

        String emailFilePath = "/Users/macbook_air/Desktop/Backup/Automation/GasStation/src/main/java/tools/email_template.html";

        try {
            // 读取HTML文件到字符串
            String emailContent = Files.readString(Paths.get(emailFilePath));

            Pattern testAll_pattern = Pattern.compile("用例总数:\\s*(\\d+)");
            Matcher testAll_matcher = testAll_pattern.matcher(emailContent);

            Pattern testPass_pattern = Pattern.compile("用例通过:\\s+(\\d+)");
            Matcher testPass_matcher = testPass_pattern.matcher(emailContent);

            Pattern testFail_pattern = Pattern.compile("用例失败:\\s+(\\d+)");
            Matcher testFail_matcher = testFail_pattern.matcher(emailContent);

            Pattern testSkip_pattern = Pattern.compile("用例跳过:\\s+(\\d+)");
            Matcher testSkip_matcher = testSkip_pattern.matcher(emailContent);

            Pattern passRate_pattern = Pattern.compile("成功率:\\s(\\d+\\.\\d+%)");
            Matcher passRate_matcher = passRate_pattern.matcher(emailContent);

            Pattern beginTime_pattern = Pattern.compile("开始时间:\\s(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})");
            Matcher beginTime_matcher = beginTime_pattern.matcher(emailContent);

            Pattern totalTime_pattern = Pattern.compile("运行时间:\\s(\\d+\\.\\d+s)");
            Matcher totalTime_matcher = totalTime_pattern.matcher(emailContent);

            if (testAll_matcher.find()) {
                emailContent = testAll_matcher.replaceAll("用例总数: " + testAll);
            }

            if (testPass_matcher.reset(emailContent).find()) { //这边使用reset()函数，可以重复使用同一个Matcher对象来匹配不同的输入字符串
                emailContent = testPass_matcher.replaceAll("用例通过: " + testPass);
            }

            if (testFail_matcher.reset(emailContent).find()) {
                emailContent = testFail_matcher.replaceAll("用例失败: " + testFail);
            }

            if (testSkip_matcher.reset(emailContent).find()) {
                emailContent = testSkip_matcher.replaceAll("用例跳过: " + testSkip);
            }

            if (passRate_matcher.reset(emailContent).find()) {
                emailContent = passRate_matcher.replaceAll("成功率: " + passRate);
            }

            if (beginTime_matcher.reset(emailContent).find()) {
                emailContent = beginTime_matcher.replaceAll("开始时间: " + beginTime);
            }

            if (totalTime_matcher.reset(emailContent).find()) {
                emailContent = totalTime_matcher.replaceAll("运行时间: " + totalTime);
            }

            // 将修改后的HTML写回到文件（可选）
            Files.write(Paths.get(emailFilePath), emailContent.getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

