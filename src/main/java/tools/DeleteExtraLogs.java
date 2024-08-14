package tools;

import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

public class DeleteExtraLogs {
    
    public static final Logger logger = Logger.getLogger(Browser.class.getName());

    @Test(description = "99-1:删除多余的log文件")
    public void DeleteLog() {
        String directoryPath = "/Users/macbook_air/Desktop/Backup/Automation/GasStation/log";
        File directory = new File(directoryPath);

        // 确保提供的路径是一个目录
        if (!directory.exists() || !directory.isDirectory()) {
            logger.info("指定的路径不是一个目录，或者目录不存在！");
            return;
        }

        // 列出目录下的所有文件和目录
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                // 跳过名为my.log的文件
                if (!file.getName().equals("my.log")) {
                    // 使用Files.deleteIfExists确保文件存在时才删除
                    try {
                        Files.deleteIfExists(file.toPath());
                        logger.info("已删除多余的log文件: " + file.getName());
                    } catch (IOException e) {
                        System.err.println("无法删除多余的log文件: " + file.getName() + "。原因: " + e.getMessage());
                    }
                }
            }
        }
    }
}

