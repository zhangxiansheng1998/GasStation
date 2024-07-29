package cases;

import elements.LoginElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tools.Browser;
import tools.MyScreenshot;
import java.io.IOException;

public class Login {

    public static WebDriver driver;
    public static Browser browser;
    private static MyScreenshot screenshotHelper;

    @BeforeClass
    public static void setUp() throws IOException{
        Login.browser = new Browser();
        Login.browser.InitConfigData();
        driver = Login.browser.getBrowser(); // 这一步才开始调用getBrowser()函数，并生成浏览器driver
        screenshotHelper = new MyScreenshot(driver);  // 实例化截图类,调用screenshot()函数进行截图
        Login.browser.implicitlyWait(10);
    }

    @Test(description = "1-1:登录系统")
    public void Login() {
        Login.browser.input(By.xpath(LoginElement.phone_input_box),"15180381485");
        Login.browser.input(By.xpath(LoginElement.code_input_box),"thinkr");
        Login.browser.explicitlyWait(By.xpath(LoginElement.login_button),20);
        Login.browser.click(By.xpath(LoginElement.login_button));
        System.out.println("登录成功!");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}