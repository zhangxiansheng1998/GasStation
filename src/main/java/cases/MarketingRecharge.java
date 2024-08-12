package cases;

import elements.*;
import elements.MarketingRechargeElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import sqls.MarketingRechargeSql;
import tools.Browser;
import tools.MyScreenshot;

import java.io.IOException;

public class MarketingRecharge {
    public static WebDriver driver;
    public static Browser browser;
    private static MyScreenshot screenshotHelper;

    @BeforeClass
    public static void setUp() throws IOException {
        MarketingRecharge.browser = new Browser();
        MarketingRecharge.browser.InitConfigData();
        driver = MarketingRecharge.browser.getBrowser(); // 这一步才开始调用getBrowser()函数，并生成浏览器driver
        screenshotHelper = new MyScreenshot(driver);  // 实例化截图类,调用screenshot()函数进行截图
        MarketingRecharge.browser.implicitlyWait(10);
    }

    @Test(description = "8-1:登录系统")
    public void Login() {
        MarketingRecharge.browser.input(By.xpath(LoginElement.phone_input_box),"15180381485");
        MarketingRecharge.browser.input(By.xpath(LoginElement.code_input_box),"thinkr");
        MarketingRecharge.browser.explicitlyWait(By.xpath(LoginElement.login_button),10);
        MarketingRecharge.browser.click(By.xpath(LoginElement.login_button));
        System.out.println("登录成功!");
    }

    @Test(dependsOnMethods = {"Login"}, description = "8-2:进入油卡充值优惠页面")
    public void RechargeDiscount() throws InterruptedException {
        MarketingRecharge.browser.explicitlyWait(By.xpath(ShopElement.my_shop_button), 10);
        MarketingRecharge.browser.click(By.xpath(ShopElement.my_shop_button));
        MarketingRecharge.browser.click(By.xpath(ShopElement.shop_logo));
        MarketingRecharge.browser.click(By.xpath(MarketingRechargeElement.marketing));
        Thread.sleep(2000);
        MarketingRecharge.browser.click(By.xpath(MarketingRechargeElement.recharge_discount));
        MarketingRecharge.browser.explicitlyWait(By.xpath(MarketingRechargeElement.recharge_discount_text), 10);
        MarketingRecharge.browser.Assert(By.xpath(MarketingRechargeElement.recharge_discount_text),"油卡充值优惠");
    }

    @Test(dependsOnMethods = {"RechargeDiscount"}, description = "8-3:新增油卡充值优惠")
    public void AddRechargeDiscount() throws InterruptedException {
        MarketingRecharge.browser.explicitlyWait(By.xpath(MarketingRechargeElement.add_recharge_discount_button), 10);
        MarketingRecharge.browser.click(By.xpath(MarketingRechargeElement.add_recharge_discount_button));
        MarketingRecharge.browser.input(By.xpath(MarketingRechargeElement.recharge_discount_name), MarketingRecharge.browser.GenerateGasGunName());
        MarketingRecharge.browser.input(By.xpath(MarketingRechargeElement.satisfied_amount), "100");
        MarketingRecharge.browser.input(By.xpath(MarketingRechargeElement.donate_fixed_amount), "5");
        MarketingRecharge.browser.click(By.xpath(MarketingRechargeElement.immediately_start_button));
        MarketingRecharge.browser.click(By.xpath(MarketingRechargeElement.confirm_button));
        System.out.println("油卡充值优惠新增成功!");
    }

    @Test(dependsOnMethods = {"AddRechargeDiscount"}, description = "8-4:下线油卡充值优惠")
    public void OfflineRechargeDiscount() throws InterruptedException {
        MarketingRecharge.browser.explicitlyWait(By.xpath(MarketingRechargeElement.offline_button), 10);
        MarketingRecharge.browser.click(By.xpath(MarketingRechargeElement.offline_button));
        MarketingRecharge.browser.click(By.xpath(MarketingRechargeElement.offline_confirm_button));
        MarketingRecharge.browser.explicitlyWait(By.xpath(MarketingRechargeElement.offline_text), 10);
        MarketingRecharge.browser.Assert(By.xpath(MarketingRechargeElement.offline_text),"操作成功");
        System.out.println("油卡充值优惠下线成功!");
    }

    @Test(dependsOnMethods = {"OfflineRechargeDiscount"}, description = "8-5:通过sql语句删除充值优惠")
    public void DeleteRechargeDiscount() {
        String[] args = new String[0]; // 或者使用 new String[]{}
        MarketingRechargeSql.main(args); //调用TurnOverSql类中的main函数
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
