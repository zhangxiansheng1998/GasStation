package tools;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyScreenshot {

    private WebDriver driver;

    public MyScreenshot(WebDriver driver) {
        this.driver = driver;
    }

    public void screenshot() {
        // 检查driver是否实现了TakesScreenshot接口
        if (driver instanceof TakesScreenshot) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
            String screenshotPath = "/Users/macbook_air/Desktop/Backup/Automation/GasStation/src/main/java/screenshots";
            String screenshotName = timestamp + ".png";

            // 截图保存位置，这里假设为项目根目录，你可以根据需要修改
            File dest = new File(screenshotPath, screenshotName);

            try {
                // 复制截图文件到目标位置
                FileUtils.copyFile(screenshot, dest);
                System.out.println("screenshot position: " + dest.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to take screenshot: " + e.getMessage());
            }
        } else {
            System.err.println("WebDriver does not implement TakesScreenshot interface");
        }
    }

    // 如果有需要，您可以添加更多的方法或功能
}

