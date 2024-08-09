package cases;

import elements.LoginElement;
import elements.ShopElement;
import elements.TurnOverElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tools.Browser;
import java.io.IOException;
import sqls.TurnOverSql;
import tools.MyScreenshot;

public class TurnOver {

    public static WebDriver driver;
    public static Browser browser;
    private static MyScreenshot screenshotHelper;

    @BeforeClass
    public static void setUp() throws IOException{
        TurnOver.browser = new Browser();
        TurnOver.browser.InitConfigData();
        driver = TurnOver.browser.getBrowser(); // 这一步才开始调用getBrowser()函数，并生成浏览器driver
        screenshotHelper = new MyScreenshot(driver);  // 实例化截图类,调用screenshot()函数进行截图
        TurnOver.browser.implicitlyWait(10);
    }

    @Test(description = "5-1:登录系统")
    public void Login() {
        TurnOver.browser.input(By.xpath(LoginElement.phone_input_box),"15180381485");
        TurnOver.browser.input(By.xpath(LoginElement.code_input_box),"thinkr");
        TurnOver.browser.explicitlyWait(By.xpath(LoginElement.login_button),10);
        TurnOver.browser.click(By.xpath(LoginElement.login_button));
        System.out.println("登录成功!");
    }

    @Test(dependsOnMethods = {"Login"}, description = "5-2:通过sql语句修改开票状态和开票员")
    public void UpdateStatic() {
        String[] args = new String[0]; // 或者使用 new String[]{}
        TurnOverSql.main(args); //调用TurnOverSql类中的main函数
    }

    @Test(dependsOnMethods = {"UpdateStatic"}, description = "5-3:进入流水记录页面")
    public void TurnOverRecord() {
        TurnOver.browser.explicitlyWait(By.xpath(ShopElement.my_shop_button), 10);
        TurnOver.browser.click(By.xpath(ShopElement.my_shop_button));
        TurnOver.browser.click(By.xpath(ShopElement.shop_logo));
        TurnOver.browser.click(By.xpath(TurnOverElement.turnover_record));
        TurnOver.browser.explicitlyWait(By.xpath(TurnOverElement.turnover_record_text), 10);
        TurnOver.browser.Assert(By.xpath(TurnOverElement.turnover_record_text),"流水记录");
    }

    @Test(dependsOnMethods = {"TurnOverRecord"}, description = "5-4:开票")
    public void Invoice() throws InterruptedException {
        TurnOver.browser.explicitlyWait(By.xpath(TurnOverElement.Invoice), 10);
        TurnOver.browser.click(By.xpath(TurnOverElement.Invoice));
        Thread.sleep(2000);
        TurnOver.browser.click(By.xpath(TurnOverElement.confirm_text));
        TurnOver.browser.explicitlyWait(By.xpath(TurnOverElement.Invoice_text), 10);
        TurnOver.browser.Assert(By.xpath(TurnOverElement.Invoice_text),"开票成功！");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
